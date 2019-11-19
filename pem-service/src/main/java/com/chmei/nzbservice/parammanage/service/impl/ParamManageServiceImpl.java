package com.chmei.nzbservice.parammanage.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.parammanage.service.IParamManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 参数管理和字典表配置实现
 * @author 翟超锋
 * @since 2019年05月07日 15点30分
 *
 */
@Service("paramManageService")
public class ParamManageServiceImpl extends BaseServiceImpl implements IParamManageService {

	
	 /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ParamManageServiceImpl.class);

	/**
	 * 调用操作日志
	 */
	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 参数管理和字典表配置查询
	 */
    @Override
    public void queryParamManageList(InputDTO input, OutputDTO output)  throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PARAMS_CONFIG_CD,Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD);
		input.setService("paramManageService");
		input.setMethod("queryParamManageList");
    	LOGGER.info("ParamManageServiceImpl.queryParamManageList...参数管理和字典表配置查询...");
        getNzbDataService().execute(input, output);
    }

    /**
     * 新增和修改公用
     */
	@Override
	public void addEditParamManage(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PARAMS_CONFIG_CD,Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD);
		input.setService("paramManageService");
		input.setMethod("addEditParamManage");
		LOGGER.info("ParamManageServiceImpl.addEditParamManage...新增和修改公用...");
		getNzbDataService().execute(input, output);
		
	}

	/**
	 * 根据id做逻辑删除
	 */
	@Override
	public void delParamManage(InputDTO input, OutputDTO output) throws NzbServiceException {
		saveOperateMessage(input,output, Constants.OPERATE_PAGE_CD.PARAMS_CONFIG_CD,Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD);
		input.setService("paramManageService");
		input.setMethod("delParamManage");
		LOGGER.info("ParamManageServiceImpl.delParamManage... 根据id做逻辑删除...");
		getNzbDataService().execute(input, output);
		
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
