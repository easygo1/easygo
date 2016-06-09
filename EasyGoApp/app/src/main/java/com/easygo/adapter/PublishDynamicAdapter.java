package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.easygo.activity.PublishDynamicActivity;
import com.easygo.activity.R;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/6/6.
 * 发表说说时让每行显示4张图片
 */
public class PublishDynamicAdapter extends BaseAdapter{


    GridView myGridView;
    int size;
    List<String> list;
    Context mContext;
    LayoutInflater mInflater;
    LinearLayout.LayoutParams params;


    public PublishDynamicAdapter(PublishDynamicActivity mContext, List<String> gridviewlist, GridView mNoScrollGridview) {
        this.list = gridviewlist;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
        this.myGridView= mNoScrollGridview;
    }


    @Override
    public int getCount() {
        size=list.size();
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=mInflater.inflate(R.layout.gridview_item,null);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.gridview_image);
        //显示要加载的图片
        Glide.with(mContext)
                .load(list.get(position))
                .into(imageView);

        //固定让每行显示4张图片
        myGridView.setNumColumns(4);
        myGridView.setColumnWidth(RelativeLayout.LayoutParams.MATCH_PARENT/3);
        params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150,1);

        imageView.setLayoutParams(params);
        return convertView;
    }
}
