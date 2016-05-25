package com.easygo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.easygo.activity.R;
import com.easygo.beans.HouseAssess;

import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailAssessFragment extends Fragment{
    List<HouseAssess> mHouseAssessList;
    View mAssessView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAssessView=inflater.inflate(R.layout.house_detail_assess,null);
        initViews();
        return mAssessView;
    }

    private void initViews() {
        ListView listView= (ListView) mAssessView.findViewById(R.id.assess_listview);

    }
}
