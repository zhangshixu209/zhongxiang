package com.chmei.nzbmanage.organiza.controller;

import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.organiza.bean.OrganizaManageForm;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;


/**
 * 组织机构管理查询
 *
 * @author zhangsx
 * @since 2019年05月07日 15点20分
 */
@RestController
@RequestMapping("/api/organizaManage")
public class OrganizaManageController extends BaseController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(OrganizaManageController.class);

    /**
     * 组织机构查询列表
     * @param organizaManageForm 入参 
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOrganizaManageList")
    public OutputDTO queryOrganizaManageList(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "queryOrganizaManageList");
        } catch (Exception ex) {
            LOGGER.error("查询错误", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 组织机构新增
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveOrganizaManageInfo")
    public OutputDTO saveOrganizaManageInfo(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "saveOrganizaManageInfo");
        } catch (Exception ex) {
            LOGGER.error("新增失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 组织机构删除
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delOrganizaManageInfo")
    public OutputDTO delOrganizaManageInfo(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        LOGGER.info("OrganizaManageController.delOrganizaManageInfo删除数据:" + organizaManageForm.getId());
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "delOrganizaManageInfo");
        } catch (Exception ex) {
            LOGGER.error("删除失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 组织机构更新
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateOrganizaManageInfo")
    public OutputDTO updateOrganizaManageInfo(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "updateOrganizaManageInfo");
        } catch (Exception ex) {
            LOGGER.error("更新失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 组织机构查看详情
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOrganizaManageDetail")
    public OutputDTO queryOrganizaManageDetail(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "queryOrganizaManageDetail");
        } catch (Exception ex) {
            LOGGER.error("查询详情失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 组织机构查看详情
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryParentOrganizaList")
    public OutputDTO queryParentOrganizaList(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "queryParentOrganizaList");
        } catch (Exception ex) {
            LOGGER.error("查询父机构失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 校验父机构是否被使用
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkParentOrganiza")
    public OutputDTO checkParentOrganiza(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "checkParentOrganiza");
        } catch (Exception ex) {
            LOGGER.error("查询失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
    
    /**
     * 校验机构是否关联了用户
     * @param organizaManageForm 入参
     * @param request request 请求对象
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkOrganizaUserDept")
    public OutputDTO checkOrganizaUserDept(@ModelAttribute OrganizaManageForm organizaManageForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(organizaManageForm);
            outputDTO = getOutputDTO(params, "organizaManageService", "checkOrganizaUserDept");
        } catch (Exception ex) {
            LOGGER.error("查询失败", ex);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }
}
