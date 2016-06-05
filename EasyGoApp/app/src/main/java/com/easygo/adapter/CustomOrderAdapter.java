package com.easygo.adapter;


import android.content.Context;
import android.content.SyncStatusObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.gson.GsonOrderInfo;
import com.easygo.utils.DaysUtil;

import java.util.List;

/**
 * Created by 崔凯 on 2016/5/25.
 */
public class CustomOrderAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<GsonOrderInfo> mGsonOrderInfoList;
    public CustomOrderAdapter(Context context, List<GsonOrderInfo> list) {
        mContext = context;
        this.mGsonOrderInfoList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mGsonOrderInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGsonOrderInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //缓存布局中的控件
    class ViewHolder{
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
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.order_list_item,null);
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
        }else{
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //动态修改每一行的控件的内容
        final GsonOrderInfo gsonOrderInfo = mGsonOrderInfoList.get(position);
        int days = DaysUtil.getDays(gsonOrderInfo.getOrders().getChecktime(),gsonOrderInfo.getOrders().getLeavetime());
        double money = days*(gsonOrderInfo.getHouse().getHouse_one_price()+(gsonOrderInfo.getHouse().getHouse_add_price()*(gsonOrderInfo.getOrders().getChecknum()-1)));
        viewHolder.orderTitle.setText(gsonOrderInfo.getHouse().getHouse_title());
        viewHolder.orderState.setText(gsonOrderInfo.getOrders().getOrder_state());
        viewHolder.orderChecktime.setText(gsonOrderInfo.getOrders().getChecktime());
        viewHolder.orderLeavetime.setText(gsonOrderInfo.getOrders().getLeavetime());
        viewHolder.orderSumtime.setText("共"+days+"晚");
        viewHolder.orderRoomtype.setText(gsonOrderInfo.getHouse().getHouse_style());
        viewHolder.orderTotal.setText(money+"");
        //viewHolder.orderImageView.setImageResource(order.getImage());

        gsonOrderInfo.getOrders().getOrder_state();

        return convertView;
    }


}
