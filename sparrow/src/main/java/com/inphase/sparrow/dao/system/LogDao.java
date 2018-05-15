package com.inphase.sparrow.dao.system;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.inphase.sparrow.base.BaseDao;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.SystemLog;

/**      
 * @Description:日志的增删改查
 * @author: sunchao
 */
@Repository
public class LogDao extends BaseDao{
	
	private static final Logger logger = Logger.getLogger("LogDao");

	public void saveLog(SystemLog systemLog) {
		String sql = "INSERT INTO `s_logfile`(`oper_id`,`log_datetime`,`log_mode`,`log_content`,`log_ip`,oper_name) "
				+ "VALUES(:operId,  UNIX_TIMESTAMP(),  :logMode,  :logContent,  :logIP, :operName)";
		Map<String, Object> paramMap = null;
		try {
			paramMap = new HashMap<String, Object>(BeanUtils.describe(systemLog));
		} catch (IllegalAccessException | InvocationTargetException	| NoSuchMethodException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}
	
	public TableMessage<List<SystemLog>> listLog(TableParam tableParam){
		String sql = "SELECT SQL_CALC_FOUND_ROWS `log_id`,`oper_id`,oper_name,FROM_UNIXTIME(`log_datetime`,'%Y-%m-%d %T') log_datetime,"
				+ "`log_mode`,`log_content`,`log_ip` logIP FROM `s_logfile` WHERE 1=1 ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(tableParam.getSearchCondition().get("operName"))){
			sql += "AND oper_name LIKE :operName ";
			paramMap.put("operName", "%"+tableParam.getSearchCondition().get("operName")+"%");
		}
		if(Integer.valueOf(tableParam.getSearchCondition().get("logMode").toString()) != -1){
			sql += "AND log_mode = :logMode ";
			paramMap.put("logMode", tableParam.getSearchCondition().get("logMode"));
		}
		if(!StringUtils.isEmpty(tableParam.getSearchCondition().get("startTime"))){
			sql += "AND log_datetime between UNIX_TIMESTAMP(:startTime) AND UNIX_TIMESTAMP(:endTime) ";
			paramMap.put("startTime", tableParam.getSearchCondition().get("startTime"));
			paramMap.put("endTime", tableParam.getSearchCondition().get("endTime")+" 23:59:59");
		}
		sql += "ORDER BY log_id desc LIMIT :start, :size";
		paramMap.put("start", tableParam.getiDisplayStart());
		paramMap.put("size", tableParam.getiDisplayLength());
		List<SystemLog> logList = getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap, 
				new BeanPropertyRowMapper<SystemLog>(SystemLog.class));
		int count = getJdbcTemplate().getJdbcTemplate().queryForObject("SELECT FOUND_ROWS()", Integer.class);
		return new TableMessage<List<SystemLog>>(count, tableParam.getsEcho(), logList);
	}
}
