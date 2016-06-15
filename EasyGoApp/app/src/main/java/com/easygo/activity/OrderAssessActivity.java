package com.easygo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class OrderAssessActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TYPE = "type";
    public static final int WHAT_ORDERASSESSCOMMIT = 1;
    public static final int STRA_DEFULT = 0;
    TextView mTextView, mAssessCommitTextView;
    ImageView mImageView, mHouseFirstPhotoImageView;
    RatingBar mOrderAssessRatingBar;
    EditText mAssessContentEditText;

    int order_id, house_id, user_id, star;
    String house_photo;
    String assess_content;//评价的内容

    SharedPreferences mSharedPreferences;

    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();//请求队列
    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT_ORDERASSESSCOMMIT) {
                String result = response.get();
                finish();
                //Toast.makeText(OrderAssessActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(OrderAssessActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(OrderAssessActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(OrderAssessActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(OrderAssessActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(OrderAssessActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(OrderAssessActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(OrderAssessActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(OrderAssessActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_assess);
        initview();
        getData();
        initListener();
    }

    private void getData() {
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", order_id);//从上个页面传过来的
        house_id = intent.getIntExtra("house_id",house_id);
        house_photo = intent.getStringExtra("house_photo");
         Log.e("path",house_photo);
        Glide.with(this)
                .load(house_photo)
                .into(mHouseFirstPhotoImageView);
        mSharedPreferences = this.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
    }

    private void initview() {
        mTextView = (TextView) findViewById(R.id.title_text);
        mTextView.setText("订单评价");
        mImageView = (ImageView) findViewById(R.id.back);
        mAssessCommitTextView = (TextView) findViewById(R.id.assess_commit);
        mOrderAssessRatingBar = (RatingBar) findViewById(R.id.order_assess_star_detail);
        mAssessContentEditText = (EditText) findViewById(R.id.order_assess_content);
        mHouseFirstPhotoImageView = (ImageView) findViewById(R.id.order_house_assess_image);

    }

    private void initListener() {
        mImageView.setOnClickListener(this);
        mAssessCommitTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                star = (int) mOrderAssessRatingBar.getRating();
                assess_content = mAssessContentEditText.getText().toString();
                //判断是否保存了，然后再操作
                if (star != STRA_DEFULT || mAssessContentEditText.length() != STRA_DEFULT) {
                    commitshowDialog();
                } else {
                    finish();
                }
                break;
            case R.id.assess_commit:
                star = (int) mOrderAssessRatingBar.getRating();
                assess_content = mAssessContentEditText.getText().toString();
                Log.e("info", mOrderAssessRatingBar.getRating() + ":" + mAssessContentEditText.getText().toString());
                //用户点击了保存 判断是否填写完整
                if (star != STRA_DEFULT && mAssessContentEditText.length() != STRA_DEFULT) {
                    Toast.makeText(OrderAssessActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("order_id",order_id);
                    intent.setClass(OrderAssessActivity.this,OrderDetailActivity.class);
                    startActivity(intent);
                    myApplication = (MyApplication) this.getApplication();
                    mUrl = myApplication.getUrl();
                    //创建请求对象
                    request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                    //添加请求参数
                    request.add("methods", "addAssess");
                    request.add("order_id", order_id);
                    request.add("house_id", house_id);
                    request.add("user_id", user_id);
                    request.add("star", star);
                    request.add("assess_content", assess_content);
                    mRequestQueue.add(
                            WHAT_ORDERASSESSCOMMIT, request, mOnResponseListener);
                } else {
                    Toast.makeText(OrderAssessActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void commitshowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("您尚未提交" + "\n" + "是否对已编辑的内容进行保存?")
                .setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
    }


}

