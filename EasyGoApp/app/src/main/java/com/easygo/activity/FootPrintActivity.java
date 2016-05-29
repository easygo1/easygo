package com.easygo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//历史足迹页面
public class FootPrintActivity extends AppCompatActivity {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print);
        mTextView= (TextView) findViewById(R.id.title_text);
        mTextView.setText("我的足迹");
        Intent intent=new Intent();
        intent.setClass(FootPrintActivity.this,SelectLocationActivity.class);
        startActivity(intent);
    }
}
