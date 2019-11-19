package com.chmei.nzbservice.feedback.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 反馈意见service接口
 * 
 * @author 翟超锋
 * @since 2019年05月10日 15点30分
 *
 */
public interface IFeedbackService {
	/**
	 * 列表查询
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	void queryList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 详情查询by主键
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	void selectByPrimaryKey(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除by主键
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	void deleteByPrimaryKey(InputDTO input, OutputDTO output) throws NzbServiceException;


	/**
	 * 新增
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	void insertSelective(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	void doUpdateFeedback(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	
}
