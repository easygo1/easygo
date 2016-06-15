package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.HouseListAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonAboutHouse;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HouseCollect;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.user.User;
import com.easygo.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchHouseActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TYPE = "type";
    public static final int WHAT_LOAD_DATA = 10;
    TextView mCityTextView;
    ImageView mBackImageView, mCityImageView;
    //    ListView mHouseListView;//房源list
    TextView mAddContactTextView, mInTextView, mOutTextView, mSendTextView;

    //用来加载头布局
    LayoutInflater mInflater;
    View mBeforeListView, mCheckLayout, mLeaveLayout;
    HouseListAdapter mAdapter;//适配器


    //item中需要使用的数据List
    List<House> mHouseList = null;
    List<User> mUserList = null;
    List<HousePhoto> mHousePhotoList = null;
    List<Integer> mAssessList = null;
    List<HouseCollect> mHouseCollectList = null;
    List<Integer> starNumList = null;

    int user_id, cur;
    String mPath;
    String mTimeListString;//搜索得到的入住时间列表
    List<Date> mDateList;
    List<String> mTimeList;//搜索得到的入住时间列表
    String searchdate, searchcity, inday, outday;
    //搜索的城市名称入住离开时间

    //请求队列
    private RequestQueue requestQueue;
    Request<String> request;
    //PullToRefreshListView实例
    PullToRefreshListView mPullToRefreshListView;
    SharedPreferences mSharedPreferences, mDateSharedPreferences;//偏好设置
    GsonAboutHouse gsonAboutHouse;//解析对象

    private final static String DATE_FORMAT = "yyyy-MM-dd";
    java.text.SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_house);
        mDateSharedPreferences = getSharedPreferences("date", Context.MODE_PRIVATE);
        mSharedPreferences = getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        initListView();//初始化list，必须放在前面
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //设置刷新时头部的状态
        initRefreshListView();
        initData();
        initview();
        addlisener();
//        new LoadDataAsyncTask(SearchHouseActivity.this).execute();//执行下载数据
        //getData();
        // new Thread(new GameThread()).start();
    }

    /*private class GameThread implements Runnable {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(400);
                } catch(Exception e){
                    Thread.currentThread().interrupt();
                }
                //使用postInvalidate可以直接在线程中刷新
                mHouseListView.postInvalidate();
            }
        }

    }*/
    @Override
    protected void onStart() {
        super.onStart();
        mTimeList = new ArrayList<>();
        //偏好设置中取出
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        inday = mDateSharedPreferences.getString("dateIn", "");
        outday = mDateSharedPreferences.getString("dateOut", "");
        searchcity = mSharedPreferences.getString("searchcity", "");
        if (!"".equals(inday)) {
            mInTextView.setText(inday);
        }
        if (!"".equals(outday)) {
            mOutTextView.setText(outday);
        }
        Date indate = null;
        try {
            indate = formatter.parse(inday);
            Date outdate = formatter.parse(outday);
            mDateList = DateUtils.getDaysListBetweenDates(indate, outdate);
            for (int i = 0; i < mDateList.size(); i++) {
                searchdate = formatter.format(mDateList.get(i));//格式化数据
                mTimeList.add(searchdate);
            }
            mTimeListString = mTimeList.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!searchcity.equals("") && !inday.equals("")) {
            getData(mTimeListString, searchcity);
            mCityTextView.setText(searchcity);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initListView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.search_house_listview);
    }

    public void initRefreshListView() {
        ILoadingLayout startLabels = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

    private void initData() {
        //Intent intent = getIntent();
        //mTimeListString = intent.getStringExtra("timelist");
        // Log.e("timelist", intent.getStringExtra("timelist"));
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        //集合的初始化
        mHouseList = new ArrayList<>();
        mUserList = new ArrayList<>();
        mHousePhotoList = new ArrayList<>();
        mAssessList = new ArrayList<>();
        mHouseCollectList = new ArrayList<>();
        starNumList = new ArrayList<>();
        //初始化适配器
        mAdapter = new HouseListAdapter(SearchHouseActivity.this, mHouseList, mUserList, mHousePhotoList, mAssessList, mHouseCollectList, user_id, starNumList);

    }

    private void initview() {
        //得到ListView之前的布局
        mInflater = LayoutInflater.from(SearchHouseActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.search_house_before, null);
        //listview头布局
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        mBeforeListView.setLayoutParams(layoutParams);
        ListView listview = mPullToRefreshListView.getRefreshableView();
        listview.addHeaderView(mBeforeListView);

        //添加适配器
        mPullToRefreshListView.setAdapter(mAdapter);
        mCityTextView = (TextView) findViewById(R.id.title_text);
        // mCityTextView.setText("上海");
        mBackImageView = (ImageView) findViewById(R.id.back);
        mCityImageView = (ImageView) findViewById(R.id.search_house_city);

        mCheckLayout = mBeforeListView.findViewById(R.id.search_inday_layout);
        mLeaveLayout = mBeforeListView.findViewById(R.id.search_outday_layout);
        mInTextView = (TextView) mBeforeListView.findViewById(R.id.search_inday);
        mOutTextView = (TextView) mBeforeListView.findViewById(R.id.search_outday);
       /* if (!"".equals(inday)) {
            mInTextView.setText(inday);
        }
        if (!"".equals(outday)) {
            mOutTextView.setText(outday);
        }*/
    }

    private void addlisener() {
//        Log.e("searchcity",searchcity);
//        mCityTextView.setText(searchcity);
        mBackImageView.setOnClickListener(this);
        mCityImageView.setOnClickListener(this);
        mCheckLayout.setOnClickListener(SearchHouseActivity.this);
        mLeaveLayout.setOnClickListener(SearchHouseActivity.this);
        mInTextView.setOnClickListener(SearchHouseActivity.this);
        mOutTextView.setOnClickListener(SearchHouseActivity.this);
        //设置上拉和下拉时候的监听器
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉时
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // cur = 1;
                //清空List
                mHouseList.clear();
                mUserList.clear();
                mHousePhotoList.clear();
                mAssessList.clear();
                mHouseCollectList.clear();
                starNumList.clear();
                new LoadDataAsyncTask(SearchHouseActivity.this).execute();//执行下载数据
                mAdapter.notifyDataSetChanged();
            }

            //上拉时
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //页码加一
                // cur++;
                // new LoadDataAsyncTask(SearchHouseActivity.this).execute();
            }
        });
        //点击进入对应的房源
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到详情页面需要的房屋信息，房东信息，
                //传参
                Intent intent = new Intent(SearchHouseActivity.this,
                        HouseDetailActivity.class);
//                Log.e("position",position+"");
//                Log.e("mHouseList",mHouseList.get(position-1).getHouse_id()+"");
                int houseid = mHouseList.get(position - 1).getHouse_id();
                intent.putExtra("houseid", houseid);
                startActivity(intent);
            }
        });
    }

    static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private SearchHouseActivity activity;
        private String Searchcity;
        private int cur;
        private String mTimeListString;
        private int userid;

        //初始化三个参数
        public LoadDataAsyncTask(SearchHouseActivity activity) {
            this.activity = activity;
            // city = activity.city;
            cur = activity.cur;
            mTimeListString = activity.mTimeListString;
            userid = activity.user_id;
            Searchcity = activity.searchcity;
        }

        @Override
        protected String doInBackground(Void... params) {
//            Log.e("zz",""+cur);
            //加载数据
            activity.getData(mTimeListString, Searchcity);
            return "success";
        }

        //  onPostExecute（）是对返回的值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                activity.mAdapter.notifyDataSetChanged();//通知数据集改变,界面刷新
                activity.mPullToRefreshListView.onRefreshComplete();//表示刷新完成
            }
        }
    }


    private void getData(String TimeListString, String searchcity) {
//        Log.e("timelist", TimeListString);
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象

        request = NoHttp.createStringRequest(mPath, RequestMethod.GET);
        // 添加请求参数
        request.add("methods", "searchHouse");
        request.add("timelist", TimeListString);
        request.add("searchcity", searchcity + "市");
        request.add("user_id", user_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(WHAT_LOAD_DATA, request, onResponseListener);

    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            String result = response.get();
            switch (what) {
                case WHAT_LOAD_DATA:
//                   Log.e("result",result);
                    // Log.e("result",result);
                    /*switch (result){
                        case "没有搜索到您要查找的房源":
                            mHouseList.clear();
                            mUserList.clear();
                            mHousePhotoList.clear();
                            mAssessList.clear();
                            mHouseCollectList.clear();
                            starNumList.clear();
                            new LoadDataAsyncTask(SearchHouseActivity.this).execute();//执行下载数据
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(SearchHouseActivity.this,result, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            //把JSON格式的字符串改为Student对象
                            Gson gson = new Gson();
                            Type type = new TypeToken<GsonAboutHouse>() {
                            }.getType();
                            gsonAboutHouse = gson.fromJson(result, type);
                            Log.e("搜索结果", ""+gsonAboutHouse.getHouseList().size());
                            if (gsonAboutHouse.getHouseList().size() == 0) {
                                Toast.makeText(SearchHouseActivity.this, "没有更多房源了~", Toast.LENGTH_SHORT).show();
                                mAdapter.notifyDataSetChanged();
                            }
                            //Toast.makeText(SearchHouseActivity.this, "qwqw" + gsonAboutHouse.getHouseList().size(), Toast.LENGTH_SHORT).show();
                            mHouseList.addAll(gsonAboutHouse.getHouseList());
                            mUserList.addAll(gsonAboutHouse.getUserList());
                            mHousePhotoList.addAll(gsonAboutHouse.getHousePhotoList());
                            mAssessList.addAll(gsonAboutHouse.getAssessList());
                            mHouseCollectList.addAll(gsonAboutHouse.getHouseCollectList());
                            starNumList.addAll(gsonAboutHouse.getStarNumList());
                            //通知刷新
                            mAdapter.notifyDataSetChanged();
                            //表示刷新完成
                            break;
                    }*/
                    if (result.equals("没有搜索到您要查找的房源") || result == null) {
                        Toast.makeText(SearchHouseActivity.this, "没有搜索到您要查找的房源", Toast.LENGTH_SHORT).show();
                        mHouseList.clear();
                        mUserList.clear();
                        mHousePhotoList.clear();
                        mAssessList.clear();
                        mHouseCollectList.clear();
                        starNumList.clear();
                        mAdapter.notifyDataSetChanged();
                    } else {
                        //把JSON格式的字符串改为Student对象
                        Gson gson = new Gson();
                        Type type = new TypeToken<GsonAboutHouse>() {
                        }.getType();
                        gsonAboutHouse = gson.fromJson(result, type);
                        Log.e("搜索结果", "" + gsonAboutHouse.getHouseList().size());
                        if (gsonAboutHouse.getHouseList().size() == 0) {
                            Toast.makeText(SearchHouseActivity.this, "没有更多房源了~", Toast.LENGTH_SHORT).show();
                            mAdapter.notifyDataSetChanged();
                        }
                        //Toast.makeText(SearchHouseActivity.this, "qwqw" + gsonAboutHouse.getHouseList().size(), Toast.LENGTH_SHORT).show();
                        mHouseList.addAll(gsonAboutHouse.getHouseList());
                        mUserList.addAll(gsonAboutHouse.getUserList());
                        mHousePhotoList.addAll(gsonAboutHouse.getHousePhotoList());
                        mAssessList.addAll(gsonAboutHouse.getAssessList());
                        mHouseCollectList.addAll(gsonAboutHouse.getHouseCollectList());
                        starNumList.addAll(gsonAboutHouse.getStarNumList());
                        //通知刷新
                        mAdapter.notifyDataSetChanged();
                        //表示刷新完成
                    }
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(SearchHouseActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(SearchHouseActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(SearchHouseActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(SearchHouseActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(SearchHouseActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(SearchHouseActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(SearchHouseActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(SearchHouseActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_house_city:
                //选择城市
                intent.setClass(SearchHouseActivity.this, SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.search_inday_layout:
                intent.setClass(SearchHouseActivity.this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.search_outday_layout:
                intent.setClass(SearchHouseActivity.this, CalendarActivity.class);
                startActivity(intent);
                break;
        }
    }
}
