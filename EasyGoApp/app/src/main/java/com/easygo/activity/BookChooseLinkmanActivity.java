package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.BookChooseLinkmanAdapter;
import com.easygo.application.MyApplication;
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

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
* 申请预订时选择的常用入住人列表
* */
public class BookChooseLinkmanActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    //自定义一个dialog
    private WaitDialog mDialog;
    public static final int FIND_LINKMAN_WHAT = 1;
    public static final int ADD_LINKMAN_REQUEST_CODE = 1;
    public static final int CHOOSE_LINKMAN_RESULT_CODE = 2;

    List<UserLinkman> mList = null;
    ListView mChooseLinkManListView;
    BookChooseLinkmanAdapter mAdapter;
    TextView mTitleTextView, mAddTextView,mCommitTextView;
    //网络请求
    String mPath;
    RequestQueue mRequestQueue;
    int user_id;
    UserLinkman mUserLinkman;//用于接收添加入住人后传过来的对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_book_choose_linkman);
        initView();
        initData();
        loadData();
        addListeners();
    }

    private void initView() {
        mDialog = new WaitDialog(this);//提示框
        mChooseLinkManListView = (ListView) findViewById(R.id.room_book_choose_list);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mAddTextView = (TextView) findViewById(R.id.room_book_choose_add);
        //选择以上入住人（mCommitTextView）
        mCommitTextView = (TextView) findViewById(R.id.room_book_choose_commit);
        mTitleTextView.setText("选择入住人");
    }

    private void initData() {
        mSharedPreferences = getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        //type = mSharedPreferences.getInt("type", 0);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        //初始化List
        mList = new ArrayList<>();
        //模拟数据
       /* for (int i = 0; i < 10; i++) {
            mList.add(new UserLinkman(i, i, "小明" + i, "身份证：350500199508105515"));
        }*/
        mAdapter = new BookChooseLinkmanAdapter(BookChooseLinkmanActivity.this, mList);
        mChooseLinkManListView.setAdapter(mAdapter);
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
        mRequestQueue.add(FIND_LINKMAN_WHAT, request, onResponseListener);
        mAdapter.notifyDataSetChanged();
    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case FIND_LINKMAN_WHAT:
                    String result = response.get();// 响应结果
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<UserLinkman>>() {
                    }.getType();
                    List<UserLinkman> linkmanList = gson.fromJson(result, type);
                    mList.addAll(linkmanList);
                    mAdapter.notifyDataSetChanged();
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
            Toast.makeText(BookChooseLinkmanActivity.this, "网络请求失败了", Toast.LENGTH_SHORT).show();
        }
    };

    private void addListeners() {
        mAddTextView.setOnClickListener(this);
        mCommitTextView.setOnClickListener(this);
        //listview的监听
        mChooseLinkManListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BookChooseLinkmanActivity.this, "小明" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                Bundle mBundle = data.getExtras();
                mUserLinkman = (UserLinkman) mBundle.getSerializable("linkman");
                mList.add(mUserLinkman);
//                Log.e("mime",mUserLinkman.getLinkman_name()+"..."+mUserLinkman.getUser_linkman_id());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room_book_choose_add:
                //添加一个入住人
                Intent mIntent = new Intent(BookChooseLinkmanActivity.this, UserLinkmanAddActivity.class);
                startActivityForResult(mIntent, ADD_LINKMAN_REQUEST_CODE);
                break;
            case R.id.room_book_choose_commit:
                List<UserLinkman> commitList = new ArrayList<>();
                for (UserLinkman ulm : mList) {
                    if (ulm.isChecked()){
                        //如果被选中就添加到List中
                        commitList.add(ulm);
                    }
                }
                Intent mIntent2 = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("chooseLinkman", (Serializable) commitList);
                mIntent2.putExtras(mBundle);
                setResult(CHOOSE_LINKMAN_RESULT_CODE,mIntent2);
                finish();
                break;
        }
    }
}
