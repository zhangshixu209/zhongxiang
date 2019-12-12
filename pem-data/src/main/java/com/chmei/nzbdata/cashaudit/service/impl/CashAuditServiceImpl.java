package com.chmei.nzbdata.cashaudit.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.cashaudit.service.ICashAuditService;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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
			// 生成主键ID
			Long id = getSequence();
			input.getParams().put("id", id);
			Integer count = getBaseDao().insert("CashAuditMapper.saveCashAuditInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("提现申请失败");
				return;
			}
			output.setCode("0");
			output.setMsg("提现申请成功");
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
	@Override
	public void updateCashAuditInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Integer count = getBaseDao().update("CashAuditMapper.updateCashAuditInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("更新失败");
				return;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"CashAuditMapper.queryCashAuditDetail", params);
			String auditType = (String) params.get("auditType");
			String auditTypeCd = "";
			if ("1".equals(auditType)) {
				auditTypeCd = "已通过，请前往账户查看！";
			} else if ("2".equals(auditType)) {
				auditTypeCd = "未通过，提现金额已返回到钱包。";
			}
			String content = "您的提现申请审核" + auditTypeCd;
			map.put("id", getSequence());
			map.put("messageTitle", "提现审核结果");
			map.put("messageContent", content);
			map.put("messageType", Constants.MESSAGE_TYPE_1005);
			map.put("memberAccount", map.get("memberAccount"));
			// 添加推送消息
			getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
		} catch (Exception e) {
			LOGGER.error("更新失败: " + e);
		}
	}

}
