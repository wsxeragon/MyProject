package cn.inphase.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itv.tool.PropertiesUtil;
import com.thoughtworks.xstream.XStream;

import cn.inphase.domain.Article;
import cn.inphase.domain.Button;
import cn.inphase.domain.Filter;
import cn.inphase.domain.TextMassMsg;
import cn.inphase.domain.TextType;
import cn.inphase.tool.MyTool;
import cn.inphase.tool.SignUtil;

@Controller
@RequestMapping(value = "/wechat")
public class WechatController {
	private static Gson gson = new Gson();

	private static String accessToken = "nlc_0jSL26qzD4TbclbvPXR-aLWELgQFK8FXczY1EE7EjevchBg6e4bCVf2JK5xQhi5fLkliyWHfhvg-s_lyEMG2Ghd2U9RHCq0bVxDDPaIovfX3_DZvVbdyl6ozgh9nVTCjABAZXG";
	private static long timer = 0;
	private static long js_timer = 0;
	private static final long two_hours = 1000 * 60 * 60 * 2;

	@PostConstruct
	public void tiktok() {
		System.out.println("start");
		// getAccessToken();
		System.out.println(accessToken);
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timer++;
					if (timer % (1000 * 60) == 0) {
						System.out.println(timer);
					}
				}
			}
		}) {
		}.start();
	}

	// accesstoken
	@ResponseBody
	@RequestMapping("/getAccessToken.do")
	public Map<String, Object> getAccessToken() {
		Map<String, Object> resultMap = new HashMap<>();
		String accessUrl = PropertiesUtil.get("access_token_url");
		String grantType = PropertiesUtil.get("grant_type");
		String appId = PropertiesUtil.get("appid");
		String secret = PropertiesUtil.get("secret");
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", grantType);
		params.put("appid", appId);
		params.put("secret", secret);
		try {
			resultMap = MyTool.HttpGet(accessUrl, params);
			Map<String, Object> map_1 = new HashMap<>();
			try {
				map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMap.put("respbody", map_1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMap.get("respbody") != null
				&& ((Map<String, Object>) resultMap.get("respbody")).get("access_token") != null) {
			accessToken = (String) ((Map<String, Object>) resultMap.get("respbody")).get("access_token");
			// 重置计时器
			timer = 0;
		}
		System.out.println(accessToken);
		return resultMap;
	}

	// JSaccesstoken code=CODE&grant_type=
	@ResponseBody
	@RequestMapping("/getJSAccessToken.do")
	public Map<String, Object> getJSAccessToken(String code, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<>();
		String accessUrl = PropertiesUtil.get("js_access_token_url");
		String grantType = PropertiesUtil.get("js_grant_type");
		String appId = PropertiesUtil.get("appid");
		String secret = PropertiesUtil.get("secret");
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", grantType);
		params.put("appid", appId);
		params.put("secret", secret);
		params.put("code", code);
		try {
			resultMap = MyTool.HttpGet(accessUrl, params);
			Map<String, Object> map_1 = new HashMap<>();
			try {
				map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMap.put("respbody", map_1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMap.get("respbody") != null
				&& ((Map<String, Object>) resultMap.get("respbody")).get("access_token") != null) {
			Cookie cookie = new Cookie("openid",
					(String) ((Map<String, Object>) resultMap.get("respbody")).get("openid"));
			Cookie cookie1 = new Cookie("access_token",
					(String) ((Map<String, Object>) resultMap.get("respbody")).get("access_token"));
			cookie.setMaxAge(30 * 60);// 设置为30min
			cookie.setPath("/");
			cookie1.setMaxAge(30 * 60);// 设置为30min
			cookie1.setPath("/");
			System.out.println("已添加===============");
			response.addCookie(cookie);
			response.addCookie(cookie1);
		}
		return resultMap;
	}

	// 获取openid列表
	@ResponseBody
	@RequestMapping("getOpenidList.do")
	public Map<String, Object> getOpenidList() {
		Map<String, Object> resultMap = new HashMap<>();
		String get_openid_list_url = PropertiesUtil.get("get_openid_list");
		get_openid_list_url = get_openid_list_url + "?access_token=" + accessToken + "&next_openid=" + "";
		resultMap = MyTool.postJson(get_openid_list_url, "");
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}

	// 获取用户信息 使用jsaccessToken
	@ResponseBody
	@RequestMapping("/getJsUserInfo.do")
	public Map<String, Object> getJsUserInfo(String js_access_token, String openid) {
		Map<String, Object> resultMap = new HashMap<>();
		String userinfo = PropertiesUtil.get("js_userinfo");
		Map<String, String> params = new HashMap<>();
		params.put("access_token", js_access_token);
		params.put("openid", openid);
		params.put("lang", "zh_CN");
		try {
			resultMap = MyTool.HttpGet(userinfo, params);
			Map<String, Object> map_1 = new HashMap<>();
			try {
				map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMap.put("respbody", map_1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

	// 获取用户信息
	@ResponseBody
	@RequestMapping("/getUserInfo.do")
	public Map<String, Object> getUserInfo(String openid) {
		Map<String, Object> resultMap = new HashMap<>();
		String get_userInfo_url = PropertiesUtil.get("get_userInfo");
		Map<String, String> params = new HashMap<>();
		params.put("access_token", accessToken);
		params.put("openid", openid);
		params.put("lang", "zh_CN");
		try {
			resultMap = MyTool.HttpGet(get_userInfo_url, params);
			Map<String, Object> map_1 = new HashMap<>();
			try {
				map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMap.put("respbody", map_1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

	// 获取用户信息列表
	@ResponseBody
	@RequestMapping("/getUserInfoList.do")
	public Map<String, Object> getUserInfoList() {
		Map<String, Object> resultMap = new HashMap<>();
		String get_userInfo_list_url = PropertiesUtil.get("get_userInfo_list");
		get_userInfo_list_url = get_userInfo_list_url + "?access_token=" + accessToken;
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map2.put("openid", "oMjMNs2Pmlt0CNZdJ17oV2mFr82Y");
		map2.put("lang", "zh_CN");
		List<Object> list = new ArrayList<>();
		list.add(map2);
		map1.put("user_list", list);
		String str = gson.toJson(map1);
		try {
			resultMap = MyTool.postJson(get_userInfo_list_url, str);
			Map<String, Object> map_1 = new HashMap<>();
			try {
				map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMap.put("respbody", map_1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

	// 创建菜单
	@ResponseBody
	@RequestMapping("/createMenu.do")
	public Map<String, Object> createMenu() {
		Map<String, Object> resultMap = new HashMap<>();
		String menuCreateUrl = PropertiesUtil.get("menu_create");
		String acesstoken = null;
		// 如果计时器超过两个小时重新获取
		if (timer >= two_hours) {
			// Map<String, Object> result = getAccessToken();
			// acesstoken = (String) ((Map<String, Object>)
			// result.get("respbody")).get("access_token");
		} else {
			acesstoken = accessToken;
		}

		menuCreateUrl = menuCreateUrl + "?access_token="
				+ "WRi4-z0FhIp1cj15LwJKNyXvc14v1RwO_kAsdu1XmD84MiEPFpux5rpB4yB2op63SwB-rTIqAOH6jqiSg5zUnMmI2w7oYWxO_zjuFQuClLPgjOOLCRTBF-Gik3Tr421lBCFeAJAQRU";
		System.out.println(menuCreateUrl);
		// button1
		Button button1 = new Button();
		button1.setType("click");
		button1.setName("按钮1");
		button1.setKey("001");
		Button subButton1 = new Button();
		subButton1.setType("view");
		subButton1.setName("授权");
		subButton1.setUrl("http://15z54z0179.51mypc.cn/Test/html/index.html");
		subButton1.setKey("001_1");
		Button subButton2 = new Button();
		subButton2.setType("scancode_push");
		subButton2.setName("扫码推事件");
		subButton2.setKey("001_2");
		Button subButton4 = new Button();
		subButton4.setType("scancode_waitmsg");
		subButton4.setName("扫码带提示");
		subButton4.setKey("001_3");
		List<Button> subButtons = new ArrayList<>();
		subButtons.add(subButton1);
		subButtons.add(subButton2);
		subButtons.add(subButton4);
		button1.setSub_button(subButtons);
		// button2
		Button button2 = new Button();
		button2.setType("click");
		button2.setName("按钮22");
		button2.setKey("002");
		Button subButton3 = new Button();
		subButton3.setType("location_select");
		subButton3.setName("发送位置");
		subButton3.setKey("002_1");
		Button subButton5 = new Button();
		subButton5.setType("pic_sysphoto");
		subButton5.setName("拍照发图");
		subButton5.setKey("002_2");
		Button subButton6 = new Button();
		subButton6.setType("pic_photo_or_album");
		subButton6.setName("拍照或者相册发图");
		subButton6.setKey("002_3");
		Button subButton7 = new Button();
		subButton7.setType("pic_weixin");
		subButton7.setName("微信相册发图");
		subButton7.setKey("002_4");
		List<Button> list1 = new ArrayList<>();
		list1.add(subButton3);
		list1.add(subButton5);
		list1.add(subButton6);
		list1.add(subButton7);
		button2.setSub_button(list1);
		// button3
		Button button3 = new Button();
		button3.setType("click");
		button3.setName("按钮33");
		button3.setKey("003");
		Button subButton31 = new Button();
		subButton31.setType("click");
		subButton31.setName("003_1");
		subButton31.setKey("003_1");
		Button subButton32 = new Button();
		subButton32.setType("click");
		subButton32.setName("003_2");
		subButton32.setKey("003_2");
		Button subButton33 = new Button();
		subButton33.setType("click");
		subButton33.setName("003_3");
		subButton33.setKey("003_3");
		Button subButton34 = new Button();
		subButton34.setType("click");
		subButton34.setName("003_4");
		subButton34.setKey("003_4");
		List<Button> subButtons1 = new ArrayList<>();
		subButtons1.add(subButton31);
		subButtons1.add(subButton32);
		subButtons1.add(subButton33);
		subButtons1.add(subButton34);
		button3.setSub_button(subButtons1);
		List<Button> buttons = new ArrayList<>();
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("button", buttons);
		System.out.println(gson.toJson(map1));
		try {
			resultMap = MyTool.postJson(menuCreateUrl, gson.toJson(map1));
			Map<String, Object> map_1 = new HashMap<>();
			try {
				map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMap.put("respbody", map_1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMap.get("respbody") != null
				&& ((Map<String, Object>) resultMap.get("respbody")).get("access_token") != null)
			accessToken = (String) ((Map<String, Object>) resultMap.get("respbody")).get("access_token");
		return resultMap;
	}

	// 验证token和核心服务
	@ResponseBody
	@RequestMapping("processMessage.do")
	public void processMessage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<>();
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			// 只调用一次
			// if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			// out.print(echostr);
			// }
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				// 调用核心业务类接收消息、处理消息
				String respMessage = MyTool.handleMessage(request);
				// 响应消息
				out = response.getWriter();
				out.print(respMessage);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	// 新增永久图文素材
	@ResponseBody
	@RequestMapping("uploadNews.do")
	public Map<String, Object> uploadNews(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tempMap = new HashMap<>();
		String uploadNewsUrl = PropertiesUtil.get("upload_news");
		uploadNewsUrl = uploadNewsUrl + "?access_token=" + accessToken;
		Article article = new Article();
		article.setAuthor("wsx");
		article.setThumb_media_id("nxpYj5-J2Rlk8yCbJzvB0PJwweoG61XHgGSL5Ho129g");
		article.setTitle("第一个图文");
		article.setDigest("说些什么呢");
		article.setShow_cover_pic("0");
		article.setContent(
				"<img src='http://mmbiz.qpic.cn/mmbiz_jpg/IUlf8momsqgGwicMMwdG0fEIZ8SLL2GlblMhL7Py3EWGCCJWXVByPJicsApMibwtD7nXwLX74icVHA2j1ld2Pw8ccQ/0'/>"
						+ "<h1>天天好心情</h1>");
		article.setContent_source_url("https://www.baidu.com");
		List<Article> articles = new ArrayList<>();
		articles.add(article);
		tempMap.put("articles", articles);
		String jsonStr = gson.toJson(tempMap);
		tempMap.clear();
		System.out.println(jsonStr);
		resultMap = MyTool.postJson(uploadNewsUrl, jsonStr);
		try {
			tempMap = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", tempMap);
		return resultMap;
	}

	// 获取素材列表
	@ResponseBody
	@RequestMapping("getMaterial.do")
	public Map<String, Object> getMaterial(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String batchget_material_url = PropertiesUtil.get("batchget_material");
		batchget_material_url = batchget_material_url + "?access_token=" + accessToken;
		Map<String, Object> map = new HashMap<>();
		map.put("type", "image");
		map.put("offset", 0);
		map.put("count", 20);
		// map.put("media_id", "nxpYj5-J2Rlk8yCbJzvB0H9FTcpuR64evXLdDwmkXSk");
		String jsonStr = gson.toJson(map);
		resultMap = MyTool.postJson(batchget_material_url, jsonStr);
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}

	// 解析接收到的xml
	@ResponseBody
	@RequestMapping("parseXml.do")
	public Map<String, String> parseXml(HttpServletRequest request) {
		// 将解析结果存储在HashMap中
		Map<String, String> resultMap = new HashMap<String, String>();
		InputStream inputStream = null;
		try {
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
				resultMap.put(e.getName(), e.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
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
		return resultMap;
	}

	// 发送xml信息
	@ResponseBody
	@RequestMapping("sendXml.do")
	public void sendXml(HttpServletRequest request, HttpServletResponse response) {
		TextType textType = new TextType();
		textType.setFromUserName("wsx");
		textType.setToUserName("hello");
		textType.setCreateTime(new Date().getTime());
		textType.setMsgType("text");
		textType.setContent("hahahahahaah");
		XStream xStream = new XStream();
		xStream.alias("xml", TextType.class);
		String xml = xStream.toXML(textType);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	// 群发
	@ResponseBody
	@RequestMapping("sendToAll.do")
	public Map<String, Object> sendToAll(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<>();
		Filter filter = new Filter();
		filter.setIs_to_all(true);
		// ImageMassMsg imageMassMsg = new ImageMassMsg();
		// imageMassMsg.setFilter(filter);
		// Map<String, Object> map = new HashMap<>();
		// map.put("media_id", "nxpYj5-J2Rlk8yCbJzvB0PJwweoG61XHgGSL5Ho129g");
		// imageMassMsg.setImage(map);
		// String str = gson.toJson(imageMassMsg);
		TextMassMsg textMassMsg = new TextMassMsg();
		textMassMsg.setFilter(filter);
		Map<String, Object> text = new HashMap<>();
		text.put("content", "nxpYj5-J2Rlk8yCbJzvB0PJwweoG61XHgGSL5Ho129g");
		textMassMsg.setText(text);
		String str = gson.toJson(textMassMsg);
		System.out.println(str);
		String mass_message_url = PropertiesUtil.get("mass_message");
		mass_message_url = mass_message_url + "?access_token=" + accessToken;
		resultMap = MyTool.postJson(mass_message_url, str);
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, String>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}

	// 创建标签
	@ResponseBody
	@RequestMapping("createTag.do")
	public Map<String, Object> createTag() {
		Map<String, Object> resultMap = new HashMap<>();
		String create_tag_url = PropertiesUtil.get("create_tag");
		create_tag_url = create_tag_url + "?access_token=" + accessToken;
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map2.put("name", "群组2");
		map1.put("tag", map2);
		String str = gson.toJson(map1);
		resultMap = MyTool.postJson(create_tag_url, str);
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}

	// 查看所有标签的信息
	@ResponseBody
	@RequestMapping("getTag.do")
	public Map<String, Object> getTag() {
		Map<String, Object> resultMap = new HashMap<>();
		String get_tag_url = PropertiesUtil.get("get_tag");
		get_tag_url = get_tag_url + "?access_token=" + accessToken;
		resultMap = MyTool.postJson(get_tag_url, "");
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}

	// 查看标签下的用户信息
	@ResponseBody
	@RequestMapping("getUserByTag.do")
	public Map<String, Object> getUserByTag() {
		Map<String, Object> resultMap = new HashMap<>();
		String get_user_by_tag_url = PropertiesUtil.get("get_user_by_tag");
		get_user_by_tag_url = get_user_by_tag_url + "?access_token=" + accessToken;
		Map<String, String> map1 = new HashMap<>();
		map1.put("tagid", "101");
		map1.put("next_openid", "");
		String str = gson.toJson(map1);
		resultMap = MyTool.postJson(get_user_by_tag_url, str);
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}

	// 为用户添加标签
	@ResponseBody
	@RequestMapping("postTag.do")
	public Map<String, Object> postTag() {
		Map<String, Object> resultMap = new HashMap<>();
		String post_tag_url = PropertiesUtil.get("post_tag");
		post_tag_url = post_tag_url + "?access_token=" + accessToken;
		List<String> list = new ArrayList<>();
		list.add("oMjMNs2Pmlt0CNZdJ17oV2mFr82Y");
		Map<String, Object> map1 = new HashMap<>();
		map1.put("openid_list", list);
		map1.put("tagid", "101");
		String str = gson.toJson(map1);
		resultMap = MyTool.postJson(post_tag_url, str);
		Map<String, Object> map_1 = new HashMap<>();
		try {
			map_1 = gson.fromJson((String) resultMap.get("respbody"), new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("respbody", map_1);
		return resultMap;
	}
}
