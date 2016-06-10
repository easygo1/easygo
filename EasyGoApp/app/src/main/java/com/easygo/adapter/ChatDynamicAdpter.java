package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easygo.activity.R;
import com.easygo.beans.chat_comment.CommentData;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.ClickNineGridViewAdapter;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by 王韶辉 on 2016/5/23.
 */
public class ChatDynamicAdpter extends BaseAdapter {
    //初始化布局的类，可以找到layout文件夹中的所有布局
    private LayoutInflater mInflater;
    private Context mContext;
    private List<CommentData> mCommentDataList_list;
    private FinalBitmap finalImageLoader ;
    //private GridViewAdapter nearByInfoImgsAdapter;


    public ChatDynamicAdpter(List<CommentData> mCommentDataList_list,Context context) {
        this.mCommentDataList_list = mCommentDataList_list;
        this.mContext=context;
        this.mInflater = LayoutInflater.from(mContext);
        this.finalImageLoader=FinalBitmap.create(mContext);
        this.finalImageLoader.configLoadingImage(R.mipmap.ic_launcher);
    }

    public List<CommentData> getmCommentDataList_list() {
        return mCommentDataList_list;
    }

    public void setmCommentDataList_list(List<CommentData> mCommentDataList_list) {
        this.mCommentDataList_list = mCommentDataList_list;
    }

    @Override
    public int getCount() {
        return mCommentDataList_list == null ? 0 : mCommentDataList_list.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentDataList_list == null ? null : mCommentDataList_list.get(position);
    }

    //缓存布局中的控件
    class ViewHolder{
        ImageView mcomment_imageview;
        TextView mfabiao_man,mfabiao_time,mbrowse,mdynamic_content,mnumber_like;
        LinearLayout mZan,mComment,mForward;
        NineGridView nineGridView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 指定列表中每一行的布局，并且为每一行中布局的控件赋初值
     * @param position 当前绘制的是第几行界面，从0开始
     * @param convertView 缓存视图的一个类
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mCommentDataList_list.size() == 0) {
            return null;
        }
        ViewHolder viewHolder;
        //找到每一行的布局
        if (convertView == null){
            //说明是第一次绘制整屏列表，例如1-6行
            convertView =mInflater.inflate(R.layout.chat_listview_item,null);
            viewHolder = new ViewHolder();
            //初始化当前行布局中的所有控件
            viewHolder.mcomment_imageview = (ImageView) convertView.findViewById(R.id.comment_imageview);
            viewHolder.mComment = (LinearLayout) convertView.findViewById(R.id.comment);
            viewHolder.mForward = (LinearLayout) convertView.findViewById(R.id.forward);
            viewHolder.mZan = (LinearLayout) convertView.findViewById(R.id.zan);
            viewHolder.mfabiao_man = (TextView) convertView.findViewById(R.id.fabiao_man);
            viewHolder.mfabiao_time = (TextView) convertView.findViewById(R.id.fabiao_time);
            viewHolder.mbrowse = (TextView) convertView.findViewById(R.id.browse);
            viewHolder.mdynamic_content = (TextView) convertView.findViewById(R.id.dynamic_content);
            viewHolder.mnumber_like= (TextView) convertView.findViewById(R.id.number_like);
            //加载上传的图片
            viewHolder.nineGridView= (NineGridView) convertView.findViewById(R.id.gridview_imageview);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //动态修改每一行控件的内容
        final CommentData commentData = mCommentDataList_list.get(position);
        //显示要加载的图片
        Glide.with(mContext)
                .load(commentData.getUser_photo())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .error(R.mipmap.user_photo_defult)
                .into(viewHolder.mcomment_imageview);
        viewHolder.mfabiao_man.setText(commentData.getUser_nickname());
        viewHolder.mfabiao_time.setText(commentData.getNews_time());
        if (commentData.getNews_views() == 0) {
            viewHolder.mbrowse.setText("浏览量：0 次");
        }else{
            viewHolder.mbrowse.setText("浏览量："+commentData.getNews_views()+"次");
        }
        viewHolder.mdynamic_content.setText(commentData.getNews_content());
        if (commentData.getNews_stars() == 0) {
            viewHolder.mnumber_like.setText("还没有人为该动态点赞");
        }else{
            viewHolder.mnumber_like.setText("已经有  "+commentData.getNews_stars()+"  人觉得很赞~");
        }



        //使用框架加载图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = commentData.getPhoto_path();
        if (imageDetails != null) {
            for (String url : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(url);
                info.setBigImageUrl(url);
                imageInfo.add(info);
            }
        }
        //调用框架的适配器
        viewHolder.nineGridView.setAdapter(new ClickNineGridViewAdapter(mContext,imageInfo));
        return convertView;
    }
}
