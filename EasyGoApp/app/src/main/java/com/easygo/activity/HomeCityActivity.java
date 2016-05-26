package com.easygo.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.HouseListAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.House;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeCityActivity extends AppCompatActivity {

    /**
     * 用来标志请求的what, 类似handler的what一样，这里用来区分请求.
     */
    private static final int NOHTTP_WHAT_TEST = 0x001;



    /**
     * 请求队列.
     */
    private RequestQueue requestQueue;

    PullToRefreshListView mPullToRefreshListView;//PullToRefreshListView实例
    List<House> mList = null;
    HouseListAdapter mAdapter;
    String mPath;
    //筛选菜单用到的控件
    private Spinner housespinner, sexspinner, pricespinner, checkspinner;
    private List<String> data_list_housetype, data_list_sexs, data_list_pricesort, data_list_checknum;
    private SpinnerAdapter housetype_adapter, sexs_adapter, pricesort_adapter, checknum_adapter;

    private String housetype[];
    private String sexs[];
    private String pricesort[];
    private String checknum[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_city);
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
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeCityActivity.this, "房源" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //适配器
        housetype_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, data_list_housetype, housetype);
        sexs_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, data_list_sexs, sexs);
        pricesort_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, data_list_pricesort, pricesort);
        checknum_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, data_list_checknum, checknum);
        //设置样式
        housetype_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexs_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pricesort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        checknum_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        housespinner.setAdapter(housetype_adapter);
        sexspinner.setAdapter(sexs_adapter);
        pricespinner.setAdapter(pricesort_adapter);
        checkspinner.setAdapter(checknum_adapter);

        initLinsenter();
    }

    private void initLinsenter() {
        housespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeCityActivity.this,"房源类型"+housetype[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sexspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeCityActivity.this,"性别限制"+sexs[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        pricespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeCityActivity.this,"价格限制"+pricesort[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        checkspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeCityActivity.this,"入住时间"+checknum[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initData() {
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        mList = new ArrayList<>();
        mAdapter = new HouseListAdapter(HomeCityActivity.this, mList);
        mPullToRefreshListView.setAdapter(mAdapter);

        //筛选条件数据
        data_list_housetype = new ArrayList<>();
        housetype = getResources().getStringArray(R.array.selectbed);
        for (int i = 0; i < housetype.length; i++) {
            data_list_housetype.add(housetype[i]);
        }

        //数据
        data_list_sexs = new ArrayList<>();
        sexs = getResources().getStringArray(R.array.selectsex);
        for (int i = 0; i < sexs.length; i++) {
            data_list_sexs.add(sexs[i]);
        }

        //数据
        data_list_pricesort = new ArrayList<>();
        pricesort = getResources().getStringArray(R.array.selectprice);
        for (int i = 0; i < pricesort.length; i++) {
            data_list_pricesort.add(pricesort[i]);
        }

        //数据
        data_list_checknum = new ArrayList<>();
        checknum = getResources().getStringArray(R.array.selecttime);
        for (int i = 0; i < checknum.length; i++) {
            data_list_checknum.add(checknum[i]);
        }
    }

    private void initViews() {
        //1.find the listview
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.home_city_listview);
        housespinner = (Spinner) findViewById(R.id.housespinner);
        sexspinner = (Spinner) findViewById(R.id.sexspinner);
        pricespinner = (Spinner) findViewById(R.id.pricespinner);
        checkspinner = (Spinner) findViewById(R.id.checkspinner);
    }

    private int count = 1;

    //模拟数据
    public void loadData() {
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "getAllHouse");
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(NOHTTP_WHAT_TEST, request, onResponseListener);

    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == NOHTTP_WHAT_TEST) {
                // 请求成功
                String result = response.get();// 响应结果
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type type = new TypeToken<List<House>>(){}.getType();
//                mList = gson.fromJson(result,type);
                mList.addAll((List<House>)gson.fromJson(result,type));
                mAdapter.notifyDataSetChanged();
                //表示刷新完成
                mPullToRefreshListView.onRefreshComplete();
            }
        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
            Toast.makeText(HomeCityActivity.this, "开始了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish(int what) {
            Toast.makeText(HomeCityActivity.this, "结束了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(HomeCityActivity.this, "失败了", Toast.LENGTH_SHORT).show();
        }
    };


    /*//出现问题，所以没用
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()){
            case R.id.housespinner:
                Toast.makeText(HomeCityActivity.this,"房源类型"+housetype[position],Toast.LENGTH_SHORT).show();
                break;
            case R.id.sexspinner:
                Toast.makeText(HomeCityActivity.this,"性别限制"+sexs[position],Toast.LENGTH_SHORT).show();
                break;
            case R.id.pricespinner:
                Toast.makeText(HomeCityActivity.this,"价格限制"+pricesort[position],Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkspinner:
                Toast.makeText(HomeCityActivity.this,"入住时间"+checknum[position],Toast.LENGTH_SHORT).show();
                break;
        }
    }*/

    /*@Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private HomeCityActivity activity;

        public LoadDataAsyncTask(HomeCityActivity activity) {
            this.activity = activity;
        }
        @Override
        protected String doInBackground(Void... params) {
            //用一个线程来模拟刷新
           /* try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
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
        startLabels.setRefreshingLabel("正在刷新");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

    //设置spinner的字体样式
    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[]{};

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, List<String> data_list_housetype, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, 10, 0, 10);
            //  tv.setTextColor(Color.BLUE);
            tv.setTextSize(16);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            // android.R.id.text1 is default text view in resource of the android.
            // android.R.layout.simple_spinner_item is default layout in resources of android.

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            return convertView;
        }
    }
}
