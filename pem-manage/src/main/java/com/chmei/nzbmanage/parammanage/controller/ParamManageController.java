package com.chmei.nzbmanage.parammanage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.parammanage.bean.ParamManageForm;


/**
 * 参数管理和字典表配置
 *
 * @author 翟超锋
 * @since 2019年05月07日 15点20分
 */
@RestController
@RequestMapping("/api/paramManage")
public class ParamManageController extends BaseController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ParamManageController.class);

    
    /**
     * 字典管理查询初始化
     * @param ParamManageForm 
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryParamManageList")
    public OutputDTO queryParamManageList(@ModelAttribute ParamManageForm paramManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(paramManageForm);
            outputDTO = getOutputDTO(params, "paramManageService", "queryParamManageList");
        } catch (Exception ex) {
            LOGGER.error("查询错误", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    
    
    /**
     * 新增和修改公用
     * @param ParamManageForm 
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/addEditParamManage")
    public OutputDTO addEditParamManage(@ModelAttribute ParamManageForm paramManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(paramManageForm);
            params.put("crtId", getCurrUserId());
            outputDTO = getOutputDTO(params, "paramManageService", "addEditParamManage");
        } catch (Exception ex) {
            LOGGER.error("查询错误", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
  
    
    
    /**
     * 字典表删除操作，逻辑删除不是物理删除
     * @param ParamManageForm 
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delParamManage")
    public OutputDTO delParamManage(@ModelAttribute ParamManageForm paramManageForm, HttpServletRequest request) {
        LOGGER.info("ParamManageController.delParamManage删除数据:" + paramManageForm.getId());
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(paramManageForm);
            outputDTO = getOutputDTO(params, "paramManageService", "delParamManage");
        } catch (Exception ex) {
            LOGGER.error("查询错误", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
