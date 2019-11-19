package com.chmei.nzbservice.user.service.impl;

import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.user.service.IRoleRightService;

@Service("roleRightService")
public class RoleRightServiceImpl extends BaseServiceImpl  implements IRoleRightService {

	@Override
	public void updateRoleRight(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleRightService");
		input.setMethod("update");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryRightListByRoleId(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleRightService");
		input.setMethod("queryRightListByRoleId");
		getNzbDataService().execute(input,output); 
	}
}
  
