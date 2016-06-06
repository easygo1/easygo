package com.easygo.adapter;


import android.content.Context;
import android.content.SyncStatusObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easygo.activity.R;
import com.easygo.beans.gson.GsonOrderInfo;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
import com.easygo.utils.DaysUtil;

import java.util.List;

/**
 * Created by 崔凯 on 2016/5/25.
 */
public class CustomOrderAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<Orders> mOrdersList = null;
    List<House> mHouselist = null;
    List<HousePhoto> mHousePhotoList = null;

    public CustomOrderAdapter(Context context, List<Orders> ordersList, List<House> houselist, List<HousePhoto> housePhotoList) {
        mContext = context;
        this.mOrdersList = ordersList;
        this.mHouselist = houselist;
        this.mHousePhotoList = housePhotoList;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mOrdersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrdersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //缓存布局中的控件
    class ViewHolder {
        TextView orderTitle;
        TextView orderState;
        ImageView orderImageView;
        TextView orderChecktime;
        TextView orderLeavetime;
        TextView orderSumtime;
        TextView orderRoomtype;
        TextView orderTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.orderTitle = (TextView) convertView.findViewById(R.id.order_title);
            viewHolder.orderState = (TextView) convertView.findViewById(R.id.order_state);
            viewHolder.orderChecktime = (TextView) convertView.findViewById(R.id.order_checktime);
            viewHolder.orderLeavetime = (TextView) convertView.findViewById(R.id.order_leavetime);
            viewHolder.orderSumtime = (TextView) convertView.findViewById(R.id.order_sumtime);
            viewHolder.orderRoomtype = (TextView) convertView.findViewById(R.id.order_roomtype);
            viewHolder.orderTotal = (TextView) convertView.findViewById(R.id.order_total);
            viewHolder.orderImageView = (ImageView) convertView.findViewById(R.id.order_imageView);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //动态修改每一行的控件的内容
        //final GsonOrderInfo gsonOrderInfo = mGsonOrderInfoList.get(position);
        int days = DaysUtil.getDays(mOrdersList.get(position).getChecktime(), mOrdersList.get(position).getLeavetime());
        double money = days * (mHouselist.get(position).getHouse_one_price() + (mHouselist.get(position).getHouse_add_price() * (mOrdersList.get(position).getChecknum() - 1)));
        viewHolder.orderTitle.setText(mHouselist.get(position).getHouse_title());
        viewHolder.orderState.setText(mOrdersList.get(position).getOrder_state());
        viewHolder.orderChecktime.setText(mOrdersList.get(position).getChecktime());
        viewHolder.orderLeavetime.setText(mOrdersList.get(position).getLeavetime());
        viewHolder.orderSumtime.setText("共" + days + "晚");
        viewHolder.orderRoomtype.setText(mHouselist.get(position).getHouse_style());
        viewHolder.orderTotal.setText(money + "");
        //viewHolder.orderImageView.setImageResource(order.getImage());
        Glide.with(mContext).load(mHousePhotoList.get(position).getHouse_photo_path()).into(viewHolder.orderImageView);

        return convertView;
    }


}
