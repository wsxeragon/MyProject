package com.inphase.sparrow.log;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.inphase.sparrow.base.annotation.Log;
import com.inphase.sparrow.base.util.CommonUtils;
import com.inphase.sparrow.base.util.JsonUtils;
import com.inphase.sparrow.dao.system.LogDao;
import com.inphase.sparrow.entity.system.SystemLog;
import com.inphase.sparrow.entity.system.User;

/**      
 * @Description:全局日志功能
 * @author: sunchao
 */
@Aspect
@Component
public class SystemLogAspect {
	
	@Autowired
	private LogDao logDao;

	/**
	 * 定义日志切点
	 */
	@Pointcut("execution(* com.inphase.sparrow.controller.system.*.*(..)) && @annotation(com.inphase.sparrow.base.annotation.Log)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于在调用切点之前进行拦截操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	/*@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
	}*/

	/**
	 * 配置环绕通知,使用在方法aspect()上注册的切入点，环绕通知包含了另外四中通知的功能，因此不建议同时使用
	 * 
	 * @param joinPoint
	 */
	
	/* @Around("controllerAspect()") 
	 public void around(ProceedingJoinPoint joinPoint){}*/
	 

	/**
	 * 后置通知  在配置的切入点在调用后进行拦截
	 * 
	 * @param joinPoint
	 *            切点对象
	 */
	@After(value = "controllerAspect()")
	public void after(JoinPoint joinPoint){
		SystemLog systemLog = new SystemLog();
		//根据方法签名，获取当前方法对象
		Signature signature = joinPoint.getSignature();  
		MethodSignature methodSignature = (MethodSignature)signature;  
		Method method = methodSignature.getMethod();  
		Log log = method.getAnnotation(Log.class);
		systemLog.setLogContent(log.operationName());
		systemLog.setLogMode(log.operationType());
		
		//获取cookie里面的用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
		User user = JsonUtils.jsonDeserialization(CommonUtils.getCookie((HttpServletRequest) request,
						"loginuser"), User.class);
		systemLog.setOperId(user.getOperId());
		systemLog.setOperName(user.getOperName());
		systemLog.setLogIP(CommonUtils.getIpAddress(request));
		
		logDao.saveLog(systemLog);
	}

	/**
	 * 返回拦截 在切入点正常返回后拦截
	 * 
	 * @param joinPoint
	 */
	
	/* @AfterReturning(pointcut="controllerAspect()",returning="result") 
	 public void afterReturn(JoinPoint joinPoint, Object result){}*/
	 

	/**
	 * 异常通知，用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 * @param e
	 */
	
	 /*@AfterThrowing(pointcut="controllerAspect()", throwing="e")
	 public void doAfterThrowing(JoinPoint joinPoint, Throwable e){}*/
	 
}
