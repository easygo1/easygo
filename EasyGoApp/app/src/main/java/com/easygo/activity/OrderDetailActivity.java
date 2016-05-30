package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easygo.adapter.BookContactAdapter;
import com.easygo.beans.UserLinkman;

import java.util.ArrayList;
import java.util.List;

/*订单详情页面*/
public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener{


    ListView mOrderListView;
    View mBeforeListView,mAfterListView;
    //复用申请预定界面的ListView适配器
    BookContactAdapter mAdapter;
    List<UserLinkman> mList = null;
    //用来加载头布局
    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        initListView();
        initData();
        initView();
        addListeners();
    }


    private void initListView() {
        mOrderListView = (ListView) findViewById(R.id.order_detail_listview);
    }

    private void initData() {
        //初始化List
        mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < 5; i++) {
            mList.add(new UserLinkman(i,i,"小明"+i,"350500199508105515"));
        }
    }

    private void initView() {
        //得到ListView之前的布局
        mInflater = LayoutInflater.from(OrderDetailActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.order_detail_before_listview,null);
        //得到ListView之后的布局
        mAfterListView = mInflater.inflate(R.layout.order_detail_after_listview,null);

        mAdapter = new BookContactAdapter(OrderDetailActivity.this,mList);
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

    }
}
