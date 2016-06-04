package com.easygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.easygo.activity.R;
import com.easygo.adapter.CustomOrderAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.order.Order;
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
 * Created by 崔凯 on 2016/5/24.
 */
public class CustomOrderIngFragment extends Fragment {
    public static final int NOHTTP_WHAT = 1;
    //定义视图
    View mOrederIngView;
    //定义适配器
    CustomOrderAdapter mCustomOrderAdapter;
    List<Order> mList;
    ListView mListView;
    String mUrl;
    private RequestQueue mRequestQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mOrederIngView = inflater.inflate(R.layout.order_custom_ing,null);
        initViews();
        initData();
        initAdapter();
        return mOrederIngView;
    }

    private void initAdapter() {
        mCustomOrderAdapter = new CustomOrderAdapter(getActivity(),mList);
        mListView.setAdapter(mCustomOrderAdapter);
    }

    private void initData() {
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
        //添加请求参数
        request.add("methods","getAllOrderByUserId");
        //request.add("userid","1");
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        mRequestQueue.add(NOHTTP_WHAT,request, onResponseListener);
        mList = new ArrayList<>();
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
                Gson gson = new Gson();
                Type type = new TypeToken<List<Orders>>(){}.getType();
//                mList = gson.fromJson(result,type);
                mList.addAll((List<Order>)gson.fromJson(result,type));
                mCustomOrderAdapter.notifyDataSetChanged();
                //表示刷新完成
//                mPullToRefreshListView.onRefreshComplete();
                Log.e("list",mList.toString());
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
        private void initViews() {
        mListView = (ListView) mOrederIngView.findViewById(R.id.order_custom_inglist);
    }
}
