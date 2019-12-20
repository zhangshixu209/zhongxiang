package com.chmei.nzbservice.operatelog.service.impl;

import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;

/**
 * 操作日志接口
 * @author zhangsx
 * @since 2019年05月10日 10点30分
 *
 */
@Service("operateLogService")
public class OperateLogServiceImpl extends BaseServiceImpl implements IOperateLogService   {

    /**
     * 组织机构管理查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    @Override
    public void queryOperateLogList(InputDTO input, OutputDTO output)
            throws NzbServiceException {
        input.setService("operateLogService");
        input.setMethod("queryOperateLogList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 组织机构管理新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    @Override
    public void saveOperateLogInfo(InputDTO input, OutputDTO output)
            throws NzbServiceException {
        input.setService("operateLogService");
        input.setMethod("saveOperateLogInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 登录日志新增
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveLoginLogInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("operateLogService");
        input.setMethod("saveLoginLogInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 登录日志列表查询
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryLoginLogList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("operateLogService");
        input.setMethod("queryLoginLogList");
        getNzbDataService().execute(input, output);
    }
}
