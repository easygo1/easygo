package com.easygo.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.easygo.fragment.OwnerOrderBookMeFragment;
import com.easygo.fragment.OwnerOrderIBookFragment;

public class OwnerOrderActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ownerorderReturn;
    private Button ownerOrderIbook;
    private Button ownerOrderBookme;
    private FrameLayout ownerOrderFragment;
    private OwnerOrderIBookFragment mOwnerOrderIBookFragment;
    private OwnerOrderBookMeFragment mOwnerOrderBookMeFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_order);
        initViews();
        addListeners();
        //默认显示我预定的碎片
        initDefault();

    }

    private void initDefault() {
        //开始先显示第一个界面
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //默认显示预订我的模块，初始化预订我的碎片
        mOwnerOrderBookMeFragment = new OwnerOrderBookMeFragment();
        mFragmentTransaction.add(R.id.owner_order_fragment, mOwnerOrderBookMeFragment,"bookme");
        mFragmentTransaction.commit();
        ownerOrderBookme.setBackgroundResource(R.drawable.owner_order_bookmebtn2);
        ownerOrderBookme.setTextColor(getResources().getColor(R.color.order_white));
        ownerOrderIbook.setBackgroundResource(R.drawable.owner_order_ibookbtn);
        ownerOrderIbook.setTextColor(getResources().getColor(R.color.order_yellow));
    }

    private void addListeners() {
        ownerorderReturn.setOnClickListener(this);
        ownerOrderIbook.setOnClickListener(this);
        ownerOrderBookme.setOnClickListener(this);
    }

    private void initViews() {
        ownerorderReturn = (ImageView) findViewById(R.id.owner_order_return);
        ownerOrderIbook = (Button) findViewById(R.id.owner_order_ibook);
        ownerOrderBookme = (Button) findViewById(R.id.owner_order_bookme);
        ownerOrderFragment = (FrameLayout) findViewById(R.id.owner_order_fragment);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.owner_order_return:
                finish();
                break;
            //点击我预定的时候
            case R.id.owner_order_ibook:
                ownerOrderIbook.setBackgroundResource(R.drawable.owner_order_ibookbtn2);
                ownerOrderIbook.setTextColor(getResources().getColor(R.color.order_white));
                ownerOrderBookme.setBackgroundResource(R.drawable.owner_order_bookmebtn);
                ownerOrderBookme.setTextColor(getResources().getColor(R.color.order_yellow));
                //设置当前碎片为我预定的碎片
                initFragment(R.id.owner_order_ibook);
                break;
            //点击预订我的时候
            case R.id.owner_order_bookme:
                ownerOrderBookme.setBackgroundResource(R.drawable.owner_order_bookmebtn2);
                ownerOrderBookme.setTextColor(getResources().getColor(R.color.order_white));
                ownerOrderIbook.setBackgroundResource(R.drawable.owner_order_ibookbtn);
                ownerOrderIbook.setTextColor(getResources().getColor(R.color.order_yellow));
                //设置当前碎片预订我的的碎片
                initFragment(R.id.owner_order_bookme);
                break;
        }
    }
    private void initFragment(int id) {
        //隐藏所有已经添加到事务中的碎片
        hideChatFragments();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //判断当前碎片是哪一个
        switch (id) {
            case R.id.owner_order_ibook:
                if(mOwnerOrderIBookFragment == null){
                    mOwnerOrderIBookFragment = new OwnerOrderIBookFragment();
                    mFragmentTransaction.add(R.id.owner_order_fragment,mOwnerOrderIBookFragment,"ibook");
                }else{
                    mFragmentTransaction.show(mOwnerOrderIBookFragment);
                }
                break;
            case R.id.owner_order_bookme:
                if(mOwnerOrderBookMeFragment == null){
                    mOwnerOrderBookMeFragment = new OwnerOrderBookMeFragment();
                    mFragmentTransaction.add(R.id.owner_order_fragment,mOwnerOrderBookMeFragment,"bookme");
                }else{
                    mFragmentTransaction.show(mOwnerOrderBookMeFragment);
                }
                break;
        }
        mFragmentTransaction.commit();
    }
    //隐藏所有的碎片，首先需要知道碎片是否已经添加到事务中
    private void hideChatFragments() {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(mOwnerOrderIBookFragment!=null && mOwnerOrderIBookFragment.isAdded()){
            mFragmentTransaction.hide(mOwnerOrderIBookFragment);
        }
        if(mOwnerOrderBookMeFragment!=null && mOwnerOrderBookMeFragment.isAdded()){
            mFragmentTransaction.hide(mOwnerOrderBookMeFragment);
        }
        mFragmentTransaction.commit();
    }
}
