package cn.inphase.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private Map<String, Object> propsMap = new HashMap<>();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		// TODO Auto-generated method stub
		super.processProperties(beanFactoryToProcess, props);
		for (Object k : props.keySet()) {
			propsMap.put(k.toString(), props.get(k));
		}
	}

	public Map<String, Object> getPropsMap() {
		return propsMap;
	}

	public String getStringByKey(String key) {
		return propsMap.get(key).toString();
	}

	public int getIntByKey(String key) {
		return Integer.parseInt(propsMap.get(key).toString());
	}

	public double getDoubleByKey(String key) {
		return Double.parseDouble(propsMap.get(key).toString());
	}

}
