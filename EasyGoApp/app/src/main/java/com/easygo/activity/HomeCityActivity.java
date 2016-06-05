package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.easygo.beans.gson.GsonAboutHouse;
import com.easygo.beans.house.House;
import com.easygo.beans.house.HouseCollect;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.user.User;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeCityActivity extends AppCompatActivity {

    /**
     * 用来标志请求的what, 类似handler的what一样，这里用来区分请求.
     */
    private static final int NOHTTP_WHAT_LOAD = 0x001;
    public static final int NOHTTP_WHAT_DELETECOLLECT = 2;
    public static final int NOHTTP_WHAT_ADDCOLLECT = 3;

    //请求队列
    private RequestQueue requestQueue;
    Request<String> request;
    //PullToRefreshListView实例
    PullToRefreshListView mPullToRefreshListView;
    //item中需要使用的数据List
    List<House> mHouseList = null;
    List<User> mUserList = null;
    List<HousePhoto> mHousePhotoList = null;
    List<Integer> mAssessList = null;
    List<HouseCollect> mHouseCollectList = null;

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

    //下载数据时用的参数
    private String city = "苏州市";
    private int cur = 1;
    private int userid = 1;
    GsonAboutHouse gsonAboutHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_city);
        initViews();
        initData();
        //2.初始化数据
        loadData(city, cur, userid);
        //3.设置上拉加载下拉刷新组件和事件监听
        //设置刷新模式为BOTH才可以上拉和下拉都能起作用,必须写在前面
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时头部的状态
        initRefreshListView();
        //添加各种监听
        addListener();

    }


    private void initData() {
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        //集合的初始化
        mHouseList = new ArrayList<>();
        mUserList = new ArrayList<>();
        mHousePhotoList = new ArrayList<>();
        mAssessList = new ArrayList<>();
        mHouseCollectList = new ArrayList<>();
        //适配器初始化
        mAdapter = new HouseListAdapter(HomeCityActivity.this,
                mHouseList, mUserList, mHousePhotoList, mAssessList, mHouseCollectList);
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

        //筛选的适配器
        housetype_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item,
                data_list_housetype, housetype);
        sexs_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item,
                data_list_sexs, sexs);
        pricesort_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item,
                data_list_pricesort, pricesort);
        checknum_adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item,
                data_list_checknum, checknum);
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
    }

    private void initViews() {
        //1.find the listview
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.home_city_listview);
        housespinner = (Spinner) findViewById(R.id.housespinner);
        sexspinner = (Spinner) findViewById(R.id.sexspinner);
        pricespinner = (Spinner) findViewById(R.id.pricespinner);
        checkspinner = (Spinner) findViewById(R.id.checkspinner);
    }

    private void addListener() {
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
                mHouseCollectList.clear();
                new LoadDataAsyncTask(HomeCityActivity.this).execute();//执行下载数据
            }

            //上拉时
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //页码加一
                cur++;
                new LoadDataAsyncTask(HomeCityActivity.this).execute();
            }
        });
        //点击进入对应的房源
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到详情页面需要的房屋信息，房东信息，
                //传参
                Intent intent = new Intent(HomeCityActivity.this,
                        HouseDetailActivity.class);
                Log.e("position",position+"");
                Log.e("mHouseList",mHouseList.get(position-1).getHouse_id()+"");

                int houseid = mHouseList.get(position-1).getHouse_id();
                intent.putExtra("houseid", houseid);
                startActivity(intent);
            }
        });
        //筛选相关的
        housespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeCityActivity.this,"房源类型"+housetype[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sexspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeCityActivity.this,"性别限制"+sexs[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        pricespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeCityActivity.this,"价格限制"+pricesort[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        checkspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeCityActivity.this,"入住时间"+checknum[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //获取数据(传入查询的城市，页码，请求的用户id)
    public void loadData(String newCity, int newCur, int user_id) {
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "getAllHouseByCity");
        request.add("city", newCity);
        request.add("cur", newCur);
        request.add("userid", user_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(NOHTTP_WHAT_LOAD, request, onResponseListener);

    }

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
        private String city;
        private int cur;
        private int userid;

        //初始化三个参数
        public LoadDataAsyncTask(HomeCityActivity activity) {
            this.activity = activity;
            city = activity.city;
            cur = activity.cur;
            userid = activity.userid;
        }

        @Override
        protected String doInBackground(Void... params) {
//            Log.e("zz",""+cur);
            //加载数据
            activity.loadData(city, cur, userid);
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
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(18);
            return convertView;
        }
    }

    //删除一条收藏
    public void deleteCollect(int house_collect_id) {
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(new MyApplication().getUrl(), RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "deleteHouseCollect");
        request.add("houseCollectId", house_collect_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(NOHTTP_WHAT_DELETECOLLECT, request, onResponseListener);

    }

    //增加一条收藏
    public void addCollect(int user_id, int house_id) {
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(new MyApplication().getUrl(), RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "addHouseCollect");
        request.add("userid", user_id);
        request.add("houseid", house_id);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
        requestQueue.add(NOHTTP_WHAT_ADDCOLLECT, request, onResponseListener);

    }
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == NOHTTP_WHAT_LOAD) {
                // 请求成功
                String result = response.get();// 响应结果
//                Log.e("tag", result);
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();

                Type type = new TypeToken<GsonAboutHouse>() {
                }.getType();
                gsonAboutHouse = gson.fromJson(result, type);
                if (gsonAboutHouse.getHouseList().size() == 0) {
                    Toast.makeText(HomeCityActivity.this, "没有更多房源了~", Toast.LENGTH_SHORT).show();
                }
                mHouseList.addAll(gsonAboutHouse.getHouseList());
                mUserList.addAll(gsonAboutHouse.getUserList());
                mHousePhotoList.addAll(gsonAboutHouse.getHousePhotoList());
                mAssessList.addAll(gsonAboutHouse.getAssessList());
                mHouseCollectList.addAll(gsonAboutHouse.getHouseCollectList());
                //通知刷新
                mAdapter.notifyDataSetChanged();
                //表示刷新完成
                mPullToRefreshListView.onRefreshComplete();
            }
        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
//            Toast.makeText(HomeCityActivity.this, "开始了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish(int what) {
//            Toast.makeText(HomeCityActivity.this, "结束了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(HomeCityActivity.this, "失败了", Toast.LENGTH_SHORT).show();
        }
    };

}
