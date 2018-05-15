package com.wsx.springbootTest.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyTool {

	public static Map<String, Object> HttpGet(String urlStr, Map<String, String> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (params != null && params.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (String key : params.keySet()) {
					sb.append("&");
					sb.append(key);
					sb.append("=");
					sb.append(params.get(key));
				}
				if (urlStr.contains("?")) {
					urlStr = urlStr + sb.toString();
				} else {
					urlStr = urlStr + "?" + sb.toString().substring(1);
				}
			}
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 打开连接
			// urlConnection.setConnectTimeout(10000);
			int code = urlConnection.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			resultMap.put("respbody", new Gson().fromJson(sb.toString(), new TypeToken<Map<String, Object>>() {
			}.getType()));
			resultMap.put("status", code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("respbody", e.getMessage());
			resultMap.put("status", -1);
		}
		return resultMap;
	}

	public static Map<String, Object> postJson(String url, String jsonStr) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		// String IP = AddressUtil.getServerIp();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		// httpPost.addHeader("secretkey",secretkey);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(new StringEntity(jsonStr, Charset.forName("utf-8")));
		CloseableHttpClient client = HttpClients.createMinimal();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "utf-8");
			Thread current = Thread.currentThread();
			rsMap.put("status", response.getStatusLine().getStatusCode());
			rsMap.put("respbody", result);
		} catch (Exception e) {
			e.printStackTrace();
			rsMap.put("respbody", e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rsMap;
	}

	public static Map<String, Object> postForm(String url, Map<String, String> params)
			throws UnsupportedEncodingException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		// String IP = AddressUtil.getServerIp();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		// httpPost.addHeader("secretkey",secretkey);
		httpPost.setHeader("Accept", "application/json");
		// 凭据提供器
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				// 认证范围
				new AuthScope("http://localhost:9093/", 9093),
				// 认证用户名和密码
				new UsernamePasswordCredentials("", ""));
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (String k : params.keySet()) {
			formParams.add(new BasicNameValuePair(k, params.get(k)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
		CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "utf-8");
			Thread current = Thread.currentThread();
			rsMap.put("status", response.getStatusLine().getStatusCode());
			rsMap.put("respbody", new Gson().fromJson(result, new TypeToken<Map<String, Object>>() {
			}.getType()));
		} catch (Exception e) {
			e.printStackTrace();
			rsMap.put("respbody", e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rsMap;
	}
}
