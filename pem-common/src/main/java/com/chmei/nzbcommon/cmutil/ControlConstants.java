package com.chmei.nzbcommon.cmutil;

/**
 * 常量类
 */
public final class ControlConstants {
	/** 私有构造器 **/
	private ControlConstants() {
	}

	/** 系统errorCode编码 **/
	public interface RETURN_CODE {
		String IS_OK = "0";// 正常
		String SYSTEM_ERROR = "-1";// 错误
	}
	
	/** 国际化标识 **/
	public interface LOCAL_TYPE {
		String ZH_CN = "zh_CN";// 中文
		String EN_US = "en_US";// 英文
	}
}
