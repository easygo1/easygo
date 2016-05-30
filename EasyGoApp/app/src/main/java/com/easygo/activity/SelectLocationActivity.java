package com.easygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

public class SelectLocationActivity extends AppCompatActivity implements
        GeocodeSearch.OnGeocodeSearchListener {
    private MapView mMapView;//控件地图
    private AMap mAMap;
    private GeocodeSearch geocoderSearch;
    private LatLng mLatLng = new LatLng(31.275129, 120.742301);//经纬度
    private Marker marker;//标记
    private String Address;//当前位置
    private EditText mlocationEditText;
    private Button mokButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        initView();
        //必须这样写
        mAMap = mMapView.getMap();
        mMapView.onCreate(savedInstanceState);
        //初始化标记
        initMarker();
        initlistener();
    }

    private void initlistener() {
        mokButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatlon(mlocationEditText.getText().toString(), "苏州");
                Intent intent=new Intent();
                intent.setClass(SelectLocationActivity.this,ReleasesroomActivity.class);
                intent.putExtra("address",Address);
                startActivity(intent);
            }
        });
        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            /**
             * 地图单击事件回调函数
             *
             * @param latLng
             *            点击的地理坐标
             */
            @Override
            public void onMapClick(LatLng latLng) {
                mAMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.search_location_blue));
                marker = mAMap.addMarker(markerOptions);
                marker.setVisible(true);
                //移动地图中心，并将缩放比例调整为20 范围（3-20）
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    /*//经纬度转化成位置信息
                    RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.longitude, latLng.latitude), 200,
                            GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求 marker.setTitle("位置" + latLng);
                    RegeocodeAddress regeocodeAddress = geocoderSearch.getFromLocation(query);
                     marker.setTitle("位置" + latLng.toString() + "\n"+regeocodeAddress.getFormatAddress());
                    */
                marker.setTitle("我的位置" + latLng.toString());
                marker.showInfoWindow();
            }
        });

    }

    private void initMarker() {
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 18));
        //设置标记的初始位置，标记显示图片
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.search_location_blue));
        //将标记添加到地图中
        marker = mAMap.addMarker(markerOptions);
        marker.setVisible(true);
        marker.setTitle("位置" + mLatLng + "\n");
        marker.showInfoWindow();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.select_map);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        mlocationEditText = (EditText) findViewById(R.id.location_search);
        mokButton = (Button) findViewById(R.id.search_ok);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name, String city) {
        GeocodeQuery query = new GeocodeQuery(name, city);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    //将经纬度转化成位置信息
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                Address = regeocodeResult.getRegeocodeAddress().getFormatAddress().toString()
                        + "附近";
                show(Address);
            } else {
                show("没有结果");
            }
        } else {
            show("出错了");
        }
    }

    private void show(String s) {
        Toast.makeText(SelectLocationActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    //把位置信息转化成经纬度
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
        Log.e("info", "经纬度" + address.getLatLonPoint());
        mAMap.clear();
        //设置标记的初始位置，标记显示图片
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(address.getLatLonPoint().getLatitude(),address.getLatLonPoint().getLongitude()));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.search_location_blue));
        marker = mAMap.addMarker(markerOptions);
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatLonPoint().getLatitude(),address.getLatLonPoint().getLongitude()), 18));
        marker.setTitle("经纬度" + address.getLatLonPoint() + "\n位置" + address.getFormatAddress());
        marker.showInfoWindow();
        address.getCity();//得到城市
        show(address.getFormatAddress());
        Address=address.getFormatAddress();
    }
}
