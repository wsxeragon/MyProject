package com.wsx.springbootTest.dao;

import org.apache.ibatis.annotations.Param;

import com.wsx.springbootTest.domain.SysUser;

public interface RoleDao {

	SysUser findByUsername(@Param("username") String username);
}
