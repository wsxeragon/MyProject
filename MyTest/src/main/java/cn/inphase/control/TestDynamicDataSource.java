package cn.inphase.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.inphase.domain.User;
import cn.inphase.service.TestService;
import cn.inphase.tool.DataSourceContextHolder;
import cn.inphase.tool.DynamicDataSource;

@Controller
@Scope("prototype")
@RequestMapping(value = "/controller1")
public class TestDynamicDataSource {
	@Autowired
	private DynamicDataSource dynamicDataSource;
	@Autowired
	private TestService testService;

	private int a = 0;
	private User User = new User("123", "123", null, null);

	@ResponseBody
	@RequestMapping("/test.do")
	public void test1() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test1?useUnicode=true&amp;characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dynamicDataSource.addDataSource("test1", dataSource);
		DataSourceContextHolder.setDataSourceType("test1");
		Map<String, Object> map = new HashMap<>();
		map = testService.selectById("1");
		System.out.println(map);
		BasicDataSource dataSource1 = new BasicDataSource();
		dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource1.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8");
		dataSource1.setUsername("root");
		dataSource1.setPassword("");
		dynamicDataSource.addDataSource("test", dataSource1);
		DataSourceContextHolder.setDataSourceType("test");
		map = testService.selectById("1");
		System.out.println(map);
		BasicDataSource dataSource2 = new BasicDataSource();
		dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource2.setUrl("jdbc:mysql://localhost:3306/test2?useUnicode=true&amp;characterEncoding=utf-8");
		dataSource2.setUsername("root");
		dataSource2.setPassword("");
		dynamicDataSource.addDataSource("test2", dataSource2);
		DataSourceContextHolder.setDataSourceType("test2");
		map = testService.selectById("1");
		System.out.println(map);
	}

	@ResponseBody
	@RequestMapping("/getA.do")
	public Map<String, String> getA() {
		Map<String, String> map = new HashMap<>();
		map.put("a", "" + User);
		return map;
	}

	@ResponseBody
	@RequestMapping("/setA.do")
	public Map<String, String> setA(@RequestParam("v") String v) {
		Map<String, String> map = new HashMap<>();
		User.setAge(v);
		map.put("a", "" + User);
		return map;
	}
}
