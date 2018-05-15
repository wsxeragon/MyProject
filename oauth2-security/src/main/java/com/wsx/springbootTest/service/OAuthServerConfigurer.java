package com.wsx.springbootTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer // 开启认证服务器
public class OAuthServerConfigurer extends AuthorizationServerConfigurerAdapter {

	// 配置oauth认证安全策略
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}

	@Autowired
	private MyClientDetailsService myClientDetailsService;

	// 配置客户端认证方式以及客户端连接参数
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 配置两个客户端,一个用于password认证一个用于client认证
		// clients.inMemory().withClient("client").resourceIds(RESOURCES_ID.LEVEL_ONE)
		// .authorizedGrantTypes("authorization_code",
		// "refresh_token").scopes("app").authorities("client")
		// .secret("123456");

		clients.withClientDetails(myClientDetailsService);
	}

	// 认证转换器
	// @Bean
	// public JwtAccessTokenConverter getJwtAccessTokenConverter() {
	// JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	// KeyPair keyPair = new KeyStoreKeyFactory(new
	// ClassPathResource("serverKeystore.jks"), "123456".toCharArray())
	// .getKeyPair("alias1");
	// converter.setKeyPair(keyPair);
	// return converter;
	//
	// }

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	RedisConnectionFactory redisConnectionFactory;
	// @Autowired
	// private DataSource dataSource;
	//
	// @Bean // 声明 ClientDetails实现
	// public ClientDetailsService clientDetails() {
	// return new JdbcClientDetailsService(dataSource);
	// }
	//
	// @Bean // 声明TokenStore实现
	// public TokenStore tokenStore() {
	// return new JdbcTokenStore(dataSource);
	// }

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// endpoints.authenticationManager(authenticationManager);
		// endpoints.tokenStore(tokenStore());
		//
		// // 配置TokenServices参数
		// DefaultTokenServices tokenServices = new DefaultTokenServices();
		// tokenServices.setTokenStore(endpoints.getTokenStore());
		// tokenServices.setSupportRefreshToken(false);
		// tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		// tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		// tokenServices.setAccessTokenValiditySeconds((int)
		// TimeUnit.DAYS.toSeconds(30)); // 30天
		// endpoints.tokenServices(tokenServices);
		endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory)).authenticationManager(authenticationManager);
	}

}
