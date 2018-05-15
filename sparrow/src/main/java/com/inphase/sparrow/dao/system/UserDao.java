package com.inphase.sparrow.dao.system;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.inphase.sparrow.base.BaseDao;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.User;

@Repository
public class UserDao extends BaseDao {

	private static final Logger logger = Logger.getLogger("BaseDao");

	/**
	 * @Description 根据用户登录名获取用户信息
	 * @param userName 用户登录名
	 * @return 用户信息
	 */
	public List<User> getUserByName(String operLogin) {
		String sql = "SELECT `oper_id`,`oper_name`,`oper_login`,`oper_password` ,FROM_UNIXTIME(`oper_createdate`,'%Y-%m-%d %T')`oper_createdate`,"
				+ "`oper_status`,`oper_phone`,`oper_sex`,`oper_idcard`,`oper_qq` operQQ,`oper_headpic`,"
				+ "`oper_address`,`oper_email`,`oper_provience`,`oper_city`,`oper_district` "
				+ "FROM `s_operator` where oper_login=:operLogin AND oper_status = 1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operLogin", operLogin);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper<User>(User.class));
	}

	/**
	 * @Description 根据角色id获取用户列表
	 * @param roleId
	 * @return
	 */
	public List<User> getUserByRoleId(long roleId) {
		String sql = "SELECT a.`oper_id`,`oper_name`,`oper_login`,`oper_password` ,FROM_UNIXTIME(`oper_createdate`,'%Y-%m-%d %T')`oper_createdate`,"
				+ "`oper_status`,`oper_phone`,`oper_sex`,`oper_idcard`,`oper_qq` operQQ,`oper_headpic`,"
				+ "`oper_address`,`oper_email`,`oper_provience`,`oper_city`,`oper_district` "
				+ "FROM `s_operator` a INNER JOIN s_functionoperator b ON a.oper_id=b.oper_id where b.role_id=:roleId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper<User>(User.class));
	}

	/**
	 * @Description 新增用户
	 * @param user 用户信息
	 * @throws Exception 用户添加失败，抛出异常
	 */
	public long saveUser(User user) {
		String sql = "INSERT INTO `s_operator` (`oper_name`,`oper_login`,`oper_password`,`oper_createdate`,"
				+ "`oper_status`,`oper_phone`,`oper_sex`,`oper_qq`,`oper_headpic`,"
				+ "`oper_address`,`oper_email`,`oper_provience`,`oper_city`,`oper_district`)"
				+ " VALUES(:operName,:operLogin,:operPassword,UNIX_TIMESTAMP(NOW()),1,:operPhone,"
				+ " :operSex,:operQQ,:operHeadpic,:operAddress,:operEmail,:operProvience,:operCity,:operDistrict)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramSource, keyHolder,
				new String[] { "oper_Id" });
		return (Long) keyHolder.getKey();
	}

	/**
	 * @Description 更新用户信息
	 * @param user 用户新信息
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	public void updateUser(User user) {
		String sql = "UPDATE `s_operator` SET `oper_name` = :operName,`oper_status` = :operStatus,"
				+ "`oper_phone` = :operPhone,`oper_sex` = :operSex,`oper_qq` = :operQQ,"
				+ "`oper_headpic` = :operHeadpic,`oper_address` = :operAddress,`oper_email` = :operEmail,"
				+ "oper_provience = :operProvience,oper_city = :operCity,oper_district = :operDistrict "
				+ "WHERE `oper_id` = :operId";
		Map<String, Object> paramMap = null;
		try {
			paramMap = new HashMap<String, Object>(BeanUtils.describe(user));
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * @Description 修改用户状态
	 * @param state 1.正常 0.禁止
	 * @param operId
	 */
	public void updateUserState(int state, long operId) {
		String sql = "UPDATE `s_operator` SET `oper_status` = :operStatus WHERE `oper_id` = :operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operStatus", state);
		paramMap.put("operId", operId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * @Description 删除用户
	 * @param operId 用户ID
	 */
	public void removeUser(long operId) {
		String sql = "DELETE FROM `s_operator` WHERE `oper_id` = :operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * @Description  根据用户ID获取用户信息
	 * @param operId 用户ID
	 * @return
	 */
	public List<User> getUserById(long operId) {
		String sql = "SELECT `oper_id`,`oper_name`,`oper_login`,`oper_password` ,FROM_UNIXTIME(`oper_createdate`,'%Y-%m-%d %T')`oper_createdate`,"
				+ "`oper_status`,`oper_phone`,`oper_sex`,`oper_qq` operQQ,`oper_headpic`,`oper_address`,"
				+ "`oper_email`,`oper_provience`,`oper_city`,`oper_district` "
				+ "FROM `s_operator` where oper_id=:operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		return getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper<User>(User.class));
	}

	/**
	 * @Description 获取用户列表数据
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public TableMessage<List<User>> listUser(TableParam tableParam) {
		String sql = "SELECT SQL_CALC_FOUND_ROWS `oper_id`,`oper_name`,`oper_login`,`oper_password` ,FROM_UNIXTIME(`oper_createdate`,'%Y-%m-%d %T')`oper_createdate`,"
				+ "`oper_status`,`oper_phone`,`oper_sex`,`oper_idcard`,`oper_qq` operQQ,`oper_headpic`,`oper_address`,`oper_email` "
				+ "FROM `s_operator` WHERE 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 查询条件处理
		if (!StringUtils.isEmpty(tableParam.getSearchCondition().get("operName"))) {
			sql += " AND oper_name LIKE :operName";
			paramMap.put("operName", "%" + tableParam.getSearchCondition().get("operName") + "%");
		}
		if (!StringUtils.isEmpty(tableParam.getSearchCondition().get("operLogin"))) {
			sql += " AND oper_login LIKE :operaLogin";
			paramMap.put("operaLogin", "%" + tableParam.getSearchCondition().get("operLogin") + "%");
		}
		if (!StringUtils.isEmpty(tableParam.getSearchCondition().get("operPhone"))) {
			sql += " AND oper_phone LIKE :operPhone";
			paramMap.put("operPhone", "%" + tableParam.getSearchCondition().get("operPhone") + "%");
		}
		if (!StringUtils.isEmpty(tableParam.getSearchCondition().get("startTime"))) {
			sql += " AND oper_createdate between UNIX_TIMESTAMP(:startTime) AND UNIX_TIMESTAMP(:endTime)";
			paramMap.put("startTime", tableParam.getSearchCondition().get("startTime"));
			paramMap.put("endTime", tableParam.getSearchCondition().get("endTime") + " 23:59:59");
		}
		if (!StringUtils.isEmpty(tableParam.getSearchCondition().get("operStatus"))
				&& Integer.valueOf(tableParam.getSearchCondition().get("operStatus").toString()) != -1) {
			sql += " AND oper_status=:operStatus";
			paramMap.put("operStatus", tableParam.getSearchCondition().get("operStatus"));
		}

		// 排序处理
		switch (tableParam.getiSortCol()) {
		case 1:
			sql += " ORDER BY oper_login :sort";
			break;
		case 2:
			sql += " ORDER BY oper_name :sort";
			break;
		case 4:
			sql += " ORDER BY oper_createdate :sort";
			break;
		case 3:
			sql += " ORDER BY oper_status :sort";
			break;
		default:
			sql += " ORDER BY oper_id desc";
			break;
		}
		paramMap.put("sort", tableParam.getsSortDir());
		sql += " LIMIT :start,:size";

		paramMap.put("start", tableParam.getiDisplayStart());
		paramMap.put("size", tableParam.getiDisplayLength());
		List<User> userList = getJdbcTemplate().getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper<User>(User.class));
		int count = getJdbcTemplate().getJdbcTemplate().queryForObject("SELECT FOUND_ROWS()", Integer.class);
		return new TableMessage<List<User>>(count, tableParam.getsEcho(), userList);
	}

	/**
	 * @Description 添加用户角色关联关系
	 * @param args
	 */
	public void saveUserRoleMap(Map<String, ?>[] args) {
		String sql = "INSERT INTO `s_functionoperator` (`oper_id`, `role_id`) VALUES(:operId, :roleId)";
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(args);
		getJdbcTemplate().getNamedParameterJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	/**
	 * @Description 根据用户id删除用户与角色关联关系
	 * @param operId
	 */
	public void removeUserRoleMap(long operId) {
		String sql = "DELETE FROM `s_functionoperator` WHERE `oper_id` = :operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * @Description 保存用户与地区的关联关系
	 * @param args
	 */
	public void saveUserAreaMap(Map<String, ?>[] args) {
		String sql = "INSERT INTO `s_areaopermap` (`area_id`, `oper_id`) VALUES (:areaId, :operId)";
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(args);
		getJdbcTemplate().getNamedParameterJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	/**
	 * @Description 根据用户id删除用户与可管理地区关联关系
	 * @param operId
	 */
	public void removeUserAreaMap(long operId) {
		String sql = "DELETE FROM `s_areaopermap` WHERE `oper_id` = :operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * @Description 用户密码重置
	 * @param operId
	 * @param password
	 */
	public void resetPassword(long operId, String password) {
		String sql = "UPDATE `s_operator` SET `oper_password` = :operPassword WHERE `oper_id` = :operId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operPassword", password);
		paramMap.put("operId", operId);
		getJdbcTemplate().getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * @Description 查询当天登录次数
	 * @param userName
	 */
	public int getLoginCount(String userName) {
		String sql = "SELECT count(*) FROM s_logfile a LEFT JOIN s_operator b ON a.oper_id = b.oper_id "
				+ "WHERE b.oper_login = :userName AND log_mode='1' AND a.log_datetime >= UNIX_TIMESTAMP(curdate()) AND a.log_datetime < UNIX_TIMESTAMP( date_add(curdate(), INTERVAL 1 DAY) )";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);
		int count = getJdbcTemplate().getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
		return count;
	}

	/**
	 * @Description 查询三个月内密码修改次数
	 * @param userName
	 */
	public int getModPwdCount(String userName) {
		String sql = "SELECT count(*) FROM s_logfile a LEFT JOIN s_operator b ON a.oper_id = b.oper_id"
				+ " WHERE b.oper_login = :userName AND a.log_mode = 7 AND a.log_datetime <= UNIX_TIMESTAMP(date_add(curdate(), INTERVAL 1 DAY)) AND a.log_datetime > UNIX_TIMESTAMP( date_add(curdate(), INTERVAL -3 MONTH) )";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);
		int count = getJdbcTemplate().getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
		return count;
	}

	/**
	 * @Description 查询用户在当前页面下的可显示按钮
	 * @param userName
	 */
	public List<String> getButtonsByPage(long operId, int pageId) {
		String sql = "select a.funn_name from s_functionitem a LEFT JOIN s_functionmap b on a.funn_id = b.funn_id "
				+ "LEFT JOIN s_functionoperator c on c.role_id = b.role_id LEFT JOIN s_operator d on d.oper_id = c.oper_id "
				+ "where d.oper_id = :operId and a.funn_status = 1 and a.funn_type = 2 and a.s_f_funn_id = :pageId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operId", operId);
		paramMap.put("pageId", pageId);
		List<String> list = getJdbcTemplate().getNamedParameterJdbcTemplate().queryForList(sql, paramMap, String.class);
		return list;
	}
}
