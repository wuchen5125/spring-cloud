package com.nc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class NCHttpClientUtil {
	public static String sendNCxmlData(String url,
			String xmlStr) {
		BufferedReader in = null;
		String result = "";
		OutputStream out = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-type", "text/xml");
			// 发送POST请求
			conn.setRequestMethod("POST");
			// 获取URLConnection对象对应的输出流
			out = conn.getOutputStream();
			//读取xml文件,转换为字节流
			byte[] temp = xmlStr.getBytes("UTF-8");
			//			logger.info(">>>>>调用NC接口查询xml<<<<<<:" + xmlStr);
			// 发送请求参数
			out.write(temp, 0, temp.length);
			// flush输出流的缓冲

			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			int line;
			while ((line = in.read()) != -1) {
				result += (char) line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
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
		System.out.println("推送地址："+ url);
		System.out.println("请求报文："+ xmlStr);
		System.out.println("请求结果："+ result);
		return result;
	}
	
}
