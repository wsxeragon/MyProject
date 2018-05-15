package com.inphase.sparrow.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.inphase.sparrow.base.BaseDao;
import com.inphase.sparrow.entity.system.Area;

/**      
 * @Description:地区的数据库操作
 * @author: sunchao
 */
@Repository
public class AreaDao extends BaseDao{

	public List<Area> listAllArea(long operId){
		String sql = "SELECT a.`area_id`,`area_fatherID` areaFatherId,`area_cityCode` areaCityCode,`area_name`,"
				+ "if(b.area_id is null,'0','1') checked "
				+ "FROM `s_area` a LEFT JOIN `s_areaopermap` b ON a.`area_id`=b.`area_id` AND b.`oper_id`=:operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap, 
				new BeanPropertyRowMapper<Area>(Area.class));
		
	}
	
	public List<Area> listAreaByParentId(long parentId){
		String sql = "SELECT `area_id`,`area_fatherID` areaFatherId,`area_cityCode` areaCityCode,`area_name` "
				+ "FROM `s_area` WHERE area_fatherId=:areaFatherId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("areaFatherId", parentId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap, 
				new BeanPropertyRowMapper<Area>(Area.class));
	}
	
	/**
	 * @Description 根据用户id获取用户的个管理地区
	 * @param operId
	 * @return
	 */
	public List<Area> listAreaByOperId(long operId){
		String sql = "SELECT a.`area_id`,`area_fatherId` areaFatherId, `area_citycode` areaCityCode,`area_name` FROM `s_area` a "
				+ "INNER JOIN `s_areaopermap` b ON a.`area_id` = b.`area_id` WHERE b.`oper_id`=:operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap, 
				new BeanPropertyRowMapper<Area>(Area.class));
	}
}
