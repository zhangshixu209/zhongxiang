package com.chmei.nzbmanage.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chmei.nzbcommon.cmbean.OutputDTO;

/**
 * AuthorizationController  
 * @author liu
 * @version
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/api/common")
public class AuthorizationController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping(value = "/getAuthorization",method=RequestMethod.POST)
	@ResponseBody
	
	public OutputDTO getAuthorization() {

		String service = "cinccService";
		String method = "getAuthorization1";
		Map params = new HashMap<String, Object>();
		OutputDTO out = super.getOutputDTO(params, service, method);
		logger.info("获取鉴权信息authorization={[]}",out.getData().toString());
		return out;
	}
	
	
	
}











