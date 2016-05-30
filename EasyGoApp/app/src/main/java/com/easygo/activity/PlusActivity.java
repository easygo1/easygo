package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * 加号：“+”额外功能添加
 */
public class PlusActivity extends AppCompatActivity {
    ImageView mImageView1,mImageView2,mImageView3;
    LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_plus);
        initView();
        action();

    }

    private void action() {
        //平移补间动画
        TranslateAnimation ta=new TranslateAnimation(10,10,1000,100);
        ta.setDuration(1000);
        mLinearLayout.startAnimation(ta);
    }

    private void initView() {
        mLinearLayout= (LinearLayout) findViewById(R.id.linearlayout1);
        mImageView1= (ImageView) findViewById(R.id.imageview1);
        mImageView2= (ImageView) findViewById(R.id.imageview3);
        mImageView3= (ImageView) findViewById(R.id.imageview3);
    }

    public void fanhui(View view) {
        //返回上一个页面
        finish();
    }
}
