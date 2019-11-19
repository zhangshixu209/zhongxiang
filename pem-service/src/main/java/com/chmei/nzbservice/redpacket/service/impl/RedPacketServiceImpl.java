package com.chmei.nzbservice.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.redpacket.service.IImgRedPacketService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 众享红包service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点46分
 *
 */
@Service("redPacketService")
public class RedPacketServiceImpl extends BaseServiceImpl implements IImgRedPacketService {
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(RedPacketServiceImpl.class);

	/**
	 * 众享红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveImgRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("众享红包新增...redPacketService.saveImgRedPacketInfo....");
		input.setService("redPacketService");
		input.setMethod("saveImgRedPacketInfo");
		getNzbDataService().execute(input, output);
	}


}
