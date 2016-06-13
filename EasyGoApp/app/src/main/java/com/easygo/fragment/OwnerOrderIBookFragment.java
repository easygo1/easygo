package com.easygo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.adapter.OrderFragmentAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonOrderInfo;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
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

/**
 * Created by 崔凯 on 2016/6/7.
 */
public class OwnerOrderIBookFragment extends Fragment implements View.OnClickListener{
    private ViewPager mViewPager;
    OrderFragmentAdapter mOrderFragmentAdapter;
    FragmentManager mFragmentManager;
    private ArrayList fragments;
    private TextView orderIbookIngtextView;
    private TextView orderIbookHistorytextView;
    private TextView orderIbookIngbottom;
    private TextView orderIbookHistorybottom;
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
    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.owner_order_fragment_ibook,null);
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        initViews();
        addListeners();
        initData();
        return mView;
    }
    private void initViews() {
        orderIbookIngtextView = (TextView) mView.findViewById(R.id.order_bookme_ingtextView);
        orderIbookHistorytextView = (TextView) mView.findViewById(R.id.order_bookme_historytextView);
        orderIbookIngbottom = (TextView) mView.findViewById(R.id.order_bookme_ingbottom);
        orderIbookHistorybottom = (TextView) mView.findViewById(R.id.order_bookme_historybottom);
        mViewPager = (ViewPager) mView.findViewById(R.id.order_bookme_viewPager);

    }
    private void addListeners() {
        orderIbookIngtextView.setOnClickListener(this);
        orderIbookHistorytextView.setOnClickListener(this);
    }
    private void initData() {
        mOrdersList = new ArrayList<>();
        mHouseList = new ArrayList<>();
        mHousePhotoList = new ArrayList<>();
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
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
                Log.e("zhihou ","4646");
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
        MyApplication myApplication = new MyApplication();
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
        OwnerOrderIBookIngFragment ownerOrderIBookIngFragment = (OwnerOrderIBookIngFragment)mOrderFragmentAdapter.getItem(0);
        ownerOrderIBookIngFragment.deleteOrder(position);
    }
    private void initViewPager() {
        fragments = new ArrayList<Fragment>();
        Fragment ownerOrderIBookIngFragment= new OwnerOrderIBookIngFragment();
        Fragment ownerOrderIBookHistoryFragment = new OwnerOrderIBookHistoryFragment();
        fragments.add(ownerOrderIBookIngFragment);
        fragments.add(ownerOrderIBookHistoryFragment);
        mFragmentManager = getChildFragmentManager();
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
                    orderIbookIngtextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                    orderIbookIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                    orderIbookHistorytextView.setTextColor(getResources().getColor(R.color.order_textView));
                    orderIbookHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                }else if (position == 1){
                    orderIbookIngtextView.setTextColor(getResources().getColor(R.color.order_textView));
                    orderIbookIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                    orderIbookHistorytextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                    orderIbookHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
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
            case R.id.order_bookme_ingtextView:
                orderIbookIngtextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                orderIbookIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                orderIbookHistorytextView.setTextColor(getResources().getColor(R.color.order_textView));
                orderIbookHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.order_bookme_historytextView:
                orderIbookIngtextView.setTextColor(getResources().getColor(R.color.order_textView));
                orderIbookIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                orderIbookHistorytextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                orderIbookHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                mViewPager.setCurrentItem(1);
                break;
        }
    }
}
