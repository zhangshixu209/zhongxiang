package com.chmei.nzbservice.common.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.idgene.IdGenerator;
import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.dubbo.IRemoteService;
import com.chmei.nzbservice.util.OperLogMatcher;

/**
 * 服务基类
 */
public class BaseServiceImpl {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
	protected IdGenerator idGenerator;
	@Autowired
    private IRemoteService nzbDataRemoteServiceConsumer;
    @Autowired
    private ICacheService cacheService;
  
    public IRemoteService getNzbDataService() {
    	return nzbDataRemoteServiceConsumer;
    }
    
	public ICacheService getCacheService() {
		return cacheService;
	}
	
	/**
	 * 发送操作日志记录
	 * 该方法适用于查询
	 * 该方法适用于单独日志记录,调用单独的genlcore层插入日志API接口
	 * @param input
	 */
	public void sendOperLog(InputDTO input) throws NzbServiceException {
		sendOperLog(input, null);
	}
	
	/**
	 * 发送操作日志记录
	 * @param input
	 */
	private void sendOperLog(InputDTO input, String operationDescribe) throws NzbServiceException {
		if(null == input.getLogParams() || input.getLogParams().isEmpty()) {
			LOG.info("LogParams参数为空，放弃此次操作日志记录！");
			return;
		}
		if(null == input.getLogParams().get("itemBelong")) {
			LOG.info("itemBelong参数为空，放弃此次操作日志记录！");
			return;
		}
		InputDTO operLogInput = new InputDTO();
		operLogInput.getComParams().putAll(input.getComParams());
		operLogInput.getLogParams().putAll(input.getLogParams());
		Map<String, String> operLogMap = OperLogMatcher.logMatcher.get(input.getService() + "_" + input.getMethod() + "_" + input.getLogParams().get("itemBelong"));
		if(null == operLogMap || operLogMap.isEmpty()) {
			throw new NzbServiceException("操作日志无法匹配，请检查！");
		}
		operLogInput.getLogParams().putAll(operLogMap);
		if(StringUtils.isNotBlank(operationDescribe)) {
			operLogInput.getLogParams().put("operationDescribe", operationDescribe);
		}
		operLogInput.setService("operationLogService");
		operLogInput.setMethod("insert");
		
		getNzbDataService().execute(operLogInput, new OutputDTO());

	}

	/**
	 * 设置匹配器操作日志相关参数
	 * 该方法适用(新增/修改/删除)操作
	 * 该方法适用于同相关业务请求数据下沉至genlcore层,在具体业务API内调用插入日志操作
	 * @param input
	 */
	public void setOperLogMatcher(InputDTO input) throws NzbServiceException {
		if(null == input.getLogParams() || input.getLogParams().isEmpty()) {
			LOG.info("LogParams参数为空，放弃此次操作日志记录！");
			return;
		}
		Map<String, String> operLogMap = OperLogMatcher.logMatcher.get(input.getService() + "_" + input.getMethod() + "_" + input.getLogParams().get("itemBelong"));
		if(null == operLogMap || operLogMap.isEmpty()) {
			throw new NzbServiceException("操作日志无法匹配，请检查！");
		}
		input.getLogParams().putAll(operLogMap);
	}

}
