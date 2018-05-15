package com.wsx.springbootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.transaction.PlatformTransactionManager;

import com.wsx.springbootTest.domain.User;

@SpringBootApplication
@MapperScan("com.wsx.springbootTest.dao")
public class Application {
	private static Logger logger = Logger.getLogger(Application.class);

	@Value("${spring.datasource.driver-class-name}")
	private String driverClass;
	// @Value("${spring.datasource.url}")
	// private String url;
	// @Value("${spring.datasource.username}")
	// private String username;
	// @Value("${spring.datasource.password}")
	// private String password;
	@Value("${myProps.simpleProp}")
	private String simpleProp;
	@Value("${randomValue}")
	private String randomValue;

	// 动态数据源
	// @Bean(name = "dynamicDataSource")
	// public DynamicDataSource dynamicDataSource() {
	// DataSource dataSource = dataSource();
	// DataSource dataSource2 = new DataSource();
	// dataSource2.setDriverClassName(driverClass);
	// dataSource2.setUrl(url);
	// dataSource2.setUsername(username);
	// dataSource2.setPassword(password);
	// DynamicDataSource dynamicDataSource = new
	// com.wsx.springbootTest.domain.DynamicDataSource();
	// Map<String, Object> map = new HashMap<>();
	// map.put("defaultDataSource", dataSource2);
	// dynamicDataSource.setTargetDataSources(map);
	// dynamicDataSource.setDefaultTargetDataSource(dataSource2);
	// return dynamicDataSource;
	// }

	// DataSource配置
	// @Bean(name = "dataSource")
	// @ConfigurationProperties(prefix = "spring.datasource")
	// public DataSource dataSource() {
	// System.out.println(driverClass);
	// System.out.println(simpleProp);
	// System.out.println(randomValue);
	// return new org.apache.tomcat.jdbc.pool.DataSource();
	// }

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

	@Bean
	public User user123() {
		return new User("123", "wsx");
	}

	/**
	 * Main Start
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("============= SpringBoot Start Success =============");
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public ExecutorService getThreadPool() {
		return Executors.newFixedThreadPool(6);
	}

}
