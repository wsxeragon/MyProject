package cn.inphase.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BeanFactoryPostPrcessorImpl implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
		BeanDefinition bd = arg0.getBeanDefinition("person0");
		MutablePropertyValues pv = bd.getPropertyValues();
		System.out.println(pv.getPropertyValueList());
		if (pv.contains("name")) {
			pv.addPropertyValue("name", "hacker");
		}
	}

}
