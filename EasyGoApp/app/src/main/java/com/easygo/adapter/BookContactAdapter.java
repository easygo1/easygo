package com.easygo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.user.UserLinkman;

import java.util.List;

public class BookContactAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<UserLinkman> mList;

    public BookContactAdapter(Context context, List<UserLinkman> list) {
        mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //入住人的姓名，身份证
    class ViewHolder {
        TextView text_name;
        TextView text_idcard;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.room_book_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text_name = (TextView) convertView.findViewById(R.id.room_book_list_name);
            viewHolder.text_idcard = (TextView) convertView.findViewById(R.id.room_book_list_idcard);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserLinkman userLinkman = mList.get(position);

        viewHolder.text_name.setText(userLinkman.getName());
        viewHolder.text_idcard.setText(userLinkman.getIdcard());

        //settext 必须是字符串！！！！！
        return convertView;
    }
}
