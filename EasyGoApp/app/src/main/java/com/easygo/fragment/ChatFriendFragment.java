package com.easygo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String mUrl,phone=null;
    View mChatFriendView;

    FriendListAdapter friendAdapter;
    List<String> friendlist;
    ListView mFriendListView;
    SharedPreferences mSharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatFriendView=inflater.inflate(R.layout.chat_friend,null);
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        phone = mSharedPreferences.getString("phone",null);
        Log.e("当前用户的手机号码",phone);
        initView();
        refreshfriendList();
        return mChatFriendView;
    }
    //初始化适配器
    private void initAdapter() {
        friendAdapter=new FriendListAdapter(friendlist,getActivity());
        mFriendListView.setAdapter(friendAdapter);
    }
    //初始化控件
    private void initView() {
        friendlist=new ArrayList<>();
        mFriendListView= (ListView) mChatFriendView.findViewById(R.id.friend_listview);
        mDialog = new WaitDialog(getActivity());
    }
    //刷新好友列表
    private void refreshfriendList(){
        //获取到服务器的url
        MyApplication myApplication=new MyApplication();
        mUrl=myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        // 添加请求参数,当前的手机号进行查找好友
        request.add("methods", "showfriendlist");
        request.add("phone", phone);
        mRequestQueue.add(FriendList, request, onResponseListener);
    }

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
                // 请求成功
                String result = response.get();// 响应结果
                Log.e("解析前好友列表返回结果",result);
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                friendlist = gson.fromJson(result, new TypeToken<List<String>>(){}.getType());
                Log.e("解析后",friendlist.toString());
                //添加适配器
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
