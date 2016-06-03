package com.easygo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.easygo.activity.R;
import com.easygo.adapter.FriendListAdapter;
import com.easygo.application.MyApplication;
import com.easygo.view.WaitDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatFriendFragment extends Fragment {
    public static final String TYPE = "type";
    //请求对象
    public static final int FriendList=7;
    //自定义一个dialog
    private WaitDialog mDialog;
    //请求队列
    private RequestQueue mRequestQueue;
    String mUrl,phone=null,token=null;
    View mChatFriendView;
    FriendListAdapter friendAdapter;
    List<String> friendlist;
    ListView mFriendListView;
    SharedPreferences mSharedPreferences;
    //PullToRefreshListView实例
    //PullToRefreshListView mPullToRefreshListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatFriendView=inflater.inflate(R.layout.chat_friend,null);
        //获取到服务器的url
        MyApplication myApplication=new MyApplication();
        mUrl=myApplication.getUrl();
        //获取到手机号和token
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        phone = mSharedPreferences.getString("phone",null);
        //初始化控件
        initView();
        //对服务器进行请求，并刷新好友列表
        refreshfriendList();
        //用户信息提供者
        UserInfoProvider();
       /* //设置刷新时头部的状态
        initRefreshListView();*/
        return mChatFriendView;
    }
    //初始化控件
    private void initView() {
        friendlist=new ArrayList<>();
        mFriendListView= (ListView) mChatFriendView.findViewById(R.id.friend_listview);
        mDialog = new WaitDialog(getActivity());
    }
    //初始化适配器
    private void initAdapter() {
        friendAdapter=new FriendListAdapter(friendlist,getActivity());
       // mFriendListView.setAdapter(friendAdapter);
        mFriendListView.setAdapter(friendAdapter);
       /* //3.设置上拉加载下拉刷新组件和事件监听
        //设置刷新模式为BOTH才可以上拉和下拉都能起作用,必须写在前面
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时头部的状态
        initRefreshListView();
        //设置上拉和下拉时候的监听器
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉时

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //清除集合中的数据
                friendlist.clear();
                new LoadDataAsyncTask(ChatFriendFragment.this).execute();//执行下载数据
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
              //下拉不做操作
            }
        });*/
    }

    /*static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private ChatFriendFragment activity;

        public LoadDataAsyncTask(ChatFriendFragment activity) {
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
            activity.refreshfriendList();
            return "success";
        }

        //  onPostExecute（）是对返回的值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                activity.friendAdapter.notifyDataSetChanged();//通知数据集改变,界面刷新
                activity.mPullToRefreshListView.onRefreshComplete();//表示刷新完成
            }
        }
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
   }*/
    //刷新好友列表
    private void refreshfriendList(){
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        // 添加请求参数,当前的手机号进行查找好友
        request.add("methods", "showfriendlist");
        request.add("phone", phone);
        mRequestQueue.add(FriendList, request, onResponseListener);
    }
    //对网络的请求
    private OnResponseListener<String> onResponseListener=new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == FriendList) {
                //清空当前集合
                friendlist.clear();
                //在此执行下拉刷新
                // 请求成功
                String result = response.get();// 响应结果
                Log.e("解析前好友列表返回结果",result);
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                friendlist = gson.fromJson(result, new TypeToken<List<String>>(){}.getType());
                Log.e("解析后",friendlist.toString());
                //添加适配器
                initAdapter();
                //启动聊天界面
                startPrivateChat();
               /* //通知刷新好友列表
                friendAdapter.notifyDataSetChanged();
                //表示刷新完成
                mPullToRefreshListView.onRefreshComplete();*/
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

    //启动聊天界面
    public void startPrivateChat(){
        mFriendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取好友的用户名
                String target= (String) mFriendListView.getAdapter().getItem(position);
                //启动聊天窗口
                RongIM.getInstance().startPrivateChat(getActivity(),target,null);
            }
        });
    }

    //用户信息提供者
    private void UserInfoProvider() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                //用户名，用户显示名字，头像的url
                UserInfo userInfo=new UserInfo(s,s, Uri.parse(""));
                return userInfo;
            }
        },true);//如果需要缓存用户信息为true，否则为false
    }
}
