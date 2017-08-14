package com.zx.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.util.Log;

//Http请求的工具类
public class HttpUtils {

	private static final int TIMEOUT_IN_MILLIONS = 5000;

	public interface CallBack {
		void onRequestComplete(String result);
	}

	/**
	 * 异步的Get请求
	 * 
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = doGet(urlStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * 异步的Post请求
	 * 
	 * @param urlStr
	 * @param params
	 * @param callBack
	 * @throws Exception
	 */
	public static void doPostAsyn(final String urlStr, final String params,
			final CallBack callBack) throws Exception {
		new Thread() {
			public void run() {
				try {
					String result = doPost(urlStr, params);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();

	}

	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();

				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}

		return null;

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

			if (param != null && !param.trim().equals("")) {
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String post(final String url, final Map<String, String> params,
			final Map<String, File> files) throws IOException {

		new Thread() {
			public void run() {
				String BOUNDARY = java.util.UUID.randomUUID().toString();
				String PREFIX = "--", LINEND = "\r\n";
				String MULTIPART_FROM_DATA = "multipart/form-data";
				String CHARSET = "UTF-8";
				URL uri;
				try {
					uri = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) uri
							.openConnection();
					conn.setReadTimeout(10 * 1000); // 缓存的最长时间
					conn.setDoInput(true);// 允许输入
					conn.setDoOutput(true);// 允许输出
					conn.setUseCaches(false); // 不允许使用缓存
					conn.setRequestMethod("POST");
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Charsert", "UTF-8");
					conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
							+ ";boundary=" + BOUNDARY); // 首先组拼文本类型的参数
					StringBuilder sb = new StringBuilder();
					for (Map.Entry<String, String> entry : params.entrySet()) {
						sb.append(PREFIX);
						sb.append(BOUNDARY);
						sb.append(LINEND);
						sb.append("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"" + LINEND);
						sb.append("Content-Type: text/plain; charset=" + CHARSET
								+ LINEND);
						sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
						sb.append(LINEND);
						sb.append(entry.getValue());
						sb.append(LINEND);
					}
					DataOutputStream outStream = new DataOutputStream(
							conn.getOutputStream());
					outStream.write(sb.toString().getBytes()); // 发送文件数据
					if (files != null)
						for (Map.Entry<String, File> file : files.entrySet()) {
							StringBuilder sb1 = new StringBuilder();
							sb1.append(PREFIX);
							sb1.append(BOUNDARY);
							sb1.append(LINEND);
							sb1.append("Content-Disposition: form-data; name=\"files[]\"; filename=\""
									+ file.getValue().getName() + "\"" + LINEND);
							sb1.append("Content-Type: application/octet-stream; charset="
									+ CHARSET + LINEND);
							sb1.append(LINEND);
							outStream.write(sb1.toString().getBytes());
							InputStream is = new FileInputStream(file.getValue());
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = is.read(buffer)) != -1) {
								outStream.write(buffer, 0, len);
							}
							is.close();
							outStream.write(LINEND.getBytes());
						}
					// 请求结束标志
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND)
							.getBytes();
					outStream.write(end_data);
					outStream.flush();
					// 得到响应码
					int res = conn.getResponseCode();
					InputStream in = conn.getInputStream();
					StringBuilder sb2 = new StringBuilder();
					if (res == 200) {
						int ch;
						while ((ch = in.read()) != -1) {
							sb2.append((char) ch);
						}
					}
					System.out.println(sb2.toString());
					outStream.close();
					conn.disconnect();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		return "";
	}

}
