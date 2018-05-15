package com.inphase.sparrow.base.util;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.inphase.sparrow.base.handler.BusinessException;


public class JsonUtils {
	
	private static final Logger logger = Logger.getLogger("errorfile");
	
	private static ObjectMapper objectMapper = null;

	static {
		objectMapper = new ObjectMapper();
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		// 设置是否在序列化时在最外层再加一层根节点
		//objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		//objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		//设置序列化是默认值和null不进行序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		//objectMapper.setSerializationInclusion(Include.NON_DEFAULT);
		objectMapper.setDateFormat(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"));
	}
	
	private JsonUtils(){}
	
	/**
	 * 直接序列化不过滤字段
	 * @param value 待序列化的实体对像
	 * @return
	 */
	public static String jsonSerialization(Object value) {
		
		try {
			return objectMapper.writeValueAsString(value);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(500000,"解析对象错误");  
		}
	}

	public static <T> T jsonDeserialization(String json, Class<?> clazz){
		try {
			return  (T)objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(500000,"反序列化对象错误");  
		}
	}

	/**
	 * 复杂类型json反序列化 ,比如List,Map
	 * @param json
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static <T> T jsonDeserialization(String json, Class<?> collectionClass, Class<?>... elementClasses){
		try {
			JavaType javaType = getJavaType(collectionClass,elementClasses);
			return objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(500000,"反序列化对象错误");
		}
	}
	
	private static JavaType getJavaType(Class<?> collectionClass, Class<?>... elementClasses){
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
}
