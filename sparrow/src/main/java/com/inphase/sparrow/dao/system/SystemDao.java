package com.inphase.sparrow.dao.system;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.inphase.sparrow.base.BaseDao;
import com.inphase.sparrow.entity.system.SystemLog;

/**      
 * @Description:系统日志数据库操作
 * @author: sunchao
 */
@Repository
public class SystemDao extends BaseDao{
	
	private static final Logger logger = Logger.getLogger("SystemDao");

	public void addSystemLog(SystemLog systemLog) {
		String sql = "INSERT INTO `s_logfile` (`oper_id`,`log_datetime`,`log_mode`,`log_content`,`log_ip`, oper_name) "
				+ "VALUES(:operId, UNIX_TIMESTAMP(now()), :logMode, :logContent, :logIP', :operName)";
		Map<String, Object> paramMap = null;
		try {
			paramMap = new HashMap<String,Object>(BeanUtils.describe(systemLog));
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
		
	}
}
