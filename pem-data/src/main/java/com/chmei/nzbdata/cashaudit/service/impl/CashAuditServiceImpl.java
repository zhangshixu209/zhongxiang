package com.chmei.nzbdata.cashaudit.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.cashaudit.service.ICashAuditService;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体现审核接口
 * 
 * @author zhangshixu
 * @since 2019年10月23日 09点46分
 *
 */
@Service("cashAuditService")
public class CashAuditServiceImpl extends BaseServiceImpl implements ICashAuditService {

	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(CashAuditServiceImpl.class);
	@Resource
	private IZxMyTeamService iZxMyTeamService;

	/**
	 * 提现审核管理查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryCashAuditList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int total = getBaseDao().getTotalCount("CashAuditMapper.queryCashAuditCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao()
						.queryForList("CashAuditMapper.queryCashAuditList", params);
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
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
			output.setCode("1");
			output.setMsg("系统错误");
		}
	}

	/**
	 * 提现审核管理新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveCashAuditInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String cashAmount = (String) params.get("cashAmount");
			Map<String, Object> map = new HashMap<>();
			map.put("memberAccount", params.get("memberAccount"));
			// 查询用户信息
			@SuppressWarnings("unchecked")
			Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", map);
			String walletBalance = (String) zxAppUser.get("walletBalance");
			if(Double.valueOf(walletBalance) - Double.valueOf(cashAmount) < 0){
				output.setCode("-1");
				output.setMsg("钱包余额不足!");
				return;
			}
			// 生成主键ID
			Long id = getSequence();
			input.getParams().put("id", id);
			int count = getBaseDao().insert("CashAuditMapper.saveCashAuditInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("提现申请失败");
				return;
			}
			// 存在就修改钱包
			if(zxAppUser != null){
				Map<String, Object> updateUser = new HashMap<>();
				updateUser.put("memberAccount", zxAppUser.get("memberAccount"));
				updateUser.put("walletBalance", Double.valueOf((String) zxAppUser.get("walletBalance")) - Double.valueOf(cashAmount));
				int i = getBaseDao().update("MemberMapper.updateMemberBalance", updateUser);
				if (i > 0) {
					// 钱包扣除金额记录:
					Map<String, Object> walletMoneyInfo = new HashMap<>();
					walletMoneyInfo.put("walletInfoId", getSequence());
					walletMoneyInfo.put("walletInfoAddOrMinus", "-");
					walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
					walletMoneyInfo.put("walletInfoMoney", cashAmount);
					walletMoneyInfo.put("walletInfoFrom", "提现");
					getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
					output.setCode("0");
					output.setMsg("提现申请成功");
				}
			}
		} catch (Exception e) {
			LOGGER.error("新增失败: " + e);
		}
	}

	/**
	 * 提现审核管理查询详细信息
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void queryCashAuditDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> item = (Map<String, Object>) getBaseDao()
					.queryForObject("CashAuditMapper.queryCashAuditDetail", params);
			output.setItem(item);
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
			output.setCode("1");
			output.setMsg("系统错误");
		}
	}

	/**
	 * 提现审核管理编辑
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateCashAuditInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("realCashAmount", Double.valueOf(params.get("cashAmount") + "") * 0.9);
			int count = getBaseDao().update("CashAuditMapper.updateCashAuditInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("更新失败");
				return;
			}
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"CashAuditMapper.queryCashAuditDetail", params);
			String auditType = (String) params.get("auditType");
			String auditTypeCd = "";
			if ("1".equals(auditType)) {
				auditTypeCd = "已通过，预计两个工作日到账！";
				String cashAmount = (String) map.get("cashAmount");
				// 钱包扣除金额记录:
				Map<String, Object> walletMoneyInfo = new HashMap<>();
				walletMoneyInfo.put("walletInfoId", getSequence());
				walletMoneyInfo.put("walletInfoAddOrMinus", "-");
				walletMoneyInfo.put("walletInfoUserId", map.get("memberAccount"));
				walletMoneyInfo.put("walletInfoMoney", Double.valueOf(cashAmount) * 0.1);
				walletMoneyInfo.put("walletInfoFrom", "提现手续费");
				getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
			} else if ("2".equals(auditType)) {
				String cashAmount = (String) map.get("cashAmount");
				Map<String, Object> map_ = new HashMap<>();
				map_.put("memberAccount", map.get("memberAccount"));
				// 查询用户信息
				Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
						queryForObject("MemberMapper.queryMemberDetail", map_);
				// 存在就修改钱包
				if(zxAppUser != null){
				Map<String, Object> updateUser = new HashMap<>();
				updateUser.put("memberAccount", zxAppUser.get("memberAccount"));
				updateUser.put("walletBalance", Double.valueOf((String) zxAppUser.get("walletBalance")) + Double.valueOf(cashAmount));
				int i = getBaseDao().update("MemberMapper.updateMemberBalance", updateUser);
					if (i > 0) {
						auditTypeCd = "未通过，提现金额已返回到钱包。";
						// 钱包扣除金额记录:
						Map<String, Object> walletMoneyInfo = new HashMap<>();
						walletMoneyInfo.put("walletInfoId", getSequence());
						walletMoneyInfo.put("walletInfoAddOrMinus", "+");
						walletMoneyInfo.put("walletInfoUserId", zxAppUser.get("memberAccount"));
						walletMoneyInfo.put("walletInfoMoney", cashAmount);
						walletMoneyInfo.put("walletInfoFrom", "提现失败");
						getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
						output.setCode("0");
						output.setMsg("提现失败");
					}
				}
			}
			String auditOpinion = (String) params.get("auditOpinion");
			if(StringUtil.isNotEmpty(auditOpinion)){
				auditTypeCd += "温馨提示：" + auditOpinion;
			}
			String content = "您的提现申请审核" + auditTypeCd;
			map.put("id", getSequence());
			map.put("messageTitle", "提现审核通知");
			map.put("messageContent", content);
			map.put("messageStatus", "1");
			map.put("messageType", Constants.MESSAGE_TYPE_1005);
			map.put("memberAccount", map.get("memberAccount"));
			// 添加推送消息
			getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
		} catch (Exception e) {
			LOGGER.error("更新失败: " + e);
		}
	}

}
