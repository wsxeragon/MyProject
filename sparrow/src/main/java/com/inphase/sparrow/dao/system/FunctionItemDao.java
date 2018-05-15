package com.inphase.sparrow.dao.system;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.inphase.sparrow.base.BaseDao;
import com.inphase.sparrow.entity.system.FunctionItem;

/**      
 * @Description:功能菜单相关数据库操作
 * @author: sunchao
 */
@Repository
public class FunctionItemDao extends BaseDao{

	/**
	 * @Description 根据用户id、功能菜单类型、父id获取功能菜单列表
	 * @param operId 用户id
	 * @param type   功能类型
	 * @param parentId 父Id
	 * @return
	 */
	public List<FunctionItem> getFunctionItem(long operId,int type,long parentId){
		String sql = "SELECT DISTINCT a.funn_id, s_f_funn_id parentId,funn_name,funn_type,funn_description,funn_sort,funn_url,"
				+ "funn_image FROM `s_functionitem` a INNER JOIN `s_functionmap` b ON a.`funn_id`=b.`funn_id` "
				+ "WHERE funn_status = 1 AND funn_type = :type AND EXISTS (SELECT 1 FROM `s_operator` c INNER JOIN `s_functionoperator` d "
				+ "ON c.`oper_id` = d.`oper_id` WHERE b.`role_id`=d.`role_id` AND c.`oper_id`=:operId)";
		if(parentId != -1){
			sql += " AND s_f_funn_id=:parentId";
		}
		sql += " order by funn_sort";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("operId", operId);
		paramMap.put("type", type);
		paramMap.put("parentId", parentId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().
				query(sql, paramMap, new BeanPropertyRowMapper<FunctionItem>(FunctionItem.class));
	}
	
	/**
	 * @Description 根据角色id，获取功能权限
	 * @param roleId
	 * @return
	 */
	public List<FunctionItem> listFunctionItem(long roleId){
		String sql = "SELECT a.funn_id, s_f_funn_id parentId,funn_name,funn_type,funn_description,funn_sort,"
				+ "funn_url,funn_image,IF(b.funn_id is null, 0, 1) funn_selected FROM `s_functionitem` a "
			+ "LEFT JOIN `s_functionmap` b ON a.`funn_id`=b.`funn_id` AND b.`role_id`= :roleId  WHERE a.`funn_status`=1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().
				query(sql, paramMap, new BeanPropertyRowMapper<FunctionItem>(FunctionItem.class));
	}
}
