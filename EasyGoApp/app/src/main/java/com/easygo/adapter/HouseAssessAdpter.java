package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easygo.beans.HouseAssess;

import java.util.List;

/**
 * Created by zzia on 2016/5/24.
 */
public class HouseAssessAdpter extends BaseAdapter {
    List<HouseAssess> mHouseAssessList;
    Context mContext;
    LayoutInflater mInflater;

    public HouseAssessAdpter(Context context, List<HouseAssess> houseAssessList) {
        mContext = context;
        mHouseAssessList = houseAssessList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mHouseAssessList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHouseAssessList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}
