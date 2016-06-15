package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.HouseCollectAdpter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonUserCollect;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.user.User;
import com.easygo.view.WaitDialog;
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
import java.util.ArrayList;
import java.util.List;

public class HouseCollectionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int WHAT_USER_COLLECT = 20;
    public static final int WHAT_DELETECOLLECT = 21;
    private WaitDialog mDialog;

    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    TextView mTextView;
    ImageView mBackImageView;

    GsonUserCollect mGsonUserCollect;
    List<House> mHouseList;
    List<HousePhoto> mHousePhotoList;
    List<User> mUserList;//房东
    List<Integer> mAssessList;
    List<Integer> starNumList;
    HouseCollectAdpter mAdpter;

    String mPath;
    //请求队列
    private RequestQueue requestQueue;
    Request<String> request;
    //PullToRefreshListView实例
    PullToRefreshListView mPullToRefreshListView;

    private int cur = 1;
    private int userid;//从偏好设置中获取
    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            String result = response.get();// 响应结果
            switch (what) {
                case WHAT_USER_COLLECT:
                    //得到该用户的房源收藏列表
                    // 请求成功
                    //把JSON格式的字符串改为Student对象
                    Gson gson = new Gson();
                    Type type = new TypeToken<GsonUserCollect>() {
                    }.getType();
                    mGsonUserCollect = gson.fromJson(result, type);
                    if (mGsonUserCollect.getHouselist().size() == 0) {
                        Toast.makeText(HouseCollectionActivity.this, "没有更多收藏了~", Toast.LENGTH_SHORT).show();
                    }
                    //房源信息
                    mHouseList.addAll(mGsonUserCollect.getHouselist());
                    //房东信息
                    mUserList.addAll(mGsonUserCollect.getUserlist());
                    //房源主图信息
                    mHousePhotoList.addAll(mGsonUserCollect.getHousephotolist());
                    mAssessList.addAll(mGsonUserCollect.getAssessList());
                    starNumList.addAll(mGsonUserCollect.getStarNumList());
                    //mHouseCollectList.addAll(gsonAboutHouse.getHouseCollectList());
                    //通知刷新
                    mAdpter.notifyDataSetChanged();
                    //表示刷新完成
                    mPullToRefreshListView.onRefreshComplete();
                    break;
                case WHAT_DELETECOLLECT:
                    //取消收藏
                    // Toast.makeText(HouseCollectionActivity.this, "取消"+result, Toast.LENGTH_SHORT).show();
//                    cur =1;
//                    new LoadDataAsyncTask(HouseCollectionActivity.this).execute();//执行下载数据
//                    mAdpter.notifyDataSetChanged();
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(HouseCollectionActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(HouseCollectionActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(HouseCollectionActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(HouseCollectionActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(HouseCollectionActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(HouseCollectionActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(HouseCollectionActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(HouseCollectionActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_collection);
        initViews();
        initData();
        //2.初始化数据
        loadData(cur, userid);
        //3.设置上拉加载下拉刷新组件和事件监听
        //设置刷新模式为BOTH才可以上拉和下拉都能起作用,必须写在前面
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时头部的状态
        initRefreshListView();
        //添加各种监听
        addListener();

    }

    private void initViews() {
        mDialog = new WaitDialog(this);//提示框
        mTextView = (TextView) findViewById(R.id.title_text);
        mTextView.setText("我的收藏");
        mBackImageView = (ImageView) findViewById(R.id.back);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.house_collection_listview);
    }

    private void initData() {
        mSharedPreferences = getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        userid = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        //初始化数据
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        mHouseList = new ArrayList<>();
        mUserList = new ArrayList<>();
        mHousePhotoList = new ArrayList<>();
        mAssessList = new ArrayList<>();
        starNumList = new ArrayList<>();
        mAdpter = new HouseCollectAdpter(HouseCollectionActivity.this, mHouseList, mUserList,
                mHousePhotoList, mAssessList, starNumList, userid);
        mPullToRefreshListView.setAdapter(mAdpter);
    }

    //加载数据 获取该user的房源收藏数据
    private void loadData(int cur, int user_id) {
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "selecthousecollect");
        request.add("cur", cur);
        request.add("user_id", user_id);
        requestQueue.add(WHAT_USER_COLLECT, request, mOnResponseListener);
    }

    private void initRefreshListView() {
        ILoadingLayout startLabels = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

    private void addListener() {
        mBackImageView.setOnClickListener(this);
        //设置上拉和下拉时候的监听器
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉时
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                cur = 1;
                //清空List
                mHouseList.clear();
                mUserList.clear();
                mHousePhotoList.clear();
                mAssessList.clear();
                starNumList.clear();
                //  mHouseCollectList.clear();
                new LoadDataAsyncTask(HouseCollectionActivity.this).execute();//执行下载数据
                mAdpter.notifyDataSetChanged();
            }

            //上拉时
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //页码加一
                cur++;
                new LoadDataAsyncTask(HouseCollectionActivity.this).execute();
                mAdpter.notifyDataSetChanged();
            }
        });
        //点击进入对应的房源
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到详情页面需要的房屋信息，房东信息，
                //传参
                Intent intent = new Intent(HouseCollectionActivity.this,
                        HouseDetailActivity.class);
                Log.e("position", position + "");
                Log.e("mHouseList", mHouseList.get(position - 1).getHouse_id() + "");
                int houseid = mHouseList.get(position - 1).getHouse_id();
                intent.putExtra("houseid", houseid);
                startActivity(intent);
            }
        });
    }


    static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private HouseCollectionActivity activity;
        private int cur;
        private int userid;

        //初始化三个参数
        public LoadDataAsyncTask(HouseCollectionActivity activity) {
            this.activity = activity;
            this.cur = activity.cur;
            this.userid = activity.userid;
        }

        @Override
        protected String doInBackground(Void... params) {
//            Log.e("zz",""+cur);
            //加载数据
            activity.loadData(cur, userid);
            return "success";
        }

        //  onPostExecute（）是对返回的值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                activity.mAdpter.notifyDataSetChanged();//通知数据集改变,界面刷新
                activity.mPullToRefreshListView.onRefreshComplete();//表示刷新完成
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    //删除一条收藏
    public void deleteCollect(int delUserid, int delHouseid,Context context) {
        mDialog = new WaitDialog(context);
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(new MyApplication().getUrl(), RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "deleteHouseCollectById");
        request.add("houseid", delHouseid);
        request.add("userid", delUserid);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(WHAT_DELETECOLLECT, request, mOnResponseListener);

    }
}
