package cn.inphase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import cn.inphase.domain.Customer;
import cn.inphase.domain.User4;

@Component
public interface UserDao extends BaseDao<User4> {
	public List<Customer> getPhoneAndNickname(@Param("flag") String flag, @Param("start") int start,
			@Param("pageSize") int pageSize);

	public List<Customer> getPhoneAndNicknameTemp(@Param("flag") String flag, @Param("start") int start,
			@Param("pageSize") int pageSize);

	public int selectCount(@Param("flag") String flag);

	public int updateFlagById(@Param("id") String id, @Param("flag") String flag);

	public int updateFlag(@Param("list") List<String> list, @Param("flag") String flag);
}
