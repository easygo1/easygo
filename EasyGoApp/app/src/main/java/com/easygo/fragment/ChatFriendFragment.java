package com.easygo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.easygo.activity.R;
import com.easygo.adapter.FriendListAdapter;
import com.easygo.application.MyApplication;
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

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * Created by PengHong on 2016/4/29.6/9日改
 */
public class ChatFriendFragment extends Fragment {
    public static final String TYPE = "type";
    //请求对象
    public static final int FriendList = 7;
    //自定义一个dialog
    private WaitDialog mDialog;
    //请求队列
    private RequestQueue mRequestQueue;
    String mUrl, phone = null,nickname=null,userphoto=null;
    View mChatFriendView;
    FriendListAdapter friendAdapter;
    List<User> friendlist;
    SharedPreferences mSharedPreferences;
    //PullToRefreshListView实例
    PullToRefreshListView mPullToRefreshListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatFriendView = inflater.inflate(R.layout.chat_friend, null);
        //获取到服务器的url
        initUrl();
        //获取到手机号和token
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        //获取到手机号
        phone = mSharedPreferences.getString("phone", null);
        //获取到昵称
        nickname=mSharedPreferences.getString("user_nickname", null);
        //获取到头像
        userphoto=mSharedPreferences.getString("user_photo", null);
        //初始化控件
        initView();
        /**
         * 加载数据部分
         */
        //对服务器进行请求，并刷新好友列表
        refreshfriendList();
        //用户信息提供者
        UserInfoProvider();
        //设置刷新时头部的状态
        initRefreshListView();
        //添加下拉刷新的监听
        addListener();

        return mChatFriendView;
    }

    //连接到服务器
    private void initUrl() {
        MyApplication myApplication = new MyApplication();
        mUrl = myApplication.getUrl();
    }

    //初始化控件
    private void initView() {
        friendlist = new ArrayList<>();
        mDialog = new WaitDialog(getActivity());
        mPullToRefreshListView = (PullToRefreshListView) mChatFriendView.findViewById(R.id.friend_listview);
    }


    //初始化适配器
    private void initAdapter() {
        friendAdapter = new FriendListAdapter(friendlist, getActivity());
        mPullToRefreshListView.setAdapter(friendAdapter);
    }

    /***********************************
     * 执行下拉刷新操作
     ***********************************/

    private void addListener() {
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                new LoadDataAsyncTask(ChatFriendFragment.this).execute();//执行下载数据
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
        private ChatFriendFragment fragment;

        //初始化
        public LoadDataAsyncTask(ChatFriendFragment fragment) {
            this.fragment = fragment;
        }

        //向服务器发出请求更新数据
        @Override
        protected String doInBackground(Void... params) {
            //加载数据
            fragment.refreshfriendList();
            return "success";
        }

        //  onPostExecute（）是对返回的值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                fragment.friendAdapter.notifyDataSetChanged();//通知数据集改变,界面刷新
                fragment.mPullToRefreshListView.onRefreshComplete();//表示刷新完成
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
    }

    /***********************************
     * 向服务器发出请求
     ***********************************/
    //刷新好友列表
    private void refreshfriendList() {
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
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
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
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                friendlist = gson.fromJson(result, new TypeToken<List<User>>() {
                }.getType());
                Log.e("解析后", friendlist.toString());
                //添加适配器
                initAdapter();
                //启动聊天界面
                startPrivateChat();

                //通知刷新好友列表
                friendAdapter.notifyDataSetChanged();
                //表示刷新完成
                mPullToRefreshListView.onRefreshComplete();
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

    /***********************************
     * 启动融云聊天界面
     ***********************************/
    public void startPrivateChat() {
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取好友的phone
                String phone=friendlist.get(position-1).getUser_phone();
                Log.e(phone+"","对方的手机号是");
                //获取好友的用户名
                String target =friendlist.get(position-1).getUser_nickname();
                Log.e(target+"","对方的昵称是");
                /**
                 * 启动单聊界面。
                 *
                 * @param context      应用上下文。
                 * @param targetUserId 要与之聊天的用户 Id。
                 * @param title        聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */
                RongIM.getInstance().startPrivateChat(getActivity(), phone, target);
            }
        });
    }

    //用户信息提供者
    private void UserInfoProvider() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                for (int i=0;friendlist.size()>=i;i++) {
                    if(friendlist.size()==i){
                        return new UserInfo(phone,nickname,Uri.parse(userphoto));
                    }else
                    if (friendlist.get(i).getUser_phone().equals(s)) {
                        return new UserInfo(friendlist.get(i).getUser_phone(),friendlist.get(i).getUser_nickname(), Uri.parse(friendlist.get(i).getUser_photo()));
                    }
                }
                return null;
            }
        }, true);//如果需要缓存用户信息为true，否则为false
    }
}
