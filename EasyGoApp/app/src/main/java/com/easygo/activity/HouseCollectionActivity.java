package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HouseCollectionActivity extends AppCompatActivity {
    TextView mTextView;
    ImageView mBackImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_collection);
        initViews();
    }

    private void initViews() {
        mTextView= (TextView) findViewById(R.id.title_text);
        mTextView.setText("我的收藏");
        mBackImageView= (ImageView) findViewById(R.id.back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
