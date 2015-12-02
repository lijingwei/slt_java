package com.lee.parttime;

import java.util.LinkedHashSet;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.lee.common.util.PreferencesUtils;
import com.lee.parttime.ui.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

public class SplashActivity extends BaseActivity implements
		AMapLocationListener,TagAliasCallback {

	private LocationManagerProxy mAMapLocManager = null;
	private String cityName = "";
	private String cityCode = "";
	public static boolean isForeground = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		ViewUtils.inject(this);
		 new Handler().postDelayed(new Runnable() {
				public void run() {
					Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
					SplashActivity.this.startActivity(mainIntent);
					SplashActivity.this.finish();
				}
			}, 3000);
		 registerMessageReceiver(); 
		initLocation();// 定位城市
		initJpush();// 接受推送信息
	}

	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void initJpush() {
		JPushInterface.init(getApplicationContext());
	}
	
	@Override
	protected void onResume() {
		isForeground = true;
		JPushInterface.onResume(SplashActivity.this);
		super.onResume();
	}
	
	// 注册JPUSH接收服务
		private MessageReceiver mMessageReceiver;
		public static final String MESSAGE_RECEIVED_ACTION = "com.lee.parttime.MESSAGE_RECEIVED_ACTION";
		public static final String KEY_TITLE = "title";
		public static final String KEY_MESSAGE = "message";
		public static final String KEY_EXTRAS = "extras";

		public void registerMessageReceiver() {
			mMessageReceiver = new MessageReceiver();
			IntentFilter filter = new IntentFilter();
			filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
			filter.addAction(MESSAGE_RECEIVED_ACTION);
			registerReceiver(mMessageReceiver, filter);
			Log.e("Splash", "registerReceiver");
		}

		public class MessageReceiver extends BroadcastReceiver {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
					String messge = intent.getStringExtra(KEY_MESSAGE);
					String extras = intent.getStringExtra(KEY_EXTRAS);
					StringBuilder showMsg = new StringBuilder();
					showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
					if (!TextUtils.isEmpty(extras)) {
						showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
					}
					Log.e("--jpush setCostomMsg--", showMsg.toString());
				}
			}
		}

		/**
		 * 设置TAG
		 */
		private void setTagAlias() {
			String alias = "0";
			String tag = "android";
			
			alias = PreferencesUtils.getString(SplashActivity.this, "usersID","0");

			if (TextUtils.isEmpty(tag)) {
				return;
			}
			// ","隔开的多个 转换成 Set
			String[] sArray = tag.split(",");
			Set<String> tagSet = new LinkedHashSet<String>();
			for (String sTagItme : sArray) {
				if (sTagItme.length() > 0) {
					tagSet.add(sTagItme);
				}
			} // 调用JPush API设置Tag
			JPushInterface.setAliasAndTags(SplashActivity.this, alias, tagSet, this);
		}


	private void initLocation() {
		LogUtils.e("---------amap init-----");
		mAMapLocManager = LocationManagerProxy.getInstance(SplashActivity.this);
		// Location
		// API定位采用GPS定位方式，第一个参数是定位provider，第二个参数时间最短是5000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		mAMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork,
				5 * 1000, 15, this);
	}

	protected void onPause() {
		super.onPause();
		isForeground = false;
		JPushInterface.onPause(SplashActivity.this);
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
		}
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
		super.onDestroy();
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/**
	 * 混合定位回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			cityName = location.getCity();
			cityCode = location.getCityCode();
			PreferencesUtils.putString(SplashActivity.this, "lat", geoLat + "");
			PreferencesUtils.putString(SplashActivity.this, "lon", geoLng + "");
			PreferencesUtils.putString(SplashActivity.this, "cityname", cityName);
			PreferencesUtils.putString(SplashActivity.this, "citycode", cityCode);
			
			LogUtils.e(geoLat + "" + geoLng + "");
		}
	}

	@Override
	public void gotResult(int code, String alias, Set<String> tags) {
		String logs;
		switch (code) {
		case 0:
			logs = "Set tag and alias success, alias = " + alias + "; tags = "
					+ tags;
			Log.i("--jpush--", logs);
			break;

		default:
			logs = "Failed with errorCode = " + code + " alias = " + alias
					+ "; tags = " + tags;
			Log.e("--jpush--", logs);
		}

	}


}
