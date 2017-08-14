package com.zx.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import com.example.BaseApplication;
import com.zx.activity.CommentActivity;
import com.zx.bean.Sellman;
import com.zx.family.R;
import com.zx.image.MemoryCacheUtils;
import com.zx.image.NetCacheUtils;
import com.zx.util.Utils;
import com.zx.util.XmlParser;

@SuppressLint("NewApi")
public class CollectFragment extends Fragment implements OnClickListener {
	private View currentView;
	private LinearLayout openMenu;
	public static List<Sellman> mInfos = null;
	MemoryCacheUtils mMemoryCacheUtils = new MemoryCacheUtils();
	private ListView listView;

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInfos = new ArrayList<Sellman>();
		getData();
		if (mInfos.isEmpty()) {
			currentView = inflater.inflate(R.layout.no_sellman, container,
					false);
		} else {
			currentView = inflater.inflate(R.layout.home, container, false);
			listView = (ListView) currentView.findViewById(R.id.listView);
			// 处理Item的点击事件
			listView.setAdapter(new ListViewAdapter(mInfos));
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Sellman man = null;
					for (Sellman sellman : mInfos) {
						if (sellman.getId() == id) {
							man = sellman;
						}
					}
					//System.out.println(man.getComment_id());
					Intent intent = new Intent(getActivity(),
							CommentActivity.class);
					intent.putExtra("id", "" + id);
					System.out.println(id);
					intent.putExtra("tel", man.getTel());
					startActivity(intent);
				}

			});
		}
		openMenu = (LinearLayout) currentView
				.findViewById(R.id.linear_above_toHome);
		openMenu.setOnClickListener(this);
		return currentView;
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

	private void getData() {
		if (!Utils.isConnected(getActivity())) {
			Toast.makeText(getActivity(), "网络未连接", Toast.LENGTH_SHORT).show();
			return;
		}

		Thread thread = new Thread() {

			public void run() {
				mInfos = XmlParser
						.getOrderXml("http://www.wadye.cn/prictice/order_list.php?format=xml&c_tel="
								+ BaseApplication.id);
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
						getInfo.getKeyword(), getInfo.getAddress(),getInfo.getRatnum(),
						getInfo.getPhoto(), getInfo.getLat(), getInfo.getLog());
				itemViews[i].setId(getInfo.getId());
			}
			listView.addFooterView(new View(getActivity()));
		}

		private View makeItemView(String sname, String keyword, String address,float num,
				String url, double slat, double slog) {

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
			power.setText("时间：" + keyword);
			TextView speed = (TextView) itemView.findViewById(R.id.tv_long);
			speed.setText("人数：" + address);
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
