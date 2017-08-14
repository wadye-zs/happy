package com.zx.activity;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.BaseApplication;
import com.zx.bean.Sellman;
import com.zx.family.R;
import com.zx.fragment.HomeFragment;
import com.zx.util.InjectView;
import com.zx.util.Injector;

@SuppressLint("SetJavaScriptEnabled")
public class MoreActivity extends Activity {

	@InjectView(R.id.iv_head_left)
	private ImageView head_left;
	@InjectView(R.id.linear_above_toHome)
	private LinearLayout above_toHome;
	BaseApplication baseApplication;
	private WebView webView;
	private Sellman man;
	private ImageButton ImageButton;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more);
		Injector.get(this).inject();// init views
		baseApplication = (BaseApplication) getApplicationContext();
		webView = (WebView) findViewById(R.id.webview_common);
		ImageButton=(ImageButton) findViewById(R.id.ImageButton2);
		button =(Button) findViewById(R.id.button1);
		Intent intent =this.getIntent();
		long id =intent.getLongExtra("id",0);
		//System.out.println(man.toString());
		if (id==0) {
			//ImageButton.setClickable(false);
			ImageButton.setVisibility(View.GONE);
			//button.setClickable(false);
			button.setVisibility(View.GONE);
			webView.loadUrl("http://www.wadye.cn:8080/LOVER/activity.html");
		}else {
			List<Sellman> list =HomeFragment.getmInfos();
			for (Sellman sellman : list) {
				if (sellman.getId()==id) {
					man=sellman;
				}
			}
			webView.loadUrl("http://www.wadye.cn/prictice/showInfo.php?s_id="+id);
		}
		WebSettings setting = webView.getSettings();  
		setting.setJavaScriptEnabled(true);//支持js  
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
			    view.loadUrl(url);
			    return super.shouldOverrideUrlLoading(view, url);
			    }
			});
		setListener();
	}

	private void setListener() {
		above_toHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

	public void Book(View view) {
		if (baseApplication.onLine ==false) {
			Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, CallActivity.class);
			intent.putExtra("address", man.getAddress());
			intent.putExtra("tel", man.getTel());
			intent.putExtra("name", man.getName());
			startActivity(intent);
		}
	}
	@SuppressWarnings("deprecation")
	public void navigation(View view){

		try {
			Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"+man.getLat()+","+man.getLog()+"|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if(isInstallByread("com.baidu.BaiduMap")){
				startActivity(intent); //启动调用
				Log.e("GasStation", "百度地图客户端已经安装") ;
			}else{
				 Toast.makeText(this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**  
	 * 判断是否安装目标应用  
	 * @param packageName 目标应用安装后的包名  
	 * @return 是否已安装目标应用  
	 */   
	 private boolean isInstallByread(String packageName) {   
	    return new File("/data/data/" + packageName).exists();   
	 }
}
