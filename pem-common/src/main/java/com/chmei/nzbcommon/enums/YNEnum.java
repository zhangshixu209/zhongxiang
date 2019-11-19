package com.chmei.nzbcommon.enums;

import org.apache.commons.lang3.StringUtils;

public enum YNEnum {

	Y("1","是"),
	N("0","否");
	
	private String key;
	private String value;
	
	private YNEnum(String key,String value){
        this.key = key;
        this.value = value;
	}
	
	public static String getValueByKey(String key) {
		String value = "无";
		for(YNEnum yn : YNEnum.values()) {
			if(StringUtils.equals(key, yn.getKey())) {
				value = yn.getValue();
				break;
			}
		}
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
