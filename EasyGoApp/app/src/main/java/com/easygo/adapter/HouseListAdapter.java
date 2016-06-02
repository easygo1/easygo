package com.easygo.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easygo.activity.HomeCityActivity;
import com.easygo.activity.R;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HouseCollect;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Assess;
import com.easygo.beans.user.User;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class HouseListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<House> mHouseList = null;
    List<User> mUserList = null;
    List<HousePhoto> mHousePhotoList = null;
    List<Integer> mAssessList = null;
    List<HouseCollect> mHouseCollectList = null;
    HomeCityActivity mHomeCityActivity;


    public HouseListAdapter(Context context, List<House> mHouseList, List<User> mUserList,
                            List<HousePhoto> mHousePhotoList, List<Integer> mAssessListList,
                            List<HouseCollect> mHouseCollectList) {
        mContext = context;
        this.mHouseList = mHouseList;
        this.mUserList = mUserList;
        this.mHousePhotoList = mHousePhotoList;
        this.mAssessList = mAssessListList;
        this.mHouseCollectList = mHouseCollectList;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mHouseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHouseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        //房屋相关
        ImageView mRoomImageView;
        TextView text_info;
        TextView text_price;
        TextView text_describe;
        //用户相关
        ImageView mUserImageView;
        TextView text_username;
        RatingBar mRatingBar;
        TextView text_assess;
        //ImageView mCollectionImageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.house_list_item, null);
            viewHolder = new ViewHolder();
            //房屋
            viewHolder.mRoomImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_room_image);
            viewHolder.text_info = (TextView) convertView.findViewById(R.id.homepage_city_item_text_info);
            viewHolder.text_describe = (TextView) convertView.findViewById(R.id.homepage_city_item_text_describe);
            viewHolder.text_price = (TextView) convertView.findViewById(R.id.homepage_city_item_text_price);
            //用户
            viewHolder.mUserImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_head_image);
            viewHolder.text_username = (TextView) convertView.findViewById(R.id.homepage_city_item_text_name);
            viewHolder.mRatingBar = (RatingBar) convertView.findViewById(R.id.homepage_city_item_ratingBar);
            viewHolder.text_assess = (TextView) convertView.findViewById(R.id.homepage_city_item_assess);
            //viewHolder.mCollectionImageView= (ImageView) convertView.findViewById(R.id.homepage_city_item_collect_image);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //得到房屋的一个对象
        final House house = mHouseList.get(position);
        HousePhoto housePhoto = mHousePhotoList.get(position);
        //房东对象
        final User user = mUserList.get(position);
//        Assess assess = mAssessList.get()
        //房屋
        Glide.with(mContext).load(housePhoto.getHouse_photo_path()).into(viewHolder.mRoomImageView);
//        viewHolder.mRoomImageView.setImageResource(R.drawable.home_city_room1);
        viewHolder.text_info.setText(house.getHouse_style() + "*" + house.getHouse_title());
        viewHolder.text_price.setText(house.getHouse_one_price() + "");
        viewHolder.text_describe.setText(house.getHouse_describe());
        //用户
        Glide.with(mContext)
                .load(user.getUser_photo())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .placeholder(R.drawable.user_test)
                .error(R.drawable.user_error)
                .into(viewHolder.mUserImageView);
        viewHolder.text_username.setText(user.getUser_realname());
//        viewHolder.mRatingBar.setNumStars();
        viewHolder.text_assess.setText(mAssessList.get(position) + "条评论");

        final ImageView mCollectionImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_collect_image);
        //解决收藏混乱问题
        mCollectionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一开始是未选中，点击之后换为选中
                //说明用户之前未收藏。现在想收藏
                if (!mHouseList.get(position).isCollected()) {
                    //此处之前为false
                    mCollectionImageView.setImageResource(R.mipmap.icon_collect_on);
                    mHouseList.get(position).setCollected(true);
                    //将收藏的行加入到数据库中
                    mHomeCityActivity  = new HomeCityActivity();
                    //应该从偏好设置中取出来用户ID
                    mHomeCityActivity.addCollect(1,house.getHouse_id());
                } else if (mHouseList.get(position).isCollected()) {
                    //此处之前为false
                    //说明用户之前收藏过,再次点击则取消
                    mCollectionImageView.setImageResource(R.mipmap.icon_collect_blue);
                    mHouseList.get(position).setCollected(false);
                    //将数据库中的收藏行删除
                    mHomeCityActivity  = new HomeCityActivity();
                    int deleteId = 0;
                    for (HouseCollect mhouseCollect: mHouseCollectList) {
                        if (mhouseCollect.getHouse_id() == house.getHouse_id()){
                            deleteId = mhouseCollect.getHouse_collect_id();
                        }
                    }
                    mHomeCityActivity.deleteCollect(deleteId);
                }
            }
        });
        //取出用户收藏的房源
        for (HouseCollect houseCollect : mHouseCollectList) {
            if (mHouseList.get(position).getHouse_id() == houseCollect.getHouse_id()) {
                house.setCollected(true);
            }
        }

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
