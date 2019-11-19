package com.chmei.nzbservice.complaint.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.complaint.service.IMemberComplaintService;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员投诉service接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月28日 16点20分
 *
 */
@Service("memberComplaintService")
public class MemberComplaintServiceImpl extends BaseServiceImpl implements IMemberComplaintService {
	
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(MemberComplaintServiceImpl.class);

	/**
	 * 操作日志对象
	 */
	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 初始化查询列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryMemberComplaintList(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询列表查询...memberComplaintService.queryMemberComplaintList()....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.COMPLAINT_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("memberComplaintService");
		input.setMethod("queryMemberComplaintList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 会员投诉详情查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryMemberComplaintDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询列表查询...memberComplaintService.queryMemberComplaintDetail()....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.COMPLAINT_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("memberComplaintService");
		input.setMethod("queryMemberComplaintDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 修改会员投诉状态
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("修改会员投诉状态...memberComplaintService.updateMemberComplaintInfo()....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.COMPLAINT_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("memberComplaintService");
		input.setMethod("updateMemberComplaintInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 新增会员投诉
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("新增会员投诉接口...memberComplaintService.saveMemberComplaintInfo()....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.COMPLAINT_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("memberComplaintService");
		input.setMethod("saveMemberComplaintInfo");
		getNzbDataService().execute(input, output);
	}

}
