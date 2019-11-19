package com.chmei.nzbcommon.util.excel.exception;

public class ExcelException extends RuntimeException {

	private static final long serialVersionUID = 8735084330744657672L;

	public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
