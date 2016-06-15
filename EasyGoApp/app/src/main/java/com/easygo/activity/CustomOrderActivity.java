package com.easygo.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.adapter.CustomOrderAdapter;
import com.easygo.adapter.OrderFragmentAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonOrderInfo;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
import com.easygo.fragment.CustomOrderHistoryFragment;
import com.easygo.fragment.CustomOrderIngFragment;
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

public class CustomOrderActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    OrderFragmentAdapter mOrderFragmentAdapter;
    FragmentManager mFragmentManager;
    private ArrayList fragments;
    private TextView orderCustomIngtextView;
    private TextView orderCustomHistorytextView;
    private TextView orderCustomIngbottom;
    private TextView orderCustomHistorybottom;
    private ImageView customorderReturn;
    public static final int NOHTTP_WHAT = 1;
    public static final int NOHTTP_WHAT_DEL = 2;
    //定义适配器
    GsonOrderInfo mGsonOrderInfo = null;
    public List<Orders> mOrdersList = null;
    public List<House> mHouseList = null;
    public List<HousePhoto> mHousePhotoList = null;
    String mUrl;
    private RequestQueue mRequestQueue;
    SharedPreferences mSharedPreferences;
    public static final String TYPE = "type";
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order);
        mSharedPreferences = CustomOrderActivity.this.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        initViews();
        addListeners();

        initData();
    }



    private void addListeners() {
        customorderReturn.setOnClickListener(this);
        orderCustomIngtextView.setOnClickListener(this);
        orderCustomHistorytextView.setOnClickListener(this);
        orderCustomIngbottom.setOnClickListener(this);
        orderCustomHistorybottom.setOnClickListener(this);
    }


    private void initViews() {
        customorderReturn = (ImageView) findViewById(R.id.customorder_return);
        orderCustomIngtextView = (TextView) findViewById(R.id.order_custom_ingtextView);
        orderCustomHistorytextView = (TextView) findViewById(R.id.order_custom_historytextView);
        orderCustomIngbottom = (TextView) findViewById(R.id.order_custom_ingbottom);
        orderCustomHistorybottom = (TextView) findViewById(R.id.order_custom_historybottom);
        mViewPager = (ViewPager) findViewById(R.id.order_custom_viewPager);
    }
    private void initData() {
        mOrdersList = new ArrayList<>();
        mHouseList = new ArrayList<>();
        mHousePhotoList = new ArrayList<>();
        MyApplication myApplication = (MyApplication) CustomOrderActivity.this.getApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
        //添加请求参数
        request.add("methods", "getAllOrderByUserId");
        request.add("user_id", user_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        mRequestQueue.add(NOHTTP_WHAT, request, onResponseListener);
        // mList = new ArrayList<>();
        /*Order order1 = new Order("11111","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        Order order2 = new Order("2222","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        Order order3 = new Order("3333","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        Order order4 = new Order("4444","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        mList.add(order1);
        mList.add(order2);
        mList.add(order3);
        mList.add(order4);*/
    }
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == NOHTTP_WHAT) {
                String result = response.get();
                //把JSON格式的字符串改为Student对象
                Log.e("cuikairesult", result + "11");
                Gson gson = new Gson();
                Type type = new TypeToken<GsonOrderInfo>() {}.getType();
                mGsonOrderInfo = gson.fromJson(result, type);
                mOrdersList.addAll(mGsonOrderInfo.getOrdersList());
                mHouseList.addAll(mGsonOrderInfo.getHouseList());
                mHousePhotoList.addAll(mGsonOrderInfo.getHousePhotoList());
                initViewPager();
            }else if (what == NOHTTP_WHAT_DEL) {
                String result = response.get();
                Log.e("chenggong",result+"");
                //把JSON格式的字符串改为Student对象
               /*if(result.equals("删除成功")){
                   mOrderFragmentAdapter.notifyDataSetChanged();
               }*/
                mOrderFragmentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        }

        @Override
        public void onFinish(int what) {

        }
    };
    public void deleteOneOrder(int order_id,int position){
        Log.e("调用了，",order_id+"");
        MyApplication myApplication = (MyApplication) CustomOrderActivity.this.getApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
        //添加请求参数
        request.add("methods", "delOrders");
        request.add("order_id", order_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        mRequestQueue.add(NOHTTP_WHAT_DEL, request, onResponseListener);
        mOrdersList.get(position).setOrder_state("已取消");
        CustomOrderIngFragment customOrderIngFragment = (CustomOrderIngFragment) mOrderFragmentAdapter.getItem(0);
        customOrderIngFragment.deleteOrder(position);
    }
    public void yesStayOneOrder(int order_id,int position){
        Log.e("调用了，",order_id+"");
        MyApplication myApplication = (MyApplication) CustomOrderActivity.this.getApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
        //添加请求参数
        request.add("methods", "yesStayOrders");
        request.add("order_id", order_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        mRequestQueue.add(NOHTTP_WHAT_DEL, request, onResponseListener);

        mOrdersList.get(position).setOrder_state("已取消");
        CustomOrderIngFragment customOrderIngFragment = (CustomOrderIngFragment) mOrderFragmentAdapter.getItem(0);
        customOrderIngFragment.deleteOrder(position);
    }
    private void initViewPager() {
        fragments = new ArrayList<Fragment>();
        Fragment customOrderIngFragment = new CustomOrderIngFragment();
        Fragment customOrderHistoryFragment = new CustomOrderHistoryFragment();
        fragments.add(customOrderIngFragment);
        fragments.add(customOrderHistoryFragment);
        mFragmentManager = getSupportFragmentManager();

        mOrderFragmentAdapter = new OrderFragmentAdapter(mFragmentManager,fragments);
        mViewPager.setAdapter(mOrderFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动时设置监听
            }

            @Override
            public void onPageSelected(int position) {
                //选择时
                if (position == 0){
                    orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                    orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                    orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_textView));
                    orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                }else if (position == 1){
                    orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_textView));
                    orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                    orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                    orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //更改滑动状态时
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.customorder_return:
                finish();
                break;
            case R.id.order_custom_ingtextView:
                orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_textView));
                orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.order_custom_historytextView:
                orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_textView));
                orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                mViewPager.setCurrentItem(1);
                break;
            case R.id.order_custom_ingbottom:
                break;
            case R.id.order_custom_historybottom:
                break;
        }

    }
}
