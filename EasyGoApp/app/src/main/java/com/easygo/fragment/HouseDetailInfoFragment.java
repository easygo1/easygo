package com.easygo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.easygo.activity.R;

import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailInfoFragment extends Fragment{
    ListView mEquipmentListView;
    View mHouseContentView;
    //根据房源编号获取设施
    List<String> mEquipmentList;

    //显示数据需要使用到的控件
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHouseContentView=inflater.inflate(R.layout.fragment_info_scroll,null);
        initHouseinfoView();
        initData();
        return mHouseContentView;
    }



    private void initData() {
        /*mEquipmentList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mEquipmentList.add("设施"+i);
        }*/
    }

    private void initHouseinfoView() {
      //  mEquipmentListView= (ListView) mHouseContentView.findViewById(R.id.house_equipment_listview);
//        mEquipmentListView.setAdapter(new HouseEquipmentAdpter(mEquipmentList,getActivity()));

    }


}
