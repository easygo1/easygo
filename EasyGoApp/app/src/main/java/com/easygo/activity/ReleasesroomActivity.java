package com.easygo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.beans.order.Orders;
import com.easygo.fragment.ReleasesroomNoFragment;
import com.easygo.fragment.ReleasesroomYesFragment;
import com.easygo.utils.UpYunException;
import com.easygo.utils.UpYunUtils;
import com.easygo.utils.Uploader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReleasesroomActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int WHAT = 1;
    public static final int REQUEST_CODE = 11;
    //碎片
    ReleasesroomYesFragment mReleasesroomYesFragment;
    ReleasesroomNoFragment mReleasesroomNoFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

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
    //复选框
    boolean[] flags = new boolean[]{false, false, false, false, false, false, false, false, false, false,false};

    Intent intent;
    int user_id = 8;
    String house_title = null;
    String house_style = null;
    double house_address_lng;//经度
    double house_address_lat;//纬度
    String house_address_province = null;
    String house_address_city = null;
    String house_most_num_str = null;
    int house_most_num;
    String house_one_price_str = null;
    double house_one_price;
    String house_add_price_str = null;
    double house_add_price;
    String house_describe = null;
    String house_traffic = null;
    String house_limit_sex = null;
    String house_stay_time_str = null;
    int house_stay_time;

    private static final String DEFAULT_DOMAIN = "easygo.b0.upaiyun.com";// 空间域名
    private static final String API_KEY = "3AtEDO2ByBUZ7qGVTLPUnuKLOWM="; // 表单api验证密钥
    private static final String BUCKET = "easygo";// 空间名称
    private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 1000 * 5 * 10; // 过期时间，必须大于当前时间
    //用来接收图片的本地的地址
    private List<String> mList = new ArrayList<>();
    //用来存上传到服务器的图片地址
    private List<String> mUpList = new ArrayList<>();
    //用来接收选择的房源便利设施
    private List<String> mEquipmentList = new ArrayList<>();
    private String uploadPath;
    LayoutInflater mInflater;
    View mView;
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT) {
                String result = response.get();
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type type = new TypeToken<List<Orders>>(){}.getType();
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
        initDefault();
    }
    //默认显示有相机的碎片
    private void initDefault() {
        Intent intent = getIntent();
        String flag = null;
        flag = intent.getStringExtra("flag");
        if(flag == null){
        //开始显示第一个界面
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //默认显示有相机的碎片，初始化该碎片
        mReleasesroomNoFragment = new ReleasesroomNoFragment();
        mFragmentTransaction.add(R.id.releaseroom_fragment,mReleasesroomNoFragment);
        mFragmentTransaction.commit();
        }else if(flag.equals("yes")){
            //开始显示第一个界面
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            //默认显示有相机的碎片，初始化该碎片
            mReleasesroomYesFragment = new ReleasesroomYesFragment();
            mFragmentTransaction.add(R.id.releaseroom_fragment,mReleasesroomYesFragment);
            mFragmentTransaction.commit();
            initCurrentFragment(1);
        }
    }
    public void initCurrentFragment(int id) {
        //隐藏所有已经添加到事务中的碎片
        hideAllFragments();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //判断当前碎片是哪一个
        switch (id) {
            case 0:
                if (mReleasesroomNoFragment == null) {
                    mReleasesroomNoFragment = new ReleasesroomNoFragment();
                    mFragmentTransaction.add(R.id.releaseroom_fragment, mReleasesroomNoFragment);
                } else {
                    mFragmentTransaction.show(mReleasesroomNoFragment);
                }
                break;
            case 1:
                if (mReleasesroomYesFragment == null) {
                    mReleasesroomYesFragment = new ReleasesroomYesFragment();
                    mFragmentTransaction.add(R.id.releaseroom_fragment, mReleasesroomYesFragment);
                } else {
                    mFragmentTransaction.show(mReleasesroomYesFragment);
                }
                break;
        }
        mFragmentTransaction.commit();
    }
    public void hideAllFragments() {
        //隐藏所有的碎片，首先需要知道碎片是否已经添加到事务中
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (mReleasesroomNoFragment != null && mReleasesroomNoFragment.isAdded()) {
            mFragmentTransaction.hide(mReleasesroomNoFragment);
        }
        if (mReleasesroomYesFragment != null && mReleasesroomYesFragment.isAdded()) {
            mFragmentTransaction.hide(mReleasesroomYesFragment);
        }
        mFragmentTransaction.commit();
    }
    private void initView() {
        mInflater =  LayoutInflater.from(this);
        mView = mInflater.inflate(R.layout.releasesroom_fragment_no,null);

        mReturnImageView = (ImageView) findViewById(R.id.releaseroom_return);
        mCameraImageView = (ImageView) mView.findViewById(R.id.releaseroom_camera);
        mTitleEditText = (EditText) findViewById(R.id.releaseroom_title);
        mHomeinfoEditText = (EditText) findViewById(R.id.releaseroom_homeinfo);
        mTrafficinfoEditText = (EditText) findViewById(R.id.releaseroom_trafficinfo);
        mRoompicLayout = (RelativeLayout) findViewById(R.id.releaseroom_roompic_no);
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
        //mCameraImageView.setOnClickListener(this);
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

            case R.id.releaseroom_bed:
                showBedDialog();
                break;
            case R.id.releaseroom_facilities:
                showFacilitiesDialog();
                break;
            case R.id.releaseroom_address:
                intent = new Intent();
                intent.setClass(ReleasesroomActivity.this,SelectLocationActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
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
                if(getFragmentManager().findFragmentById(R.id.releaseroom_fragment) == mReleasesroomNoFragment){
                    Toast.makeText(ReleasesroomActivity.this, "请将房源信息填写完整", Toast.LENGTH_SHORT).show();
                    break;
                }
                mReleasesroomYesFragment = (ReleasesroomYesFragment) getFragmentManager().findFragmentById(R.id.releaseroom_fragment);
                mList = mReleasesroomYesFragment.mList;
                Log.e("cuikaiactivity",mList.toString());
                house_title = mTitleEditText.getText().toString();
                house_describe = mHomeinfoEditText.getText().toString();
                house_traffic = mTrafficinfoEditText.getText().toString();
                if (house_title != null && house_describe != null && house_style != null && house_address_province !=null &&
                        house_traffic != null && house_most_num_str != null && house_one_price_str != null && house_add_price_str != null
                        && house_limit_sex != null && house_stay_time_str != null ) {
                    for (int i = 0; i < mList.size(); i++) {
                        new UploadTask().execute(mList.get(i));
                    }
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                while (true) {
                                    if (mList.size() == mUpList.size()) {
                                        Log.e("cuikaiup", mUpList.toString());
                                        addHouseToSQL();
                                        return;
                                    } else {
                                        sleep(1000);
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t.start();
                }else {
                    Toast.makeText(ReleasesroomActivity.this, "请将房源信息填写完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void addHouseToSQL() {
        //将用户设置的数据进行处理
        processData();
        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
        mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("type", 2);
        //提交保存结果
        mEditor.commit();
        Gson gson = new Gson();
        //Type type = new TypeToken<List<String>>(){}.getType();
        String photoList = gson.toJson(mUpList);
        String equipmentList = gson.toJson(mEquipmentList);
        Log.e("cuikaiphoto",photoList+"666");
        MyApplication myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
        //添加请求参数
        request.add("methods","addHouse");
        request.add("user_id",user_id);
        request.add("house_title",house_title);
        request.add("house_style",house_style);
        request.add("house_address_province",house_address_province);
        request.add("house_address_city",house_address_city);
        request.add("house_address_lng",house_address_lng);
        request.add("house_address_lat",house_address_lat);
        request.add("house_most_num",house_most_num);
        request.add("house_one_price",house_one_price);
        request.add("house_add_price",house_add_price);
        request.add("house_describe",house_describe);
        request.add("house_traffic",house_traffic);
        request.add("house_limit_sex",house_limit_sex);
        request.add("house_stay_time",house_stay_time);
        request.add("photoList",photoList);
        request.add("equipmentList",equipmentList);
     /* what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
     * request: 请求对象
     * onResponseListener 回调对象，接受请求结果*/
        mRequestQueue.add(WHAT,request, onResponseListener);
        intent = new Intent();
        intent.putExtra("flag","me");
        intent.setClass(ReleasesroomActivity.this,MainActivity.class);
        startActivity(intent);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //比较requestCode和REQUESTCODE，证明活动是否为REQUESTCODE相关的操作发起。
        if (requestCode == REQUEST_CODE){
            if (resultCode == SelectLocationActivity.RESULT_OK){
                Bundle bundle = data.getExtras();
                String house_address = bundle.getString("house_address");
                mAddressTextView.setText(house_address);
                house_address_lng = bundle.getDouble("house_address_lng");
                house_address_lat = bundle.getDouble("house_address_lat");
                house_address_province = bundle.getString("house_address_province");
                house_address_city = bundle.getString("house_address_city");
            }
        }
    }

    private void showFacilitiesDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("请选择便利设施");
        builder.setMultiChoiceItems(R.array.equipment, flags, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                flags[which] = isChecked;//单击时将是否选中记下来，默认是未选中
            }
        });
        //添加一个确定按钮
        builder.setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < flags.length; i++) {
                    if (flags[i] == true) {
                        /*selectedStr = selectedStr + " " +
                                getResources().getStringArray(R.array.equipment)[i];*/
                        mEquipmentList.add(getResources().getStringArray(R.array.equipment)[i]);
                    }
                }
                if (mEquipmentList.size() > 0){
                    mFacilitiesTextView.setText(mEquipmentList.toString()+"");
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
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
    public class UploadTask extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ReleasesroomActivity.this);
            dialog.setMessage("正在提交中,请稍等...");
            dialog.show();
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(String... params) {
            if (TextUtils.isEmpty(params[0])) {
                return "";
            }

            String string = null;
            Log.i("test", "文件大小：" + new File(params[0]).length() / (1024 * 1024f) + "M");

            try {
                // 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
                String SAVE_KEY = File.separator + "test" + File.separator + System.currentTimeMillis() + ".jpg";

                // 取得base64编码后的policy
                String policy = UpYunUtils.makePolicy(SAVE_KEY, EXPIRATION, BUCKET);
                // 根据表单api签名密钥对policy进行签名
                // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                String signature = UpYunUtils.signature(policy + "&" + API_KEY);

                long startTime = System.currentTimeMillis();
                Log.i("test", "开始上传：" + startTime);
                // 上传文件到对应的bucket中去。
                string = DEFAULT_DOMAIN + Uploader.upload(policy, signature, BUCKET, params[0]);

                Log.i("test", "上传完成：" + (System.currentTimeMillis() - startTime) + "ms");
            } catch (UpYunException e) {
                e.printStackTrace();
                Log.i("test", "异常了..." + e.getMessage());
            }

            return string;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            uploadPath = result;
            if (result != null) {
                Log.i("test", "上传成功,访问地址为：" + result);
                Toast.makeText(ReleasesroomActivity.this, "上传成功,访问地址为：" + result, Toast.LENGTH_SHORT).show();
                mUpList.add(uploadPath);
            } else {
                Log.i("test", "上传图片失败");
                Toast.makeText(ReleasesroomActivity.this, "上传图片失败", Toast.LENGTH_SHORT).show();
            }


            dialog.dismiss();
        }

    }
}
