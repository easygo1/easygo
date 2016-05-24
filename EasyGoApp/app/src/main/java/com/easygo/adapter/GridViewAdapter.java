package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easygo.activity.R;
import com.easygo.view.MyGridView;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/5/23.
 */
public class GridViewAdapter extends BaseAdapter {
    MyGridView myGridView;
    List<Integer> list;
    private Context mContext;
    int size=0;
    LayoutInflater mInflater;
    LinearLayout.LayoutParams params;

    public GridViewAdapter(List<Integer> list, Context chatDynamicFragment,MyGridView mygridView) {

        this.list = list;
        this.mContext=chatDynamicFragment;
        mInflater=LayoutInflater.from(mContext);
        myGridView=mygridView;
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
        imageView.setImageResource(list.get(position));
        if(size==1){
            //修改每一行列数由自适应改为只有一列
            myGridView.setNumColumns(1);
            //修改gridview每一列宽度是90的默认值：当前gridview位于相对布局下面
            myGridView.setColumnWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
            //图片的宽填满，高设置为600
            params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,600);
            imageView.setLayoutParams(params);
        }else if(size==2||size==4){
            //修改每一行列数由自适应改为只有一列
            myGridView.setNumColumns(2);
            //修改gridview每一列宽度是90的默认值：当前gridview位于相对布局下面
            myGridView.setColumnWidth(RelativeLayout.LayoutParams.MATCH_PARENT/2);
            //图片的宽填满，高设置为600
            params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,350,1);
            imageView.setLayoutParams(params);

        }else{
            myGridView.setNumColumns(3);
            myGridView.setColumnWidth(RelativeLayout.LayoutParams.MATCH_PARENT/3);
            params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,250,1);
            imageView.setLayoutParams(params);
        }
        return convertView;
    }
}
