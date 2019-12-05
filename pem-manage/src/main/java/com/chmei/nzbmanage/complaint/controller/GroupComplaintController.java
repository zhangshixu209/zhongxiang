package com.chmei.nzbmanage.complaint.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.complaint.bean.GroupComplaintForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 反馈意见控制器
 * 
 * @author zhangshixu
 * @since 2019年10月22日 14点53分
 */
@RestController
@RequestMapping("/api/groupComplaint")
public class GroupComplaintController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(GroupComplaintController.class);
    
    /**
     * 
     * 初始化加载群投诉查询列表
     * @param groupComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGroupComplaintList")
    public OutputDTO queryGroupComplaintList(@ModelAttribute GroupComplaintForm groupComplaintForm) {
    	LOGGER.info("初始化加载群投诉查询列表...ComplaintController.queryGroupComplaintList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(groupComplaintForm);
        outputDTO = getOutputDTO(params, "groupComplaintService", "queryGroupComplaintList");
        return outputDTO;
    }

    /**
     *
     * 初始化加载群投诉查询详情
     * @param groupComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGroupComplaintDetail")
    public OutputDTO queryGroupComplaintDetail(@ModelAttribute GroupComplaintForm groupComplaintForm) {
        LOGGER.info("初始化加载群投诉查询详情...ComplaintController.queryGroupComplaintDetail()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(groupComplaintForm);
        outputDTO = getOutputDTO(params, "groupComplaintService", "queryGroupComplaintDetail");
        return outputDTO;
    }

    /**
     *
     * 更新群投诉状态
     * @param groupComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateGroupComplaintInfo")
    public OutputDTO updateGroupComplaintInfo(@ModelAttribute GroupComplaintForm groupComplaintForm) {
        LOGGER.info("更新群投诉状态...ComplaintController.updateGroupComplaintInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(groupComplaintForm);
        outputDTO = getOutputDTO(params, "groupComplaintService", "updateGroupComplaintInfo");
        return outputDTO;
    }

    /**
     *
     * 新增群投诉
     * @param groupComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveGroupComplaintInfo")
    public OutputDTO saveGroupComplaintInfo(@ModelAttribute GroupComplaintForm groupComplaintForm) {
        LOGGER.info("新增群投诉接口...ComplaintController.saveGroupComplaintInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(groupComplaintForm);
        outputDTO = getOutputDTO(params, "groupComplaintService", "saveGroupComplaintInfo");
        return outputDTO;
    }

}
