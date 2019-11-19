package com.chmei.nzbdata.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 链接红包dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 14点26分
 *
 */
public interface ILinkRedPacketService {
	/**
	 * 链接红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void saveLinkRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 返回红包信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void updateImgRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbDataException;

}
