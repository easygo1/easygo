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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends AppCompatActivity {
    public static final int ChangeButtonWHAT = 1;
    public static final int ResetButtonWHAT = 2;
    public static final int VerificationError = 3;
    public static final int VerificationSuccess = 4;
    //手机号和验证码的Edittext
    EditText mTextPhone,mTextErificationCode;
    //手机号和验证码字符串
    String mPhoneString,mErificationString;
    //获取验证码按钮
    Button mButton;
    //相隔几秒获取验证码
    int shortTime = 0;

    String APPKEY = "133cbdb82f328";
    String APPSECRET = "410f7ac6fa30bc24b5648321d09bbb06";
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
    }



    public void initSDK(){
        SMSSDK.initSDK(RegisterActivity.this,APPKEY,APPSECRET);
//        Looper.prepare();
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
        mTextPhone = (EditText) findViewById(R.id.register_phone);
        mTextErificationCode = (EditText) findViewById(R.id.register_erification_code);
        mButton = (Button) findViewById(R.id.verification);
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
    //注册页面的注册按钮，用于提交验证码
    public void register(View view) {
        mPhoneString = mTextPhone.getText().toString();
        mErificationString = mTextErificationCode.getText().toString();
        //提交短信验证码，在监听中返回
        SMSSDK.submitVerificationCode("86", mPhoneString ,mErificationString);

        show("注册号码" + mPhoneString+".."+mErificationString);
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
