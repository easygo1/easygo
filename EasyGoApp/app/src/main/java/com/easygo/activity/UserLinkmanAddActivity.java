package com.easygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.UserLinkmanAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonAboutHouseDetail;
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
public class UserLinkmanAddActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int USERLINKMAN_ADD_WHAT = 1;
    //自定义一个dialog
    private WaitDialog mDialog;

    List<UserLinkman> mList = null;
    UserLinkmanAdapter mAdapter;
    TextView mTitleTextView, mCommitTextView;
    EditText mNameEditText, mIdEditText;
    String mPath, mRealName, mIdNumber;
    //网络请求
    RequestQueue mRequestQueue;
    int user_id = 1;//偏好设置中取出
    UserLinkman mUserLinkman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_linkman);
        initView();
        addListeners();
//        uploadData();
    }


    private void initView() {
        mDialog = new WaitDialog(this);//提示框
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mNameEditText = (EditText) findViewById(R.id.me_user_linkman_name);
        mIdEditText = (EditText) findViewById(R.id.me_user_linkman_number);
        mCommitTextView = (TextView) findViewById(R.id.me_user_linkman_commit);
        mTitleTextView.setText("新增入住人");
    }

    private void addListeners() {
        mNameEditText.setOnClickListener(this);
        mIdEditText.setOnClickListener(this);
        mCommitTextView.setOnClickListener(this);

    }

    private void uploadData() {
        mRealName = mNameEditText.getText().toString();
        mIdNumber = mIdEditText.getText().toString();
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        //添加请求参数
        request.add("methods", "addLinkman");
        request.add("userid", user_id);
        request.add("linkmanname", mRealName);
        request.add("idcard", mIdNumber);
        mRequestQueue.add(USERLINKMAN_ADD_WHAT, request, onResponseListener);
    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case USERLINKMAN_ADD_WHAT:
                    //添加一个入住人成功，成功后，回到跳转过来的页面，并把当前的入住人返回
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    mUserLinkman = new UserLinkman();
                    mUserLinkman.setUser_id(user_id);
                    mUserLinkman.setLinkman_name(mRealName);
                    mUserLinkman.setIdcard(mIdNumber);
                    bundle.putSerializable("linkman", mUserLinkman);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
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
            Toast.makeText(UserLinkmanAddActivity.this, "网络请求失败了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_user_linkman_commit:
                //点击确定后，提交到服务器
                uploadData();
//                Toast.makeText(UserLinkmanAddActivity.this, mIdNumber + ".." + mRealName, Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
