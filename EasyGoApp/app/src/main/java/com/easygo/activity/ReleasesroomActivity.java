package com.easygo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easygo.application.MyApplication;
import com.easygo.beans.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.lang.reflect.Type;
import java.util.List;

public class ReleasesroomActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int WHAT = 1;
    ImageView mReturnImageView,mCameraImageView;
    EditText mTitleEditText,mHomeinfoEditText,mTrafficinfoEditText;
    RelativeLayout mRoompicLayout,mBedLayout,mFacilitiesLayout,mAddressLayout,mMostnumLayout,mOnepriceLayout,mAddpriceLayout,mLimitsexLayout,mStaytimeLayout,mRealnameLayout;
    TextView mBedTextView,mFacilitiesTextView,mAddressTextView,mMostnumTextView,mOnepriceTextView,mAddpriceTextView,mLimitsexTextView,mStaytimeTextView,mRealnameTextView;
    Button mSuccessButton;
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    String mUrl;
    private RequestQueue mRequestQueue;
    Intent intent;
    String house_style = null;
    String house_most_num_str = null;
    int house_most_num;
    String house_one_price_str = null;
    double house_one_price;
    String house_add_price_str = null;
    double house_add_price;
    String house_limit_sex = null;
    String house_stay_time_str = null;
    int house_stay_time;

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT) {
                String result = response.get();
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type type = new TypeToken<List<Order>>(){}.getType();
//                mList = gson.fromJson(result,type);
                // mList.addAll((List<Order>)gson.fromJson(result,type));
                // mCustomOrderAdapter.notifyDataSetChanged();
                //表示刷新完成
//                mPullToRefreshListView.onRefreshComplete();
                // Log.e("list",mList.toString());
            }
        }

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        }

        @Override
        public void onFinish(int what) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasesroom);
        initView();
        addListeners();
    }


    private void initView() {
        mReturnImageView = (ImageView) findViewById(R.id.releaseroom_return);
        mCameraImageView = (ImageView) findViewById(R.id.releaseroom_camera);
        mTitleEditText = (EditText) findViewById(R.id.releaseroom_title);
        mHomeinfoEditText = (EditText) findViewById(R.id.releaseroom_homeinfo);
        mTrafficinfoEditText = (EditText) findViewById(R.id.releaseroom_trafficinfo);
        mRoompicLayout = (RelativeLayout) findViewById(R.id.releaseroom_roompic);
        mBedLayout = (RelativeLayout) findViewById(R.id.releaseroom_bed);
        mFacilitiesLayout = (RelativeLayout) findViewById(R.id.releaseroom_facilities);
        mAddressLayout = (RelativeLayout) findViewById(R.id.releaseroom_address);
        mMostnumLayout = (RelativeLayout) findViewById(R.id.releaseroom_mostnum);
        mOnepriceLayout = (RelativeLayout) findViewById(R.id.releaseroom_oneprice);
        mAddpriceLayout = (RelativeLayout) findViewById(R.id.releaseroom_addprice);
        mLimitsexLayout = (RelativeLayout) findViewById(R.id.releaseroom_limitsex);
        mStaytimeLayout = (RelativeLayout) findViewById(R.id.releaseroom_staytime);
        mRealnameLayout = (RelativeLayout) findViewById(R.id.releaseroom_realname);
        mSuccessButton = (Button) findViewById(R.id.releaseroom_success);
        mBedTextView = (TextView) findViewById(R.id.releaseroom_bedtextView);
        mFacilitiesTextView = (TextView) findViewById(R.id.releaseroom_facilitiestextView);
        mAddressTextView = (TextView) findViewById(R.id.releaseroom_addresstextView);
        mMostnumTextView = (TextView) findViewById(R.id.releaseroom_mostnumtextView);
        mOnepriceTextView = (TextView) findViewById(R.id.releaseroom_onepricetextView);
        mAddpriceTextView = (TextView) findViewById(R.id.releaseroom_addpricetextView);
        mLimitsexTextView = (TextView) findViewById(R.id.releaseroom_limitsextextView);
        mStaytimeTextView = (TextView) findViewById(R.id.releaseroom_staytimetextView);
        mRealnameTextView = (TextView) findViewById(R.id.releaseroom_realnametextView);
    }
    private void addListeners() {
        mReturnImageView.setOnClickListener(this);
        mCameraImageView.setOnClickListener(this);
        mBedLayout.setOnClickListener(this);
        mFacilitiesLayout.setOnClickListener(this);
        mAddressLayout.setOnClickListener(this);
        mMostnumLayout.setOnClickListener(this);
        mOnepriceLayout.setOnClickListener(this);
        mAddpriceLayout.setOnClickListener(this);
        mLimitsexLayout.setOnClickListener(this);
        mStaytimeLayout.setOnClickListener(this);
        mRealnameLayout.setOnClickListener(this);
        mSuccessButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.releaseroom_return:
                intent = new Intent();
                intent.putExtra("flag","me");
                intent.setClass(ReleasesroomActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.releaseroom_camera:
                break;
            case R.id.releaseroom_bed:
                showBedDialog();
                break;
            case R.id.releaseroom_facilities:
                showFacilitiesDialog();
                break;
            case R.id.releaseroom_address:
                Intent intent1=new Intent();
                intent1.setClass(ReleasesroomActivity.this,SelectLocationActivity.class);
                startActivity(intent1);
                break;
            case R.id.releaseroom_mostnum:
                showMostnumDialog();
                break;
            case R.id.releaseroom_oneprice:
                showOnePriceDialog();
                break;
            case R.id.releaseroom_addprice:
                showAddpriceDialog();
                break;
            case R.id.releaseroom_limitsex:
                showLimitsex();
                break;
            case R.id.releaseroom_staytime:
                showStaytimeDialog();
                break;
            case R.id.releaseroom_realname:
                break;
            case R.id.releaseroom_success:
                //将用户设置的数据进行处理
                processData();
                //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
                mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
                //向偏好设置文件中保存数据
                mEditor = mSharedPreferences.edit();
                mEditor.putInt("type", 2);
                //提交保存结果
                mEditor.commit();
                MyApplication myApplication = (MyApplication) this.getApplication();
                mUrl = myApplication.getUrl();
                //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
                mRequestQueue = NoHttp.newRequestQueue();
                //创建请求对象
                Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                //添加请求参数
                request.add("methods","addHouse");
                request.add("house_style",house_style);
                request.add("house_most_num",house_most_num);
                request.add("house_one_price",house_one_price);
                request.add("house_add_price",house_add_price);
                request.add("house_limit_sex",house_limit_sex);
                request.add("house_stay_time",house_stay_time);
        /*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
		 * request: 请求对象
		 * onResponseListener 回调对象，接受请求结果
		 */
                mRequestQueue.add(WHAT,request, onResponseListener);
                intent = new Intent();
                intent.putExtra("flag","me");
                intent.setClass(ReleasesroomActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void processData() {
        //去掉字符串中的“人”,便于之后转换为int类型
        house_most_num = Integer.valueOf(house_most_num_str.replace("人",""));
        if (house_one_price_str.equals("免费")){
            house_one_price = 0;
        }else {
            house_one_price = Double.valueOf(house_one_price_str.replace("元", ""));
        }
        if (house_add_price_str.equals("免费")){
            house_add_price = 0;
        }else {
            house_add_price = Double.valueOf(house_add_price_str.replace("元", ""));
        }
        //当房主选择最长入住时间为可议时，将“可议”转换为0存入数据库
        if (house_stay_time_str.equals("可议")){
            house_stay_time = 0;
        }else {
            house_stay_time = Integer.valueOf(house_stay_time_str.replace("天",""));
        }
    }


    private void showBedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.bed, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house_style = getResources().getStringArray(R.array.bed)[which];
                mBedTextView.setText(house_style);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showFacilitiesDialog() {
    }
    private void showMostnumDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.mostnum, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house_most_num_str = getResources().getStringArray(R.array.mostnum)[which];
                mMostnumTextView.setText(house_most_num_str);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showOnePriceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.oneprice, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house_one_price_str = getResources().getStringArray(R.array.oneprice)[which];
                mOnepriceTextView.setText(house_one_price_str);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showAddpriceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.addprice, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house_add_price_str = getResources().getStringArray(R.array.addprice)[which];
                mAddpriceTextView.setText(house_add_price_str);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showLimitsex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.limitsex, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house_limit_sex = getResources().getStringArray(R.array.limitsex)[which];
                mLimitsexTextView.setText(house_limit_sex);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showStaytimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.staytime, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house_stay_time_str = getResources().getStringArray(R.array.staytime)[which];
                mStaytimeTextView.setText(house_stay_time_str);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
