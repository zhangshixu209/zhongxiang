package com.chmei.nzbdata.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.chmei.nzbdata.user.service.ITURoleUserService;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;

@Transactional
@Service("tURoleUserService")
public class TURoleUserServiceImpl extends BaseServiceImpl implements ITURoleUserService {

	private static final Logger logger = LoggerFactory.getLogger(TURoleUserServiceImpl.class);

	/**
	 * 给用户添加角色
	 */
	@Override
	public void insertRoleUserForUser(InputDTO input, OutputDTO output) {
		Long id = getSequence();
		input.getParams().put("id", id);
		getBaseDao().insert("TURoleUserMapper.insertRoleUserForUser", input.getParams());
		output.getItem().put("ruId", id);
	}

	@Override
	public void deleteRoleUserForUser(InputDTO input, OutputDTO output) {
		getBaseDao().delete("TURoleUserMapper.deleteRoleUserForUser", input.getParams());
	}

	@Override
	public void queryRoleAdminList(InputDTO input, OutputDTO output) throws NzbDataException {
		// 获取起始索引
		Integer l = (Integer) input.getParams().get("limit");
		Integer limit = l == null ? 10 : l;
		Integer pageNumber = (Integer) input.getParams().get("pageNumber");
		Integer offset = ((pageNumber - 1) * limit);
		input.getParams().put("offset", offset);
		int count = getBaseDao().getTotalCount("TURoleUserMapper.queryTotal", input.getParams());
		output.setTotal(count);
		if (count > 0) {
			// 存在数据
			List<Map<String, Object>> list = getBaseDao().queryForList("TURoleUserMapper.queryRoleAdminList",
					input.getParams());
			output.setItems(list);
		}
	}

	@Override
	public void queryRoleErAdminList(InputDTO input, OutputDTO output) throws NzbDataException {
		// 获取起始索引
		Integer l = (Integer) input.getParams().get("limit");
		Integer limit = l == null ? 10 : l;
		Integer pageNumber = (Integer) input.getParams().get("pageNumber");
		Integer offset = ((pageNumber - 1) * limit);
		input.getParams().put("offset", offset);
		int count = getBaseDao().getTotalCount("TURoleUserMapper.queryTotal", input.getParams());
		output.setTotal(count);
		if (count > 0) {
			// 存在数据
			List<Map<String, Object>> list = getBaseDao().queryForList("TURoleUserMapper.queryRoleErUserList",
					input.getParams());
			output.setItems(list);
		}
	}

	/**
	 * 角色关联用户 inputDTO
	 * 需传入roleId,去除的关联用户id的数组removeUserIds，以及新增的关联用户的id的数组addUserIds
	 */
	@Override
	public void updateRoleUserByRole(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		// 删除去除的用户权限
		String[] removeUserIds = (String[]) params.get("removeUserIds");
		int delete = 1;
		int insert = 1;
		if (removeUserIds.length > 0 && StringUtil.isNotEmpty(removeUserIds[0])) {
			delete = getBaseDao().delete("TURoleUserMapper.deleteBatchByRoleId", input.getParams());

		}
		// 批量添加新增的权限
		List<Map<String, Object>> roleUserList = new ArrayList<>();
		String[] addUserIds = (String[]) params.get("addUserIds");
		if (addUserIds.length > 0 && StringUtil.isNotEmpty(addUserIds[0])) {
			for (String userId : addUserIds) {
				Map<String, Object> roleUser = new HashMap<>();
				long id = getSequence();
				roleUser.put("id", id);
				roleUser.put("roleId", params.get("roleId"));
				roleUser.put("userId", userId);
				getCrtInfo(roleUser, input);
				roleUserList.add(roleUser);
			}
			params.put("roleUserList", roleUserList);
			insert = getBaseDao().insert("TURoleUserMapper.saveBatch", params);
			logger.info("修改角色用户，角色Id=[{}]，操作人=[{}]", params.get("roleId"), params.get("crtUserId"));
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
		}
		output.setTotal(delete * insert);
	}

	@Override
	public void updateRoleUserByUser(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		// 删除的用户权限
		String[] removeRoleIds = (String[]) params.get("removeRoleIds");
		int delete = 1;
		int insert = 1;
		if (removeRoleIds.length > 0 && StringUtil.isNotEmpty(removeRoleIds[0])) {
			delete = getBaseDao().delete("TURoleUserMapper.deleteBatchByUserId", input.getParams());
		}
		// 批量添加新增的权限
		List<Map<String, Object>> roleUserList = new ArrayList<>();
		String[] addRoleIds = (String[]) params.get("addRoleIds");
		if (addRoleIds.length > 0 && StringUtil.isNotEmpty(addRoleIds[0])) {
			for (String roleId : addRoleIds) {
				Map<String, Object> roleUser = new HashMap<>();
				long id = getSequence();
				roleUser.put("id", id);
				roleUser.put("userId", params.get("userId"));
				roleUser.put("roleId", roleId);
				getCrtInfo(roleUser, input);
				roleUserList.add(roleUser);
			}
			params.put("roleUserList", roleUserList);
			insert = getBaseDao().insert("TURoleUserMapper.saveBatch", params);
			logger.info("修改用户角色，用户Id=[{}]，操作人=[{}]", params.get("userId"), params.get("crtUserId"));
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
		}
		output.setTotal(delete * insert);
	}

	@Override
	public void queryAll(InputDTO input, OutputDTO output) throws NzbDataException {
		List<Map<String, Object>> list = getBaseDao().queryForList("TURoleUserMapper.queryAll", input.getParams());
		output.setItems(list);
	}
}
