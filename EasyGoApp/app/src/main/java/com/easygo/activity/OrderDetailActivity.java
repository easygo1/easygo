package com.easygo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easygo.adapter.BookContactAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonOrderInfoAllDetail;
import com.easygo.beans.user.UserLinkman;
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

/*订单详情页面*/
public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int WHAT_GETORDERINFO = 1;
    TextView mTextView;
    ListView mOrderListView;
    View mBeforeListView, mAfterListView;
    //复用申请预定界面的ListView适配器
    BookContactAdapter mAdapter;
    List<UserLinkman> mList = null;
    //用来加载头布局
    LayoutInflater mInflater;

    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();//请求队列
    GsonOrderInfoAllDetail mGsonOrderInfoAllDetail;
    int order_id = 3;//从上个页面传过来的id

    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT_GETORDERINFO) {
                String result = response.get();
                //Toast.makeText(getActivity(), "得到用户头像了" + result, Toast.LENGTH_SHORT).show();
                //解析对象
                Gson gson = new Gson();
                Type mytype = new TypeToken<GsonOrderInfoAllDetail>() {
                }.getType();
                mGsonOrderInfoAllDetail = gson.fromJson(result, mytype);
                Log.e("订单解析结果",mGsonOrderInfoAllDetail.toString());
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
        setContentView(R.layout.order_detail);
        sendRequest();
        initListView();
        initData();
        initView();
        addListeners();
    }

    private void sendRequest() {
        myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        //创建请求对象
        request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
        //添加请求参数
        request.add("methods", "getorderdetailbyorderid");
        request.add("order_id", order_id);
        mRequestQueue.add(WHAT_GETORDERINFO, request, mOnResponseListener);
    }


    private void initListView() {
        mTextView = (TextView) findViewById(R.id.title_text);
        mTextView.setText("订单详情");
        mOrderListView = (ListView) findViewById(R.id.order_detail_listview);
    }

    private void initData() {
        //初始化List
        mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < 5; i++) {
            mList.add(new UserLinkman(i, i, "小明" + i, "350500199508105515"));
        }
    }

    private void initView() {
        //得到ListView之前的布局
        mInflater = LayoutInflater.from(OrderDetailActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.order_detail_before_listview, null);
        //得到ListView之后的布局
        mAfterListView = mInflater.inflate(R.layout.order_detail_after_listview, null);

        mAdapter = new BookContactAdapter(OrderDetailActivity.this, mList);
        //将ListView之前，之后的布局加到前面
        mOrderListView.addHeaderView(mBeforeListView);
        mOrderListView.addFooterView(mAfterListView);
        //添加适配器
        mOrderListView.setAdapter(mAdapter);

        //ListView之前控件的初始化
        //ListView之后的控件

    }

    private void addListeners() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
