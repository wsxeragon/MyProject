package cn.inphase.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.inphase.dao.MyMapper;
import cn.inphase.domain.User;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private MyMapper Testdao;

	@Override
	public Map<String, Object> selectById(String id) {
		return Testdao.selectById(id);
	}

	@Override
	public User selectUserWithAddressById(String id) {
		return Testdao.selectUserWithAddressById(id);
	}

	@Override
	public User selectUserWithAddressAndOrder(String id) {
		return Testdao.selectUserWithAddressAndOrder(id);
	}

}
