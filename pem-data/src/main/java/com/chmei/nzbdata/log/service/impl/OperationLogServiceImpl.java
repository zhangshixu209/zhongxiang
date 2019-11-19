package com.chmei.nzbdata.log.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.ControlConstants;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.log.service.IOperationLogService;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.DateUtil;

/**
 * 操作日志
 * <p>
 * Title: OperationLogServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author gaoxuemin
 * @date 2018年9月5日
 */
@Transactional
@Service("operationLogService")
public class OperationLogServiceImpl extends BaseServiceImpl implements IOperationLogService {

	private static final Logger LOGGER = Logger.getLogger(OperationLogServiceImpl.class);

	/**
	 * 新增操作日志
	 */
	@Override
	public void insert(InputDTO input, OutputDTO output) throws NzbDataException {
		addOperLog(input);
	}

	@Override
	public void findList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		Date time = new Date();
		String tableDateStr = null;
		Date timeBegin = DateUtil.string2Date(Objects.toString(params.get("timeBegin")));
		Date timeEnd = DateUtil.string2Date(Objects.toString(params.get("timeEnd")));
		if (null != timeBegin && null != timeEnd) {
			tableDateStr = DateUtil.date2String(timeBegin, DateUtil.DATE_PATTERN.YYYYMM);
		} else {
			tableDateStr = DateUtil.date2String(time, DateUtil.DATE_PATTERN.YYYYMM);
		}
		params.put("tableName", "t_op_operation_log_" + tableDateStr);

		int count = getBaseDao().getTotalCount("operationLogMapper.findListCount", params);
		output.setTotal(count);
		if (count > 0) {
			List<Map<String, Object>> queryForList = getBaseDao().queryForList("operationLogMapper.findList", params);
			output.setItems(queryForList);
		}

	}

	/**
	 * 查询详情
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void findDescribe(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		Date crtTime = DateUtil.string2Date(Objects.toString(params.get("crtTime")));
		String tableNameTime = DateUtil.date2String(crtTime, DateUtil.DATE_PATTERN.YYYYMM);
		params.put("tableName", "t_op_operation_log_describe_" + tableNameTime);
		Map<String, Object> data = (Map<String, Object>) getBaseDao()
				.queryForObject("operationLogDescribeMapper.findById", params);
		if (null == data) {
			output.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			output.setMsg("详情信息不存在！");
			return;
		}
		params.put("tableName", "t_op_operation_log_" + tableNameTime);
		Map<String, Object> operLog = (Map<String, Object>) getBaseDao().queryForObject("operationLogMapper.findById",
				params);
		if (null != operLog) {
			data.put("operationModel", Objects.toString(operLog.get("operationModel"), ""));
			data.put("operationAction", Objects.toString(operLog.get("operationAction"), ""));
		}
		output.setItem(data);
	}

	/**
	 * 操作日志插入
	 * 
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	@Override
	public void insertOperLog(InputDTO input, OutputDTO output) throws NzbDataException {
		if (null == input.getLogParams() || input.getLogParams().isEmpty()) {
			LOGGER.info("LogParams参数为空，放弃此次操作日志记录！");
			return;
		}

		Map<String, Object> addOperationLog = new HashMap<>();

		Date crtTime = DateUtil.string2Date(input.getLogParams().get("crtTime"));
		String tableDate = DateUtil.date2String(crtTime, DateUtil.DATE_PATTERN.YYYYMM);
		addOperationLog.put("tableName", "t_op_operation_log_" + tableDate);
		Long id = getSequence();
		addOperationLog.put("id", id);
		addOperationLog.put("crtTime", crtTime);
		addOperationLog.put("itemBelong", input.getLogParams().get("itemBelong"));
		addOperationLog.put("operationModel", input.getLogParams().get("operationModel"));
		addOperationLog.put("operationAction", input.getLogParams().get("operationAction"));
		addOperationLog.put("operationUserMobile", input.getComParams().get("operationUserMobile"));
		addOperationLog.put("operationUserName", input.getComParams().get("operationUserName"));
		addOperationLog.put("cltptIpAddr", input.getLogParams().get("cltptIpAddr"));
		addOperationLog.put("orgId", input.getComParams().get("orgId"));
		addOperationLog.put("orgName", input.getComParams().get("orgName"));
		addOperationLog.put("operationAccountId", input.getComParams().get("accountId"));

		int result = getBaseDao().insert("operationLogMapper.insert", addOperationLog);
		if (result == 0) {
			output.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			output.setMsg("新增操作日志失败!");
			LOGGER.error("新增操作日志失败!");
			return;
		}
		// 如果是查询操作则无需新增描述信息
		if (Objects.equals(addOperationLog.get("operationAction"), "查询")) {
			return;
		}
		String operationDescribe = input.getLogParams().get("operationDescribe");
		if (StringUtils.isBlank(operationDescribe)) {
			return;
		}

		// 增加描述信息
		addOperationLog.put("tableName", "t_op_operation_log_describe_" + tableDate);
		addOperationLog.put("operationDescribe", operationDescribe);
		result = getBaseDao().insert("operationLogDescribeMapper.insert", addOperationLog);
		if (result == 0) {
			output.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			output.setMsg("新增操作日志描述失败!");
			LOGGER.error("新增操作日志描述失败!");
			return;
		}

	}

}
