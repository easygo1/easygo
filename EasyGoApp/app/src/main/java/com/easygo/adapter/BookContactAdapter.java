package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.UserLinkman;

import java.util.List;

public class BookContactAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<UserLinkman> mList;

    public BookContactAdapter(Context context, List<UserLinkman> list) {
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

    class ViewHolder {
        ImageView mRoomImageView;
        TextView text_info;
        TextView text_price;
        TextView text_describe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.house_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mRoomImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_room_image);
            viewHolder.text_info = (TextView) convertView.findViewById(R.id.homepage_city_item_text_info);
            viewHolder.text_describe = (TextView) convertView.findViewById(R.id.homepage_city_item_text_describe);
            viewHolder.text_price = (TextView) convertView.findViewById(R.id.homepage_city_item_text_price);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
