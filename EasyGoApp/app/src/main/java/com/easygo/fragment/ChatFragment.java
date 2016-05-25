package com.easygo.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.easygo.activity.R;
import com.easygo.view.MyPopDownPopWindow;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{

    Button mButton_chat_info, mButton_chat_friend, mButton_chat_dynamic;
    ImageButton add_friend;
    private ChatInfoFragment mChatInfoFragment;
    private ChatFriendFragment mChatFriendFragment;
    private ChatDynamicFragment mChatDynamicFragment;
    private FragmentManager mChatFragmentManager;
    private FragmentTransaction mChatFragmentTransaction;
    private MyPopDownPopWindow popMenus;
    View mChatView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatView = inflater.inflate(R.layout.bottom_chat, null);
        initChatView();

        addChatListeners();
        initChatDefault();
        return mChatView;
    }
    private void initChatView() {

        mButton_chat_info= (Button) mChatView.findViewById(R.id.chat_info);
        mButton_chat_friend = (Button) mChatView.findViewById(R.id.chat_friend);
        mButton_chat_dynamic = (Button) mChatView.findViewById(R.id.chat_dynamic);
        add_friend= (ImageButton) mChatView.findViewById(R.id.add_friend);
    }
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
                popMenus=new MyPopDownPopWindow(getActivity(),itemsOnClick) ;
                /*popMenus.showAtLocation(ChatFragment.this.getActivity().findViewById(R.id.add_friend),
                        Gravity.BOTTOM, 0, 0);*/

                popMenus.showAsDropDown(getActivity().findViewById(R.id.add_friend));
            }
        });
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            popMenus.dismiss();
            switch (v.getId()) {
                case R.id.add_friend_username:

                    break;
                case R.id.add_friend_address:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        //恢复所有按钮颜色
        resetchat();
        int id=v.getId();
        openPopWindow(id);
        //设置当前选中图标和文本颜色
        setButtonColor(id);
        //设置当前碎片
        initChatFragment(id);
    }

    private void openPopWindow(int id) {
        switch (id){
            case R.id.add_friend:
                showDialog();
                break;
        }

    }

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

    private void resetchat() {
        //把聊天按钮没有单击时进行还原
        mButton_chat_info.setBackgroundResource(R.drawable.chatmybtn2);
        mButton_chat_friend.setBackgroundResource(R.drawable.chatmybtn2);
        mButton_chat_dynamic.setBackgroundResource(R.drawable.chatmybtn2);
    }
    private void hideChatFragments() {
        //隐藏所有的碎片，首先需要知道碎片是否已经添加到事务中
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
