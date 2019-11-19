package com.chmei.nzbservice.log.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

public interface IOperationLogService {

	public void loginLog(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	public void logoutLog(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	public void findList(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	public void findDescribe(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	public void getOperationModelList(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 新增操作日志
	 * @param input
	 * @param output
	 * @throws GenlCoreException
	 */
	public void insertOperLog(InputDTO input, OutputDTO output) throws NzbServiceException;
}
