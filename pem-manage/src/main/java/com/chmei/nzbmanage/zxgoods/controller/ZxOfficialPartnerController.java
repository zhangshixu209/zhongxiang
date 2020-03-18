package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxgoods.bean.ZxOfficialPartnerForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 合作商控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点57分
 */
@RestController
@RequestMapping("/api/partner")
public class ZxOfficialPartnerController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxOfficialPartnerController.class);
    
    /**
     * 初始化加载合作商查询列表
     *
     * @param zxOfficialPartnerForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOfficialPartnerList")
    public OutputDTO queryOfficialPartnerList(@ModelAttribute ZxOfficialPartnerForm zxOfficialPartnerForm) {
    	LOGGER.info("初始化加载合作商查询列表...RotationChartController.queryOfficialPartnerList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOfficialPartnerForm);
        outputDTO = getOutputDTO(params, "zxOfficialPartnerService", "queryOfficialPartnerList");
        return outputDTO;
    }
    
    /**
     * 新增合作商
     * @param zxOfficialPartnerForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveOfficialPartnerInfo")
    public OutputDTO saveOfficialPartnerInfo(@ModelAttribute ZxOfficialPartnerForm zxOfficialPartnerForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOfficialPartnerForm);
        outputDTO = getOutputDTO(params, "zxOfficialPartnerService", "saveOfficialPartnerInfo");
        return outputDTO;
    }

    /**
     * 合作商详情
     * @param zxOfficialPartnerForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOfficialPartnerDetail")
    public OutputDTO queryOfficialPartnerDetail(@ModelAttribute ZxOfficialPartnerForm zxOfficialPartnerForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOfficialPartnerForm);
        outputDTO = getOutputDTO(params, "zxOfficialPartnerService", "queryOfficialPartnerDetail");
        return outputDTO;
    }

    /**
     * 编辑合作商
     * @param zxOfficialPartnerForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateOfficialPartnerInfo")
    public OutputDTO updateOfficialPartnerInfo(@ModelAttribute ZxOfficialPartnerForm zxOfficialPartnerForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOfficialPartnerForm);
        outputDTO = getOutputDTO(params, "zxOfficialPartnerService", "updateOfficialPartnerInfo");
        return outputDTO;
    }

    /**
     * 删除合作商
     * @param zxOfficialPartnerForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delOfficialPartnerInfo")
    public OutputDTO delOfficialPartnerInfo(@ModelAttribute ZxOfficialPartnerForm zxOfficialPartnerForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOfficialPartnerForm);
        outputDTO = getOutputDTO(params, "zxOfficialPartnerService", "delOfficialPartnerInfo");
        return outputDTO;
    }
}
