package com.umpay.proxyservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.umpay.proxyservice.Constant;


/**
 * response工具类
 * @author 不明
* @date 化小强 修改2016年6月15日 上午10:29:14
 */
public class ResponseUtil {
	
	
	 private static final String SEP_BE="<" ;
	 private static final String SEP_AF=">" ;
	 private static final String SEP_XIE="/" ;
	 private static final String SEP_RETURN="\n" ;

	public static Map<String, String> genResult(Map<String, String> xmlMap,
			String retcode) {
		if (xmlMap==null) {
			return new HashMap<String, String>();
		}
		xmlMap.put(HttpMap.RETCODE, retcode);
		return xmlMap;
	}
	/**
	 * 鹏元返回结果
	 * @author xuxiaojia
	 * @param xmlMap
	 * @param val 结果
	 * @return
	 */
	public static Map<String, String> genFengyingResult(Map<String, String> xmlMap,
			String val) {
		if (xmlMap==null) {
			return new HashMap<String, String>();
		}
		xmlMap.put(Constant.PY_RESULT, val);
		return xmlMap;
	}
	
	
	public static Map<String, String> genResult2(Map<String, String> xmlMap,
			String retcode) {
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put(HttpMap.FUNCODE, xmlMap.get(HttpMap.FUNCODE));
		responseMap.put(HttpMap.TRANSID, xmlMap.get(HttpMap.TRANSID));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		responseMap.put(HttpMap.DATETIME, sdf.format(new Date()));
		responseMap.put(HttpMap.RETCODE, retcode);
		
		if(responseMap.get(HttpMap.FUNCODE) ==null){
			System.out.println("++++"+responseMap.get(HttpMap.FUNCODE));
			System.out.println(responseMap.get(HttpMap.FUNCODE) == null ? "0" : "1");
		}

		String str = HttpMap.FUNCODE+(responseMap.get(HttpMap.FUNCODE)==null ? "" : responseMap.get(HttpMap.FUNCODE).toString())+
				HttpMap.TRANSID + (responseMap.get(HttpMap.TRANSID)==null ? "" : responseMap.get(HttpMap.TRANSID).toString())+
				HttpMap.DATETIME+ (responseMap.get(HttpMap.DATETIME)==null ? "" : responseMap.get(HttpMap.DATETIME).toString())+
				HttpMap.RETCODE+ (responseMap.get(HttpMap.RETCODE)== null ? "" : responseMap.get(HttpMap.RETCODE).toString());


		responseMap.put(HttpMap.SIGN, MD5Utils.getMD5Str(str));

		return responseMap;
	}
	
	
	public static String genFinalResult2(Map<String, String> xmlMap,String childXml,String mobileid,String retcode) {
		StringBuilder sb = new StringBuilder();
		
		Map<String, String> responseMap=genResult2(xmlMap,retcode);
		//responseMap.put(HttpMap.MOBILEID, mobileid);
		
		sb.append(SEP_BE).append("response").append(SEP_AF).append(SEP_RETURN);
		for(String key : responseMap.keySet()){
			sb.append(SEP_BE).append(key).append(SEP_AF).
			append(responseMap.get(key)).
			append(SEP_BE).append(SEP_XIE).append(key).append(SEP_AF).append(SEP_RETURN);
		}
		sb.append(childXml);
		sb.append(SEP_BE).append(SEP_XIE).append("response").append(SEP_AF).append(SEP_RETURN);
		
		return sb.toString();
	}
}
