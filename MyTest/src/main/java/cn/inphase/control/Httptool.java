package cn.inphase.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Httptool {

	private static final String USER_AGENT = "Mozilla/5.0";

	public static Map<String, Object> sendGET(String urlStr, Map<String, String> params) {
		Map<String, Object> resultMap = new HashMap<>();
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
		StringBuffer response = new StringBuffer("");
		int code = -1;
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpGet httpGet = new HttpGet(urlStr);
			httpGet.addHeader("User-Agent", USER_AGENT);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			response = new StringBuffer();
			code = httpResponse.getStatusLine().getStatusCode();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));) {
				String inputLine;
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("respbody", response.toString());
		resultMap.put("status", code);
		return resultMap;

	}

	public static Map<String, Object> sendPOST(String urlStr, Map<String, String> params) {
		Map<String, Object> resultMap = new HashMap<>();
		int code = -1;
		StringBuffer response = new StringBuffer("");
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpPost httpPost = new HttpPost(urlStr);
			httpPost.addHeader("User-Agent", USER_AGENT);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String k : params.keySet()) {
					urlParameters.add(new BasicNameValuePair(k, params.get(k)));
				}
			}

			HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
			httpPost.setEntity(postParams);

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			code = httpResponse.getStatusLine().getStatusCode();
			String inputLine;
			response = new StringBuffer();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));) {
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", response.toString());
		resultMap.put("status", code);
		return resultMap;
	}

}