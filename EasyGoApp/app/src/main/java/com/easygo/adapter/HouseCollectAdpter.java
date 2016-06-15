package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easygo.activity.HouseCollectionActivity;
import com.easygo.activity.R;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.user.User;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by zzia on 2016/6/9.
 */
public class HouseCollectAdpter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    HouseCollectionActivity mCollectionActivity;
    List<House> mHouseList = null;
    List<User> mUserList = null;
    List<HousePhoto> mHousePhotoList = null;
    List<Integer> mAssessList = null;
    List<Integer> starNumList;

    public HouseCollectAdpter(Context context, List<House> houseList, List<User> userList, List<HousePhoto> housePhotoList, List<Integer> assessList,
                              List<Integer> starNumList) {
        mContext = context;
        mHouseList = houseList;
        mUserList = userList;
        mHousePhotoList = housePhotoList;
        mAssessList = assessList;
        this.starNumList = starNumList;
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
        ImageView mCollectionImageView;
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
            viewHolder.mCollectionImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_collect_image);
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
//        Log.e("size",""+starNumList.get(position));
        /*if (starNumList != null) {
            if (starNumList.get(position) != 0) {
                viewHolder.mRatingBar.setNumStars(starNumList.get(position));
            } else {
                viewHolder.mRatingBar.setNumStars(5);
            }
        }*/

        viewHolder.text_assess.setText(mAssessList.get(position) + "条评论");
        viewHolder.mCollectionImageView.setImageResource(R.mipmap.icon_collect_on);
        final ImageView mCollectionImageView = (ImageView) convertView.findViewById(R.id.homepage_city_item_collect_image);
        //解决收藏混乱问题
        mCollectionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据库中的收藏行删除
                mCollectionActivity = new HouseCollectionActivity();
                mCollectionActivity.deleteCollect(mHouseList.get(position).getHouse_id());
            }
        });
        return convertView;
    }
}
