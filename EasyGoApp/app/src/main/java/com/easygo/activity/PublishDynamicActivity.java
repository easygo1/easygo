package com.easygo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.PublishDynamicAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.chat_comment.CommentDynamic;
import com.easygo.utils.UpYunException;
import com.easygo.utils.UpYunUtils;
import com.easygo.utils.Uploader;
import com.easygo.view.MyPopupWindow;
import com.easygo.view.WaitDialog;
import com.google.gson.Gson;
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
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发表动态
 */
public class PublishDynamicActivity extends AppCompatActivity implements View.OnClickListener {
    //请求对象
    public static final int SEND_DYNAMIC = 2;
    //自定义一个dialog
    private WaitDialog mDialog;
    /**
     * 请求队列.
     */
    private RequestQueue mRequestQueue;
    String mUrl;
    //偏好设置
    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    //
    public static final int TAKE_CAMERA = 0;
    public static final int TAKE_PHOTO = 1;
    private static final int REQUEST_CODE = 732;
    private static final String DEFAULT_DOMAIN = "easygo.b0.upaiyun.com";// 空间域名
    private static final String API_KEY = "3AtEDO2ByBUZ7qGVTLPUnuKLOWM="; // 表单api验证密钥
    private static final String BUCKET = "easygo";// 空间名称
    private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 1000 * 5 * 10; // 过期时间，必须大于当前时间

    private ImageView mBack, mAddPhoto;
    private TextView mDynamic;
    private EditText mPublishDynamic;
    private GridView mNoScrollGridview;
    private String path;
    private List<String> mphotopath;//相册图片的地址
    private File mCurrentPhotoFile;//获取当前相册选中的图片文件
    //发消息的id
    String dynamic;

    private int user_id;
    //定义发说说的javabean
    CommentDynamic mCommentDynamic;

    private MyPopupWindow popMenus;

    //添加图片的适配器
    PublishDynamicAdapter mPublishDynamicAdapter;
    List<String> gridviewlist;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        initView();
        initData();
        addListener();
    }

    private void initData() {
        //模拟数据
        gridviewlist = new ArrayList<>();
        mphotopath = new ArrayList<>();
        //添加服务器请求
        MyApplication myApplication = new MyApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();

        //初始化gridview中的照片
        mPublishDynamicAdapter = new PublishDynamicAdapter(PublishDynamicActivity.this, gridviewlist, mNoScrollGridview);
        mNoScrollGridview.setAdapter(mPublishDynamicAdapter);
    }

    private void addListener() {
        mBack.setOnClickListener(this);
        mDynamic.setOnClickListener(this);
        mAddPhoto.setOnClickListener(this);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mDynamic = (TextView) findViewById(R.id.dynamic);
        mPublishDynamic = (EditText) findViewById(R.id.publishdynamic);
        mNoScrollGridview = (GridView) findViewById(R.id.noScrollgridview);
        mAddPhoto = (ImageView) findViewById(R.id.add_photo);
        mDialog = new WaitDialog(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                //返回
                back();
                break;
            case R.id.dynamic:

                //发表,向数据库中插入一条信息
                for (int i = 0; i < gridviewlist.size(); i++) {
                    new UploadTask().execute(gridviewlist.get(i));
                }

               /* if (mphotopath.size() == gridviewlist.size()) {
                    Log.e("网络图片", mphotopath.toString());
                    publishdynamic();
                }*/
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            while (true) {
                                if (mphotopath.size() == gridviewlist.size()) {
                                    Log.e("网络图片", mphotopath.toString());
                                    publishdynamic();
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
                break;
            case R.id.add_photo:
                //添加图片
                setAddPhoto();
                break;
        }
    }

    //发表说说，向数据库插入一条信息
    private void publishdynamic() {
        // mphotopath=new ArrayList<>();
        mSharedPreferences = PublishDynamicActivity.this.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        user_id = mSharedPreferences.getInt("user_id", 0);
        if (0 == user_id) {
            Toast.makeText(PublishDynamicActivity.this, "请先进行登录", Toast.LENGTH_SHORT).show();
        } else {
            // 创建请求对象
            Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
            //获取到说说说的内容
            dynamic = mPublishDynamic.getText().toString();
            mCommentDynamic = new CommentDynamic(user_id, dynamic, mphotopath);
            //将其封装成gson对象
            Gson gson = new Gson();
            String result = gson.toJson(mCommentDynamic);
            //传输从android端获取到的数据
            request.add("methods", "adddynamic");
            request.add("news_dynamic", result);
            //将请求添加到队列中
            mRequestQueue.add(SEND_DYNAMIC, request, onResponseListener);
        }

    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            //接收服务端返回的数据进行判断并进行页面跳转
            if (what == SEND_DYNAMIC) {
                String success = response.get();
                if (success.equals("success")) {
                    Toast.makeText(PublishDynamicActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    //跳转到动态页面
                    Intent intent = new Intent(PublishDynamicActivity.this, MainActivity.class);

                    startActivity(intent);

                } else {
                    Toast.makeText(PublishDynamicActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(PublishDynamicActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(PublishDynamicActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(PublishDynamicActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(PublishDynamicActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(PublishDynamicActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(PublishDynamicActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(PublishDynamicActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(PublishDynamicActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }
    };

    //返回到上一个界面
    private void back() {
        finish();
    }

    //发说说时添加图片
    private void setAddPhoto() {
        //自定义的弹出框类
        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenus = new MyPopupWindow(PublishDynamicActivity.this, itemsOnClick);
                popMenus.showAtLocation(PublishDynamicActivity.this.findViewById(R.id.add_photo),
                        Gravity.BOTTOM, 0, 0);
            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            popMenus.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    //拍照上传
                    Toast.makeText(PublishDynamicActivity.this, "点击了拍照", Toast.LENGTH_SHORT).show();
                    takeCamera();
                    break;
                case R.id.btn_pick_photo:
                    //从相册获取
                    Toast.makeText(PublishDynamicActivity.this, "点击了从相册获取", Toast.LENGTH_SHORT).show();
                    addphoto();
                    break;
                default:
                    break;
            }
        }
    };


    //说说添加照片
    private void addphoto() {
        //打开了系统相册
        Intent intent = new Intent(PublishDynamicActivity.this, ImagesSelectorActivity.class);
        // 最多上传15张照片
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 15);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //拍照
    private void takeCamera() {
        String photoDir = Environment.getExternalStorageDirectory().getPath() + "/devstore_image";
        try {
            File photo_dir = new File(photoDir);
            if (!photo_dir.exists()) {
                photo_dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            mCurrentPhotoFile = new File(photo_dir, fileName);
            // 开启相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            startActivityForResult(intent, TAKE_CAMERA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传图片
    public class UploadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String str = null;
            try {
                Log.i("test", "文件大小：" + new File(params[0]).length() / (1024 * 1024f) + "M");
                // 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
                String SAVE_KEY = File.separator + "senddynamic" + File.separator + System.currentTimeMillis() + ".jpg";
                // 取得base64编码后的policy
                String policy = null;
                policy = UpYunUtils.makePolicy(SAVE_KEY, EXPIRATION, BUCKET);
                // 根据表单api签名密钥对policy进行签名
                // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                String signature = UpYunUtils.signature(policy + "&" + API_KEY);
                long startTime = System.currentTimeMillis();
                Log.i("test", "开始上传：" + startTime);
                // 上传文件到对应的bucket中去。
                str = DEFAULT_DOMAIN + Uploader.upload(policy, signature, BUCKET, params[0]);
//                Toast.makeText(MyInfomationActivity.this, "上传用时"+(System.currentTimeMillis() - startTime) + "ms", Toast.LENGTH_SHORT).show();
                Log.i("test", "上传完成：" + (System.currentTimeMillis() - startTime) + "ms");
            } catch (UpYunException e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            path = result;
            if (result != null) {
                //Log.i("test", "上传成功,访问地址为：" + result);
                //Toast.makeText(getActivity(), "上传成功,访问地址为：" + result, Toast.LENGTH_SHORT).show();
                Log.e("网络路径为", path);
                mphotopath.add(path);
            } else {
                //Log.i("test", "上传图片失败");
                Toast.makeText(PublishDynamicActivity.this, "上传图片失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                //相册选取
                if (data != null) {
                    //得到的是本地的路径
                    gridviewlist = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                    for (int i = 0; i < gridviewlist.size(); i++) {
//                        new UploadTask().execute(gridviewlist.get(i));
                        Toast.makeText(PublishDynamicActivity.this, gridviewlist.get(i), Toast.LENGTH_SHORT).show();
                    }
                    assert gridviewlist != null;
                    //初始化gridview中的照片
                    mPublishDynamicAdapter = new PublishDynamicAdapter(PublishDynamicActivity.this, gridviewlist, mNoScrollGridview);
                    mNoScrollGridview.setAdapter(mPublishDynamicAdapter);
                }
                break;
            case TAKE_CAMERA:

                //拍照选取
                new UploadTask().execute(mCurrentPhotoFile.getAbsolutePath());
                //初始化gridview中的照片
                mPublishDynamicAdapter = new PublishDynamicAdapter(PublishDynamicActivity.this, gridviewlist, mNoScrollGridview);
                mNoScrollGridview.setAdapter(mPublishDynamicAdapter);
                break;
        }
    }
}
