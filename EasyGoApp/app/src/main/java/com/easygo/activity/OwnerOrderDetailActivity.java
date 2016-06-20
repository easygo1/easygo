package com.easygo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * 房东订单详情
 */
public class OwnerOrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int WHAT_GETORDERINFO = 10;
    public static final int WHAT_UPDATEORDERBOOK = 11;
    public static final int WHAT_DELEORDER = 12;
    TextView mTextView, mFootOrderTextView,mOrderbooknameTextview,mOrderChecknumTextView;
    ListView mOrderListView;
    View mBeforeListView, mAfterListView;
    //复用申请预定界面的ListView适配器
    OrderContactAdpter mAdapter;
    //用来加载头布局
    LayoutInflater mInflater;

    TextView mBookNameTextView, mBookTelTextView;
    TextView mOrderStateTextView, mOrderTimeTextView, mCheckTiemTextView, mCheckLeaveTextView, mOrderTotalTextView, mOrderSumTimeTextView, mHouseTypeTextView, mHouseUserNameTextView;
    ImageView mHousePhotoImageView, mbackImageView;
    LinearLayout mConcelOrderLinearLayout, mUpdateorderBookLinearLayout;
    Button mChatOwnerButton;
    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue(5);//请求队列
    GsonOrderInfoAllDetail mGsonOrderInfoAllDetail;
    Orders mOrders;
    User house_user;//房客信息
    String house_user_nickname, phone;
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
            String result = response.get();
            switch (what) {
                case WHAT_GETORDERINFO:
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
                    housephoto = mGsonOrderInfoAllDetail.getHousephoto();
                    house = mGsonOrderInfoAllDetail.getHouse();
                    Glide.with(OwnerOrderDetailActivity.this)
                            .load(housephoto.getHouse_photo_path())
                            .into(mHousePhotoImageView);
                    mOrderStateTextView.setText(mOrders.getOrder_state());
                    mOrderTimeTextView.setText(mOrders.getOrder_time());
                    mCheckTiemTextView.setText(mOrders.getChecktime());
                    mCheckLeaveTextView.setText(mOrders.getLeavetime());
                    mOrderTotalTextView.setText("" + mOrders.getTotal());
                    mHouseTypeTextView.setText(house.getHouse_style());
                    mOrderSumTimeTextView.setText("共" + mOrders.getChecknum() + "晚");
                    mHouseUserNameTextView.setText(house_user.getUser_realname());
                    mOrderbooknameTextview.setText(mOrders.getBook_name());
                    mOrderChecknumTextView.setText("共"+mOrders.getChecknum()+"晚");
                    mBookNameTextView.setText(mOrders.getBook_name());
                    mBookTelTextView.setText(mOrders.getTel());
                    house_user_nickname = house_user.getUser_nickname();
                    phone = house_user.getUser_phone();
                    /*if (mOrders.getOrder_state().equals("已取消")) {
                        mConcelOrderLinearLayout.setVisibility(View.GONE);
                        mUpdateorderBookLinearLayout.setVisibility(View.GONE);
                    }*/
                    if(mOrders.getOrder_state().equals("待付款")){
                        mFootOrderTextView.setText("已确认");
                    }
                    if(!(mOrders.getOrder_state().equals("待付款")||mOrders.getOrder_state().equals("待确认"))){
                        mConcelOrderLinearLayout.setVisibility(View.GONE);
                        mUpdateorderBookLinearLayout.setVisibility(View.GONE);
                    }
                    break;
                case WHAT_UPDATEORDERBOOK:
                    //房东确认订单
                    Toast.makeText(OwnerOrderDetailActivity.this, "已确认", Toast.LENGTH_SHORT).show();
                    break;

                case WHAT_DELEORDER:
                    Toast.makeText(OwnerOrderDetailActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(OwnerOrderDetailActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(OwnerOrderDetailActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(OwnerOrderDetailActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(OwnerOrderDetailActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(OwnerOrderDetailActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(OwnerOrderDetailActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(OwnerOrderDetailActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(OwnerOrderDetailActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
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
        request.add("methods", "getOwnerorderdetailbyorderid");
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
        mInflater = LayoutInflater.from(OwnerOrderDetailActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.before_owner_order_detail, null);
        //得到ListView之后的布局
        mAfterListView = mInflater.inflate(R.layout.after_owner_order_detail, null);
        //将ListView之前，之后的布局加到前面
        mOrderListView.addHeaderView(mBeforeListView);
        mOrderListView.addFooterView(mAfterListView);
        mBookNameTextView = (TextView) findViewById(R.id.book_name);
        mBookTelTextView = (TextView) findViewById(R.id.book_tel);
        mOrderStateTextView = (TextView) findViewById(R.id.order_state);
        mOrderTimeTextView = (TextView) findViewById(R.id.order_time);
        mHousePhotoImageView = (ImageView) findViewById(R.id.order_imageView);
        mCheckTiemTextView = (TextView) findViewById(R.id.order_checktime);
        mCheckLeaveTextView = (TextView) findViewById(R.id.order_leavetime);
        mOrderTotalTextView = (TextView) findViewById(R.id.order_total);
        mOrderSumTimeTextView = (TextView) findViewById(R.id.order_sumtime);
        mOrderbooknameTextview= (TextView) findViewById(R.id.order_bookname);
        mOrderChecknumTextView= (TextView) findViewById(R.id.order_checknum);
        mHouseTypeTextView = (TextView) findViewById(R.id.order_roomtype);
        mHouseUserNameTextView = (TextView) findViewById(R.id.house_user_name);
        mFootOrderTextView = (TextView) findViewById(R.id.order_uptate_textview);
        mbackImageView = (ImageView) findViewById(R.id.back);
        mAdapter = new OrderContactAdpter(OwnerOrderDetailActivity.this, mUserOrderLinkmanList);
        //添加适配器
        mOrderListView.setAdapter(mAdapter);
        //ListView之前控件的初始化
        //ListView之后的控件
        mUpdateorderBookLinearLayout = (LinearLayout) findViewById(R.id.order_update);
        mConcelOrderLinearLayout = (LinearLayout) findViewById(R.id.order_delte);
        mChatOwnerButton = (Button) findViewById(R.id.chat_owner);
    }

    private void addListeners() {
        mbackImageView.setOnClickListener(this);
        mUpdateorderBookLinearLayout.setOnClickListener(this);
        mConcelOrderLinearLayout.setOnClickListener(this);
        mChatOwnerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.order_update:
                if (mOrderStateTextView.getText().toString().equals("待确认")) {
                    //创建请求对象
                    request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                    //添加请求参数
                    request.add("methods", "yesOrders");
                    request.add("order_id", order_id);
                    mRequestQueue.add(WHAT_UPDATEORDERBOOK, request, mOnResponseListener);
                }
                break;
            case R.id.order_delte:
                //取消订单
                if (mOrderStateTextView.getText().toString().equals("已取消")) {
                    Toast.makeText(OwnerOrderDetailActivity.this, "该订单已经取消", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog();
                }
                break;
            case R.id.chat_owner:
                /**
                 * 启动单聊界面。
                 *
                 * @param context      应用上下文。
                 * @param targetUserId 要与之聊天的用户 Id。
                 * @param title        聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */
                RongIM.getInstance().startPrivateChat(OwnerOrderDetailActivity.this, phone, house_user_nickname);
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("您要取消该订单？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //创建请求对象
                        request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                        //添加请求参数
                        request.add("methods", "delOrders");
                        request.add("order_id", order_id);
                        mRequestQueue.add(WHAT_DELEORDER, request, mOnResponseListener);
                        mOrderStateTextView.setText("已取消");
                        mConcelOrderLinearLayout.setVisibility(View.INVISIBLE);
                        mUpdateorderBookLinearLayout.setVisibility(View.INVISIBLE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }
}
