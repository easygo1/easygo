package com.easygo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.activity.CalendarActivity;
import com.easygo.activity.FootPrintActivity;
import com.easygo.activity.HouseCollectionActivity;
import com.easygo.activity.R;
import com.easygo.activity.SelectCityActivity;


/**
 * Created by PengHong on 2016/4/29.
 */
public class SearchFragment extends Fragment{
    View mSearchView;
    View mSearchCityView,mFootPrintView,mCollectionView,mDateSelectView;
    Button mSearchButton;

    private TextView tv_in,tv_out, mSearchTextView;
    SharedPreferences sp;
    String inday,outday;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSearchView=inflater.inflate(R.layout.bottom_search, null);
        initView();
        initData();
        initListener();
        return mSearchView;
    }

    private void initData() {
        Intent intent=getActivity().getIntent();
        String city=intent.getStringExtra("city");
        Toast.makeText(getActivity(),city,Toast.LENGTH_SHORT).show();
        mSearchTextView.setText(city);
    }

    private void initListener() {
        final Intent intent = new Intent();
        mSearchCityView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getActivity(),"点击了搜索城市",Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(),SelectCityActivity.class);
                startActivity(intent);
                return false;
            }
        });
        mDateSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"选择入住时间",Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(),CalendarActivity.class);
                startActivity(intent);
            }
        });
        mFootPrintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了历史足迹",Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(),FootPrintActivity.class);
                startActivity(intent);
            }
        });

        mCollectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了我的收藏",Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(),HouseCollectionActivity.class);
                startActivity(intent);
            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView() {
        mSearchCityView=mSearchView.findViewById(R.id.city_search);
        mDateSelectView=mSearchView.findViewById(R.id.time_search);
        mSearchTextView = (TextView) mSearchView.findViewById(R.id.search_texttext);
        mFootPrintView=mSearchView.findViewById(R.id.search_footpoint);
        mCollectionView=mSearchView.findViewById(R.id.search_collection);
        mSearchButton= (Button) mSearchView.findViewById(R.id.search_btn);
        //初始化日历控件
        sp=getActivity().getSharedPreferences("date",Context.MODE_PRIVATE);

        tv_in=(TextView)mSearchView. findViewById(R.id.search_check_time);
        tv_out=(TextView) mSearchView.findViewById(R.id.search_leave_time);
    }

    @Override
    public void onStart() {
        super.onStart();
        inday=sp.getString("dateIn", "");
        outday=sp.getString("dateOut", "");
        if(!"".equals(inday)){
            tv_in.setText(inday);
        }
        if(!"".equals(outday)){
            tv_out.setText(outday);
        }
    }

}
