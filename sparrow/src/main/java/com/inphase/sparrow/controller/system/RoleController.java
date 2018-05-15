package com.inphase.sparrow.controller.system;

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

import com.inphase.sparrow.base.annotation.Log;
import com.inphase.sparrow.entity.ResponseMessage;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.Role;
import com.inphase.sparrow.entity.system.User;
import com.inphase.sparrow.service.system.FunctionItemService;
import com.inphase.sparrow.service.system.RoleService;
import com.inphase.sparrow.service.system.UserService;

/**      
 * @Description: 角色控制层
 * @author: sunchao
 */
@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionItemService functionItemService;

	@Autowired
	private UserService userService;

	@RequestMapping("main")
	public String main(@ModelAttribute("loginUser") User uc, Model model, HttpServletRequest request,
			HttpSession session) {
		List<String> buttons = (List<String>) session.getAttribute("ROLE_LIST_BUTTONS");
		if (buttons == null) {
			session.setAttribute("ROLE_LIST_BUTTONS", userService.getButtonsByPage(uc.getOperId(), 3));
		}
		return "system/role_list";
	}

	/**
	 * @Description 保存角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping("save")
	@Log(operationName = "添加角色", operationType = 2)
	@ResponseBody
	public ResponseMessage<Void> saveRole(Role role) {
		roleService.saveRoleTran(role);
		return new ResponseMessage<Void>(200, "添加成功。");
	}

	/**
	 * @Description 更新角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping("update")
	@Log(operationName = "更新角色", operationType = 3)
	@ResponseBody
	public ResponseMessage<Void> updateRole(Role role) {
		roleService.updateRole(role);
		return new ResponseMessage<Void>(200, "更新成功。");

	}

	/**
	 * @Description 删除角色信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("remove")
	@Log(operationName = "删除角色", operationType = 4)
	@ResponseBody
	public ResponseMessage<Void> removeRole(long roleId) {
		roleService.removeRoleTran(roleId);
		return new ResponseMessage<Void>(200, "删除成功。");
	}

	/**
	 * @Description 获取单个角色信息
	 * @param roleId
	 * @param type
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("get")
	public String getRole(@RequestParam(required = false, defaultValue = "-1") long roleId, int type, Model model,
			HttpServletRequest request) {
		if (type != 1) {
			Role role = roleService.getRole(roleId);
			model.addAttribute("role", role);
		}
		model.addAttribute("functionItem", functionItemService.getFunctionItem(roleId));
		return "system/fragment/role_edit";
	}

	/**
	 * @Description 获取权限组下的用户
	 * @param roleId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("getUser")
	public String getUserByRole(long roleId, Model model) {
		model.addAttribute("roleUserList", userService.getUserByRoleId(roleId));
		return "system/fragment/role_userlist";
	}

	/**
	 * @Description 获取列表角色数据
	 * @param tableParam
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public TableMessage<List<Role>> listRole(TableParam tableParam, HttpServletRequest request) {
		// 查询条件
		Map<String, Object> searchCondition = new HashMap<String, Object>();
		searchCondition.put("roleName", request.getParameter("roleName"));
		tableParam.setSearchCondition(searchCondition);
		return roleService.listRole(tableParam);
	}
}
