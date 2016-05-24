package com.easygo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView feedbackReturn;
    private ImageView feedbackAdd;
    private ImageView feedbackImageView;
    private Button feedbackSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initViews();
        addListeners();
    }
    private void initViews() {
        feedbackReturn = (ImageView) findViewById(R.id.feedback_return);
        feedbackAdd = (ImageView) findViewById(R.id.feedback_add);
        feedbackImageView = (ImageView) findViewById(R.id.feedback_imageView);
        feedbackSuccess = (Button) findViewById(R.id.feedback_success);
    }
    private void addListeners() {
        feedbackReturn.setOnClickListener(this);
        feedbackAdd.setOnClickListener(this);
        feedbackSuccess.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.feedback_return:
                finish();
                break;
            case R.id.feedback_add:
                break;
            case R.id.feedback_success:
                break;
        }
    }
}
