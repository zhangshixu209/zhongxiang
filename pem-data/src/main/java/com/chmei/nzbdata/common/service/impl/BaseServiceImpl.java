package com.chmei.nzbdata.common.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.ertask.serial.ErTaskcodeGenerator;
import com.chmei.nzbcommon.idgene.IdGenerator;
import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbdata.common.dao.IBaseDao;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.DateUtil;

/**
 * Service 基类
 */
public class BaseServiceImpl {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ErTaskcodeGenerator erTaskcodeGenerator;
	
	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}
	
	public ICacheService getCacheService() {
		return cacheService;
	}
	
	
	
	public long getSequence() throws NzbDataException {
		return idGenerator.nextId();
	}
	
	public String getNextErTaskcode(String platformId) {
		Long taskcode = erTaskcodeGenerator.nextNo(platformId);
		return platformId + String.format("%04d", taskcode);
	}
	
	/**
	 * 生成创建信息
	 * @param crtMap
	 * @param inputDTO
	 * @return
	 */
	public Map<String, Object> getCrtInfo(Map<String, Object> crtMap, InputDTO inputDTO){
		if (inputDTO != null) {
			Map<String, Object> params = inputDTO.getParams();
			crtMap.put("crtUserId", params.get("crtUserId"));
		}
		crtMap.put("crtTime", DateUtil.date2String(new Date(), DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
		crtMap.put("modfTime", DateUtil.date2String(new Date()));
		return crtMap;
	}
	
	/**
	 * 生成修改信息
	 * @param modfMap
	 * @param inputDTO
	 * @return
	 */
	public Map<String, Object> getModfInfo(Map<String, Object> modfMap, InputDTO inputDTO){
		if (inputDTO != null) {
			Map<String, Object> params = inputDTO.getParams();
			modfMap.put("modfUserId", params.get("modfUserId"));
		}
		modfMap.put("modfTime", DateUtil.date2String(new Date(),
                DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
		return modfMap;
	}
	
	/**
	 * 生成创建信息
	 * @param inputDTO
	 * @param primaryKey
	 * @param tableName
	 */
	public void getCrtInfo(Map<String, Object> params,String primaryKey,String tableName){
		if (params != null) {
			Date now = new Date();
			params.put(primaryKey, getSequence());
			params.put("crtTime",now);
			params.put("modfTime", now);
		}
	}
	
	/**
	 * 填充通用信息
	 */
	public Map<String,Object> getCommonInfo(Map<String, Object> params,Date date,boolean isCrt){
		date = date != null ? date : new Date();
		if(isCrt){
			params.put("crtTime", date);
		}else{
			params.put("modfTime", date);
		}
		return params;
	}
	
	public void addOperLog(InputDTO input) throws NzbDataException {
		addOperLog(input, null);
	}
	
	public void addOperLog(InputDTO input, String operationDescribe) throws NzbDataException {
		// 限制长度只能为8000
		if(null != operationDescribe && operationDescribe.length() >= 8000) {
			operationDescribe = operationDescribe.substring(0, 7950);
		}
		input.getLogParams().put("operationDescribe", operationDescribe);
		input.getLogParams().put("crtTime", DateUtil.date2String(new Date()));
	}
	
}
