package com.easygo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.easygo.activity.ChatDynamicActivity;
import com.easygo.activity.R;
import com.easygo.adapter.ChatDynamicAdpter;
import com.easygo.beans.chat_comment.CommentData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatDynamicFragment extends Fragment {
    //定义视图
    View mChatDynamicView;
    //定义适配器
    ChatDynamicAdpter mChatDynamicAdpter;

    List<CommentData> commentDataList;
    List<String> gridview_list;

    ListView commentListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChatDynamicView=inflater.inflate(R.layout.chat_dynamic,null);
        initView();
        initData();
        initAdapter();

        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里需要做好传值动作

                Toast.makeText(getActivity(), "这是第"+position+"个页面", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), ChatDynamicActivity.class);

                startActivity(intent);
            }
        });
        return  mChatDynamicView;
    }

    private void initAdapter() {
        mChatDynamicAdpter=new ChatDynamicAdpter(commentDataList,getActivity());
        commentListView.setAdapter(mChatDynamicAdpter);
    }

    private void initView() {
        //初始化所有控件
        commentListView= (ListView) mChatDynamicView.findViewById(R.id.dynamic_listview);

    }
    private void initData() {
        commentDataList=new ArrayList<CommentData>();

        gridview_list=new ArrayList<>();
        //添加图片
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");
        gridview_list.add("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg");

        //动态列表
        CommentData commentdata1=new CommentData(R.mipmap.ic_launcher,"rose","12:00","浏览：321次","今天天气真好",gridview_list);
        CommentData commentdata2=new CommentData(R.mipmap.ic_launcher,"jack","12:00","浏览：123次","昨天下雨了",gridview_list);
        CommentData commentdata3=new CommentData(R.mipmap.ic_launcher,"jarry","12:00","浏览：234次","天气终于晴了",gridview_list);
        CommentData commentdata4=new CommentData(R.mipmap.ic_launcher,"tom","12:00","浏览：23次","好饿啊",gridview_list);
        CommentData commentdata5=new CommentData(R.mipmap.ic_launcher,"苍天在上","12:00","浏览：4123次","hello world",gridview_list);

        //添加数据
        commentDataList.add(commentdata1);
        commentDataList.add(commentdata2);
        commentDataList.add(commentdata3);
        commentDataList.add(commentdata4);
        commentDataList.add(commentdata5);

    }
}
