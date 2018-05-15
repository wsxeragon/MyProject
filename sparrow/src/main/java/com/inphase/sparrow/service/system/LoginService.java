package com.inphase.sparrow.service.system;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inphase.sparrow.base.handler.BusinessException;
import com.inphase.sparrow.base.util.PropertyUtil;
import com.inphase.sparrow.dao.system.UserDao;
import com.inphase.sparrow.entity.system.User;

/**      
 * @Description:登录相关的服务层操作
 * @author: sunchao
 */
@Service
public class LoginService {

	@Autowired
	private UserDao userDao;

	public User login(String userName, String password) {
		List<User> user = userDao.getUserByName(userName);
		if (user.size() == 1) {
			if (!DigestUtils.md5Hex(password).equalsIgnoreCase(user.get(0).getOperPassword())) {
				throw new BusinessException(100003, "密码不正确");
			}
			int loginCount = userDao.getLoginCount(userName);
			PropertyUtil propertyUtil = new PropertyUtil("sparrow.properties");
			if (loginCount > Integer.valueOf(propertyUtil.getProperty("log_count"))) {
				throw new BusinessException(100011, "登陆次数过于频发");
			}
		} else {
			throw new BusinessException(100004, "用户不存在");
		}
		return user.get(0);
	}

	/**
	 * @Description 密码修改
	 * @param user
	 * @param newPassword
	 */
	public void updatePassword(User user, String oldPassword, String newPassword) {
		List<User> userTemp = userDao.getUserById(user.getOperId());
		if (!DigestUtils.md5Hex(oldPassword).equalsIgnoreCase(userTemp.get(0).getOperPassword())) {
			throw new BusinessException(200002, "旧密码不正确。");
		}
		userDao.resetPassword(user.getOperId(), DigestUtils.md5Hex(newPassword));
	}

	public int getModPwdCount(String username) {
		return userDao.getModPwdCount(username);
	}

}
