package com.easygo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.easygo.utils.UpYunException;
import com.easygo.utils.UpYunUtils;
import com.easygo.utils.Uploader;
import com.easygo.view.MyPopupWindow;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 崔凯 on 2016/5/30.
 */
public class ReleasesroomNoFragment extends Fragment{
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private RelativeLayout releaseroomRoompicNo;
    private ImageView releaseroomCamera;
    View mView;
    private MyPopupWindow popMenus;
    private static final String DEFAULT_DOMAIN = "easygo.b0.upaiyun.com";// 空间域名
    private static final String API_KEY = "3AtEDO2ByBUZ7qGVTLPUnuKLOWM="; // 表单api验证密钥
    private static final String BUCKET = "easygo";// 空间名称
    private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 1000 * 5 * 10; // 过期时间，必须大于当前时间

    private String uploadPath;
    private ArrayList<String> mResults = new ArrayList<>();
    ArrayList<String> mUpResults = new ArrayList<>();
    private File mCurrentPhotoFile;
    private static final int TAKE_CAMERA = 0;
    private static final int REQUEST_CODE = 732;
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            popMenus.dismiss();
            switch (v.getId()) {
                //点击了拍照
                case R.id.btn_take_photo:
                    takeCamera();
                    break;
                //点击了从相册选择
                case R.id.btn_pick_photo:
                    takePhoto();
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
        mFragmentManager = getFragmentManager();
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
    public class UploadTask extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("正在上传中,请稍等...");
            dialog.show();
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(String... params) {
            if (TextUtils.isEmpty(params[0])) {
                return "";
            }

            String string = null;
            Log.i("test", "文件大小：" + new File(params[0]).length() / (1024 * 1024f) + "M");

            try {
                // 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
                String SAVE_KEY = File.separator + "house" + File.separator + System.currentTimeMillis() + ".jpg";

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

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            uploadPath = result;
            if (result != null) {
                Log.i("test", "上传成功,访问地址为：" + result);
                //Toast.makeText(getActivity(), "上传成功,访问地址为：" + result, Toast.LENGTH_SHORT).show();
                mUpResults.add(uploadPath);
            } else {
                Log.i("test", "上传图片失败");
                Toast.makeText(getActivity(), "上传图片失败", Toast.LENGTH_SHORT).show();
            }


            dialog.dismiss();
        }

    }
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
    private void takePhoto() {
        // start multiple photos selector
        Intent intent = new Intent(getActivity(),ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case TAKE_CAMERA:
                if (null != mCurrentPhotoFile) {
                  //  new UploadTask().execute(mCurrentPhotoFile.getAbsolutePath());
                } else {
                    Toast.makeText(getActivity(), "照相失败，请重试！", Toast.LENGTH_SHORT).show();
                }
                mFragmentTransaction = mFragmentManager.beginTransaction();
                ReleasesroomYesFragment releasesroomYesFragment = new ReleasesroomYesFragment();
                String[] arr1 = new String[] {mCurrentPhotoFile.getAbsolutePath()};
                Bundle bundle = new Bundle();
                bundle.putStringArray("arr",arr1);
                releasesroomYesFragment.setArguments(bundle);
                mFragmentTransaction.replace(R.id.releaseroom_fragment,releasesroomYesFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case REQUEST_CODE:
                if(data != null){
                    mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                    Toast.makeText(getActivity(), mResults.size()+"", Toast.LENGTH_SHORT).show();
                    assert mResults !=null;
                    /*for (int i = 0;i < mResults.size();i ++){
                        new UploadTask().execute(mResults.get(i));
                        Toast.makeText(getActivity(), mResults.get(i), Toast.LENGTH_SHORT).show();
                    }*/
                }
                mFragmentTransaction = mFragmentManager.beginTransaction();
                int size = mResults.size();
                String [] arr = (String[]) mResults.toArray(new String[size]);
                ReleasesroomYesFragment releasesroomYesFragment1 = new ReleasesroomYesFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putStringArray("arr",arr);
                releasesroomYesFragment1.setArguments(bundle1);
                mFragmentTransaction.replace(R.id.releaseroom_fragment,releasesroomYesFragment1);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            default:
                break;
        }
        /*ReleasesroomYesFragment releasesroomYesFragment = ReleasesroomYesFragment.newInstance(mResults);
        Intent intent = new Intent();
        intent.putExtra("flag","yes");
        intent.setClass(getActivity(),ReleasesroomActivity.class);
        startActivity(intent);*/

        super.onActivityResult(requestCode, resultCode, data);
    }
}
