package com.example;

import android.app.Application;
import android.content.res.Configuration;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zx.fragment.HomeFragment;

public class BaseApplication extends Application {
	private String jumpType = "";
	public static BaseApplication instance;
	//在线？？
	public static boolean onLine = false;
	public static String id = null;
	

	public static BDLocation location;
	private LocationClient mLocationClient;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initLocation();
		System.out.println("定位成功");
		//ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	// 定位初始化
	private void initLocation() {
		mLocationClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setOpenGps(true);
		option.setScanSpan(1000 * 10);
		mLocationClient.setLocOption(option);

		mLocationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					//System.out.println("空了");
					return;
				}
				HomeFragment.lat = location.getLatitude();
				HomeFragment.lon = location.getLongitude();

				// System.out.println(location.getLatitude()+" "+location.getLongitude());
			}

			@Override
			public void onReceivePoi(BDLocation location) {

			}
		});
		mLocationClient.start();
	}

}
