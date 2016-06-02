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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easygo.activity.R;
import com.easygo.utils.UpYunException;
import com.easygo.utils.UpYunUtils;
import com.easygo.utils.Uploader;
import com.easygo.view.MyPopupWindow;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.rong.imkit.utils.StringUtils;

/**
 * Created by 崔凯 on 2016/5/30.
 */
public class ReleasesroomYesFragment extends Fragment implements View.OnClickListener{
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private RelativeLayout releaseroomRoompicYes;
    private Button releaseroomYesNumber;
    private Button releaseroomYesAdd;
    private Button releaseroomYesDel;
    private Button releaseroomYesFirst;
    View mView;
    //第一步：数据源
    String[] arr;
    //第二步：确定viewpager布局，这里直接在主界面声明viewpager即可
    ViewPager mViewPager;
    List<ImageView> mViewPagerList;
    MyAdpter myAdpter;
    public List<String> mList = new ArrayList<>();
    int thisposition = 0;
    private MyPopupWindow popMenus;
    private static final String DEFAULT_DOMAIN = "easygo.b0.upaiyun.com";// 空间域名
    private static final String API_KEY = "3AtEDO2ByBUZ7qGVTLPUnuKLOWM="; // 表单api验证密钥
    private static final String BUCKET = "easygo";// 空间名称
    private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 1000 * 5 * 10; // 过期时间，必须大于当前时间

    private String uploadPath;
    private ArrayList<String> mResults = new ArrayList<>();
    private ArrayList<String> mUpResults = new ArrayList<>();
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
        mView = inflater.inflate(R.layout.releasesroom_fragment_yes,null);
        Bundle bundle = getArguments();
        arr = bundle.getStringArray("arr");
        //将数组转换为list
        List<String> list = Arrays.asList(arr);
        mList = new ArrayList<>(list);
        Toast.makeText(getActivity(), ""+mList.toString(), Toast.LENGTH_SHORT).show();
        Log.e("cuikai",mList.toString());
        initViews();
        initViewPage();
        addListenner();
        return mView;
    }
    /*public static ReleasesroomYesFragment newInstance(ArrayList<String> list){
        //将list变为数组
        int size = list.size();
        String [] arr = (String[]) list.toArray(new String[size]);
        ReleasesroomYesFragment fragment = new ReleasesroomYesFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("list",arr);
        fragment.setArguments(bundle);
        return fragment;
    }*/


    private void initViews() {
        //releaseroomRoompicYes = (RelativeLayout) mView.findViewById(R.id.releaseroom_roompic_yes);
        mViewPager = (ViewPager) mView.findViewById(R.id.releaseroom_viewpager);
        releaseroomYesNumber = (Button) mView.findViewById(R.id.releaseroom_yes_number);
        releaseroomYesAdd = (Button) mView.findViewById(R.id.releaseroom_yes_add);
        releaseroomYesDel = (Button) mView.findViewById(R.id.releaseroom_yes_del);
        releaseroomYesFirst = (Button) mView.findViewById(R.id.releaseroom_yes_first);
    }
    private class MyAdpter extends PagerAdapter{
        private List<ImageView> mAdapterList;
        public MyAdpter(List<ImageView> adapterList) {
            this.mAdapterList = adapterList;
        }

        @Override
        public int getCount() {
            return mViewPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        //滑动时销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.e("cuikaidestroy",position+"");
           // container.removeView(mViewPagerList.get(position));

        }

        /*@Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(mViewPagerList.get(position));
            Log.e("cuikaiinit",position+"");
            return mViewPagerList.get(position);
        }
    }

    private void initViewPage() {
        mViewPagerList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());

            Glide.with(getActivity()).load(mList.get(i)).into(imageView);
            //imageView.setBackgroundResource(advertImages1[i]);
            mViewPagerList.add(imageView);
        }
        myAdpter = new MyAdpter(mViewPagerList);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(myAdpter);
        //滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动时设置监听
                releaseroomYesNumber.setText((position+1)+"/"+mViewPagerList.size());
                if(position == 0){
                    releaseroomYesFirst.setVisibility(View.INVISIBLE);
                }else {
                    releaseroomYesFirst.setVisibility(View.VISIBLE);
                }
                thisposition = position;
            }

            @Override
            public void onPageSelected(int position) {
                //选择时
               // Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //更改滑动状态时
            }
        });
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
                if(mList.size() < 5){
                    popMenus=new MyPopupWindow(getActivity(),itemsOnClick) ;
                    popMenus.showAtLocation(getActivity().findViewById(R.id.releaseroom_addprice),
                            Gravity.BOTTOM, 0, 0);
                }else {
                    Toast.makeText(getActivity(), "最多添加5张图片", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.releaseroom_yes_del:
                //Toast.makeText(getActivity(), ""+thisposition, Toast.LENGTH_SHORT).show();
                Log.e("cuikaiposition",thisposition+"");
                mList.remove(thisposition);
              //  mUpResults.remove(thisposition);
                if (mList.size() < 1){
                    mFragmentManager = getFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    ReleasesroomNoFragment releasesroomNoFragment = new ReleasesroomNoFragment();
                    mFragmentTransaction.replace(R.id.releaseroom_fragment,releasesroomNoFragment);
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }else {
                    initViewPage();
                }
                break;
            case R.id.releaseroom_yes_first:
                Log.e("cuikai1111",mList.toString());
                String path = mList.get(0);
                mList.set(0,mList.get(thisposition));
                mList.set(thisposition,path);
                initViewPage();
                thisposition = 0;
                Log.e("cuikai222",mList.toString());
                break;
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
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5-mList.size());
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);
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
                Toast.makeText(getActivity(), "上传成功,访问地址为：" + result, Toast.LENGTH_SHORT).show();
                mUpResults.add(uploadPath);
            } else {
                Log.i("test", "上传图片失败");
                Toast.makeText(getActivity(), "上传图片失败", Toast.LENGTH_SHORT).show();
            }


            dialog.dismiss();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case TAKE_CAMERA:
                if (null != mCurrentPhotoFile) {
                    //new UploadTask().execute(mCurrentPhotoFile.getAbsolutePath());
                    mList.add(mCurrentPhotoFile.getAbsolutePath());
                    initViewPage();
                } else {
                    Toast.makeText(getActivity(), "照相失败，请重试！", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE:
                if(data != null){
                    mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                    Toast.makeText(getActivity(), mResults.size()+"", Toast.LENGTH_SHORT).show();
                    assert mResults !=null;
                    for (int i = 0;i < mResults.size();i ++){
                        //new UploadTask().execute(mResults.get(i));
                        //Toast.makeText(getActivity(), mResults.get(i), Toast.LENGTH_SHORT).show();
                        mList.add(mResults.get(i));
                    }
                    initViewPage();
                }

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
