package com.wsx.springbootTest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wsx.springbootTest.domain.SysPermission;
import com.wsx.springbootTest.domain.SysUser;

public interface PermissionDao {
	SysUser findByUsername(@Param("username") String username);

	List<SysPermission> findAll();
}
