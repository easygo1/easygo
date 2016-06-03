package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.easygo.activity.R;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/6/1.
 */
public class SelectFriendAdapter extends BaseAdapter {
    List<String> mFriendList;
    Context mContext;
    LayoutInflater mInflater;

    public SelectFriendAdapter(Context mContext,List<String> mFriendList) {
        this.mFriendList = mFriendList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);//初始化
    }

    @Override
    public int getCount() {
        //返回数据数量
        return mFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        //返回每一行的数据结果
        return mFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //返回每一行的数据id，几乎不会用到
        return position;
    }

    //缓存布局中的控件
    class ViewHolder {
        TextView textView;
    }

    /**
     * @param position    表示当前显示的是第几行数据，从0开始
     * @param convertView 表示当前行显示的布局是哪个布局
     * @param parent      包含当前行布局的父布局
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //确定每一行的布局，并且把对应的数据显示到布局上
        //1.找到每一行的布局
        if (convertView == null) {
            //说明是第一次绘制整屏列表，如1-6行
            convertView = mInflater.inflate(R.layout.select_friend_list, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.friend_id);

            //把当前控件缓存到布局中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //第一个参数：每一行要显示的布局索引，第二个参数：包含每一行布局的福布局，这里直接用null

        //确定每一行布局中每个控件显示的内容
        final String myData= mFriendList.get(position);

        //监听每一行的按钮单机事件
        Button mButton= (Button) convertView.findViewById(R.id.add);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向数据库中进行添加好友申请
            }
        });

        return convertView;
    }

}
