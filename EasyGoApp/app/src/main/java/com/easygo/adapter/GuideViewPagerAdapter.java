package com.easygo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/6/12.
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    //用来承载所有的views
    private List<View> views;
    private Context context;

    public GuideViewPagerAdapter(List<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    //view不用时进行销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position));

        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    //判断当前的view是不是当前的对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
