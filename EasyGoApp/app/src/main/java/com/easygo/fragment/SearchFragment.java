package com.easygo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.easygo.activity.R;
import com.easygo.activity.SelectCityActivity;


/**
 * Created by PengHong on 2016/4/29.
 */
public class SearchFragment extends Fragment {
    View mSearchView;
    View mSearchCityView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSearchView=inflater.inflate(R.layout.bottom_search, null);
        initView();
        initListener();
        return mSearchView;
    }

    private void initListener() {
        mSearchCityView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getActivity(),"点击了搜索城市",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(),SelectCityActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    private void initView() {
        mSearchCityView=mSearchView.findViewById(R.id.city_search);
    }
}
