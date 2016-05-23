package com.easygo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zzia on 2016/5/21.
 */
public class MyViewpager extends ViewPager {
    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /*return super.onTouchEvent(ev);*/
        return false;
    }
}
