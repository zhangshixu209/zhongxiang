package com.chmei.nzbservice.complaint.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.complaint.service.IGroupComplaintService;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 群投诉service接口实现
 * 
 * @author zhangshixu
 * @since 2019年12月05日 14点30分
 *
 */
@Service("groupComplaintService")
public class GroupComplaintServiceImpl extends BaseServiceImpl implements IGroupComplaintService {
	
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(GroupComplaintServiceImpl.class);

	/**
	 * 操作日志对象
	 */
	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 列表查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryGroupComplaintList(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询列表查询...groupComplaintService.queryGroupComplaintList()....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.COMPLAINT_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("groupComplaintService");
		input.setMethod("queryGroupComplaintList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 群投诉详情查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryGroupComplaintDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("groupComplaintService");
		input.setMethod("queryGroupComplaintDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 修改群投诉状态
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateGroupComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("groupComplaintService");
		input.setMethod("updateGroupComplaintInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 新增群投诉
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveGroupComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("groupComplaintService");
		input.setMethod("saveGroupComplaintInfo");
		getNzbDataService().execute(input, output);
	}
}
