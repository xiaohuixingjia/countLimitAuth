package com.umpay.proxyservice.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private final static Logger _log = LoggerFactory.getLogger("HttpUtil");

	private final static String REQUEST_METHOD = "POST";
	private final static String REQUEST_CONTENT_TYPE = "Content-type";
	private final static String REQUEST_CONTENT_TYPE_VALUE = "text/plain";
	//private final static String REQUEST_CONTENT_TYPE_VALUE = "application/json";
	private final static String REQUEST_CHARSET = "Accept-Charset";
	private final static String REQUEST_CHARSET_VALUE = "UTF-8";
	private final static String REQUEST_CONTENT_LENGTH = "Content-Length";

	private final static int READ_TIME_OUT = 35 * 100;
	private final static int CONNECT_TIME_OUT = 1 * 1000; 

	
	public static String post(String u, String str) throws Exception{
		return HttpUtil.post(u, str, READ_TIME_OUT, CONNECT_TIME_OUT);
	}

	public static String post(String u, String str,int readTimeOut , int connectTimeOut) throws Exception{

		URL url;
		url = new URL(u);

		HttpURLConnection huc;
		DataOutputStream printout = null;
		huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod(REQUEST_METHOD);
		huc.setRequestProperty(REQUEST_CONTENT_TYPE,REQUEST_CONTENT_TYPE_VALUE);
		huc.setRequestProperty(REQUEST_CHARSET, REQUEST_CHARSET_VALUE);
		huc.setRequestProperty(REQUEST_CONTENT_LENGTH,String.valueOf(str.getBytes().length));
		huc.setDoOutput(true);
		huc.setReadTimeout(readTimeOut);
		huc.setConnectTimeout(connectTimeOut);
		printout = new DataOutputStream(huc.getOutputStream());
		printout.write(str.getBytes());
		printout.flush();
		
		if (printout != null) {
			try {
				printout.close();
			} catch (IOException e) {
				_log.error("http close printout error:", e);
			}
		}

		StringBuffer sb = new StringBuffer();
		String line;
		InputStream is = null;
		BufferedReader br = null;
		
		
		try {
			is = huc.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			_log.error("http input stream error:", e);
			return null;
		} finally {
			try {
				if(br!=null){
					br.close();
				}
				if(is!=null){
					is.close();
				}
				if(huc!=null){
					huc.disconnect();
				}
			} catch (Exception e) {
				_log.error("http close input stream error:", e);
				return null;
			}
		}		
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		post("http://10.10.111.11", "asefsafsafe");
	}
	
//	public static String sendGet(String url) {
//        String result = "";
//        BufferedReader in = null;
//        try {
//            String urlNameString = url;
//            URL realUrl = new URL(urlNameString);
//            // 打开和URL之间的连�?
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属�?
//            connection.setRequestProperty("accept", "*/*");
//            //connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 建立实际的连�?
//            connection.connect();
//            // 获取�?��响应头字�?
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历�?��的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
//            // 定义 BufferedReader输入流来读取URL的响�?
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 使用finally块来关闭输入�?
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return result;
//    }

	
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			
			connection.connect();
			System.out.println(connection.getHeaderFields());
			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public static String sendGet(String url,int readTimeOut , int connectTimeOut) throws Exception{
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setReadTimeout(readTimeOut);
			connection.setConnectTimeout(connectTimeOut);
			connection.connect();
			System.out.println(connection.getHeaderFields());
			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static String replaceDalTag(String s) throws Exception{
		int st = s.indexOf("[start]");
		int et = s.indexOf("[end]");
		return s.substring(st+7, et);
	}

}