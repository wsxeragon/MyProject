package com.inphase.sparrow.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class BaseDao {

	private NamedParameterJdbcDaoSupport jdbcTemplate;


	@Autowired
	public void setJdbcTemplate(NamedParameterJdbcDaoSupport jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public NamedParameterJdbcDaoSupport getJdbcTemplate() {
		return jdbcTemplate;
	}
}
