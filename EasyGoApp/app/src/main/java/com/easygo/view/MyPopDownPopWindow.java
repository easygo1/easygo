package com.easygo.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.easygo.activity.R;

/**
 * Created by 王韶辉 on 2016/5/25.
 */
public class MyPopDownPopWindow extends PopupWindow {

    private Button btn_username, btn_address;
    private View mMenuView;//popwindow菜单布局
    private Context mContext;

    public MyPopDownPopWindow(Activity mContext, View.OnClickListener itemsOnClick) {
        super(mContext);
        this.mContext = mContext;
        //popwindow导入
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popdownwindow, null);
        btn_username = (Button) mMenuView.findViewById(R.id.add_friend_username);
        btn_address = (Button) mMenuView.findViewById(R.id.add_friend_address);

        //设置按钮监听
        btn_username.setOnClickListener(itemsOnClick);
        btn_address.setOnClickListener(itemsOnClick);

        //设置SelectPicPopupWindow的View，导入布局动画
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.add_friend_popwindow).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


}
