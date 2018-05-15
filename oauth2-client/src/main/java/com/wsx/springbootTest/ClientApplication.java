package com.wsx.springbootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.transaction.PlatformTransactionManager;

import com.wsx.springbootTest.tool.MyTool;

@SpringBootApplication
@MapperScan("com.wsx.springbootTest.dao")
public class ClientApplication {
	private static Logger logger = Logger.getLogger(ClientApplication.class);

	// 提供SqlSeesion
	// 开启自动配置后，springboot会自动生成一个dataSource
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * Main Start
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
		logger.info("============= SpringBoot Start Success =============");
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Test
	public void test1() throws IOException {
		// System.out.println(MyTool.OkGet("http://localhost:8080/TestController/jsp/test2"));

		// Map<String, String> map = new HashMap<>();
		// map.put("msg", "hello");
		// System.out.println(MyTool.OkPostJson("http://localhost:8080/TestController/jsp/json",
		// new Gson().toJson(map)));

		// Map<String, String> map = new HashMap<>();
		// map.put("arg0", "first");
		// map.put("arg1", "second");
		// System.out.println(MyTool.OkPostForm("http://localhost:8080/TestController/jsp/form",
		// map));

		// System.out.println(MyTool.restGet("http://localhost:8080/TestController/jsp/test2"));

		// Map<String, String> map = new HashMap<>();
		// map.put("data", "抽奖");
		// System.out
		// .println(MyTool.restPostForm("http://110.190.90.247:8002/DemondDetailsService/usermanager/valid",
		// map));

		Map<String, String> map = new HashMap<>();
		map.put("msg", "noob");
		System.out.println(MyTool.restPostJson("http://localhost:8080/TestController/jsp/json", map));
	}

}
