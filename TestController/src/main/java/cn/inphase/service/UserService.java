package cn.inphase.service;

import java.util.List;

import cn.inphase.domain.Customer;
import cn.inphase.domain.User4;

public interface UserService {

	public List<Customer> getPhoneAndNickname(String flag, int pageNo, int pageSize);

	public List<Customer> getPhoneAndNicknameTemp(String flag, int pageNo, int pageSize);

	public int selectCount(String flag);

	public int updateFlagById(String id, String flag);

	public int updateFlag(List<String> list, String flag);

	public boolean insert(User4 user4);
}
