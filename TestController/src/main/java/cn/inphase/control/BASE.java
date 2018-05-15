package cn.inphase.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.itv.tool.DateUtils;
import com.itv.tool.HttpsInvokeUtil;
import com.itv.tool.PropertiesUtil;

import cn.inphase.domain.Customer;
import cn.inphase.service.UserService;
import cn.inphase.tool.MyPropertyPlaceholderConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class BASE {

	@Autowired
	private UserService userService;

	private static int SIZE = 10000;
	private static int MAX = 20;
	private static String imToken = "";// IM服务端请求token
	private static int expires_in = 0;// token有效持续时长,单位秒
	private static long refreshTokenTime = System.currentTimeMillis() / 1000;// 下次刷新token时间

	/**
	 * 刷新token
	 * 
	 * @return
	 */
	private void getIMToken() {
		if (DateUtils.IsOverdue(refreshTokenTime)) {
			String org_name = PropertiesUtil.get("org_name");
			String app_name = PropertiesUtil.get("app_name");
			String grant_type = PropertiesUtil.get("grant_type");
			String client_id = PropertiesUtil.get("client_id");
			String client_secret = PropertiesUtil.get("client_secret");
			JSONObject json = new JSONObject();
			json.put("grant_type", grant_type);
			json.put("client_id", client_id);
			json.put("client_secret", client_secret);
			String getTokenUrl = PropertiesUtil.get("getToken").replace("{org_name}", org_name).replace("{app_name}",
					app_name);
			try {
				// 获取远程返回结果
				Map<String, String> header = null;
				Map<String, Object> resultMap = HttpsInvokeUtil.postJsonByIM(getTokenUrl, json.toJSONString(), header);
				// Log4jTool.logInfo(interfaceUrl +
				// startpagemanage_getlist,JSON.toJSONString(map1),
				String temp = resultMap.get("respbody").toString();
				JSONObject resultJson = JSONObject.parseObject(temp);
				imToken = resultJson.get("access_token").toString();
				expires_in = Integer.parseInt(resultJson.get("expires_in").toString());
				refreshTokenTime = DateUtils.getTimestamp(expires_in, Calendar.SECOND);
			} catch (Exception e) {
				// Log4jTool.logErrror(JSON.toJSONString(map1), e);
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// 一次注册一条
	@Test
	public void test0() {
		double size = 1000.0;
		int totalNum = userService.selectCount("0");
		int count = (int) Math.ceil(totalNum / size);
		for (int j = 1; j <= count; j++) {
			List<Customer> phones = userService.getPhoneAndNicknameTemp("0", 1, (int) size);
			System.out.println("次数：" + j + "  始：" + phones.get(0) + "  终：" + phones.get(phones.size() - 1));
			for (int i = 0; i < phones.size(); i++) {
				String HXregister_url = PropertiesUtil.get("HXregister_url");
				Map<String, String> params = new HashMap<>();
				params.put("username", phones.get(i).getAccount());
				params.put("nickname", phones.get(i).getAccount());
				Map<String, Object> result = HttpsInvokeUtil.HttpGET(HXregister_url, params);
				userService.updateFlagById(phones.get(i).getId(), "1");
			}
		}

	}

	@Test
	public void test1() {
		int count = userService.selectCount("0");
		// 一次读取10000条，总共读取次数
		int num = (int) Math.ceil(count / SIZE);
		// 存储每次发送20条的id
		List<String> idlist = new ArrayList<>();
		// 请求成功表示
		boolean flag = true;
		for (int i = 1; i <= num; i++) {
			int t = 1;
			List<Customer> phones = userService.getPhoneAndNickname("0", i, SIZE);
			for (int j = 0; j < Math.ceil(phones.size()) / MAX; j++) {
				List<Object> list = new ArrayList<>();
				flag = true;
				for (int k = 0; k < MAX; k++) {
					Map<String, String> resp = new HashMap<>();
					if (t > phones.size()) {
						break;
					}
					idlist.add(phones.get(t - 1).getId());
					resp.put("username", phones.get(t - 1).getAccount());
					resp.put("password", "123456");
					list.add(resp);
					t++;
				}
				String urlStr = PropertiesUtil.get("regeditUser");
				System.out.println(idlist);
				Map<String, Object> result;
				try {
					result = HttpPOST(urlStr, new Gson().toJson(list));
					System.out.println(result);
					if ((int) result.get("status") != 200) {
						flag = false;
					}
				} catch (Exception e) {
					flag = false;
				}
				if (!flag) {
					System.out.println("待处理" + idlist);
					userService.updateFlag(idlist, "2");
				} else {
					userService.updateFlag(idlist, "1");
				}
				idlist.clear();
			}
		}

	}

	// 一次查询20条
	@Test
	public void test11() {
		int count = userService.selectCount("0");
		int num = (int) Math.ceil(count / MAX);
		// 存储每次发送20条的id
		List<String> idlist = new ArrayList<>();
		// 请求成功表示
		boolean flag = true;
		for (int i = 1; i <= num; i++) {
			List<Customer> phones = userService.getPhoneAndNickname("0", i, MAX);
			List<Object> list = new ArrayList<>();
			for (int j = 0; j < phones.size(); j++) {
				flag = true;
				Map<String, String> resp = new HashMap<>();
				idlist.add(phones.get(j).getId());
				resp.put("username", phones.get(j).getAccount());
				resp.put("password", "123456");
				list.add(resp);
			}
			String urlStr = PropertiesUtil.get("regeditUser");
			System.out.println(idlist);
			Map<String, Object> result;
			try {
				result = HttpPOST(urlStr, new Gson().toJson(list));
				System.out.println(result);
				if ((int) result.get("status") != 200) {
					flag = false;
				}
			} catch (Exception e) {
				flag = false;
			}
			if (!flag) {
				System.out.println("待处理" + idlist);
				userService.updateFlag(idlist, "2");
			} else {
				userService.updateFlag(idlist, "1");
			}
			idlist.clear();

		}

	}

	// 删除注册用户
	@Test
	public void test2() {
		int code = 200;
		String urlStr = PropertiesUtil.get("regeditUser");
		while (code == 200) {
			Map<String, Object> result = HTTPDelete(urlStr + "?limit=500");
			code = (int) result.get("status");
			System.out.println(code);
		}

	}

	@Test
	public void test3() {
		List<String> idlist = new ArrayList<>();
		idlist.add("1");
		idlist.add("2");
		idlist.add("3");
		int r = userService.updateFlag(idlist, "1");
		System.out.println(r);
	}

	public Map<String, Object> HttpPOST(String urlStr, String jsonStr) {
		if (StringUtils.isEmpty(imToken) || DateUtils.IsOverdue(refreshTokenTime)) {
			getIMToken();
		}
		int code = 404;
		Map<String, Object> resultMap = new HashMap<>();
		try {
			// 创建连接
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + imToken);
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(jsonStr.getBytes("UTF-8"));
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// 断开连接
			code = connection.getResponseCode();
			connection.disconnect();
			resultMap.put("respbody", sb.toString());
			resultMap.put("status", code);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

	public Map<String, Object> HTTPDelete(String urlStr) {
		if (StringUtils.isEmpty(imToken) || DateUtils.IsOverdue(refreshTokenTime)) {
			getIMToken();
		}
		int code = 404;
		Map<String, Object> resultMap = new HashMap<>();
		StringBuffer sb = null;
		try {
			// 创建连接
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("DELETE");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + imToken);
			code = connection.getResponseCode();
			// connection.connect();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("respbody", sb.toString());
		resultMap.put("status", code);
		return resultMap;

	}

	@Autowired
	@Qualifier("propertyConfigurer1")
	private MyPropertyPlaceholderConfigurer configurer1;

	@Test
	public void testProps() {
		System.out.println(configurer1.getStringByKey("org_name"));
	}

}
