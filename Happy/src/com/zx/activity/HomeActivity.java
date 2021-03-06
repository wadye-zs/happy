package com.zx.activity;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.baidu.mapapi.SDKInitializer;
import com.zx.family.R;
import com.zx.fragment.HomeFragment;
import com.zx.fragment.MenuFragment;
import com.zx.util.InjectView;
import com.zx.util.Injector;

public class HomeActivity extends Activity {
	@InjectView(R.id.slidingpanellayout)
	private SlidingPaneLayout slidingPaneLayout;
	private MenuFragment menuFragment;
	private HomeFragment homeFragment;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	Context context = this;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(R.layout.slidingpane_main);
		Injector.get(this).inject();// init views
		menuFragment = new MenuFragment();
		homeFragment = new HomeFragment();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingpane_menu, menuFragment);
		transaction.replace(R.id.slidingpane_content, homeFragment);
		transaction.commit();
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {

			}

			@Override
			public void onPanelOpened(View panel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelClosed(View panel) {

			}
		});

	}

	public SlidingPaneLayout getSlidingPaneLayout() {
		return slidingPaneLayout;
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setIcon(R.drawable.warning);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				System.exit(0);
			}
		});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	public void MapView(View view) {
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra("info",(Serializable)HomeFragment.getmInfos());
		startActivity(intent);
	}

	private Context getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
