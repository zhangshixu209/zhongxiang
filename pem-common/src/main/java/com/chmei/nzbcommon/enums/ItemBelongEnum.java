package com.chmei.nzbcommon.enums;

public enum ItemBelongEnum {
	platform_manage(1); // 平台管理端
	
	private final int itemBelong;
	private ItemBelongEnum(int itemBelong) {
		this.itemBelong = itemBelong;
	}
	
	public int getItemBelong() {
		return itemBelong;
	}
}
