package com.easygo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.view.WaitDialog;
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

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TYPE = "type";

    String mUrl;
    //请求对象
    public static final int AddFrind=7;
    //自定义一个dialog
    private WaitDialog mDialog;
    //请求队列.
    private RequestQueue mRequestQueue;

    private EditText mSelectFriendEditText;
    private Button mSelectFriendButton;
    /*private ListView mUserListView;
    List<String> user_id_list;*/

    //设置偏好设置
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
        initData();
        addListener();
    }

    private void addListener() {
        mSelectFriendButton.setOnClickListener(this);
    }

    private void initData() {
        MyApplication myApplication=new MyApplication();
        mUrl=myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
    }

    //初始化控件
    private void initView() {
        mSelectFriendEditText= (EditText) findViewById(R.id.select_friend);
        mSelectFriendButton= (Button) findViewById(R.id.select_btn);
        //mUserListView= (ListView) findViewById(R.id.user_list);
        mDialog = new WaitDialog(this);
    }
    private OnResponseListener<String> onResponseListener=new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if(what==AddFrind){
                //进行添加好友
                Toast.makeText(AddFriendActivity.this, "好友添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(AddFriendActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(AddFriendActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(AddFriendActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(AddFriendActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(AddFriendActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(AddFriendActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(AddFriendActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(AddFriendActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }
    };

    @Override
    public void onClick(View v) {
        //根据输入框中写入的数据，进行查找
        int id=v.getId();
        switch (id){
            case R.id.select_btn:
                //此按钮先进行添加好友操作
                addfriend();
                break;
        }
    }
    //添加好友
    private void addfriend() {
        //设置偏好设置获取token
        mSharedPreferences =this.getSharedPreferences(TYPE,MODE_PRIVATE);
        //默认值为null
        String phone=mSharedPreferences.getString("phone",null);
        Log.e("phone",phone);
        String user_id2=mSelectFriendEditText.getText().toString();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //传输从android端获取到的数据
        request.add("methods", "addFriend");
        request.add("phone1",phone);
        request.add("phone2",user_id2);
        //将请求添加到队列中
        mRequestQueue.add(AddFrind, request, onResponseListener);
    }

    public void back(View view) {
        finish();
    }
}
