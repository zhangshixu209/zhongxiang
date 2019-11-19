package com.chmei.nzbmanage.dubbo.controller;

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
 * @author lixinjie
 * @since 2018-11-29
 */
@RequestMapping(path = "/api/pb/admin/dubbo")
@RestController
public class DubboController {

	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	private IHttpInvoker httpcomponentsHttpInvoker;
	
	@Autowired
	private IControlService nzbServiceControlServiceConsumer;
	
	@GetMapping(path = "/test")
	public Object test() {
		InputDTO input = new InputDTO();
		input.setService("adminService");
		input.setMethod("saveAdmin");
		input.getParams().put("crtUserId", 1);
		input.getParams().put("loginId", "aa");
		input.getParams().put("loginPw", "bb");
		input.getParams().put("userName", "cc");
		OutputDTO output = nzbServiceControlServiceConsumer.execute(input);
		return output;
	}
	
	
}
