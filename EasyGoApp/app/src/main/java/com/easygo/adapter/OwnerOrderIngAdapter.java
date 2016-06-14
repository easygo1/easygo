package com.easygo.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easygo.activity.OwnerOrderActivity;
import com.easygo.activity.R;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
import com.easygo.fragment.OwnerOrderBookMeFragment;
import com.easygo.utils.DaysUtil;

import java.util.List;

/**
 * Created by 崔凯 on 2016/5/25.
 */
public class OwnerOrderIngAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<Orders> mOrdersList = null;
    List<House> mHouselist = null;
    List<HousePhoto> mHousePhotoList = null;

    public OwnerOrderIngAdapter(Context context, List<Orders> ordersList, List<House> houselist, List<HousePhoto> housePhotoList) {
        mContext = context;
        this.mOrdersList = ordersList;
        this.mHouselist = houselist;
        this.mHousePhotoList = housePhotoList;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mOrdersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrdersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //缓存布局中的控件
    class ViewHolder {
        TextView orderTitle;
        TextView orderState;
        ImageView orderImageView;
        TextView orderChecktime;
        TextView orderLeavetime;
        TextView orderSumtime;
        TextView orderRoomtype;
        TextView orderTotal;
        LinearLayout orderOwnerDelete;
        LinearLayout orderYes;
        LinearLayout orderLinerlayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_owner_list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.orderTitle = (TextView) convertView.findViewById(R.id.order_owner_title);
            viewHolder.orderState = (TextView) convertView.findViewById(R.id.order_owner_state);
            viewHolder.orderChecktime = (TextView) convertView.findViewById(R.id.order_checktime);
            viewHolder.orderLeavetime = (TextView) convertView.findViewById(R.id.order_leavetime);
            viewHolder.orderSumtime = (TextView) convertView.findViewById(R.id.order_sumtime);
            viewHolder.orderRoomtype = (TextView) convertView.findViewById(R.id.order_roomtype);
            viewHolder.orderTotal = (TextView) convertView.findViewById(R.id.order_total);
            viewHolder.orderImageView = (ImageView) convertView.findViewById(R.id.order_imageView);
            viewHolder.orderOwnerDelete = (LinearLayout) convertView.findViewById(R.id.order_owner_delete);
            viewHolder.orderYes = (LinearLayout) convertView.findViewById(R.id.order_yes);
            viewHolder.orderLinerlayout = (LinearLayout) convertView.findViewById(R.id.order_linerlayout);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //动态修改每一行的控件的内容
        //final GsonOrderInfo gsonOrderInfo = mGsonOrderInfoList.get(position);
        int days = DaysUtil.getDays(mOrdersList.get(position).getChecktime(), mOrdersList.get(position).getLeavetime());
        double money = days * (mHouselist.get(position).getHouse_one_price() + (mHouselist.get(position).getHouse_add_price() * (mOrdersList.get(position).getChecknum() - 1)));
        viewHolder.orderTitle.setText(mHouselist.get(position).getHouse_title());
        viewHolder.orderState.setText(mOrdersList.get(position).getOrder_state());
        viewHolder.orderChecktime.setText(mOrdersList.get(position).getChecktime());
        viewHolder.orderLeavetime.setText(mOrdersList.get(position).getLeavetime());
        viewHolder.orderSumtime.setText("共" + days + "晚");
        viewHolder.orderRoomtype.setText(mHouselist.get(position).getHouse_style());
        viewHolder.orderTotal.setText(money + "");
        //viewHolder.orderImageView.setImageResource(order.getImage());
        Glide.with(mContext).load(mHousePhotoList.get(position).getHouse_photo_path()).into(viewHolder.orderImageView);
        viewHolder.orderOwnerDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrdersList.get(position).getOrder_state().equals("待付款") || mOrdersList.get(position).getOrder_state().equals("待确认")){
                    showDelDialog(position);
                }else {
                    Toast.makeText(mContext, "您不可以取消该订单", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.orderYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Toast.makeText(mContext, "点击了确认订单" , Toast.LENGTH_SHORT).show();
                if (mOrdersList.get(position).getOrder_state().equals("待确认")){
                    showYesDialog(position);
                }else if(mOrdersList.get(position).getOrder_state().equals("已取消")) {
                    Toast.makeText(mContext, "该订单已被取消，无法确认", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "该订单已确认，无需重复确认", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*viewHolder.orderLinerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "跳转到订单详情界面" , Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }
    private void showDelDialog(final int position){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        //Toast.makeText(mContext, "确认" + which, Toast.LENGTH_SHORT).show();
                        //点击确认进行取消订单
                        /*CustomOrderActivity customOrderActivity = (CustomOrderActivity) mContext;
                        customOrderActivity.deleteOneOrder(mOrdersList.get(position).getOrder_id());*/
                        // OwnerOrderIBookFragment ownerOrderIBookFragment = new OwnerOrderIBookFragment();
                        // ownerOrderIBookFragment.deleteOneOrder(mOrdersList.get(position).getOrder_id());
                        OwnerOrderActivity ownerOrderActivity = (OwnerOrderActivity)mContext;
                        Log.e("fragment11",ownerOrderActivity.getSupportFragmentManager().getFragments().toString()+"");
                        OwnerOrderBookMeFragment ownerOrderBookMeFragment = (OwnerOrderBookMeFragment)ownerOrderActivity.getSupportFragmentManager().findFragmentByTag("bookme");
                        ownerOrderBookMeFragment.deleteOneOrder(mOrdersList.get(position).getOrder_id(),position);
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        //Toast.makeText(mContext, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
                    /*case Dialog.BUTTON_NEUTRAL:
                        Toast.makeText(mContext, "忽略" + which, Toast.LENGTH_SHORT).show();
                        break;*/
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认取消该订单?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认",dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
//        builder.setNeutralButton("忽略", dialogOnclicListener);
        builder.create().show();
    }
    private void showYesDialog(final int position){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        //点击确认进行确认订单
                        /*CustomOrderActivity customOrderActivity = (CustomOrderActivity) mContext;
                        customOrderActivity.deleteOneOrder(mOrdersList.get(position).getOrder_id());*/
                        // OwnerOrderIBookFragment ownerOrderIBookFragment = new OwnerOrderIBookFragment();
                        // ownerOrderIBookFragment.deleteOneOrder(mOrdersList.get(position).getOrder_id());
                        OwnerOrderActivity ownerOrderActivity = (OwnerOrderActivity)mContext;
                        Log.e("fragment11",ownerOrderActivity.getSupportFragmentManager().getFragments().toString()+"");
                        OwnerOrderBookMeFragment ownerOrderBookMeFragment = (OwnerOrderBookMeFragment)ownerOrderActivity.getSupportFragmentManager().findFragmentByTag("bookme");
                        Log.e("5656",""+mOrdersList.get(position).getOrder_id()+"---"+position);
                        ownerOrderBookMeFragment.yesOneOrder(mOrdersList.get(position).getOrder_id(),position);
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        //Toast.makeText(mContext, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
                    /*case Dialog.BUTTON_NEUTRAL:
                        Toast.makeText(mContext, "忽略" + which, Toast.LENGTH_SHORT).show();
                        break;*/
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认该订单?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认",dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
//        builder.setNeutralButton("忽略", dialogOnclicListener);
        builder.create().show();
    }

}
