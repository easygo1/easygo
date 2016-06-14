package com.easygo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.application.MyApplication;
import com.easygo.beans.order.Orders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import c.b.BP;
import c.b.PListener;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTextView,mOrderNameTextView,mPriceTextView;
    ImageView mImageView;
    RadioButton mAlipayRadioButton, mWeixinRadioButton;
    Button mPayButton;
    RadioGroup mtypeRadioGroup;
    ProgressDialog dialog;

    double price = 0.02;
    String dcribe = "靠近机场";
    String ordername = "和风小院";
    int order_id=0;

    String mUrl;
    MyApplication myApplication;
    Request<String> request;

    String APPID = "640698dda5dab6cb17f864264b35ae91";
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;
    public static final int WHAT_ISASSESSORDERS = 1;
    private RequestQueue mRequestQueue;
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == WHAT_ISASSESSORDERS) {
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
        setContentView(R.layout.activity_pay);
        // 必须先初始化
        BP.init(this, APPID);
        getData();
        initview();
        addlistener();
        setData();
    }

    private void setData() {
        mOrderNameTextView.setText(dcribe);
        mPriceTextView.setText(""+price+"元");
    }

    private void getData() {
        myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        Intent intent=getIntent();
        order_id=intent.getIntExtra("order_id",0);//上个页面传过来的order_id
        dcribe=intent.getStringExtra("describe");//名称
        price=intent.getDoubleExtra("price",0);//价格
    }

    private void initview() {
        mTextView = (TextView) findViewById(R.id.title_text);
        mTextView.setText("订单支付");
        mOrderNameTextView= (TextView) findViewById(R.id.order_name);
        mPriceTextView= (TextView) findViewById(R.id.order_price);
        mImageView = (ImageView) findViewById(R.id.back);
        mAlipayRadioButton = (RadioButton) findViewById(R.id.radio_alipay);
        mWeixinRadioButton = (RadioButton) findViewById(R.id.radio_weixin);
        mPayButton = (Button) findViewById(R.id.pay_btn);
        mtypeRadioGroup = (RadioGroup) findViewById(R.id.pay_type);
    }

    private void addlistener() {
        mImageView.setOnClickListener(this);
        mPayButton.setOnClickListener(this);
       // mAlipayRadioButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pay_btn:
                if (mtypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_alipay) // 当选择的是支付宝支付时
                    pay(true);
                else if (mtypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_weixin) // 调用插件用微信支付
                    pay(false);
                /*else if (type.getCheckedRadioButtonId() == R.id.query) // 选择查询时
                    query();*/
                break;
        }
    }

    /*@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radio_alipay:
                if (isChecked) {
                    Log.e("alipay", "" + isChecked);
                    Toast.makeText(PayActivity.this, "支付宝支付", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radio_weixin:
                if (isChecked) {
                    Log.e("weixin", "" + isChecked);
                    Toast.makeText(PayActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        // showDialog("正在获取订单...");

        BP.pay(ordername, dcribe, 0.02, alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(PayActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                //tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                //tv.append(name + "'s pay status is success\n\n");
                Log.e("order_id99",order_id+"");
                //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
                mRequestQueue = NoHttp.newRequestQueue();
                //创建请求对象
                request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                //添加请求参数
                request.add("methods", "orderpayok");
                request.add("order_id", order_id);
                mRequestQueue.add(WHAT_ISASSESSORDERS, request, onResponseListener);
                finish();
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                //order.setText(orderId);
                // tv.append(name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                Log.e("payfail",""+code);

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            PayActivity.this,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_SHORT).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(PayActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                /*tv.append(name + "'s pay status is fail, error code is \n"
                        + code + " ,reason is " + reason + "\n\n");*/
                hideDialog();
            }
        });
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }
}
