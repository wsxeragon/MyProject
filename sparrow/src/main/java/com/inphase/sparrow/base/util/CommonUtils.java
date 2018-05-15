package com.inphase.sparrow.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CommonUtils {
	
	private static final Logger logger = Logger.getLogger("errorfile");
	
	private static final String NUMBERCHAR = "0123456789";
	private static final String UNKNOWN = "unknown";
	
	private CommonUtils(){}
	
	/** 
	   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	   * 
	   * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
	   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
	   * 
	   * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
	   * 192.168.1.100 
	   * 
	   * 用户真实IP为： 192.168.1.110 
	   * 
	   * @param request 
	   * @return 
	   */
	  public static String getIpAddress(HttpServletRequest request) {
		  
		  String ip = request.getHeader("x-forwarded-for"); 
		  if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) { 
			  ip = request.getHeader("Proxy-Client-IP"); 
		  } 
		  if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) { 
			  ip = request.getHeader("WL-Proxy-Client-IP"); 
		  } 
		  if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) { 
			  ip = request.getHeader("HTTP_CLIENT_IP"); 
		  } 
		  if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) { 
			  ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		  } 
		  if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) { 
			  ip = request.getRemoteAddr(); 
		  } 
		  return ip; 
	  }
	  
	  /**
	 * @Description 添加Cookie
	 * @param response 
	 * @param name  cookie名称
	 * @param value cookie存放的数据
	 * @param maxAge cookie的超期时间
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){		
		try {
			Cookie cookie = new Cookie(name,URLEncoder.encode(value, "UTF-8"));
			//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，
			//那么只有设置该cookie路径及其子路径可以访问
			cookie.setPath("/");
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
	  }
	
	 /**
	 * @Description 删除Cookie
	 * @param response 
	 * @param name  cookie名称
	 * @param value cookie存放的数据
	 * @param maxAge cookie的超期时间
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name){		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
	  }
	
	/**
	 * @Description 添加Cookie 默认超期3天
	 * @param response
	 * @param name cookie名称
	 * @param value cookie存放的数据
	 */
	public static void addCookie(HttpServletResponse response, String name, String value){
		addCookie(response, name, value, 7*24*60);
	  }
	
	/**
	 * @Description 获取Cookie
	 * @param request
	 * @param name  cookie名称
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name){
		String value = null;
		try{
			for(Cookie cookie : request.getCookies()){
				if(name.equals(cookie.getName())){
					value = URLDecoder.decode(cookie.getValue(), "UTF-8");
					break;
				}
			}
		}catch(Exception e){
			logger.error(e);
		}
		return value;
		
	}
	
	/**
	 * @Description 获取类路径下properties文件下的k-v值
	 * @param key  要获取的value的key
	 * @param fileName  类路径下的文件名
	 * @return
	 */
	public static String getProperty(String key, String fileName){
		String value = "";
		InputStream is = CommonUtils.class.getClassLoader().getResourceAsStream(fileName);
		Properties property = new Properties();
		try {
			property.load(is);
			value = property.getProperty(key);
		} catch (IOException e) {
			logger.error(e);
		}
		return value;
	}

	/**
	 * @Description 默认读取系统全局配置文件（sparrow.property）
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		return getProperty(key, "sparrow.properties");
	}

    /**
	 * 
	 * @Title getRandom
	 * @Description 获取指定长度的随机数
	 * @author zuoyc
	 * @date 2017年5月11日
	 * @param length
	 * @return
	 */
	public static String getRandom(int length) {
		StringBuilder buffer = new StringBuilder();
		Random r = new Random();
		for (int i=0;i<length;i++) {
			buffer.append(NUMBERCHAR.charAt(r.nextInt(10)));
		}
		return buffer.toString();
	}
}
