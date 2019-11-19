package com.chmei.nzbservice.dubbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;
import com.chmei.nzbcommon.http.invoker.IHttpInvoker;
import com.chmei.nzbcommon.idgene.IdGenerator;

/**
 * <p>本类的主要目的是用于dubbo测试。
 * <p>可以调用自己发布的dubbo服务，进行小范围测试
 * @author lixinjie
 * @since 2018-11-28
 */
@RequestMapping(path = "/dubbo")
@RestController
public class DubboController {

	@Autowired
	protected IdGenerator idGenerator;
	
	@Autowired
	protected IHttpInvoker httpcomponentsHttpInvoker;
	
	//nzb-service工程的消费者
	@Autowired
	private IControlService controlServiceConsumer;
	
	@GetMapping(path = "/test")
	public Object test() {
		InputDTO input = new InputDTO();
		input.setService("adminService");
		input.setMethod("saveAdmin");
		input.getParams().put("crtUserId", 1);
		input.getParams().put("loginId", "aa");
		input.getParams().put("loginPw", "bb");
		input.getParams().put("userName", "cc");
		OutputDTO output = controlServiceConsumer.execute(input);
		return output;
	}
}
