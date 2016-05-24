package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.chat_comment.CommentData;
import com.easygo.fragment.ChatDynamicFragment;
import com.easygo.view.MyGridView;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/5/23.
 */
public class ChatDynamicAdpter extends BaseAdapter {
    //初始化布局的类，可以找到layout文件夹中的所有布局
    private LayoutInflater mInflater;
    private Context mContext;

    private List<CommentData> mCommentDataList_list;
    private List<Integer> gridview_list;
    private FinalBitmap finalImageLoader ;
    private GridViewAdapter nearByInfoImgsAdapter;
    private int wh;

    public ChatDynamicAdpter(List<CommentData> mCommentDataList_list,ChatDynamicFragment chatDynamicFragment) {
        this.mCommentDataList_list = mCommentDataList_list;
        this.mContext=chatDynamicFragment.getActivity().getApplicationContext();
        this.mInflater = LayoutInflater.from(mContext);
        //this.wh=(SysUtils.getScreenWidth((Activity)mContext)-SysUtils.Dp2Px(mContext, 99))/3;
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
        ImageView mcomment_imageview,mzan_img,mcomment_img,mforward_img,mcomment_headimg,mgridview_image;
        TextView mfabiao_man,mfabiao_time,mbrowse,mzan_text,mcomment_text,mforward_text,mcomment_name,mcomment_content,mcomment_time,mdynamic_content;
        MyGridView myGridView;

    }

    @Override
    public long getItemId(int position) {
        return mCommentDataList_list == null ? null : position;
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
            gridview_list=mCommentDataList_list.get(position).getGridview_list();
            //说明是第一次绘制整屏列表，例如1-6行
            convertView =mInflater.inflate(R.layout.chat_listview_item,null);
            viewHolder = new ViewHolder();
            //初始化当前行布局中的所有控件
            viewHolder.mcomment_imageview = (ImageView) convertView.findViewById(R.id.comment_imageview);
            viewHolder.mzan_img = (ImageView) convertView.findViewById(R.id.zan_img);
            viewHolder.mcomment_img = (ImageView) convertView.findViewById(R.id.comment_img);
            viewHolder.mforward_img = (ImageView) convertView.findViewById(R.id.forward_img);
            viewHolder.mcomment_headimg = (ImageView) convertView.findViewById(R.id.comment_headimg);
            viewHolder.mfabiao_man = (TextView) convertView.findViewById(R.id.fabiao_man);
            viewHolder.mfabiao_time = (TextView) convertView.findViewById(R.id.fabiao_time);
            viewHolder.mbrowse = (TextView) convertView.findViewById(R.id.browse);
            viewHolder.mzan_text = (TextView) convertView.findViewById(R.id.zan_text);
            viewHolder.mcomment_text = (TextView) convertView.findViewById(R.id.comment_text);
            viewHolder.mforward_text = (TextView) convertView.findViewById(R.id.forward_text);
            viewHolder.mcomment_name = (TextView) convertView.findViewById(R.id.comment_name);
            viewHolder.mcomment_content = (TextView) convertView.findViewById(R.id.comment_content);
            viewHolder.mcomment_time = (TextView) convertView.findViewById(R.id.comment_time);
            viewHolder.mdynamic_content = (TextView) convertView.findViewById(R.id.dynamic_content);
            //加载上传的图片
            viewHolder.myGridView= (MyGridView) convertView.findViewById(R.id.gridview);
            viewHolder.mgridview_image= (ImageView) convertView.findViewById(R.id.gridview_image);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder)  ;
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //动态修改每一行控件的内容
        final CommentData commentData = mCommentDataList_list.get(position);


        viewHolder.mcomment_imageview.setImageResource(commentData.getComment_imageview());
        viewHolder.mzan_img.setImageResource(commentData.getZan_img());
        viewHolder.mcomment_img.setImageResource(commentData.getComment_img());
        viewHolder.mforward_img.setImageResource(commentData.getForward_img());
        viewHolder.mcomment_headimg.setImageResource(commentData.getComment_headimg());
        viewHolder.mcomment_imageview.setImageResource(commentData.getComment_imageview());
        viewHolder.mfabiao_man.setText(commentData.getFabiao_man());
        viewHolder.mfabiao_time.setText(commentData.getFabiao_time());
        viewHolder.mbrowse.setText(commentData.getBrowse());
        viewHolder.mzan_text.setText(commentData.getZan_text());
        viewHolder.mcomment_text.setText(commentData.getComment_text());
        viewHolder.mforward_text.setText(commentData.getForward_text());
        viewHolder.mcomment_name.setText(commentData.getComment_name());
        viewHolder.mcomment_content.setText(commentData.getComment_content());
        viewHolder.mcomment_time.setText(commentData.getComment_time());
        viewHolder.mdynamic_content.setText(commentData.getDynamic_content());


        //添加gridview适配器
        nearByInfoImgsAdapter = new GridViewAdapter(gridview_list,mContext,viewHolder.myGridView);
        viewHolder.myGridView.setAdapter(nearByInfoImgsAdapter);

        return convertView;
    }
}
