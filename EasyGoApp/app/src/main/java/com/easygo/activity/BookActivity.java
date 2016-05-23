package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mHumanNumberTextView,mSubtractTextView,mAddTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_book);
        initView();
        addListeners();
//        Toast.makeText(BookActivity.this, Integer.parseInt((String) mHumanNumberTextView.getText())+"", Toast.LENGTH_SHORT).show();

    }

    private void initView() {
        mHumanNumberTextView = (TextView) findViewById(R.id.room_book_human_number);
        mSubtractTextView = (TextView) findViewById(R.id.room_book_subtract);
        mAddTextView = (TextView) findViewById(R.id.room_book_add);
    }

    private void addListeners() {
//        mHumanNumberTextView.setOnClickListener(this);
        mSubtractTextView.setOnClickListener(this);
        mAddTextView.setOnClickListener(this);
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
}
