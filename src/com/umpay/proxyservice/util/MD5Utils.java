package com.umpay.proxyservice.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * md5工具类
 * @author huaxiaoqiang
* @date 2016年6月15日 上午10:29:49
 */
public class MD5Utils {
	public static  String getMD5Str(String str) {  
		
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
        return md5StrBuff.toString();  
    }  
	
	public static void main(String[] args){
	
		//String str="funcode0001datetime20150112205012meridzhongzhichengtransidT98857count3";
		
		String str="funcode0002transid20150127110854591711datetime20150127110900retcode0000count1";

		System.out.println(getMD5Str(str));
	}
}
