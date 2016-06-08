package com.easygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.OrderContactAdpter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonOrderInfoAllDetail;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
import com.easygo.beans.order.UserOrderLinkman;
import com.easygo.beans.user.User;
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

    public static final int WHAT_GETORDERINFO = 10;
    public static final int WHAT_UPDATEORDERBOOK = 11;
    TextView mTextView, mOrderRemindTextView, mFootOrderTextView;
    ListView mOrderListView;
    View mBeforeListView, mAfterListView;
    //复用申请预定界面的ListView适配器
    OrderContactAdpter mAdapter;
    //用来加载头布局
    LayoutInflater mInflater;

    EditText mBookNameEditText, mBookTelEditText;
    TextView mOrderStateTextView, mOrderTimeTextView, mCheckTiemTextView, mCheckLeaveTextView, mOrderTotalTextView, mOrderSumTimeTextView, mHouseTypeTextView, mHouseUserNameTextView, mHouseAddressTextView;
    ImageView mHousePhotoImageView, mOrderPhotoImageView,mbackImageView;
    LinearLayout mUpdateorderBookLinearLayout;

    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();//请求队列
    GsonOrderInfoAllDetail mGsonOrderInfoAllDetail;
    Orders mOrders;
    User house_user;//房东信息
    House house;
    HousePhoto housephoto;

    List<UserOrderLinkman> mUserOrderLinkmanList;//订单入住人信息
    int order_id = 0;//从上个页面传过来的id

    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT_GETORDERINFO) {
                String result = response.get();
                //解析对象
                Gson gson = new Gson();
                Type mytype = new TypeToken<GsonOrderInfoAllDetail>() {
                }.getType();
                mGsonOrderInfoAllDetail = gson.fromJson(result, mytype);
//                Log.e("订单解析结果", mGsonOrderInfoAllDetail.toString());
                mUserOrderLinkmanList.addAll(mGsonOrderInfoAllDetail.getUserorderlinkmanlist());
                mAdapter.notifyDataSetChanged();

                mOrders = mGsonOrderInfoAllDetail.getOrders();
                house_user = mGsonOrderInfoAllDetail.getHouse_user();
                housephoto=mGsonOrderInfoAllDetail.getHousephoto();
                house = mGsonOrderInfoAllDetail.getHouse();
                if (mOrders.getOrder_state().equals("已取消")) {
                    mOrderPhotoImageView.setImageResource(R.mipmap.fail_order);
                    mOrderRemindTextView.setText("您已经取消该订单，订单已经失效");
                } else if (mOrders.getOrder_state().equals("待确认")) {
                    mOrderRemindTextView.setText("请您稍等，待房东的确认");
                } else if (mOrders.getOrder_state().equals("待付款")) {
                    mOrderRemindTextView.setText("您的订单未完成，请尽快完成支付");
                } else if (mOrders.getOrder_state().equals("待入住")) {
                    mOrderRemindTextView.setText("订单已完成，等待您的入住");
                } else if (mOrders.getOrder_state().equals("已完成")) {
                    mOrderRemindTextView.setText("欢迎您的下次入住");
                    mFootOrderTextView.setText("去评价");
                }
                mOrderStateTextView.setText(mOrders.getOrder_state());
                mOrderTimeTextView.setText(mOrders.getOrder_time());
                mCheckTiemTextView.setText(mOrders.getChecktime());
                mCheckLeaveTextView.setText(mOrders.getLeavetime());
                // mOrderTotalTextView.setText(mOrders.get);
                mHouseTypeTextView.setText(house.getHouse_style());
                mOrderSumTimeTextView.setText("" + mOrders.getTotal());
                mHouseUserNameTextView.setText(house_user.getUser_realname());
                mHouseAddressTextView.setText(house.getHouse_address_province() + house.getHouse_address_city());
                mBookNameEditText.setText(mOrders.getBook_name());
                mBookTelEditText.setText(mOrders.getTel());

            } else if (what == WHAT_UPDATEORDERBOOK) {
                String result = response.get();
                Toast.makeText(OrderDetailActivity.this, result, Toast.LENGTH_SHORT).show();
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
        Intent intent=getIntent();
        order_id=intent.getIntExtra("order_id",0);
        //从上个页面获取的
        myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        initListView();
        initData();
        initView();
        addListeners();
        sendRequest();
    }

    private void sendRequest() {
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
        mUserOrderLinkmanList = new ArrayList<>();
    }

    private void initView() {
        //得到ListView之前的布局
        mInflater = LayoutInflater.from(OrderDetailActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.order_detail_before_listview, null);
        //得到ListView之后的布局
        mAfterListView = mInflater.inflate(R.layout.order_detail_after_listview, null);
        //将ListView之前，之后的布局加到前面
        mOrderListView.addHeaderView(mBeforeListView);
        mOrderListView.addFooterView(mAfterListView);
        mOrderRemindTextView = (TextView) findViewById(R.id.order_remind);
        mOrderPhotoImageView = (ImageView) findViewById(R.id.order_photo);
        mBookNameEditText = (EditText) findViewById(R.id.book_name);
        mBookTelEditText = (EditText) findViewById(R.id.book_tel);
        mOrderStateTextView = (TextView) findViewById(R.id.order_state);
        mOrderTimeTextView = (TextView) findViewById(R.id.order_time);
        mHousePhotoImageView = (ImageView) findViewById(R.id.order_imageView);
        mCheckTiemTextView = (TextView) findViewById(R.id.order_checktime);
        mCheckLeaveTextView = (TextView) findViewById(R.id.order_leavetime);
        mOrderTotalTextView = (TextView) findViewById(R.id.order_total);
        mOrderSumTimeTextView = (TextView) findViewById(R.id.order_sumtime);
        mHouseTypeTextView = (TextView) findViewById(R.id.order_roomtype);
        mHouseUserNameTextView = (TextView) findViewById(R.id.house_user_name);
        mHouseAddressTextView = (TextView) findViewById(R.id.house_address);
        mFootOrderTextView = (TextView) findViewById(R.id.order_uptate_textview);
        mbackImageView= (ImageView) findViewById(R.id.back);
        mAdapter = new OrderContactAdpter(OrderDetailActivity.this, mUserOrderLinkmanList);
        //添加适配器
        mOrderListView.setAdapter(mAdapter);
        //ListView之前控件的初始化
        //ListView之后的控件
        mUpdateorderBookLinearLayout = (LinearLayout) findViewById(R.id.order_update);
    }

    private void addListeners() {
        mbackImageView.setOnClickListener(this);
        mUpdateorderBookLinearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.order_update:
                if (mOrderStateTextView.getText().equals("待确认")) {
                    //创建请求对象
                    request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                    //添加请求参数
                    request.add("methods", "updateorderbook");
                    request.add("order_id", order_id);
                    request.add("book_name", mBookNameEditText.getText().toString());
                    request.add("book_tel", mBookTelEditText.getText().toString());
                    mRequestQueue.add(WHAT_UPDATEORDERBOOK, request, mOnResponseListener);
                }
                if (mOrderStateTextView.getText().equals("已完成")) {
                    Intent intent=new Intent();
                    intent.putExtra("house_photo",housephoto.getHouse_photo_path().toString());
                    intent.putExtra("order_id",order_id);
                    intent.putExtra("house_id",housephoto.getHouse_id());
                    intent.setClass(OrderDetailActivity.this,OrderAssessActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
