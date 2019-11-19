package com.chmei.nzbcommon.util.excel;

import org.apache.poi.ss.usermodel.CellStyle;

public class CellWrapper {
	
	private CellStyle cellStyle;  
	
	private String key;

	private int cellIndex;
	
	private String formatter;
	

	public CellWrapper(String key,CellStyle cellStyle){
		this.key = key;
		this.cellStyle = cellStyle;
	}
	
	public CellWrapper(String key,int cellIndex) {
		this.key = key;
		this.cellIndex = cellIndex;
	}
	
	
	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public int getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
}
