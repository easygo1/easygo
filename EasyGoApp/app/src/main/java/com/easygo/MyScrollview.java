package com.easygo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zzia on 2016/5/20.
 */
public class MyScrollview extends ScrollView{
    GestureDetector gestureDetector;
    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(new Yscroll());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*return super.onInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);*/
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /*return super.onTouchEvent(ev);*/
        return false;
    }

    class Yscroll extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            //控制手指滑动的距离
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

}
