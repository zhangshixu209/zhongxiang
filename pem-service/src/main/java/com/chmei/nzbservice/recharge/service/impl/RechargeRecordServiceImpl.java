package com.chmei.nzbservice.recharge.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.recharge.service.IRechargeRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 轮播图service接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月29日 17点48分
 *
 */
@Service("rechargeRecordService")
public class RechargeRecordServiceImpl extends BaseServiceImpl implements IRechargeRecordService {
	
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(RechargeRecordServiceImpl.class);

	/**
	 * 操作日志对象
	 */
	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 初始化加载充值记录查询列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryRechargeRecordList(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询详情查询...rechargeRecordService.queryRechargeRecordList....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.RECHARGE_RECORD_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rechargeRecordService");
		input.setMethod("queryRechargeRecordList");
		getNzbDataService().execute(input, output);
	}

}
