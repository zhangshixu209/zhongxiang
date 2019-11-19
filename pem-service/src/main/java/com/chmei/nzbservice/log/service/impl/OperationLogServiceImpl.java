package com.chmei.nzbservice.log.service.impl;

import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.log.service.IOperationLogService;
import com.chmei.nzbservice.util.OperLogMatcher;
	
/**
 * 操作日志
 * <p>Title: OperationLogServiceImpl</p>  
 * <p>Description: </p>  
 * @author gaoxuemin  
 * @date 2018年9月5日
 */
@Service("operationLogService")
public class OperationLogServiceImpl extends BaseServiceImpl implements IOperationLogService {

	/**
	 * 登录操作日志
	 */
	@Override
	public void loginLog(InputDTO input, OutputDTO output) throws NzbServiceException {
		sendOperLog(input);
	}

	/**
	 * 登出操作日志
	 */
	@Override
	public void logoutLog(InputDTO input, OutputDTO output) throws NzbServiceException {
		sendOperLog(input);
	}

	/**
	 * 分页查询操作日志
	 */
	@Override
	public void findList(InputDTO input, OutputDTO output) throws NzbServiceException {
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询详情
	 */
	@Override
	public void findDescribe(InputDTO input, OutputDTO output) throws NzbServiceException {
		getNzbDataService().execute(input, output);
	}

	/**
	 * 获取匹配器操作对象集合
	 */
	@Override
	public void getOperationModelList(InputDTO input, OutputDTO output) throws NzbServiceException {
		output.setData(OperLogMatcher.operationModelList);
	}

	@Override
	public void insertOperLog(InputDTO input, OutputDTO output) throws NzbServiceException {
		getNzbDataService().execute(input, output);
	}
	
}
