package com.inphase.sparrow.controller.system;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.inphase.sparrow.base.annotation.Log;
import com.inphase.sparrow.base.util.FileUtil;
import com.inphase.sparrow.entity.ResponseMessage;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.Role;
import com.inphase.sparrow.entity.system.User;
import com.inphase.sparrow.service.system.AreaService;
import com.inphase.sparrow.service.system.FunctionItemService;
import com.inphase.sparrow.service.system.RoleService;
import com.inphase.sparrow.service.system.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	public static final String USER_LIST = "system/user_list";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private FunctionItemService functionItemService;

	@RequestMapping("main")
	public String main(@ModelAttribute("loginUser") User uc, HttpSession session) {
		List<String> buttons = (List<String>) session.getAttribute("USER_LIST_BUTTONS");
		if (buttons == null) {
			session.setAttribute("USER_LIST_BUTTONS", userService.getButtonsByPage(uc.getOperId(), 2));
		}
		return USER_LIST;
	}

	/**
	 * @Description 保存用户
	 * @param request 
	 * @param user 待保存的用户信息
	 * @param file 上传的头像文件
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("saveuser")
	@Log(operationName = "添加用户", operationType = 2)
	public String saveUser(User user, @RequestParam("headpic") CommonsMultipartFile file) throws IOException {
		String[] filepath = FileUtil.upload("image", 0, file);
		if (null != filepath[0]) {
			user.setOperHeadpic(filepath[0]);
		}
		userService.saveUserTran(user);
		return USER_LIST;
	}

	/**
	 * @Description 保存用户权限关联关系
	 * @param operId 用户id
	 * @param roleIds 权限组id 以逗号分割
	 * @return
	 */
	@RequestMapping("saveuserrole")
	@Log(operationName = "添加用户角色关联", operationType = 2)
	@ResponseBody
	public ResponseMessage<Void> saveUserRoleMap(long operId, String roleIds) {
		userService.saveUserRoleMap(operId, roleIds);
		return new ResponseMessage<Void>(200, "添加用户成功。");
	}

	/**
	 * @Description 更新用户信息
	 * @param request
	 * @param user
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("update")
	@Log(operationName = "修改用户", operationType = 3)
	public String updateUser(User user, @RequestParam("headpic") CommonsMultipartFile file) {
		String[] filepath = FileUtil.upload("image", 0, file);
		if (null != filepath[0]) {
			user.setOperHeadpic(filepath[0]);
		}
		userService.updateUser(user);
		return USER_LIST;
	}

	/**
	 * @Description 重置密码
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resetPassword")
	@Log(operationName = "用户密码重置", operationType = 3)
	@ResponseBody
	public ResponseMessage<Void> resetPassword(User user) {
		userService.resetPassword(user);
		return new ResponseMessage<Void>(200, "重置密码成功。");
	}

	/**
	 * @Description 删除用户信息
	 * @param operId
	 * @return
	 */
	@RequestMapping("remove")
	@Log(operationName = "删除用户", operationType = 4)
	@ResponseBody
	public ResponseMessage<Void> removeUser(long operId) {
		userService.removeUserTran(operId);
		return new ResponseMessage<Void>(200, "删除用户成功。");
	}

	/**
	 * @Description 根据用户ID获取用户信息
	 * @param operId 用户ID
	 * @param type 操作类型 1：新增 2：编辑 3:查看 4:重置密码
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("get")
	public String getUser(@RequestParam(required = false, defaultValue = "-1") long operId, int type, Model model) {
		User user;
		if (type != 1) {
			user = userService.getUserById(operId);
			model.addAttribute("user", user);
		}
		if (type == 1 || type == 2) {
			// 获取权限组
			List<Role> roleList = roleService.listAllRole(operId);
			model.addAttribute("rolelist", roleList);

			// 获取可管理地区树
			String areaTree = areaService.listAllArea(operId);
			model.addAttribute("areaTree", areaTree);
		}
		if (type == 3) {
			String functionItem = (String) functionItemService.getFunctionItem(operId, 1, -1, true);
			model.addAttribute("functionTree", functionItem);
			String area = areaService.listAreaByOperId(operId);
			model.addAttribute("areaTree", area);
			return "system/fragment/user_show";
		} else if (type == 4) {
			return "system/fragment/user_password_reset";
		}
		return "system/user_edit";
	}

	/**
	 * @Description 获取用户列表数据
	 * @param tableParam
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public TableMessage<List<User>> listUser(TableParam tableParam, HttpServletRequest request) {
		// 查询条件参数处理
		Map<String, Object> searchCondition = new HashMap<String, Object>();
		searchCondition.put("operName", request.getParameter("operName"));
		searchCondition.put("operLogin", request.getParameter("operLogin"));
		searchCondition.put("operPhone", request.getParameter("operPhone"));
		searchCondition.put("startTime", request.getParameter("startTime"));
		searchCondition.put("endTime", request.getParameter("endTime"));
		searchCondition.put("operStatus", request.getParameter("operStatus"));
		tableParam.setSearchCondition(searchCondition);
		return userService.listUser(tableParam);
	}

	/**
	 * 
	* @Title: validate
	* @author zuoyc 
	* @Description: 验证登录名是否已经注册 
	* @param operLogin
	* @return 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping("validateOperLogin")
	@ResponseBody
	public boolean validate(@RequestParam String operLogin,
			@RequestParam(required = false, defaultValue = "-1") long operId) {
		// 编辑用户信息是不用验证，因为登录名不可更改
		if (operId != -1) {
			return true;
		}
		List<User> userTemp = userService.getUserByName(operLogin);
		if (userTemp.size() == 1) {
			return false;
		}
		return true;
	}
}
