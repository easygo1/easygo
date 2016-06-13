package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.UserLinkmanAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonAboutHouse;
import com.easygo.beans.user.UserLinkman;
import com.easygo.view.WaitDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
* 用户个人中心的常用入住人
* */
public class UserLinkmanActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TYPE = "type";
    public static final int DELETE_LINKMAN_WHAT = 2;
    public static final int UPDATE_LINKMAN_RESULT_CODE = 2;
    //自定义一个dialog
    private WaitDialog mDialog;
    public static final int LOAD_LINKMAN_WHAT = 1;
    List<UserLinkman> mList = null;
    ListView mLinkManListView;
    UserLinkmanAdapter mAdapter;
    ImageView mBackImageView;
    TextView mTitleTextView, mAddLinkmanTextView;
    //网络请求
    RequestQueue mRequestQueue;
    String mPath;
    int user_id;//偏好设置中取出
    SharedPreferences mSharedPreferences;
    UserLinkman mUserLinkman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_linkman);
        initView();
        initData();
        loadData();
        addListeners();
    }

    private void initView() {
        mDialog = new WaitDialog(this);//提示框
        mBackImageView = (ImageView) findViewById(R.id.back);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mLinkManListView = (ListView) findViewById(R.id.me_user_linkman_listview);
        mAddLinkmanTextView = (TextView) findViewById(R.id.me_user_linkman_add);
        mTitleTextView.setText("常用入住人");
    }


    private void initData() {
        mSharedPreferences = getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        //type = mSharedPreferences.getInt("type", 0);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        //初始化List
        mList = new ArrayList<>();
        mAdapter = new UserLinkmanAdapter(UserLinkmanActivity.this, mList);
        mLinkManListView.setAdapter(mAdapter);
    }

    private void loadData() {
        //模拟数据
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        //添加请求参数
        request.add("methods", "getLinkmanByUserId");
        request.add("userid", user_id);
        mRequestQueue.add(LOAD_LINKMAN_WHAT, request, onResponseListener);

    }

    private void addListeners() {
        mBackImageView.setOnClickListener(this);
        mAddLinkmanTextView.setOnClickListener(this);
        //listview的监听
        mLinkManListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(UserLinkmanActivity.this, "小明" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserLinkmanActivity.this, UserLinkmanUpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("linkman", mList.get(position));
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                setResult(UPDATE_LINKMAN_RESULT_CODE, intent);
                startActivityForResult(intent, 2);
            }
        });
    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case LOAD_LINKMAN_WHAT:
                    String result = response.get();// 响应结果
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<UserLinkman>>() {
                    }.getType();
                    List<UserLinkman> linkmanList = gson.fromJson(result, type);
                    mList.addAll(linkmanList);
                    mAdapter.notifyDataSetChanged();
                    break;
//                case DELETE_LINKMAN_WHAT:
//                    break;
            }

        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
            mDialog.show();
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(UserLinkmanActivity.this, "网络请求失败了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //requestCode判断传过去的是哪个Intent
        switch (resultCode) {
            //得到返回值
            case RESULT_OK:
                Bundle mBundle = data.getExtras();
                mUserLinkman = (UserLinkman) mBundle.getSerializable("linkman");
                mList.add(mUserLinkman);
//                Log.e("mime",mUserLinkman.getLinkman_name()+"..."+mUserLinkman.getIdcard());
                mAdapter.notifyDataSetChanged();
                break;
            case UPDATE_LINKMAN_RESULT_CODE:
                Bundle mBundle2 = data.getExtras();
                mUserLinkman = (UserLinkman) mBundle2.getSerializable("linkman");
                int position = mBundle2.getInt("position");
                mList.remove(position);
                mList.add(position, mUserLinkman);
//                Log.e("mime",mUserLinkman.getLinkman_name()+"..."+mUserLinkman.getIdcard());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_user_linkman_add:
//                Toast.makeText(UserLinkmanActivity.this, "添加", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserLinkmanActivity.this, UserLinkmanAddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void deleteLinkman(int user_linkman_id, int position) {
        //模拟数据
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        Request<String> request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        //添加请求参数
        request.add("methods", "deleteLinkman");
        request.add("userLinkmanId", user_linkman_id);
        mRequestQueue.add(DELETE_LINKMAN_WHAT, request, onResponseListener);
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

}
