package com.easygo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.Order;

import java.util.List;

/**
 * Created by 崔凯 on 2016/5/25.
 */
public class CustomOrderAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<Order> mList;

    public CustomOrderAdapter(Context context, List<Order> list) {
        mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
        final Order order = mList.get(position);
        viewHolder.orderTitle.setText(order.getTitle());
        viewHolder.orderState.setText(order.getState());
        viewHolder.orderChecktime.setText(order.getChecktime());
        viewHolder.orderLeavetime.setText(order.getLeavetime());
        viewHolder.orderSumtime.setText(order.getSumtime());
        viewHolder.orderRoomtype.setText(order.getType());
        viewHolder.orderTotal.setText(order.getTotal()+"");
        viewHolder.orderImageView.setImageResource(order.getImage());

        return convertView;
    }
}
