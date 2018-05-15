package com.inphase.sparrow.base.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inphase.sparrow.base.util.CommonUtils;
import com.inphase.sparrow.base.util.JsonUtils;
import com.inphase.sparrow.entity.ResponseMessage;
import com.inphase.sparrow.entity.system.User;


/**      
 * @Description:框架全局处理，异常以及controller的全局返回
 * @author: sunchao
 */
//此注解会拦截用requestmapping注解的方法
@ControllerAdvice
public class GlobeControllerHandler{
	
	private Logger log = Logger.getLogger(GlobeControllerHandler.class);
	
	/**
	 * @Description 对businessexception异常进行处理
	 * @param request
	 * @param be
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseMessage<Void> catchControllerException(HttpServletRequest request, BusinessException be){
		return new ResponseMessage<Void>(be.getCode(),be.getErrorMessage());
	}
	
	/**
	 * @Description 对exception异常进行处理
	 * @param request
	 * @param model
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String catchSystemException(HttpServletRequest request,Model model, Throwable ex){
		log.error("ErrorMessage:"+ExceptionUtils.getFullStackTrace(ex));
		String loginInfo = CommonUtils.getCookie(request, "loginuser");
    	if(!StringUtils.isEmpty(loginInfo)){
    		User user = JsonUtils.jsonDeserialization(loginInfo,User.class);
            model.addAttribute("loginUser",user);
    	} 
		return "error/error_jump";
	}
	
    /**
     * @Description 进入controller之前调用此方法，将cookie写入response
     * @param model
     * @param request
     */
    @ModelAttribute
    public void addAttributes(Model model,HttpServletRequest request){
    	String loginInfo = CommonUtils.getCookie(request, "loginuser");
    	if(!StringUtils.isEmpty(loginInfo)){
    		User user = JsonUtils.jsonDeserialization(loginInfo,User.class);
            model.addAttribute("loginUser",user);
    	}    	
    }
}
