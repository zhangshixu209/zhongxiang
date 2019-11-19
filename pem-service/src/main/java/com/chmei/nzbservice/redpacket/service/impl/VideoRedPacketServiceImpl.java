package com.chmei.nzbservice.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.redpacket.service.IVideoRedPacketService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 视频红包service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点44分
 *
 */
@Service("videoRedPacketService")
public class VideoRedPacketServiceImpl extends BaseServiceImpl implements IVideoRedPacketService {
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(VideoRedPacketServiceImpl.class);

	/**
	 * 视频红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveVideoRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("视频红包新增...videoRedPacketService.saveVideoRedPacketInfo....");
		input.setService("videoRedPacketService");
		input.setMethod("saveVideoRedPacketInfo");
		getNzbDataService().execute(input, output);
	}


}
