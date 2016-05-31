package com.easygo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.easygo.activity.R;
import com.easygo.adapter.HouseAssessAdpter;
import com.easygo.beans.house.HouseAssess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailAssessFragment extends Fragment{
    List<HouseAssess> mHouseAssessList;
    View mAssessView;
    HouseAssessAdpter mHouseAssessAdpter;
    ListView mlistView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAssessView=inflater.inflate(R.layout.house_detail_assess,null);
        initViews();
        initData();
        return mAssessView;
    }

    private void initData() {
        mHouseAssessList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HouseAssess houseAssess=new HouseAssess("房客"+i,i/2,"还不错哦"+i,"2016-05-"+i,"欢迎下次来"+i);
            mHouseAssessList.add(houseAssess);
        }
        mHouseAssessAdpter=new HouseAssessAdpter(getActivity(),mHouseAssessList);
        mlistView.setAdapter(mHouseAssessAdpter);
    }

    private void initViews() {
        mlistView= (ListView) mAssessView.findViewById(R.id.assess_listview);
    }
}
