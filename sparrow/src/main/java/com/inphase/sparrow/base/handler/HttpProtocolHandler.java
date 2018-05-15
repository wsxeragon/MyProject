package com.inphase.sparrow.base.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class HttpProtocolHandler {

	private static final String DEFAULT_CHARSET = "UTF-8";

	/** 数据传输处理时间*/
	private int socketTimeout = 10000;

	/** 连接超时时间，由bean factory设置，缺省为8秒钟 */
	private int defaultConnectionTimeout = 8000;

	/** 单路由最大并发数*/
	private int defaultMaxConnPerHost = 30;

	/** 连接池最大并发链接数 */
	private int defaultMaxTotalConn = 80;

	/** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒*/
	private int connectionRequestTimeout = 1000;

	private PoolingHttpClientConnectionManager connManager;

	private RequestConfig defaultRequestConfig;

	private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();

	private HttpProtocolHandler() {

		connManager = new PoolingHttpClientConnectionManager(getSocketRegistry());

		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		connManager.setDefaultSocketConfig(socketConfig);

		connManager.setDefaultConnectionConfig(getConnectionConfig());
		connManager.setMaxTotal(defaultMaxTotalConn);
		connManager.setDefaultMaxPerRoute(defaultMaxConnPerHost);

		defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
	}

	/**
	 * 工厂方法
	 * 
	 * @return
	 */
	public static HttpProtocolHandler getInstance() {
		return httpProtocolHandler;
	}

	/**
	 * 
	 * @Title httpGetHandler
	 * @Description 获取HTTP GET请求对象
	 * @author zuoyc
	 * @date 2017年5月17日
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 * @throws Exception 
	 */
	public HttpGet httpGetHandler(String url, Map<String, String> params)
			throws URISyntaxException, MalformedURLException {
		List<NameValuePair> nvps = getNameValuePair(params);
		URI uri = getURI(url, nvps);
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setURI(uri);
		httpGet.setConfig(getRequestConfig());
		return httpGet;
	}

	/**
	 * 
	 * @Title httpPostHandler
	 * @Description 获取HTTP POST请求对象
	 * @author zuoyc
	 * @date 2017年5月17日
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 * @throws Exception 
	 */
	public HttpPost httpPostHandler(String url, Map<String, String> params)
			throws URISyntaxException, MalformedURLException {
		List<NameValuePair> nvps = getNameValuePair(params);
		URI uri = getURI(url, nvps);
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setConfig(getRequestConfig());
		return httpPost;
	}

	/**
	 * 
	 * @Title httpPostFormHandler
	 * @Description 获取POST form请求对象，该对象可模拟form表的提交
	 * @author zuoyc
	 * @date 2017年5月17日
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws URISyntaxException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public HttpPost httpPostFormHandler(String url, Map<String, String> params, String charset)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = getNameValuePair(params);
		UrlEncodedFormEntity entity = getHttpEntity(nvps, charset);
		httpPost.setEntity(entity);
		httpPost.setConfig(getRequestConfig());
		return httpPost;
	}

	/**
	 * 
	 * @Title execute
	 * @Description 执行请求
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param request 请求对象
	 * @param headers 请求头部信息
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String execute(HttpUriRequest request) throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();
		String responseBody = null;
		HttpClientContext context = HttpClientContext.create();
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) throws IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, DEFAULT_CHARSET) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		};
		responseBody = httpClient.execute(request, responseHandler, context);
		return responseBody;
	}

	/**
	 * 
	 * @Title getNameValuePair
	 * @Description 封装请求参数
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 */
	public List<NameValuePair> getNameValuePair(Map<String, String> params) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			Set<Entry<String, String>> set = params.entrySet();
			for (Entry<String, String> entry : set) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		return nvps;
	}

	/**
	 * 
	 * @Title getURI
	 * @Description 获取get方法的参数URI
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param nvps
	 * @return
	 * @throws URISyntaxException
	 * @throws MalformedURLException 
	 */
	public URI getURI(String inputURL, List<NameValuePair> nvps) throws URISyntaxException, MalformedURLException {
		URL url = new URL(inputURL);
		return new URIBuilder().setScheme(url.getProtocol()).setHost(url.getHost()).setPath(url.getPath())
				.setPort(url.getPort()).setParameters(nvps).build();
	}

	/**
	 * 
	 * @Title getHttpEntity
	 * @Description 获取POST方法的参数Entity
	 * @author zuoyc
	 * @date 2017年5月8日
	 * @param nvps
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public UrlEncodedFormEntity getHttpEntity(List<NameValuePair> nvps, String charset)
			throws UnsupportedEncodingException {
		String defaultCharset = "".equals(charset) ? DEFAULT_CHARSET : charset;
		return new UrlEncodedFormEntity(nvps, defaultCharset);
	}

	private Registry<ConnectionSocketFactory> getSocketRegistry() {

		SSLContext sslcontext = SSLContexts.createSystemDefault();

		return RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
	}

	private ConnectionConfig getConnectionConfig() {

		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(2000).build();

		return ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
	}

	private RequestConfig getRequestConfig() {
		return RequestConfig.copy(defaultRequestConfig).setSocketTimeout(socketTimeout)
				.setConnectTimeout(defaultConnectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
				.build();
	}
}
