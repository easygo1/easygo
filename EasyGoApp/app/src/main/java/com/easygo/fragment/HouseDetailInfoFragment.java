package com.easygo.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.activity.HouseDetailActivity;
import com.easygo.activity.R;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HouseEquipmentName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailInfoFragment extends Fragment implements OnClickListener {
    ListView mEquipmentListView;
    View mHouseContentView;
    private TextView houseDetailDescribe, houseDetailStyle, houseDetailSex,
            houseDetailStayTime, houseDetailMostNum, houseDetailAddPrice,
            mEquipTextView1, mEquipTextView2, mEquipTextView3, mEquipTextView4,
            mShowmoreTextView;
    List<HouseEquipmentName> mShowMoreList;
    //得到房源
    House mHouse;
    Bundle mBundle;
    //根据房源编号获取设施
    List<String> mEquipmentList;
    //用于复选框
    boolean[] flags = new boolean[]{false, false, false, false, false, false, false, false, false, false, false};

    //显示数据需要使用到的控件
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHouseContentView = inflater.inflate(R.layout.fragment_info_scroll, null);
        initViews();
//        initData();
//        initHouseinfoView();
        initListener();
        return mHouseContentView;
    }

    private void initViews() {
        houseDetailDescribe = (TextView) mHouseContentView.findViewById(R.id.house_detail_describe);
        houseDetailStyle = (TextView) mHouseContentView.findViewById(R.id.house_detail_style);
        houseDetailSex = (TextView) mHouseContentView.findViewById(R.id.house_detail_sex);
        houseDetailStayTime = (TextView) mHouseContentView.findViewById(R.id.house_detail_stay_time);
        houseDetailMostNum = (TextView) mHouseContentView.findViewById(R.id.house_detail_most_num);
        houseDetailAddPrice = (TextView) mHouseContentView.findViewById(R.id.house_detail_add_price);

        mEquipTextView1 = (TextView) mHouseContentView.findViewById(R.id.house_detail_equipment1);
        mEquipTextView2 = (TextView) mHouseContentView.findViewById(R.id.house_detail_equipment2);
        mEquipTextView3 = (TextView) mHouseContentView.findViewById(R.id.house_detail_equipment3);
        mEquipTextView4 = (TextView) mHouseContentView.findViewById(R.id.house_detail_equipment4);
        mShowmoreTextView = (TextView) mHouseContentView.findViewById(R.id.house_detail_showmore);
    }

    public void initInfoData(House house, List<HouseEquipmentName> hemList) {
        houseDetailDescribe.setText(house.getHouse_describe());
        houseDetailStyle.setText(house.getHouse_style());
        houseDetailSex.setText(house.getHouse_limit_sex());
        houseDetailStayTime.setText(house.getHouse_stay_time() + "");
        houseDetailMostNum.setText(house.getHouse_most_num() + "");
        houseDetailAddPrice.setText(house.getHouse_add_price() + "");
//        Log.e("666", hemList.get(0).getEquipment_name());
        if (hemList.size() == 1) {
            mEquipTextView1.setText(hemList.get(0).getEquipment_name());
            mEquipTextView2.setVisibility(View.GONE);
            mEquipTextView3.setVisibility(View.GONE);
            mEquipTextView4.setVisibility(View.GONE);
        } else if (hemList.size() == 2) {
            mEquipTextView1.setText(hemList.get(0).getEquipment_name());
            mEquipTextView2.setText(hemList.get(1).getEquipment_name());
            mEquipTextView3.setVisibility(View.GONE);
            mEquipTextView4.setVisibility(View.GONE);
        } else if (hemList.size() == 3) {
            mEquipTextView1.setText(hemList.get(0).getEquipment_name());
            mEquipTextView2.setText(hemList.get(1).getEquipment_name());
            mEquipTextView3.setText(hemList.get(2).getEquipment_name());
            mEquipTextView4.setVisibility(View.GONE);
        } else if (hemList.size() >= 4) {
            mEquipTextView1.setText(hemList.get(0).getEquipment_name());
            mEquipTextView2.setText(hemList.get(1).getEquipment_name());
            mEquipTextView3.setText(hemList.get(2).getEquipment_name());
            mEquipTextView4.setText(hemList.get(3).getEquipment_name());
        } else if (hemList.size() == 0) {
            mEquipTextView1.setVisibility(View.GONE);
            mEquipTextView2.setVisibility(View.GONE);
            mEquipTextView3.setVisibility(View.GONE);
            mEquipTextView4.setVisibility(View.GONE);
        }
        //mShowMoreList初始化
        mShowMoreList = new ArrayList<>();
        mShowMoreList.addAll(hemList);


    }

    private void initListener() {
        mShowmoreTextView.setOnClickListener(this);
    }


    private void initHouseinfoView() {
//        mHouseLocationView=mHouseContentView.findViewById(R.id.house_location);
        //  mEquipmentListView= (ListView) mHouseContentView.findViewById(R.id.house_equipment_listview);
//        mEquipmentListView.setAdapter(new HouseEquipmentAdpter(mEquipmentList,getActivity()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.house_detail_showmore:
                //查看更多
                showMore();
                break;
        }
    }

    public void showMore() {
        if (mShowMoreList.size() == 0) {
            Toast.makeText(getActivity(), "暂时没有更多设施~", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < mShowMoreList.size(); i++) {
            if (mShowMoreList.get(i).getEquipment_name().equals("无线网络")) {
                flags[0] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("卫生间")) {
                flags[1] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("空调")) {
                flags[2] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("洗漱用品")) {
                flags[3] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("洗衣机")) {
                flags[4] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("厨房")) {
                flags[5] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("电视")) {
                flags[6] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("微波炉")) {
                flags[7] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("吹风机")) {
                flags[8] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("停车位")) {
                flags[9] = true;
            } else if (mShowMoreList.get(i).getEquipment_name().equals("烘干机")) {
                flags[10] = true;
            }
        }
        final android.app.AlertDialog.Builder builder
                = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("便利设施");
        builder.setMultiChoiceItems(R.array.equipment, flags,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        return;
                    }
                });


        //添加一个确定按钮
        builder.setPositiveButton(" 返 回 ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }


}
