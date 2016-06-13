package com.easygo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easygo.adapter.CommentDynamicAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.chat_comment.CommentData;
import com.easygo.beans.chat_comment.CommentDynamicData;
import com.easygo.view.WaitDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.ClickNineGridViewAdapter;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ChatDynamicActivity extends AppCompatActivity implements View.OnClickListener {

    //请求对象
    public static final int SHOW_COMMENT = 1;
    public static final int SEND_COMMENT = 2;
    public static final int ZAN = 3;
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

    private ImageView mcomment_imageview, mgridview_image;
    private TextView mfabiao_man, mfabiao_time, mbrowse, mdynamic_content, mnumber_like;
    private LinearLayout mZan, mComment, mForward;
    private NineGridView nineGridView;
    private ListView mListView;
    private int user_id;

    private List<CommentDynamicData> mCommentDynamicData_list;
    private CommentData commentData;
    private CommentDynamicData mCommentDynamicData;
    //添加适配器
    CommentDynamicAdapter mCommentDynamicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dynamic);

        initView();
        initData();
        addListeners();
        initAdapter();

    }

    private void initAdapter() {
        //进行适配
        mCommentDynamicAdapter = new CommentDynamicAdapter(ChatDynamicActivity.this, mCommentDynamicData_list);
        mListView.setAdapter(mCommentDynamicAdapter);
    }

    private void addListeners() {
        mZan.setOnClickListener(this);
        mComment.setOnClickListener(this);
        mForward.setOnClickListener(this);
    }

    private void initData() {
      /*  //用户的头像传给javabean
        mCommentDynamicData.setHeadimgurl(headurl);
        mCommentDynamicData.setNickname(nickname);*/
        mSharedPreferences = ChatDynamicActivity.this.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        //获取该账号的头像url
        user_id = mSharedPreferences.getInt("user_id", 0);


        //初始化评论集合
        mCommentDynamicData_list = new ArrayList<>();
        //添加服务器请求
        MyApplication myApplication = new MyApplication();
        mUrl = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        /**
         * 动态部分
         */
        //获取到点击该动态的值
        Bundle mBundle = getIntent().getExtras();
        //得到点击item的所有的值
        commentData = (CommentData) mBundle.getSerializable("choosedynamic");

        Glide.with(ChatDynamicActivity.this)
                .load(commentData.getUser_photo())
                .bitmapTransform(new CropCircleTransformation(ChatDynamicActivity.this))
                .error(R.mipmap.user_photo_defult)
                .into(mcomment_imageview);
        mfabiao_man.setText(commentData.getUser_nickname());
        mfabiao_time.setText(commentData.getNews_time());
        mbrowse.setText("浏览量：" + commentData.getNews_views() + "次");
        mdynamic_content.setText(commentData.getNews_content());
        mnumber_like.setText(" " + commentData.getNews_stars() + " ");


        //使用框架加载图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = commentData.getPhoto_path();
        if (imageDetails != null) {
            for (String url : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(url);
                info.setBigImageUrl(url);
                imageInfo.add(info);
            }
        }
        //调用框架的适配器
        nineGridView.setAdapter(new ClickNineGridViewAdapter(ChatDynamicActivity.this, imageInfo));

        /**
         * 评论部分,向服务器请求数据
         */
        initCommentData();
    }

    private void initCommentData() {
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //接收服务端传过来的数据，对界面进行更新
        //传输从android端获取到的数据
        request.add("methods", "showcomment");
        request.add("comment_news_id", commentData.getNews_id());

        //将请求添加到队列中
        mRequestQueue.add(SHOW_COMMENT, request, onResponseListener);
    }

    private void initView() {
        //初始化所有控件
        mcomment_imageview = (ImageView) findViewById(R.id.comment_imageview);
        mgridview_image = (ImageView) findViewById(R.id.gridview_image);
        mfabiao_man = (TextView) findViewById(R.id.fabiao_man);
        mfabiao_time = (TextView) findViewById(R.id.fabiao_time);
        mbrowse = (TextView) findViewById(R.id.browse);
        mdynamic_content = (TextView) findViewById(R.id.dynamic_content);
        mnumber_like = (TextView) findViewById(R.id.number_like);
        mZan = (LinearLayout) findViewById(R.id.zan);
        mComment = (LinearLayout) findViewById(R.id.comment);
        mForward = (LinearLayout) findViewById(R.id.forward);
        nineGridView = (NineGridView) findViewById(R.id.gridview_imageview);
        mListView = (ListView) findViewById(R.id.comment_listview);
        mDialog = new WaitDialog(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.zan:
                //点赞
                clicklike();

                break;
            case R.id.comment:
                showCommentDialog();
                break;
            case R.id.forward:
                //返回上一个界面，并且将这个界面的内容进行一次动态发布
                Toast.makeText(ChatDynamicActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    //点赞功能
    private void clicklike() {
        //向数据库发送请求，news表赞数+1
        Log.e(commentData.getNews_stars() + "", "赞的个数是");
        //向服务器发出请求
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        // 创建请求对象
        Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
        //请求服务器获取动态表中的所有动态
        request.add("methods", "zan");
        request.add("news_id", commentData.getNews_id());
        mRequestQueue.add(ZAN, request, onResponseListener);
    }

    //显示出评论输入框
    private void showCommentDialog() {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入要发表的内容：")
                .setView(text)
                .setPositiveButton("评论", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (text.getText().toString().equals("")) {
                            Toast.makeText(ChatDynamicActivity.this, "评论信息不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            // 创建请求对象
                            Request<String> request = NoHttp.createStringRequest(mUrl, RequestMethod.POST);
                            //获取到该动态的id,发表评论人的昵称，发表说说说的内容
                            mCommentDynamicData = new CommentDynamicData(commentData.getNews_id(), user_id, text.getText().toString());
                            //将以上三条数据封装成javabean对象，并进行json传输
                            Gson gson = new Gson();
                            String result = gson.toJson(mCommentDynamicData);
                            //传输从android端获取到的数据
                            request.add("methods", "addcomment");
                            request.add("news_comment", result);
                            //将请求添加到队列中
                            mRequestQueue.add(SEND_COMMENT, request, onResponseListener);
                        }
                    }
                });
        builder.create().show();
    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == SHOW_COMMENT) {
                String comment = response.get();
                Gson gson = new Gson();
                mCommentDynamicData_list = gson.fromJson(comment, new TypeToken<List<CommentDynamicData>>() {
                }.getType());
                //添加适配器
                initAdapter();
            }
            if (what == SEND_COMMENT) {
                String success = response.get();
                if (success.equals("success")) {
                    Toast.makeText(ChatDynamicActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatDynamicActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                }
                initCommentData();
            }
            if (what == ZAN) {
                Toast.makeText(ChatDynamicActivity.this, "赞+1", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(ChatDynamicActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(ChatDynamicActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(ChatDynamicActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(ChatDynamicActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(ChatDynamicActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(ChatDynamicActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(ChatDynamicActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(ChatDynamicActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }
    };
}
