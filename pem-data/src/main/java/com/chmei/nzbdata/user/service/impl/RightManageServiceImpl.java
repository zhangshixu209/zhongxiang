package com.chmei.nzbdata.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.RightManageService;
import com.chmei.nzbdata.util.RedisKey;

@Transactional
@Service("rightManageService")
public class RightManageServiceImpl extends BaseServiceImpl implements RightManageService {
	/**
	 * log
	 */
	private  static final Logger logger = LoggerFactory.getLogger(RightManageServiceImpl.class);
	//初始化加载权限
	@Override
	public void queryInitAllRight(InputDTO input, OutputDTO output) {
		// 加载系统权限到缓存
		List<Map<String, Object>> rightList = getBaseDao().queryForList(
				"TURightMapper.queryList", input.getParams());
		logger.info("rightList={}", rightList);
		if(null!=rightList) {
			loadRightsIntoCache(rightList);
		}
	}
	
	//初始化加载角色权限
	@Override
	public void queryInitAllRoleRight(InputDTO input, OutputDTO output) {
		// 初始化加载角色权限
		List<Map<String, Object>> roleList = getBaseDao().queryForList("TURoleMapper.queryList", input.getParams());
		logger.info("roleList={}", roleList);
		if(null!=roleList) {
			for (Map<String, Object> role : roleList) {
				//获取角色下所有的权限
				List<Map<String, Object>> roleRightList = queryRoleRightByRole(role);
				logger.info("roleRightList={}", roleRightList);
				//把该角色下的所有权限加载如缓存，key为角色Id
				loadRoleRightsIntoCache(roleRightList,role.get("id").toString());
			}
		}
	}
	
	//查询某角色下所有的权限
	private List<Map<String,Object>> queryRoleRightByRole(Map<String, Object> role) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", role.get("id"));
		List<Map<String, Object>> list = getBaseDao().queryForList("TURoleRightMapper.queryRightListByRoleId", param);
		return list;
	}
	
	/**
	 * 把所有角色权限按权限类型计算权限哈希值并缓存
	 * @param rights
	 * @param clearCache
	 */
	private void loadRoleRightsIntoCache(List<Map<String, Object>> roleRights,String roleId) {
		logger.info("初始化角色权限，加载角色权限到缓存,角色Id=[{}]",roleId);
		Map<String, String> sysRightsHash = new HashMap<>();
		if(null!=roleRights) {
			for(int i = 0; i < roleRights.size(); i++) {
				Map<String, Object> roleRight = roleRights.get(i);
				sysRightsHash.put(String.valueOf(roleRight.get("mo")), JsonUtil.convertObject2Json(roleRight));
			}
		}
		String key = RedisKey.REDIS_SYS_MANAGE_ROLE_RIGHT_HASH;
		logger.info("roleRightKey={}", key);
		this.getCacheService().hdel(key, roleId);
		this.getCacheService().hset(key, roleId, JsonUtil.convertObject2Json(sysRightsHash));
		logger.info("初始化角色权限，查询角色权限到缓存已完成，角色Id=[{}]",roleId);
	}
	
	/**
	 * 把所有权限按权限类型计算权限哈希值并缓存
	 * @param rights
	 * @param clearCache
	 */
	private void loadRightsIntoCache(List<Map<String, Object>> rights) {
		Map<String, String> sysRightsHash = new HashMap<>();
		int len = rights.size();
		for(int i = 0; i < len; i++) {
			Map<String, Object> right = rights.get(i);
			sysRightsHash.put(String.valueOf(right.get("mo")), JsonUtil.convertObject2Json(right));
		}
		String key = RedisKey.REDIS_SYS_MANAGE_RIGHTS_HASH;
		logger.info("rightKey={}", key);
		this.getCacheService().del(key);
		this.getCacheService().putMap(key, sysRightsHash);
	}
	
}
  
