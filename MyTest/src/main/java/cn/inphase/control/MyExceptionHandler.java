package cn.inphase.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.inphase.tool.Log4jTool;

//@ControllerAdvice
public class MyExceptionHandler {
	/** 
	   * 全局处理Exception 
	   * @return 
	   */
	// @ResponseBody
	@ExceptionHandler(value = { Exception.class })
	public Map<String, Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		// log4j放入的数据可通过'%Xin'获取
		MDC.put("in", "data_in");
		String eMsg = Log4jTool.getExceptionDetail(ex);
		// 传入的数据在log4j中格式中 可以通过'%m'来获取
		Log4jTool.logErrror(eMsg);
		result.put("status", 500);
		result.put("msg", ex.getMessage());
		return result;
	}
}