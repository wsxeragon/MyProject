package com.inphase.sparrow.dao.system;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.Role;

/**      
 * @Description:用户角色的数据库操作使用mybaits
 * @author: sunchao
 */
/* MyBatis使用时请打开这个注解
 * 
 */
@MapperScan
public interface RoleDaoWithMyBatis {

	public Role getRole(long roleId);
	
	public List<Role> listRolePage(TableParam tableParam);
	
	public List<Role> listAllRole(long operId); 
	
	public int getExistsOperator(long roleId);
	
	public void saveRole(Role role);
	
	public void updateRole(Role role);
	
	public void removeRole(long roleId);
	
	public void removeRoleFunctionItemMap(long roleId);
	
	public void saveRoleFunctionMap(List<Map<String,Long>> paramList);
}
