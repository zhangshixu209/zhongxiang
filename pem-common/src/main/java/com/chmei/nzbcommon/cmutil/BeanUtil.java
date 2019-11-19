package com.chmei.nzbcommon.cmutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BeanUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/** 私有构造器 **/
	private BeanUtil() {
	}

	/**
	 * 将String转换成Date对象
	 * 
	 * @param date
	 * @return Date对象
	 */
	public static Date parse(String date) {
		String method = "parse";
		try {
			return isEmpty(date) ? null : dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error(method, "convert String 2 Date Error.", date, e);
		}
		return null;
	}

	/**
	 * 将Date 对象转换成String
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return date == null ? "" : dateFormat.format(date);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return (value == null || "".equals(value.trim()));
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 通过反射获取值
	 */
	public static Object getValueFromField(Field field, String name,
			String value) {
		Object object = value;
		String type = field.getType().getSimpleName();

		if (isEmpty(value)
				&& ("short,int,Integer,long,double,float".contains(type))) {
			value = "0";
		}

		if ("short".equalsIgnoreCase(type)) { // Short
			object = Short.valueOf(value);
		} else if ("char".equals(type) || "Character".equals(type)) { // Character
			if (!BeanUtil.isEmpty(value)) {
				object = value.charAt(0);
			}
		} else if ("int".equals(type) || "Integer".equals(type)) { // Integer
			object = Integer.valueOf(value);
		} else if ("long".equalsIgnoreCase(type)) { // Long
			object = Long.valueOf(value);
		} else if ("byte".equalsIgnoreCase(type)) { // Byte
			object = Byte.valueOf(value);
		} else if ("float".equalsIgnoreCase(type)) { // Float
			object = Float.valueOf(value);
		} else if ("double".equalsIgnoreCase(type)) { // Double
			object = Double.valueOf(value);
		} else if ("boolean".equalsIgnoreCase(type)) { // Boolean
			object = Boolean.valueOf(value);
		} else if ("Date".equals(type)) { // Date
			object = BeanUtil.parse(value);
		} else if ("byte[]".equalsIgnoreCase(type)) { // byte[]
			object = ConvertUtil.string2Bytes(value);
		}
		return object;
	}

	/**
	 * 通过反射获取值
	 */
	public static Object getValueFromField(Object object, Field field) {
		try {
			Object value = field.get(object);
			String type = field.getType().getSimpleName();
			/*
			 * if ("short".equalsIgnoreCase(type)) { // Short value =
			 * Short.valueOf(value); } else if
			 * ("char".equals(type)||"Character".equals(type)) { // char value =
			 * field.getChar(object); } else if
			 * ("int".equals(type)||"Integer".equals(type)) { // int value =
			 * field.getInt(object); } else if ("long".equalsIgnoreCase(type)) {
			 * // long value = field.getLong(object); } else if
			 * ("byte".equalsIgnoreCase(type)) { // byte value =
			 * field.getByte(object); } else if ("float".equalsIgnoreCase(type))
			 * { // float value = field.getFloat(object); } else if
			 * ("double".equalsIgnoreCase(type)) { // double value =
			 * field.getDouble(object); } else if
			 * ("boolean".equalsIgnoreCase(type)) { // boolean value =
			 * field.getBoolean(object); } else
			 */
			if ("Date".equals(type)) { // Date
				value = BeanUtil.format((Date) value);
			} else if ("byte[]".equalsIgnoreCase(type)) { // byte[]
				if (value != null) {
					value = ConvertUtil.bytes2String((byte[]) value);
				}
			} else if ("String".equalsIgnoreCase(type)) {
				if (value == null) {
					value = "";
				}
			} else {
				value = field.get(object);
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取所有父类的属性列表
	 */
	public static Field[] getSuperFields(Class<?> subCls, List<Field> subFields) {
		Class<?> superClass = subCls.getSuperclass();
		subFields = new ArrayList<Field>(subFields);
		if (superClass != null && superClass != Object.class) {
			Field[] superFields = superClass.getDeclaredFields();
			subFields.addAll(Arrays.asList(superFields));
			if (superClass.getSuperclass() != Object.class) {
				getSuperFields(superClass, subFields);// 递归调用
			}
		}
		return subFields.toArray(new Field[subFields.size()]);
	}

	/**
	 * 转换Map<String,Object> 转换成T对应的对象
	 * 
	 * @param 需要转换的结果类型
	 */
	public static <T> T convertMap2Bean(Map<String, Object> bean, Class<T> clz) {
		if (bean == null) {
			return null;
		}

		try {
			T t = clz.newInstance();
			Field[] fields = t.getClass().getDeclaredFields();
			fields = BeanUtil.getSuperFields(clz, Arrays.asList(fields));// 获取父类的属性列表
			for (Field field : fields) {
				String key = field.getName();// 获取属性名
				String value = (String) bean.get(key);
				if (value == null || "null".equals(value)) {
					continue;
				}// 如果对应属性的值，bean中不存在，返回继续遍历

				boolean visiable = field.isAccessible();
				field.setAccessible(Boolean.TRUE);
				Object object = BeanUtil.getValueFromField(field, key, value);
				field.set(t, object);
				field.setAccessible(visiable);
			}
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将对象转换成Map<String,Object>
	 * 
	 * @param 需要转换的对象
	 */
	public static Map<String, Object> convertBean2Map(Object object) {
		if (object == null) {
			return null;
		}

		Map<String, Object> bean = new HashMap<String, Object>();
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			fields = getSuperFields(object.getClass(), Arrays.asList(fields));
			for (Field field : fields) {
				String key = field.getName();
				// 非序列号 && 非静态属性
				if (!"serialVersionUID".equals(key)
						&& !Modifier.isStatic(field.getModifiers())) {
					boolean visible = field.isAccessible();
					field.setAccessible(Boolean.TRUE);
					Object value = BeanUtil.getValueFromField(object, field);
					field.setAccessible(visible);
					bean.put(key.trim(), value);
				}
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Map<String, Object>> convertBeans2List(Object... objects) {
		if (objects == null) {
			return null;
		}
		List<Map<String, Object>> beans = new ArrayList<Map<String, Object>>();
		for (Object object : objects) {
			Map<String, Object> bean = BeanUtil.convertBean2Map(object);
			if (bean != null && bean.size() != 0) {
				beans.add(bean);
			}
		}
		return beans;
	}

	public static <T> List<T> convertList2Beans(
			List<Map<String, Object>> lists, Class<T> clz) {
		if (lists == null || lists.size() == 0) {
			return null;
		}
		List<T> results = new ArrayList<T>();
		for (Map<String, Object> map : lists) {
			T t = convertMap2Bean(map, clz);
			if (t != null) {
				results.add(t);
			}
		}
		return results;
	}
	
	/**
	 * 将对象转换成Map<String,Object>
	 * <p>不进行数据类型转换，不包含null字段
	 * <p>李新杰
	 * @param 需要转换的对象
	 */
	public static Map<String, Object> convertBeanToMap(Object object) {
		return convertBeanToMap(object, false);
	}
	
	/**
	 * 将对象转换成Map<String,Object>
	 * <p>不进行数据类型转换
	 * <p>李新杰
	 * @param 需要转换的对象
	 */
	public static Map<String, Object> convertBeanToMap(Object object, boolean includeNull) {
		Map<String, Object> bean = new HashMap<String, Object>();
		if (object == null) {
			return bean;
		}
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			fields = getSuperFields(object.getClass(), Arrays.asList(fields));
			for (Field field : fields) {
				String key = field.getName();
				// 非序列号 && 非静态属性
				if (!"serialVersionUID".equals(key)
						&& !Modifier.isStatic(field.getModifiers())) {
					field.setAccessible(true);
					Object value = field.get(object);
					if (value != null) {
						bean.put(key, value);
					} else if (includeNull) {
						bean.put(key, value);
					}
				}
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}