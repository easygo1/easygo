package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easygo.activity.R;

import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseEquipmentAdpter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<String> mList;

    public HouseEquipmentAdpter(List<String> list, Context context) {

        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size() / 2 + mList.size() % 2;
        }
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
    class ViewHolder {
        TextView textView1,textView2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //找到每一行的布局
        if (convertView == null) {
            //说明是第一次绘制整屏列表
            convertView = mInflater.inflate(R.layout.house_detail_equipment_item, null);//第二个参数代表依赖的父布局
            viewHolder = new ViewHolder();//缓存控件
            //初始化当前布局中的所有控件
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.equipitem1);
           viewHolder.textView2 = (TextView) convertView.findViewById(R.id.equipitem2);

            //把当前的控件缓存到当前布局视图中去
            convertView.setTag(viewHolder);
        } else {
            //说明刚开始滑动，后面的所有行布局采用第一次绘制时的布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView1.setText(mList.get(position));
        viewHolder.textView2.setText(mList.get(mList.size()-1-position));
        return convertView;
    }
}
