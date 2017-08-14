﻿package com.zx.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BaseApplication;
import com.zx.activity.HomeActivity;
import com.zx.activity.LoginActivity;
import com.zx.family.R;
import com.zx.util.Tools;

@SuppressLint("NewApi")
public class MenuFragment extends Fragment implements View.OnClickListener {

	private View currentView;
	private ImageView iv_login;
	BaseApplication baseApplication;
	private Button bt_abouts, bt_home, bt_comment, bt_collect, bt_reComment;
	private TextView loginView;

	public View getCurrentView() {
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.slidingpane_menu, container,
				false);
		bt_abouts = (Button) currentView.findViewById(R.id.btn_abouts);
		bt_home = (Button) currentView.findViewById(R.id.btn_home);
		bt_comment = (Button) currentView.findViewById(R.id.btn_comment);
		iv_login = (ImageView) currentView.findViewById(R.id.iv_login);
		bt_collect = (Button) currentView.findViewById(R.id.return_comment);
		bt_reComment = (Button) currentView.findViewById(R.id.btn_collect);
		bt_abouts.setOnClickListener(this);
		bt_home.setOnClickListener(this);
		bt_collect.setOnClickListener(this);
		bt_comment.setOnClickListener(this);
		iv_login.setOnClickListener(this);
		bt_reComment.setOnClickListener(this);
		baseApplication = (BaseApplication) getActivity()
				.getApplicationContext();
		return currentView;
	}

	@SuppressLint("CommitTransaction")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		FragmentTransaction ft = getFragmentManager().beginTransaction();// 开始一个事物
		switch (v.getId()) {
		case R.id.iv_login:
			if (baseApplication.onLine == false) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "已登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_home:
			Fragment homeFragment = new HomeFragment();
			ft.replace(R.id.slidingpane_content, homeFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_collect:
			Fragment colletFragment = new CollectFragment();
			ft.replace(R.id.slidingpane_content, colletFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_comment:
			Fragment commentFragment = new AllorderFragment();
			ft.replace(R.id.slidingpane_content, commentFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.return_comment:
			Fragment recomFragment = new ReCommentFragment();
			ft.replace(R.id.slidingpane_content, recomFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		case R.id.btn_abouts:
			Fragment aboutsFragment = new UpdateFragment();
			ft.replace(R.id.slidingpane_content, aboutsFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			break;
		}
		((HomeActivity) getActivity()).getSlidingPaneLayout().closePane();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loginView = (TextView) currentView.findViewById(R.id.isLogin);

		initRoundImage();
	}

	@SuppressWarnings("deprecation")
	private void initRoundImage() {
		if (BaseApplication.onLine == true) {
			loginView.setText("");
			Tools tools = new Tools();
			iv_login.setBackgroundDrawable(new BitmapDrawable(tools
					.toRoundBitmap(getActivity(), "ioc.png")));
			iv_login.getBackground().setAlpha(0);
			iv_login.setImageBitmap(tools.toRoundBitmap(getActivity(),
					"ioc.png"));

		} else {

			Tools tools = new Tools();
			iv_login.setBackgroundDrawable(new BitmapDrawable(tools
					.toRoundBitmap(getActivity(), "ali_head.jpg")));
			iv_login.getBackground().setAlpha(0);
			iv_login.setImageBitmap(tools.toRoundBitmap(getActivity(),
					"ali_head.jpg"));
		}
	}
}
