package com.homework.addressbook.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtil {
	
	/**
	 * 获取用户ip地址
	 * @param request
	 * @return
	 */
	public  static String getClientIP(HttpServletRequest request) {
    	String ipAddress = null;     
	    ipAddress = request.getHeader("X-Forwarded-For");   
	    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	ipAddress = request.getHeader("X-Real-IP");   
	    }   
	    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	        ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	    }   
	    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	ipAddress = request.getRemoteAddr();   
	    	if(ipAddress.equals("127.0.0.1")){   
	    		//根据网卡取本机配置的IP   
	    		InetAddress inet=null;   
	    		try {   
	    			inet = InetAddress.getLocalHost();   
	    		} catch (UnknownHostException e) {   
	    			e.printStackTrace();   
	    		}   
	    		ipAddress= inet.getHostAddress();   
	    	}
  
	    }   
	    //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	    if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	        if(ipAddress.indexOf(",")>0){   
	            ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	        }   
	    }   
    	return ipAddress;
    }
	
	
	/**
	 * 获取当前请求的url路径
	 * @param request
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		int serverPort = request.getServerPort();
		if (serverPort == 80) {
			
			String serverName = request.getServerName();
			String requestURI="http://" + serverName //服务器地址  
					//   + ":"  + request.getServerPort()           //端口号  
					+ request.getRequestURI();
			String queryString = request.getQueryString(); //请求参数
			if (queryString != null) {
				requestURI = requestURI+"?"+queryString;
			}
			return requestURI;		
		}else {
			String serverName = request.getServerName();
			String requestURI="http://" + serverName //服务器地址  
					   + ":"  + request.getServerPort()           //端口号  
					+ request.getRequestURI();
			String queryString = request.getQueryString(); //请求参数
			if (queryString != null) {
				requestURI = requestURI+"?"+queryString;
			}
			return requestURI;		
		}
	}


	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}

	
	/**
	 * 判断是否是微信浏览器
	 * @param request
	 * @return true->是微信浏览器 
	 */
	public static boolean isWxBrowser(HttpServletRequest request) {
		String header = request.getHeader("user-agent");
		if (header == null) {
			return false;
		}
		String ua =  header.toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		}
		return false;
	}
	

	public static String getMobileModel(String userAgent){
		//获取手机型号开始**************************************************************************
		// 获取Android手机型号
		String mobileModel = "unknown";
		try {
			Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
			Matcher matcher = pattern.matcher(userAgent);
			if (matcher.find()) {
				String sub = StringUtils.substringBetween(userAgent, "(", ")");
				String[] split = StringUtils.split(sub, ";");
				sub = split[split.length-2]+split[split.length-1];
				mobileModel = StringUtils.replaceOnce(sub, "Build", "").trim();
			
			}
			String upperCase = userAgent.toUpperCase();
			// 判断iphone和ipad
			if (upperCase.indexOf("IPHONE") > 0) {
				mobileModel = "iphone";
			}
			if (upperCase.indexOf("IPAD") > 0) {
				mobileModel = "ipad";
			}
		} catch (Exception e) {
		}
		//获取手机型号结束**************************************************************************
		return mobileModel;
	}

	

}
