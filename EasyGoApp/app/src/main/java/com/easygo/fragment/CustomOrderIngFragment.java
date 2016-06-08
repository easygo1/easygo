package com.easygo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.easygo.activity.CustomOrderActivity;
import com.easygo.activity.R;
import com.easygo.adapter.CustomOrderAdapter;
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
 * Created by 崔凯 on 2016/5/24.
 */
public class CustomOrderIngFragment extends Fragment {
    public static final int NOHTTP_WHAT = 1;
    //定义视图
    View mOrederIngView;
    //定义适配器
    CustomOrderAdapter mCustomOrderAdapter;
    GsonOrderInfo mGsonOrderInfo = null;
    List<Orders> mOrdersList = null;
    List<House> mHouseList = null;
    List<HousePhoto> mHousePhotoList = null;
    ListView mListView;
    String mUrl;
    private RequestQueue mRequestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mOrederIngView = inflater.inflate(R.layout.order_custom_ing, null);
        initViews();

        initAdapter();
        initData();
        return mOrederIngView;
    }

    private void initAdapter() {
        mOrdersList = new ArrayList<>();
        mHouseList = new ArrayList<>();
        mHousePhotoList = new ArrayList<>();
        mCustomOrderAdapter = new CustomOrderAdapter(getActivity(), mOrdersList,mHouseList,mHousePhotoList);
        mListView.setAdapter(mCustomOrderAdapter);
    }

    public void initData(){

        CustomOrderActivity customOrderActivity = (CustomOrderActivity) getActivity();
        //将不是已完成状态的订单加入list
        if(customOrderActivity.mOrdersList.size()>0){
            for (int i = 0; i < customOrderActivity.mOrdersList.size(); i++){
                if (!customOrderActivity.mOrdersList.get(i).getOrder_state().equals("已完成")){
                    mOrdersList.add(customOrderActivity.mOrdersList.get(i));
                    mHouseList.add(customOrderActivity.mHouseList.get(i));
                    mHousePhotoList.add(customOrderActivity.mHousePhotoList.get(i));
                }
            }
        }
        mCustomOrderAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        mListView = (ListView) mOrederIngView.findViewById(R.id.order_custom_inglist);
    }
}
