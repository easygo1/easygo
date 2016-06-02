package com.easygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easygo.application.MyApplication;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends AppCompatActivity {
    public static final int ChangeButtonWHAT = 1;
    public static final int ResetButtonWHAT = 2;
    public static final int VerificationError = 3;
    public static final int VerificationSuccess = 4;
    public static final int Register=5;
    //手机号和验证码的Edittext
    EditText mTextPhone,mTextErificationCode,mregister_password;
    //手机号和验证码字符串，密码字符串
    String mPhoneString,mErificationString,mRegisterPassword;
    //获取验证码按钮,注册按钮
    Button mButton;
    //相隔几秒获取验证码
    int shortTime = 0;

    String APPKEY = "133cbdb82f328";
    String APPSECRET = "410f7ac6fa30bc24b5648321d09bbb06";

    String mUrl;
    //请求队列
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         *1，初始化SMSSDK
         * public static void initSDK(Context context,java.lang.String appkey,java.lang.String appSecrect,boolean warnOnReadContact)
         * @param context:上下文
         * @param appkey：我们在mob.com创建应用时候生成APP Key
         * @param appSecret：我们在mob.com创建应用时候生成APP Secret
         * @param warnOnReadContact: 是否警告在读取联系人
            */
        initSDK();
        initView();
        initData();
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
    private void startRegisterRequest() {
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //传输从android端获取到的数据
        request.add("methods","register");
        request.add("user_phone", mPhoneString);
        request.add("user_password", mRegisterPassword);

        //将请求添加到队列中
        mRequestQueue.add(Register, request, onResponseListener);
    }

    private OnResponseListener<String> onResponseListener=new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if(what == Register) {//如果请求为注册，则执行注册请求
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LogintestActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(RegisterActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(RegisterActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(RegisterActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();

            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(RegisterActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(RegisterActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(RegisterActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(RegisterActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(RegisterActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    //注册页面的注册按钮，用于提交验证码
    public void register(View view) {
        //将输入的数据变成字符串
        mRegisterPassword=mregister_password.getText().toString();
        mPhoneString = mTextPhone.getText().toString();
        mErificationString = mTextErificationCode.getText().toString();
        //show("注册号码" + mPhoneString+".."+mErificationString);
        //输入时进行判断
        if(mPhoneString.equals("")) {
            //判断手机号是否为空
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(mErificationString.equals("")) {
            //判断验证码是否为空
            Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        }else if(mRegisterPassword.equals("")){
            //判断密码是否为空
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            //提交短信验证码，在监听中返回
            SMSSDK.submitVerificationCode("86", mPhoneString ,mErificationString);
            //向数据库中传输用户名和密码，
            startRegisterRequest();
        }
    }

    public void initSDK(){
        SMSSDK.initSDK(RegisterActivity.this,APPKEY,APPSECRET);
        //Looper.prepare();
        EventHandler eh =new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        mHandler.sendEmptyMessage(VerificationSuccess);
                        Log.e("info","提交验证码成功");
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        Log.e("info","获取验证码成功");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    //验证失败
                    mHandler.sendEmptyMessage(VerificationError);
                    ((Throwable)data).printStackTrace();
                }
            }
        };
//        Looper.loop();
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }
    private void initView() {
        mTextPhone = (EditText) findViewById(R.id.register_phone);//手机号
        mTextErificationCode = (EditText) findViewById(R.id.register_erification_code);//验证按
        mregister_password= (EditText) findViewById(R.id.register_password);//密码
        mButton = (Button) findViewById(R.id.verification);//获取验证码
        //mButton = (Button) findViewById(R.id.register);//注册按钮

    }
    //获取验证码
    public void verification(View view) {
        //通过手机号去获取验证码
        mPhoneString = mTextPhone.getText().toString();
        SMSSDK.getVerificationCode("86", mPhoneString,null);//获取验证码
        //获取验证码后，设置不让获取
        show("注册号码" + mPhoneString);
        delay();
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ChangeButtonWHAT:
                    mButton.setClickable(false);
                    mButton.setText(shortTime+"s后重新获取");
                    break;
                case ResetButtonWHAT:
                    mButton.setClickable(true);
                    mButton.setText("重新获取");
//                    mTextErificationCode.setText("");
                    break;
                case VerificationError:
                    show("验证失败，请重新获取验证码！");
                    break;
                case VerificationSuccess:
                    show("验证成功！");
                    break;
            }
        }
    };

    //用于更改获取验证码显示
    public void delay(){
        //每隔60s发送一次
        shortTime = 60;
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while (true){
                        if(shortTime > 0){
                            mHandler.sendEmptyMessage(ChangeButtonWHAT);
                            sleep(1000);
                            shortTime--;
                        }else {
                            mHandler.sendEmptyMessage(ResetButtonWHAT);
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public void show(String text){
        Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_LONG).show();
    }

}
