package com.easygo.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easygo.activity.CustomOrderActivity;
import com.easygo.activity.OwnerOrderActivity;
import com.easygo.activity.PublishDynamicActivity;
import com.easygo.activity.R;
import com.easygo.activity.ReleasesroomActivity;

public class MoreWindow extends PopupWindow implements OnClickListener {
	public static final String TYPE = "type";
	int type=0;
	private String TAG = MoreWindow.class.getSimpleName();
	Activity mContext;
	private int mWidth;
	private int mHeight;
	private int statusBarHeight ;
	private Bitmap mBitmap= null;
	private Bitmap overlay = null;
	/*private ImageView mMoreWindowLocal;*/
	View  view;
	SharedPreferences mSharedPreferences;

	private Handler mHandler = new Handler();

	public MoreWindow(Activity context) {
		mContext = context;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.activity_publish_dynamic, null);

		/*mMoreWindowLocal= (ImageView) view.findViewById(R.id.more_window_local);
		mMoreWindowLocal.setOnClickListener(itemsOnClick);*/
		/*//popwindow导入
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view=inflater.inflate(R.layout.activity_publish_dynamic, null);*/
	}

	public void init() {
		Rect frame = new Rect();
		mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		statusBarHeight = frame.top;
		DisplayMetrics metrics = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mHeight = metrics.heightPixels;

		setWidth(mWidth);
		setHeight(mHeight);


	}
	
	private Bitmap blur() {
		if (null != overlay) {
			return overlay;
		}
		long startMs = System.currentTimeMillis();

		View view = mContext.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		mBitmap = view.getDrawingCache();
		
		float scaleFactor = 8;
		float radius = 10;
		int width = mBitmap.getWidth();
		int height =  mBitmap.getHeight();

		overlay = Bitmap.createBitmap((int) (width / scaleFactor),(int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(mBitmap, 0, 0, paint);

		overlay = FastBlur.doBlur(overlay, (int) radius, true);
		Log.i(TAG, "blur time is:"+(System.currentTimeMillis() - startMs));
		return overlay;
	}
	
	//设置X按钮的属性
	public void showMoreWindow(View anchor, int bottomMargin) {
		final RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.center_music_more_window, null);
		setContentView(layout);
		
		ImageView close= (ImageView)layout.findViewById(R.id.center_music_window_close);
		//设置X号的宽高
		android.widget.RelativeLayout.LayoutParams params =new android.widget.RelativeLayout.LayoutParams(70, 70);
		params.bottomMargin = bottomMargin;
		//设置X号的位置
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);

		close.setLayoutParams(params);
		
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isShowing()) {
					closeAnimation(layout);
				}
			}

		});
		showAnimation(layout);
		setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
		setOutsideTouchable(true);
		setFocusable(true);
		showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
	}

	private void showAnimation(ViewGroup layout){
		for(int i=0;i<layout.getChildCount();i++){
			final View child = layout.getChildAt(i);
			if(child.getId() == R.id.center_music_window_close){
				continue;
			}
			child.setOnClickListener(this);
			child.setVisibility(View.INVISIBLE);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
					fadeAnim.setDuration(300);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(150);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
				}
			}, i * 50);
		}
		
	}

	private void closeAnimation(ViewGroup layout){
		for(int i=0;i<layout.getChildCount();i++){
			final View child = layout.getChildAt(i);
			if(child.getId() == R.id.center_music_window_close){
				continue;
			}
			child.setOnClickListener(this);
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
					fadeAnim.setDuration(200);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(100);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
					fadeAnim.addListener(new AnimatorListener() {
						
						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animator animation) {
							child.setVisibility(View.INVISIBLE);
						}
						
						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}, (layout.getChildCount()-i-1) * 30);
			
			if(child.getId() == R.id.more_window_local){
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						dismiss();
					}
				}, (layout.getChildCount()-i) * 30 + 80);
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_window_local:
			//发表动态
			publishdynamic();
			break;
		case R.id.more_window_online:
			//Log.e("你点击了发表房源","你点击了发表房源");
			publishedlistings();
			break;
		case R.id.more_window_delete:
			//Log.e("我的订单","我的订单");
			order();
			break;
		case R.id.more_window_collect:
			//Log.e("签到功能","签到功能");
			sign();
			break;
		case R.id.more_window_auto:
			//Log.e("客服中心","客服中心");
			Toast.makeText(mContext, "敬请期待", Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_window_external:
			//Log.e("更多","更多");
			Toast.makeText(mContext, "敬请期待", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	//签到
	private void sign() {
		mSharedPreferences = mContext.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
		type = mSharedPreferences.getInt("type", 0);
		if(type==0){
			Toast.makeText(mContext, "请先去登录", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(mContext, "今日签到：积分+10 ^_^", Toast.LENGTH_SHORT).show();
		}
	}

	//我的订单
	private void order() {
		mSharedPreferences = mContext.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
		type = mSharedPreferences.getInt("type", 0);
		if(type == 0){
			Toast.makeText(mContext, "请先去登录", Toast.LENGTH_SHORT).show();
		}else if(type==1){
			//房客
			Intent intent = new Intent(mContext, CustomOrderActivity.class);
			ActivityCompat.startActivity(mContext,intent,null);
		}else if(type==2){
			//房东
			Intent intent = new Intent(mContext, OwnerOrderActivity.class);
			ActivityCompat.startActivity(mContext,intent,null);
		}
	}

	//发布房源
	private void publishedlistings() {
		mSharedPreferences = mContext.getSharedPreferences(TYPE, Context.MODE_PRIVATE);
		type = mSharedPreferences.getInt("type", 0);
		if(type == 0){
			Toast.makeText(mContext, "请先去登录", Toast.LENGTH_SHORT).show();
		}else if(type==1){
			//房客
			Intent intent = new Intent(mContext, ReleasesroomActivity.class);
			ActivityCompat.startActivity(mContext,intent,null);
		}else if(type==2){
			//房东
			Toast.makeText(mContext, "您已经发布了房源", Toast.LENGTH_SHORT).show();

		}
	}

	//发表动态
	private void publishdynamic() {
		if(type==0){
			Toast.makeText(mContext, "请先去登录", Toast.LENGTH_SHORT).show();
		}else{
			//页面跳转
			Intent intent = new Intent(mContext, PublishDynamicActivity.class);
			ActivityCompat.startActivity(mContext,intent,null);
		}
	}

	public void destroy() {
		if (null != overlay) {
			overlay.recycle();
			overlay = null;
			System.gc();
		}
		if (null != mBitmap) {
			mBitmap.recycle();
			mBitmap = null;
			System.gc();
		}
	}

}
