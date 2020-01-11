package com.chmei.nzbdata.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享好友群管理dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月13日 10点21分
 *
 */
public interface IZxChatGroupService {
	/**
	 * 升级群组容量
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void upgradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 校验当前群组升级费用
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void checkGradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbDataException;

}
