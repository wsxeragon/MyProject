package com.inphase.sparrow.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.log4j.Logger;

import com.inphase.sparrow.base.annotation.ZTreeField;
import com.inphase.sparrow.base.annotation.ZTreeProperty;

/**
 * @Description: ZTree 实体类
 * @author: sunchao
 */
public class ZTree {
	
	private static final Logger logger = Logger.getLogger("ZTree");

	private long id;

	private long pId;

	private String name;

	private boolean open;

	private boolean checked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getpId() {
		return pId;
	}

	public void setpId(long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @Description 将原始树形数据格式化为ZTree数据
	 * @param obj
	 *            原始树形数据
	 * @param clazz
	 *            树的实体类型
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public static List<ZTree> format(Object obj, Class<?> clazz) {
		List<ZTree> zTreeList = new ArrayList<ZTree>();
		dealWithData(obj, clazz, zTreeList);
		return zTreeList;
	}

	private static void dealWithData(Object obj, Class<?> clazz,List<ZTree> zTreeList) {
		List<?> list = null;
		Object genericObje;
		ZTree zTree;
		if (obj instanceof List) {
			list = (List<?>) obj;
		}
		try {
			for (int i = 0; i < list.size(); i++) {
				zTree = new ZTree();
				genericObje = clazz.cast(list.get(i));
				Field[] fields = genericObje.getClass().getDeclaredFields();
				for (Field field : fields) {
					// 通过自定义注解来判断基础字段
					ZTreeProperty property = field
							.getAnnotation(ZTreeProperty.class);
					if (property != null) {
						if (ZTreeField.ID.equals(property.value())) {
							zTree.setId(Long.valueOf(BeanUtils.getProperty(
									genericObje, field.getName())));
						} else if (ZTreeField.PID.equals(property.value())) {
							zTree.setpId(Long.valueOf(BeanUtils.getProperty(
									genericObje, field.getName())));
						} else if (ZTreeField.NAME.equals(property.value())) {
							zTree.setName(BeanUtils.getProperty(genericObje,
									field.getName()));
						} else if (ZTreeField.CHECKED.equals(property.value())) {
							zTree.setChecked("1".equals(BeanUtils.getProperty(
									genericObje, field.getName())) ? true
									: false);
						}
					}

					// 通过反射字段的类型，得到字段的泛型类型，和传递进入的参数类型来判断当前树的子节点
					Class<?> fieldClass = field.getType();
					Type fc = field.getGenericType();
					if (ClassUtils.isAssignable(fieldClass, List.class) && fc instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) fc;
						Class<?> genericClazz = (Class<?>) pt
								.getActualTypeArguments()[0];
						if (genericClazz.equals(clazz)) {
							List<?> childrenList = (List<?>) MethodUtils
									.invokeMethod(
											genericObje,
											"get"
													+ field.getName()
															.substring(0, 1)
															.toUpperCase()
													+ field.getName()
															.substring(
																	1,
																	field.getName()
																			.length()));
							if (childrenList!=null && !childrenList.isEmpty()) {
								if (zTree.getpId() == 0) {
									zTree.setOpen(true);
								}
								dealWithData(childrenList, clazz, zTreeList);
							}							
						}
					}
				}
				zTreeList.add(zTree);
			}
		} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
}
