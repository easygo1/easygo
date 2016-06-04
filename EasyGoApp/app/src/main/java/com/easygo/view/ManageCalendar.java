package com.easygo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easygo.activity.R;
import com.easygo.beans.house.HouseDateManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 王韶辉 on 2016/5/25.
 */
public class ManageCalendar extends LinearLayout {
    private static Context context;
    //数据库中房东设置的已关的时间列
    List<HouseDateManage> sqlNotList = new ArrayList<>();
    //数据库中被租出去的时间列
    List<HouseDateManage> userBuyList = new ArrayList<>();

    private Date theInDay;
    //    private String inday = "", outday = "";
    public static View viewIn;
    public static View viewOut;
    public static String positionIn;
    public static String positionOut;

    static long nd = 1000 * 24L * 60L * 60L;//一天的毫秒数

    private List<String> gvList;//存放天

    private OnDaySelectListener callBack;//回调函数

    private static String nowday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");//日期格式化

    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");//日期格式化

    /**
     * 构造函数
     *
     * @param context
     */
    public ManageCalendar(Context context) {
        super(context);
        ManageCalendar.context = context;
    }

    /**
     * 构造函数
     *
     * @param context
     */
    public ManageCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        ManageCalendar.context = context;
    }

//    public void setInDay(String inday) {
//        this.inday = inday;
//    }

    //    public void setOutDay(String outday) {
//        this.outday = outday;
//    }
    //设置数据库里房东设置过的，不可租的日期
    public void setNotDay(List<HouseDateManage> sqlNotList) {
        this.sqlNotList = sqlNotList;
    }

    //设置数据库里房客购买过的，已租的日期
    public void setBuyDay(List<HouseDateManage> userBuyList) {
        this.userBuyList = userBuyList;
    }

    //设置今天是哪一天，并且初始化GridView
    public void setTheDay(Date dateIn) {
        this.theInDay = dateIn;
        init();
    }

    /**
     * 初始化日期以及view等控件
     */
    private void init() {
        //gvList存放显示的天数
        gvList = new ArrayList<String>();//存放天
        final Calendar cal = Calendar.getInstance();//获取日历实例
        cal.setTime(theInDay);//cal设置为当天的
        cal.set(Calendar.DATE, 1);//cal设置当前day为当前月第一天
        int tempSum = countNeedHowMuchEmpety(cal);//获取当前月第一天为星期几
        int dayNumInMonth = getDayNumInMonth(cal);//获取当前月有多少天
        //为gvList添加值
        setGvListData(tempSum, dayNumInMonth,
                cal.get(Calendar.YEAR) + "-" + getMonth((cal.get(Calendar.MONTH) + 1)));

        View view = LayoutInflater.from(context).inflate(R.layout.comm_calendar, this, true);//获取布局，开始初始化
        TextView tv_year = (TextView) view.findViewById(R.id.tv_year);
        if (cal.get(Calendar.YEAR) > new Date().getYear()) {
            tv_year.setVisibility(View.VISIBLE);
            //2016年
            tv_year.setText(cal.get(Calendar.YEAR) + "年");
        }
        //**月
        TextView tv_month = (TextView) view.findViewById(R.id.tv_month);
        tv_month.setText(String.valueOf(theInDay.getMonth() + 1));
        MyGridView gv = (MyGridView) view.findViewById(R.id.gv_calendar);
        //适配器
        calendarGridViewAdapter gridViewAdapter =
                new calendarGridViewAdapter(gvList, sqlNotList, userBuyList);
        gv.setAdapter(gridViewAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                String choiceDay = (String) adapterView.getAdapter().getItem(position);
                //得到了当前点击的日期
//                Log.e("11", choiceDay);
                String[] date = choiceDay.split(",");
                String day = date[1];

                if (!" ".equals(day)) {
                    if (Integer.parseInt(day) < 10) {
                        day = "0" + date[1];
                    }
                    choiceDay = date[0] + "-" + day;
                    if (callBack != null) {//调用回调函数回调数据
                        callBack.onDaySelectListener(arg1, choiceDay);
                    }
                }
            }
        });
    }


    /**
     * 为gridview中添加需要展示的数据
     *
     * @param tempSum
     * @param dayNumInMonth
     */
    private void setGvListData(int tempSum, int dayNumInMonth, String YM) {
        gvList.clear();
        for (int i = 0; i < tempSum; i++) {
            gvList.add(" , ");
        }
        for (int j = 1; j <= dayNumInMonth; j++) {
            gvList.add(YM + "," + String.valueOf(j));
        }
    }

    //月份的修改，个位数的月份就补0
    private String getMonth(int month) {
        String mon = "";
        if (month < 10) {
            mon = "0" + month;
        } else {
            mon = "" + month;
        }
        return mon;
    }

    /**
     * 获取当前月的总共天数
     *
     * @param cal
     * @return
     */
    private int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当前月第一天在第一个礼拜的第几天，得出第一天是星期几
     *
     * @param cal
     * @return
     */
    private int countNeedHowMuchEmpety(Calendar cal) {
        int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return firstDayInWeek;
    }


    /**
     * gridview中adapter的viewholder
     *
     * @author Administrator
     */
    static class GrideViewHolder {
        TextView tvDay, tv;
    }

    /**
     * gridview的adapter
     *
     * @author Administrator
     */
    static class calendarGridViewAdapter extends BaseAdapter {

        List<String> gvList;//存放天
        //        String inday, outday;
        //数据库中房东设置的已关的时间列
        List<HouseDateManage> sqlNotList = new ArrayList<>();
        //数据库中被租出去的时间列
        List<HouseDateManage> userBuyList = new ArrayList<>();


        public calendarGridViewAdapter(List<String> gvList, List<HouseDateManage> sqlNotList,
                                       List<HouseDateManage> userBuyList) {
            super();
            this.gvList = gvList;
            this.sqlNotList = sqlNotList;
            this.userBuyList = userBuyList;
        }

        @Override
        public int getCount() {
            return gvList.size();
        }

        @Override
        public String getItem(int position) {
            return gvList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GrideViewHolder holder;
            if (convertView == null) {
                holder = new GrideViewHolder();
                convertView = inflate(context, R.layout.common_calendar_gridview_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_calendar);
                holder.tvDay = (TextView) convertView.findViewById(R.id.tv_calendar_day);
                convertView.setTag(holder);
            } else {
                holder = (GrideViewHolder) convertView.getTag();
            }
            String[] date = getItem(position).split(",");


//            Log.e("这是什么：",date[1]);

            holder.tvDay.setText(date[1]);
            /*if ((position + 1) % 7 == 0 || (position) % 7 == 0) {
                holder.tvDay.setTextColor(Color.parseColor("#339900"));
            }*/
            if (!date[1].equals(" ")) {
                String day = date[1];
                if (Integer.parseInt(date[1]) < 10) {
                    day = "0" + date[1];
                }
                if ((date[0] + "-" + day).equals(nowday)) {

                    holder.tvDay.setTextColor(Color.parseColor("#FF6600"));
                    holder.tvDay.setTextSize(15);
                    holder.tvDay.setText("今天");
                }


                //房东设置的不可租
                for (HouseDateManage houseDateManage : sqlNotList) {
                    if (!"".equals(houseDateManage.getDate_not_use())
                            && (date[0] + "-" + day).equals(houseDateManage.getDate_not_use())) {
//                        Log.e("test", date[0] + "-" + day);
//                        convertView.setBackgroundColor(Color.parseColor("#33B5E5"));
                        convertView.setBackground(convertView.getResources()
                                .getDrawable(R.drawable.date_manage));
                        holder.tvDay.setTextColor(Color.WHITE);
                        holder.tvDay.setText(date[1]);
                        holder.tv.setText("已关");
                        viewIn = convertView;
                        positionIn = date[1];
                    }
                    /*if (!"".equals(outday) && (date[0] + "-" + day).equals(outday)) {
                        convertView.setBackgroundColor(Color.parseColor("#33B5E5"));
                        holder.tvDay.setTextColor(Color.WHITE);
                        holder.tvDay.setText(date[1]);
                        holder.tv.setText("离开");
                        viewOut = convertView;
                        positionOut = date[1];
                    }*/
                }
                // 用户租出去的
                for (HouseDateManage houseDateManage : userBuyList) {
                    if (!"".equals(houseDateManage.getDate_not_use())
                            && (date[0] + "-" + day).equals(houseDateManage.getDate_not_use())) {
//                        Log.e("test", date[0] + "-" + day);
//                        convertView.setBackgroundColor(Color.parseColor("#33B5E5"));
                        convertView.setBackground(
                                convertView.getResources().getDrawable(R.drawable.date_manage));
                        holder.tvDay.setTextColor(Color.WHITE);
                        holder.tvDay.setText(date[1]);
                        holder.tv.setText("已租");
                        viewIn = convertView;
                        positionIn = date[1];
                    }
                    /*if (!"".equals(outday) && (date[0] + "-" + day).equals(outday)) {
                        convertView.setBackgroundColor(Color.parseColor("#33B5E5"));
                        holder.tvDay.setTextColor(Color.WHITE);
                        holder.tvDay.setText(date[1]);
                        holder.tv.setText("离开");
                        viewOut = convertView;
                        positionOut = date[1];
                    }*/
                }


                /*
                *   date[0] + "-" + day格式
                *   2016-06-21
                *
                */

                try {
                    //若日历日期<当前日期，则不能选择
                    if (dateFormat2.parse(date[0] + "-" + day).getTime() < dateFormat2.parse(nowday).getTime()) {
                        holder.tvDay.setTextColor(Color.parseColor("#999999"));
                    }
                    //若日历日期-当前日期>90天，则不能选择
                    long dayxc = (dateFormat2.parse(date[0] + "-" + day).getTime() - dateFormat2.parse(nowday).getTime()) / nd;
                    if (dayxc > 90) {
                        holder.tvDay.setTextColor(Color.parseColor("#999999"));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return convertView;
        }
    }

    /**
     * 自定义监听接口
     *
     * @author Administrator
     */
    public interface OnDaySelectListener {
        void onDaySelectListener(View view, String date);
    }

    /**
     * 自定义监听接口设置对象
     *
     * @param o
     */
    public void setOnDaySelectListener(OnDaySelectListener o) {
        callBack = o;
    }
}
