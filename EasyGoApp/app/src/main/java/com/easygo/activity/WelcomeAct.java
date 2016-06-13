package com.easygo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by 王韶辉 on 2016/6/12.
 */
public class WelcomeAct extends Activity {
    private boolean isFirstIn = false;

    //设置延时的时间，之后判断进入引导页还是主界面
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("guide", MODE_PRIVATE);
        //第一次获取时为空
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        if (!isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            //当进入引导界面之后储存起来
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            SharedPreferences.Editor editor= preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
        }
    }

    private void goHome() {
        Intent i = new Intent(WelcomeAct.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goGuide() {
        Intent i = new Intent(WelcomeAct.this, Guide.class);
        startActivity(i);
        finish();
    }
}
