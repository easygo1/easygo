package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easygo.activity.R;

import java.util.List;

/**
 * Created by 王韶辉 on 2016/6/2.
 */
public class FriendListAdapter extends BaseAdapter {

    //初始化布局的类，可以找到layout文件夹中的所有布局
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mfriendlist;

    public FriendListAdapter(List<String> mfriendlist, Context context) {
        this.mfriendlist = mfriendlist;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mfriendlist == null ? 0 : mfriendlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mfriendlist == null ? null : mfriendlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //缓存布局中的控件
    class ViewHolder {
        TextView mFriendItem;
    }

    /**
     * 指定列表中每一行的布局，并且为每一行中布局的控件赋初值
     *
     * @param position    当前绘制的是第几行界面，从0开始
     * @param convertView 缓存视图的一个类
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mfriendlist.size() == 0) {
            return null;
        }
        ViewHolder viewHolder;
        //找到每一行的布局
        if (convertView == null) {
            //说明是第一次绘制整屏列表，例如1-6行
            convertView = mInflater.inflate(R.layout.friend_list_item, null);
            viewHolder = new ViewHolder();
            //初始化控件
            viewHolder.mFriendItem = (TextView) convertView.findViewById(R.id.friend_item);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //动态修改每一行控件的内容
        final String friend_more = mfriendlist.get(position);
        viewHolder.mFriendItem.setText(friend_more);

        return convertView;
    }
}
