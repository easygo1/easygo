package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HouseCollectionActivity extends AppCompatActivity {
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_collection);
        mTextView= (TextView) findViewById(R.id.title_text);
        mTextView.setText("我的收藏");
    }
}
