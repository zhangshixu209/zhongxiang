package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享好友群管理service接口
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点30分
 *
 */
public interface IZxChatGroupService {

	/**
	 * 升级群组容量
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void upgradeChatGroupInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
