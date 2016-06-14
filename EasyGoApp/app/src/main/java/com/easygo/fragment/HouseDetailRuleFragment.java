package com.easygo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.bumptech.glide.Glide;
import com.easygo.activity.DateShowActivity;
import com.easygo.activity.HouseDetailActivity;
import com.easygo.activity.MapActivity;
import com.easygo.activity.R;
import com.easygo.beans.house.House;
import com.easygo.beans.user.User;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by zzia on 2016/5/20.
 * 房屋日期，地图
 */
public class HouseDetailRuleFragment extends Fragment implements View.OnClickListener {
    private MapView mMapView;
    private AMap mAMap;
    private double mlocationLng;//经度
    private double mlocationLat;//纬度
    private LatLng mLatLng;

    Button mDateButton;
    View mView, mHouseContentView, mHouseLocationView;
    Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_rule_scroll, null);
        initData();
        initViews();
        mMapView.onCreate(savedInstanceState);
        addListener();
        //初始化标记
        initMarker();
        return mView;
    }

    private void initViews() {
        mMapView = (MapView) mView.findViewById(R.id.house_detail_map);

        mDateButton = (Button) mView.findViewById(R.id.house_detail_rule_date);
        mHouseLocationView = mView.findViewById(R.id.house_location);
    }

    private void addListener() {
        mDateButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house_detail_rule_date:
                HouseDetailActivity h = (HouseDetailActivity) getActivity();
                mIntent = new Intent(getActivity(), DateShowActivity.class);
                mIntent.putExtra("house_id", h.houseid);
                startActivity(mIntent);
                break;
            case R.id.house_location:
               /* mIntent = new Intent();
                mIntent.setClass(getActivity(), MapActivity.class);
                startActivity(mIntent);*/
                break;
        }
    }

    private void initMarker() {
        mAMap = mMapView.getMap();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.search_location_blue));
        markerOptions.position(mLatLng);
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
        Marker marker = mAMap.addMarker(markerOptions);
        marker.setVisible(true);
//        marker.setTitle("地址");
        marker.showInfoWindow();
    }

    public void initData() {
        HouseDetailActivity houseDetailActivity = (HouseDetailActivity) getActivity();
        House house = houseDetailActivity.mHouse;
        mlocationLat = house.getHouse_address_lat();
        mlocationLng = house.getHouse_address_lng();
        mLatLng = new LatLng(mlocationLat, mlocationLng);
    }


}
