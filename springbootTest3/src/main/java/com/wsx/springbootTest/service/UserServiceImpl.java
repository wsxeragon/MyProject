package com.wsx.springbootTest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsx.springbootTest.dao.UserDao;
import com.wsx.springbootTest.domain.Customer;
import com.wsx.springbootTest.domain.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findUserInfo(String id) {
		return userDao.findUserInfo(id);
	}

	@Override
	public List<User> getUserList() {
		return userDao.getUserList();
	}

	@Override
	public User getUser(String id) {
		return userDao.getUser(id);
	}

	@Override
	public boolean addUser(User user) {
		return userDao.addUser(user) == 1;
	}

	@Override
	public boolean deleteUser(String id) {
		return userDao.deleteUser(id) == 1;
	}

	@Override
	public boolean updateUser(User oldUser) {
		return userDao.updateUser(oldUser) == 1;
	}

	@Override
	public List<Customer> getPhoneAndNickname(int pageNo, int pageSize) {
		return userDao.getPhoneAndNickname((pageNo - 1) * pageSize, pageSize);
	}
}
