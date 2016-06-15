package com.easygo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easygo.activity.CustomOrderActivity;
import com.easygo.activity.DateManageActivity;
import com.easygo.activity.HouseCollectionActivity;
import com.easygo.activity.LogintestActivity;
import com.easygo.activity.MyInfomationActivity;
import com.easygo.activity.MyWalletActivity;
import com.easygo.activity.OwnerOrderActivity;
import com.easygo.activity.R;
import com.easygo.activity.RealNameIdentifyActivity;
import com.easygo.activity.RegisterActivity;
import com.easygo.activity.ReleasesroomActivity;
import com.easygo.activity.SetActivity;
import com.easygo.activity.UpdateroomActivity;
import com.easygo.activity.UserLinkmanActivity;
import com.easygo.application.MyApplication;
import com.easygo.beans.user.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.lang.reflect.Type;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by PengHong on 2016/4/29.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    public static final String TYPE = "type";
    public static final int WHAT_GETUSERPHOTO = 1;
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
    private TextView mIsIdentifyTextView;
   // private ImageView mIsIdentifyImageView;

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

    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();//请求队列

    private int user_id;
    private User user;


    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT_GETUSERPHOTO) {
                String result = response.get();
                //Toast.makeText(getActivity(), "得到用户头像了" + result, Toast.LENGTH_SHORT).show();
                //解析对象
                Gson gson = new Gson();
                Type mytype = new TypeToken<User>() {
                }.getType();
                user = gson.fromJson(result, mytype);
                if (type == 1) {
                    //房客状态
                    Glide.with(getActivity())
                            .load(user.getUser_photo())
                            .bitmapTransform(new CropCircleTransformation(getActivity()))
                            .error(R.mipmap.user_photo_defult)
                            .into(meCustomerUserImageview);
                    meCustomerDescriptustomer.setText("个性签名："+user.getUser_mood());
                    if (user.getUser_idcard()==null){
                        //mIsIdentifyImageView.setImageResource(R.mipmap.no_realname);
                        mIsIdentifyTextView.setText("未认证");
                    }
                } else if (type == 2) {
                    //房东状态
                    Glide.with(getActivity())
                            .load(user.getUser_photo())
                            .bitmapTransform(new CropCircleTransformation(getActivity()))
                            .error(R.mipmap.user_photo_defult)
                            .into(meOwnerUserImageview);
                    meOwnerDescription.setText(user.getUser_mood());
                }
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(getActivity(), "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(getActivity(), "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(getActivity(), "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(getActivity(), "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(getActivity(), "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(getActivity(), "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        type = mSharedPreferences.getInt("type", 0);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        if (type != 0) {
            myApplication = (MyApplication) getActivity().getApplication();
            mUrl = myApplication.getUrl();
            //创建请求对象
            request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
            //添加请求参数
            request.add("methods", "getUserInfo");
            request.add("user_id", user_id);
            mRequestQueue.add(WHAT_GETUSERPHOTO, request, mOnResponseListener);
        }

        //type为0表示未登录状态
        if (type == 0) {
            mView = inflater.inflate(R.layout.bottom_me, null);
            initNoLoginViews();
            addNoLoginListeners();
        } else if (type == 1) {//type为1表示以房客身份登录
            mView = inflater.inflate(R.layout.bottom_me_customer, null);
            initCustomerViews();
            addCustomerListeners();
        } else {//表示以房东身份登录
            mView = inflater.inflate(R.layout.bottom_me_owner, null);
            initOwnerViews();
            addOwnerListeners();
        }
        return mView;

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
    private void initCustomerViews() {
        meCustomerMyset = (ImageView) mView.findViewById(R.id.me_customer_myset);
        meCustomerUserImageview = (ImageView) mView.findViewById(R.id.me_customer_user_imageview);
        meCustomerMypoints = (TextView) mView.findViewById(R.id.me_customer_mypoints);
        meCustomerDescriptustomer = (TextView) mView.findViewById(R.id.me_customer_description);
        meCustomerLinkman = (TextView) mView.findViewById(R.id.me_customer_linkman);
        meCustomerCertification = (TextView) mView.findViewById(R.id.me_customer_certification);
        meCustomerReleaseroom = (TextView) mView.findViewById(R.id.me_customer_releaseroom);
        meCustomerCustomerservice = (TextView) mView.findViewById(R.id.me_customer_customerservice);
        mIsIdentifyTextView= (TextView) mView.findViewById(R.id.is_identify_text);
        //mIsIdentifyImageView= (ImageView) mView.findViewById(R.id.is_identify_img);

        myorder = (LinearLayout) mView.findViewById(R.id.myorder);
        mycollection = (LinearLayout) mView.findViewById(R.id.mycollection);
        mywallet = (LinearLayout) mView.findViewById(R.id.mywallet);
        myinformation = (LinearLayout) mView.findViewById(R.id.myinformation);
    }

    //房东状态的
    private void initOwnerViews() {
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
        switch (id) {
            case R.id.me_myset:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), SetActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_user_imageview:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_user_login:
                intent = new Intent();
                intent.setClass(getActivity(), LogintestActivity.class);
                startActivity(intent);
                break;
            case R.id.me_user_register:
                intent = new Intent();
                intent.setClass(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.me_customerservice:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), CustomOrderActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_customer_myset:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), SetActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_customer_releaseroom:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), ReleasesroomActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_customer_linkman:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), UserLinkmanActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.myinformation:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), MyInfomationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mywallet:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), MyWalletActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mycollection:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), HouseCollectionActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.myorder:
                if (type == 1) {
                    intent = new Intent();
                    intent.setClass(getActivity(), CustomOrderActivity.class);
                    startActivity(intent);
                }else if (type == 2){
                    intent = new Intent();
                    intent.setClass(getActivity(),OwnerOrderActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_owner_myset:
                if (type != 0) {
                    intent = new Intent();
                    intent.setClass(getActivity(), SetActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请去登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(getActivity(), LogintestActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_owner_myroom:
                intent = new Intent();
                intent.setClass(getActivity(), UpdateroomActivity.class);
                startActivity(intent);
                break;
            case R.id.me_customer_certification:
                intent = new Intent();
                intent.setClass(getActivity(), RealNameIdentifyActivity.class);
                startActivity(intent);
                break;
            case R.id.me_owner_datemanage:
                //可租日期管理
                intent = new Intent();
                intent.setClass(getActivity(), DateManageActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                break;
        }
    }

}
