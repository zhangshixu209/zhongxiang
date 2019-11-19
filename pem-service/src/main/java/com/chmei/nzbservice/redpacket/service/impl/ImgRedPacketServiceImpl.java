package com.chmei.nzbservice.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.redpacket.service.IImgRedPacketService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 图文红包service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点44分
 *
 */
@Service("imgRedPacketService")
public class ImgRedPacketServiceImpl extends BaseServiceImpl implements IImgRedPacketService {
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(ImgRedPacketServiceImpl.class);

	/**
	 * 图文红包发新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveImgRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("图文红包新增...imgRedPacketService.saveImgRedPacketInfo....");
		input.setService("imgRedPacketService");
		input.setMethod("saveImgRedPacketInfo");
		getNzbDataService().execute(input, output);
	}


}
