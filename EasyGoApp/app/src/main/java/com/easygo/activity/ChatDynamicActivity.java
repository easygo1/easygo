package com.easygo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.adapter.CommentDynamicAdapter;
import com.easygo.beans.chat_comment.CommentData;
import com.easygo.beans.chat_comment.CommentDynamicData;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.ClickNineGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatDynamicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mcomment_imageview,mgridview_image;
    private TextView mfabiao_man,mfabiao_time,mbrowse,mdynamic_content;
    private LinearLayout mZan,mComment,mForward;
    private NineGridView nineGridView;
    private ListView mListView;
    List<String> gridview_list;

    private List<CommentDynamicData> mCommentDynamicData_list;
    List<CommentData> commentDataList;
    //添加适配器
    CommentDynamicAdapter mCommentDynamicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dynamic);
        initView();
        initData();
        addListeners();
        //进行适配
        mCommentDynamicAdapter=new CommentDynamicAdapter(ChatDynamicActivity.this,mCommentDynamicData_list);
        mListView.setAdapter(mCommentDynamicAdapter);
    }

    private void addListeners() {
        mZan.setOnClickListener(this);
        mComment.setOnClickListener(this);
        mForward.setOnClickListener(this);
    }

    private void initData() {
        //初始化评论集合
        mCommentDynamicData_list=new ArrayList<>();
        commentDataList=new ArrayList<>();

        gridview_list=new ArrayList<>();
        //添加图片
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");

        CommentData commentData=new CommentData(R.mipmap.ic_launcher,"rose","12:00","浏览：321次","今天天气真好",gridview_list);

        mcomment_imageview.setImageResource(commentData.getComment_imageview());
        mfabiao_man.setText(commentData.getFabiao_man());
        mfabiao_time.setText(commentData.getFabiao_time());
        mbrowse.setText(commentData.getBrowse());
        mdynamic_content.setText(commentData.getDynamic_content());

        //使用框架加载图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = commentData.getImgUrls();
        if (imageDetails != null) {
            for (String url : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(url);
                info.setBigImageUrl(url);
                imageInfo.add(info);
            }
        }
        //调用框架的适配器
        nineGridView.setAdapter(new ClickNineGridViewAdapter(ChatDynamicActivity.this,imageInfo));
    }

    private void initView() {
        //初始化所有控件
        mcomment_imageview= (ImageView) findViewById(R.id.comment_imageview);
        mgridview_image= (ImageView) findViewById(R.id.gridview_image);
        mfabiao_man= (TextView) findViewById(R.id.fabiao_man);
        mfabiao_time= (TextView) findViewById(R.id.fabiao_time);
        mbrowse= (TextView) findViewById(R.id.browse);
        mdynamic_content= (TextView) findViewById(R.id.dynamic_content);
        mZan= (LinearLayout) findViewById(R.id.zan);
        mComment= (LinearLayout) findViewById(R.id.comment);
        mForward= (LinearLayout) findViewById(R.id.forward);
        nineGridView= (NineGridView) findViewById(R.id.gridview_imageview);
        mListView= (ListView) findViewById(R.id.comment_listview);
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.zan:

                break;
            case R.id.comment:
                showCommentDialog();
                break;
            case R.id.forward:

                break;
        }
    }
    //显示出评论输入框
    private void showCommentDialog() {
        final EditText text=new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入要发表的内容：")
                .setView(text)
                .setPositiveButton("评论", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(text.getText().toString().equals("")){
                            Toast.makeText(ChatDynamicActivity.this, "评论信息不能为空", Toast.LENGTH_SHORT).show();
                        }else{
                            CommentDynamicData commentDynamicData=new CommentDynamicData(R.drawable.logo,"Rose",text.getText().toString(),"12:00");
                            mCommentDynamicData_list.add(commentDynamicData);

                            mCommentDynamicAdapter.notifyDataSetChanged();
                        }

                    }
                });

        builder.create().show();
    }
}
