package com.chmei.nzbservice.message.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.message.service.IZxMessageService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 众享信息service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月11日 09点28分
 *
 */
@Service("zxMessageService")
public class ZxMessageServiceImpl extends BaseServiceImpl implements IZxMessageService {

	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(ZxMessageServiceImpl.class);

	/**
	 * 众享信息新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveZxMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息新增...zxMessageService.saveZxMessageInfo....");
		input.setService("zxMessageService");
		input.setMethod("saveZxMessageInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息列表查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxMessageList(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息列表查询...zxMessageService.saveZxMessageInfo....");
		input.setService("zxMessageService");
		input.setMethod("queryZxMessageList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息点赞统计
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveZxMessagePraiseCount(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息点赞统计...zxMessageService.saveZxMessagePraiseCount....");
		input.setService("zxMessageService");
		input.setMethod("saveZxMessagePraiseCount");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息浏览统计
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveZxMessageBrowseCount(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息浏览统计...zxMessageService.saveZxMessageBrowseCount....");
		input.setService("zxMessageService");
		input.setMethod("saveZxMessageBrowseCount");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息收藏
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveZxMessageCollection(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息收藏...zxMessageService.saveZxMessageCollection....");
		input.setService("zxMessageService");
		input.setMethod("saveZxMessageCollection");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息删除我的收藏
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delZxMessageCollection(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息删除我的收藏...zxMessageService.delZxMessageCollection....");
		input.setService("zxMessageService");
		input.setMethod("delZxMessageCollection");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息删除我的发布
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delMyZxMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息删除我的发布...zxMessageService.delMyZxMessageInfo....");
		input.setService("zxMessageService");
		input.setMethod("delMyZxMessageInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息我的收藏列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryMyZxMessageCollection(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息我的收藏列表...zxMessageService.queryMyZxMessageCollection....");
		input.setService("zxMessageService");
		input.setMethod("queryMyZxMessageCollection");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享信息详情查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxMessageDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享信息详情查询...zxMessageService.queryZxMessageDetail....");
		input.setService("zxMessageService");
		input.setMethod("queryZxMessageDetail");
		getNzbDataService().execute(input, output);
	}

}
