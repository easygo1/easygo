package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class RealNameIdentifyActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int WHAT_REALNAME = 16;
    public static final int WHAT_SAVEIDCARD = 17;
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    TextView mTextView;
    ImageView mBackImageView;
    EditText mRealNameEditText, mIdcardEditText;
    String realName, idcard;
    Button mIdentifyButton;
    int user_id;
    //实名认证接口
    String myUrl = "http://api.avatardata.cn/IdCardCertificate/Verify";
    String mykey = "cbf5684b9867429cac9741b1e49e8bc7";
    //项目的接口path
    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();//请求队列

    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_REALNAME:
                    try {
                        //JSONStringer jsonStringer=new JSONStringer(response.get().toString());
                        JSONObject jsonobject = new JSONObject(response.get());
                        String result = jsonobject.getString("reason");
                        Toast.makeText(RealNameIdentifyActivity.this, result, Toast.LENGTH_SHORT).show();
                        if (result.equals("Succes")) {
                            //说明认证成功，就插入到数据库中
                            // /创建请求对象
                            request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                            //添加请求参数
                            request.add("methods", "updateRealName");//应用appkey
                            request.add("user_id", user_id);
                            request.add("realname", realName);//真实姓名
                            request.add("idcard", idcard);//身份证号
                            //request.add("format", true);//是否对其进行格式化，默认false,节省流量
                            Log.e("真实姓名", realName);
                            Log.e("身份证号", idcard);
                            mRequestQueue.add(WHAT_SAVEIDCARD, request, mOnResponseListener);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case WHAT_SAVEIDCARD:
                    String result1 = response.get();
                    //将数据存储在数据库中
                    Toast.makeText(RealNameIdentifyActivity.this, "result" + result1, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("flag", "me");
                    intent.setClass(RealNameIdentifyActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        }

        @Override
        public void onFinish(int what) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_identify);
        myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        mSharedPreferences = this.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        initview();
        addlistener();
    }

    private void initview() {
        mTextView = (TextView) findViewById(R.id.title_text);
        mTextView.setText("实名认证");
        mBackImageView = (ImageView) findViewById(R.id.back);
        mRealNameEditText = (EditText) findViewById(R.id.real_name_identify);
        mIdcardEditText = (EditText) findViewById(R.id.idcard_identify);
        mIdentifyButton = (Button) findViewById(R.id.identify_btn);
    }

    private void addlistener() {
        mBackImageView.setOnClickListener(this);
        mIdentifyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.identify_btn:
                realName = mRealNameEditText.getText().toString();
                idcard = mIdcardEditText.getText().toString();
                if ((!realName.equals("")) && (!idcard.equals(""))) {
                    // /创建请求对象
                    request = NoHttp.createStringRequest(myUrl, RequestMethod.GET);
                    //添加请求参数
                    request.add("key", mykey);//应用appkey
                    request.add("realname", realName);//真实姓名
                    request.add("idcard", idcard);//身份证号
                    request.add("format", true);//是否对其进行格式化，默认false,节省流量
                    Log.e("真实姓名", realName);
                    Log.e("身份证号", idcard);
                    mRequestQueue.add(WHAT_REALNAME, request, mOnResponseListener);
                } else {
                    Toast.makeText(RealNameIdentifyActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
