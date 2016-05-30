package com.easygo.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.view.MyPopupWindow;

import java.util.Calendar;

public class MyInfomationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mReturnImageView;
    private RelativeLayout mChangeHeadLayout,mChangeUsernameLayout,mChangeRealnameLayout,mChangeCityLayout,mChangeSexLayout,mChangeLabelLayout,mChangeAutographLayout,mChangeDateBirthLayout,mChangePasswordLayout;
    private TextView mChangeHeadTextView,mChangeUsernameTextView,mChangeRealnameTextView,mChangeCityTextView,mChangeSexTextView,mChangeLabelTextView,mChangeAutographTextView,mChangeDateBirthTextView,mChangePasswordTextView;
    private Button mSuccessButton;
    private MyPopupWindow popMenus;

    //复选框
    boolean[] flags=new boolean[]{false,false,false,false,false,false,false,false,false,false};


    public static final String TYPE = "type";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infomation);
        initView();
        addListeners();
    }

    private void initView() {
        mReturnImageView = (ImageView) findViewById(R.id.myinformation_return);

        mChangeHeadLayout = (RelativeLayout) findViewById(R.id.change_head);
        mChangeUsernameLayout = (RelativeLayout) findViewById(R.id.change_username);
        mChangeRealnameLayout = (RelativeLayout) findViewById(R.id.change_realname);
        mChangeCityLayout = (RelativeLayout) findViewById(R.id.change_city);
        mChangeSexLayout = (RelativeLayout) findViewById(R.id.change_sex);
        mChangeLabelLayout = (RelativeLayout) findViewById(R.id.change_label);
        mChangeAutographLayout = (RelativeLayout) findViewById(R.id.change_autograph);
        mChangeDateBirthLayout = (RelativeLayout) findViewById(R.id.change_date_birth);
        mChangePasswordLayout = (RelativeLayout) findViewById(R.id.change_password);

        mSuccessButton = (Button) findViewById(R.id.myinformation_success);

        mChangeHeadTextView = (TextView) findViewById(R.id.change_head_textView);
        mChangeUsernameTextView = (TextView) findViewById(R.id.change_username_textView);
        mChangeRealnameTextView = (TextView) findViewById(R.id.change_realname_textView);
        mChangeSexTextView = (TextView) findViewById(R.id.change_sex_textView);
        mChangeCityTextView = (TextView) findViewById(R.id.change_city_textView);
        mChangeLabelTextView = (TextView) findViewById(R.id.change_label_textView);
        mChangeAutographTextView = (TextView) findViewById(R.id.change_autograph_textView);
        mChangeDateBirthTextView = (TextView) findViewById(R.id.change_date_birth_textView);
        mChangePasswordTextView = (TextView) findViewById(R.id.change_password_textView);

    }

    private void addListeners() {
        mReturnImageView.setOnClickListener(this);
        mChangeHeadLayout.setOnClickListener(this);
        mChangeUsernameLayout.setOnClickListener(this);
        mChangeRealnameLayout.setOnClickListener(this);
        mChangeCityLayout.setOnClickListener(this);
        mChangeSexLayout.setOnClickListener(this);
        mChangeLabelLayout.setOnClickListener(this);
        mChangeAutographLayout.setOnClickListener(this);
        mChangeDateBirthLayout.setOnClickListener(this);
        mChangePasswordTextView.setOnClickListener(this);
        //mSuccessButton.setOnClickListener(this);
    }

    public void success(View view) {
        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
        mSharedPreferences = getSharedPreferences(TYPE,MODE_PRIVATE);
        //向偏好设置文件中保存数据
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("type", 2);
        //提交保存结果
        mEditor.commit();
        Intent intent = new Intent();
        intent.putExtra("flag","me");
        intent.setClass(MyInfomationActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.myinformation_return:
                Intent intent = new Intent();
                intent.putExtra("flag","me");
                intent.setClass(MyInfomationActivity.this,MainActivity.class);
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
        }
    }
    //填写自己的真实姓名
    private void showChangeRealnameDialog() {
        final EditText text=new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入你的真实姓名：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChangeRealnameTextView.setText(text.getText().toString());
                    }
                })
                .setNegativeButton("取消",null);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(MyInfomationActivity.this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {
                mChangeDateBirthTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, monthOfYear, dayOfMonth);

        datePickerDialog.show();
    }
    //编辑自己的个性签名
    private void showChangeAutographDialog() {
        final EditText text=new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入你的个性签名：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChangeAutographTextView.setText(text.getText().toString());
                    }
                })
                .setNegativeButton("取消",null);
        builder.create().show();
    }
    //选择标签
    private void showChangeLabelDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择标签");
        builder.setMultiChoiceItems(R.array.label, flags, new DialogInterface.OnMultiChoiceClickListener(){
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String label = getResources().getStringArray(R.array.label)[which];
                mChangeLabelTextView.setText(label);
            }
        });
        //添加一个确定按钮
        builder.setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {

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
                mChangeSexTextView.setText(sexStr);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    //选择所在地
    private void showChangeCityDialog() {

    }
    //更换昵称
    private void showChangeUsernameDialog() {
        final EditText text=new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入昵称：")
                .setView(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChangeUsernameTextView.setText(text.getText().toString());
                    }
                })
                .setNegativeButton("取消",null);
        builder.create().show();
    }
    //更换头像
    private void showChangeHeadDialog() {

        //自定义的弹出框类
        mChangeHeadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenus=new MyPopupWindow(MyInfomationActivity.this,itemsOnClick) ;
                popMenus.showAtLocation(MyInfomationActivity.this.findViewById(R.id.change_head_textView),
                        Gravity.BOTTOM, 0, 0);
            }
        });

    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            popMenus.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    Toast.makeText(MyInfomationActivity.this, "点击了从相册获取", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_pick_photo:
                    Toast.makeText(MyInfomationActivity.this, "点击了拍照", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
