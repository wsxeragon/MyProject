package cn.inphase.control;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.inphase.domain.Person;
import cn.inphase.domain.Peter;
import cn.inphase.domain.User;
import cn.inphase.service.MyEvent;
import cn.inphase.service.PhoneEvent;
import cn.inphase.service.TestService;
import cn.inphase.tool.MyPropertyPlaceholderConfigurer;

@ApplicationScope
@Controller
@RequestMapping(value = "/controller")
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class TestController {
	// @Autowired可以对那些已知的具有可解析依赖的接口起效，
	// 不需要实现ApplicationContextAware接口就可以自动获取到ApplicationContext
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private TestService testService;

	@ResponseBody
	@RequestMapping("/selectMap.do")
	public Map<String, Object> selectMap(String id) {
		Map<String, Object> map = new HashMap<>();
		map = testService.selectById(id);
		return map;
	}

	@ResponseBody
	@RequestMapping("/selectOnetoOne.do")
	public Map<String, Object> selectOnetoOne(String id) {
		Map<String, Object> map = new HashMap<>();
		User user = testService.selectUserWithAddressById(id);
		map.put("user", user);
		return map;
	}

	@ResponseBody
	@RequestMapping("/selectOnetoAll.do")
	public Map<String, Object> selectOnetoAll(String id) {
		Map<String, Object> map = new HashMap<>();
		User user = testService.selectUserWithAddressAndOrder(id);
		map.put("user", user);
		return map;
	}

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<>();

		System.out.println(1);

	}

	@RequestMapping(value = "/vm/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		List<String> list = new ArrayList<>();
		list.add("一");
		list.add("二");
		list.add("三");
		list.add("四");
		modelAndView.addObject("list", list);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@Autowired
	@Qualifier("propertyConfigurer1")
	private MyPropertyPlaceholderConfigurer configurer1;

	@ResponseBody
	@RequestMapping("/testSession")
	public Map<String, Object> method2(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		// 获取web.xml里的值
		ServletContext servletContext = session.getServletContext();
		String log4jRefreshInterval = servletContext.getInitParameter("log4jRefreshInterval");
		System.out.println(log4jRefreshInterval);

		// 获取MyPropertyPlaceholderConfigurer中的值
		String org_name = configurer1.getStringByKey("appid");
		System.out.println(org_name);
		map.put("log4jRefreshInterval", log4jRefreshInterval);
		map.put("org_name", org_name);
		return map;
	}

	@Test
	public void test1() {
		Httptool.sendGET("http://localhost:8080/Test/controller/testSession", null);
	}

	@Resource(name = "peter")
	Peter peter;

	@ResponseBody
	@RequestMapping("/testLookup")
	public Map<String, Object> testLookup() throws CloneNotSupportedException {
		Map<String, Object> map = new HashMap<>();
		Person p = new Person();
		// gson对象默认不序列化为null的值，静态属性属于类不属于对象，不被序列化
		System.out.println(new Gson().toJson(p));
		GsonBuilder gBuilder = new GsonBuilder();
		System.out.println(gBuilder.serializeNulls().create().toJson(p));
		map.put("friend", peter.getFriend().toString());
		map.put("friend0", peter.getFriend());
		map.put("peter", peter.toString());
		return map;
	}

	@Test
	public void testG() {
		System.out.println(new Gson().fromJson("{\"name\":\"wer\",\"age\":0}", Person.class));
		System.out.println(new Gson().fromJson("{\"name0\":\"wer\",\"age\":0}", Person.class));
		System.out.println(new Gson().fromJson("{\"NAME\":\"wer\",\"age\":0}", Person.class));
		System.out.println(new Gson().fromJson("{\"NA\":\"wer\",\"age\":0}", Person.class));
	}

	@Resource(name = "personRequest")
	Person pr;

	/*
	 * 
	 * 将作用域小的bean注入到作用域长的bean中，需要手动开启代理，否则大作用域会覆盖小作用域
	 * 
	 * requestScope的controller里注入requestScope的bean，每次请求，得到的注入bean都是不同的
	 * 
	 * sessionScope的controller里注入requestScope的bean，每次请求，得到的注入bean都是同一个，
	 * 需要开启代理才能实现不同的bean
	 * 
	 * controller 默认是applicationScope
	 */
	@ResponseBody
	@RequestMapping("/testScope")
	public Map<String, Object> testScope() throws CloneNotSupportedException {
		Map<String, Object> map = new HashMap<>();
		pr.setName("www");
		pr.setAge(18);
		map.put("p", pr.toString());
		map.put("personRequest", applicationContext.getBean("personRequest").toString());
		map.put("&personRequest", applicationContext.getBean("&personRequest").toString());
		return map;
	}

	// @Override
	// public void setApplicationContext(ApplicationContext applicationContext)
	// throws BeansException {
	// this.applicationContext = applicationContext;
	//
	// }

	@ResponseBody
	@RequestMapping("/testEvent")
	public Map<String, Object> testEvent() {
		Map<String, Object> map = new HashMap<>();
		// 创建事件
		MyEvent event = new MyEvent(this);
		event.setEventContent("hello world");
		// 触发事件
		applicationContext.publishEvent(event);

		PhoneEvent phoneEvent = new PhoneEvent(this, "123456789");
		applicationContext.publishEvent(phoneEvent);
		phoneEvent = new PhoneEvent(this, "123");
		applicationContext.publishEvent(phoneEvent);

		applicationContext.publishEvent("今天天气不错");
		map.put("success", true);
		return map;
	}

	@Test
	public void testCollections() {
		System.out.println(Collections.EMPTY_LIST == Collections.emptyList());
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		System.out.println(Collections.max(list));
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		});
		// list.add(22);
		Collections.replaceAll(list, 1, 10);
		System.out.println(list);

	}

	@ResponseBody
	@RequestMapping("/hello")
	public Map<String, Object> hello() {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}

	@Test
	public void test11() {
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate);
		if (localDate.getMonthValue() == 2 && localDate.getDayOfMonth() == 27) {
			System.out.println("surprise");
		}
		LocalDate localDate2 = localDate.plus(1, ChronoUnit.MONTHS);
		LocalDate localDate3 = localDate.plus(-2, ChronoUnit.MONTHS);
		System.out.println(localDate2);
		System.out.println(localDate2.isAfter(localDate));
		System.out.println(localDate3);

		Clock clock = Clock.systemUTC();
		System.out.println(clock.getZone() + ":" + clock.millis());

		Integer[] ints = new Integer[1];
		ints[0] = 1;
		System.out.println(ints[0]);
		String str1 = "12";
		String str2 = "1";
		str2 = str2 + "2";
		System.out.println(str1 == str2);// f
	}

	@Test
	public void test5() {
		System.out.println("基本类型：" + returnNum());
		System.out.println("引用类型：" + returnArray()[0]);
	}

	public int[] returnArray() {
		int[] a = new int[1];
		try {
			a[0] = 1;
			return a;
		} catch (Exception e) {
			return a;
			// TODO: handle exception
		} finally {
			a[0] = 3;
		}

	}

	public int returnNum() {
		int a = 0;
		try {
			a++;
			return a;
		} catch (Exception e) {
			// TODO: handle exception
			return a;
		} finally {
			a++;
		}

	}
}
