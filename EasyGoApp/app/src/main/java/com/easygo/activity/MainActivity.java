package com.easygo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.fragment.ChatFragment;
import com.easygo.fragment.HomeFragment;
import com.easygo.fragment.MeFragment;
import com.easygo.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout mAllLayout, mHomeLayout, mSearchLayout, mPlusLayout, mChatLayout, mMeLayout;
    ImageView mHomeImageView, mSearchImageView, mChatImageView, mMeImageView;
    TextView mHomeTextView, mSearchTextView, mChatTextView, mMeTextView;
    Toast mToast;

    //碎片
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    HomeFragment mHomeFragment;
    SearchFragment mSearchFragment;
    ChatFragment mChatFragment;
    MeFragment mMeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        addListeners();
        //默认显示买模块，修改图标和文字颜色为选中颜色
        initDefault();
    }

    private void initDefault() {

        Intent intent = getIntent();
        String flag = null;
        flag = intent.getStringExtra("flag");
        if (flag == null) {
            //开始先显示第一个界面
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            //默认显示买模块，初始化买碎片
            mHomeFragment = new HomeFragment();
            mFragmentTransaction.add(R.id.middle, mHomeFragment);
            mFragmentTransaction.commit();

        } else if (flag.equals("me")) {
            //如果接收到标志位flag=me,显示我的界面
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mMeFragment = new MeFragment();
            mFragmentTransaction.add(R.id.middle, mMeFragment);
            mFragmentTransaction.commit();
            //还原图标和文本颜色
            reset();
            //得到选中的id
            int id = R.id.me;
            //设置当前选中图标和文本颜色
            setImageAndTextColor(id);
            //设置当前选中碎片
            initCurrentFragment(id);
        }else if (flag.equals("search")) {
            //如果接收到标志位flag=search,显示搜索界面
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mSearchFragment = new SearchFragment();
            mFragmentTransaction.add(R.id.middle, mSearchFragment);
            mFragmentTransaction.commit();
            //还原图标和文本颜色
            reset();
            //得到选中的id
            int id = R.id.search;
            //设置当前选中图标和文本颜色
            setImageAndTextColor(id);
            //设置当前选中碎片
            initCurrentFragment(id);
        }
    }

    private void addListeners() {
        mHomeLayout.setOnClickListener(this);
        mSearchLayout.setOnClickListener(this);
        mChatLayout.setOnClickListener(this);
        mMeLayout.setOnClickListener(this);
        mPlusLayout.setOnClickListener(this);
    }

    private void initViews() {
        mAllLayout = (LinearLayout) findViewById(R.id.all);
        mHomeLayout = (LinearLayout) findViewById(R.id.homepage);
        mSearchLayout = (LinearLayout) findViewById(R.id.search);
        mPlusLayout = (LinearLayout) findViewById(R.id.plus);
        mChatLayout = (LinearLayout) findViewById(R.id.chat);
        mMeLayout = (LinearLayout) findViewById(R.id.me);

        mHomeImageView = (ImageView) findViewById(R.id.homepage_imageview);
        mSearchImageView = (ImageView) findViewById(R.id.search_imageview);
        mChatImageView = (ImageView) findViewById(R.id.chat_imageview);
        mMeImageView = (ImageView) findViewById(R.id.me_imageview);

        mHomeTextView = (TextView) findViewById(R.id.homepage_text);
        mSearchTextView = (TextView) findViewById(R.id.search_text);
        mChatTextView = (TextView) findViewById(R.id.chat_text);
        mMeTextView = (TextView) findViewById(R.id.me_text);

    }

    @Override
    public void onClick(View v) {
        //如果不是附加按钮
        if (v.getId() != R.id.plus) {
            //还原图标和文本颜色
            reset();
            //得到选中的id
            int id = v.getId();
            //设置当前选中图标和文本颜色
            setImageAndTextColor(id);
            //设置当前选中碎片
            initCurrentFragment(id);
        } else {
            Intent plusIntent = new Intent(MainActivity.this, PlusActivity.class);
            startActivity(plusIntent);

            /*//如果是附加按钮
            //隐藏所有已经添加到事务中的碎片
            hideAllFragments();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mPlusFragment = new PlusFragment();
            mFragmentTransaction.add(R.id.all,mPlusFragment);

            mFragmentTransaction.commit();*/
        }

    }

    private void initCurrentFragment(int id) {
        //隐藏所有已经添加到事务中的碎片
        hideAllFragments();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        //判断当前碎片是哪一个
        switch (id) {
            case R.id.homepage:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    mFragmentTransaction.add(R.id.middle, mHomeFragment);
                } else {
                    mFragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.search:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                    mFragmentTransaction.add(R.id.middle, mSearchFragment);
                } else {
                    mFragmentTransaction.show(mSearchFragment);
                }
                break;
            case R.id.chat:
                if (mChatFragment == null) {
                    mChatFragment = new ChatFragment();
                    mFragmentTransaction.add(R.id.middle, mChatFragment);
                } else {
                    mFragmentTransaction.show(mChatFragment);
                }
                break;
            case R.id.me:
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                    mFragmentTransaction.add(R.id.middle, mMeFragment);
                } else {
                    mFragmentTransaction.show(mMeFragment);
                }
                break;
        }
        mFragmentTransaction.commit();
    }

    private void hideAllFragments() {
        //隐藏所有的碎片，首先需要知道碎片是否已经添加到事务中
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (mHomeFragment != null && mHomeFragment.isAdded()) {
            mFragmentTransaction.hide(mHomeFragment);
        }
        if (mSearchFragment != null && mSearchFragment.isAdded()) {
            mFragmentTransaction.hide(mSearchFragment);
        }
        if (mChatFragment != null && mChatFragment.isAdded()) {
            mFragmentTransaction.hide(mChatFragment);
        }
        if (mMeFragment != null && mMeFragment.isAdded()) {
            mFragmentTransaction.hide(mMeFragment);
        }
        mFragmentTransaction.commit();
    }

    private void reset() {
        //把底部导航栏的颜色还原为灰色
        mHomeImageView.setImageResource(R.mipmap.btn_home_page_disabled);
        mSearchImageView.setImageResource(R.mipmap.btn_search_disabled);
        mChatImageView.setImageResource(R.mipmap.btn_chat_disabled);
        mMeImageView.setImageResource(R.mipmap.btn_me_disabled);

        mHomeTextView.setTextColor(Color.GRAY);
        mSearchTextView.setTextColor(Color.GRAY);
        mChatTextView.setTextColor(Color.GRAY);
        mMeTextView.setTextColor(Color.GRAY);
    }

    public void setImageAndTextColor(int id) {
        //参数表示选中项线性布局的i
        switch (id) {
            case R.id.homepage:
                mHomeImageView.setImageResource(R.mipmap.btn_home_page_normal);
                mHomeTextView.setTextColor(getResources().getColor(R.color.textDown));
                break;
            case R.id.search:
                mSearchImageView.setImageResource(R.mipmap.btn_search_normal);
                mSearchTextView.setTextColor(getResources().getColor(R.color.textDown));
                break;
            case R.id.chat:
                mChatImageView.setImageResource(R.mipmap.btn_chat_normal);
                mChatTextView.setTextColor(getResources().getColor(R.color.textDown));
                break;
            case R.id.me:
                mMeImageView.setImageResource(R.mipmap.btn_me_normal);
                mMeTextView.setTextColor(getResources().getColor(R.color.textDown));
                break;
        }
    }

    public void show(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
        } else {
            //土司不为空，直接使用已有对象，修改提示内容
            mToast.setText(text);
        }
        mToast.show();

    }
}
