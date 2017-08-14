package com.zx.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zx.util.Tools;

public class LocalCacheUtils {

	private static final String CACHE_PATH = Tools.getRootFilePath() + "/images";

	/**
	 * 从本地读取图片
	 * 
	 * @param url
	 */
	public Bitmap getBitmapFromLocal(String url) {
		String fileName = null;// 把图片的url当做文件名,并进行MD5加密
		try {
			fileName = url.substring(url.length()-9, url.length()-4);
			File file = new File(CACHE_PATH, fileName);

			Bitmap bitmap = BitmapFactory
					.decodeStream(new FileInputStream(file));

			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 从网络获取图片后,保存至本地缓存
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void setBitmapToLocal(String url, Bitmap bitmap) {
		try {
			String fileName = url.substring(url.length()-9, url.length()-4);// 把图片的url当做文件名,并进行MD5加密
			File file = new File(CACHE_PATH, fileName);

			// 通过得到文件的父文件,判断父文件是否存在
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}

			// 把图片保存至本地
			bitmap.compress(Bitmap.CompressFormat.PNG, 100,
					new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
