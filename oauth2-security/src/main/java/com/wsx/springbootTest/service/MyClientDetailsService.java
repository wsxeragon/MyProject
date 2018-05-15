package com.wsx.springbootTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.wsx.springbootTest.dao.ClientDao;
import com.wsx.springbootTest.domain.MyClientDetails;

@Service
public class MyClientDetailsService implements ClientDetailsService {

	@Autowired
	private ClientDao clientDao;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		MyClientDetails myClientDetails = clientDao.findByClientId(clientId);
		return myClientDetails;
	}

}
