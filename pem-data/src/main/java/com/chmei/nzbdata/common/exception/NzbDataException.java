package com.chmei.nzbdata.common.exception;

/**
 * 自定义
 * 
 * 
 *
 */
public class NzbDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6242165744027783598L;

	/**
	 * 
	 * @param msg
	 */
	public NzbDataException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public NzbDataException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * 
	 * @param e
	 */
	public NzbDataException(Throwable e) {
		super(e);
	}
}
