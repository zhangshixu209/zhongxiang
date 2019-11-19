package com.chmei.nzbservice.cashaudit.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 提现审核接口
 * @author zhangshixu
 * @since 2019年10月23日 09点39分
 *
 */
public interface ICashAuditService {

    /**
     * 提现审核查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void queryCashAuditList(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 修改提现审核状态
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void updateCashAuditInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 提现审核查询详情
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void queryCashAuditDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 提现审核新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void saveCashAuditInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
}
