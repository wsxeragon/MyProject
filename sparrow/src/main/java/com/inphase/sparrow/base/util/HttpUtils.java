package com.inphase.sparrow.base.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.inphase.sparrow.base.handler.HttpProtocolHandler;


public class HttpUtils {
	
	private static final Logger logger = Logger.getLogger("errorfile");
	
	private static final String DEFAULT_CHARSET= "UTF-8";
	//设置HTTP请求内容为JSON格式
	public static final String CONTENTTYPE_JSON = "application/json";
	//设置HTPP请求内容为XML格式
	public static final String CONTENTTYPE_XML = "application/xml";
	
	private HttpUtils(){}

	/**
	 * 
	 * @Title get
	 * @Description 执行get请求
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String get(String url, Map<String, String> params) {
		HttpProtocolHandler  httpHandler = HttpProtocolHandler.getInstance();
		String result = null;
		HttpGet httpGet;
		try {
			httpGet = httpHandler.httpGetHandler(url, params);
			result = httpHandler.execute(httpGet);
		} catch (URISyntaxException | IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}
	/**
	 * 
	 * @Title post
	 * @Description 执行POST请求
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String post(String url, Map<String, String> params) {
		HttpProtocolHandler  httpHandler = HttpProtocolHandler.getInstance();
		String result = null;
		HttpPost httpPost;
		try {
			httpPost = httpHandler.httpPostHandler(url, params);
			result = httpHandler.execute(httpPost);
		} catch (URISyntaxException | IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}
	/**
	 * 
	 * @Title post
	 * @Description post提交json、xml等格式数据
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param url
	 * @param ctype  内容类型,如application/json,application/xml
	 * @param paramStr
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url,String ctype, String paramStr) {
		HttpProtocolHandler  httpHandler = HttpProtocolHandler.getInstance();
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(paramStr, DEFAULT_CHARSET);
		httpPost.setHeader("Content-Type",ctype);
		httpPost.setEntity(entity);
		String result = null;
		try {
			result = httpHandler.execute(httpPost);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}
	/**
	 * 
	 * @Title postByForm
	 * @Description 模拟form表单提交
	 * @author zuoyc
	 * @date 2017年5月19日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String postByForm(String url, Map<String, String> params) {
		HttpProtocolHandler  httpHandler = HttpProtocolHandler.getInstance();
		String result = null;
		HttpPost httpPost;
		try {
			httpPost = httpHandler.httpPostFormHandler(url, params, DEFAULT_CHARSET);
			result = httpHandler.execute(httpPost);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}
}