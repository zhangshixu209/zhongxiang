package com.chmei.nzbdata.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.ITURoleRightService;
import com.chmei.nzbdata.util.RedisKey;
import com.chmei.nzbdata.util.StringUtil;

@Transactional
@Service("tURoleRightService")
public class TURoleRightServiceImpl extends BaseServiceImpl implements ITURoleRightService {
	
	private  static final Logger logger = LoggerFactory.getLogger(TURoleRightServiceImpl.class);
	
	/**
	 * 修改角色权限
	 * 1、inputDTO 需传入roleId,以及新增的权限id的数组rightIds
	 * 2、inputDTO 需传入reportTypeId,以及新增的权限id的数组reportTypeId
	 */
	@Override
	public void update(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String , Object> params = input.getParams();
		logger.info("修改角色权限，角色Id=[{}]，操作人=[{}]",input.getParams().get("roleId"),input.getParams().get("crtUserId"));
		//根据角色删除角色权限
		int insert = 1;
		int delete = getBaseDao().delete("TURoleRightMapper.deleteByRoleId", input.getParams());
		logger.info("修改角色权限，第一步，删除角色权限，角色Id=[{}]，操作人Id=[{}]",
				input.getParams().get("roleId"),input.getParams().get("crtUserId"));
		//批量添加新增的角色权限
		List<Map<String, Object>> roleRightList = new ArrayList<>();
		String[] rightIds = (String[]) params.get("rightIds");
		Object roleId = params.get("roleId");
		if(delete>-1&&rightIds.length>0&&StringUtil.isNotEmpty(rightIds[0])) {
			for (String rightId : rightIds) {
				Map<String, Object> roleUser = new HashMap<>();
				long id = getSequence();
				roleUser.put("id", id);
				roleUser.put("roleId", roleId);
				roleUser.put("rightId", rightId);
				getCrtInfo(roleUser,input);
				roleRightList.add(roleUser);
			}
			params.put("roleRightList", roleRightList);
			logger.info("修改角色权限，第二步，新增角色权限，角色Id=[{}]，操作人=[{}]",
					input.getParams().get("roleId"),input.getParams().get("crtUserId"));
			insert = getBaseDao().insert("TURoleRightMapper.saveBatch", params);
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
		}
//		//处理报表和角色关联权限
//		String[] reportTypeId = (String[]) params.get("reportTypeId");
//		logger.info("修改角色权限报表分类，第二步，新增角色权限报表分类，角色Id=[{}]，操作人=[{}]",
//				input.getParams().get("roleId"),input.getParams().get("crtUserId"));
//		getBaseDao().delete("TURoleRightMapper.deleteByRoleIdReoprt", input.getParams());
//		dealWithReportType(reportTypeId,roleId,input);
		//删除权限缓存
		deleteCache();
		output.setTotal(delete*insert);
	}
	
	/**
	 * 新增插入报表分类和角色关系表
	 * @param reportTypeId 报表分类
	 * @param roleId  角色id
	 * @return
	 */
	private boolean dealWithReportType(String[] reportTypeId,Object roleId,InputDTO input){
		if(reportTypeId == null || reportTypeId.length == 0){
			logger.info("新增插入报表分类和角色关系表，没有报表分类权限，角色Id=[{}]",roleId);
			return false;
		}
		Map<String, Object> params = input.getParams();//参数
		List<Map<String, Object>> repoetRoleList = new ArrayList<>();
		for (String businessTypeId : reportTypeId) {
			Map<String, Object> roleUser = new HashMap<>();
			long id = getSequence();
			roleUser.put("id", id);
			roleUser.put("roleId", roleId);
			roleUser.put("businessTypeId", businessTypeId);
			getCrtInfo(roleUser,input);
			repoetRoleList.add(roleUser);
		}
		params.put("repoetRoleList", repoetRoleList);
		getBaseDao().insert("TURoleRightMapper.repoetRoleListSave", params);
		return true;
	}
	
	

	@Override
	public void queryRightListByRoleId(InputDTO input, OutputDTO output) throws NzbDataException {
		List<Map<String, Object>> list = getBaseDao().queryForList(
				"TURoleRightMapper.queryRightListByRoleId", input.getParams());
		output.setItems(list);
	}
	
	private void deleteCache() {
		String roleRightKey = RedisKey.REDIS_SYS_MANAGE_ROLE_RIGHT_HASH;
		String rightKey = RedisKey.REDIS_SYS_MANAGE_RIGHTS_HASH;
		this.getCacheService().del(rightKey);
		this.getCacheService().del(roleRightKey);
		logger.info("删除权限缓存redis");
	}
}
