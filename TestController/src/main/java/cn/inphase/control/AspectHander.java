package cn.inphase.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面
 * 默认spring aop不会拦截controller层，使用该类需要在spring公共配置文件中注入改bean，
 * 另外需要配置<aop:aspectj-autoproxy proxy-target-class="true"/>
 */
@Aspect
public class AspectHander {

	@Pointcut("execution(* cn.inphase.control.JSPController.testBefore(..))") // 匹配方法
	private void webPointcut1() {
	}

	// 只能获取入参，无法修改入参
	// @Before("webPointcut1()")
	// public void permissionCheck(JoinPoint point) {
	// Object[] args = point.getArgs();
	// Map<String, Object> map = new HashMap<>();
	// map.put("修改入参", "success");
	// args[0] = map;
	// }

	@Around("webPointcut1()")
	public Object watchPerformance(ProceedingJoinPoint jp) {
		Object result = null;
		try {
			Object[] args = jp.getArgs();
			Map<String, Object> map = new HashMap<>();
			map.put("修改入参", "success");
			args[0] = map;
			HttpServletRequest request = (HttpServletRequest) args[1];
			request.setAttribute("test", "hahahha");
			args[1] = request;
			System.out.println("切入1");
			result = jp.proceed(args);
			System.out.println("切入2");
		} catch (Throwable e) {
			System.out.println("切入3");
			System.out.println(e.getMessage());
		}
		System.out.println("切入4");
		((Map<String, Object>) result).put("修改返回值", "success");
		return result;
	}

	// @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	// // 匹配注解
	// @Pointcut("execution(* cn.inphase.control.*.*(..))")//匹配方法
	@Pointcut("within(cn.inphase..*)") // 匹配包 注意区别于within(cn.inphase.*)
	// 后者不含子包
	private void webPointcut2() {
	}

	// 异常被抓住就无法切入 ，可以在下层抛出，顶层抓取，
	@AfterThrowing(pointcut = "webPointcut2()", throwing = "e")
	public void handleThrowing(Exception e) {
		System.out.println("异常抛出时切入");
		System.out.println(e.getMessage());

	}

}