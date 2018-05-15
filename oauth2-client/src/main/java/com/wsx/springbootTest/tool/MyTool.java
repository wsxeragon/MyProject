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
import java.util.concurrent.TimeUnit;

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
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

	public static Map<String, Object> postForm1(String url, Map<String, String> params)
			throws UnsupportedEncodingException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		// String IP = AddressUtil.getServerIp();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		// httpPost.addHeader("secretkey",secretkey);
		httpPost.setHeader("Accept", "application/json");
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (String k : params.keySet()) {
			formParams.add(new BasicNameValuePair(k, params.get(k)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
		CloseableHttpClient client = HttpClients.custom().build();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "utf-8");
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

	public static Map<String, Object> restGet(String url) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);

		RestTemplate restTemplate1 = new RestTemplate(requestFactory);

		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			ResponseEntity<String> entity = restTemplate1.getForEntity(url, String.class);
			if (entity.getStatusCodeValue() == 200) {
				rsMap.put("succes", true);
				rsMap.put("result", entity.getBody());
			} else {
				rsMap.put("succes", false);
			}
		} catch (Exception e) {
			rsMap.put("succes", false);
			e.printStackTrace();
		}

		return rsMap;
	}

	public static Map<String, Object> restPostForm(String url, Map<String, String> params) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);

		RestTemplate restTemplate1 = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(
				org.springframework.http.MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8"));
		headers.add("imei", "123123");
		headers.add("token", "3998ee18-b23e-4617-947f-ff8bee611607");
		headers.add("phone", "18620543981");
		headers.add("userid", "29305065989");

		// post表单时 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		for (String key : params.keySet()) {
			multiValueMap.add(key, params.get(key));
		}

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, headers);

		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			ResponseEntity<String> entity = restTemplate1.postForEntity(url, httpEntity, String.class);
			if (entity.getStatusCodeValue() == 200) {
				rsMap.put("succes", true);
				rsMap.put("result", entity.getBody());
			} else {
				rsMap.put("succes", false);
			}
		} catch (Exception e) {
			rsMap.put("succes", false);
			e.printStackTrace();
		}
		return rsMap;
	}

	public static Map<String, Object> restPostJson(String url, Map<String, String> params) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		RestTemplate restTemplate1 = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		org.springframework.http.MediaType mediaType = org.springframework.http.MediaType
				.parseMediaType("application/json;charset=UTF-8");
		headers.setContentType(mediaType);

		// post json 字符串
		HttpEntity<String> httpEntity = new HttpEntity<>(new Gson().toJson(params), headers);
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			ResponseEntity<String> entity = restTemplate1.postForEntity(url, httpEntity, String.class);
			if (entity.getStatusCodeValue() == 200) {
				rsMap.put("succes", true);
				rsMap.put("result", entity.getBody());
			} else {
				rsMap.put("succes", false);
			}
		} catch (Exception e) {
			rsMap.put("succes", false);
			e.printStackTrace();
		}
		return rsMap;
	}

	public static Map<String, Object> restPostFile(String url, List<String> paths, Map<String, String> params) {

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10000);
		requestFactory.setReadTimeout(10000);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.parseMediaType("multipart/form-data"));

		RestTemplate restTemplate1 = new RestTemplate(requestFactory);
		MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
		// 放文件
		for (String path : paths) {
			FileSystemResource fileSystemResource = new FileSystemResource(path);
			valueMap.add("files", fileSystemResource);
		}
		// 放参数
		for (String key : params.keySet()) {
			valueMap.add(key, params.get(key));
		}

		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap, headers);

		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			ResponseEntity<String> entity = restTemplate1.postForEntity(url, httpEntity, String.class);
			if (entity.getStatusCodeValue() == 200) {
				rsMap.put("succes", true);
				rsMap.put("result", entity.getBody());
			} else {
				rsMap.put("succes", false);
			}
		} catch (Exception e) {
			rsMap.put("succes", false);
			e.printStackTrace();
		}
		return rsMap;
	}

	public static Map<String, Object> OkGet(String url) throws IOException {
		Map<String, Object> rsMap = new HashMap<String, Object>();

		OkHttpClient okHttpClient = new OkHttpClient();

		Request request = new Request.Builder().url(url).build();

		try {
			Response response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				rsMap.put("result", response.body().string());
				rsMap.put("success", true);
			} else {
				rsMap.put("success", false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rsMap;
	}

	public final static int CONNECT_TIMEOUT = 60;
	public final static int READ_TIMEOUT = 100;
	public final static int WRITE_TIMEOUT = 60;

	public static Map<String, Object> OkPostJson(String url, String json) throws IOException {

		OkHttpClient client = new OkHttpClient.Builder()//
				.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)// 设置读取超时时间
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)// 设置写的超时时间
				.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)// 设置连接超时时间
				.build();

		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

		Request request = new Request.Builder().url(url).post(body).build();

		Map<String, Object> rsMap = new HashMap<>();

		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				rsMap.put("result", response.body().string());
				rsMap.put("success", true);
			} else {
				rsMap.put("success", false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rsMap;
	}

	public static Map<String, Object> OkPostForm(String url, Map<String, String> params) throws IOException {

		OkHttpClient client = new OkHttpClient.Builder()//
				.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)// 设置读取超时时间
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)// 设置写的超时时间
				.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)// 设置连接超时时间
				.build();

		Builder formBodyBuilder = new FormBody.Builder();
		for (String key : params.keySet()) {
			formBodyBuilder.add(key, params.get(key));
		}
		FormBody formBody = formBodyBuilder.build();
		Request request = new Request.Builder().url(url).post(formBody).build();

		Map<String, Object> rsMap = new HashMap<>();
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				rsMap.put("result", response.body().string());
				rsMap.put("success", true);
			} else {
				rsMap.put("success", false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rsMap;
	}

}
