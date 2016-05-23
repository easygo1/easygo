package com.easygo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.easygo.adapter.HouseDetailAdpter;
import com.easygo.fragment.HouseDetailAssessFragment;
import com.easygo.fragment.HouseDetailInfoFragment;
import com.easygo.fragment.HouseDetailOwnerFragment;
import com.easygo.fragment.HouseDetailRuleFragment;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailActivity extends AppCompatActivity {

    ViewPager mPhotoViewPager;
    //图片资源数组
    private int[] houseImages;
    private List<ImageView> mImageViewList;

    RadioGroup mRadioGroup;
    ViewPager mHouseinfoViewPager;
    List<Fragment> mHouseInfoList;
    //四个fragment
    HouseDetailInfoFragment mInfoFragment;
    HouseDetailRuleFragment mRuleFragment;
    HouseDetailOwnerFragment mOwnerFragment;
    HouseDetailAssessFragment mAssessFragment;
    //碎片
    FragmentManager mFragmentManager;
    HouseDetailAdpter mHouseDetailAdpter;
   // FragmentTransaction mFragmentTransaction;
   // ScrollView mMyScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        initViews();
        initListener();
        initData();
        //绑定适配器
        mPhotoViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViewList.get(position));
                return mImageViewList.get(position);
            }
        });

       /* mMyScrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               *//* v.getParent().requestDisallowInterceptTouchEvent(true);*//*
                return true;
            }
        });*/
    }

    //初始化视图
    private void initViews() {
        mPhotoViewPager = (ViewPager) findViewById(R.id.house_detail_photo_viewpager);
        mRadioGroup = (RadioGroup) findViewById(R.id.house_radiogroup);
        mHouseinfoViewPager = (ViewPager) findViewById(R.id.house_detail_infomation_viewpager);
//        mMyScrollview = (ScrollView) findViewById(R.id.myscrollview);
    }

    //初始化
    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动时改变按钮
        mHouseinfoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
                resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /* mHouseinfoViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
    }

    private void resetRadioButton(int position) {
        //获取position位置处对应的单选按钮
        RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }

    private void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.house_info:
                mHouseinfoViewPager.setCurrentItem(0);
                break;
            case R.id.house_rule:
                mHouseinfoViewPager.setCurrentItem(1);
                break;
            case R.id.house_owner_info:
                mHouseinfoViewPager.setCurrentItem(2);
                break;
            case R.id.house_assess:
                mHouseinfoViewPager.setCurrentItem(3);
                break;
        }
    }

    //初始化数据源
    private void initData() {
        houseImages = new int[]{
                R.drawable.house_detail1,
                R.drawable.house_detail2,
                R.drawable.house_detail3,
                R.drawable.house_detail4
        };
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < houseImages.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(houseImages[i]);
            mImageViewList.add(imageView);
        }

        mHouseInfoList = new ArrayList<>();
        mInfoFragment = new HouseDetailInfoFragment();
        mRuleFragment = new HouseDetailRuleFragment();
        mOwnerFragment = new HouseDetailOwnerFragment();
        mAssessFragment = new HouseDetailAssessFragment();
        mHouseInfoList.add(mInfoFragment);
        mHouseInfoList.add(mRuleFragment);
        mHouseInfoList.add(mOwnerFragment);
        mHouseInfoList.add(mAssessFragment);

        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mHouseDetailAdpter = new HouseDetailAdpter(mFragmentManager, mHouseInfoList);
        mHouseinfoViewPager.setAdapter(mHouseDetailAdpter);
    }
}
