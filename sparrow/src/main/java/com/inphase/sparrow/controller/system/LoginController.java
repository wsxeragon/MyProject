package com.inphase.sparrow.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inphase.sparrow.base.annotation.Log;
import com.inphase.sparrow.base.handler.BusinessException;
import com.inphase.sparrow.base.util.CommonUtils;
import com.inphase.sparrow.base.util.JsonUtils;
import com.inphase.sparrow.entity.system.FunctionItem;
import com.inphase.sparrow.entity.system.SystemLog;
import com.inphase.sparrow.entity.system.User;
import com.inphase.sparrow.service.system.FunctionItemService;
import com.inphase.sparrow.service.system.LogService;
import com.inphase.sparrow.service.system.LoginService;

/**    
 * @Description:登录相关功能
 * @author: sunchao
 */
@Controller
public class LoginController {

	private static final Logger logger = Logger.getLogger("info");
	private static final String LOGIN = "login";

	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;

	@Autowired
	private FunctionItemService functionItemService;

	@RequestMapping("login")
	public String login(String userName, String password, Model model, HttpServletResponse response,
			HttpSession session) {
		try {
			if (StringUtils.isEmpty(userName)) {
				throw new BusinessException(100001, "用户名为空");
			}
			if (StringUtils.isEmpty(password)) {
				throw new BusinessException(100002, "密码为空");
			}
			User user = loginService.login(userName, password);
			List<FunctionItem> functionItemList = (List<FunctionItem>) functionItemService
					.getFunctionItem(user.getOperId(), 1, -1, false);
			user.setFunctionItemList(functionItemList);
			CommonUtils.addCookie(response, "loginuser", JsonUtils.jsonSerialization(user), 3600);
		} catch (BusinessException e) {
			logger.info(e);
			model.addAttribute("error", e.getCode());
			model.addAttribute("msg", e.getMessage());
			return LOGIN;
		}
		int count = loginService.getModPwdCount(userName);
		if (count == 0) {
			session.setAttribute("modPwdMsg", "建议修改密码");
		} else {
			session.removeAttribute("modPwdMsg");
		}
		return "redirect:/main";
	}

	@Log(operationType = 6, operationName = "用户登出")
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		CommonUtils.removeCookie(request, response, "loginuser");
		session.invalidate();
		return LOGIN;
	}

	@Log(operationType = 1, operationName = "用户登录")
	@RequestMapping("main")
	public String main(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "main";
	}

	// @Log(operationType = 7, operationName = "密码修改")
	@RequestMapping("pwdupdate")
	public String pwdUpdate(@ModelAttribute("loginUser") User user,
			@RequestParam(required = false, defaultValue = "-1") String oldPassword,
			@RequestParam(required = false, defaultValue = "-1") String newPassword, Model model,
			HttpServletRequest request, HttpSession session) {
		if (!"-1".equals(newPassword)) {
			try {
				loginService.updatePassword(user, oldPassword, newPassword);
				SystemLog systemLog = new SystemLog();
				systemLog.setLogContent("密码修改");
				systemLog.setLogMode(7);
				systemLog.setOperId(user.getOperId());
				systemLog.setOperName(user.getOperName());
				systemLog.setLogIP(CommonUtils.getIpAddress(request));
				logService.saveLog(systemLog);
				model.addAttribute("info", "恭喜您，密码修改成功!");
			} catch (BusinessException be) {
				logger.info(be);
				model.addAttribute("info", "旧密码错误,请重新输入!");
			}
		}
		int count = loginService.getModPwdCount(user.getOperLogin());
		if (count == 0) {
			session.setAttribute("modPwdMsg", "建议修改密码");
		} else {
			session.removeAttribute("modPwdMsg");
		}
		return "system/user_password";
	}

	@RequestMapping("logingo")
	public String logingo(Model model) {
		return LOGIN;
	}

	@RequestMapping("errorgo")
	public String error(Model model) {
		return "error/500";
	}

	@ResponseBody
	@RequestMapping("getModPwdCount")
	public Map<String, Object> getModPwdCount(String username) {
		Map<String, Object> resultMap = new HashMap<>();
		int count = loginService.getModPwdCount(username);
		if (count == 0) {
			resultMap.put("error", "100012");
			resultMap.put("msg", "建议修改密码");
		}
		return resultMap;
	}
}
