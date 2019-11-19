package com.chmei.nzbservice.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 图文红包service接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点42分
 *
 */
public interface IImgRedPacketService {
	/**
	 * 图文红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveImgRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
