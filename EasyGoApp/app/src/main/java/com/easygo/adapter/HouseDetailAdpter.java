package com.easygo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailAdpter extends FragmentPagerAdapter {
    //子类的构造方法肯定会调用父类的构造方法，若子类没有显示调用，则系统默认调用父类的无参构造方法
    //如果父类没有无参构造方法，子类必须显示调用父类的有参构造方法
    List<Fragment> mList;
    public HouseDetailAdpter(FragmentManager fragmentManager,List<Fragment> list) {
        super(fragmentManager);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
