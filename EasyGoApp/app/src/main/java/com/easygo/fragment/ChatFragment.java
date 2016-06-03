package com.easygo.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.easygo.activity.AddFriendActivity;
import com.easygo.activity.LogintestActivity;
import com.easygo.activity.R;
import com.easygo.activity.RegisterActivity;
import com.easygo.view.MyPopDownPopWindow;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{
    public static final String USERTYPE = "type";
    //来判断是否登录
    int type = 0;
    //初始化控件
    private Button mButton_chat_info, mButton_chat_friend, mButton_chat_dynamic,mChatLogin,mChatReg;
    private ImageView add_friend;
    //定义聊天界面的碎片
    private ChatInfoFragment mChatInfoFragment;
    private ChatFriendFragment mChatFriendFragment;
    private ChatDynamicFragment mChatDynamicFragment;

    private FragmentManager mChatFragmentManager;
    private FragmentTransaction mChatFragmentTransaction;
    //添加好友的popwindow
    private MyPopDownPopWindow popMenus;

    private View mChatView;
    SharedPreferences mSharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(USERTYPE, Context.MODE_PRIVATE);
        type = mSharedPreferences.getInt("type",0);
        //type为0表示未登录状态
        if(type ==0 ){
            mChatView =inflater.inflate(R.layout.fragment_chat_nologin,null);
            initNoLoginViews();
            addNoLoginListeners();

        }else{//表示已登录状态
            mChatView = inflater.inflate(R.layout.bottom_chat, null);
            initChatView();
            addChatListeners();
            initChatDefault();
        }
        return mChatView ;
    }
    //设置未登录时按钮的监听
    private void addNoLoginListeners() {
        //跳转到登录界面
        mChatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin=new Intent(getActivity(), LogintestActivity.class);
                startActivity(intentlogin);
            }
        });
        //跳转到注册界面
        mChatReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreg=new Intent(getActivity(), RegisterActivity.class);
                startActivity(intentreg);
            }
        });
    }
    //初始化未登录时控件
    private void initNoLoginViews() {
        mChatLogin= (Button) mChatView.findViewById(R.id.chat_login);
        mChatReg= (Button) mChatView.findViewById(R.id.chat_reg);
    }
    //初始化已登录时的控件
    private void initChatView() {
        mButton_chat_info= (Button) mChatView.findViewById(R.id.chat_info);
        mButton_chat_friend = (Button) mChatView.findViewById(R.id.chat_friend);
        mButton_chat_dynamic = (Button) mChatView.findViewById(R.id.chat_dynamic);
        add_friend= (ImageView) mChatView.findViewById(R.id.add_friend);
    }
    //设置已登录时的控件
    private void addChatListeners() {
        //设置监听
        mButton_chat_friend.setOnClickListener(this);
        mButton_chat_info.setOnClickListener(this);
        mButton_chat_dynamic.setOnClickListener(this);
        add_friend.setOnClickListener(this);
    }

    private void initChatDefault() {
        //开始先显示第一个界面
        mButton_chat_info.setBackgroundResource(R.drawable.chatmybtn);
        mChatFragmentManager = getFragmentManager();
        mChatFragmentTransaction = mChatFragmentManager.beginTransaction();
        //默认显示买模块，初始化买碎片
        mChatInfoFragment = new ChatInfoFragment();
        mChatFragmentTransaction.add(R.id.chat_middle,mChatInfoFragment);
        mChatFragmentTransaction.commit();
    }

    private void initChatFragment(int id) {
        //隐藏所有已经添加到事务中的碎片
        hideChatFragments();
        mChatFragmentTransaction = mChatFragmentManager.beginTransaction();

        //判断当前碎片是哪一个
        switch (id) {
            case R.id.chat_info:
                if(mChatInfoFragment == null){
                    mChatInfoFragment = new ChatInfoFragment();
                    mChatFragmentTransaction.add(R.id.chat_middle,mChatInfoFragment);
                }else{
                    mChatFragmentTransaction.show(mChatInfoFragment);
                }
                break;
            case R.id.chat_friend:
                if(mChatFriendFragment == null){
                    mChatFriendFragment = new ChatFriendFragment();
                    mChatFragmentTransaction.add(R.id.chat_middle,mChatFriendFragment);
                }else{
                    mChatFragmentTransaction.show(mChatFriendFragment);
                }
                break;
            case R.id.chat_dynamic:
                if(mChatDynamicFragment == null){
                    mChatDynamicFragment = new ChatDynamicFragment();
                    mChatFragmentTransaction.add(R.id.chat_middle,mChatDynamicFragment);
                }else{
                    mChatFragmentTransaction.show(mChatDynamicFragment);
                }
                break;
        }
        mChatFragmentTransaction.commit();
    }
    //显示添加好友的dialog
    private void showDialog() {
        //自定义的弹出框类
        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenus= new MyPopDownPopWindow(getActivity(),itemsOnClick);
                popMenus.showAsDropDown(getActivity().findViewById(R.id.add_friend));
            }
        });
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            //消除popwindow
            popMenus.dismiss();
            switch (v.getId()) {
                //按照好友姓名进行查找好友
                case R.id.add_friend_username:
                    Intent selectfriend=new Intent(getActivity(), AddFriendActivity.class);
                    startActivity(selectfriend);
                    break;
                //按照好友地址查找好友
                case R.id.add_friend_address:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id=v.getId();

        //恢复所有按钮颜色
        resetchat();

        openPopWindow(id);
        //设置当前选中图标和文本颜色
        setButtonColor(id);
        //设置当前碎片
        initChatFragment(id);
    }
    //显示popwindow进行添加好友
    private void openPopWindow(int id) {
        switch (id){
            case R.id.add_friend:
                showDialog();
                break;
        }
    }
    //点击改变按钮的颜色
    private void setButtonColor(int id) {
        //参数表示选中项线性布局的id
        switch (id) {
            case R.id.chat_info:
                mButton_chat_info.setBackgroundResource(R.drawable.chatmybtn);
                break;
            case R.id.chat_friend:
                mButton_chat_friend.setBackgroundResource(R.drawable.chatmybtn);
                break;
            case R.id.chat_dynamic:
                mButton_chat_dynamic.setBackgroundResource(R.drawable.chatmybtn);
                break;
        }
    }
    //把聊天按钮没有单击时进行还原
    private void resetchat() {
        mButton_chat_info.setBackgroundResource(R.drawable.chatmybtn2);
        mButton_chat_friend.setBackgroundResource(R.drawable.chatmybtn2);
        mButton_chat_dynamic.setBackgroundResource(R.drawable.chatmybtn2);
    }
    //隐藏所有的碎片，首先需要知道碎片是否已经添加到事务中
    private void hideChatFragments() {
        mChatFragmentTransaction = mChatFragmentManager.beginTransaction();
        if(mChatInfoFragment!=null && mChatInfoFragment.isAdded()){
            mChatFragmentTransaction.hide(mChatInfoFragment);
        }
        if(mChatFriendFragment!=null && mChatFriendFragment.isAdded()){
            mChatFragmentTransaction.hide(mChatFriendFragment);
        }
        if(mChatDynamicFragment!=null && mChatDynamicFragment.isAdded()){
            mChatFragmentTransaction.hide(mChatDynamicFragment);
        }
        mChatFragmentTransaction.commit();
    }
}