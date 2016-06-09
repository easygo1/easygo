package com.easygo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easygo.activity.OrderDetailActivity;
import com.easygo.activity.R;
import com.easygo.adapter.OwnerOrderIngAdapter;
import com.easygo.beans.gson.GsonOrderInfo;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
import com.yolanda.nohttp.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 崔凯 on 2016/5/24.
 */
public class OwnerOrderBookMeIngFragment extends Fragment {
    public static final int NOHTTP_WHAT = 1;
    //定义视图
    View mOrederIngView;
    //定义适配器
    OwnerOrderIngAdapter mOwnerOrderIngAdapter;
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
        mOwnerOrderIngAdapter = new OwnerOrderIngAdapter(getActivity(), mOrdersList,mHouseList,mHousePhotoList);
        mListView.setAdapter(mOwnerOrderIngAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("order_id",mOrdersList.get(position).getOrder_id());
                intent.setClass(getActivity(), OrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        OwnerOrderBookMeFragment ownerOrderBookMeFragment = (OwnerOrderBookMeFragment) getParentFragment();
        //将不是已完成状态的订单加入list
        if(ownerOrderBookMeFragment.mOrdersList.size()>0){
            for (int i = 0; i < ownerOrderBookMeFragment.mOrdersList.size(); i++){
                if (!ownerOrderBookMeFragment.mOrdersList.get(i).getOrder_state().equals("已完成")){
                    mOrdersList.add(ownerOrderBookMeFragment.mOrdersList.get(i));
                    mHouseList.add(ownerOrderBookMeFragment.mHouseList.get(i));
                    mHousePhotoList.add(ownerOrderBookMeFragment.mHousePhotoList.get(i));
                }
            }
        }
        mOwnerOrderIngAdapter.notifyDataSetChanged();
    }
    public void deleteOrder(int position){
        mOrdersList.remove(position);
        mHouseList.remove(position);
        mHousePhotoList.remove(position);
        mOwnerOrderIngAdapter.notifyDataSetChanged();
    }
    private void initViews() {
        mListView = (ListView) mOrederIngView.findViewById(R.id.order_custom_inglist);
    }
}
