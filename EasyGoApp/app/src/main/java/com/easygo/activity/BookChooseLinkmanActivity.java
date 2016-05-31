package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.BookChooseLinkmanAdapter;
import com.easygo.beans.user.UserLinkman;

import java.util.ArrayList;
import java.util.List;

/*
* 申请预订时选择的常用入住人列表
* */
public class BookChooseLinkmanActivity extends AppCompatActivity {
    List<UserLinkman> mList = null;
    ListView mChooseLinkManListView;
    BookChooseLinkmanAdapter mAdapter;
    TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_book_choose_linkman);
        initView();
        initData();
        addListeners();
    }

    private void initData() {
        //初始化List
        mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < 10; i++) {
            mList.add(new UserLinkman(i, i, "小明" + i, "身份证：350500199508105515"));
        }
        mAdapter = new BookChooseLinkmanAdapter(BookChooseLinkmanActivity.this, mList);
        mChooseLinkManListView.setAdapter(mAdapter);
    }

    private void initView() {
        mChooseLinkManListView = (ListView) findViewById(R.id.room_book_choose_list);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mTitleTextView.setText("选择入住人");
    }

    private void addListeners() {
        //listview的监听
        mChooseLinkManListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BookChooseLinkmanActivity.this, "小明" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
