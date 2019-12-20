package com.chmei.nzbmanage.operatelog.controller;

import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.operatelog.bean.OperateLogForm;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;


/**
 * 操作日志
 *
 * @author zhangsx
 * @since 2019年05月07日 15点20分
 */
@RestController
@RequestMapping("/api/operateLog")
public class OperateLogController extends BaseController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(OperateLogController.class);

    /**
     * 操作日志列表
     * @param operateLogForm 入参 
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOperateLogList")
    public OutputDTO queryOperateLogList(@ModelAttribute OperateLogForm operateLogForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(operateLogForm);
            outputDTO = getOutputDTO(params, "operateLogService", "queryOperateLogList");
        } catch (Exception ex) {
            LOGGER.error("查询错误", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 操作日志新增
     * @param operateLogForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveOperateLogInfo")
    public OutputDTO saveOperateLogInfo(@ModelAttribute OperateLogForm operateLogForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(operateLogForm);
            outputDTO = getOutputDTO(params, "operateLogService", "saveOperateLogInfo");
        } catch (Exception ex) {
            LOGGER.error("新增失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 登录日志列表
     * @param operateLogForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryLoginLogList")
    public OutputDTO queryLoginLogList(@ModelAttribute OperateLogForm operateLogForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(operateLogForm);
            outputDTO = getOutputDTO(params, "operateLogService", "queryLoginLogList");
        } catch (Exception ex) {
            LOGGER.error("查询错误", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
}
