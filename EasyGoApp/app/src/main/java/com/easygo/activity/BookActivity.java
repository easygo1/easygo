package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class BookActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    ListView mListView;
    TextView mHumanNumberTextView,mSubtractTextView,mAddTextView;
    View mBeforeListView;
    Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_book);
        initView();
        addListeners();

    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.room_book_listview);
        mHumanNumberTextView = (TextView) findViewById(R.id.room_book_human_number);
        mSubtractTextView = (TextView) findViewById(R.id.room_book_subtract);
        mAddTextView = (TextView) findViewById(R.id.room_book_add);
        mBeforeListView = findViewById(R.id.room_book_before_list);

        mListView.addHeaderView(mBeforeListView);

    }

    private void addListeners() {
//        mHumanNumberTextView.setOnClickListener(this);
        mSubtractTextView.setOnClickListener(this);
        mAddTextView.setOnClickListener(this);
        mListView.setOnItemClickListener(BookActivity.this);
    }

    /*
    * 人数最少1个，然后需要根据数据库得到的每个房间的具体信息，设置最多可住人数，及计算价格
    *
    */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        int mNum;//点击时刻的人数
        mNum = Integer.parseInt((String) mHumanNumberTextView.getText());
        switch (id){
            case R.id.room_book_add:
                Toast.makeText(BookActivity.this, Integer.parseInt((String) mHumanNumberTextView.getText())+"", Toast.LENGTH_SHORT).show();
                //此处应该得到数据库值然后加以更改
                if (mNum <= 7){
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
            default:
                break;
        }
    }

    //ListView 的监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
