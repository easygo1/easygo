package com.easygo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/*订单详情页面*/
public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int WHAT_GETORDERINFO = 10;
    public static final int WHAT_UPDATEORDERBOOK = 11;
    public static final int WHAT_DELEORDER = 12;
    public static final int WHAT_ISASSESSORDERS = 15;
    public static final int WHAT_OKGO=14;
    TextView mTextView, mOrderRemindTextView, mFootOrderTextView;
    ListView mOrderListView;
    View mBeforeListView, mAfterListView;
    //复用申请预定界面的ListView适配器
    OrderContactAdpter mAdapter;
    //用来加载头布局
    LayoutInflater mInflater;

    EditText mBookNameEditText, mBookTelEditText;
    TextView mOrderStateTextView, mOrderTimeTextView, mCheckTiemTextView, mCheckLeaveTextView, mOrderTotalTextView, mOrderSumTimeTextView, mHouseTypeTextView, mHouseUserNameTextView, mHouseAddressTextView;
    ImageView mHousePhotoImageView, mOrderPhotoImageView, mbackImageView;
    LinearLayout mConcelOrderLinearLayout, mUpdateorderBookLinearLayout;
    Button mChatOwnerButton;

    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue(5);//请求队列
    GsonOrderInfoAllDetail mGsonOrderInfoAllDetail;
    Orders mOrders;
    User house_user;//房东信息
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
                    if (mOrders.getOrder_state().equals("已取消")) {
                        mOrderPhotoImageView.setImageResource(R.mipmap.fail_order);
                        mOrderRemindTextView.setText("您已经取消该订单，订单已经失效");
                        mConcelOrderLinearLayout.setVisibility(View.GONE);
                        mUpdateorderBookLinearLayout.setVisibility(View.GONE);
                    } else if (mOrders.getOrder_state().equals("待确认")) {
                        mOrderRemindTextView.setText("请您稍等，待房东的确认");
                    } else if (mOrders.getOrder_state().equals("待付款")) {
                        mOrderRemindTextView.setText("您的订单未完成，请尽快完成支付");
                        mFootOrderTextView.setText("去付款");
                    } else if (mOrders.getOrder_state().equals("待入住")) {
                        mOrderRemindTextView.setText("订单已完成，等待您的入住");
                        mConcelOrderLinearLayout.setVisibility(View.INVISIBLE);
//                        mUpdateorderBookLinearLayout.setVisibility(View.GONE);
                        mFootOrderTextView.setText("确认入住");
                    } else if (mOrders.getOrder_state().equals("已完成")) {
                        mOrderRemindTextView.setText("欢迎您的下次入住");
                        mFootOrderTextView.setText("去评价");
                        mConcelOrderLinearLayout.setVisibility(View.INVISIBLE);
                    }
                    if(!mOrders.getOrder_state().equals("待确认")){
                        mBookNameEditText.setEnabled(false);
                        mBookTelEditText.setEnabled(false);
                    }
                    Glide.with(OrderDetailActivity.this)
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
                    mHouseAddressTextView.setText(house.getHouse_address_province() + house.getHouse_address_city());
                    mBookNameEditText.setText(mOrders.getBook_name());
                    mBookTelEditText.setText(mOrders.getTel());
                    house_user_nickname = house_user.getUser_nickname();
                    phone = house_user.getUser_phone();
                    break;
                case WHAT_UPDATEORDERBOOK:
                    Toast.makeText(OrderDetailActivity.this, "已修改", Toast.LENGTH_SHORT).show();
                    break;
                case WHAT_DELEORDER:
                    Toast.makeText(OrderDetailActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    break;
                case WHAT_ISASSESSORDERS:

                    if (result.equals("已经评价")) {
                        mFootOrderTextView.setText("已评价");
                        Toast.makeText(OrderDetailActivity.this, "该订单已经评价过了", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("未评价")) {
                        Log.e("result", result);
                        Intent intent = new Intent();
                        intent.putExtra("house_photo", housephoto.getHouse_photo_path().toString());
                        intent.putExtra("order_id", order_id);
                        intent.putExtra("house_id", housephoto.getHouse_id());
                        intent.setClass(OrderDetailActivity.this, OrderAssessActivity.class);
                        startActivity(intent);
                    }

                    break;
                case WHAT_OKGO:
                    Toast.makeText(OrderDetailActivity.this,"已确认", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(OrderDetailActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(OrderDetailActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(OrderDetailActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(OrderDetailActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(OrderDetailActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(OrderDetailActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(OrderDetailActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(OrderDetailActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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
        mbackImageView = (ImageView) findViewById(R.id.back);
        mAdapter = new OrderContactAdpter(OrderDetailActivity.this, mUserOrderLinkmanList);
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
                    request.add("methods", "updateorderbook");
                    request.add("order_id", order_id);
                    request.add("book_name", mBookNameEditText.getText().toString());
                    request.add("book_tel", mBookTelEditText.getText().toString());
                    mRequestQueue.add(WHAT_UPDATEORDERBOOK, request, mOnResponseListener);
                }
                if (mOrderStateTextView.getText().toString().equals("已完成")) {
                    //创建请求对象
                    request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                    //添加请求参数
                    request.add("methods", "assessokOrders");
                    request.add("order_id", order_id);
                    mRequestQueue.add(WHAT_ISASSESSORDERS, request, mOnResponseListener);
                }
                if (mOrderStateTextView.getText().toString().equals("待付款")) {
                    Intent intent = new Intent();
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("describe", house.getHouse_describe() + "-" + house.getHouse_style());
                    intent.putExtra("price", mOrders.getTotal());
                    intent.putExtra("house_id",mOrders.getHouse_id());
                    intent.putExtra("checktime",mOrders.getChecktime());
                    intent.putExtra("leavetime",mOrders.getLeavetime());
                    Log.e("orderdetail","过去之前就出来了");
                    Log.e("orderdetail",mOrders.getChecktime()+"...."+mOrders.getLeavetime()+"orderderail");
                    intent.setClass(OrderDetailActivity.this, PayActivity.class);
                    startActivity(intent);
                }
                if (mOrderStateTextView.getText().toString().equals("待入住")) {
                    showOKDialog();
                }
                break;
            case R.id.order_delte:
                //取消订单
                if (mOrderStateTextView.getText().toString().equals("已取消")) {
                    Toast.makeText(OrderDetailActivity.this, "该订单已经取消", Toast.LENGTH_SHORT).show();
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
                RongIM.getInstance().startPrivateChat(OrderDetailActivity.this, phone, house_user_nickname);
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
                        mOrderPhotoImageView.setImageResource(R.mipmap.fail_order);
                        mOrderRemindTextView.setText("您已经取消该订单，订单已经失效");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }
    private void showOKDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("您要确认该房源已入住？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //创建请求对象
                        request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                        //添加请求参数
                        request.add("methods", "yesStayOrders");
                        request.add("order_id", order_id);
                        mRequestQueue.add(WHAT_OKGO, request, mOnResponseListener);
                        mConcelOrderLinearLayout.setVisibility(View.INVISIBLE);
                        //mUpdateorderBookLinearLayout.setVisibility(View.GONE);
                        mOrderStateTextView.setText("已完成");
                        mFootOrderTextView.setText("去评价");
                        mOrderRemindTextView.setText("欢迎您的下次入住");
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
