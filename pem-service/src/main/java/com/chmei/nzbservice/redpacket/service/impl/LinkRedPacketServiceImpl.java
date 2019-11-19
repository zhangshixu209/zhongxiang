package com.chmei.nzbservice.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.redpacket.service.ILinkRedPacketService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 链接红包service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点41分
 *
 */
@Service("linkRedPacketService")
public class LinkRedPacketServiceImpl extends BaseServiceImpl implements ILinkRedPacketService {
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(LinkRedPacketServiceImpl.class);

	/**
	 * 链接红包发布新增
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveLinkRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("链接红包新增...LinkRedPacketService.saveLinkRedPacketInfo....");
		input.setService("linkRedPacketService");
		input.setMethod("saveLinkRedPacketInfo");
		getNzbDataService().execute(input, output);
	}


}
