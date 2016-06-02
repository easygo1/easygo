package com.easygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.HouseDetailAdpter;
import com.easygo.fragment.HouseDetailAssessFragment;
import com.easygo.fragment.HouseDetailInfoFragment;
import com.easygo.fragment.HouseDetailOwnerFragment;
import com.easygo.fragment.HouseDetailRuleFragment;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 具体房源页面
 */
public class HouseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager mPhotoViewPager;
    //图片资源数组
    private int[] houseImages;
    private List<ImageView> mImageViewList;

    RadioGroup mRadioGroup;
    List<Fragment> mHouseInfoList;
    //四个fragment
    HouseDetailInfoFragment mInfoFragment;
    HouseDetailRuleFragment mRuleFragment;
    HouseDetailOwnerFragment mOwnerFragment;
    HouseDetailAssessFragment mAssessFragment;
    //碎片
    FragmentManager mFragmentManager;
    HouseDetailAdpter mHouseDetailAdpter;

    ViewPager mHouseinfoViewPager;

    //房源信息用到的控件
    TextView mHouseDescribeTextview, mHousePriceTextview, mHousePhotoSizeTextView;
    ImageView mHouseCollectionImageView, mHouseShareImageView;
    Button mBookButton;
    //一键分享使用
    private OnekeyShare mOnekeyShare = null;

    //接收到的数据
    //House house = new House("房屋标题", "我是房屋描述", "我是房屋类型", "交通信息", 5, 120, 20, "不限", 3, 3, 4, false);

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
    }

    //初始化视图
    private void initViews() {
        mPhotoViewPager = (ViewPager) findViewById(R.id.house_detail_photo_viewpager);
        mRadioGroup = (RadioGroup) findViewById(R.id.house_radiogroup);
        mHouseinfoViewPager = (ViewPager) findViewById(R.id.house_detail_infomation_viewpager);

        mHouseDescribeTextview = (TextView) findViewById(R.id.house_describe);
        mHousePriceTextview = (TextView) findViewById(R.id.house_price);
        mHousePhotoSizeTextView = (TextView) findViewById(R.id.house_photo_size);
        mHouseCollectionImageView = (ImageView) findViewById(R.id.house_collection);
        mHouseShareImageView = (ImageView) findViewById(R.id.house_share);
        mBookButton = (Button) findViewById(R.id.book_house_btn);
    }

    //初始化
    private void initListener() {
        //图片滑动时改变图片张数的显示
        mPhotoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(HouseDetailActivity.this, "第" + position + "张图片", Toast.LENGTH_SHORT).show();
                //mHousePhotoSizeTextView.setText((position + 1) + "/" + house.getHouse_photo_size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        //单击收藏
        mHouseCollectionImageView.setOnClickListener(this);
        //单击分享
        mHouseShareImageView.setOnClickListener(this);
        //申请预定
        mBookButton.setOnClickListener(this);
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

        //mHouseDescribeTextview.setText(house.getHouse_title());
       // mHousePriceTextview.setText("" + house.getHouse_one_price() + "元");
       // mHousePhotoSizeTextView.setText(1 + "/" + house.getHouse_photo_size());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house_collection:
                Toast.makeText(HouseDetailActivity.this, "单击了收藏按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.house_share:
                mOnekeyShare = new OnekeyShare();
                mOnekeyShare.setTitle("一键分享");
                mOnekeyShare.setText("一键分享测试");
                mOnekeyShare.setImageUrl("http://www.mob.com/images/logo_black.png");

                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                mOnekeyShare.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                mOnekeyShare.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//                mOnekeyShare.setSiteUrl("http://www.baidu.com");
                mOnekeyShare.setTitleUrl("http://www.baidu.com");
                //这话必须放最后
                mOnekeyShare.show(HouseDetailActivity.this);
                break;
            case R.id.book_house_btn:
                Intent intent = new Intent();
                intent.setClass(HouseDetailActivity.this, BookActivity.class);
                startActivity(intent);
                break;
        }
    }
}
