package com.chmei.nzbservice.user.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.user.service.ISysSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysSqlService")
public class SysSqlServiceImpl extends BaseServiceImpl implements ISysSqlService {

	/**
	 * 调用操作日志
	 */
	@Autowired
	private IOperateLogService operateLogService;


	/**
	 * 查询数据库备份/还原列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryMySqlList(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.DATABASE_TYPE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("sysSqlService");
		input.setMethod("queryMySqlList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 备份数据库信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void backupsDBInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.DATABASE_TYPE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.DB_TYPE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("sysSqlService");
		input.setMethod("backupsDBInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 还原数据库信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void reductionDBInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.DATABASE_TYPE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.DB_TYPE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("sysSqlService");
		input.setMethod("reductionDBInfo");
		getNzbDataService().execute(input, output);
	}
	
}
  
