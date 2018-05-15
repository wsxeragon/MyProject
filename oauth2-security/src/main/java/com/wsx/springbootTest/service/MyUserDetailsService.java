package com.wsx.springbootTest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wsx.springbootTest.dao.PermissionDao;
import com.wsx.springbootTest.dao.RoleDao;
import com.wsx.springbootTest.domain.MyUserDetails;
import com.wsx.springbootTest.domain.SysPermission;
import com.wsx.springbootTest.domain.SysUser;

//自定义权限设置过程  spring会自动调用
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	RoleDao roleDao;
	@Autowired
	PermissionDao permissionDao;

	// 查询数据库中用户权限和密码
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据用户名查询用户详情
		SysUser user = permissionDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		// 用于存储用户权限
		List<GrantedAuthority> authList = new ArrayList<>();
		// 添加用户拥有的权限
		for (SysPermission sp : user.getPermissions()) {
			authList.add(new SimpleGrantedAuthority(sp.getName()));
		}
		// return new User(username, user.getPassword(), authList);
		return new MyUserDetails(username, user.getPassword(), user.getPhone(), authList);
	}

}
