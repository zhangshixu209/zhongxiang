package com.chmei.nzbdata.complaint.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.complaint.service.IMemberComplaintService;
import com.chmei.nzbdata.im.service.impl.EasemobIMUsers;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员投诉dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月28日 18点11分
 */

@Service("memberComplaintService")
public class MemberComplaintServiceImpl extends BaseServiceImpl implements IMemberComplaintService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberComplaintServiceImpl.class);
	/** 调用环信接口 */
	private static final EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	@Resource
	private IZxMyTeamService iZxMyTeamService;

	/**
	 * 列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryMemberComplaintList(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("memberComplaintService.queryMemberComplaintList, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		int total = getBaseDao().getTotalCount("MemberComplaintMapper.queryMemberComplaintCount", params);
		if (total > 0) {
			List<Map<String, Object>> list = getBaseDao().queryForList("MemberComplaintMapper.queryMemberComplaintList", params);
			if (null != list && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> result = new HashMap<>();
					result.put("memberAccount", map.get("memberAccount"));
					InputDTO inputDTO = new InputDTO();
					inputDTO.setParams(result);
					// 根据当前用户ID 查询团队人数
					int size1 = iZxMyTeamService.countMyTeam(inputDTO, output);
					map.put("teamNum", size1); // 直推人数
				}
			}
			output.setItems(list);
		}
		output.setTotal(total);
	}

	/**
	 * 会员投诉详情查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryMemberComplaintDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("memberComplaintService.queryMemberComplaintDetail, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberComplaintMapper.queryMemberComplaintDetail", params);
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 修改会员投诉状态
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 投诉状态
			String status = (String) params.get("status");
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberComplaintMapper.queryMemberComplaintDetail", params);
			Map<String, Object> maps = new HashMap<>();
			maps.put("memberAccount", map.get("memberAccount"));
			if("1004".equals(status)){ // 冻结账号
				Object result = easemobIMUsers.deactivateIMUser((String) params.get("memberAccount"));
				if (null != result) {
					String content = "您投诉的用户：" + params.get("memberAccount") + "已被冻结";
					map.put("id", getSequence());
					map.put("messageTitle", "投诉结果查询");
					map.put("messageContent", content);
					map.put("messageStatus", "1");
					map.put("messageType", Constants.MESSAGE_TYPE_1004);
					map.put("memberAccount", map.get("complainant"));
					maps.put("status", "3");
					// 添加推送消息
					getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
				}
				LOGGER.info("deactivateIMUser============:"+gson.toJson(result));
			} else if ("1005".equals(status)) { // 解冻账号
				Object result = easemobIMUsers.activateIMUser((String) params.get("memberAccount"));
				LOGGER.info("deactivateIMUser============:"+gson.toJson(result));
			} else if ("1003".equals(status)) {
				map.put("id", getSequence());
				map.put("messageTitle", "投诉结果查询");
				map.put("messageContent", map.get("complaintRemark"));
				map.put("messageStatus", "1");
				map.put("messageType", Constants.MESSAGE_TYPE_1004);
				map.put("memberAccount", map.get("memberAccount"));
				maps.put("status", "2");
				// 添加推送消息
				getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
			}
			int count = getBaseDao().update("MemberComplaintMapper.updateMemberComplaintInfo", params);
			if (count > 0) {
				// 同步修改会会员状态
				getBaseDao().update("MemberMapper.updateMemberInfo", maps);
			} else {
				output.setCode("-1");
				output.setMsg("保存失败");
			}
		} catch (Exception ex) {
			LOGGER.error("保存失败", ex);
		}
	}

	/**
	 * 新增会员投诉
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence()); // 获取id
			int count = getBaseDao().update("MemberComplaintMapper.saveMemberComplaintInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("保存失败");
			}
			output.setCode("0");
			output.setMsg("投诉成功");
		} catch (Exception ex) {
			LOGGER.error("保存失败", ex);
		}
	}

}
