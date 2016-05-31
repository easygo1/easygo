package com.easygo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.view.WaitDialog;
import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

public class LogintestActivity extends AppCompatActivity {

    private static final int NOHTTP_WHAT_TEST = 0x001;
    String mPath;
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    /**
     * 请求的时候等待框.
     */
    private WaitDialog mWaitDialog;
    /**
     * 请求队列.
     */
    private RequestQueue requestQueue;
    EditText muser_phone,muser_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logintest);
        initView();
        initData();
        mWaitDialog = new WaitDialog(this);
        requestQueue = NoHttp.newRequestQueue();

    }

    private OnResponseListener<String> onResponseListener=new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
            mWaitDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            // 创建请求对象
            Request<String> request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
            request.add("user_phone", (Binary) muser_phone.getText());
            request.add("user_password", (Binary) muser_password.getText());
            requestQueue.add(NOHTTP_WHAT_TEST, request, onResponseListener);
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(LogintestActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish(int what) {
            // 请求结束，这里关闭dialog
            mWaitDialog.dismiss();
        }
    };

    private void initView() {
        muser_phone= (EditText) findViewById(R.id.user_phone);
        muser_password= (EditText) findViewById(R.id.user_password);

    }

    private void initData() {
        MyApplication myApplication=new MyApplication();
        mPath=myApplication.getUrl();
    }

    public void login(View view) {
        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
        mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("type", 1);
        //提交保存结果
        mEditor.commit();
        Intent intent = new Intent();
        intent.putExtra("flag","me");
        intent.setClass(LogintestActivity.this,MainActivity.class);
        startActivity(intent);

    }

    //登录页面的注册，跳转到注册页面
    public void reg(View view) {
        Intent intent=new Intent(LogintestActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
