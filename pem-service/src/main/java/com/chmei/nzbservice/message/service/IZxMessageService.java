package com.chmei.nzbservice.message.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享信息service接口
 * 
 * @author zhangshixu
 * @since 2019年11月11日 09点27分
 *
 */
public interface IZxMessageService {

	/**
	 * 众享信息新增
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveZxMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息列表查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryZxMessageList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息点赞统计
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveZxMessagePraiseCount(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息浏览统计
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveZxMessageBrowseCount(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息收藏
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveZxMessageCollection(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息删除我的收藏
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void delZxMessageCollection(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息删除我的发布
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void delMyZxMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息我的收藏列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryMyZxMessageCollection(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享信息详情查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryZxMessageDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

}
