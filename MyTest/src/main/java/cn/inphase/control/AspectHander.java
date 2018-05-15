package cn.inphase.control;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面
 * 默认spring aop不会拦截controller层，使用该类需要在spring公共配置文件中注入改bean，
 * 另外需要配置<aop:aspectj-autoproxy proxy-target-class="true"/>
 * 
 */
// @Aspect
public class AspectHander {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	private void webPointcut() {
	}

	@AfterThrowing(pointcut = "webPointcut()", throwing = "e")
	public void handleThrowing(Exception e) {
		System.out.println("切入");
		System.out.println(e.getMessage());

	}

}