package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IZxChatGroupService;
import org.springframework.stereotype.Service;

/**
 * 众享好友群管理service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxChatGroupService")
public class ZxChatGroupServiceImpl extends BaseServiceImpl implements IZxChatGroupService {
	
	/**
	 * 升级群组容量
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void upgradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxChatGroupService");
		input.setMethod("upgradeChatGroupInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 校验当前群组升级费用
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void checkGradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxChatGroupService");
		input.setMethod("checkGradeChatGroupInfo");
		getNzbDataService().execute(input, output);
	}

}
