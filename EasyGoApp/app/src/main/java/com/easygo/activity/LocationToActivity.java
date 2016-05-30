package com.easygo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

public class LocationToActivity extends AppCompatActivity implements
        GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener {
    private ProgressDialog progDialog = null;
    private GeocodeSearch geocoderSearch;
    private String addressName;
    private LatLonPoint latLonPoint;
    private EditText mlocationEditText,mXlocationEditText,mYlocationEditText;
    private TextView mWZshowTextView,mXYshowTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_to);
        initView();
    }

    private void initView() {
        mlocationEditText= (EditText) findViewById(R.id.location_hz);
        mXlocationEditText= (EditText) findViewById(R.id.location_x);
        mYlocationEditText= (EditText) findViewById(R.id.location_y);
        mWZshowTextView= (TextView) findViewById(R.id.hz_show);
        mXYshowTextView= (TextView) findViewById(R.id.xy_show);
        Button geoButton = (Button) findViewById(R.id.geoButton);
        geoButton.setOnClickListener(this);
        Button regeoButton = (Button) findViewById(R.id.regeoButton);
        regeoButton.setOnClickListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        progDialog = new ProgressDialog(this);
    }

    /**
     * 显示进度条对话框
     */
    public void showDialog() {
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在获取地址");
        progDialog.show();
    }

    /**
     * 隐藏进度条对话框
     */
    public void dismissDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name,String city) {
        showDialog();
        GeocodeQuery query = new GeocodeQuery(name, city);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        showDialog();
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
               /* aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
                geoMarker.setPosition(AMapUtil.convertToLatLng(address
                        .getLatLonPoint()));*/
                addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
                        + address.getFormatAddress();
                mWZshowTextView.setText(addressName);
                show(addressName);
            } else {

                show("没有结果");
            }

        } else {
            show("出错了");
        }
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress()
                        + "附近";
                mXYshowTextView.setText(addressName);
                show(addressName);
            } else {
                show("没有结果");
            }
        } else {
            show("出错了");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 响应地理编码按钮
             */
            case R.id.geoButton:
                getLatlon(mlocationEditText.getText().toString(),"苏州");
                break;
            /**
             * 响应逆地理编码按钮
             */
            case R.id.regeoButton:
                latLonPoint= new LatLonPoint(Double.parseDouble(mYlocationEditText.getText().toString()), Double.parseDouble(mXlocationEditText.getText().toString()));
                getAddress(latLonPoint);
                break;
            default:
                break;
        }
    }

    private void show(String s) {
        Toast.makeText(LocationToActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
