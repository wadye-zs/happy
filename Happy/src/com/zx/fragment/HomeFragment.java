﻿package com.zx.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.activity.MoreActivity;
import com.zx.bean.Sellman;
import com.zx.family.R;
import com.zx.image.MemoryCacheUtils;
import com.zx.image.NetCacheUtils;
import com.zx.util.Utils;
import com.zx.util.XmlParser;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnClickListener {
	private View currentView;
	private LinearLayout openMenu;
	private ListView listView;
	private View listHeaderView;
	private ViewPager viewPager;
	public static double lat;
	public static double lon;
	public static List<Sellman> mInfos = new ArrayList<Sellman>();
	MemoryCacheUtils mMemoryCacheUtils = new MemoryCacheUtils();


	public static List<Sellman> getmInfos() {
		return mInfos;
	}

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentView = inflater.inflate(R.layout.home, container, false);
		openMenu = (LinearLayout) currentView
				.findViewById(R.id.linear_above_toHome);
		openMenu.setOnClickListener(this);
		getData();
		
		listView = (ListView) currentView.findViewById(R.id.listView);
		listView.setAdapter(new ListViewAdapter(mInfos));
		listHeaderView = getActivity().getLayoutInflater().inflate(
				R.layout.head_picture, null);
		// 活动页面跳转
		listHeaderView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), MoreActivity.class);
				intent.putExtra("id", 0);
				startActivity(intent);
			}
		});
		viewPager = (ViewPager) listHeaderView.findViewById(R.id.head_pic);
		viewPager.setBackgroundResource(R.drawable.home1);
		/*
		 * // 图片滑动 ViewGroup group = (ViewGroup) listHeaderView
		 * .findViewById(R.id.viewGroup); imgIdArray = new int[] {
		 * R.drawable.home1, R.drawable.home2, R.drawable.home3 }; //
		 * 将点点加入到ViewGroup中 tips = new ImageView[imgIdArray.length]; for (int i
		 * = 0; i < tips.length; i++) { ImageView imageView = new
		 * ImageView(getActivity()); imageView.setLayoutParams(new
		 * LayoutParams(20,20)); tips[i] = imageView; if (i == 0) {
		 * tips[i].setBackgroundResource(R.drawable.page_indicator_focused); }
		 * else {
		 * tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused); }
		 * 
		 * LinearLayout.LayoutParams layoutParams = new
		 * LinearLayout.LayoutParams( new
		 * ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT)); layoutParams.leftMargin = 1;
		 * layoutParams.rightMargin = 1; group.addView(imageView, layoutParams);
		 * } mImageViews = new ImageView[imgIdArray.length]; for (int i = 0; i <
		 * mImageViews.length; i++) { ImageView imageView = new
		 * ImageView(getActivity()); mImageViews[i] = imageView;
		 * imageView.setBackgroundResource(imgIdArray[i]); } MyAdapter myAdapter
		 * = new MyAdapter(mImageViews, tips); // 设置Adapter
		 * viewPager.setAdapter(myAdapter); // 设置监听，主要是设置点点的背景
		 * viewPager.setOnPageChangeListener(myAdapter); // 设置ViewPager的默认项,
		 * 设置为长度的100倍，这样子开始就能往左滑动 viewPager.setCurrentItem((mImageViews.length)
		 * * 100);
		 */
		listView.addHeaderView(listHeaderView);

		// 处理Item的点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getActivity(),
				// position,Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), MoreActivity.class);
				System.out.println(id);
				intent.putExtra("id", id);
				// Toast.makeText(getActivity(), ""+position, 1).show();
				startActivity(intent);
			}

		});
		return currentView;
	}

	private void getData() {
		if (!Utils.isConnected(getActivity())) {
			Toast.makeText(getActivity(), "网络未连接", Toast.LENGTH_SHORT).show();
			return;
		}

		Thread thread = new Thread() {

			public void run() {
				mInfos = XmlParser.getManXml("http://www.wadye.cn/prictice/list.php?format=xml");
			}
		};
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.linear_above_toHome):
			openMenu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) getActivity()
							.findViewById(R.id.slidingpanellayout);
					if (slidingPaneLayout.isOpen()) {
						slidingPaneLayout.closePane();
					} else {
						slidingPaneLayout.openPane();
					}
				}
			});

			break;
		}
	}

	public class ListViewAdapter extends BaseAdapter {
		View[] itemViews;

		public ListViewAdapter(List<Sellman> mlistInfo) {
			// TODO Auto-generated constructor stub
			itemViews = new View[mlistInfo.size()];
			for (int i = 0; i < mlistInfo.size(); i++) {
				Sellman getInfo = (Sellman) mlistInfo.get(i); // 获取第i个对象
				// 调用makeItemView，实例化一个Item
				// System.out.println(getInfo.toString());
				itemViews[i] = makeItemView(getInfo.getName(),
						getInfo.getKeyword(), getInfo.getPhoto(),getInfo.getRatnum(),
						getInfo.getLat(), getInfo.getLog());
				itemViews[i].setId(getInfo.getId());
				//System.out.println(getInfo.getId());
			}
			listView.addFooterView(new View(getActivity()));
		}

		private View makeItemView(String sname, String keyword, String url,float num,
				double slat, double slog) {

			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 使用View的对象itemView与item关联
			View itemView = inflater.inflate(R.layout.smanshop, null);

			// 通过findViewById()方法实例item内各组件
			ImageView logView = (ImageView) itemView
					.findViewById(R.id.log_image_view);
			// logView.setImageResource();
			synchronized (logView) {
				// 检查缓存中是否已有
				Bitmap bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
				if (bitmap != null) {
					// 如果有了就从缓存中取出显示
					logView.setImageBitmap(bitmap);
				} else {
					// 软应用缓存中不存在，磁盘中也不存在，只能下载
					// 用异步任务去下载
					new NetCacheUtils(mMemoryCacheUtils).getBitmapFromNet(
							logView, url);
				}
			}
			TextView name = (TextView) itemView.findViewById(R.id.tv_name);
			name.setText(sname); // 填入相应的值
			// System.out.println(sname);
			TextView power = (TextView) itemView.findViewById(R.id.tv_keyword);
			power.setText(keyword);
			TextView speed = (TextView) itemView.findViewById(R.id.tv_long);
			TextView startnum =(TextView) itemView.findViewById(R.id.tv_startnum);
			DecimalFormat df=new DecimalFormat(".#");
			num =Float.parseFloat(df.format(num));
			startnum.setText(""+num);
			ImageView imageView =(ImageView) itemView.findViewById(R.id.tv_start);
			
			if (num<2) {
				imageView.setImageResource(R.drawable.star10);
			}else if (num<3) {
				imageView.setImageResource(R.drawable.star20);
			}else if (num<3.5) {
				imageView.setImageResource(R.drawable.star30);
			}else if (num<4) {
				imageView.setImageResource(R.drawable.star35);
			}else if (num<4.5) {
				imageView.setImageResource(R.drawable.star40);
			}else if (num<5) {
				imageView.setImageResource(R.drawable.star45);
			}else {
				imageView.setImageResource(R.drawable.star50);
			}
			if (lat ==0&&lon ==0) {
				speed.setText("距离  0");
			}else {			
				 df=new DecimalFormat(".##");
				speed.setText("距离  "
						+ df.format((Double)(Math.sqrt(Math.pow((lat - slat)*1000, 2)
								+ Math.pow((lon - slog)*1000, 2)))/10)+"km");
				System.out.println(lat+" "+slat+" "+lon+" "+slog);
			}
			return itemView; 
		}

		@Override
		public int getCount() {
			return itemViews.length;
		}

		@Override
		public Object getItem(int position) {
			return itemViews[position];
		}

		@Override
		public long getItemId(int position) {
			return itemViews[position].getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				return itemViews[position];
			else {
				convertView = itemViews[position];
			}
			return convertView;
		}
	}


}
