package com.chmei.nzbservice.rotation.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.rotation.service.IRotationChartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 轮播图service接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点27分
 *
 */
@Service("rotationChartService")
public class RotationChartServiceImpl extends BaseServiceImpl implements IRotationChartService {
	
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(RotationChartServiceImpl.class);

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
	public void queryRotationChartList(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询列表查询...rotationChartService.queryRotationChartList....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ROTATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rotationChartService");
		input.setMethod("queryRotationChartList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 新增轮播图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveRotationChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("新增轮播图...rotationChartService.saveRotationChartInfo....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ROTATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rotationChartService");
		input.setMethod("saveRotationChartInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 轮播图详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryRotationChartDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("初始化查询详情查询...rotationChartService.queryRotationChartDetail....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ROTATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rotationChartService");
		input.setMethod("queryRotationChartDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑轮播图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateRotationChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("编辑轮播图...rotationChartService.updateRotationChartInfo....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ROTATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rotationChartService");
		input.setMethod("updateRotationChartInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除轮播图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void deleteRotationChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("删除轮播图...rotationChartService.deleteRotationChartInfo....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ROTATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rotationChartService");
		input.setMethod("deleteRotationChartInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 修改轮播图在线状态
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateOnlineStatus(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("修改轮播图在线状态...rotationChartService.updateOnlineStatus....");
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ROTATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("rotationChartService");
		input.setMethod("updateOnlineStatus");
		getNzbDataService().execute(input, output);
	}

}
