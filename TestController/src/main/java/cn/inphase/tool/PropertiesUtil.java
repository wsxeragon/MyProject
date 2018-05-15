/**  
* @文件名 PropertiesUtil.java
* @版权 Copyright 2001-2017 版权所有：成都同步新创科技股份有限公司
* @描述 PropertiesUtil.java
* @修改人 lanshan
* @修改时间 2017年3月28日 下午2:27:20
*/
package cn.inphase.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * <调用配置文件工具类>
 * @author lanshan
 * @version V1.0,2017年3月28日 下午2:27:20
 * @see [相关类/方法]
 * @since V1.0
 * 
 */
public class PropertiesUtil {
	public static Properties props = new Properties();

	public static String get(String propertyName) {
		if (props.isEmpty()) {
			try {
				// InputStream is =
				// Test1.class.getResourceAsStream("test1.properties");
				// InputStream is =
				// PropertiesUtil.class.getResourceAsStream("propertiesUtil.properties");
				InputStream is = PropertiesUtil.class.getResourceAsStream("/conf/application.properties");
				props.load(new InputStreamReader(is, "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props.getProperty(propertyName);
	}
}
