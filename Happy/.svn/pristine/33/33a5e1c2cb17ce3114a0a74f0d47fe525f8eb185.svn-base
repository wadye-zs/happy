package com.zx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zx.bean.Code;
import com.zx.family.R;
import com.zx.util.HttpUtils;

public class RegisterActivity extends Activity {

	private EditText fPwd;
	private EditText sPwd;
	private EditText fname;
	private Code code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_pwd);
		fname = (EditText) findViewById(R.id.f_name);
		fPwd = (EditText) findViewById(R.id.f_pwd);
		sPwd = (EditText) findViewById(R.id.s_pwd);
	}

	public void mSure(View view) {
		final String name = fname.getText().toString();
		String oldString = fPwd.getText().toString();
		final String newString = sPwd.getText().toString();
		if (name.isEmpty()) {
			Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
			return;
		}
		if (oldString.isEmpty()) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}
		if (newString.isEmpty()) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}
		if (newString.length() < 6) {
			Toast.makeText(this, "密码请输入6位以上的字符", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!newString.equals(oldString)) {
			Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}

		Intent intent = getIntent();
		final String phone = intent.getStringExtra("phone");
		System.out.println(phone + " " + newString);
		if (phone == null) {
			Toast.makeText(this, "手机号码未验证", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Thread thread = new Thread() {
				public void run() {
					String pwd = HttpUtils
							.doGet("http://www.wadye.cn/prictice/resign.php?user="
									+ phone
									+ "&pass="
									+ newString
									+ "&name="
									+ name);
					Gson codeGson = new Gson();
					code = codeGson.fromJson(pwd, Code.class);
				}
			};
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(code);

			if (code.getCode().equals("401")) {
				Toast.makeText(this, "该电话号码已注册 ", Toast.LENGTH_SHORT).show();
				finish();
			}
			if (code.getCode().equals("400")) {
				Toast.makeText(this, "未知错误  ", Toast.LENGTH_SHORT).show();
				finish();
			}
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}
