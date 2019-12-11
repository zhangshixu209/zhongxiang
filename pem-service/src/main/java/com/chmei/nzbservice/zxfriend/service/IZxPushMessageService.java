package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;


/**
 * 众享推送消息dao接口
 * 
 * @author zhangshixu
 * @since 2019年12月11日 10点41分
 *
 */
public interface IZxPushMessageService {

	/**
	 * 查询推送消息列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryPushMessageList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改推送消息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updatePushMessageStatus(InputDTO input, OutputDTO output) throws NzbServiceException;

}
