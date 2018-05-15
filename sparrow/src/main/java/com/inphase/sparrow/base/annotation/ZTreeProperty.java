package com.inphase.sparrow.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**      
 * @Description:ZTree格式化注解
 * @author: sunchao
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZTreeProperty {
	
	/** 要执行的具体操作比如：添加用户  **/
	public ZTreeField value() ;

}
