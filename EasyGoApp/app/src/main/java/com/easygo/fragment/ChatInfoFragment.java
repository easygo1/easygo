package com.easygo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easygo.activity.R;
import com.easygo.beans.user.User;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatInfoFragment extends Fragment{
    private Context mContext;
    //添加好友集合
    private List<User> userIdList;

    public static final String TYPE = "type";
    //聊天会话列表
    View mChatInfoView;
    String mUrl,token=null,user_nickname=null,user_photo=null,phone=null;
    SharedPreferences mSharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatInfoView = inflater.inflate(R.layout.chat_info, container,false);
        //获取到手机号和token
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        token = mSharedPreferences.getString("token",null);
        user_nickname = mSharedPreferences.getString("user_nickname",null);
        user_photo = mSharedPreferences.getString("user_photo",null);
        phone = mSharedPreferences.getString("phone",null);
        Log.e("聊天好友界面的token",token);
        //进行融云连接
        connect(token);
        //用户信息提供者
        initUserInfo();

        //初始化会话列表
        initConversation();

        return mChatInfoView;
    }

    //初始化会话列表
    private void initConversation() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */

    private void connect(String token) {
        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("token错误", "请检查APP-KEY");
            }
            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("连接成功", "--onSuccess" + userid);
            }
            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("连接失败", "--onError" + errorCode);
            }
        });
    }

    //生命周期伴随着总进程
    public UserInfo getUserInfo(String s) {
        //循环获取当前用户信息的userid
        for (User i : userIdList) {
            if (i.getUser_phone().equals(s)) {
               // Log.e(TAG, i.getPortraitUri());
                return new UserInfo(i.getUser_realname(), i.getUser_realname(), Uri.parse(i.getUser_photo()));
            }
        }
        Log.e("MainActivity", "UserId is:" + s);
        return null;
    }


    private void initUserInfo() {
        userIdList = new ArrayList<>();
        //连接用户的昵称，手机号，头像
        userIdList.add(new User(user_nickname,phone,user_photo));
        //用户信息提供者
        RongIM.setUserInfoProvider((RongIM.UserInfoProvider) mContext, true);
    }
}
