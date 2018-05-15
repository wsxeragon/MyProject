package com.inphase.sparrow.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.inphase.sparrow.entity.TableParam;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class }) })  
public class PageInterceptor implements Interceptor {
	private String pattern = ".+ByPage$";    // 需要进行分页操作的字符串正则表达式

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

	/*
	 * (non-Javadoc) 拦截器要执行的方法
	 */
    @Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler,
				SystemMetaObject.DEFAULT_OBJECT_FACTORY,
				SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
				new DefaultReflectorFactory());
		MappedStatement mappedStatement = (MappedStatement) metaObject
				.getValue("delegate.mappedStatement");
		String id = mappedStatement.getId();
		if (id.matches(pattern)) {
			BoundSql boundSql = statementHandler.getBoundSql();
			TableParam page = (TableParam)boundSql.getParameterObject();
			String sql = boundSql.getSql();
			String countSql = "select count(*)from (" + sql + ")a";
			Connection connection = (Connection) invocation.getArgs()[0];
			PreparedStatement countStatement = connection
					.prepareStatement(countSql);
			ParameterHandler parameterHandler = (ParameterHandler) metaObject
					.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(countStatement);
			ResultSet rs = countStatement.executeQuery();
			if (rs.next()) {
				page.setsTotalRecord(rs.getInt(1));
			}
			String pageSql = sql + " limit " + page.getiDisplayStart() + ","
					+ page.getiDisplayLength();
			metaObject.setValue("delegate.boundSql.sql", pageSql);
		}
		return invocation.proceed();
	}

	/*
	 * (non-Javadoc) 拦截器需要拦截的对象
	 */
    @Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
}
