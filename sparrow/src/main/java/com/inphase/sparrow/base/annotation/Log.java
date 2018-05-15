package com.inphase.sparrow.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

	/** 要执行的操作类型比如：
	 *  1、登录  2、新增  3、修改 4、删除 5、查询
	**/
	public int operationType();
	
	/** 要执行的具体操作比如：添加用户  **/
	public String operationName() default "";
	
	/** 业务状态 **/
	public String status() default "";
}
