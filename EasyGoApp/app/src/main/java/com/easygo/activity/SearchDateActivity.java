package com.easygo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonAboutHouseManage;
import com.easygo.beans.house.HouseDateManage;
import com.easygo.view.BookCalendar;
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
*  申请预定时日期的页面
*  会显示可租和不可租，用户可以点击选择入住时间和离开时间
*/
@SuppressLint("SimpleDateFormat")
public class SearchDateActivity extends AppCompatActivity
        implements BookCalendar.OnDaySelectListener, View.OnClickListener {
    public static final int GET_DATE_WHAT = 1;
    public static final int HOUSE_DATE_ADD = 2;
    public static final int BOOK_DATE_RESULT_CODE = 1;
    //网络请求队列
    private RequestQueue requestQueue;
    Request<String> request;
    String mPath;
    int house_id;//暂时认定为存在Houseid
    GsonAboutHouseManage gsonAboutHouseManage;
    //数据库中房东设置的已关的时间列
    List<HouseDateManage> sqlNotList;
    //数据库中被租出去的时间列
    List<HouseDateManage> userBuyList;
    //日期管理实体
    HouseDateManage mHouseDateManage;
    //布局相关
    LinearLayout ll;
    BookCalendar c1;
    Date date;
    String nowday, inday = null, outday = null;//今天，入住的日期，离开的日期
    long nd = 1000 * 24L * 60L * 60L;//一天的毫秒数
    SimpleDateFormat simpleDateFormat, sd1, sd2;
    //存放这一天是否被选中了
//    List<String> mDateList;
    //标题栏的两个按钮
    TextView mSaveTextView, mTitleTextView;
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
        mTitleTextView = (TextView) findViewById(R.id.data_manage_title);
        mSaveTextView = (TextView) findViewById(R.id.data_manage_save);
        mBackImageView = (ImageView) findViewById(R.id.data_manage_back);
        mTitleTextView.setText("选择日期");
        mSaveTextView.setVisibility(View.GONE);
        mSaveTextView.setOnClickListener(this);
        mBackImageView.setOnClickListener(this);
    }

    private void loadData() {
        //得到查询的房屋的id
        Intent mIntent = getIntent();
        house_id = mIntent.getIntExtra("house_id", 0);
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
        request.add("houseid", house_id);
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
//                Log.e("gson", result);
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
            Toast.makeText(SearchDateActivity.this, "失败了", Toast.LENGTH_SHORT).show();
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
            c1 = new BookCalendar(this);
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
            //判断点击的这天是否已经被房东设置为不可租
            for (HouseDateManage hdm : sqlNotList) {
                if (simpleDateFormat.parse(date).getTime()
                        == simpleDateFormat.parse(hdm.getDate_not_use()).getTime()) {
                    return;
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*String dateDay = date.split("-")[2];
        if (Integer.parseInt(dateDay) < 10) {
            dateDay = date.split("-")[2].replace("0", "");
        }*/
        TextView textDayView = (TextView) view.findViewById(R.id.tv_calendar_day);
        TextView textView = (TextView) view.findViewById(R.id.tv_calendar);

        //此处应该先判断，数据库中的日期是否已经过时，如果过时，将List数据清空，
        if (inday == null) {
//            textDayView.setText(date);
            textView.setText("入住");
            textDayView.setTextColor(Color.WHITE);
            view.setBackground(getResources().getDrawable(R.drawable.date_manage));
            inday = date;
        } else {
            //离开日期不能小于入住日期
            try {
                if (simpleDateFormat.parse(date).getTime()
                        < simpleDateFormat.parse(inday).getTime()) {
                    view.setBackgroundColor(Color.WHITE);
                    textDayView.setTextColor(Color.BLACK);
                    Toast.makeText(SearchDateActivity.this, "离开日期不能小于入住日期", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            textDayView.setTextColor(Color.WHITE);
            view.setBackground(getResources().getDrawable(R.drawable.date_manage));
            textView.setText("离开");
            outday = date;

            Intent intent = new Intent();
            intent.putExtra("inday", inday);
            intent.putExtra("outday", outday);
//            Bundle bundle = new Bundle();
//            intent.putExtras(bundle);
            setResult(BOOK_DATE_RESULT_CODE, intent);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击了保存按钮
            case R.id.data_manage_save:
//                Toast.makeText(DateManageActivity.this, "开始哦。。。。", Toast.LENGTH_SHORT).show();

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
//                Toast.makeText(DateManageActivity.this, "结束了。。。。", Toast.LENGTH_SHORT).show();
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
