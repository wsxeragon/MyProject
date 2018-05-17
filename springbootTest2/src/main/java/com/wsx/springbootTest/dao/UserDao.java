package com.wsx.springbootTest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.wsx.springbootTest.domain.Customer;
import com.wsx.springbootTest.domain.User;

@Component
public interface UserDao {
	public List<User> findUserInfo(@Param("id") String id);

	public int addUser(@Param("user") User user);

	public User getUser(@Param("id") String id);

	public List<User> getUserList();

	public int deleteUser(@Param("id") String id);

	public int updateUser(@Param("user") User user);

	public List<Customer> getPhoneAndNickname(@Param("start") int start, @Param("pageSize") int pageSize);
}
