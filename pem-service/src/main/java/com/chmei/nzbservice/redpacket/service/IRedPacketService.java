package com.chmei.nzbservice.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享红包service接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点45分
 *
 */
public interface IRedPacketService {
	/**
	 * 众享红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
