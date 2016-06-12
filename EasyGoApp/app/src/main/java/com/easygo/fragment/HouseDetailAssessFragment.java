package com.easygo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.easygo.activity.HouseDetailActivity;
import com.easygo.activity.R;
import com.easygo.adapter.HouseAssessAdpter;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonAboutHouseAssess;
import com.easygo.beans.gson.GsonAboutHouseDetail;
import com.easygo.beans.house.HouseAssess;
import com.easygo.beans.order.Assess;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailAssessFragment extends Fragment {
    public static final int GET_ASSESS_WHAT = 1;
    List<HouseAssess> mHouseAssessList;
    View mAssessView;
    HouseAssessAdpter mHouseAssessAdpter;
    ListView mlistView;
    int houseid;
    private RequestQueue requestQueue;
    Request<String> request;
    String mPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAssessView = inflater.inflate(R.layout.house_detail_assess, null);
        initViews();
        initData();
        return mAssessView;
    }

    private void initData() {
        HouseDetailActivity houseDetailActivity = (HouseDetailActivity) getActivity();
        houseid = houseDetailActivity.houseid;
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        mPath = myApplication.getUrl();
        // 创建请求队列, 默认并发3个请求,传入你想要的数字可以改变默认并发数, 例如NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        // 添加请求参数
        request.add("methods", "getAssess");
        request.add("houseid", houseid);
        requestQueue.add(GET_ASSESS_WHAT, request, onResponseListener);


        mHouseAssessList = new ArrayList<>();
/*        for (int i = 0; i < 10; i++) {
            HouseAssess houseAssess=new HouseAssess("房客"+i,i/2,"还不错哦"+i,
                    "2016-05-"+i,"欢迎下次来"+i);
            mHouseAssessList.add(houseAssess);
        }*/
        mHouseAssessAdpter = new HouseAssessAdpter(getActivity(), mHouseAssessList);
        mlistView.setAdapter(mHouseAssessAdpter);
    }

    private void initViews() {
        mlistView = (ListView) mAssessView.findViewById(R.id.assess_listview);
    }


    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case GET_ASSESS_WHAT:
                    GsonAboutHouseAssess gah;
                    List<Assess> allAssessList;
                    List<String> userNameList;
                    List<String> userPhotoList;
                    //获取房源信息
                    String result = response.get();// 响应结果
                    //把JSON格式的字符串改为Student对象
                    Gson gson = new Gson();
                    Type type = new TypeToken<GsonAboutHouseAssess>() {
                    }.getType();
                    gah = gson.fromJson(result, type);
                    allAssessList = gah.getAllAssessList();
                    userNameList = gah.getUserName();
                    userPhotoList = gah.getUserPhoto();
                    if(allAssessList.size() == 0){
                        Toast.makeText(getActivity(), "暂时没有任何评价！", Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < allAssessList.size(); i++) {
                        HouseAssess mHouseAssess = new HouseAssess();
                        mHouseAssess.setAssesspersonName(userNameList.get(i));
                        mHouseAssess.setStars(allAssessList.get(i).getStar());
                        mHouseAssess.setAssesscontent(allAssessList.get(i).getAssess_content());
                        mHouseAssess.setAssesspersonphoto(userPhotoList.get(i));
                        mHouseAssessList.add(mHouseAssess);
                    }
                    mHouseAssessAdpter.notifyDataSetChanged();
            }

        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
        }

        @Override
        public void onFinish(int what) {
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(getActivity(), "网络请求失败了", Toast.LENGTH_SHORT).show();
        }
    };
}
