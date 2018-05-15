package com.inphase.sparrow.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.SystemLog;
import com.inphase.sparrow.service.system.LogService;

/**      
 * @Description:系统操作日志与文本日志展示
 * @author: sunchao
 */
@Controller
@RequestMapping("log")
public class LogController{
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("main")
	public String main(int type, Model model){
		logService.getTextLog(type, "");
		String returnUrl;
		switch (type) {
		case 2:
			returnUrl = "system/log_text";
			break;
		case 1:
		default:
			returnUrl = "system/log_list";
			break;
		}
		return returnUrl;
	}
	

	@RequestMapping("getsystemlog")
	@ResponseBody
	public TableMessage<List<SystemLog>> getSystemLog(TableParam tableParam, Model model, HttpServletRequest request){
		Map<String, Object> searchCondition = new HashMap<String, Object>();
		searchCondition.put("operName", request.getParameter("operName"));
		searchCondition.put("logMode", request.getParameter("logMode"));
		searchCondition.put("startTime", request.getParameter("startTime"));
		searchCondition.put("endTime", request.getParameter("endTime"));
		tableParam.setSearchCondition(searchCondition);
		return logService.listLog(tableParam);
	}
	
	@RequestMapping("gettextlog")
	public String getTextLog(int type, String date, Model model){		
		String logContent = logService.getTextLog(type, date);
		model.addAttribute("logContent", logContent);
		return "system/fragment/log_textcontent";
	}
}
