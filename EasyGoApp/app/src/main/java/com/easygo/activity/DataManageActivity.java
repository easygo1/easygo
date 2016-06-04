package com.easygo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonAboutHouseManage;
import com.easygo.beans.house.HouseDateManage;
import com.easygo.view.ManageCalendar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
*   房东管理日期的页面
*/
@SuppressLint("SimpleDateFormat")
public class DataManageActivity extends AppCompatActivity
        implements ManageCalendar.OnDaySelectListener, View.OnClickListener {
    public static final int GET_DATE_WHAT = 1;
    public static final int HOUSE_DATE_ADD = 2;
    //网络请求队列
    private RequestQueue requestQueue;
    Request<String> request;
    String mPath;
    int houseid = 1;//暂时认定为存在Houseid
    GsonAboutHouseManage gsonAboutHouseManage;
    //数据库中房东设置的已关的时间列
    List<HouseDateManage> sqlNotList;
    //数据库中被租出去的时间列
    List<HouseDateManage> userBuyList;
    //日期管理实体
    HouseDateManage mHouseDateManage;
    //布局相关
    LinearLayout ll;
    ManageCalendar c1;
    Date date;
    String nowday;
    long nd = 1000 * 24L * 60L * 60L;//一天的毫秒数
    SimpleDateFormat simpleDateFormat, sd1, sd2;
    //存放这一天是否被选中了
//    List<String> mDateList;
    //标题栏的两个按钮
    TextView mSaveTextView;
    ImageView mBackImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initViews();
        //请求网络数据
        loadData();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowday = simpleDateFormat.format(new Date());
        sd1 = new SimpleDateFormat("yyyy");
        sd2 = new SimpleDateFormat("dd");
        ll = (LinearLayout) findViewById(R.id.ll);

    }

    private void initViews() {
        mSaveTextView = (TextView) findViewById(R.id.data_manage_save);
        mBackImageView = (ImageView) findViewById(R.id.data_manage_back);
        mSaveTextView.setOnClickListener(this);
        mBackImageView.setOnClickListener(this);
    }

    private void loadData() {
        //初始化
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();

        sqlNotList = new ArrayList<>();
        userBuyList = new ArrayList<>();

        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "getHouseDateByHouseId");
        request.add("houseid", houseid);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(GET_DATE_WHAT, request, onResponseListener);
    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == GET_DATE_WHAT) {
                // 请求成功
                String result = response.get();// 响应结果
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type type = new TypeToken<GsonAboutHouseManage>() {
                }.getType();
                Log.e("gson", result);
                gsonAboutHouseManage = gson.fromJson(result, type);

                sqlNotList = gsonAboutHouseManage.getHouseNotList();
                userBuyList = gsonAboutHouseManage.getHouseUserBuyList();
                //等得到数据后再去初始化那些View
                init();
            } else if (what == HOUSE_DATE_ADD) {
                //房东保存成功
                finish();

            }
        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
        }

        @Override
        public void onFinish(int what) {
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(DataManageActivity.this, "失败了", Toast.LENGTH_SHORT).show();
        }
    };

    private void init() {
        //初始化List
//        mDateList = new ArrayList<>();
        //得到今天往后的90天
        List<String> listDate = getDateList();
        //设置宽高
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //为每一个小方格设置监听（每一天）
        for (int i = 0; i < listDate.size(); i++) {
            c1 = new ManageCalendar(this);
            c1.setLayoutParams(params);
            Date date = null;
            try {
                date = simpleDateFormat.parse(listDate.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //传递List
            c1.setBuyDay(userBuyList);
            c1.setNotDay(sqlNotList);
            //对每个方格设置天数
            c1.setTheDay(date);
            //对日历进行点击监听
            c1.setOnDaySelectListener(this);
            ll.addView(c1);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDaySelectListener(View view, String date) {
        //若日历日期小于当前日期，或日历日期-当前日期超过三个月，则不能点击
        try {
            if (simpleDateFormat.parse(date).getTime() < simpleDateFormat.parse(nowday).getTime()) {
                return;
            }
            long dayxc = (simpleDateFormat.parse(date).getTime() - simpleDateFormat.parse(nowday).getTime()) / nd;
            if (dayxc > 90) {
                return;
            }

            //判断点击的这天是否已经被出租出去了
            for (HouseDateManage hdm : userBuyList) {
                if (simpleDateFormat.parse(date).getTime()
                        == simpleDateFormat.parse(hdm.getDate_not_use()).getTime()) {
                    return;
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String dateDay = date.split("-")[2];
        if (Integer.parseInt(dateDay) < 10) {
            dateDay = date.split("-")[2].replace("0", "");
        }
        TextView textDayView = (TextView) view.findViewById(R.id.tv_calendar_day);
        TextView textView = (TextView) view.findViewById(R.id.tv_calendar);

        //此处应该先判断，数据库中的日期是否已经过时，如果过时，将List数据清空，

        if (sqlNotList.size() == 0) {
            //List为0，说明数据库中没有数据，也就是用户没有修改过
//            view.setBackgroundColor(Color.parseColor("#33B5E5"));
            textDayView.setTextColor(Color.WHITE);
            view.setBackground(getResources().getDrawable(R.drawable.date_manage));
            textView.setText("已关");
            mHouseDateManage = new HouseDateManage();
            mHouseDateManage.setHouse_id(houseid);
            mHouseDateManage.setDate_not_use(date);
            mHouseDateManage.setDate_manage_type(2);
            sqlNotList.add(mHouseDateManage);
//            mDateList.add(date);
        } else {
            for (int i = 0; i < sqlNotList.size(); i++) {
                //i是最后一个，并且，List(i)不等于当前点击的时间，证明，之前这个日期没有点击过
                if (!date.equals(sqlNotList.get(i).getDate_not_use())
                        && i == sqlNotList.size() - 1) {
                    //如果单击的时间不等于List中的时间，设置为已关
//                    view.setBackgroundColor(Color.parseColor("#33B5E5"));
                    textDayView.setTextColor(Color.WHITE);
                    view.setBackground(getResources().getDrawable(R.drawable.date_manage));
                    textView.setText("已关");
                    mHouseDateManage = new HouseDateManage();
                    mHouseDateManage.setHouse_id(houseid);
                    mHouseDateManage.setDate_not_use(date);
                    mHouseDateManage.setDate_manage_type(2);

                    sqlNotList.add(mHouseDateManage);
                    return;
                } else if (date.equals(sqlNotList.get(i).getDate_not_use())) {
                    //如果等于List中的时间，说明之前这个日期有点击过
                    view.setBackgroundColor(Color.WHITE);
                    textDayView.setTextColor(Color.parseColor("#FF6600"));
                    textView.setText("");
                    sqlNotList.remove(i);
//                    mDateList.remove(date);
                    return;
                }
            }

        }
        /*//原有的逻辑。写一个List<String> 存储点击的日期

        if (mDateList.size() == 0) {
            view.setBackgroundColor(Color.parseColor("#33B5E5"));
            textDayView.setTextColor(Color.WHITE);
            textView.setText("已关");
            mDateList.add(date);
        } else {
            for (int i = 0; i < mDateList.size(); i++) {
                //i是最后一个，并且，List(i)不等于当前点击的时间，证明，之前这个日期没有点击过
                if (!date.equals(mDateList.get(i)) && i == mDateList.size() - 1) {
                    //如果单击的时间不等于List中的时间，设置为已关
                    view.setBackgroundColor(Color.parseColor("#33B5E5"));
                    textDayView.setTextColor(Color.WHITE);
                    textView.setText("已关");
                    mDateList.add(date);
                    return;
                } else if (date.equals(mDateList.get(i))) {
                    //如果等于List中的时间，说明之前这个日期有点击过
                    view.setBackgroundColor(Color.WHITE);
                    textDayView.setTextColor(Color.parseColor("#FF6600"));
                    textView.setText("");
                    mDateList.remove(date);
                    return;
                }
            }

        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击了保存按钮
            case R.id.data_manage_save:
//                Toast.makeText(DataManageActivity.this, "开始哦。。。。", Toast.LENGTH_SHORT).show();

                // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
//                requestQueue = NoHttp.newRequestQueue();
                // 创建请求对象
//                request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
                // 添加请求参数
                Gson gson = new Gson();
                String houseDate = gson.toJson(sqlNotList);
                request.add("methods", "updateHouseDate");
                request.add("houseDate", houseDate);
                requestQueue.add(HOUSE_DATE_ADD, request, onResponseListener);
//                Toast.makeText(DataManageActivity.this, "结束了。。。。", Toast.LENGTH_SHORT).show();
                break;
            //点击了返回按钮
            case R.id.data_manage_back:
                finish();
                break;
        }
    }

    //根据当前日期，向后数三个月（若当前day不是1号，为满足至少90天，则需要向后数4个月）
    @SuppressLint("SimpleDateFormat")
    public List<String> getDateList() {
        List<String> list = new ArrayList<String>();
        Date date = new Date();
        int nowMon = date.getMonth() + 1;
        String yyyy = sd1.format(date);
        String dd = sd2.format(date);
        if (nowMon == 9) {
            list.add(simpleDateFormat.format(date));
            list.add(yyyy + "-10-" + dd);
            list.add(yyyy + "-11-" + dd);
            if (!dd.equals("01")) {
                list.add(yyyy + "-12-" + dd);
            }
        } else if (nowMon == 10) {
            list.add(yyyy + "-10-" + dd);
            list.add(yyyy + "-11-" + dd);
            list.add(yyyy + "-12-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            }
        } else if (nowMon == 11) {
            list.add(yyyy + "-11-" + dd);
            list.add(yyyy + "-12-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-02-" + dd);
            }
        } else if (nowMon == 12) {
            list.add(yyyy + "-12-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-02-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-03-" + dd);
            }
        } else {
            list.add(yyyy + "-" + getMon(nowMon) + "-" + dd);
            list.add(yyyy + "-" + getMon((nowMon + 1)) + "-" + dd);
            list.add(yyyy + "-" + getMon((nowMon + 2)) + "-" + dd);
            if (!dd.equals("01")) {
                list.add(yyyy + "-" + getMon((nowMon + 3)) + "-" + dd);
            }
        }
        return list;
    }

    public String getMon(int mon) {
        String month = "";
        if (mon < 10) {
            month = "0" + mon;
        } else {
            month = "" + mon;
        }
        return month;
    }


}
