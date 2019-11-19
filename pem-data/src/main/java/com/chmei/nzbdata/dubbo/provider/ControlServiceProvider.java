package com.chmei.nzbdata.dubbo.provider;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.dubbo.config.annotation.Service;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;
import com.chmei.nzbcommon.cmutil.ControlConstants;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbdata.common.exception.NzbDataException;
/**
 * nzb-data工程的服务提供者
 */
@Service(timeout = 60000, retries = -1, group = "pem-data")
public class ControlServiceProvider  implements IControlService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControlServiceProvider.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public OutputDTO execute(InputDTO inputDTO) {
		OutputDTO outputDTO = new OutputDTO(ControlConstants.RETURN_CODE.IS_OK);
		try {
			outputDTO.setCode(ControlConstants.RETURN_CODE.IS_OK);
			if (inputDTO != null) {
				LOGGER.info("InputDTO={}", JsonUtil.convertObject2Json(inputDTO));
				Object object = applicationContext.getBean(inputDTO.getService());
				Method mth = object.getClass().getMethod(inputDTO.getMethod(), InputDTO.class, OutputDTO.class);
				mth.invoke(object, inputDTO ,outputDTO);
				LOGGER.info("OutputDTO={}", JsonUtil.convertObject2Json(outputDTO));
			}else{
				throw new NzbDataException("InputDTO can't be null !!!");
			}
		} catch (Exception e) {
			LOGGER.error("ControlServiceProvider，调用后台服务方法execute异常", e);
			// 异常处理
			outputDTO.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
            outputDTO.setMsg("服务异常");
			
		}
		return outputDTO;
	}
}
