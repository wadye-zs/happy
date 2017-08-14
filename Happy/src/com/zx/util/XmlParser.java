package com.zx.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zx.bean.Sellman;

public class XmlParser {


	private static File file;

	public static List<Sellman> getManXml(String urlString) {
		List<Sellman> sellmanList = new ArrayList<Sellman>();

		try {
		     file = new File(Tools.getRootFilePath() + "sellman.xml");
			if (!file.exists()) {
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();
				con.connect();
				InputStream is = con.getInputStream();
				OutputStream os = new FileOutputStream(file, true);
				byte[] bt = new byte[1024];
				int len = -1;
				while ((len = is.read(bt)) != -1) {
					os.write(bt, 0, len);
				}
				os.flush();
				os.close();
				is.close();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList n1 = doc.getElementsByTagName("item");
			int len = n1.getLength();
			for (int i = 0; i < len; i++) {
				Sellman sellman = new Sellman();
				int len2 = n1.item(i).getChildNodes().getLength();
				for (int j = 0; j < len2; j++) {
					Node node1 = n1.item(i).getChildNodes().item(j);
					if (node1.getNodeType() == 1) {
						String content = node1.getFirstChild().getNodeValue();
						String nodeName = node1.getNodeName();
						if ("sm_id".equals(nodeName))
							sellman.setId(Integer.parseInt(content));
						if ("s_name".equals(nodeName))
							sellman.setName(content);
						if ("keyword".equals(nodeName))
							sellman.setKeyword(content);
						if ("sm_photo".equals(nodeName))
							sellman.setPhoto(content);
						if ("sm_tel".equals(nodeName))
							sellman.setTel(content);
						if ("sm_address".equals(nodeName))
							sellman.setAddress(content);
						if ("latitude".equals(nodeName))
							sellman.setLat(Double.parseDouble(content));
						if ("longitude".equals(nodeName))
							sellman.setLog(Double.parseDouble(content));
						if ("grade".equals(nodeName))
							sellman.setRatnum(Float.parseFloat(content));
						//System.out.println(content);
					}
				}
				sellmanList.add(sellman);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}finally{
		    file.delete();
		}
		return sellmanList;

	}
	public static List<Sellman> getOrderXml(String urlString) {
		List<Sellman> sellmanList = new ArrayList<Sellman>();

		try {
		     file = new File(Tools.getRootFilePath() + "sellman.xml");
			if (!file.exists()) {
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();
				con.connect();
				InputStream is = con.getInputStream();
				OutputStream os = new FileOutputStream(file, true);
				byte[] bt = new byte[1024];
				int len = -1;
				while ((len = is.read(bt)) != -1) {
					os.write(bt, 0, len);
				}
				os.flush();
				os.close();
				is.close();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList n1 = doc.getElementsByTagName("item");
			int len = n1.getLength();
			for (int i = 0; i < len; i++) {
				Sellman sellman = new Sellman();
				int len2 = n1.item(i).getChildNodes().getLength();
				for (int j = 0; j < len2; j++) {
					Node node1 = n1.item(i).getChildNodes().item(j);
					if (node1.getNodeType() == 1) {
						String content = node1.getFirstChild().getNodeValue();
						String nodeName = node1.getNodeName();
						if ("id".equals(nodeName))
							sellman.setId(Integer.parseInt(content));
						if ("s_name".equals(nodeName))
							sellman.setName(content);
						if ("sm_photo".equals(nodeName))
							sellman.setPhoto(content);
						if ("sm_tel".equals(nodeName))
							sellman.setTel(content);
						if ("latitude".equals(nodeName))
							sellman.setLat(Double.parseDouble(content));
						if ("longitude".equals(nodeName))
							sellman.setLog(Double.parseDouble(content));
						if ("time".equals(nodeName))
							sellman.setKeyword(content);
						if ("peo_num".equals(nodeName))
							sellman.setAddress(content);
						if ("comment_id".equals(nodeName))
							sellman.setComment_id(Integer.parseInt(content));
						if ("grade".equals(nodeName))
							sellman.setRatnum(Float.parseFloat(content));
					}
				}
				if (sellman.getComment_id()==0) {
					
					sellmanList.add(sellman);
				}
				//System.out.println(sellman.getComment_id());			
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}finally{
		    file.delete();
		}
		return sellmanList;
	}
	public static List<Sellman> getAllXml(String urlString) {
		List<Sellman> sellmanList = new ArrayList<Sellman>();

		try {
		     file = new File(Tools.getRootFilePath() + "sellman.xml");
			if (!file.exists()) {
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();
				con.connect();
				InputStream is = con.getInputStream();
				OutputStream os = new FileOutputStream(file, true);
				byte[] bt = new byte[1024];
				int len = -1;
				while ((len = is.read(bt)) != -1) {
					os.write(bt, 0, len);
				}
				os.flush();
				os.close();
				is.close();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList n1 = doc.getElementsByTagName("item");
			int len = n1.getLength();
			for (int i = 0; i < len; i++) {
				Sellman sellman = new Sellman();
				int len2 = n1.item(i).getChildNodes().getLength();
				for (int j = 0; j < len2; j++) {
					Node node1 = n1.item(i).getChildNodes().item(j);
					if (node1.getNodeType() == 1) {
						String content = node1.getFirstChild().getNodeValue();
						String nodeName = node1.getNodeName();
						if ("sm_id".equals(nodeName))
							sellman.setId(Integer.parseInt(content));
						if ("s_name".equals(nodeName))
							sellman.setName(content);
						if ("sm_photo".equals(nodeName))
							sellman.setPhoto(content);
						if ("sm_tel".equals(nodeName))
							sellman.setTel(content);
						if ("latitude".equals(nodeName))
							sellman.setLat(Double.parseDouble(content));
						if ("longitude".equals(nodeName))
							sellman.setLog(Double.parseDouble(content));
						if ("time".equals(nodeName))
							sellman.setKeyword(content);
						if ("peo_num".equals(nodeName))
							sellman.setAddress(content);
						if ("comment_id".equals(nodeName))
							sellman.setComment_id(Integer.parseInt(content));
						if ("grade".equals(nodeName))
							sellman.setRatnum(Float.parseFloat(content));
					}
				}
				if (sellman.getComment_id()!=0) {
					
					sellmanList.add(sellman);
				}
				//System.out.println(sellman.getComment_id());			
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}finally{
		    file.delete();
		}
		return sellmanList;

	}
}
