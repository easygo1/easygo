package com.easygo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.easygo.activity.BookActivity;
import com.easygo.activity.HomeCityActivity;
import com.easygo.activity.HouseDetailActivity;
import com.easygo.activity.OrderDetailActivity;
import com.easygo.activity.R;
import com.easygo.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by PengHong on 2016/4/29.
 */
public class HomeFragment extends Fragment {
    //第一步：数据源
    //广告的图片
    private int[] advertImages = new int[]{
            R.drawable.advert1,
            R.drawable.advert2,
            R.drawable.advert3,
    };
    //热门城市的图片
    private int[] cityImages = new int[]{
            R.drawable.home_city1,
            R.drawable.home_city2,
            R.drawable.home_city3,
    };
    //热门城市的图片
    private int[] hotImages = new int[]{
            R.drawable.home_hot1,
            R.drawable.home_hot2,
            R.drawable.home_hot3,
    };
    //本地生活的图片
    private int[] localImages = new int[]{
            R.drawable.home_local1,
            R.drawable.home_local2,
            R.drawable.home_local3,
    };
    //第二步：确定viewpager布局，这里直接在主界面声明viewpager即可
    ViewPager mCityViewPager;
    ViewPager mAdvertViewPager;
    ViewPager mHotViewPager;
    ViewPager mLocalViewPager;
    //广告、热门城市、热门房源的list
    List<ImageView> mHomePageCityList;
    List<ImageView> mHomePageAdvertList;
    List<ImageView> mHomePageHotList;
    List<ImageView> mHomePageLocalList;
    //第三步：确定适配器，这里采用PagerAdapter

    //得到绑定的页面布局
    View mView;
    //自动轮播使用的服务
    private ScheduledExecutorService scheduledExecutorService;
    //自动播放时使用的变量
    private int currentItem;
   /* //黑点的集合
    private List<View> dots;
    //记录上一次点的位置
    private int oldPosition = 0;*/

    private AMapLocationClient mLocationClient;
    private TextView mLocationTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bottom_homepage, null);
        initViews();
        //广告适配器初始化
        initHomePagerAdvert();
        //城市适配器初始化
        initHomePagerCity();
        //热门房源适配器初始化
        initHomePagerHot();
        //本地生活适配器初始化
        initHomePagerLocal();
        //初始化位置
        initLocation();
        return mView;
    }

    private void initViews() {
        mAdvertViewPager = (ViewPager) mView.findViewById(R.id.homepage_advert_viewpager);
        mCityViewPager = (ViewPager) mView.findViewById(R.id.homepage_city_viewpager);
        mHotViewPager = (ViewPager) mView.findViewById(R.id.homepage_hot_viewpager);
        mLocalViewPager = (ViewPager) mView.findViewById(R.id.homepage_local_viewpager);
        mLocationTextView= (TextView) mView.findViewById(R.id.location_my);
    }


    private void initHomePagerAdvert() {
        //显示的小点
       /* dots = new ArrayList<View>();
        dots.add(getActivity().findViewById(R.id.dot_0));
        dots.add(getActivity().findViewById(R.id.dot_1));
        dots.add(getActivity().findViewById(R.id.dot_2));*/
        //广告轮播图
        mHomePageAdvertList = new ArrayList<>();
        for (int i = 0; i < advertImages.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(advertImages[i]);
            mHomePageAdvertList.add(imageView);
        }

        //广告滑动图的适配器
        mAdvertViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mHomePageAdvertList.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mHomePageAdvertList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mHomePageAdvertList.get(position));

                //监听
                mHomePageAdvertList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BookActivity.class);
                        startActivity(intent);

                    }
                });

                return mHomePageAdvertList.get(position);
            }
        });

    }

    private void initHomePagerCity() {
        //显示的图片
        mHomePageCityList = new ArrayList<>();
        for (int i = 0; i < cityImages.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(cityImages[i]);
            mHomePageCityList.add(imageView);
        }

        //城市滑动图的适配器
        mCityViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return mHomePageCityList.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mHomePageCityList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(mHomePageCityList.get(position));
                //监听
                mHomePageCityList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), HomeCityActivity.class);
                        startActivity(intent);
//                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                    }
                });
                return mHomePageCityList.get(position);
            }
        });
        //滑动监听
        mCityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initHomePagerHot() {
        //显示的图片
        mHomePageHotList = new ArrayList<>();
        for (int i = 0; i < hotImages.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(hotImages[i]);
            mHomePageHotList.add(imageView);
        }

        //热门房源滑动图的适配器
        mHotViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mHomePageHotList.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mHomePageHotList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(mHomePageHotList.get(position));

                //测试使用，跳转到具体房源页面
                mHomePageHotList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), HouseDetailActivity.class);
                        startActivity(intent);
                    }
                });

                return mHomePageHotList.get(position);
            }

        });

    }

    private void initHomePagerLocal() {
        //显示的图片
        mHomePageLocalList = new ArrayList<>();
        for (int i = 0; i < localImages.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(localImages[i]);
            mHomePageLocalList.add(imageView);
        }

        //城市滑动图的适配器
        mLocalViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mHomePageLocalList.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mHomePageLocalList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(mHomePageLocalList.get(position));

                //测试使用，跳转到具体房源页面
                mHomePageLocalList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                        startActivity(intent);
                    }
                });
                return mHomePageLocalList.get(position);
            }
        });
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(getActivity());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        mLocationTextView.setText(location);
                    }
                }
            }
        });
        mLocationClient.startLocation();

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % advertImages.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mAdvertViewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }
}
