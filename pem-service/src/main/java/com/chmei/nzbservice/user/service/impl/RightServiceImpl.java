package com.chmei.nzbservice.user.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.user.service.IRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl  implements IRightService {

	/**
	 * 调用操作日志
	 */
	@Autowired
	private IOperateLogService operateLogService;

	@Override
	public void queryRightDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURightService");
		input.setMethod("queryObject");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void saveRight(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PERMISSION_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD);
		input.setService("tURightService");
		input.setMethod("save");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void deleteRight(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PERMISSION_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD);
		input.setService("tURightService");
		input.setMethod("delete");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void updateRight(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PERMISSION_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD);
		input.setService("tURightService");
		input.setMethod("update");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryRightTree(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PERMISSION_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD);
		input.setService("tURightService");
		input.setMethod("queryRightTree");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryAllRight(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURightService");
		input.setMethod("queryAllRight");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryRightByUrlAddr(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tURightService");
		input.setMethod("queryRightByUrlAddr");
		getNzbDataService().execute(input,output);
	}
	/**
     * 获取部门树   
     * @param input 入参
     * @param output 返回结果
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryUserDepartment(InputDTO input, OutputDTO output)
            throws NzbServiceException {
        input.setService("tURightService");
        input.setMethod("queryUserDepartment");
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
  
