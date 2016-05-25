package com.easygo.activity;
/*
*
* 申请预定的界面
* */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.BookContactAdapter;
import com.easygo.beans.UserLinkman;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    ListView mBookListView;
    TextView mHumanNumberTextView,mSubtractTextView,mAddTextView,mAddContactTextView;
    View mBeforeListView,mAfterListView,mCheckLayout,mLeaveLayout;
    BookContactAdapter mAdapter;
    List<UserLinkman> mList = null;
    //用来加载头布局
    LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_book);
        //顺序不能变
        //先初始化ListView
        initListView();
        initData();
        initView();
        addListeners();
        //加载网络数据
//        loadData();
    }

    private void initListView() {
        mBookListView = (ListView) findViewById(R.id.room_book_listview);
    }

    private void initData() {
        //初始化List
        mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < 10; i++) {
            mList.add(new UserLinkman(i,i,"小明"+i,"350500199508105515"));
        }
    }

    private void initView() {
        //得到ListView之前的布局
        mInflater = LayoutInflater.from(BookActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.room_book_before_listview,null);
        //得到ListView之后的布局
        mAfterListView = mInflater.inflate(R.layout.room_book_after_listview,null);
        mAdapter = new BookContactAdapter(BookActivity.this,mList);
        //将ListView之前，之后的布局加到前面
        mBookListView.addHeaderView(mBeforeListView);
        mBookListView.addFooterView(mAfterListView);
        //添加适配器
        mBookListView.setAdapter(mAdapter);

        //ListView之前控件的初始化
        mCheckLayout =mBeforeListView.findViewById(R.id.room_book_check_layout);
        mLeaveLayout = mBeforeListView.findViewById(R.id.room_book_leave_layout);
        mHumanNumberTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_human_number);
        mSubtractTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_subtract);
        mAddTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_add);
        //ListView之后的控件
        mAddContactTextView = (TextView) mAfterListView.findViewById(R.id.room_book_add_contact);

    }
    //用来获取服务器的数据
    private void loadData() {
        mAdapter.notifyDataSetChanged();
    }

    private void addListeners() {
        //ListView之前的控件监听
        mCheckLayout.setOnClickListener(BookActivity.this);
        mLeaveLayout.setOnClickListener(BookActivity.this);
        mSubtractTextView.setOnClickListener(BookActivity.this);
        mAddTextView.setOnClickListener(BookActivity.this);
        //ListView的监听
        mBookListView.setOnItemClickListener(BookActivity.this);
        //ListView之后的控件监听
        mAddContactTextView.setOnClickListener(BookActivity.this);

    }

    /*
    * 人数最少1个，然后需要根据数据库得到的每个房间的具体信息，设置最多可住人数，及计算价格
    */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        int mNum;//点击时刻的人数
        mNum = Integer.parseInt((String) mHumanNumberTextView.getText());
        switch (id){
            case R.id.room_book_add:
                //此处应该得到数据库值然后加以更改
                if (mNum != 7){
                    mNum++;
                    mHumanNumberTextView.setText(mNum + "");
                }
                break;
            case  R.id.room_book_subtract:
                if (mNum != 1){
                    mNum--;
                    mHumanNumberTextView.setText(mNum + "");
                }
                break;
            case R.id.room_book_check_layout:
                Toast.makeText(BookActivity.this, "入住", Toast.LENGTH_SHORT).show();
                break;
            case R.id.room_book_leave_layout:
                Toast.makeText(BookActivity.this, "离开", Toast.LENGTH_SHORT).show();
                break;
            case R.id.room_book_add_contact:
                break;
            default:
                break;
        }
    }

    //ListView 的监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
