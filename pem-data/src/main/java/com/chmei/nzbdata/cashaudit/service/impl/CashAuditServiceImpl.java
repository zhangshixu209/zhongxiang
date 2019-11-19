package com.chmei.nzbdata.cashaudit.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.cashaudit.service.ICashAuditService;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
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
				output.setMsg("新增失败");
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
	@Override
	public void updateCashAuditInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Integer count = getBaseDao().update("CashAuditMapper.updateCashAuditInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("更新失败");
			}
		} catch (Exception e) {
			LOGGER.error("更新失败: " + e);
		}
	}

}
