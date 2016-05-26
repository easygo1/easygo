package com.easygo.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.BookContactAdapter;
import com.easygo.adapter.UserLinkmanAdapter;
import com.easygo.beans.UserLinkman;

import java.util.ArrayList;
import java.util.List;

/*
* 用户个人中心的常用入住人
* */
public class UserLinkmanActivity extends AppCompatActivity implements View.OnClickListener{
    List<UserLinkman> mList = null;
    ListView mLinkManListView;
    UserLinkmanAdapter mAdapter;
    TextView mAddLinkmanTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_linkman);
        initView();
        initData();
        addListeners();
    }


    private void initData() {
        //初始化List
        mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < 10; i++) {
            mList.add(new UserLinkman(i,i,"小明"+i,"身份证：350500199508105515"));
        }
        mAdapter = new UserLinkmanAdapter(UserLinkmanActivity.this,mList);
        mLinkManListView.setAdapter(mAdapter);
    }

    private void initView() {
        mLinkManListView = (ListView) findViewById(R.id.me_user_linkman_listview);
        mAddLinkmanTextView = (TextView) findViewById(R.id.me_user_linkman_add);
    }

    private void addListeners() {
        mAddLinkmanTextView.setOnClickListener(this);
        //listview的监听
        mLinkManListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(UserLinkmanActivity.this, "小明"+position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_user_linkman_add:
                Toast.makeText(UserLinkmanActivity.this, "添加", Toast.LENGTH_SHORT).show();
                break;
        }
    }




}
