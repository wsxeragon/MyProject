package cn.inphase.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import cn.inphase.domain.ArticleType;
import cn.inphase.domain.Image;
import cn.inphase.domain.ImageType;
import cn.inphase.domain.Item;
import cn.inphase.domain.TextType;

public class MyTool {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

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
			resultMap.put("respbody", sb.toString());
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

	// 接受请求，返回处理结果
	public static String handleMessage(HttpServletRequest request) {
		InputStream inputStream = null;
		String respContent = "请求处理异常，请稍候尝试！";
		try {
			Map<String, String> requestMap = new HashMap<>();
			// 默认返回的文本消息内容

			// 从request中取得输入流
			inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				requestMap.put(e.getName(), e.getText());
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			TextType textType = new TextType();
			XStream xStream = new XStream();
			switch (msgType) {
			case REQ_MESSAGE_TYPE_TEXT:
				textType.setFromUserName(toUserName);
				textType.setToUserName(fromUserName);
				textType.setCreateTime(new Date().getTime());
				textType.setMsgType(REQ_MESSAGE_TYPE_TEXT);
				textType.setContent("你发送的是文字信息，服务器回复的也是文字信息");
				xStream.alias("xml", TextType.class);
				respContent = xStream.toXML(textType);
				break;
			case REQ_MESSAGE_TYPE_EVENT:
				String event = requestMap.get("Event");
				System.out.println("event:" + event);
				if (event.equals(EVENT_TYPE_SUBSCRIBE)) {
					textType.setFromUserName(toUserName);
					textType.setToUserName(fromUserName);
					textType.setCreateTime(new Date().getTime());
					textType.setMsgType(REQ_MESSAGE_TYPE_TEXT);
					textType.setContent("感谢订阅");
					xStream.alias("xml", TextType.class);
					respContent = xStream.toXML(textType);
				}
				if (event.equals(EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					if (eventKey.equals("003_1")) {
						ImageType imageType = new ImageType();
						imageType.setFromUserName(toUserName);
						imageType.setToUserName(fromUserName);
						imageType.setCreateTime(new Date().getTime());
						imageType.setMsgType(REQ_MESSAGE_TYPE_IMAGE);
						Image image = new Image();
						image.setMediaId("AgUE5DO6k8Eav5BslwhMWlFQqUHumxT6fe0N0hzBWQKDs1LKpbieGyHK5vkp310p");
						imageType.setImage(image);
						xStream.alias("Image", Image.class);
						xStream.alias("xml", ImageType.class);
						respContent = xStream.toXML(imageType);
					}
					if (eventKey.equals("003_2")) {
						ArticleType articleType = new ArticleType();
						articleType.setFromUserName(toUserName);
						articleType.setToUserName(fromUserName);
						articleType.setCreateTime(new Date().getTime());
						articleType.setMsgType(RESP_MESSAGE_TYPE_NEWS);
						Item item = new Item();
						item.setDescription("hahaha");
						item.setPicUrl("http://img0.imgtn.bdimg.com/it/u=4040470692,460373187&fm=214&gp=0.jpg");
						item.setTitle("图文");
						item.setUrl("https://www.baidu.com");
						List<Item> list = new ArrayList<>();
						list.add(item);
						articleType.setArticles(list);
						xStream.alias("item", Item.class);
						xStream.alias("xml", ArticleType.class);
						respContent = xStream.toXML(articleType);
					}
				}
				break;
			default:
				textType.setFromUserName(toUserName);
				textType.setToUserName(fromUserName);
				textType.setCreateTime(new Date().getTime());
				textType.setMsgType(REQ_MESSAGE_TYPE_TEXT);
				textType.setContent("哈？？！！");
				xStream.alias("xml", TextType.class);
				respContent = xStream.toXML(textType);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(respContent);
		return respContent;
	}

}
