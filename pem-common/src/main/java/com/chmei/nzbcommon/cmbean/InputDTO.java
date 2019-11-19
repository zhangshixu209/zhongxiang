package com.chmei.nzbcommon.cmbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.cmutil.JsonUtil;

/**
 * 入参公用对象
 */
public class InputDTO extends Entity {
	private static final long serialVersionUID = -4753448539316449501L;
	private String service;// 调用后台服务类的名称
	private String method;// 服务类中的方法名
	private String busiCode;// 接口编码

	private Map<String, Object> params = new HashMap<String, Object>();// 参数信息
	private List<Map<String, Object>> beans = new ArrayList<Map<String, Object>>();// 参数集
	private Map<String, String> comParams = new HashMap<String, String>();// 冗余参数
	private Map<String, String> logParams = new HashMap<String, String>();// 日志参数

	//李新杰 2018-12-06
	//直接把Java对象传过去
	private Object javaBean;
	private List<Object> javaBeans;

	@SuppressWarnings("unchecked")
	public <T> T getJavaBean() {
		return (T)javaBean;
	}

	public <T> void setJavaBean(T javaBean) {
		this.javaBean = javaBean;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getJavaBeans() {
		return (List<T>)javaBeans;
	}

	@SuppressWarnings("unchecked")
	public <T> void setJavaBeans(List<T> javaBeans) {
		this.javaBeans = (List<Object>)javaBeans;
	}
	//end

	public Map<String, String> getComParams() {
		return null == comParams ? new HashMap<String, String>() : comParams;
	}

	public void setComParams(Map<String, String> comParams) {
		this.comParams = comParams;
	}

	/** 无参构造器 **/
	public InputDTO() {
	}

	/** 构造器 **/
	public InputDTO(String service, String method) {
		this.service = service;
		this.method = method;
	}

	/** 构造器 **/
	public InputDTO(String service, String method, Map<String, Object> params) {
		this.service = service;
		this.method = method;
		this.params = params;
	}

	/** 构造器 **/
	public InputDTO(String service, String method,
			Map<String, Object> params, Map<String, String> logParams) {
		this.service = service;
		this.method = method;
		this.params = params;
		this.logParams = logParams;
	}

	/** 构造器 **/
	public InputDTO(String service, String method,
			Map<String, Object> params, Map<String, String> comParams,Map<String, String> logParams) {
		this.service = service;
		this.method = method;
		this.params = params;
		this.comParams=comParams;
		this.logParams = logParams;
	}


	public InputDTO copy(String service, String method) {
		InputDTO inObj = this.deepClone();// 深度克隆
		if (inObj != null) {// 重设service、method
			inObj.setService(service);
			inObj.setMethod(method);
		} else {// 克隆失败，自己赋值
			inObj = new InputDTO(service, method);
			inObj.getParams().putAll(params);
			inObj.getLogParams().putAll(logParams);
			inObj.getBeans().addAll(beans);
			inObj.setComParams(comParams);
			inObj.setBusiCode(busiCode);
		}
		return inObj;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getParams() {
		if (null == params) {
			params = new HashMap<String, Object>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * 将bean属性转换成<T>对象
	 * 
	 * (修改了之前的实现逻辑，并将转换方法提出公用)
	 */
	public <T> T convertParams2Bean(Class<T> clz) {
		return BeanUtil.convertMap2Bean(params, clz);
	}

	/**
	 * 将beans属性转换成List<T>对象
	 */
	public <T> List<T> convertBeans2Beans(Class<T> clz) {
		List<T> list = new ArrayList<T>();
		try {
			for (Map<String, Object> bean : beans) {
				T t = BeanUtil.convertMap2Bean(bean, clz);
				if (t != null) {
					list.add(t);
				}
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, Object>> getBeans() {
		return null == beans ? new ArrayList<Map<String, Object>>() : beans;
	}

	public void setBeans(List<Map<String, Object>> beans) {
		this.beans = beans;
	}

	public Map<String, String> getLogParams() {
		return null == logParams ? new HashMap<String, String>() : logParams;
	}

	public void setLogParams(Map<String, String> logParams) {
		this.logParams = logParams;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	/**
	 * 往bean属性中放入键值对
	 */
	public void addParams(String key, String toKey, String value) {
		if (toKey != null && !"".equals(toKey)) {
			getParams().put(toKey, value);
		} else {
			getParams().put(key, value);
		}
	}

	/**
	 * 往beans中的index处的Map中放入键值对
	 */
	public void addBeans(String key, String toKey, int index, String value) {
		if (getBeans().size() <= index) {
			getBeans().add(new HashMap<String, Object>());
		}
		if (toKey != null && !"".equals(toKey)) {
			getBeans().get(index).put(toKey, value);
		} else {
			getBeans().get(index).put(key, value);
		}
	}

	/**
	 * 获取bean属性值
	 * 
	 */
	public String getValue(String key) {
		Object value = getParams().get(key);
		if (value != null
				&& (value instanceof Number || value instanceof String)) {
			return String.valueOf(value);
		}
		return (String) getParams().get(key);
	}

	public String getBeansValue(int index, String key) {
		// 越界
		if (getBeans().size() <= index) {
			return null;
		}
		return (String) getBeans().get(index).get(key);
	}

	public String toJson() {
		return JsonUtil.convertObject2Json(this);
	}
}
