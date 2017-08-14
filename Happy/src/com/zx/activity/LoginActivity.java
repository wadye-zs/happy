package com.zx.activity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.example.BaseApplication;
import com.zx.family.R;
import com.zx.util.HttpUtils;
import com.zx.util.InjectView;
import com.zx.util.Injector;
import com.zx.util.Utils;

public class LoginActivity extends Activity {
	@InjectView(R.id.iv_head_left)
	private ImageView head_left;
	@InjectView(R.id.linear_above_toHome)
	private LinearLayout above_toHome;

	private EditText nameText;
	private EditText pwdText;

	String APPKEY = "178e4c3c95ff3";
	String APPSECRETE = "d90c9abf0d40ff0a6ba6d1096673a89b";
	private ImageView pImage;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		Injector.get(this).inject();// init views
		nameText = (EditText) findViewById(R.id.login_name);
		pwdText = (EditText) findViewById(R.id.login_password);
		pImage=(ImageView) findViewById(R.id.iv_personal_icon);
		setListener();
		SMSSDK.initSDK(this, APPKEY, APPSECRETE);
	}

	private void setListener() {
		above_toHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	public void Forget(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		intent.putExtra("phone", "13632");
		startActivity(intent);
	}

	public void Login(View view) {
		String userName = nameText.getText().toString();
		String passWord = pwdText.getText().toString();
		if (userName.isEmpty()) {
			if (passWord.isEmpty()) {
				Toast.makeText(LoginActivity.this, "请输入用户名和密码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(LoginActivity.this, "请输入用户名！", Toast.LENGTH_SHORT)
					.show();
			return;
		} else if (passWord.isEmpty()) {
			//pImage.setImageResource(R.drawable.demo);
			Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		loginHandle(userName, passWord);
	}


	private String code;
	public void loginHandle(final String userName, final String passWord) {

		if (!Utils.isConnected(this)) {
			Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
			return;
		}

		Thread thread = new Thread() {


			public void run() {
				String log = HttpUtils
						.doGet("http://www.wadye.cn/prictice/login.php?user="
								+ userName + "&pass=" + passWord);
			    code =log.substring(10, 13);
				System.out.println(code);
			}
		};
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		if ("200".equals(code)) {
			BaseApplication.onLine= true;
			Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
			BaseApplication.id=userName;
			finish();
			return;
		}
		Toast.makeText(this, "用户名或密码错误 ", Toast.LENGTH_LONG).show();
	}
	//快捷注册
	public void QuickLogin(View view) {

		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		RegisterPage registerPage = new RegisterPage();
		registerPage.setRegisterCallback(new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				String country =null;
				String phone = null;
				if (result == SMSSDK.RESULT_COMPLETE) {
					HashMap<String, Object> maps = (HashMap<String, Object>) data;
					 country = (String) maps.get("country");
					 phone = (String) maps.get("phone");
					submitInfo(country, phone);
				}

			}
		});
		registerPage.show(this);

	}

	public void submitInfo(String country, String phone) {
		SMSSDK.submitUserInfo(null, null, null, country, phone);
		Intent intent = new Intent(this, RegisterActivity.class);
		intent.putExtra("phone", phone);
		startActivity(intent);
	}
}
