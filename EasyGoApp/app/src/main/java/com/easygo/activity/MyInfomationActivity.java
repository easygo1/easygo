package com.easygo.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easygo.application.MyApplication;
import com.easygo.beans.gson.GsonUserInfoHobby;
import com.easygo.beans.user.User;
import com.easygo.utils.UpYunException;
import com.easygo.utils.UpYunUtils;
import com.easygo.utils.Uploader;
import com.easygo.view.CityDialog;
import com.easygo.view.MyPopupWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyInfomationActivity extends AppCompatActivity implements View.OnClickListener {
    //第三方图片存储
    private static final String DEFAULT_DOMAIN = "easygo.b0.upaiyun.com";// 空间域名
    private static final String API_KEY = "3AtEDO2ByBUZ7qGVTLPUnuKLOWM="; // 表单api验证密钥
    private static final String BUCKET = "easygo";// 空间名称
    private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 1000 * 5 * 10; // 过期时间，必须大于当前时间

    public static final int TAKE_PHOTO = 1;
    public static final int TAKE_CAMERA = 0;
    private TextView mTextView;
    private ImageView mReturnImageView;
    private RelativeLayout mChangeHeadLayout, mChangeUsernameLayout, mChangeRealnameLayout, mChangeCityLayout, mChangeSexLayout, mChangeJobLayout, mChangeLabelLayout, mChangeAutographLayout, mChangeDateBirthLayout, mChangePasswordLayout, mChangeInstroductLayout, mChangeEmailLayout;
    private TextView mChangeidcardTextView,mChangeHeadTextView, mChangeUsernameTextView, mChangeRealnameTextView, mChangeUserIdcardTextView, mChangeCityTextView, mChangeSexTextView, mChangeJobTextView, mChangeLabelTextView, mChangeAutographTextView, mChangeDateBirthTextView, mChangePasswordTextView, mChangeInstroductTextview, mChangeEmailTextView;
    private Button mSuccessButton;
    private MyPopupWindow popMenus;

    int type;

    //复选框
    boolean[] flags = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false};


    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    private File mCurrentPhotoFile;//获取当前相册选中的图片文件
    private String mphotopath;//相册图片的地址
    private String mloadpath;//图片上传到服务器的地址
    private ImageView mshowImageView;//显示用户头像
    private ClipboardManager clipboard;
    private CityDialog dialog;
    //nohttp
    public static final int WHAT = 1;
    public static final int WHAT_UPDATE_HOBBY = 2;
    public static final int WHAT_UPDATE_PHOTO = 3;
    public static final int WHAT_GETUSERINFO = 4;
    GsonUserInfoHobby mGsonUserInfoHobby;
    User user;
    List<String> hobbyNamelist;

    String mUrl;
    MyApplication myApplication;
    Request<String> request;
    List<String> lables;
    String lablesString = "";
    String[] address;//用户地址
    String province = "";
    String city = "";
    String birthday = "";
    String user_realname = "";
    String user_nickname = "";
    String user_sex = "";
    String user_job = "";
    String user_mood = "";
    String user_mail = "";
    String user_introduct = "";
    String user_idcard="";

    //创建请求队列，默认并发3请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
    //只用定义一次
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue(4);//请求队列

    private int user_id;//在偏好设置中获取
    private String selectedStr = "";//用户的标签多选

    private OnResponseListener<String> mOnResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            String result = response.get();
            switch (what) {
                case WHAT_GETUSERINFO:
                    //Toast.makeText(MyInfomationActivity.this, "result得到用户信息" + result, Toast.LENGTH_SHORT).show();
                    //解析对象
                    Gson gson = new Gson();
                    Type mytype = new TypeToken<GsonUserInfoHobby>() {
                    }.getType();
                    mGsonUserInfoHobby = gson.fromJson(result, mytype);
                    if (mGsonUserInfoHobby.getUser() != null) {
                        user = mGsonUserInfoHobby.getUser();
                        Log.e("user对象", user.toString());
                    }
                    if (mGsonUserInfoHobby.getUserHobbyNamelist() != null) {
                        hobbyNamelist = mGsonUserInfoHobby.getUserHobbyNamelist();
                    }
                    if (user.getUser_photo() != null) {
                        Glide.with(MyInfomationActivity.this)
                                .load(user.getUser_photo())
                                .bitmapTransform(new CropCircleTransformation(MyInfomationActivity.this))
                                .error(R.mipmap.user_photo_defult)
                                .into(mshowImageView);
                    }
                    if (user.getUser_nickname() != null) {
                        mChangeUsernameTextView.setText(user.getUser_nickname());
                    }
                    if (user.getUser_realname() != null) {
                        mChangeRealnameTextView.setText(user.getUser_realname());
                    }
                    if (user.getUser_idcard() != null) {
                        mChangeUserIdcardTextView.setText(user.getUser_idcard());
                    }
                    if ((user.getUser_address_province() != null) && (user.getUser_address_city() != null)) {
                        mChangeCityTextView.setText(user.getUser_address_province() + "-" + user.getUser_address_city());
                    }
                    if (user.getUser_sex() != null) {
                        mChangeSexTextView.setText(user.getUser_sex());
                    }
                    if (user.getUser_job() != null) {
                        mChangeJobTextView.setText(user.getUser_job());
                    }
                    for (int i = 0; i < hobbyNamelist.size(); i++) {
                        lablesString += hobbyNamelist.get(i) + " ";
//                        Log.e("lablesString", lablesString);
//                        Log.e("hobby", i + hobbyNamelist.get(i));
                    }
                    if (lablesString != null) {
                        mChangeLabelTextView.setText(lablesString);
                    }
                    if (user.getUser_mood() != null) {
                        mChangeAutographTextView.setText(user.getUser_mood());
                    }
                    if (user.getUser_birthday() != null) {
                        mChangeDateBirthTextView.setText(user.getUser_birthday());
                    }
                    if (user.getUser_introduct() != null) {
                        mChangeInstroductTextview.setText(user.getUser_introduct());
                    }
                    if (user.getUser_mail() != null) {
                        mChangeEmailTextView.setText(user.getUser_mail());
                    }
                    if(user.getUser_idcard()!=null){
                        mChangeidcardTextView.setText(user.getUser_idcard());
                    }
                    break;
                case WHAT_UPDATE_PHOTO:
                    Toast.makeText(MyInfomationActivity.this, "result更新photo" + result, Toast.LENGTH_SHORT).show();
                    break;
                case WHAT_UPDATE_HOBBY:
                    //Log.e("zfg", "hobby" + result);
                    Toast.makeText(MyInfomationActivity.this, "更新hobby" + result, Toast.LENGTH_SHORT).show();
                    break;
                case WHAT:
                    Toast.makeText(MyInfomationActivity.this, "更新用户" + result, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (exception instanceof ClientError) {// 客户端错误
                Toast.makeText(MyInfomationActivity.this, "客户端发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof ServerError) {// 服务器错误
                Toast.makeText(MyInfomationActivity.this, "服务器发生错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(MyInfomationActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(MyInfomationActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(MyInfomationActivity.this, "未发现指定服务器", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(MyInfomationActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                Toast.makeText(MyInfomationActivity.this, "没有发现缓存", Toast.LENGTH_SHORT).show();
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            } else {
                Toast.makeText(MyInfomationActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infomation);
        myApplication = (MyApplication) this.getApplication();
        mUrl = myApplication.getUrl();
        initView();
        initAllData();
        addListeners();

    }

    private void initAllData() {
        mSharedPreferences = this.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
        type = mSharedPreferences.getInt("type", 0);
        user_id = mSharedPreferences.getInt("user_id", 0);//整个页面要用
        if (user_id != 0) {
            // /创建请求对象
            request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
            //添加请求参数
            request.add("methods", "selectInfoById");
            request.add("user_id", user_id);
            mRequestQueue.add(WHAT_GETUSERINFO, request, mOnResponseListener);
        } else {

        }
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.title_text);
        mTextView.setText("我的信息");
        mReturnImageView = (ImageView) findViewById(R.id.back);
        mChangeHeadLayout = (RelativeLayout) findViewById(R.id.change_head);
        mChangeUsernameLayout = (RelativeLayout) findViewById(R.id.change_username);
        mChangeRealnameLayout = (RelativeLayout) findViewById(R.id.change_realname);
        mChangeCityLayout = (RelativeLayout) findViewById(R.id.change_city);
        mChangeSexLayout = (RelativeLayout) findViewById(R.id.change_sex);
        mChangeJobLayout = (RelativeLayout) findViewById(R.id.change_job);
        mChangeLabelLayout = (RelativeLayout) findViewById(R.id.change_label);
        mChangeAutographLayout = (RelativeLayout) findViewById(R.id.change_autograph);
        mChangeDateBirthLayout = (RelativeLayout) findViewById(R.id.change_date_birth);
        mChangePasswordLayout = (RelativeLayout) findViewById(R.id.change_password);
        mChangeInstroductLayout = (RelativeLayout) findViewById(R.id.change_introduce);
        mChangeEmailLayout = (RelativeLayout) findViewById(R.id.change_email);
        mSuccessButton = (Button) findViewById(R.id.myinformation_success);
        //个人具体信息
        mChangeUsernameTextView = (TextView) findViewById(R.id.change_username_textView);
        mChangeRealnameTextView = (TextView) findViewById(R.id.change_realname_textView);
        mChangeUserIdcardTextView = (TextView) findViewById(R.id.user_idcard_textView);
        mChangeSexTextView = (TextView) findViewById(R.id.change_sex_textView);
        mChangeJobTextView = (TextView) findViewById(R.id.change_job_textView);
        mChangeCityTextView = (TextView) findViewById(R.id.change_city_textView);
        mChangeLabelTextView = (TextView) findViewById(R.id.change_label_textView);
        mChangeAutographTextView = (TextView) findViewById(R.id.change_autograph_textView);
        mChangeDateBirthTextView = (TextView) findViewById(R.id.change_date_birth_textView);
        mChangePasswordTextView = (TextView) findViewById(R.id.change_password_textView);
        mChangeInstroductTextview = (TextView) findViewById(R.id.change_instroduce_textView);
        mChangeEmailTextView = (TextView) findViewById(R.id.change_email_textView);
        mChangeidcardTextView= (TextView) findViewById(R.id.user_idcard_textView);
        mshowImageView = (ImageView) findViewById(R.id.photo_show);
    }

    private void addListeners() {
        mReturnImageView.setOnClickListener(this);
        mChangeHeadLayout.setOnClickListener(this);
        mChangeUsernameLayout.setOnClickListener(this);
        mChangeRealnameLayout.setOnClickListener(this);
        mChangeCityLayout.setOnClickListener(this);
        mChangeSexLayout.setOnClickListener(this);
        mChangeJobLayout.setOnClickListener(this);
        mChangeLabelLayout.setOnClickListener(this);
        mChangeAutographLayout.setOnClickListener(this);
        mChangeDateBirthLayout.setOnClickListener(this);
        mChangePasswordTextView.setOnClickListener(this);
        mChangeInstroductLayout.setOnClickListener(this);
        mChangeEmailLayout.setOnClickListener(this);
        mSuccessButton.setOnClickListener(this);
    }

    public void success(View view) {
        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
        mSharedPreferences = getSharedPreferences(TYPE, MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("type", 2);
        //提交保存结果
        mEditor.commit();
        Intent intent = new Intent();
        intent.putExtra("flag", "me");
        intent.setClass(MyInfomationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                Intent intent = new Intent();
                intent.putExtra("flag", "me");
                intent.setClass(MyInfomationActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.change_head:
                showChangeHeadDialog();
                break;
            case R.id.change_username:
                showChangeUsernameDialog();
                break;
            case R.id.change_realname:
                showChangeRealnameDialog();
                break;
            case R.id.change_city:
                showChangeCityDialog();
                break;
            case R.id.change_sex:
                showChangeSexDialog();
                break;
            case R.id.change_job:
                showChangeJobDialog();
                break;
            case R.id.change_label:
                showChangeLabelDialog();
                break;
            case R.id.change_autograph:
                showChangeAutographDialog();
                break;
            case R.id.change_date_birth:
                showChangeDateBirthDialog();
                break;
            case R.id.change_password:
                showChangePasswordDialog();
                break;
            case R.id.change_introduce:
                showChangeIntroduceDialog();
                break;
            case R.id.change_email:
                showChangeEmailDialog();
                break;
            case R.id.myinformation_success:
                getUpdateData();
                //创建请求对象
                request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                //添加请求参数
                request.add("methods", "updateUserById");
                request.add("user_id", user_id);
                request.add("user_realname", user_realname);
                request.add("user_nickname", user_nickname);
                request.add("user_sex", user_sex);
                request.add("user_job", user_job);
                request.add("user_address_province", province);
                request.add("user_address_city", city);
                request.add("user_mood", user_mood);
                request.add("user_mail", user_mail);
                request.add("user_introduct", user_introduct);
                request.add("user_birthday", birthday);
                mRequestQueue.add(WHAT, request, mOnResponseListener);
                intent = new Intent();
                intent.putExtra("flag", "me");
                intent.setClass(MyInfomationActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getUpdateData() {
        if (!mChangeCityTextView.getText().toString().equals("选择")) {
            address = mChangeCityTextView.getText().toString().split("\\-");
            province = address[0];
            if (address.length <= 1) {
                city = "";
            } else {
                city = address[1];
            }
        }
        if (!mChangeDateBirthTextView.getText().toString().equals("选择")) {
            birthday = mChangeDateBirthTextView.getText().toString();
        }
        if (!mChangeRealnameTextView.getText().toString().equals("选择")) {
            user_realname = mChangeRealnameTextView.getText().toString();
        }
        if (!mChangeUsernameTextView.getText().toString().equals("选择")) {
            user_nickname = mChangeUsernameTextView.getText().toString();
        }
        if (!mChangeSexTextView.getText().toString().equals("选择")) {
            user_sex = mChangeSexTextView.getText().toString();
        }
        if (!mChangeJobTextView.getText().toString().equals("选择")) {
            user_job = mChangeJobTextView.getText().toString();
        }
        if (!mChangeAutographTextView.getText().toString().equals("选择")) {
            user_mood = mChangeAutographTextView.getText().toString();
        }
        if (!mChangeInstroductTextview.getText().toString().equals("选择")) {
            user_introduct = mChangeInstroductTextview.getText().toString();
        }
        if (!mChangeEmailTextView.getText().toString().equals("选择")) {
            user_mail = mChangeEmailTextView.getText().toString();
        }
    }

    //填写自己的真实姓名
    private void showChangeRealnameDialog() {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入你的真实姓名：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (text.getText().toString() != null) {
                            mChangeRealnameTextView.setText(text.getText().toString());
                        }
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
    }

    //更换密码
    private void showChangePasswordDialog() {

    }

    //选择出生日期
    private void showChangeDateBirthDialog() {
        int year, monthOfYear, dayOfMonth;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(MyInfomationActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mChangeDateBirthTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, monthOfYear, dayOfMonth);

        datePickerDialog.show();
    }

    //编辑自己的个性签名
    private void showChangeAutographDialog() {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入你的个性签名：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (text.getText().toString() != null) {
                            mChangeAutographTextView.setText(text.getText().toString());
                        }
                    }
                })
                .setNegativeButton("取消", null);
        text.setText(user.getUser_mood());
        builder.create().show();
    }

    //选择标签
    private void showChangeLabelDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择标签");
        builder.setMultiChoiceItems(R.array.label, flags, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                flags[which] = isChecked;//单击时将是否选中记下来，默认是未选中
            }
        });
        //添加一个确定按钮
        builder.setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                lables = new ArrayList<String>();
                for (int i = 0; i < flags.length; i++) {
                    if (flags[i] == true) {
                        selectedStr = selectedStr + " " +
                                getResources().getStringArray(R.array.label)[i];
                        lables.add(getResources().getStringArray(R.array.label)[i]);
                    }
                }
                mChangeLabelTextView.setText(selectedStr);
                Log.e("标签对象", lables.toString());
                /*MyApplication myApplication = (MyApplication) this.getApplication();
                mUrl = myApplication.getUrl();*/
                //创建请求对象
                request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
                //添加请求参数
                Gson gson = new Gson();
                lablesString = gson.toJson(lables);
                request.add("methods", "updateUserHobbyByid");
                request.add("lables", lablesString);
                request.add("user_id", user_id);
                mRequestQueue.add(WHAT_UPDATE_HOBBY, request, mOnResponseListener);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //更换性别
    private void showChangeSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.sex, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sexStr = getResources().getStringArray(R.array.sex)[which];
                if (!sexStr.equals("选择")) {
                    mChangeSexTextView.setText(sexStr);
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //更换职业
    private void showChangeJobDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.job, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sexStr = getResources().getStringArray(R.array.job)[which];
                if (!sexStr.equals("选择")) {
                    mChangeJobTextView.setText(sexStr);
                }

                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //选择所在地
    private void showChangeCityDialog() {
        CityDialog.InputListener listener = new CityDialog.InputListener() {
            @Override
            public void getText(String str) {
                mChangeCityTextView.setText(str);
            }
        };
        dialog = new CityDialog(MyInfomationActivity.this, listener);
        dialog.setTitle("省市区");
        dialog.show();
    }

    //更换昵称
    private void showChangeUsernameDialog() {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入昵称：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (text.getText().toString() != null) {
                            mChangeUsernameTextView.setText(text.getText().toString());
                        } else {
                            Toast.makeText(MyInfomationActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
    }

    //更换邮箱
    private void showChangeEmailDialog() {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入邮箱：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (text.getText().toString() != null) {
                            mChangeEmailTextView.setText(text.getText().toString());
                        } else {
                            Toast.makeText(MyInfomationActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
    }

    //修改个人介绍
    private void showChangeIntroduceDialog() {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入自我介绍：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (text.getText().toString() != null) {
                            mChangeInstroductTextview.setText(text.getText().toString());
                        } else {
                            Toast.makeText(MyInfomationActivity.this, "自我介绍不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
    }

    //更换头像
    private void showChangeHeadDialog() {
        //自定义的弹出框类
        mChangeHeadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenus = new MyPopupWindow(MyInfomationActivity.this, itemsOnClick);
                popMenus.showAtLocation(MyInfomationActivity.this.findViewById(R.id.change_head),
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
                    Toast.makeText(MyInfomationActivity.this, "点击了拍照", Toast.LENGTH_SHORT).show();
                    takeCamera();
                    break;
                case R.id.btn_pick_photo:
                    //从相册获取
                    Toast.makeText(MyInfomationActivity.this, "点击了从相册获取", Toast.LENGTH_SHORT).show();
                    takePhoto();
                    break;
                default:
                    break;
            }
        }
    };

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

    //相册上传
    private void takePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //上传图片
    public class UploadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String str = null;
            try {
                Log.i("test", "文件大小：" + new File(params[0]).length() / (1024 * 1024f) + "M");
                // 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
                String SAVE_KEY = File.separator + "user" + File.separator + System.currentTimeMillis() + ".jpg";
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
            mloadpath = result;//得到上传后的图片地址
            //创建请求对象
            request = NoHttp.createStringRequest(mUrl, RequestMethod.GET);
            //添加请求参数
            request.add("methods", "updateUserPhoto");
            request.add("user_id", user_id);
            request.add("user_photo", "http://"+mloadpath);
            Log.e("头像", mloadpath);
            mRequestQueue.add(WHAT_UPDATE_PHOTO, request, mOnResponseListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                //相册选取
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        Glide.with(MyInfomationActivity.this).load(getPhotoPath(this, selectedImage))
                                .bitmapTransform(new CropCircleTransformation(this))
                                .error(R.mipmap.user_photo_defult)
                                .into(mshowImageView);
                        //得到该图片在手机中的路径
                        new UploadTask().execute(getPhotoPath(this, selectedImage));
                    }
                }
                break;
            case TAKE_CAMERA:
                Glide.with(MyInfomationActivity.this).load(mCurrentPhotoFile.getAbsolutePath())
                        .bitmapTransform(new CropCircleTransformation(this))
                        .error(R.mipmap.user_photo_defult)
                        .into(mshowImageView);
                //拍照选取
                new UploadTask().execute(mCurrentPhotoFile.getAbsolutePath());
                break;
        }
    }

    //相册图片上传地址
    private String getPhotoPath(Context context, Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //光标
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        mphotopath = picturePath;
        cursor.close();
        return picturePath;
    }
}
