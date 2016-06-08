package com.easygo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easygo.activity.DateShowActivity;
import com.easygo.activity.MapActivity;
import com.easygo.activity.R;

/**
 * Created by zzia on 2016/5/20.
 * 房屋日期，地图
 */
public class HouseDetailRuleFragment extends Fragment implements View.OnClickListener {
    Button mDateButton;
    View mView, mHouseContentView, mHouseLocationView;
    Intent mIntent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_rule_scroll, null);
        initViews();
        addListener();
        return mView;
    }

    private void initViews() {
        mDateButton = (Button) mView.findViewById(R.id.house_detail_rule_date);
        mHouseLocationView = mView.findViewById(R.id.house_location);
    }

    private void addListener() {
        mDateButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house_detail_rule_date:
                mIntent = new Intent(getActivity(), DateShowActivity.class);
                startActivity(mIntent);
                break;
            case R.id.house_location:
                mIntent = new Intent();
                mIntent.setClass(getActivity(), MapActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
