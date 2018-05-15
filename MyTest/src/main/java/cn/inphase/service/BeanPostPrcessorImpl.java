package cn.inphase.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanPostPrcessorImpl implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
		System.out.println(arg1 + "初始化后");
		System.out.println(arg0 + "初始化后");
		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
		System.out.println(arg1 + "初始化前");
		System.out.println(arg0 + "初始化前");
		return arg0;
	}

}
