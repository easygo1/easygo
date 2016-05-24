package com.easygo.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.easygo.adapter.HomeCityAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.House;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class HomeCityActivity extends AppCompatActivity {


    public static final String TAG = "info";
    PullToRefreshListView mPullToRefreshListView;//PullToRefreshListView实例
    List<House> mList = null;
    HomeCityAdapter mAdapter;
    String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_city);
//        Intent intent = getIntent();
        initViews();
        initData();
        //2.绑定模拟的数据
        loadData();

        //3.设置上拉加载下拉刷新组件和事件监听
        //设置刷新模式为BOTH才可以上拉和下拉都能起作用,必须写在前面
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时头部的状态
        initRefreshListView();
        //设置上拉和下拉时候的监听器
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉时
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(HomeCityActivity.this).execute();//执行下载数据
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(HomeCityActivity.this).execute();
            }
        });

    }

    private void initData() {
        MyApplication myApplication  = (MyApplication)this.getApplication();
        mPath = myApplication.getUrl();
        mList = new ArrayList<>();
        mAdapter = new HomeCityAdapter(HomeCityActivity.this, mList);
        mPullToRefreshListView.setAdapter(mAdapter);
    }

    private void initViews() {
        //1.find the listview
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.home_city_listview);
    }

    private int count = 1;

    //模拟数据
    public void loadData() {

       /* RequestParams params = new RequestParams(mPath);
        //Params
        params.addBodyParameter("methods","getAllHouse");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type type = new TypeToken<List<House>>(){}.getType();
//                mList = gson.fromJson(result,type);
                mList.addAll((List<House>)gson.fromJson(result,type));
                mAdapter.notifyDataSetChanged();
                //表示刷新完成
                mPullToRefreshListView.onRefreshComplete();
                Log.e("list",mList.toString());
                Log.e("aaa",mList.get(2).getHouse_describe());
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error","错误原因：" + ex.getMessage() );
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });*/

        for (int i = 0; i < 10; i++) {
            mList.add(new House("家庭温馨" + count, "独立房间" + count,count));
            count++;
        }
        mAdapter.notifyDataSetChanged();

    }

    static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private HomeCityActivity activity;

        public LoadDataAsyncTask(HomeCityActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(Void... params) {
            //用一个线程来模拟刷新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //加载数据
            activity.loadData();
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

    public void initRefreshListView() {
        ILoadingLayout startLabels = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在拉");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }
}
