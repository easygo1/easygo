package com.easygo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easygo.fragment.CustomOrderHistoryFragment;
import com.easygo.fragment.CustomOrderIngFragment;

public class CustomOrderActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView mReturnImageView;
    RelativeLayout mIngRelativeLayout,mHistoryRelativeLayout;
    private CustomOrderIngFragment mCustomOrderIngFragment;
    private CustomOrderHistoryFragment mCustomOrderHistoryFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order);
        initViews();
        addListeners();
        //默认显示进行中模块，修改背景颜色为选中颜色
        initDefault();
    }

    private void initDefault() {
        //开始先显示第一个界面
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //默认显示ing界面，初始化ing碎片
        mCustomOrderIngFragment = new CustomOrderIngFragment();
        mFragmentTransaction.add(R.id.customorder_middle,mCustomOrderIngFragment);
        mFragmentTransaction.commit();
    }
    private void initViews() {
        mReturnImageView = (ImageView) findViewById(R.id.customorder_return);
        mIngRelativeLayout = (RelativeLayout) findViewById(R.id.customorder_ing);
        mHistoryRelativeLayout = (RelativeLayout) findViewById(R.id.customorder_history);
    }
    private void addListeners() {
        mReturnImageView.setOnClickListener(this);
        mIngRelativeLayout.setOnClickListener(this);
        mHistoryRelativeLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //如果不是返回按钮
        if (v.getId() != R.id.customorder_return) {
            //还原layout背景颜色
            reset();
            //得到选中的id
            int id = v.getId();
            //设置当前选中layout背景色
            setlayoutColor(id);
            //设置当前选中碎片
            initCurrentFragment(id);
        } else {
            finish();
        }
    }

    private void initCurrentFragment(int id) {
        //隐藏所有已添加到事务中的碎片
        hideAllFragments();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (id){
            case R.id.customorder_ing:
                if (mCustomOrderIngFragment == null){
                    mCustomOrderIngFragment = new CustomOrderIngFragment();
                    mFragmentTransaction.add(R.id.customorder_middle,mCustomOrderIngFragment);
                }else {
                    mFragmentTransaction.show(mCustomOrderIngFragment);
                }
                break;
            case R.id.customorder_history:
                if (mCustomOrderHistoryFragment == null){
                    mCustomOrderHistoryFragment = new CustomOrderHistoryFragment();
                    mFragmentTransaction.add(R.id.customorder_middle,mCustomOrderHistoryFragment);
                }else {
                    mFragmentTransaction.show(mCustomOrderHistoryFragment);
                }
                break;
        }
        mFragmentTransaction.commit();
    }

    private void hideAllFragments() {
        //隐藏所有的碎片，首先需要知道碎片是否已经添加到事务中
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(mCustomOrderIngFragment != null && mCustomOrderIngFragment.isAdded()){
            mFragmentTransaction.hide(mCustomOrderIngFragment);
        }
        if (mCustomOrderHistoryFragment != null && mCustomOrderHistoryFragment.isAdded()){
            mFragmentTransaction.hide(mCustomOrderHistoryFragment);
        }
        mFragmentTransaction.commit();
    }

    private void reset() {
        //将ing和history背景都还原为白色
        mIngRelativeLayout.setBackgroundColor(getResources().getColor(R.color.order_nobackground));
        mHistoryRelativeLayout.setBackgroundColor(getResources().getColor(R.color.order_nobackground));
    }
    //设置选中layout的背景颜色
    public void setlayoutColor(int id) {
        switch (id){
            case R.id.customorder_ing:
                mIngRelativeLayout.setBackgroundColor(getResources().getColor(R.color.order_background));
                break;
            case R.id.customorder_history:
                mHistoryRelativeLayout.setBackgroundColor(getResources().getColor(R.color.order_background));
                break;
        }
    }
}
