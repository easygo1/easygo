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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easygo.activity.CustomOrderActivity;
import com.easygo.activity.HouseCollectionActivity;
import com.easygo.activity.LogintestActivity;
import com.easygo.activity.MyInfomationActivity;
import com.easygo.activity.MyWalletActivity;
import com.easygo.activity.R;
import com.easygo.activity.RegisterActivity;
import com.easygo.activity.ReleasesroomActivity;
import com.easygo.activity.SetActivity;
import com.easygo.activity.UserLinkmanActivity;



/**
 * Created by PengHong on 2016/4/29.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    public static final String TYPE = "type";
    Intent intent;
    //得到绑定的界面布局
    View mView;
    int type = 0;
    private ImageView meMyset;
    private ImageView meUserImageview;
    private TextView meUserLogin;
    private TextView meUserRegister;
    private LinearLayout myorder;
    private LinearLayout mycollection;
    private LinearLayout mywallet;
    private LinearLayout myinformation;
    private TextView meLinkman;
    private TextView meCertification;
    private TextView meReleaseroom;
    private TextView meCustomerservice;
    private ImageView meCustomerMyset;
    private ImageView meCustomerUserImageview;
    private TextView meCustomerustomers;
    private TextView meCustomerDescriptustomer;
    private TextView meCustomerMypoints;

    private TextView meCustomerDescription;
    private TextView meCustomerLinkman;
    private TextView meCustomerCertification;
    private TextView meCustomerReleaseroom;
    private TextView meCustomerCustomerservice;
    private ImageView meOwnerMyset;
    private ImageView meOwnerUserImageview;
    private TextView meOwnerMypoints;
    private TextView meOwnerDescription;
    private TextView meOwnerLinkman;
    private TextView meOwnerDatemanage;
    private TextView meOwnerMyroom;
    private TextView meOwnerCustomerservice;
    SharedPreferences mSharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        type = mSharedPreferences.getInt("type",0);
        //type为0表示未登录状态
        if(type ==0 ){
            mView =inflater.inflate(R.layout.bottom_me,null);
            initNoLoginViews();
            addNoLoginListeners();
        }else if(type == 1){//type为1表示以房客身份登录
            mView =inflater.inflate(R.layout.bottom_me_customer,null);
            initCustomerViews();
            addCustomerListeners();
        }else{//表示以房东身份登录
            mView =inflater.inflate(R.layout.bottom_me_owner,null);
            initOwnerViews();
            addOwnerListeners();
        }
        return mView ;

    }
    //未登录状态的
    private void initNoLoginViews() {
        meMyset = (ImageView) mView.findViewById(R.id.me_myset);
        meUserImageview = (ImageView) mView.findViewById(R.id.me_user_imageview);
        meUserLogin = (TextView) mView.findViewById(R.id.me_user_login);
        meUserRegister = (TextView) mView.findViewById(R.id.me_user_register);
        myorder = (LinearLayout) mView.findViewById(R.id.myorder);
        mycollection = (LinearLayout) mView.findViewById(R.id.mycollection);
        mywallet = (LinearLayout) mView.findViewById(R.id.mywallet);
        myinformation = (LinearLayout) mView.findViewById(R.id.myinformation);
        meLinkman = (TextView) mView.findViewById(R.id.me_linkman);
        meCertification = (TextView) mView.findViewById(R.id.me_certification);
        meReleaseroom = (TextView) mView.findViewById(R.id.me_releaseroom);
        meCustomerservice = (TextView) mView.findViewById(R.id.me_customerservice);
    }
    //房客状态的
    private void initCustomerViews(){
        meCustomerMyset = (ImageView) mView.findViewById(R.id.me_customer_myset);
        meCustomerUserImageview = (ImageView) mView.findViewById(R.id.me_customer_user_imageview);
        meCustomerMypoints = (TextView) mView.findViewById(R.id.me_customer_mypoints);
        meCustomerDescriptustomer = (TextView) mView.findViewById(R.id.me_customer_description);
        meCustomerLinkman = (TextView) mView.findViewById(R.id.me_customer_linkman);
        meCustomerCertification = (TextView) mView.findViewById(R.id.me_customer_certification);
        meCustomerReleaseroom = (TextView) mView.findViewById(R.id.me_customer_releaseroom);
        meCustomerCustomerservice = (TextView) mView.findViewById(R.id.me_customer_customerservice);
        myorder = (LinearLayout) mView.findViewById(R.id.myorder);
        mycollection = (LinearLayout) mView.findViewById(R.id.mycollection);
        mywallet = (LinearLayout) mView.findViewById(R.id.mywallet);
        myinformation = (LinearLayout) mView.findViewById(R.id.myinformation);
    }
    //房东状态的
    private void initOwnerViews(){
        meOwnerMyset = (ImageView) mView.findViewById(R.id.me_owner_myset);
        meOwnerUserImageview = (ImageView) mView.findViewById(R.id.me_owner_user_imageview);
        meOwnerMypoints = (TextView) mView.findViewById(R.id.me_owner_mypoints);
        meOwnerDescription = (TextView) mView.findViewById(R.id.me_owner_description);
        meOwnerLinkman = (TextView) mView.findViewById(R.id.me_owner_linkman);
        meOwnerDatemanage = (TextView) mView.findViewById(R.id.me_owner_datemanage);
        meOwnerMyroom = (TextView) mView.findViewById(R.id.me_owner_myroom);
        meOwnerCustomerservice = (TextView) mView.findViewById(R.id.me_owner_customerservice);
        myorder = (LinearLayout) mView.findViewById(R.id.myorder);
        mycollection = (LinearLayout) mView.findViewById(R.id.mycollection);
        mywallet = (LinearLayout) mView.findViewById(R.id.mywallet);
        myinformation = (LinearLayout) mView.findViewById(R.id.myinformation);
    }
    //设置未登录时界面的监听
    private void addNoLoginListeners() {
        meMyset.setOnClickListener(this);
        meUserImageview.setOnClickListener(this);
        meUserLogin.setOnClickListener(this);
        meUserRegister.setOnClickListener(this);
        meLinkman.setOnClickListener(this);
        meCertification.setOnClickListener(this);
        meReleaseroom.setOnClickListener(this);
        meCustomerservice.setOnClickListener(this);
        myorder.setOnClickListener(this);
        mycollection.setOnClickListener(this);
        mywallet.setOnClickListener(this);
        myinformation.setOnClickListener(this);
    }
    //设置房客状态我的界面的监听
    private void addCustomerListeners() {
        meCustomerMyset.setOnClickListener(this);
        meCustomerUserImageview.setOnClickListener(this);
        meCustomerLinkman.setOnClickListener(this);
        meCustomerCertification.setOnClickListener(this);
        meCustomerReleaseroom.setOnClickListener(this);
        meCustomerCustomerservice.setOnClickListener(this);
        myorder.setOnClickListener(this);
        mycollection.setOnClickListener(this);
        mywallet.setOnClickListener(this);
        myinformation.setOnClickListener(this);
    }
    //设置房东状态我的界面的监听
    private void addOwnerListeners() {
        meOwnerMyset.setOnClickListener(this);
        meOwnerUserImageview.setOnClickListener(this);
        meOwnerLinkman.setOnClickListener(this);
        meOwnerDatemanage.setOnClickListener(this);
        meOwnerMyroom.setOnClickListener(this);
        meOwnerCustomerservice.setOnClickListener(this);
        myorder.setOnClickListener(this);
        mycollection.setOnClickListener(this);
        mywallet.setOnClickListener(this);
        myinformation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case  R.id.me_myset:
                intent= new Intent();
                intent.setClass(getActivity(), SetActivity.class);
                startActivity(intent);
                break;
            case R.id.me_user_imageview:
                intent= new Intent();
                intent.setClass(getActivity(), LogintestActivity.class);
                startActivity(intent);
                break;
            case R.id.me_user_login:
                intent= new Intent();
                intent.setClass(getActivity(), LogintestActivity.class);
                startActivity(intent);
                break;
            case R.id.me_user_register:
                intent= new Intent();
                intent.setClass(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.me_customerservice:
                intent = new Intent();
                intent.setClass(getActivity(), CustomOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.me_customer_myset:
                intent = new Intent();
                intent.setClass(getActivity(), SetActivity.class);
                startActivity(intent);
                break;
            case R.id.me_customer_releaseroom:
                intent = new Intent();
                intent.setClass(getActivity(), ReleasesroomActivity.class);
                startActivity(intent);
                break;
            case R.id.me_customer_linkman:
                intent = new Intent();
                intent.setClass(getActivity(), UserLinkmanActivity.class);
                startActivity(intent);
                break;
            case R.id.myinformation:
                intent = new Intent();
                intent.setClass(getActivity(), MyInfomationActivity.class);
                startActivity(intent);
                break;
            case R.id.mywallet:
                intent = new Intent();
                intent.setClass(getActivity(), MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.mycollection:
                intent = new Intent();
                intent.setClass(getActivity(), HouseCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.me_owner_myset:
                intent = new Intent();
                intent.setClass(getActivity(), SetActivity.class);
                startActivity(intent);
                break;
        }
    }
}
