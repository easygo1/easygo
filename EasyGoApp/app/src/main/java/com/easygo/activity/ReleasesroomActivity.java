package com.easygo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class ReleasesroomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasesroom);

    }



    public void success(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("type",2);
        intent.putExtras(bundle);
        intent.setClass(ReleasesroomActivity.this,MainActivity.class);

        startActivity(intent);
    }
}
