package com.easygo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ConversationActivity extends AppCompatActivity {
    private TextView mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mName = (TextView) findViewById(R.id.sname);
        //获取id
        String sId = getIntent().getData().getQueryParameter("targetId");
        //获取tittle，获取昵称
        String sName = getIntent().getData().getQueryParameter("title");//需要获取信息提供者

        mName.setText(sName);
    }

    public void back(View view) {
        finish();
    }
}
