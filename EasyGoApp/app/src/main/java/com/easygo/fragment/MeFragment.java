package com.easygo.fragment;

import android.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.activity.LogintestActivity;
import com.easygo.activity.R;
import com.easygo.activity.ReleasesroomActivity;


/**
 * Created by PengHong on 2016/4/29.
 */
public class MeFragment extends Fragment {
    public static final String TYPE = "type";
    //得到绑定的界面布局
    View mView;
    int type = 0;
    ImageView mMe_user_imageview;
    TextView mMe_customer_releaseroomTextView;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        type = mSharedPreferences.getInt("type",0);
        //type为0表示未登录状态
        if(type ==0 ){
            mView =inflater.inflate(R.layout.bottom_me,null);
            mMe_user_imageview = (ImageView) mView.findViewById(R.id.me_user_imageview);
            mMe_user_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
            });
        }else if(type == 1){//type为1表示以房客身份登录
            mView =inflater.inflate(R.layout.bottom_me_customer,null);
            mMe_customer_releaseroomTextView = (TextView) mView.findViewById(R.id.me_customer_releaseroom);
            mMe_customer_releaseroomTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ReleasesroomActivity.class);
                    startActivity(intent);
                }
            });
        }else{//表示以房东身份登录
            mView =inflater.inflate(R.layout.bottom_me_owner,null);
        }

        return mView ;

    }

}
