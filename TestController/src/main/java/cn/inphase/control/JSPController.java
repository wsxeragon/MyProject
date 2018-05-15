package cn.inphase.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.inphase.domain.Customer;
import cn.inphase.domain.Person;
import cn.inphase.domain.Person2;
import cn.inphase.domain.User4;
import cn.inphase.service.PersonService;
import cn.inphase.service.Test12;
import cn.inphase.service.UserService;
import cn.inphase.tool.DataSourceContextHolder;
import cn.inphase.tool.DynamicDataSource;
import cn.inphase.tool.MyPropertyPlaceholderConfigurer;
import cn.inphase.tool.PostFile;

@RequestMapping("/jsp")
@Controller
public class JSPController {

	@RequestMapping("/index")
	public ModelAndView method1(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		// 放字符串
		modelAndView.addObject("name", "wsx");
		Customer customer = new Customer();
		customer.setAccount("joker");
		customer.setId("0");

		// 放对象
		// request.setAttribute("customer", customer);
		modelAndView.addObject("customer", customer);

		// 存数组
		List<Customer> members = new ArrayList<>();
		Customer customer1 = new Customer();
		customer1.setAccount("mona");
		customer1.setId("1");
		members.add(customer1);
		Customer customer2 = new Customer();
		customer2.setAccount("fox");
		customer2.setId("5");
		members.add(customer2);
		modelAndView.addObject("members", members);
		return modelAndView;
	}

	@Autowired
	@Qualifier("propertyConfigurer1")
	private MyPropertyPlaceholderConfigurer configurer1;

	@ResponseBody
	@RequestMapping("/test1")
	public Map<String, Object> method2(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		// 获取web.xml里的值
		ServletContext servletContext = session.getServletContext();
		String log4jRefreshInterval = servletContext.getInitParameter("log4jRefreshInterval");
		System.out.println(log4jRefreshInterval);
		// 获取MyPropertyPlaceholderConfigurer中的值
		String org_name = configurer1.getStringByKey("org_name");
		System.out.println(org_name);
		map.put("log4jRefreshInterval", log4jRefreshInterval);
		map.put("org_name", org_name);
		return map;
	}

	@ResponseBody
	@RequestMapping("/testJsonToMap")
	public Map<String, Object> testJsonToMap(@RequestParam Map<String, String> reqMap) {
		Map<String, Object> map = new HashMap<>();
		map.put("reqMap", reqMap);
		return map;
	}

	@ResponseBody
	@RequestMapping("/postFile")
	public Map<String, Object> postFile() {
		PostFile postFile = new PostFile();
		String path = "C:\\Users\\WSX\\Desktop\\线条3.mp4";
		List<String> paths = new ArrayList<>();
		paths.add(path);
		Map<String, String> param = new HashMap<>();
		param.put("name", "noob");
		param.put("age", "12");
		Map<String, Object> res = postFile.HttpPostWithFile("http://localhost:8080/TestController/jsp/receiveFile",
				paths, param);
		return res;
	}

	// 文件接收 可以分开接受，也可以一起接收
	// 分开接收每个文件有单独的key @RequestParam注解可有可无
	// 一起接收的话，所有文件共享同一个key 必须要@RequestParam注解
	@ResponseBody
	@RequestMapping("/receiveFile")
	// public Map<String, Object> receiveFile(MultipartFile file1, MultipartFile
	// file2, String name, String age) {
	public Map<String, Object> receiveFile(@RequestParam("files") MultipartFile[] files, String name, String age) {
		Map<String, Object> map = new HashMap<>();
		// MultipartFile[] files = new MultipartFile[] { file1, file2 };
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					try {
						// 文件保存路径
						String filePath = "C:\\Users\\WSX\\Desktop\\test\\" + file.getOriginalFilename();
						// 转存文件
						file.transferTo(new File(filePath));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		map.put("name", name);
		map.put("age", age);
		map.put("success", true);
		return map;
	}

	@Autowired
	Test12 test12;

	@ResponseBody
	@RequestMapping("/testException")
	public Map<String, Object> testException() {
		Map<String, Object> map = new HashMap<>();
		try {
			test12.produceException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("1", "a");
		return map;
	}

	@ResponseBody
	@RequestMapping("/testBefore")
	public Map<String, Object> testBefore(@RequestParam(required = false) Map<String, Object> map,
			HttpServletRequest request) {
		map.put("1", "a");
		System.out.println(request.getAttribute("test"));
		return map;
	}

	@Autowired
	UserService UserService;
	@Autowired
	PersonService personService;
	@Autowired
	DynamicDataSource dynamicDataSource;
	@Resource(name = "dataSource1")
	BasicDataSource dataSource1;
	// @Autowired按byType自动注入
	// @Resource默认按byName自动注入,匹配id
	// 如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。

	// 测试 多数据源数据操作
	@ResponseBody
	@RequestMapping("/testInsert")
	public Map<String, Object> testInsert() {
		Map<String, Object> map = new HashMap<>();
		// 先使用默认数据连接，插入数据
		User4 user4 = new User4();
		user4.setAge(13);
		user4.setName("qqqq");
		boolean flag1 = UserService.insert(user4);

		// 设置新数据连接
		// BasicDataSource dataSource = new BasicDataSource();
		// dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		// dataSource.setUrl("jdbc:mysql://localhost:3306/test1?useUnicode=true&amp;characterEncoding=utf-8");
		// dataSource.setUsername("root");
		// dataSource.setPassword("");
		dynamicDataSource.addDataSource("test1", dataSource1);
		// 切换数据连接
		DataSourceContextHolder.setDataSourceType("test1");

		// 新数据连接插入数据
		Person person = new Person();
		person.setAge(13);
		person.setName("qqqq");
		boolean flag2 = personService.insert(person);
		map.put("flag1", flag1);
		map.put("flag2", flag2);
		return map;
	}

	@Autowired
	Map<String, Person> pMap;// spring会取容器中搜索Person类的实例，全部放入map中

	@Autowired
	List<Person> pList;// spring会取容器中搜索Person类的实例，全部放入List中

	@Resource(name = "myPerson")
	Person myP;
	@Resource(name = "p4")
	Person2 p4;

	@ResponseBody
	@RequestMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		System.out.println(pMap.keySet());
		System.out.println(pList);
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-mvc.xml");
		Person p1 = (Person) context.getBean("myPerson");
		System.out.println(p1);
		System.out.println(myP);
		System.out.println(p4);
		map.put("success", true);
		return map;
	}

	// 接收json
	@RequestMapping(value = "/json")
	@ResponseBody
	public Map<String, Object> json(@RequestBody Map<String, String> map0) {
		Map<String, Object> map = new HashMap<>();
		map.put("map0", map0);
		return map;
	}

	@RequestMapping(value = "/form")
	@ResponseBody
	public Map<String, Object> form(String arg0, String arg1) {
		Map<String, Object> map = new HashMap<>();
		map.put("arg0", arg0);
		map.put("arg1", arg1);
		return map;
	}

}
