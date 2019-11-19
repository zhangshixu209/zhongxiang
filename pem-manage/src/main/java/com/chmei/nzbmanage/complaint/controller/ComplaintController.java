package com.chmei.nzbmanage.complaint.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.complaint.bean.MemberComplaintForm;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/complaint")
public class ComplaintController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ComplaintController.class);
    
    /**
     * 
     * 初始化加载会员投诉查询列表
     * @param memberComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryMemberComplaintList")
    public OutputDTO queryMemberComplaintList(@ModelAttribute MemberComplaintForm memberComplaintForm) {
    	LOGGER.info("初始化加载会员投诉查询列表...ComplaintController.queryMemberComplaintList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(memberComplaintForm);
        outputDTO = getOutputDTO(params, "memberComplaintService", "queryMemberComplaintList");
        return outputDTO;
    }

    /**
     *
     * 初始化加载会员投诉查询详情
     * @param memberComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryMemberComplaintDetail")
    public OutputDTO queryMemberComplaintDetail(@ModelAttribute MemberComplaintForm memberComplaintForm) {
        LOGGER.info("初始化加载会员投诉查询详情...ComplaintController.queryMemberComplaintDetail()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(memberComplaintForm);
        outputDTO = getOutputDTO(params, "memberComplaintService", "queryMemberComplaintDetail");
        return outputDTO;
    }

    /**
     *
     * 更新会员投诉状态
     * @param memberComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateMemberComplaintInfo")
    public OutputDTO updateMemberComplaintInfo(@ModelAttribute MemberComplaintForm memberComplaintForm) {
        LOGGER.info("更新会员投诉状态...ComplaintController.updateMemberComplaintInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(memberComplaintForm);
        outputDTO = getOutputDTO(params, "memberComplaintService", "updateMemberComplaintInfo");
        return outputDTO;
    }

    /**
     *
     * 新增会员投诉
     * @param memberComplaintForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveMemberComplaintInfo")
    public OutputDTO saveMemberComplaintInfo(@ModelAttribute MemberComplaintForm memberComplaintForm) {
        LOGGER.info("新增会员投诉接口...ComplaintController.saveMemberComplaintInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(memberComplaintForm);
        outputDTO = getOutputDTO(params, "memberComplaintService", "saveMemberComplaintInfo");
        return outputDTO;
    }

}
