package com.chmei.nzbservice.common.exception;

/**
 * 自定义
 */
public class NzbServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6242165744027783598L;

	/**
	 * 
	 * @param msg
	 */
	public NzbServiceException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public NzbServiceException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * 
	 * @param e
	 */
	public NzbServiceException(Throwable e) {
		super(e);
	}
}
