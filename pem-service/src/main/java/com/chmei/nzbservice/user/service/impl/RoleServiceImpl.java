package com.chmei.nzbservice.user.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements IRoleService {

	/**
	 * 调用操作日志
	 */
	@Autowired
	private IOperateLogService operateLogService;

	@Override
	public void queryRolesForUser(InputDTO input, OutputDTO output) {
		input.setService("tURoleService");
		input.setMethod("selectRolesForUser");
		getNzbDataService().execute(input,output);
	}
	
	@Override
	public void queryRoleDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.ROLE_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_READDETAIL_CD);
		input.setService("tURoleService");
		input.setMethod("queryObject");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryRoleList(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.ROLE_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD);
		input.setService("tURoleService");
		input.setMethod("queryList");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void saveRole(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.ROLE_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD);
		input.setService("tURoleService");
		input.setMethod("save");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void deleteRole(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.ROLE_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD);
		input.setService("tURoleService");
		input.setMethod("delete");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void updateRole(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.ROLE_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD);
		input.setService("tURoleService");
		input.setMethod("update");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void deleteRoleBatch(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleService");
		input.setMethod("deleteBatch");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryAllRole(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleService");
		input.setMethod("queryAllRole");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryRoleListWithHasAdmin(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURoleService");
		input.setMethod("queryRoleListWithHasAdmin");
		getNzbDataService().execute(input,output);
	}

	private void saveOperateMessage(InputDTO input, OutputDTO output,String pageCode,String operateCode){
		if ("0".equals(output.getCode())) {
			//成功插入操作日志信息
			input.getParams().put("operatePage",pageCode); //操作页面代码
			input.getParams().put("operateType",operateCode); //操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
	}
}
  
