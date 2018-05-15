package com.wsx.springbootTest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wsx.springbootTest.domain.User;

public interface UserDao {
	public List<User> findUserInfo(@Param("id") String id);

}
