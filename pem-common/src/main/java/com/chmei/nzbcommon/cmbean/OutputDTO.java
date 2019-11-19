package com.chmei.nzbcommon.cmbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 统一的输出结果集
 */
public class OutputDTO extends Entity {
	
	private static final long serialVersionUID = -5487673118718811790L;
	private String code;
	private String msg;
	private String queryTime;//查询远程数据库耗时
	private String sortsSearch;//排序参数
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> item = new HashMap<String, Object>();
	/**报表获取数据特定数据结构处理 表体数据*/
	@JsonInclude(Include.NON_NULL)
	private List<Map<String,Object>> itemList = new ArrayList<>();
	/**报表获取数据特定数据结构处理 查询条件数据*/
	@JsonInclude(Include.NON_NULL)
	private List<Map<String,Object>> serachList = new ArrayList<>();
	/**报表获取数据特定数据结构处理   高级 查询条件数据*/
	@JsonInclude(Include.NON_NULL)
	private List<Map<String,Object>> bigSerachList = new ArrayList<>();
	@JsonInclude(Include.NON_NULL)
    /** 报表查询时拼接的sql语句 */
	private String finalSql;
	@JsonInclude(Include.NON_NULL)
	private Integer total;
	@JsonInclude(Include.NON_NULL)
	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	@JsonInclude(Include.NON_NULL)
	private Object data;
	
	//李新杰 2018-12-06
	//直接把Java对象传过去
	@JsonInclude(Include.NON_NULL)
	private Object javaBean;
	@JsonInclude(Include.NON_NULL)
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
	
	/** 无参构造器 **/
	public OutputDTO() {
	}

	public String getSortsSearch() {
		return sortsSearch;
	}

	public void setSortsSearch(String sortsSearch) {
		this.sortsSearch = sortsSearch;
	}


	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public List<Map<String, Object>> getItemList() {
		return itemList;
	}

	public void setItemList(List<Map<String, Object>> itemList) {
		this.itemList = itemList;
	}

	public OutputDTO(String code) {
		super();
		this.code = code;
	}
	public OutputDTO(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Map<String, Object>> getSerachList() {
		return serachList;
	}

	public void setSerachList(List<Map<String, Object>> serachList) {
		this.serachList = serachList;
	}

	public List<Map<String, Object>> getBigSerachList() {
		return bigSerachList;
	}

	public void setBigSerachList(List<Map<String, Object>> bigSerachList) {
		this.bigSerachList = bigSerachList;
	}

	public Map<String, Object> getItem() {
		return item;
	}

	public void setItem(Map<String, Object> item) {
		this.item = item;
	}
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Map<String, Object>> getItems() {
		return items;
	}

	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 将对象转换成Map<String,Object>,并将结果set到item中
	 */
	public Map<String, Object> convertBean2Map(Object object) {
		this.item = BeanUtil.convertBean2Map(object);
		return item;
	}
	
	/**
	 * 将List<对象>转换成List< Map>，并将结果set到beans中
	 * 
	 * @param List<对象>
	 */
	public <T> List<Map<String, Object>> convertBeanList(List<T> list) {
		try {
			for (Object object : list) {
				Map<String, Object> bean = BeanUtil.convertBean2Map(object);
				if (bean != null && bean.size() != 0) {
					this.items.add(bean);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return items;
	}
	
	/**
	 * 将OutputDTO对象中的beans列表转换成List<JavaBean>
	 */
	public <T> List<T>  convertList2JavaBeans(Class<T> clz){
		return BeanUtil.convertList2Beans(items, clz);
	}
	
	/**
	 * 将OutputDTO中的bean转换成JavaBean对象
	 */
	public <T> T convertBean2JavaBean(Class<T> clz){
		return BeanUtil.convertMap2Bean(item, clz);
	}
	/**
	 * 往bean属性中放入键值对
	 * 
	 * @param key
	 * @param toKey
	 * @param value
	 */
	public void addItem(String key, String toKey, String value) {
		if (BeanUtil.isNotEmpty(toKey)) {
			item.put(toKey, String.valueOf(value));
		} else {
			item.put(key, String.valueOf(value));
		}
	}

	/**
	 * 往beans中的index处的Map中放入键值对
	 * 
	 * @param key
	 * @param toKey
	 * @param index
	 * @param value
	 */
	public void addItemss(String key, String toKey, int index,
			String value) {
		if (items.size() <= index) {
			items.add(new HashMap<String, Object>());
		}
		if (BeanUtil.isNotEmpty(toKey)) {
			items.get(index).put(toKey, value);
		} else {
			items.get(index).put(key, value);
		}
	}

    public String getFinalSql() {
        return finalSql;
    }

    public void setFinalSql(String finalSql) {
        this.finalSql = finalSql;
    }
	
}
