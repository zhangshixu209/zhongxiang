package com.chmei.nzbservice.user.service.impl;

import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.user.service.IRoleUserService;

@Service("roleUserService")
public class RoleUserServiceImpl extends BaseServiceImpl implements IRoleUserService {
	
	@Override
	public void addRoleUserForUser(InputDTO input, OutputDTO output) {
		input.setService("tURoleUserService");
		input.setMethod("insertRoleUserForUser");
		getNzbDataService().execute(input,output);
	}
	
	@Override
	public void removeRoleUserForUser(InputDTO input, OutputDTO output) {
		input.setService("tURoleUserService");
		input.setMethod("deleteRoleUserForUser");
		getNzbDataService().execute(input,output);
	}
	
	@Override
	public void queryRoleUserList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleUserService");
		input.setMethod("queryRoleAdminList");
		getNzbDataService().execute(input,output);
	}
	
	@Override
	public void queryRoleErUserList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleUserService");
		input.setMethod("queryRoleErAdminList");
		getNzbDataService().execute(input,output);
	}


	@Override
	public void updateRoleUserByRole(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleUserService");
		input.setMethod("updateRoleUserByRole");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void updateRoleUserByUser(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleUserService");
		input.setMethod("updateRoleUserByUser");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}
	
	@Override
	public void queryRoleUserAll(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleUserService");
		input.setMethod("queryAll");
		getNzbDataService().execute(input,output);
	}
}
  
