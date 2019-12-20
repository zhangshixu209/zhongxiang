package com.chmei.nzbdata.operatelog.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.orderutils.IdGeneratorUtils;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.operatelog.service.IOperateLogService;

/**
 * 操作日志接口
 * @author zhangsx
 * @since 2019年05月10日 10点30分
 *
 */
@Service("operateLogService")
public class OperateLogServiceImpl extends BaseServiceImpl implements IOperateLogService   {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(OperateLogServiceImpl.class);
    
    /**
     * 操作日志管理查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void queryOperateLogList(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("operateLogMapper.queryOperateLogCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao()
                        .queryForList("operateLogMapper.queryOperateLogList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 操作日志管理新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void saveOperateLogInfo(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //生成主键ID
            Long id = getSequence();
            input.getParams().put("id", id);
            input.getParams().put("logNum", IdGeneratorUtils.nextId("RZ"));
            input.getParams().put("crtUserId", input.getComParams().get("accountId"));
            input.getParams().put("crtUserName", input.getComParams().get("operationUserName"));
            Integer count = getBaseDao().insert("operateLogMapper.saveOperateLogInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
        } catch (Exception e) {
            LOGGER.error("新增失败: " + e);
        }
    }

    /**
     * 登录日志新增
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void saveLoginLogInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //生成主键ID
            input.getParams().put("id", getSequence());
            Integer count = getBaseDao().insert("operateLogMapper.saveLoginLogInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
        } catch (Exception e) {
            LOGGER.error("新增失败: " + e);
        }
    }

    /**
     * 登录日志列表查询
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void queryLoginLogList(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("operateLogMapper.queryLoginLogCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao()
                        .queryForList("operateLogMapper.queryLoginLogList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }
}
