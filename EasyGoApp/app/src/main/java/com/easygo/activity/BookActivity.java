package com.easygo.activity;
/*
*
* 申请预定的界面
* */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.BookContactAdapter;
import com.easygo.application.MyApplication;
import com.easygo.beans.house.House;
import com.easygo.beans.user.UserLinkman;
import com.easygo.view.WaitDialog;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final int REQUEST_DATE_CODE = 1;
    public static final int BOOK_ORDER_WHAT = 1;//申请预定，添加到数据库
    //自定义一个dialog
    private WaitDialog mDialog;
    ListView mBookListView;
    TextView mAddContactTextView, mInTextView, mOutTextView, mSendTextView;
    EditText mNameEditText, mTelEditText;
    View mBeforeListView, mAfterListView, mCheckLayout, mLeaveLayout;
    BookContactAdapter mAdapter;
    List<UserLinkman> mList, mGetList = null;
    //用来加载头布局
    LayoutInflater mInflater;
    Intent mIntent;
    String inday, outday, mPath, book_name, book_tel;
    int user_id;//前一个页面传过来（如果取不到偏好设置中取出）
    int house_id;//前一个页面传过来
    //网络请求
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_book);
        //顺序不能变
        //先初始化ListView
        initListView();
        initData();
        initView();
        addListeners();
        //加载网络数据
//        loadData();
    }

    private void initListView() {
        mBookListView = (ListView) findViewById(R.id.room_book_listview);
    }

    private void initData() {
        //初始化List
        /*mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < 10; i++) {
            mList.add(new UserLinkman(i, i, "小明" + i, "350500199508105515"));
        }*/
        Intent mIntent = getIntent();
        House house = (House) mIntent.getSerializableExtra("house");
        house_id = house.getHouse_id();
        user_id = mIntent.getIntExtra("userid", 0);//用户的id
        mList = new ArrayList<>();
        mGetList = new ArrayList<>();

    }

    private void initView() {
        mDialog = new WaitDialog(this);//提示框
        //得到ListView之前的布局
        mInflater = LayoutInflater.from(BookActivity.this);
        mBeforeListView = mInflater.inflate(R.layout.room_book_before_listview, null);
        //得到ListView之后的布局
        mAfterListView = mInflater.inflate(R.layout.room_book_after_listview, null);
        mAdapter = new BookContactAdapter(BookActivity.this, mList);
        //将ListView之前，之后的布局加到前面
        mBookListView.addHeaderView(mBeforeListView);
        mBookListView.addFooterView(mAfterListView);
        //添加适配器
        mBookListView.setAdapter(mAdapter);

        //ListView之前控件的初始化
        mCheckLayout = mBeforeListView.findViewById(R.id.room_book_check_layout);
        mLeaveLayout = mBeforeListView.findViewById(R.id.room_book_leave_layout);
        mInTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_inday);
        mOutTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_outday);
/*        mHumanNumberTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_human_number);
        mSubtractTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_subtract);
        mAddTextView = (TextView) mBeforeListView.findViewById(R.id.room_book_add);*/
        //ListView之后的控件
        mNameEditText = (EditText) mAfterListView.findViewById(R.id.room_book_bookname);
        mTelEditText = (EditText) mAfterListView.findViewById(R.id.room_book_tel);
        mAddContactTextView = (TextView) mAfterListView.findViewById(R.id.room_book_add_contact);

        //发送申请
        mSendTextView = (TextView) findViewById(R.id.send_book);

    }

    //用来获取服务器的数据
    private void loadData() {

    }

    private void addListeners() {
        mSendTextView.setOnClickListener(this);
        //ListView之前的控件监听
        mCheckLayout.setOnClickListener(BookActivity.this);
        mLeaveLayout.setOnClickListener(BookActivity.this);
        mInTextView.setOnClickListener(BookActivity.this);
        mOutTextView.setOnClickListener(BookActivity.this);
//        mSubtractTextView.setOnClickListener(BookActivity.this);
//        mAddTextView.setOnClickListener(BookActivity.this);
        //ListView的监听
        mBookListView.setOnItemClickListener(BookActivity.this);
        //ListView之后的控件监听
        mAddContactTextView.setOnClickListener(BookActivity.this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.room_book_check_layout:
//                Toast.makeText(BookActivity.this, "入住", Toast.LENGTH_SHORT).show();
                mIntent = new Intent(BookActivity.this, BookDateActivity.class);
                startActivityForResult(mIntent, REQUEST_DATE_CODE);
                break;
            case R.id.room_book_leave_layout:
//                Toast.makeText(BookActivity.this, "离开", Toast.LENGTH_SHORT).show();
                mIntent = new Intent(BookActivity.this, BookDateActivity.class);
                startActivityForResult(mIntent, REQUEST_DATE_CODE);
                startActivity(mIntent);
                break;
            case R.id.room_book_add_contact:
                Toast.makeText(BookActivity.this, "添加一个入住人", Toast.LENGTH_SHORT).show();
                Intent chooseIntent = new Intent(BookActivity.this, BookChooseLinkmanActivity.class);
                startActivityForResult(chooseIntent, 1);
                break;
            case R.id.send_book:
                sendbook();
                break;
            default:
                break;
        }
    }

    //得到返回的日期，和入住人信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case BookDateActivity.BOOK_DATE_RESULT_CODE:
                //从日历回来的值
                inday = data.getStringExtra("inday");
                outday = data.getStringExtra("outday");
                mInTextView.setText(inday);
                mOutTextView.setText(outday);
                break;
            case BookChooseLinkmanActivity.CHOOSE_LINKMAN_RESULT_CODE:
                Bundle mBundle = data.getExtras();

                mGetList = (List<UserLinkman>) mBundle.getSerializable("chooseLinkman");
                if (mList.size() != 0) {
                    mList.clear();
                    mList.addAll(mGetList);

                } else {
                    mList.addAll(mGetList);

                }
//                mList = (List<UserLinkman>) mBundle.getSerializable("chooseLinkman");

//                Log.e("mime",mList.get(0).getLinkman_name()+"..."+mList.get(0).getUser_linkman_id());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


    //ListView 的监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void sendbook() {
        book_name = mNameEditText.getText().toString();
        book_tel = mTelEditText.getText().toString();
//        Log.e("666:", book_name + book_tel);
        if (book_name == null) {
            Toast.makeText(BookActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (book_tel == null) {
            Toast.makeText(BookActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
            return;
        }
        Gson gson = new Gson();
        String linkmanList = gson.toJson(mList);
        //网络请求
        uploadData(linkmanList);
        //未完待续。。。。。
        /*Intent intent = new Intent();
        intent.setClass(BookActivity.this, OrderDetailActivity.class);
        startActivity(intent);*/
    }

    private void uploadData(String linkmanList) {
        //模拟数据
        MyApplication myApplication = (MyApplication) this.getApplication();
        mPath = myApplication.getUrl();
        //创建请求队列，默认并发3个请求，传入你想要的数字可以改变默认并发数，例如NoHttp.newRequestQueue(1);
        mRequestQueue = NoHttp.newRequestQueue();
        //创建请求对象
        Request<String> request = NoHttp.createStringRequest(mPath, RequestMethod.POST);
        //添加请求参数
        request.add("methods", "addOrderAndLinkman");
        request.add("house_id", house_id);
        request.add("user_id", user_id);
        request.add("checknum", 4);
        request.add("checktime", inday);
        request.add("leavetime", outday);
        request.add("total", 5000.0);
        request.add("tel", book_tel);
        request.add("order_state", "待确认");
        request.add("book_name", book_name);
        request.add("linkman", linkmanList);
        mRequestQueue.add(BOOK_ORDER_WHAT, request, onResponseListener);
    }

    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case BOOK_ORDER_WHAT:
                    Toast.makeText(BookActivity.this, "申请预定成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
            mDialog.show();
        }

        @Override
        public void onFinish(int what) {
            mDialog.dismiss();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            Toast.makeText(BookActivity.this, "网络请求失败了", Toast.LENGTH_SHORT).show();
        }
    };
}
