package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.im.service.impl.EasemobChatGroup;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.zxfriend.service.IZxChatGroupService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.client.model.ModifyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众享好友群管理dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点26分
 */

@Service("zxChatGroupService")
public class ZxChatGroupServiceImpl extends BaseServiceImpl implements IZxChatGroupService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxChatGroupServiceImpl.class);
	/** 调用环信接口 */
	private static final EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	/**
	 * 升级群组容量
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void upgradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String groupId = (String) params.get("groupId");
			Integer goal = (Integer) params.get("goal");
			String[] groupIds = { groupId }; // 单纯String 转数组org.apache.commons.lang3.ArrayUtils.toArray(groupId);
			Object result = easemobChatGroup.getChatGroupDetails(groupIds);
			LOGGER.info("imGroup============:"+gson.toJson(result));
			if(result == null){
				output.setCode("-1");
				output.setMsg("群组不存在！");
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map = gson.fromJson(result.toString(), map.getClass());
			List<Map<String,Object>> data =  (ArrayList)map.get("data");
			Double d = Double.parseDouble(data.get(0).get("maxusers").toString());
			Integer maxusers = d.intValue();
			if(maxusers >= goal){
				output.setCode("-1");
				output.setMsg("不可升级！");
				return;
			}
			Double money = groupGoal(maxusers, goal); // 计算群升级费用
			// 查询当前用户信息
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (null == item) {
				output.setCode("-1");
				output.setMsg("账号不存在！");
				return;
			}
			String walletBalance = (String) item.get("walletBalance");
			if(Double.valueOf(walletBalance) < money){
				output.setCode("-1");
				output.setMsg("钱包余额不足");
				return;
			}
			item.put("walletBalance", Double.valueOf(walletBalance) - money);
			// 更新用户余额
			getBaseDao().update("MemberMapper.saveMemberRechargeInfo", item);
			// 钱包扣除金额记录:
			Map<String, Object> walletMoneyInfo = new HashMap<>();
			walletMoneyInfo.put("walletInfoId", getSequence());
			walletMoneyInfo.put("walletInfoAddOrMinus", "-");
			walletMoneyInfo.put("walletInfoUserId", item.get("memberAccount"));
			walletMoneyInfo.put("walletInfoMoney", money);
			walletMoneyInfo.put("walletInfoFrom", "支付群升级费用");
			getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
			// 修改环信相对应的群容量信息
			ModifyGroup modifyGroup = new ModifyGroup();
			modifyGroup.setMaxusers(goal); // 群容量
			Object obj = easemobChatGroup.modifyChatGroup(groupId, modifyGroup);
			Map<String,Object> maps = gson.fromJson(obj.toString(), map.getClass());
			output.setCode("0");
			output.setMsg("群升级成功！");
			output.setItem(maps);
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 校验当前群组升级费用
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void checkGradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String groupId = (String) params.get("groupId");
			Integer goal = (Integer) params.get("goal");
			String[] groupIds = { groupId }; // 单纯String 转数组org.apache.commons.lang3.ArrayUtils.toArray(groupId);
			Object result = easemobChatGroup.getChatGroupDetails(groupIds);
			LOGGER.info("imGroup============:"+gson.toJson(result));
			if(result == null){
				output.setCode("-1");
				output.setMsg("群组不存在！");
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map = gson.fromJson(result.toString(), map.getClass());
			List<Map<String,Object>> data =  (ArrayList)map.get("data");
			Double d = Double.parseDouble(data.get(0).get("maxusers").toString());
			Integer maxusers = d.intValue();
			Map<String,Object> maps = new HashMap<>();
			if(maxusers == 2000) { // 最大2000人
				output.setCode("2");
				output.setMsg("群容量最大为2000人！");
				return;
			}
			if(maxusers >= goal){ // 处理第一次进来升级费用计算
				if (maxusers == 1000) {
					goal = 1500;
				} else if (maxusers == 1500){
					goal = 2000;
				}
				Double money = groupGoal(maxusers, goal); // 计算群升级费用
				maps.put("money", money);
			} else {
				Double money = groupGoal(maxusers, goal); // 计算群升级费用
				maps.put("money", money);
			}
			output.setCode("0");
			output.setMsg("查询成功！");
			output.setItem(maps);
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 计算群升级费用
	 * @param maxusers 环信群容量
	 * @param goal 升级标准
	 * @return
	 */
	private Double groupGoal(Integer maxusers, Integer goal){
		Double money = 0.00;
		if(Constants.MAXUSERS_ONE.equals(maxusers) && Constants.MAXUSERS_TWO.equals(goal)){
			money = 50.00;
		}
		if(Constants.MAXUSERS_ONE.equals(maxusers) && Constants.MAXUSERS_THREE.equals(goal)){
			money = 80.00;
		}
		if(Constants.MAXUSERS_ONE.equals(maxusers) && Constants.MAXUSERS_FOUR.equals(goal)){
			money = 100.00;
		}
		if(Constants.MAXUSERS_TWO.equals(maxusers) && Constants.MAXUSERS_THREE.equals(goal)){
			money = 30.00;
		}
		if(Constants.MAXUSERS_TWO.equals(maxusers) && Constants.MAXUSERS_FOUR.equals(goal)){
			money = 50.00;
		}
		if(Constants.MAXUSERS_THREE.equals(maxusers) && Constants.MAXUSERS_FOUR.equals(goal)){
			money = 20.00;
		}
		return money;
	}

}
