package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easygo.activity.R;
import com.easygo.beans.chat_comment.CommentDynamicData;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by 王韶辉 on 2016/5/26.
 */
public class CommentDynamicAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<CommentDynamicData> mCommentDynamicDatas=new ArrayList<>();

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
    class ViewHolder {
        /*评论控件*/
        private ImageView headImgUrl;
        private TextView commentName, commentContent, commentTime,mnumber_like,mbrowse;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //找到每一行的布局
        if (convertView == null) {
            //说明是第一次绘制整屏列表，例如1-6行
            convertView = mInflater.inflate(R.layout.comment_dynamic, null);
            viewHolder = new ViewHolder();

            //初始化当前行布局中的所有控件
            viewHolder.headImgUrl = (ImageView) convertView.findViewById(R.id.comment_headimg);
            viewHolder.commentName = (TextView) convertView.findViewById(R.id.comment_name);
            viewHolder.commentContent = (TextView) convertView.findViewById(R.id.comment_content);
            viewHolder.mbrowse = (TextView) convertView.findViewById(R.id.browse);
            viewHolder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);
            viewHolder.mnumber_like= (TextView) convertView.findViewById(R.id.number_like);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //动态修改每一行控件的内容
        final CommentDynamicData commentDynamicData = mCommentDynamicDatas.get(position);

        //显示要加载的头像
        Glide.with(mContext)
                .load(commentDynamicData.getUserphoto())
                .error(R.mipmap.user_photo_defult)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(viewHolder.headImgUrl);

        viewHolder.commentName.setText(commentDynamicData.getNickname());
       /* if (commentDynamicData.getNews_views() == 0) {
            viewHolder.mbrowse.setText("浏览量：0 次");
        }else{
            viewHolder.mbrowse.setText("浏览量："+commentDynamicData.getNews_views()+"次");
        }*/
        viewHolder.commentContent.setText(commentDynamicData.getComment_content());
        viewHolder.commentTime.setText(commentDynamicData.getComment_time());
       /* if (commentDynamicData.getNews_stars() == 0) {
            viewHolder.mbrowse.setText("还没有人为该动态点赞");
        }else{
            viewHolder.mnumber_like.setText("已经有  "+commentDynamicData.getNews_stars()+"  人觉得很赞~");
        }
*/
        return convertView;
    }
}
