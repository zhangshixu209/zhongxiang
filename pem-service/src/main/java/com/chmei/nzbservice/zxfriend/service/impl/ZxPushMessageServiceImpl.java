package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IZxMyTeamService;
import com.chmei.nzbservice.zxfriend.service.IZxPushMessageService;
import org.springframework.stereotype.Service;

/**
 * 消息推送service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点12分
 *
 */
@Service("zxPushMessageService")
public class ZxPushMessageServiceImpl extends BaseServiceImpl implements IZxPushMessageService {

	/**
	 * 查询推送消息列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryPushMessageList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxPushMessageService");
		input.setMethod("queryPushMessageList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 修改推送消息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updatePushMessageStatus(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxPushMessageService");
		input.setMethod("updatePushMessageStatus");
		getNzbDataService().execute(input, output);
	}
}
