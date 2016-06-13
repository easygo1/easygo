package com.easygo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.easygo.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王韶辉 on 2016/6/12.
 */
public class Guide extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private GuideViewPagerAdapter vpAdapter;
    private List<View> views;
    private Button start_btn;
    //为点创建集合
    private ImageView[] dots;
    private int[] ids={R.id.iv1,R.id.iv2,R.id.iv3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initView();
        initDots();
    }

    private void initDots() {
        dots=new ImageView[views.size()];
        for(int i=0;i<views.size();i++){
            dots[i]= (ImageView) findViewById(ids[i]);
        }
    }

    private void initView() {
        //加载三个额外的view
        LayoutInflater inflater=LayoutInflater.from(this);

        views=new ArrayList<View>();
        //加载资源
        views.add(inflater.inflate(R.layout.one,null));
        views.add(inflater.inflate(R.layout.two,null));
        views.add(inflater.inflate(R.layout.three,null));
        vpAdapter=new GuideViewPagerAdapter(views,this);
        vp= (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        start_btn= (Button) views.get(2).findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Guide.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //页面被滑动时调用
    }

    @Override
    public void onPageSelected(int position) {
        //选中时调用
        for (int i=0;i<ids.length;i++){
            if(position==i){
                //将图片换位亮的
                dots[i].setImageResource(R.drawable.dot_focused1);
            }else{
                //将图片换位暗的
                dots[i].setImageResource(R.drawable.dot_normal1);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //当滑动状态改变时调用
    }
}
