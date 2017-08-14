package com.zx.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.zx.family.R;
import com.zx.util.Utils;

public class CenterActivity extends Activity{
	private EditText nameEditText;
	private EditText pwdEditText;
	private EditText telEditText;
	private EditText adressEditText;
	private ImageView register_personal_icon;
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	public ImageView iv_personal_icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_center);
		nameEditText =(EditText) findViewById(R.id.register_name);
		pwdEditText =(EditText) findViewById(R.id.register_pw);
		adressEditText =(EditText) findViewById(R.id.register_adress);
		register_personal_icon=(ImageView) findViewById(R.id.register_personal_icon);
		iv_personal_icon =(ImageView) findViewById(R.id.iv_personal_icon);
	}
	public void changeImg(View view){
		showChoosePicDialog();
	
	}
	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // 选择本地照片
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setDataAndType(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							"image/*");
					startActivityForResult(intent, CHOOSE_PICTURE);// //适用于4.4及以上android版本

					break;
				case TAKE_PICTURE: // 拍照
					Intent openCameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					break;
				}
			}
		});
		builder.create().show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
				break;
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
				}
				break;
			}
		}
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param
	 * 
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			register_personal_icon.setImageBitmap(photo);
			//iv_personal_icon.setImageBitmap(photo);
			//uploadPic(photo);
		}
	}
	public void registerIn(View view){
		Intent intent =new Intent(this,LoginActivity.class);
		Editable name =nameEditText.getText();
		Editable pwd =pwdEditText.getText();
		Editable adress =adressEditText.getText();
		//Toast.makeText(this, name+" "+pwd, Toast.LENGTH_LONG).show();
		
		finish();
	}
}
