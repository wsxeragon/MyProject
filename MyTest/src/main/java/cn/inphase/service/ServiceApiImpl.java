package cn.inphase.service;

import org.springframework.stereotype.Service;

import cn.inphase.domain.Info;

@Service("service_api")
public class ServiceApiImpl implements ServiceApi {

	@Override
	public String hello() {
		return "hello world";
	}

	@Override
	public Info getInfo() {
		Info i = new Info();
		i.setMsg("success");
		i.setCode(200);
		return i;
	}

	@Override
	public String setInfo(Info info) {
		return info.toString();
	}

}
