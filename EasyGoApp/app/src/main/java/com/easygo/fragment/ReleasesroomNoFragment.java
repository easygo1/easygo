package com.easygo.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easygo.activity.R;
import com.easygo.activity.ReleasesroomActivity;
import com.easygo.view.MyPopupWindow;

import java.io.File;

/**
 * Created by 崔凯 on 2016/5/30.
 */
public class ReleasesroomNoFragment extends Fragment{
    private RelativeLayout releaseroomRoompicNo;
    private ImageView releaseroomCamera;
    View mView;
    private MyPopupWindow popMenus;
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            popMenus.dismiss();
            switch (v.getId()) {
                //点击了拍照
                case R.id.btn_take_photo:
                    Toast.makeText(getActivity(), "点击拍照", Toast.LENGTH_SHORT).show();
                    break;
                //点击了从相册选择
                case R.id.btn_pick_photo:
                    Toast.makeText(getActivity(), "点击了相册选择", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.releasesroom_fragment_no,null);
        releaseroomRoompicNo = (RelativeLayout) mView.findViewById(R.id.releaseroom_roompic_no);
        releaseroomCamera = (ImageView) mView.findViewById(R.id.releaseroom_camera);
        releaseroomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "00000", Toast.LENGTH_SHORT).show();
               // ReleasesroomActivity releasesroomActivity = (ReleasesroomActivity) getActivity();
               // releasesroomActivity.popView();
                popMenus=new MyPopupWindow(getActivity(),itemsOnClick) ;
                popMenus.showAtLocation(getActivity().findViewById(R.id.releaseroom_camera),
                        Gravity.BOTTOM, 0, 0);
            }
        });
        return mView;
    }
    /*public class UploadTask extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("正在上传中,请稍等...");
            dialog.show();
        }

        *//*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         *//*
        @Override
        protected String doInBackground(String... params) {
            if (TextUtils.isEmpty(params[0])) {
                return "";
            }

            String string = null;
            Log.i("test", "文件大小：" + new File(params[0]).length() / (1024 * 1024f) + "M");

            try {
                // 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
                String SAVE_KEY = File.separator + "test" + File.separator + System.currentTimeMillis() + ".jpg";

                // 取得base64编码后的policy
                String policy = UpYunUtils.makePolicy(SAVE_KEY, EXPIRATION, BUCKET);

                // 根据表单api签名密钥对policy进行签名
                // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                String signature = UpYunUtils.signature(policy + "&" + API_KEY);

                long startTime = System.currentTimeMillis();
                Log.i("test", "开始上传：" + startTime);
                // 上传文件到对应的bucket中去。
                string = DEFAULT_DOMAIN + Uploader.upload(policy, signature, BUCKET, params[0]);

                Log.i("test", "上传完成：" + (System.currentTimeMillis() - startTime) + "ms");

            } catch (UpYunException e) {
                e.printStackTrace();
                Log.i("test", "异常了..." + e.getMessage());
            }

            return string;
        }

        *//*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         *//*
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            uploadPath = result;
            if (result != null) {
                Log.i("test", "上传成功,访问地址为：" + result);
                Toast.makeText(TestActivity.this, "上传成功,访问地址为：" + result, Toast.LENGTH_SHORT).show();
                //showphoto();
                dedaoph();
            } else {
                Log.i("test", "上传图片失败");
                Toast.makeText(TestActivity.this, "上传图片失败", Toast.LENGTH_SHORT).show();
            }


            dialog.dismiss();
        }

    }*/
}
