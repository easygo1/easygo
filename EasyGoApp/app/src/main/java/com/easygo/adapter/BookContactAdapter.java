package com.easygo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.activity.BookActivity;
import com.easygo.activity.R;
import com.easygo.activity.UserLinkmanActivity;
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

    //用户的常用入住人的姓名，身份证
    class ViewHolder {
        TextView text_name;
        TextView text_idcard;
        ImageView mDelImageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.room_book_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text_name = (TextView) convertView.findViewById(R.id.room_book_list_name);
            viewHolder.text_idcard = (TextView) convertView.findViewById(R.id.room_book_list_idcard);
            viewHolder.mDelImageView = (ImageView) convertView.findViewById(R.id.room_book_list_choose_image);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserLinkman userLinkman = mList.get(position);
        viewHolder.text_name.setText(userLinkman.getLinkman_name());
        viewHolder.text_idcard.setText(userLinkman.getIdcard());
        viewHolder.mDelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDelDialog(position);

            }
        });

        //settext 必须是字符串！！！！！
        return convertView;
    }

    private void showDelDialog(final int position) {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        BookActivity ba = (BookActivity) mContext;
                        ba.deleteLinkman(position);
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认删除?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }
}
