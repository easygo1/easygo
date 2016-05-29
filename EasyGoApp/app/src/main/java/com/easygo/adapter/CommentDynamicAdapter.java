package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.chat_comment.CommentDynamicData;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/5/26.
 */
public class CommentDynamicAdapter extends BaseAdapter{
    LayoutInflater mInflater;
    Context mContext;
    List<CommentDynamicData> mCommentDynamicDatas;

    public CommentDynamicAdapter(Context mContext, List<CommentDynamicData> mCommentDynamicDatas) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mCommentDynamicDatas = mCommentDynamicDatas;
    }

    @Override
    public int getCount() {
        return mCommentDynamicDatas == null ? 0 : mCommentDynamicDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentDynamicDatas == null ? 0 : mCommentDynamicDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //缓存布局中的控件
    class ViewHolder{
        /*评论控件*/
        private ImageView headImgUrl;
        private TextView commentName,commentContent,commentTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //找到每一行的布局
        if (convertView == null){
            //说明是第一次绘制整屏列表，例如1-6行
            convertView =mInflater.inflate(R.layout.comment_dynamic,null);
            viewHolder = new ViewHolder();

            //初始化当前行布局中的所有控件
            viewHolder.headImgUrl = (ImageView) convertView.findViewById(R.id.comment_headimg);
            viewHolder.commentName = (TextView) convertView.findViewById(R.id.comment_name);
            viewHolder.commentContent = (TextView) convertView.findViewById(R.id.comment_content);
            viewHolder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);

            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //动态修改每一行控件的内容
        final CommentDynamicData commentDynamicData = mCommentDynamicDatas.get(position);

        viewHolder.headImgUrl.setImageResource(commentDynamicData.getHeadImgUrl());
        viewHolder.commentName.setText(commentDynamicData.getCommentName());
        viewHolder.commentContent.setText(commentDynamicData.getCommentContent());
        viewHolder.commentTime.setText(commentDynamicData.getCommentTime());

        return convertView;
    }
}
