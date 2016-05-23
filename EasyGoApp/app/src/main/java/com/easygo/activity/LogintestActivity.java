package com.easygo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LogintestActivity extends AppCompatActivity {
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logintest);
    }

    public void login(View view) {
        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式

        mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("type", 1);
        //提交保存结果
        mEditor.commit();
        Intent intent = new Intent();
       /* Bundle bundle = new Bundle();
        bundle.putInt("type",1);
        intent.putExtras(bundle);*/
        intent.setClass(LogintestActivity.this,MainActivity.class);

        startActivity(intent);

    }

    public void reg(View view) {
        Intent intent=new Intent(LogintestActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
