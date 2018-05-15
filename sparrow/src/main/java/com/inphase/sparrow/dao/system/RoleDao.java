package com.inphase.sparrow.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.inphase.sparrow.base.BaseDao;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.Role;

/**      
 * @Description:jdbctemplate版本的RoleDao
 * @author: sunchao
 */
@Repository
public class RoleDao extends BaseDao{

	/**
	 * @Description 根据角色ID后去角色信息
	 * @param roleId
	 * @return
	 */
	public Role getRole(long roleId) {
		String sql = "SELECT role_id,role_name,IFNULL(role_description,'')role_description,"
				+ "role_isprivate FROM s_role WHERE role_id=:roleId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, 
				new BeanPropertyRowMapper<Role>(Role.class));
	}

	
	/**
	 * @Description 获取角色列表
	 * @param tableParam  列表参数
	 * @param searchCondition  查询条件参数
	 * @return
	 */
	public TableMessage<List<Role>> listRole(TableParam tableParam, Map<String,Object> searchCondition) {
		String sql = "SELECT role_id,role_name,IFNULL(role_description,'') role_description,"
				+ "role_isprivate FROM s_role where 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(searchCondition.get("roleName"))){
			sql += " AND role_name LIKE :roleName";
			paramMap.put("roleName", "%"+searchCondition.get("roleName")+"%");
		}		
		if(tableParam.getiSortCol()==1){
			sql += " ORDER BY role_name "+tableParam.getsSortDir();
		}else{
			sql += " ORDER BY role_id desc";
		}		
		sql += " LIMIT :start,:size";
		paramMap.put("start", tableParam.getiDisplayStart());
		paramMap.put("size", tableParam.getiDisplayLength());
		
		List<Role> role = getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap, 
				new BeanPropertyRowMapper<Role>(Role.class));
		int count = getJdbcTemplate().getJdbcTemplate().queryForObject("SELECT FOUND_ROWS()", Integer.class);
		return new TableMessage<List<Role>>(count, tableParam.getsEcho(), role);
	}

	/**
	 * @Description 保存角色信息
	 * @param role
	 */
	public void saveRole(Role role) {
		String sql = "Insert into `s_role`(role_name,role_description,role_isprivate) "
				+ "values(:roleName,:roleDescription,:roleIsprivate)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleName", role.getRoleName());
		paramMap.put("roleDescription", role.getRoleDescription());
		paramMap.put("roleIsprivate", role.getRoleIsprivate());
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);

	}

	/**
	 * @Description 更新角色信息
	 * @param role
	 */
	public void updateRole(Role role) {
		String sql = "UPDATE `s_role` SET `role_name` = :roleName,`role_description` = :roleDescription,"
				+ "`role_isprivate` = :roleIsprivate WHERE `role_id` = :roleId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleName", role.getRoleName());
		paramMap.put("roleDescription", role.getRoleDescription());
		paramMap.put("roleIsprivate", role.getRoleIsprivate());
		paramMap.put("role_id", role.getRoleId());
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);	
	}

	/**
	 * @Description 删除角色
	 * @param roleId
	 */
	public void removeRole(long roleId) {
		String sql = "DELETE FROM `s_role` WHERE `role_id` = :roleId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("role_id", roleId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);	
	}

	/**
	 * @Description 删除角色与权限功能关联关系
	 * @param roleId
	 */
	public void removeRoleFunctionItemMap(long roleId) {
		String sql = "DELETE FROM `s_functionmap` WHERE `role_id` = :roleId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("role_id", roleId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
		
	}

	/**
	 * @Description 保存角色功能关联信息
	 * @param paramList
	 */
	public void saveRoleFunctionMap(List<Map<String, Long>> paramList) {
		String sql = "INSERT INTO `s_functionmap` (`role_id`, `funn_id`) "
				+ "VALUES (:roleId, :functionId)";
		Map<?, ?>[] paramMapArray = new HashMap<?, ?>[paramList.size()];
		Map<String,Long> paramMap;
		for(int i = 0; i < paramList.size(); i++){
			paramMap = new HashMap<String, Long>();
			paramMap.put("roleId", paramList.get(i).get("roleId"));
			paramMap.put("functionId", paramList.get(i).get("functionId"));
			paramMapArray[i] = paramMap;
		}
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(paramMapArray);
		getJdbcTemplate().getNamedParameterJdbcTemplate().batchUpdate(sql, batchArgs);		
	}
}
