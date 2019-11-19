package com.chmei.nzbservice.dubbo.provider;

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
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * pem-service工程的服务提供者
 */
@Service(timeout = 60000, retries = -1, group = "pem-service")
public class ControlServiceProvider implements IControlService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ControlServiceProvider.class);
	
    @Autowired
	private ApplicationContext applicationContext;

    public OutputDTO execute(InputDTO inputDTO) {
        OutputDTO outputDTO = new OutputDTO(ControlConstants.RETURN_CODE.IS_OK);
        try {
            outputDTO.setCode(ControlConstants.RETURN_CODE.IS_OK);
            if (inputDTO != null) {
                Object object = applicationContext.getBean(inputDTO.getService());
                Method mth = object.getClass().getMethod(inputDTO.getMethod(), InputDTO.class, OutputDTO.class);
                mth.invoke(object, inputDTO, outputDTO);
            } else {
                throw new NzbServiceException("InputDTO can't be null !!!");
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            // 异常处理
            outputDTO.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
            outputDTO.setMsg("服务异常");
        }
        return outputDTO;
    }
}
