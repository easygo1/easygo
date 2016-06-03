package com.easygo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.beans.user.User;
import com.easygo.view.WaitDialog;
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

public class LogintestActivity extends AppCompatActivity {
    //请求对象
    public static final int Login=6;
    //自定义一个dialog
    private WaitDialog mDialog;
    /**
     * 请求队列.
     */
    private RequestQueue mRequestQueue;
    String mUrl,mPhoneString,mLoginPassword;
    EditText muser_phone,muser_password;

    //偏好设置
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    //定义一个user对象
    User user;
    String token,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logintest);
        initView();
        initData();
    }

    private OnResponseListener<String> onResponseListener=new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            //需要if判断，如果根据用户名和密码查找出来的是null则显示用户名和密码错误
            //如果返回的是一个正常的token则显示登录成功
            if(what == Login) {
               // 请求成功，直接更新UI
                String result = response.get();
                Log.e("json返回结果",result);
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type mytype = new TypeToken<User>() {
                }.getType();
                user=gson.fromJson(result,mytype);

                if(user.getToken()==null){
                    Toast.makeText(LogintestActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LogintestActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功后进行页面的跳转
                    Intent intent = new Intent();
                    intent.putExtra("flag","me");
                    intent.setClass(LogintestActivity.this,MainActivity.class);
                    startActivity(intent);

                    //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
                    mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
                    //向偏好设置文件中保存数据
                    mEditor = mSharedPreferences.edit();
                    mEditor.putInt("user_id",user.getUser_id());
                    mEditor.putString("user_realname",user.getUser_realname());
                    mEditor.putString("user_nickname",user.getUser_nickname());
                    mEditor.putString("user_sex",user.getUser_sex());
                    mEditor.putInt("user_type",user.getUser_type());
                    mEditor.putString("user_photo",user.getUser_photo());
                    mEditor.putString("user_job",user.getUser_job());
                    mEditor.putString("user_address_province",user.getUser_address_province());
                    mEditor.putString("user_address_city",user.getUser_address_city());
                    mEditor.putString("user_mood",user.getUser_mood());
                    mEditor.putString("user_mail",user.getUser_mail());
                    mEditor.putString("user_introduct",user.getUser_introduct());
                    mEditor.putString("user_birthday",user.getUser_birthday());
                    mEditor.putString("user_idcard",user.getUser_idcard());
                    mEditor.putString("token",user.getToken());
                    mEditor.putString("remarks",user.getRemarks());
                    mEditor.putString("phone",mPhoneString);
                    mEditor.putInt("type", 1);
                    //提交保存结果
                    mEditor.commit();
                }
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(LogintestActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(LogintestActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(LogintestActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(LogintestActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(LogintestActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(LogintestActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(LogintestActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(LogintestActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }
    };

    private void initView() {
        muser_phone= (EditText) findViewById(R.id.user_phone);
        muser_password= (EditText) findViewById(R.id.user_password);
        mDialog = new WaitDialog(this);
    }

    private void initData() {
        MyApplication myApplication=new MyApplication();
        mUrl=myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
    }

    /**
     * 请求服务器，并向服务器传输用户名和密码
     */
    private void startLoginRequest() {
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //传输从android端获取到的数据
        request.add("methods", "login");
        request.add("user_phone", mPhoneString);
        request.add("user_password", mLoginPassword);

        //将请求添加到队列中
        mRequestQueue.add(Login, request, onResponseListener);
    }

    public void login(View view) {
        /*//将输入的数据变成字符串
        mLoginPassword=muser_password.getText().toString();
        mPhoneString = muser_phone.getText().toString();
        if(mPhoneString.equals("")) {
            //判断手机号是否为空
            Toast.makeText(LogintestActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(mLoginPassword.equals("")) {
            //判断验证码是否为空
            Toast.makeText(LogintestActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            //向服务端传输输入的数据进行登录操作
            startLoginRequest();
        }*/
        //登录成功后进行页面的跳转
        Intent intent = new Intent();
        intent.putExtra("flag","me");
        intent.setClass(LogintestActivity.this,MainActivity.class);
        startActivity(intent);

        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
        mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putString("token",token);
        mEditor.putString("user_id",user_id);
        mEditor.putString("phone",mPhoneString);
        mEditor.putInt("type", 1);
        //提交保存结果
        mEditor.commit();
    }

    //登录页面的注册，跳转到注册页面
    public void reg(View view) {
        Intent intent=new Intent(LogintestActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
