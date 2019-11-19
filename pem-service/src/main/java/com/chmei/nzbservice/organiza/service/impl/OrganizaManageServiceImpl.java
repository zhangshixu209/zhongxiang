package com.chmei.nzbservice.organiza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.organiza.service.IOrganizaManageService;

/**
 * 组织机构管理接口
 * 
 * @author zhangsx
 * @since 2019年05月10日 10点30分
 *
 */
@Service("organizaManageService")
public class OrganizaManageServiceImpl extends BaseServiceImpl implements IOrganizaManageService {

	/**
	 * 调用操作日志
	 */
	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 组织机构管理查询
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void queryOrganizaManageList(InputDTO input, OutputDTO output) throws NzbServiceException {
		if ("0".equals(output.getCode())) {
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ORGANIZATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
		input.setService("organizaManageService");
		input.setMethod("queryOrganizaManageList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 组织机构管理新增
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void saveOrganizaManageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("organizaManageService");
		input.setMethod("saveOrganizaManageInfo");
		getNzbDataService().execute(input, output);
		if ("0".equals(output.getCode())) {
		    OutputDTO outputdto = new OutputDTO();
			// 成功插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ORGANIZATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, outputdto);
		}
	}

	/**
	 * 组织机构管理查询详细信息
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void queryOrganizaManageDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("organizaManageService");
		input.setMethod("queryOrganizaManageDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 组织机构管理编辑
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void updateOrganizaManageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("organizaManageService");
		input.setMethod("updateOrganizaManageInfo");
		getNzbDataService().execute(input, output);
		if ("0".equals(output.getCode())) {
			// 成功,插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ORGANIZATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
	}

	/**
	 * 组织机构管理删除
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void delOrganizaManageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("organizaManageService");
		input.setMethod("delOrganizaManageInfo");
		getNzbDataService().execute(input, output);
		if ("0".equals(output.getCode())) {
			// 成功,插入操作日志信息
			input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.ORGANIZATION_MANAGE_CD); // 操作页面代码
			input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD); // 操作类型代码
			operateLogService.saveOperateLogInfo(input, output);
		}
	}

	/**
	 * 查询父机构名称
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void queryParentOrganizaList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("organizaManageService");
		input.setMethod("queryParentOrganizaList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 校验父机构是否被使用
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbServiceException
	 *             自定义异常
	 */
	@Override
	public void checkParentOrganiza(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("organizaManageService");
		input.setMethod("checkParentOrganiza");
		getNzbDataService().execute(input, output);
	}

    /**
     * 校验机构是否关联了用户
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    @Override
    public void checkOrganizaUserDept(InputDTO input, OutputDTO output)
            throws NzbServiceException {
        input.setService("organizaManageService");
        input.setMethod("checkOrganizaUserDept");
        getNzbDataService().execute(input, output);
    }

}
