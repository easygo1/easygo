package com.easygo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.easygo.application.MyApplication;
import com.easygo.beans.house.HouseDateManage;
import com.easygo.beans.order.Orders;
import com.easygo.utils.DateUtils;
import com.easygo.view.WaitDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    int house_id = 1;//从前面的一个页面出过来
    String inday = "2016-06-15";//入住时间，从前面的一个页面出过来
    String outday = "2016-06-20";//离开时间，从前面的一个页面出过来
    private WaitDialog mDialog;
    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    public static final int WHAT_ISASSESSORDERS = 1;
    private RequestQueue mRequestQueue;
    int order_id = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void test(View view) {
//        Intent intent = new Intent();
//        intent.putExtra("order_id", 17);
//        intent.putExtra("describe","");
//        intent.putExtra("price", "77");
//        intent.putExtra("house_id",2);
//        intent.putExtra("checktime","2016-06-15");
//        intent.putExtra("leavetime","2016-06-20");
//        intent.setClass(TestActivity.this, PayActivity.class);
//        startActivity(intent);
        changeDate();
    }

    public void changeDate() {
        myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        List<HouseDateManage> mHouseDateManageList = new ArrayList<>();
        try {
            Date indate = formatter.parse(inday);
            Date outdate = formatter.parse(outday);
            List<Date> mDateList = DateUtils.getDaysListBetweenDates(indate, outdate);
//            List<String> mTimeList = new ArrayList<>();
            for (int i = 0; i < mDateList.size() - 1; i++) {
                String searchdate = formatter.format(mDateList.get(i));//日期
                HouseDateManage mHouseDateManage = new HouseDateManage();
                mHouseDateManage.setHouse_id(house_id);
                mHouseDateManage.setDate_not_use(searchdate);
                mHouseDateManage.setDate_manage_type(1);//预定的类型
                //封装到List中
                mHouseDateManageList.add(mHouseDateManage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String orderDate = gson.toJson(mHouseDateManageList);
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //添加请求参数
        request.add("methods", "orderpayok");
        request.add("order_id", order_id);
        request.add("orderDate", orderDate);
        // mRequestQueue.add(WHAT_ISASSESSORDERS, request, mOnResponseListener);
        mRequestQueue.add(WHAT_ISASSESSORDERS, request, onResponseListener);
    }
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT_ISASSESSORDERS) {
//                String result = response.get();
//                //把JSON格式的字符串改为Student对象
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<Orders>>(){}.getType();
//                mList = gson.fromJson(result,type);
                // mList.addAll((List<Order>)gson.fromJson(result,type));
                // mCustomOrderAdapter.notifyDataSetChanged();
                //表示刷新完成
//                mPullToRefreshListView.onRefreshComplete();
                // Log.e("list",mList.toString());
            }
        }

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        }

        @Override
        public void onFinish(int what) {

        }
    };

}
