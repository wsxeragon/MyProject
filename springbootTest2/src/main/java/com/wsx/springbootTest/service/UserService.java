package com.wsx.springbootTest.service;

import java.util.List;

import com.wsx.springbootTest.domain.Customer;
import com.wsx.springbootTest.domain.User;

public interface UserService {

	public List<User> findUserInfo(String id);

	public List<User> getUserList();

	public User getUser(String id);

	public boolean addUser(User user);

	public boolean deleteUser(String string);

	public boolean updateUser(User oldUser);

	public List<Customer> getPhoneAndNickname(int pageNo, int pageSize);
}
