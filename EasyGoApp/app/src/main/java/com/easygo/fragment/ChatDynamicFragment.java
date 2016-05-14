package com.easygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easygo.activity.R;


/**
 * Created by PengHong on 2016/4/29.
 */
public class ChatDynamicFragment extends Fragment {
    private Button mButton_chat,mButton_friend,mButton_dynamic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_dynamic,null);

    }



}
