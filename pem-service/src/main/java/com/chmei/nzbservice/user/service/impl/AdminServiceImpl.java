package com.chmei.nzbservice.user.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.user.service.IAdminService;
import com.chmei.nzbservice.util.StringUtil;

@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl implements IAdminService {

	/**
	 * log
	 */
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	/**
	 * 调用操作日志
	 */
	@Autowired
	private IOperateLogService operateLogService;

	@Override
	public void queryAdminDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tUAdminService");
		input.setMethod("queryObject");
		getNzbDataService().execute(input,output);

	}

	@Override
	public void queryAdminList(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.USER_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD);
		input.setService("tUAdminService");
		input.setMethod("queryList");
		getNzbDataService().execute(input, output);
		List<Map<String, Object>> list = (List<Map<String, Object>>) output.getItems();
		// 身份证号码脱敏
		if (null != list && !list.isEmpty()) {
		    for (Map<String, Object> map : list) {
	            String credNum = (String) map.get("extend1");
	            if (!StringUtil.isEmpty(credNum)) {
	                map.put("credNum", StringUtil.desensitizationCredNum(credNum));
                }
	        }
        }
	}

	@Override
	public void saveAdmin(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.USER_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD);
		input.setService("tUAdminService");
		input.setMethod("save");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void updateAdmin(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.USER_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD);
		input.setService("tUAdminService");
		input.setMethod("update");
		setOperLogMatcher(input);
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryAdminListByHasRoleState(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tUAdminService");
		input.setMethod("queryListWithRole");
		getNzbDataService().execute(input,output);
	}

	@Override
	public void queryAdminByLoginId(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("tUAdminService");
		input.setMethod("queryAdminByLoginId");
		getNzbDataService().execute(input,output);
	}
	
	/**
	 * 根据id删除用户
	 */
	@Override
    public void delAdminDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		Map<String, Object> params = input.getParams();
		Object object = params.get("id");
		if(object.equals(Constants.ROOT_ROLE)){
			output.setCode("-1");
			output.setMsg("特殊管理员不予许删除!");
			logger.info("特殊管理员不予许删除!-----id:{}",object);
			return;
		}
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.USER_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD);
        input.setService("tUAdminService");
        input.setMethod("delAdminDetail");
        getNzbDataService().execute(input, output);
    }
	
	/**
	 * 设置账号有效和无效
	 */
	@Override
    public void enableORdisable(InputDTO input, OutputDTO output) throws NzbServiceException {
		String validStsCd = (String)input.getParams().get("validStsCd") ;
		if("1".equals(validStsCd)){
			saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.USER_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_ABLE_CD);
		}else {
			saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.USER_MANAGE_CD,Constants.OPERATE_TYPE_CD.OPERATE_DISABLE_CD);
		}
        input.setService("tUAdminService");
        input.setMethod("enableORdisable");
        getNzbDataService().execute(input, output);
    }
	
    @Override
    public void queryAdminByMobile(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("tUAdminService");
        input.setMethod("queryAdminByMobile");
        getNzbDataService().execute(input, output);
    }

    @Override
    public void updateAdminPwd(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException {
        inputDTO.setService("tUAdminService");
        inputDTO.setMethod("updateAdminPwd");
        getNzbDataService().execute(inputDTO, outputDTO);
    }
    
    /**
     * 获取部门人员树状
     * @param inputDTO 入参
     * @param outputDTO 返回参数
     * @throws NzbServiceException   异常
     */
    @Override
    public void getDeepartmentUserTree(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException {
        inputDTO.setService("tUAdminService");
        inputDTO.setMethod("getDeepartmentUserTree");
        getNzbDataService().execute(inputDTO, outputDTO);
    }
    
    /**
     * 报表配置人员权限的时候获取部门和人员树状，需要过滤没有分类权限人员 
     * @param inputDTO 入参
     * @param outputDTO 返回参数
     * @throws NzbServiceException   异常
     */
    @Override
    public void getReportUserTree(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException {
    	inputDTO.setService("tUAdminService");
    	inputDTO.setMethod("getReportUserTree");
    	getNzbDataService().execute(inputDTO, outputDTO);
    }

	private void saveOperateMessage(InputDTO input, OutputDTO output,String pageCode,String operateCode){
		if ("0".equals(output.getCode())) {
			//成功插入操作日志信息
			input.getParams().put("operatePage",pageCode); //操作页面代码
			input.getParams().put("operateType",operateCode); //操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
	}

	/**
     * 批量导入用户数据
     * @param input 入参
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void importExcelUserData(InputDTO inputDTO, OutputDTO outputDTO)
            throws NzbServiceException {
        if ("0".equals(outputDTO.getCode())) {
            // 成功插入操作日志信息
            inputDTO.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.DATATABLE_MANAGE_CD); // 操作页面代码
            inputDTO.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_DATA_IMPORT); // 操作类型代码
            operateLogService.saveOperateLogInfo(inputDTO, outputDTO);
        }
        inputDTO.setService("tUAdminService");
        inputDTO.setMethod("importExcelUserData");
        getNzbDataService().execute(inputDTO, outputDTO);
    }

    /**
     * 获取所有用户及部门权限信息
     * @param input 入参
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void getDataTableUserTree(InputDTO inputDTO, OutputDTO outputDTO)
            throws NzbServiceException {
        inputDTO.setService("tUAdminService");
        inputDTO.setMethod("getDataTableUserTree");
        getNzbDataService().execute(inputDTO, outputDTO);
    }

}
  
