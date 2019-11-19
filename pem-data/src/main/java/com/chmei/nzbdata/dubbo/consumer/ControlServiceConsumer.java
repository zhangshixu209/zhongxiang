package com.chmei.nzbdata.dubbo.consumer;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;

/**
 * nzb-data工程的服务消费者，用于自己小范围测试
 * @author lixinjie
 * @since 2018-11-28
 */
@Component
public class ControlServiceConsumer implements IControlService {

	@Reference(timeout = 60000, retries = -1, group = "pem-data", check = false)
	private IControlService controlService;
	
	@Override
	public OutputDTO execute(InputDTO inputDTO) {
		return controlService.execute(inputDTO);
	}

}
