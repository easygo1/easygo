package com.easygo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easygo.activity.HouseDetailActivity;
import com.easygo.activity.R;
import com.easygo.beans.user.User;

/**
 * Created by zzia on 2016/5/20.
 */
public class HouseDetailOwnerFragment extends Fragment {
    private ImageView houseDetailHeadPhoto;
    private TextView houseDetailOwnerName, houseDetailOwnerAge, houseDetailOwnerSex, houseDetailOwnerCity, houseDetailOwnerIntroduct;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_owner_scroll, null);
        initView();
        initData();
        return mView;
    }

        //头像，爱好还未获取过来
    private void initView() {
//        houseDetailHeadPhoto = (ImageView) mView.findViewById(R.id.house_detail_head_photo);
        houseDetailOwnerName = (TextView) mView.findViewById(R.id.house_detail_owner_name);
//        houseDetailOwnerAge = (TextView) mView.findViewById(R.id.house_detail_owner_age);
        houseDetailOwnerSex = (TextView) mView.findViewById(R.id.house_detail_owner_sex);
        houseDetailOwnerCity = (TextView) mView.findViewById(R.id.house_detail_owner_city);
        houseDetailOwnerIntroduct = (TextView) mView.findViewById(R.id.house_detail_owner_introduct);
    }

    /*public void initData(User user) {
        //传值过来，控件都还未初始化，所以会报空指针（不可行）
        houseDetailOwnerName.setText(user.getUser_realname());
//        houseDetailOwnerAge.setText(user());
        houseDetailOwnerSex.setText(user.getUser_sex());
        houseDetailOwnerCity.setText(user.getUser_address_city());
        houseDetailOwnerIntroduct.setText(user.getUser_introduct());
    }*/
    public void initData() {
        HouseDetailActivity houseDetailActivity = (HouseDetailActivity) getActivity();
        User user = houseDetailActivity.mUser;
        houseDetailOwnerName.setText(user.getUser_realname());
//        houseDetailOwnerAge.setText(user());
        houseDetailOwnerSex.setText(user.getUser_sex());
        houseDetailOwnerCity.setText(user.getUser_address_city());
        houseDetailOwnerIntroduct.setText(user.getUser_introduct());
    }
}
