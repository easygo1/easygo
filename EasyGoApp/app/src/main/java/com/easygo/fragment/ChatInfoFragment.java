package com.easygo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easygo.activity.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatInfoFragment extends Fragment {
    public static final String TYPE = "type";
    protected static final String TAG = "MainActivity";
    View mChatInfoView;
    String token=null;
    //设置偏好设置
    SharedPreferences mSharedPreferences;
    //String token="evekT76cSp37ByW613Kyoyo/1ow4sU0qSqSJansyKi1XKgCqP0n+5Ri6T6OYIIpMiuhOdiyOaBwyT9oF+eMegw==";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatInfoView=inflater.inflate(R.layout.chat_info,null);

        //设置偏好设置获取token
        mSharedPreferences =getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        token=mSharedPreferences.getString("token",null);
        //成功获取到token的值
        Log.e("token",token);

        Button btn= (Button) mChatInfoView.findViewById(R.id.btn);
        connectRongServer(token);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(getActivity(),"admin","qqq");
                }
            }
        });
        return mChatInfoView;
    }
    //进行融云连接操作
    private void connectRongServer(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "token错误，请检查APPKEY");
            }

            @Override
            public void onSuccess(String userId) {
                Log.e(TAG,"连接成功"+userId);
            }

            @Override

            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "连接失败的id：" + errorCode.getValue());
            }
        });
    }


}
