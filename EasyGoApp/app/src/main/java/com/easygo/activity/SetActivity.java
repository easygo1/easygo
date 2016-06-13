package com.easygo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SetActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView mReturnImageView;
    TextView mFeedbackTextView,mAboutusTextView,mClearcacheTextView,mExitloginTextView;
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initViews();
        addListeners();
    }

    private void initViews() {
        mReturnImageView = (ImageView) findViewById(R.id.set_return);
        mFeedbackTextView = (TextView) findViewById(R.id.set_feedback);
        mAboutusTextView = (TextView) findViewById(R.id.set_aboutus);
        mClearcacheTextView = (TextView) findViewById(R.id.set_clearcache);
        mExitloginTextView = (TextView) findViewById(R.id.set_exitlogin);
    }
    private void addListeners() {
        mReturnImageView.setOnClickListener(this);
        mFeedbackTextView.setOnClickListener(this);
        mAboutusTextView.setOnClickListener(this);
        mClearcacheTextView.setOnClickListener(this);
        mExitloginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.set_return:
                finish();
                break;
            case R.id.set_feedback:
                Intent intent = new Intent();
                intent.setClass(SetActivity.this,FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.set_aboutus:
                Intent intentaboutus = new Intent();
                intentaboutus.setClass(SetActivity.this,AboutusActivity.class);
                startActivity(intentaboutus);
                break;
            case R.id.set_clearcache:
                Intent intent2 = new Intent();
                intent2.setClass(SetActivity.this,OwnerOrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.set_exitlogin:
                //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
                mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
                //向偏好设置文件中保存数据
                mEditor = mSharedPreferences.edit();
                mEditor.putInt("type", 0);
                mEditor.putInt("user_id",0);
                mEditor.putInt("user_type",0);
                mEditor.putString("user_realname",null);
                mEditor.putString("user_nickname",null);
                mEditor.putString("user_sex",null);
                mEditor.putString("user_photo",null);
                mEditor.putString("user_job",null);
                mEditor.putString("user_address_province",null);
                mEditor.putString("user_address_city",null);
                mEditor.putString("user_mood",null);
                mEditor.putString("user_mail",null);
                mEditor.putString("user_introduct",null);
                mEditor.putString("user_birthday",null);
                mEditor.putString("user_idcard",null);
                mEditor.putString("remarks",null);
                mEditor.putString("token",null);
                mEditor.putString("phone",null);
                //提交保存结果
                mEditor.commit();
                Intent intentExit = new Intent();
                intentExit.putExtra("flag","me");
                intentExit.setClass(SetActivity.this,MainActivity.class);
                startActivity(intentExit);
                break;
        }
    }
}
