package com.chmei.nzbdata.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.ITURoleService;
import com.chmei.nzbdata.util.RedisKey;
import com.github.pagehelper.Page;

@Transactional
@Service("tURoleService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TURoleServiceImpl extends BaseServiceImpl implements ITURoleService {

	private static final Logger logger = LoggerFactory.getLogger(TURoleServiceImpl.class);

	@Override
	public void selectRolesForUser(InputDTO input, OutputDTO output) {
		Map<String, Object> params = input.getParams();
		Page<Map<String, Object>> rolePage = (Page<Map<String, Object>>) getBaseDao()
				.queryForList("TURoleMapper.selectRolesForUser", params);
		params.remove("pageNum");
		params.remove("pageSize");
		List<Map<String, Object>> roleUsers = getBaseDao().queryForList("TURoleUserMapper.selectRoleUserForUser",
				params);
		for (Map<String, Object> role : rolePage) {
			Long id = (Long) role.get("id");
			role.put("ruId", 0);
			role.put("userId", params.get("userId"));
			for (Map<String, Object> roleUser : roleUsers) {
				if (roleUser.get("roleId").equals(id)) {
					role.put("ruId", roleUser.get("id"));
					break;
				}
			}
		}
		output.setItems(rolePage.getResult());
		output.setTotal((int) rolePage.getTotal());
	}

	@Override
	public void queryObject(InputDTO input, OutputDTO output) throws NzbDataException {
		Map queryForObject = getBaseDao().queryForObject("TURoleMapper.queryObject", input.getParams(), Map.class);
		output.setItem(queryForObject);
	}

	@Override
	public void queryList(InputDTO input, OutputDTO output) throws NzbDataException {
		// 获取起始索引
		Integer l = (Integer) input.getParams().get("limit");
		Integer limit = l == null ? 10 : l;
		Integer pageNumber = (Integer) input.getParams().get("pageNumber");
		Integer offset = ((pageNumber - 1) * limit);
		input.getParams().put("offset", offset);
		int count = getBaseDao().getTotalCount("TURoleMapper.queryTotal", input.getParams());
		output.setTotal(count);
		if (count > 0) {
			// 存在数据
			List<Map<String, Object>> list = getBaseDao().queryForList("TURoleMapper.queryList", input.getParams());
			output.setItems(list);
		}
	}

	@Override
	public void save(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> map = input.getParams();
		List<Map<String, Object>> mapName = getBaseDao().queryForList("TURoleMapper.queryRoleName", map);
		if (!CollectionUtils.isEmpty(mapName)) {
			output.setCode("-9999");
			output.setMsg("角色名称不能重复");
			return;
		}
		long id = getSequence();
		map.put("id", id);
		getCrtInfo(map, input);
		int insert = getBaseDao().insert("TURoleMapper.save", map);
		if (insert > 0) {
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
			logger.info("新增角色，角色Id=[{}],操作人Id=[{}]", id, map.get("crtUserId"));
		}
		output.setTotal(insert);

	}

	@Override
	public void delete(InputDTO input, OutputDTO output) throws NzbDataException {
		int delete = getBaseDao().delete("TURoleMapper.delete", input.getParams());
		if (delete > 0) {
			Map<String, Object> delteMap = new HashMap<>();
			delteMap.put("roleId", input.getParams().get("Id"));
			getBaseDao().delete("TURoleUserMapper.deleteByRoleId", delteMap);
			getBaseDao().delete("TURoleRightMapper.deleteByRoleId", delteMap);
			logger.info("删除角色，角色Id=[{}],操作人Id=[{}]", input.getParams().get("id"), input.getParams().get("modfUserId"));
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
		}
		output.setTotal(delete);

	}

	@Override
	public void update(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> map = input.getParams();

		List<Map<String, Object>> mapName = getBaseDao().queryForList("TURoleMapper.queryRoleName", map);
		if (!CollectionUtils.isEmpty(mapName)) {
			if (mapName.size() > 1) {
				output.setCode("-9999");
				output.setMsg("角色名称不能重复");
				return;
			} else if (mapName.size() == 1) {
				for (Map<String, Object> list : mapName) {
					if (!list.get("roleName").equals(input.getParams().get("roleName"))) {
						output.setCode("-9999");
						output.setMsg("角色名称不能重复");
						return;
					}
				}
			}
		}
		getModfInfo(map, input);
		int i = getBaseDao().update("TURoleMapper.update", map);
		if (i > 0) {
			logger.info("修改角色，角色Id=[{}],操作人Id=[{}]", map.get("id"), map.get("modfUserId"));
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
		}
		output.setTotal(i);
	}

	@Override
	public void deleteBatch(InputDTO input, OutputDTO output) throws NzbDataException {
		// 删除与角色管理的用户角色
		if (null != input.getParams().get("ids")) {
			int delete = getBaseDao().delete("TURoleMapper.deleteBatch", input.getParams());
			logger.info("删除角色，角色Ids=[{}],操作人Id=[{}]", input.getParams().get("ids"),
					input.getParams().get("modfUserId"));
			if (delete > 0) {
				String[] idArray = (String[]) input.getParams().get("ids");
				for (String roleId : idArray) {
					Map<String, Object> deleteMap = new HashMap<>();
					deleteMap.put("roleId", roleId);
					getBaseDao().delete("TURoleUserMapper.deleteByRoleId", deleteMap);
					logger.info("删除角色权限，角色Id=[{}],操作人Id=[{}]", roleId, input.getParams().get("modfUserId"));
					getBaseDao().delete("TURoleRightMapper.deleteByRoleId", deleteMap);
					logger.info("删除角色用户，角色Id=[{}],操作人Id=[{}]", roleId, input.getParams().get("modfUserId"));
				}
				addOperLog(input, JSONObject.toJSONString(input.getParams()));
			}
			// 删除权限缓存
			String sysTypeCd = String.valueOf(input.getParams().get("sysTypeCd"));
			deleteCache(sysTypeCd);
			output.setTotal(delete);
		}
	}

	@Override
	public void queryAllRole(InputDTO input, OutputDTO output) throws NzbDataException {
		List<Map<String, Object>> list = getBaseDao().queryForList("TURoleMapper.queryList", input.getParams());
		output.setItems(list);
	}

	private void deleteCache(String sysTypeCd) {
		String roleRightKey = RedisKey.REDIS_SYS_MANAGE_ROLE_RIGHT_HASH;
		String rightKey = RedisKey.REDIS_SYS_MANAGE_RIGHTS_HASH;
		this.getCacheService().del(rightKey);
		this.getCacheService().del(roleRightKey);
	}

	@Override
	public void queryRoleListWithHasAdmin(InputDTO input, OutputDTO output) throws NzbDataException {
		// 获取起始索引
		Integer l = (Integer) input.getParams().get("limit");
		Integer limit = l == null ? 10 : l;
		Integer pageNumber = (Integer) input.getParams().get("pageNumber");
		Integer offset = ((pageNumber - 1) * limit);
		input.getParams().put("offset", offset);
		int count = getBaseDao().getTotalCount("TURoleMapper.queryTotal", input.getParams());
		output.setTotal(count);
		if (count > 0) {
			// 存在数据
			List<Map<String, Object>> list = getBaseDao().queryForList("TURoleMapper.queryRoleListWithHasAdmin",
					input.getParams());
			output.setItems(list);
		}
	}

}
