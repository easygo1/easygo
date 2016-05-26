package com.easygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.easygo.activity.R;
import com.easygo.adapter.CustomOrderAdapter;
import com.easygo.beans.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 崔凯 on 2016/5/24.
 */
public class CustomOrderIngFragment extends Fragment {
    //定义视图
    View mOrederIngView;
    //定义适配器
    CustomOrderAdapter mCustomOrderAdapter;
    List<Order> mList;
    ListView mListView;
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
        mList = new ArrayList<>();
        Order order1 = new Order("11111","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        Order order2 = new Order("2222","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        Order order3 = new Order("3333","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        Order order4 = new Order("4444","带确认","5月25","5月26","共一晚","沙发",99.00,R.drawable.home_city2);
        mList.add(order1);
        mList.add(order2);
        mList.add(order3);
        mList.add(order4);
    }

    private void initViews() {
        mListView = (ListView) mOrederIngView.findViewById(R.id.order_custom_inglist);
    }
}
