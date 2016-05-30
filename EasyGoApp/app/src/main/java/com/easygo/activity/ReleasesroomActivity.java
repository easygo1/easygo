package com.easygo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.FocusFinder;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReleasesroomActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView mReturnImageView,mCameraImageView;
    EditText mTitleEditText,mHomeinfoEditText,mTrafficinfoEditText;
    RelativeLayout mRoompicLayout,mBedLayout,mFacilitiesLayout,mAddressLayout,mMostnumLayout,mOnepriceLayout,mAddpriceLayout,mLimitsexLayout,mStaytimeLayout,mRealnameLayout;
    TextView mBedTextView,mFacilitiesTextView,mAddressTextView,mMostnumTextView,mOnepriceTextView,mAddpriceTextView,mLimitsexTextView,mStaytimeTextView,mRealnameTextView;
    Button mSuccessButton;
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasesroom);
        initView();
        addListeners();
    }


    private void initView() {
        mReturnImageView = (ImageView) findViewById(R.id.releaseroom_return);
        mCameraImageView = (ImageView) findViewById(R.id.releaseroom_camera);
        mTitleEditText = (EditText) findViewById(R.id.releaseroom_title);
        mHomeinfoEditText = (EditText) findViewById(R.id.releaseroom_homeinfo);
        mTrafficinfoEditText = (EditText) findViewById(R.id.releaseroom_trafficinfo);
        mRoompicLayout = (RelativeLayout) findViewById(R.id.releaseroom_roompic);
        mBedLayout = (RelativeLayout) findViewById(R.id.releaseroom_bed);
        mFacilitiesLayout = (RelativeLayout) findViewById(R.id.releaseroom_facilities);
        mAddressLayout = (RelativeLayout) findViewById(R.id.releaseroom_address);
        mMostnumLayout = (RelativeLayout) findViewById(R.id.releaseroom_mostnum);
        mOnepriceLayout = (RelativeLayout) findViewById(R.id.releaseroom_oneprice);
        mAddpriceLayout = (RelativeLayout) findViewById(R.id.releaseroom_addprice);
        mLimitsexLayout = (RelativeLayout) findViewById(R.id.releaseroom_limitsex);
        mStaytimeLayout = (RelativeLayout) findViewById(R.id.releaseroom_staytime);
        mRealnameLayout = (RelativeLayout) findViewById(R.id.releaseroom_realname);
        mSuccessButton = (Button) findViewById(R.id.releaseroom_success);
        mBedTextView = (TextView) findViewById(R.id.releaseroom_bedtextView);
        mFacilitiesTextView = (TextView) findViewById(R.id.releaseroom_facilitiestextView);
        mAddressTextView = (TextView) findViewById(R.id.releaseroom_addresstextView);
        mMostnumTextView = (TextView) findViewById(R.id.releaseroom_mostnumtextView);
        mOnepriceTextView = (TextView) findViewById(R.id.releaseroom_onepricetextView);
        mAddpriceTextView = (TextView) findViewById(R.id.releaseroom_addpricetextView);
        mLimitsexTextView = (TextView) findViewById(R.id.releaseroom_limitsextextView);
        mStaytimeTextView = (TextView) findViewById(R.id.releaseroom_staytimetextView);
        mRealnameTextView = (TextView) findViewById(R.id.releaseroom_realnametextView);
    }
    private void addListeners() {
        mReturnImageView.setOnClickListener(this);
        mCameraImageView.setOnClickListener(this);
        mBedLayout.setOnClickListener(this);
        mFacilitiesLayout.setOnClickListener(this);
        mAddressLayout.setOnClickListener(this);
        mMostnumLayout.setOnClickListener(this);
        mOnepriceLayout.setOnClickListener(this);
        mAddpriceLayout.setOnClickListener(this);
        mLimitsexLayout.setOnClickListener(this);
        mStaytimeLayout.setOnClickListener(this);
        mRealnameLayout.setOnClickListener(this);
        mSuccessButton.setOnClickListener(this);


    }

    public void success(View view) {
        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
        mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("type", 2);
        //提交保存结果
        mEditor.commit();
        Intent intent = new Intent();
        intent.putExtra("flag","me");
        intent.setClass(ReleasesroomActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.releaseroom_return:
                Intent intent = new Intent();
                intent.putExtra("flag","me");
                intent.setClass(ReleasesroomActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.releaseroom_camera:
                break;
            case R.id.releaseroom_bed:
                showBedDialog();
                break;
            case R.id.releaseroom_facilities:
                showFacilitiesDialog();
                break;
            case R.id.releaseroom_address:
                break;
            case R.id.releaseroom_mostnum:
                showMostnumDialog();
                break;
            case R.id.releaseroom_oneprice:
                showOnePriceDialog();
                break;
            case R.id.releaseroom_addprice:
                showAddpriceDialog();
                break;
            case R.id.releaseroom_limitsex:
                showLimitsex();
                break;
            case R.id.releaseroom_staytime:
                showStaytimeDialog();
                break;
            case R.id.releaseroom_realname:
                break;
        }
    }


    private void showBedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.bed, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String bedStr = getResources().getStringArray(R.array.bed)[which];
                mBedTextView.setText(bedStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showFacilitiesDialog() {
    }
    private void showMostnumDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.mostnum, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mostnumStr = getResources().getStringArray(R.array.mostnum)[which];
                mMostnumTextView.setText(mostnumStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showOnePriceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.oneprice, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String onepriceStr = getResources().getStringArray(R.array.oneprice)[which];
                mOnepriceTextView.setText(onepriceStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showAddpriceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.addprice, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String addpriceStr = getResources().getStringArray(R.array.addprice)[which];
                mAddpriceTextView.setText(addpriceStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showLimitsex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.limitsex, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String limitsexStr = getResources().getStringArray(R.array.limitsex)[which];
                mLimitsexTextView.setText(limitsexStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showStaytimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.staytime, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String staytimeStr = getResources().getStringArray(R.array.staytime)[which];
                mStaytimeTextView.setText(staytimeStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
