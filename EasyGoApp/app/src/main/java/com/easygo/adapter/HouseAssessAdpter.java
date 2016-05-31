package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.house.HouseAssess;

import java.util.List;

/**
 * 房屋评价listview的适配器
 * Created by zzia on 2016/5/24.
 */
public class HouseAssessAdpter extends BaseAdapter {
    List<HouseAssess> mHouseAssessList;
    Context mContext;
    LayoutInflater mInflater;

    public HouseAssessAdpter(Context context, List<HouseAssess> houseAssessList) {
        mContext = context;
        mHouseAssessList = houseAssessList;
        mInflater = LayoutInflater.from(mContext);
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

    class ViewHolder {
        ImageView mImageview;//评价人的头像
        TextView mAssessUserNameTextView, mUserCheckTextView, massessContentTextView;
        //评价人的用户名,入住时间，评价内容
        TextView mReplyContentTextView;//房东回复内容
        RatingBar mAssessRatingBar;//评价星级
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.house_assess_item, null);
            viewholder = new ViewHolder();
            viewholder.mImageview = (ImageView) convertView.findViewById(R.id.assess_user_photo);
            viewholder.mAssessUserNameTextView = (TextView) convertView.findViewById(R.id.assess_user_name);
            viewholder.mAssessRatingBar = (RatingBar) convertView.findViewById(R.id.assess_stars);
            viewholder.mUserCheckTextView = (TextView) convertView.findViewById(R.id.user_check_time);
            viewholder.massessContentTextView = (TextView) convertView.findViewById(R.id.assess_content);
            viewholder.mReplyContentTextView = (TextView) convertView.findViewById(R.id.assess_reply);
            convertView.setTag(viewholder);//记得加上，要不滑动时就出现空指针错误
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewholder = (ViewHolder) convertView.getTag();
        }
        //得到一个房屋评价的对象
        HouseAssess houseAssess = mHouseAssessList.get(position);
        viewholder.mImageview.setImageResource(R.mipmap.house_owner_info);
        viewholder.mAssessUserNameTextView.setText(houseAssess.getAssesspersonName());
        viewholder.mAssessRatingBar.setNumStars(houseAssess.getStars());
        viewholder.mUserCheckTextView.setText(houseAssess.getAssesstime());
        viewholder.massessContentTextView.setText(houseAssess.getAssesscontent());
        viewholder.mReplyContentTextView.setText(houseAssess.getAssessreply());
        return convertView;
    }
}
