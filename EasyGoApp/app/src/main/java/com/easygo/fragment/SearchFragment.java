package com.easygo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.activity.CalendarActivity;
import com.easygo.activity.FootPrintActivity;
import com.easygo.activity.HouseCollectionActivity;
import com.easygo.activity.R;
import com.easygo.activity.SearchHouseActivity;
import com.easygo.activity.SelectCityActivity;
import com.easygo.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by PengHong on 2016/4/29.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    public static final String TYPE = "type";
    View mSearchView;
    View mSearchCityView, mFootPrintView, mCollectionView, mDateSelectView;
    TextView mSearchTextView;
    Button mSearchButton;

    private final static String DATE_FORMAT = "yyyy-MM-dd";
    java.text.SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    List<Date> mDateList;
    List<String> mTimeList;//搜索得到的入住时间列表
    String searchdate;
    String searchcity;//搜索的城市名称

    private TextView tv_in, tv_out;
    SharedPreferences mSharedPreferences,sp;
    String inday, outday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSearchView = inflater.inflate(R.layout.bottom_search, null);
        initView();
        initData();
        initListener();
        return mSearchView;
    }

    private void initData() {
//        Intent intent=getActivity().getIntent();
        mSharedPreferences = getActivity().getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        String city = mSharedPreferences.getString("searchcity", "苏州");
        //Toast.makeText(getActivity(),city,Toast.LENGTH_SHORT).show();
        mSearchTextView.setText(city);
    }

    private void initListener() {
        mSearchCityView.setOnClickListener(this);
        mDateSelectView.setOnClickListener(this);
        mFootPrintView.setOnClickListener(this);
        mCollectionView.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
    }

    private void initView() {
        mSearchCityView = mSearchView.findViewById(R.id.city_search);
        mDateSelectView = mSearchView.findViewById(R.id.time_search);
        mSearchTextView = (TextView) mSearchView.findViewById(R.id.search_texttext);
        mFootPrintView = mSearchView.findViewById(R.id.search_footpoint);
        mCollectionView = mSearchView.findViewById(R.id.search_collection);
        mSearchButton = (Button) mSearchView.findViewById(R.id.search_btn);
        //初始化日历控件
        sp = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);

        tv_in = (TextView) mSearchView.findViewById(R.id.search_check_time);
        tv_out = (TextView) mSearchView.findViewById(R.id.search_leave_time);
    }

    @Override
    public void onStart() {
        super.onStart();
        inday = sp.getString("dateIn", "");
        outday = sp.getString("dateOut", "");
        if (!"".equals(inday)) {
            tv_in.setText(inday);
        }
        if (!"".equals(outday)) {
            tv_out.setText(outday);
        }
        try {
            if ((!"请选择入住时间".equals(inday)) && (!"请选择离开时间".equals(outday))) {
                Date indate = formatter.parse(inday);
                Date outdate = formatter.parse(outday);
                mDateList = DateUtils.getDaysListBetweenDates(indate, outdate);
            } else {
                Toast.makeText(getActivity(), "入住离开时间不能为空", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.city_search:
//                Toast.makeText(getActivity(), "点击了搜索城市", Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(), SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.time_search:
//                Toast.makeText(getActivity(), "选择入住时间", Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(), CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.search_btn:
                //搜索房源
                //得到要搜索的城市
                searchcity = mSearchTextView.getText().toString();
                //得到要查询的日期list
                mTimeList = new ArrayList<>();
                for (int i = 0; i < mDateList.size(); i++) {
                    searchdate = formatter.format(mDateList.get(i));//格式化数据
                    mTimeList.add(searchdate);
                }
                //Log.e("zfglist", "" + mTimeList.size());
                //Log.e("zfgcity",searchcity+"1234");
                if (!searchcity.equals("")) {
                    intent.putExtra("searchcity", searchcity);
                    intent.putExtra("timelist", mTimeList.toString());
                    //intent.putExtra("inday",inday);
                    //intent.putExtra("outday",outday);
                    intent.setClass(getActivity(), SearchHouseActivity.class);
                    startActivity(intent);

                    // /创建请求对象
                    /*request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                    //添加请求参数
                    request.add("methods", "searchHouse");
                    request.add("searchcity", searchcity);
                    request.add("timelist", mTimeList.toString());
                    mRequestQueue.add(WHAT_SEARCHHOUSE, request, mOnResponseListener);
               */
                } else {
                    Toast.makeText(getActivity(), "搜索条件不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.search_footpoint:
                //Toast.makeText(getActivity(), "点击了历史足迹", Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(), FootPrintActivity.class);
                startActivity(intent);
                break;
            case R.id.search_collection:
//                Toast.makeText(getActivity(), "点击了我的收藏", Toast.LENGTH_SHORT).show();
                intent.setClass(getActivity(), HouseCollectionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
