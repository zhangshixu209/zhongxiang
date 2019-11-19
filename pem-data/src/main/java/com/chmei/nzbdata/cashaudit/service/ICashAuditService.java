package com.chmei.nzbdata.cashaudit.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 提现审核管理接口
 * @author zhangsx
 * @since 2019年05月13日 18点06分
 *
 */
public interface ICashAuditService {
    
    /**
     * 提现审核管理查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    void queryCashAuditList(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 提现审核管理新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    void saveCashAuditInfo(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 查看提现审核详情
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    void queryCashAuditDetail(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 修改提现审核状态
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    void updateCashAuditInfo(InputDTO input, OutputDTO output) throws NzbDataException;
    
}
