package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.House;
import com.easygo.beans.Music;

import java.util.List;

/**
 * Created by Gemptc on 2016/4/27.
 */
public class HomeCityAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<House> mList;

    public HomeCityAdapter(Context context, List<House> list) {
        mContext = context;
        this.mList = list;
        mInflater=LayoutInflater.from(mContext);
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
    class ViewHolder{

        ImageView mImageView;
        TextView text_info;
        TextView text_price;
        TextView text_describe;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.activity_home_city_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_room_image);
            viewHolder.text_info = (TextView) convertView.findViewById(R.id.homepage_city_item_text_info);
            viewHolder.text_describe = (TextView) convertView.findViewById(R.id.homepage_city_item_text_describe);
            viewHolder.text_price = (TextView) convertView.findViewById(R.id.homepage_city_item_text_price);

            convertView.setTag(viewHolder);
        }
        else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final House house = mList.get(position);
        viewHolder.text_info.setText(house.getHouse_style());
        viewHolder.text_price.setText(house.getHouse_one_price()+"");
        viewHolder.text_describe.setText(house.getHouse_describe());
        viewHolder.mImageView.setImageResource(R.drawable.home_city_room1);
        return convertView;
    }
}
