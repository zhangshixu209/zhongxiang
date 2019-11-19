package com.chmei.nzbmanage.dubbo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;
import com.chmei.nzbcommon.cmutil.ControlConstants.RETURN_CODE;
import com.chmei.nzbcommon.cmutil.JsonUtil;

@Component
public class NzbServiceControlServiceConsumer implements IControlService {
	
	private static final Logger logger = LoggerFactory.getLogger(NzbServiceControlServiceConsumer.class);
	
	@Reference(timeout = 60000, retries = -1, group = "pem-service", check = false)
	private IControlService controlService;
	
	public OutputDTO execute(InputDTO inputDTO) {
		OutputDTO outputDTO = null;

		long start = System.currentTimeMillis();
		logger.info("START INVOKE WEBAPP...");
		logger.info("InputDTO={}", JsonUtil.convertObject2Json(inputDTO));
		try {
			// 调用WebApp服务
			outputDTO = controlService.execute(inputDTO);
		} catch (Exception e) {
			logger.error("Error:", "INVOKE WEBAPP ERROR!", e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
			outputDTO = new OutputDTO();
			outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
			outputDTO.setMsg("异常");
		}
		long end = System.currentTimeMillis();
		logger.info("OutputDTO={}", JsonUtil.convertObject2Json(outputDTO));
		logger.info("END INVOKE WEBAPP...", String.valueOf(end - start) + "ms");
		return outputDTO;
	}

	

}
