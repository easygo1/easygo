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

import java.util.List;


public class HouseListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<House> mList;

    public HouseListAdapter(Context context, List<House> list) {
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
        ImageView mCollectionImageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.house_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mRoomImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_room_image);
            viewHolder.text_info = (TextView) convertView.findViewById(R.id.homepage_city_item_text_info);
            viewHolder.text_describe = (TextView) convertView.findViewById(R.id.homepage_city_item_text_describe);
            viewHolder.text_price = (TextView) convertView.findViewById(R.id.homepage_city_item_text_price);
            //viewHolder.mCollectionImageView= (ImageView) convertView.findViewById(R.id.homepage_city_item_collect_image);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //得到房屋的一个对象
        final House house = mList.get(position);

        viewHolder.text_info.setText(house.getHouse_style() + house.getHouse_title());
        viewHolder.text_price.setText(house.getHouse_one_price() + "");
        viewHolder.text_describe.setText(house.getHouse_describe());
        viewHolder.mRoomImageView.setImageResource(R.drawable.home_city_room1);
        final ImageView mCollectionImageView= (ImageView) convertView.findViewById(R.id.homepage_city_item_collect_image);
        //未解决。逻辑似乎没错。点击无反应
        //解决收藏混乱问题
        mCollectionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一开始是未选中，点击之后换为选中
                //说明用户之前未收藏。现在想收藏
                if (!mList.get(position).isCollected()) {
                    //此处之前为false
                    mCollectionImageView.setImageResource(R.mipmap.icon_collect_on);
                    mList.get(position).setCollected(true);
                   // Log.e("收藏","点击了"+mList.get(position).isCollected());
                    //house.setCollected(true);
                } else if(mList.get(position).isCollected()){
                    //此处之前为false
                    //说明用户之前收藏过,再次点击则取消
                    mCollectionImageView.setImageResource(R.mipmap.icon_collect_blue);
                    mList.get(position).setCollected(false);
                    //Log.e("收藏","点击了"+mList.get(position).isCollected());
//                    house.setCollected(false);
                }
            }
        });

        //重置每一行Collected状态，必须放到监听事件后面
        boolean isCollected = house.isCollected();
        if (isCollected) {
           mCollectionImageView.setImageResource(R.mipmap.icon_collect_on);
        } else {
            mCollectionImageView.setImageResource(R.mipmap.icon_collect_blue);
        }
        return convertView;
    }
}
