package com.chmei.nzbservice.feedback.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.feedback.service.IFeedbackService;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;

/**
 * 反馈意见service接口实现
 * 
 * @author 翟超锋
 * @since 2019年05月10日 15点30分
 *
 */
@Service("feedbackService")
public class FeedbackServiceImpl extends BaseServiceImpl implements IFeedbackService {
	
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(FeedbackServiceImpl.class);

	/**
	 * 操作日志对象
	 */
	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 初始化查询列表查询
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void queryList(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询列表查询...feedbackService.queryList()....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.FEED_BACK_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("feedbackService");
		input.setMethod("queryList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 回复单条列表查询
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void selectByPrimaryKey(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.FEED_BACK_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_READDETAIL_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("feedbackService");
		input.setMethod("selectByPrimaryKey");
		getNzbDataService().execute(input, output);
	}

	

	/**
	 * 删除
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void deleteByPrimaryKey(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.FEED_BACK_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("feedbackService");
		input.setMethod("deleteByPrimaryKey");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 新增意见反馈
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void insertSelective(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.FEED_BACK_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("feedbackService");
		input.setMethod("insertSelective");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void doUpdateFeedback(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.FEED_BACK_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("feedbackService");
		input.setMethod("doUpdateFeedback");
		getNzbDataService().execute(input, output);
	}


	
	
}
