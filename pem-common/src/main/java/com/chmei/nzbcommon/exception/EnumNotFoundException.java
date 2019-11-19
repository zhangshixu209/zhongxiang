package com.chmei.nzbcommon.exception;

/**
 * @author lixinjie
 * @since 2019-03-28
 */
@SuppressWarnings("serial")
public class EnumNotFoundException extends IllegalArgumentException {

	public EnumNotFoundException(String message) {
		super(message);
	}
	
	public EnumNotFoundException(String enumName, String code) {
		this(enumName + " code '" + code + "' is invalid.");
	}
}
