package cn.inphase.tool;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PostFile {

	// 文件，文本 post一起发送
	public Map<String, Object> HttpPostWithFile(String url, List<String> paths, Map<String, String> param) {
		String returnValue = "这是默认返回值，接口调用失败";
		Map<String, Object> resultMap = new HashMap<>();
		int statusCode = 404;

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		// 防止文件名乱码
		builder.setCharset(Charset.forName("utf-8"));
		// 浏览器兼容模式
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		// 设置文件
		for (String path : paths) {
			builder.addBinaryBody("files", new File(path));
		}
		// 设置文本参数
		for (String key : param.keySet()) {
			builder.addTextBody(key, param.get(key), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
		}
		// 生成 HTTP POST 实体
		HttpEntity requestEntity = builder.build();

		// 创建httpPost对象
		HttpPost httpPost = new HttpPost(url);
		// 设置 http post 请求头
		// httpPost.setHeader("Content-type", "application/json");
		// 设置 HTTP POST 实体
		httpPost.setEntity(requestEntity);

		try ( // 创建HttpClient对象
				CloseableHttpClient httpClient = HttpClients.createDefault();
				// 发送HttpPost请求
				CloseableHttpResponse httpResonse = httpClient.execute(httpPost);) {

			// 获取返回码 返回值
			statusCode = httpResonse.getStatusLine().getStatusCode();
			returnValue = EntityUtils.toString(httpResonse.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("", returnValue);
		resultMap.put("statusCoreturnValuede", statusCode);
		// 第五步：处理返回值
		return resultMap;
	}

}
