package com.chmei.nzbdata.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享红包dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 14点58分
 *
 */
public interface IRedPacketService {
	/**
	 * 众享红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void saveRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 返回红包信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void updateImgRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbDataException;

}
