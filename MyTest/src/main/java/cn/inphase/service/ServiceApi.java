package cn.inphase.service;

import cn.inphase.domain.Info;

public interface ServiceApi {
	public String hello();

	public Info getInfo();

	public String setInfo(Info info);
}
