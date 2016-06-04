package com.easygo.activity;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easygo.adapter.CustomOrderAdapter;
import com.easygo.adapter.OrderFragmentAdapter;
import com.easygo.fragment.CustomOrderHistoryFragment;
import com.easygo.fragment.CustomOrderIngFragment;
import com.mob.tools.gui.MobViewPager;

import java.util.ArrayList;

public class CustomOrderActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    OrderFragmentAdapter mOrderFragmentAdapter;
    FragmentManager mFragmentManager;
    private ArrayList fragments;
    private TextView orderCustomIngtextView;
    private TextView orderCustomHistorytextView;
    private TextView orderCustomIngbottom;
    private TextView orderCustomHistorybottom;
    private ImageView customorderReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order);
        initViews();
        addListeners();
        initViewPager();
    }

    private void addListeners() {
        //customorderReturn.setOnClickListener(this);
        orderCustomIngtextView.setOnClickListener(this);
        orderCustomHistorytextView.setOnClickListener(this);
        orderCustomIngbottom.setOnClickListener(this);
        orderCustomHistorybottom.setOnClickListener(this);
    }


    private void initViews() {
        //customorderReturn = (ImageView) findViewById(R.id.customorder_return);
        orderCustomIngtextView = (TextView) findViewById(R.id.order_custom_ingtextView);
        orderCustomHistorytextView = (TextView) findViewById(R.id.order_custom_historytextView);
        orderCustomIngbottom = (TextView) findViewById(R.id.order_custom_ingbottom);
        orderCustomHistorybottom = (TextView) findViewById(R.id.order_custom_historybottom);
        mViewPager = (ViewPager) findViewById(R.id.order_custom_viewPager);
    }

    private void initViewPager() {
        fragments = new ArrayList<Fragment>();
        Fragment customOrderIngFragment = new CustomOrderIngFragment();
        Fragment customOrderHistoryFragment = new CustomOrderHistoryFragment();
        fragments.add(customOrderIngFragment);
        fragments.add(customOrderHistoryFragment);
        mFragmentManager = getSupportFragmentManager();
        mOrderFragmentAdapter = new OrderFragmentAdapter(mFragmentManager,fragments);
        mViewPager.setAdapter(mOrderFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动时设置监听
            }

            @Override
            public void onPageSelected(int position) {
                //选择时
                if (position == 0){
                    orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                    orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                    orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_textView));
                    orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                }else if (position == 1){
                    orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_textView));
                    orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                    orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                    orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //更改滑动状态时
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            /*case R.id.customorder_return:
                finish();
                break;*/
            case R.id.order_custom_ingtextView:
                orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_textView));
                orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.order_custom_historytextView:
                orderCustomIngtextView.setTextColor(getResources().getColor(R.color.order_textView));
                orderCustomIngbottom.setBackgroundColor(getResources().getColor(R.color.order_background));
                orderCustomHistorytextView.setTextColor(getResources().getColor(R.color.order_background_bottom));
                orderCustomHistorybottom.setBackgroundColor(getResources().getColor(R.color.order_background_bottom));
                mViewPager.setCurrentItem(1);
                break;
            case R.id.order_custom_ingbottom:
                break;
            case R.id.order_custom_historybottom:
                break;
        }

    }
}
