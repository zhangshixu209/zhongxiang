package com.chmei.nzbcommon.ertask.serial;

/**
 * <p>任务编号生成器
 * @author lixinjie
 * @since 2019-02-18
 */
public interface ErTaskcodeGenerator {
	
	Long nextNo(String platformId);
	
	String getName();

	long getInitNo();
	
	int getStep();
	
	long getMaxNo();
}
