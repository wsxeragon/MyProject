package com.wsx.springbootTest.service;

import java.util.List;

import com.wsx.springbootTest.domain.SysUser;
import com.wsx.springbootTest.domain.User;

public interface UserService {

	public List<User> findUserInfo(String id);

	public SysUser findByUserName(String username);
}
