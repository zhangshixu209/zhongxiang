package com.chmei.nzbservice.operatelog.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 操作日志接口
 * @author zhangsx
 * @since 2019年05月13日 15点43分
 *
 */
public interface IOperateLogService {

    /**
     * 操作日志查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void queryOperateLogList(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 操作日志新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void saveOperateLogInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
    
}
