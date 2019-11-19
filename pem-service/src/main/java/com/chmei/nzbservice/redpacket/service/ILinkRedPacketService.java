package com.chmei.nzbservice.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 链接红包service接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点40分
 *
 */
public interface ILinkRedPacketService {
	/**
	 * 链接红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveLinkRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
