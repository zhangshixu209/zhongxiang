package com.chmei.nzbcommon.account;

/**
 * @author lixinjie
 * @since 2018-10-21
 */
public enum AccountState { 

	Normal(0, "正常"),
	LimitFreeze(1, "限时冻结"),
	UnlimitFreeze(2, "永久冻结"),
	Disabled(3, "禁用"),
	Expired(4, "过期"),
	Locked(5, "锁定");
	
	private Integer code;
	private String name;
	
	private AccountState(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static AccountState valueOf(Integer code) {
		for (AccountState as : values()) {
			if (as.code == code) {
				return as;
			}
		}
		throw new IllegalArgumentException("AccountState code '" + code + "' is invalid.");
	}
}
