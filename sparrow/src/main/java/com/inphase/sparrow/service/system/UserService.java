package com.inphase.sparrow.service.system;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inphase.sparrow.base.handler.BusinessException;
import com.inphase.sparrow.dao.system.UserDao;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.User;

/**      
 * @Description:用户的增删改查操作
 * @author: sunchao
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * @Description 添加用户操作
	 * @param user  用户数据
	 * @throws Exception
	 */
	public void saveUserTran(User user) {

		List<User> userTemp = getUserByName(user.getOperLogin());
		if (userTemp.size() == 1) {
			throw new BusinessException(200001, "该用户已经存在。");
		}
		user.setOperPassword(DigestUtils.md5Hex(user.getOperPassword()));
		long operId = userDao.saveUser(user);

		saveUserRoleMap(operId, user.getOperRole());
		saveUserAreaMap(operId, user.getOperArea());
	}

	/**
	 * @Description 更新用户操作
	 * @param user  用户数据
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	public void updateUser(User user) {
		userDao.updateUser(user);
		saveUserRoleMap(user.getOperId(), user.getOperRole());
		saveUserAreaMap(user.getOperId(), user.getOperArea());
	}

	/**
	 * @Description 密码充值
	 * @param user
	 */
	public void resetPassword(User user) {
		userDao.resetPassword(user.getOperId(), DigestUtils.md5Hex(user.getOperPassword()));
	}

	/**
	 * @Description 删除用户操作
	 * @param operId 用户ID
	 */
	public void removeUserTran(long operId) {
		userDao.removeUser(operId);
		userDao.removeUserAreaMap(operId);
		userDao.removeUserRoleMap(operId);
	}

	/**
	 * @Description 根据用户id获取用户信息
	 * @param operId 用户id
	 * @return 用户信息
	 */
	public User getUserById(long operId) {
		List<User> user = userDao.getUserById(operId);
		return user.get(0);
	}

	/**
	 * @Description 用户角色关联关系操作
	 * @param operId 用户id
	 * @param roleIds 角色id
	 */
	public void saveUserRoleMap(long operId, String roleIds) {
		if (!StringUtils.isEmpty(roleIds)) {
			userDao.removeUserRoleMap(operId);
			String[] roleIdArray = roleIds.split(",");
			Map<String, Object>[] roleIdMap = new HashMap[roleIdArray.length];
			for (int i = 0; i < roleIdArray.length; i++) {
				roleIdMap[i] = new HashMap<String, Object>();
				roleIdMap[i].put("operId", operId);
				roleIdMap[i].put("roleId", roleIdArray[i]);
			}

			userDao.saveUserRoleMap(roleIdMap);
		}
	}

	/**
	 * @Description 保存用户和可管理地区的关联关系
	 * @param operId  用户id
	 * @param areaIds 可管理地区字符串，以逗号隔开
	 */
	public void saveUserAreaMap(long operId, String areaIds) {
		if (!StringUtils.isEmpty(areaIds)) {
			userDao.removeUserAreaMap(operId);
			String[] areaIdArray = areaIds.split(",");
			Map<String, Object>[] areaIdMap = new HashMap[areaIdArray.length];

			for (int i = 0; i < areaIdArray.length; i++) {
				areaIdMap[i] = new HashMap<String, Object>();
				areaIdMap[i].put("operId", operId);
				areaIdMap[i].put("areaId", areaIdArray[i]);
			}
			userDao.saveUserAreaMap(areaIdMap);
		}
	}

	/**
	 * @Description 获取列表用户数据
	 * @param tableParam 列表参数
	 * @return
	 */
	public TableMessage<List<User>> listUser(TableParam tableParam) {
		return userDao.listUser(tableParam);
	}

	/**
	 * @Description 根据角色id获取用户列表
	 * @param roleId
	 * @return
	 */
	public List<User> getUserByRoleId(long roleId) {
		return userDao.getUserByRoleId(roleId);
	}

	/**
	 * 
	* @Title: getUserByName
	* @author zuoyc 
	* @Description: 根据用户名获取用户
	* @param operLogin
	* @return 
	* @return List<User>    返回类型 
	* @throws
	 */
	public List<User> getUserByName(String operLogin) {
		return userDao.getUserByName(operLogin);
	}

	/**
	 * 
	* @Title: getButtonsByPage
	* @author zuoyc 
	* @Description: 获取当前页面下用户可显示得按钮
	* @param operLogin
	* @return 
	* @return List<User>    返回类型 
	* @throws
	 */
	public List<String> getButtonsByPage(long operId, int pageId) {
		return userDao.getButtonsByPage(operId, pageId);
	}

}
