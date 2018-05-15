package cn.inphase.tool;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

public class JDBCUtils {

	// 表示定义数据库的用户名
	private static String USERNAME;

	// 定义数据库的密码
	private static String PASSWORD;

	// 定义数据库的驱动信息
	private static String DRIVER;

	// 定义访问数据库的地址
	private static String URL;

	// 定义数据库的链接
	private Connection connection;

	// 定义sql语句的执行对象
	private PreparedStatement pstmt;

	// 定义查询返回的结果集合
	private ResultSet resultSet;

	static {
		// 加载数据库配置信息，并给相关的属性赋值
		// loadConfig();
	}

	/**
	 * 加载数据库配置信息，并给相关的属性赋值
	 */
	public static void loadConfig() {
		try {
			InputStream inStream = JDBCUtils.class.getResourceAsStream("/jdbc.properties");
			Properties prop = new Properties();
			prop.load(inStream);
			USERNAME = prop.getProperty("jdbc.username");
			PASSWORD = prop.getProperty("jdbc.password");
			DRIVER = prop.getProperty("jdbc.driver");
			URL = prop.getProperty("jdbc.url");
		} catch (Exception e) {
			throw new RuntimeException("读取数据库配置文件异常！", e);
		}
	}

	public JDBCUtils() {

	}

	public static BasicDataSource getDataSource(String driverClass, String url, String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaxWait(3000L);
		dataSource.setInitialSize(10);
		dataSource.setMaxIdle(10);
		dataSource.setMinIdle(10);
		dataSource.setMaxActive(60);
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(30);
		dataSource.setValidationQuery("select 1");
		return dataSource;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接
	 */
	public Connection getConnection(String driver, String url, String username, String password) {
		try {
			Class.forName(driver); // 注册驱动
			connection = DriverManager.getConnection(url, username, password); // 获取连接
		} catch (Exception e) {
			throw new RuntimeException("get connection error!", e);
		}
		return connection;
	}

	/**
	 * 执行更新操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            执行参数
	 * @return 执行结果
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<?> params) throws SQLException {
		boolean flag = false;
		int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		// 填充sql语句中的占位符
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	public boolean truncateByPreparedStatement(String sql) throws SQLException {
		boolean flag = false;
		int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
		pstmt = connection.prepareStatement(sql);
		result = pstmt.executeUpdate();
		flag = result == 0 ? true : false;
		return flag;
	}

	/** 
	* 批量执行预定义模式的SQL 
	 * @throws SQLException 
	*/
	public int[] exeBatchParparedSQL(String sql, List<List<String>> paramList) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement(sql);
		// 填充sql语句中的占位符
		if (paramList != null && !paramList.isEmpty()) {
			for (int i = 0; i < paramList.size(); i++) {
				int index = 1;
				List<String> params = paramList.get(i);
				if (params != null && !params.isEmpty()) {
					for (int j = 0; j < params.size(); j++) {
						pstmt.setString(index++, params.get(j));
					}
					pstmt.addBatch();
				}
			}
		}

		// 批量执行预定义SQL
		int[] result = pstmt.executeBatch();
		return result;
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            执行参数
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findResult(Connection connection, String sql, List<?> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 释放资源
	 */
	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test1() {
		JDBCUtils jdbcUtil = new JDBCUtils();
		List<String> param = new ArrayList<>();
		param.add("1");
		param.add("22");
		jdbcUtil.getConnection("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8", "root", "");
		try {
			List<List<String>> paramList = new ArrayList<>();
			paramList.add(Arrays.asList(new String[] { "rr", "23", "2" }));
			paramList.add(Arrays.asList(new String[] { "rr2", "25", "4" }));
			paramList.add(Arrays.asList(new String[] { "rr3", "43", "5" }));
			paramList.add(Arrays.asList(new String[] { "ff", "24", "1" }));
			int[] result = jdbcUtil.exeBatchParparedSQL("insert into user (name,age,addressId) values(?,?,?)",
					paramList);
			for (int i : result) {
				System.out.println(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.releaseConn();
		}
	}

	@Test
	public void test2() {
		JDBCUtils jdbcUtil = new JDBCUtils();
		jdbcUtil.getConnection("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8", "root", "");
		try {

			boolean flag = jdbcUtil.truncateByPreparedStatement("truncate table  user");
			System.out.println(flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.releaseConn();
		}
	}

	public static void main(String[] args) {
		JDBCUtils jdbcUtil = new JDBCUtils();
		List<String> param = new ArrayList<>();
		param.add("1");
		param.add("22");
		Connection connection = jdbcUtil.getConnection("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8", "root", "");
		try {
			List<Map<String, Object>> result = jdbcUtil.findResult(connection,
					"select * from user where id = ? and age = ?", param);

			for (Map<String, Object> m : result) {
				System.out.println(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.releaseConn();
		}
	}

}
