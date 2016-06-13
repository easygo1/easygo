package com.easygo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.easygo.activity.ChatDynamicActivity;
import com.easygo.activity.R;
import com.easygo.adapter.ChatDynamicAdpter;
import com.easygo.application.MyApplication;
import com.easygo.beans.chat_comment.CommentData;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatDynamicFragment extends Fragment {

    //请求对象
    public static final int DYNAMIC = 1;
    public static final int BROWSE = 2;

    //自定义一个dialog
    private WaitDialog mDialog;
    /**
     * 请求队列.
     */
    private RequestQueue mRequestQueue;
    String mUrl;
    //偏好设置
    int type = 0;
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    //定义视图
    View mChatDynamicView;
    //定义适配器
    ChatDynamicAdpter mChatDynamicAdpter;
    //定义集合
    List<CommentData> commentDataList;
    //PullToRefreshListView实例
    PullToRefreshListView commentListView;
    //ListView commentListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatDynamicView = inflater.inflate(R.layout.chat_dynamic, null);
       /* mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        type = mSharedPreferences.getInt("type", 0);*/

        initView();
        initUrl();
        initData();
        //设置刷新时头部的状态
        initRefreshListView();
        //添加下拉刷新的监听
        addListener();
        //获取到点击的是哪个页面
        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里需要做好传值动作
                Intent intent = new Intent(getActivity(), ChatDynamicActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("choosedynamic", (Serializable) commentDataList.get(position-1));

                intent.putExtras(mBundle);
                //当被点击时，进行浏览量+1
                browseamount(commentDataList.get(position-1).getNews_id());
                startActivity(intent);
            }
        });

        return mChatDynamicView;
    }
    //打开动态具体信息，浏览量进行+1
    private void browseamount(int news_id) {
        Log.e("点击的动态id是：",news_id+"");
        //向服务器发出请求
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //请求服务器获取动态表中的所有动态
        request.add("methods", "browse");
        request.add("news_id",news_id);
        mRequestQueue.add(BROWSE, request, onResponseListener);
    }

    private void initUrl() {
        //添加服务器请求
        MyApplication myApplication = new MyApplication();
        mUrl = myApplication.getUrl();
    }


    private void initAdapter() {
        mChatDynamicAdpter = new ChatDynamicAdpter(commentDataList, getActivity());
        commentListView.setAdapter(mChatDynamicAdpter);
    }

    private void initView() {
        //初始化所有控件
        //commentListView = (ListView) mChatDynamicView.findViewById(R.id.dynamic_listview);
        commentListView = (PullToRefreshListView) mChatDynamicView.findViewById(R.id.dynamic_listview);
        mDialog = new WaitDialog(getActivity());
        //集合的初始化
        commentDataList = new ArrayList<CommentData>();
    }

    private void initData() {
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //请求服务器获取动态表中的所有动态
        request.add("methods", "showdynamic");
        mRequestQueue.add(DYNAMIC, request, onResponseListener);
    }

    /***********************************
     * 执行下拉刷新操作
     ***********************************/

    private void addListener() {
        commentListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                new LoadDataAsyncTask(ChatDynamicFragment.this).execute();//执行下载数据
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉刷新，不使用
            }
        });
    }

    //更新数据
    private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private ChatDynamicFragment fragment;

        //初始化
        public LoadDataAsyncTask(ChatDynamicFragment fragment) {
            this.fragment = fragment;
        }

        //向服务器发出请求更新数据
        @Override
        protected String doInBackground(Void... params) {
            //加载数据
            fragment.initData();
            return "success";
        }

        //  onPostExecute（）是对返回的值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                fragment.mChatDynamicAdpter.notifyDataSetChanged();//通知数据集改变,界面刷新
                fragment.commentListView.onRefreshComplete();//表示刷新完成
            }
        }
    }

    private void initRefreshListView() {
        ILoadingLayout startLabels = commentListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = commentListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }


    //对网络的请求
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == DYNAMIC) {
                //在此执行下拉刷新
                // 请求成功
                String result = response.get();// 响应结果
                //Log.e("解析前好友列表返回结果", result);
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                commentDataList = gson.fromJson(result, new TypeToken<List<CommentData>>() {
                }.getType());
                Log.e("解析后", commentDataList.toString());
                //添加到适配器
                initAdapter();
            }else if(what==BROWSE){
                //添加成功直接刷新适配器
                initAdapter();
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(getActivity(), "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(getActivity(), "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(getActivity(), "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(getActivity(), "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(getActivity(), "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(getActivity(), "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }
    };
}
