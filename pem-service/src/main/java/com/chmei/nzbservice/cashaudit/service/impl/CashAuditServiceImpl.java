package com.chmei.nzbservice.cashaudit.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.cashaudit.service.ICashAuditService;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志接口
 * @author zhangsx
 * @since 2019年05月10日 10点30分
 *
 */
@Service("cashAuditService")
public class CashAuditServiceImpl extends BaseServiceImpl implements ICashAuditService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(CashAuditServiceImpl.class);

    /**
     * 操作日志对象
     */
    @Autowired
    private IOperateLogService operateLogService;

    /**
     * 查询提现审核列表
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    @Override
    public void queryCashAuditList(InputDTO input, OutputDTO output)
            throws NzbServiceException {
        LOGGER.info("查询提现审核列表...cashAuditService.queryCashAuditList....");
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.CASH_AUDIT_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("cashAuditService");
        input.setMethod("queryCashAuditList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 修改提现审核状态
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateCashAuditInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        LOGGER.info("编辑提现审核...cashAuditService.updateCashAuditInfo....");
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.CASH_AUDIT_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("cashAuditService");
        input.setMethod("updateCashAuditInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 提现审核查询详情
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryCashAuditDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
        LOGGER.info("查询提现审核列表...cashAuditService.queryCashAuditDetail....");
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.CASH_AUDIT_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("cashAuditService");
        input.setMethod("queryCashAuditDetail");
        getNzbDataService().execute(input, output);
    }

    /**
     * 提现审核新增
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveCashAuditInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        LOGGER.info("提现审核新增...cashAuditService.saveCashAuditInfo....");
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.CASH_AUDIT_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("cashAuditService");
        input.setMethod("saveCashAuditInfo");
        getNzbDataService().execute(input, output);
    }

}
