package com.lee.parttime;

import java.util.HashMap;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderLayout;
import com.gitonway.androidimagesliderdemo.widget.imageslider.Animations.DescriptionAnimation;
import com.gitonway.androidimagesliderdemo.widget.imageslider.Indicators.PagerIndicator;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.BaseSliderView;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.TextSliderView;
import com.lee.common.util.IntentUtil;
import com.lee.common.util.PreferencesUtils;
import com.lee.parttime.job.PartTimeJobList;
import com.lee.parttime.profile.Login;
import com.lee.parttime.ui.BaseActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Build;

public class HomeActivity extends BaseActivity implements
BaseSliderView.OnSliderClickListener,AMapLocationListener{
	
	@ViewInject(R.id.city)
	TextView city;
	
	@ViewInject(R.id.tv_home)
	TextView tv_home;
	
	@ViewInject(R.id.tv_job)
	TextView tv_job;
	
	@ViewInject(R.id.tv_parttime)
	TextView tv_parttime;
	
	@ViewInject(R.id.tv_profile)
	TextView tv_profile;
	
	@ViewInject(R.id.slider)
	SliderLayout mDemoSlider;
	
	private LocationManagerProxy mAMapLocManager = null;
	private String cityName = "";
	private String cityCode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_mymainfunction_layout);
		ViewUtils.inject(this);
		
		Drawable topDrawable = getResources().getDrawable(R.drawable.tabbar_home_selected_selector);
		topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		tv_home.setCompoundDrawables(null, topDrawable, null, null);
		
		cityName = PreferencesUtils.getString(this, "cityname", "");
		if (TextUtils.isEmpty(cityName)) {
			initLocation();// 重新定位城市
		}else {
			city.setText(cityName); 
		}
		initAd();//加载广告	
		Log.e("test","loading...aaaa");
		//initMessage();//加载消息
		initRecommendList();//加载推荐列表
	}

	private void initRecommendList() {

	}
	
	@OnClick(R.id.tv_home)
	private void tv_home(View v){
		IntentUtil.start_activity(this, HomeActivity.class);
	}
	
	@OnClick(R.id.tv_job)
	private void tv_job(View v){
		IntentUtil.start_activity(this, PartTimeJobList.class);
	}
	
	@OnClick(R.id.tv_parttime)
	private void tv_parttime(View v){
		IntentUtil.start_activity(this, PartTimeJobList.class);
	}
	
	@OnClick(R.id.tv_profile)
	private void tv_profile(View v){
		IntentUtil.start_activity(this, Login.class);
	}

	private void initMessage() {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
		    "http://www.nantongsoft.com",
		    new RequestCallBack<String>(){
		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		           
		        }

		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
		        	//responseInfo.result;
		        }

		        @Override
		        public void onStart() {
		        }

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
	}

	
	private void initAd() {
		// 加载网络
		HashMap<String, String> file_maps = new HashMap<String, String>();
		file_maps.put("转发抵用券 分享很轻松", "http://61.155.81.202/zz/banner1.jpg");
		file_maps.put("每天几分钟 赚钱分分钟", "http://pic.58pic.com/58pic/14/94/17/96f58PICHhZ_1024.jpg");
		file_maps.put("收个好徒弟 逍遥快乐中", "http://61.155.81.202/zz/banner3.jpg");

		for (String name : file_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(this);
			// 初始化幻灯片页面
			textSliderView.description(name).image(file_maps.get(name))
					.setOnSliderClickListener(this);

			// 添加要传递的数据
			textSliderView.getBundle().putString("extra", name);

			mDemoSlider.addSlider(textSliderView);
		}

		// 幻灯片切换方式
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		// 指示符位置
		mDemoSlider
				.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		// 定义指示器样式
		// mDemoSlider.setCustomIndicator(your view);
		// 幻灯片循环
		// mDemoSlider.startAutoCycle();
		// 停止循环
		mDemoSlider.stopAutoCycle();
		// 设置指示器的显示与否
		mDemoSlider
				.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
		// 设置幻灯片的转化时间
		// mDemoSlider.setSliderTransformDuration(5000, null);
		// 用来自定义幻灯片标题的显示方式
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		// 幻灯片切换时间
		mDemoSlider.setDuration(3000);

	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
	}
	

	private void initLocation() {
		LogUtils.e("---------amap init-----");
		mAMapLocManager = LocationManagerProxy.getInstance(HomeActivity.this);
		// Location
		// API定位采用GPS定位方式，第一个参数是定位provider，第二个参数时间最短是5000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		mAMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork,
				5 * 1000, 15, this);
	}

	protected void onPause() {
		super.onPause();
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
		}
	}

	@Override
	public void onDestroy() {
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
			PreferencesUtils.putString(HomeActivity.this, "lat", geoLat + "");
			PreferencesUtils.putString(HomeActivity.this, "lon", geoLng + "");
			PreferencesUtils.putString(HomeActivity.this, "cityname", cityName);
			PreferencesUtils.putString(HomeActivity.this, "citycode", cityCode);
			city.setText(cityName);
			LogUtils.e(geoLat + "" + geoLng + "");
		}
	}
}
