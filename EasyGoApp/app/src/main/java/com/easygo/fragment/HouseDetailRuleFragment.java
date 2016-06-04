package com.easygo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easygo.activity.DataShowActivity;
import com.easygo.activity.R;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailRuleFragment extends Fragment implements View.OnClickListener {
    Button mDateButton;
    View mView;

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
    }
    private void addListener() {
        mDateButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house_detail_rule_date:
                Intent intent = new Intent(getActivity(), DataShowActivity.class);
                startActivity(intent);
                break;
        }
    }
}
