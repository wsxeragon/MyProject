package cn.inphase.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.inphase.domain.User;

@Component
public interface MyMapper {
	public Map<String, Object> selectById(String id);

	public User selectUserWithAddressById(String id);

	public User selectUserWithAddressAndOrder(String id);

}
