package com.wsx.springbootTest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsx.springbootTest.dao.PermissionDao;
import com.wsx.springbootTest.dao.RoleDao;
import com.wsx.springbootTest.dao.UserDao;
import com.wsx.springbootTest.domain.SysUser;
import com.wsx.springbootTest.domain.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public List<User> findUserInfo(String id) {
		return userDao.findUserInfo(id);
	}

	@Override
	public SysUser findByUserName(String username) {
		return roleDao.findByUsername(username);
	}
}
