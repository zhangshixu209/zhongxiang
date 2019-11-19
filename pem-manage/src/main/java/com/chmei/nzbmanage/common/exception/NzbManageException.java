package com.chmei.nzbmanage.common.exception;

public class NzbManageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NzbManageException(String message){
		super(message);
	}
	
	public NzbManageException(Throwable cause)
	{
		super(cause);
	}
	
	public NzbManageException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
