package cn.inphase.service;

import java.util.Map;

import cn.inphase.domain.User;

public interface TestService {
	public Map<String, Object> selectById(String id);

	public User selectUserWithAddressById(String id);

	public User selectUserWithAddressAndOrder(String id);
}
