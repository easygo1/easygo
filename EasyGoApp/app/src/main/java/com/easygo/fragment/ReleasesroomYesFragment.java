package com.easygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.easygo.activity.R;

/**
 * Created by 崔凯 on 2016/5/30.
 */
public class ReleasesroomYesFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout releaseroomRoompicYes;
    private Button releaseroomYesNumber;
    private Button releaseroomYesAdd;
    private Button releaseroomYesDel;
    private Button releaseroomYesFirst;
    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.releasesroom_fragment_yes,null);
        initViews();
        addListenner();
        return mView;
    }

    private void initViews() {
        //releaseroomRoompicYes = (RelativeLayout) mView.findViewById(R.id.releaseroom_roompic_yes);
        releaseroomYesNumber = (Button) mView.findViewById(R.id.releaseroom_yes_number);
        releaseroomYesAdd = (Button) mView.findViewById(R.id.releaseroom_yes_add);
        releaseroomYesDel = (Button) mView.findViewById(R.id.releaseroom_yes_del);
        releaseroomYesFirst = (Button) mView.findViewById(R.id.releaseroom_yes_first);
    }
    private void addListenner() {
        releaseroomYesAdd.setOnClickListener(this);
        releaseroomYesDel.setOnClickListener(this);
        releaseroomYesFirst.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.releaseroom_yes_add:
                break;
            case R.id.releaseroom_yes_del:
                break;
            case R.id.releaseroom_yes_first:
                break;
        }
    }
}
