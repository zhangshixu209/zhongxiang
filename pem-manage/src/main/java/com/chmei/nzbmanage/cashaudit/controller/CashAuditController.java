package com.chmei.nzbmanage.cashaudit.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.cashaudit.bean.CashAuditForm;
import com.chmei.nzbmanage.common.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 提现审核控制器
 * 
 * @author zhangshixu
 * @since 2019年10月23日 09点33分
 */
@RestController
@RequestMapping("/api/cashAudit")
public class CashAuditController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(CashAuditController.class);

    /**
     * 初始化加载提现审核查询列表
     *
     * @param cashAuditForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryCashAuditList")
    public OutputDTO queryCashAuditList(@ModelAttribute CashAuditForm cashAuditForm) {
        LOGGER.info("初始化加载提现审核查询列表...CashAuditController.queryCashAuditList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(cashAuditForm);
        outputDTO = getOutputDTO(params, "cashAuditService", "queryCashAuditList");
        return outputDTO;
    }

    /**
     * 修改提现审核状态
     *
     * @param cashAuditForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateCashAuditInfo")
    public OutputDTO updateCashAuditInfo(@ModelAttribute CashAuditForm cashAuditForm) {
        LOGGER.info("修改提现审核状态...CashAuditController.updateCashAuditInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        // 修改人， 姓名，审核人
        cashAuditForm.setModfUserId(getCurrUserId());
        cashAuditForm.setModfUserName(getCurrUserName());
        cashAuditForm.setAuditUserName(getCurrUserName());
        Map<String, Object> params = BeanUtil.convertBean2Map(cashAuditForm);
        outputDTO = getOutputDTO(params, "cashAuditService", "updateCashAuditInfo");
        return outputDTO;
    }

    /**
     * 查询提现审核详情
     *
     * @param cashAuditForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryCashAuditDetail")
    public OutputDTO queryCashAuditDetail(@ModelAttribute CashAuditForm cashAuditForm) {
        LOGGER.info("查询提现审核详情...CashAuditController.queryCashAuditDetail()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(cashAuditForm);
        outputDTO = getOutputDTO(params, "cashAuditService", "queryCashAuditDetail");
        return outputDTO;
    }

    /**
     * 新增提现审核记录
     *
     * @param cashAuditForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveCashAuditInfo")
    public OutputDTO saveCashAuditInfo(@ModelAttribute CashAuditForm cashAuditForm) {
        LOGGER.info("提现审核新增...CashAuditController.saveCashAuditInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(cashAuditForm);
        outputDTO = getOutputDTO(params, "cashAuditService", "saveCashAuditInfo");
        return outputDTO;
    }

}
