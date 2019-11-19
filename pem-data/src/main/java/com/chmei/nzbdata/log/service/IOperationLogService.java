package com.chmei.nzbdata.log.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 操作日志
 * <p>Title: IOperationLogService</p>  
 * <p>Description: </p>  
 * @author gaoxuemin  
 * @date 2018年9月5日
 */
public interface IOperationLogService {

	/**
	 * 新增操作日志（走MQ）
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void insert(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 分页查询操作日志
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void findList(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 查询详情
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void findDescribe(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 新增操作日志
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void insertOperLog(InputDTO input, OutputDTO output) throws NzbDataException;
}
