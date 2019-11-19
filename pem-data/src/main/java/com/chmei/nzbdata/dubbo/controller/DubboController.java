package com.chmei.nzbdata.dubbo.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;
import com.chmei.nzbcommon.idgene.IdGenerator;
import com.chmei.nzbdata.user.service.TUAdminService;

/**
 * <p>本类的主要目的是用于dubbo测试。
 * <p>可以调用自己发布的dubbo服务，进行小范围测试
 * @author lixinjie
 * @since 2018-09-03
 */
@RequestMapping(path = "/dubbo")
@RestController
public class DubboController {

	@Autowired
	private IControlService controlServiceConsumer;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private IdGenerator idGenerator;
	
	@GetMapping(path = "/test")
	public OutputDTO test() {
		InputDTO input = new InputDTO();
		input.setService("tUAdminService");
		input.setMethod("save");
		input.getParams().put("crtUserId", 1);
		input.getParams().put("loginId", "aa");
		input.getParams().put("loginPw", "bb");
		input.getParams().put("userName", "cc");
		OutputDTO output = controlServiceConsumer.execute(input);
		return output;
	}
	
	@GetMapping(path = "/service")
	public String service() {
		String[] names = applicationContext.getBeanNamesForType(TUAdminService.class);
		return Arrays.asList(names).toString();
	}
	
	
	
	@GetMapping("/query-mem-audit-cmmts")
	public OutputDTO queryMemAuditCmmts(Integer pageNum, Integer pageSize) {
		InputDTO input = new InputDTO();
		input.setService("memVaryService");
		input.setMethod("queryMemAuditCmmts");
		input.getParams().put("memberId", 1010);
		input.getParams().put("pageNum", pageNum);
		input.getParams().put("pageSize", pageSize);
		OutputDTO output = controlServiceConsumer.execute(input);
		return output;
	}
	
	@GetMapping("/change-workplace")
	public OutputDTO changeWorkplace() {
		InputDTO input = new InputDTO();
		input.setService("memVaryService");
		input.setMethod("changeWorkplace");
		
		OutputDTO output = controlServiceConsumer.execute(input);
		return output;
	}
	
	@GetMapping("/query-memvaryrecords")
	public OutputDTO queryMemVaryRecords() {
		InputDTO input = new InputDTO();
		input.setService("memVaryService");
		input.setMethod("queryMemVaryRecords");
		//input.getParams().put("memberId", 1010);
		input.getParams().put("varyTypes", Arrays.asList(2, -2));
		input.getParams().put("pageNum", 1);
		input.getParams().put("pageSize", 4);
		OutputDTO output = controlServiceConsumer.execute(input);
		return output;
	}
	
	@GetMapping("/query-members")
	public OutputDTO queryMembers() {
		InputDTO input = new InputDTO();
		input.setService("memVaryService");
		input.setMethod("queryMembers");
		input.getParams().put("pageNum", 1);
		input.getParams().put("pageSize", 4);
		OutputDTO output = controlServiceConsumer.execute(input);
		return output;
	}
}
