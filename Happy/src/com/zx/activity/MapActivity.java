package com.zx.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.zx.bean.Sellman;
import com.zx.family.R;
import com.zx.util.MapOrientationListener;
import com.zx.util.MapOrientationListener.OnOrintationListener;

public class MapActivity extends Activity{
		MapView mMapView = null;
		// 定位相关
		private LocationClient mLocationClient;
		private MyLocationListener mLocationListener;
		private boolean isFirstIn = true;
		private BaiduMap mBaiduMap;
		private Context context;
		public BDLocation location;
		private MapOrientationListener myOrientationListener;
		private float mCurrenX;

		//覆盖物相关
		private BitmapDescriptor mMarker;
		private BitmapDescriptor mMarka;
		private List<Sellman> infos;
		
		public void onClick(View view) {

				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
						LocationMode.COMPASS, true, null));
		}


		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
			// 注意该方法要再setContentView方法之前实现
			SDKInitializer.initialize(getApplicationContext());
			setContentView(R.layout.activity_map);
			// 获取地图控件引用
			mMapView = (MapView) findViewById(R.id.id_bmapView);
			this.context = this;
			
			infos=(List<Sellman>) getIntent().getSerializableExtra("info");
			initView();
			initLocation();
			initMaker();
			//覆盖物适配器
			mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){

				@Override
				public boolean onMarkerClick(Marker marker) {
					
					Bundle bundle =marker.getExtraInfo();
					Sellman info = (Sellman) bundle.getSerializable("info");
					InfoWindow infoWindow;
					TextView tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.location);
					tv.setPadding(30, 20, 30, 20);
					tv.setText(info.getName());
					final LatLng latLng =marker.getPosition();
					//变更覆盖物
					OverlayOptions options;
					options =new MarkerOptions().position(latLng).icon(mMarka).zIndex(5);
					marker.remove();
					marker =(Marker) mBaiduMap.addOverlay(options);
					Bundle arg0 =new Bundle();
					arg0.putSerializable("info", info);
					marker.setExtraInfo(arg0);
					
					Point point =mBaiduMap.getProjection().toScreenLocation(latLng);
					point.y -=80;
					LatLng ll =mBaiduMap.getProjection().fromScreenLocation(point);
					infoWindow =new InfoWindow(tv, ll, new OnInfoWindowClickListener() {
						
						@Override
						public void onInfoWindowClick() {
							addOverlays(infos);
						}
					});
					mBaiduMap.showInfoWindow(infoWindow);
					return true;
				}
			});
		}
		private void addOverlays(List<Sellman> infos) {
			mBaiduMap.clear();
			LatLng laLng =null;
			Marker marker =null;
			OverlayOptions options;
			for (Sellman info : infos) {
				System.out.println(info.toString());
				//Toast.makeText(context, info.toString(), Toast.LENGTH_LONG).show();
				laLng =new LatLng(info.getLat(), info.getLog());
				options =new MarkerOptions().position(laLng).icon(mMarker).zIndex(5);
				marker =(Marker) mBaiduMap.addOverlay(options);
				Bundle arg0 =new Bundle();
				arg0.putSerializable("info", info);
				marker.setExtraInfo(arg0);
			}
			/*MapStatusUpdate msu =MapStatusUpdateFactory.newLatLng(laLng);
			mBaiduMap.setMapStatus(msu);*/
		}

		private void initMaker() {
			mMarker= BitmapDescriptorFactory.fromResource(R.drawable.maker);
			mMarka= BitmapDescriptorFactory.fromResource(R.drawable.marka);
			addOverlays(infos);
		}
		
		private void initView() {
			mMapView = (MapView) findViewById(R.id.id_bmapView);
			mBaiduMap = mMapView.getMap();
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
			mBaiduMap.setMapStatus(msu);
			// Toast.makeText(context, "initView", Toast.LENGTH_LONG).show();
		}
		//定位初始化
		private void initLocation() {
			mLocationClient = new LocationClient(this);
			mLocationListener = new MyLocationListener();
			mLocationClient.registerLocationListener(mLocationListener);
			LocationClientOption option = new LocationClientOption();
			option.setCoorType("bd09ll");
			option.setOpenGps(true);
			option.setScanSpan(1000);
			mLocationClient.setLocOption(option);
			myOrientationListener = new MapOrientationListener(context);
			myOrientationListener
					.setOnOrintationListener(new OnOrintationListener() {

						@Override
						public void onOrientationChanged(float x) {
							mCurrenX = x;
						}
					});
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
			// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
			mMapView.onDestroy();
		}

		@Override
		protected void onResume() {
			super.onResume();
			// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
			mMapView.onResume();
		}

		@Override
		// 开启定位与传感器
		protected void onStart() {
			super.onStart();
			mBaiduMap.setMyLocationEnabled(true);
			if (!mLocationClient.isStarted()) {
				mLocationClient.start();
				myOrientationListener.start();
			}
		}

		@Override
		// 停止定位与传感器
		protected void onStop() {
			super.onStop();
			mBaiduMap.setMyLocationEnabled(false);
			if (mLocationClient.isStarted()) {
				mLocationClient.stop();
				myOrientationListener.stop();
			}
		}

		@Override
		protected void onPause() {
			super.onPause();
			// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
			mMapView.onPause();
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		private class MyLocationListener implements BDLocationListener {

			@Override
			public void onReceiveLocation(BDLocation location) {
				MapActivity.this.location = location;
				MyLocationData data = new MyLocationData.Builder()
						.direction(mCurrenX).accuracy(location.getRadius())
						.latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();
				mBaiduMap.setMyLocationData(data);
				mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
						LocationMode.NORMAL, true, null));
				System.out.println(location.getLatitude()+"　"+location.getLongitude());
				if (isFirstIn) {
					LatLng latLng = new LatLng(location.getLatitude(),
							location.getLongitude());
					MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
					mBaiduMap.animateMapStatus(msu);
					isFirstIn = false;
					//获取地址
					GeoCoder coder = GeoCoder.newInstance(); 
			        ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
			        ReverseGeoCodeOption result = reverseCode.location(latLng);
			        coder.reverseGeoCode(result);
			        coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
			 
			            @Override
			            public void onGetReverseGeoCodeResult(
			                    ReverseGeoCodeResult result) {
			                Log.i("===",
			                        "onGetReverseGeoCodeResult:" + result.getAddress());
			                Toast.makeText(context, result.getAddress(), Toast.LENGTH_LONG).show();
			            }
			 
			            @Override
			            public void onGetGeoCodeResult(GeoCodeResult result) {
			                Log.i("===", "onGetGeoCodeResult:" + result.getAddress());
			                Toast.makeText(context, result.getAddress(), Toast.LENGTH_LONG).show();
			 
			            }
			        });
					//Toast.makeText(context, latLng.toString(), Toast.LENGTH_LONG).show();
				}
			}
			@Override
			public void onReceivePoi(BDLocation arg0) {

			}

		}
	
}
