package com.chmei.nzbcommon.exception;

/**
 * 自定义
 * 
 * 
 *
 */
public class NzbCommonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6242165744027783598L;

	/**
	 * 
	 * @param msg
	 */
	public NzbCommonException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public NzbCommonException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * 
	 * @param e
	 */
	public NzbCommonException(Throwable e) {
		super(e);
	}
}
