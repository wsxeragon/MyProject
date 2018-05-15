package cn.inphase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.inphase.dao.UserDao;
import cn.inphase.domain.Customer;
import cn.inphase.domain.User4;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<Customer> getPhoneAndNickname(String flag, int pageNo, int pageSize) {
		return userDao.getPhoneAndNickname(flag, (pageNo - 1) * pageSize, pageSize);
	}

	@Override
	public int selectCount(String flag) {
		return userDao.selectCount(flag);
	}

	@Override
	public int updateFlagById(String id, String flag) {
		return userDao.updateFlagById(id, flag);
	}

	@Override
	public int updateFlag(List<String> list, String flag) {
		return userDao.updateFlag(list, flag);
	}

	@Override
	public List<Customer> getPhoneAndNicknameTemp(String flag, int pageNo, int pageSize) {
		System.out.println(flag + "   " + (pageNo - 1) * pageSize + "  " + pageSize);
		return userDao.getPhoneAndNicknameTemp(flag, (pageNo - 1) * pageSize, pageSize);
	}

	@Override
	public boolean insert(User4 user4) {
		return userDao.insert(user4) == 1;
	}

}
