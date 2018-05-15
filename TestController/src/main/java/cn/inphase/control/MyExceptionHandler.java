package cn.inphase.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class MyExceptionHandler {
	/** 
	   * 全局处理Exception 
	   * @return 
	   */
	@ResponseBody
	@ExceptionHandler(value = { Exception.class })
	public Map<String, Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		result.put("status", 500);
		result.put("msg", ex.getMessage());
		return result;
	}
}