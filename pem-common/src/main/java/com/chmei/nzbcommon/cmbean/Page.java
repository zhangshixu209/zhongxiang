package com.chmei.nzbcommon.cmbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page extends Entity {
	private static final long serialVersionUID = -6665324775888251777L;
	protected Integer total;
	protected List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

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

}
