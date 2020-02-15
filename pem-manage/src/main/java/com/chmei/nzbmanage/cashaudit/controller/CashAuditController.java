package com.chmei.nzbmanage.cashaudit.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.excel.ExcelUtil;
import com.chmei.nzbcommon.util.excel.exception.ExcelException;
import com.chmei.nzbmanage.cashaudit.bean.CashAuditForm;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.member.bean.MemberAssetsForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
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

    /**
     * 资金状况导出
     * @param bean
     * @param response
     * @return OutputDTO
     *
     */
    @RequestMapping("/exportMoney")
    @ResponseBody
    public OutputDTO exportExcel(@ModelAttribute CashAuditForm bean, HttpServletResponse response) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(bean);
            outputDTO = getOutputDTO(params, "cashAuditService", "queryCashAuditList");
            LinkedHashMap<String, String> map = getFielMap();
            List<Map<String, Object>> list = outputDTO.getItems();
            ExcelUtil.listToExcel(list, map, "提现审核导出", response);
        } catch (ExcelException e) {
            outputDTO.setCode("-1");
            outputDTO.setMsg("导出失败");
            logger.error("TaskController.exportTask", e);
        }
        return outputDTO;
    }

    /**
     * 表头
     *
     * @return LinkedHashMap
     */
    private LinkedHashMap<String, String> getFielMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("memberAccount", "账号");
        map.put("recomNickname", "推荐人");
        map.put("teamNum", "团队人数");
        map.put("registerTime", "注册日期");
        map.put("rechargeTotal", "累计充值");
        map.put("bonusTotal", "累计分红");
        map.put("cashTotal", "累计提现");
        map.put("walletBalance", "钱包余额");
        map.put("realName", "实名信息");
        map.put("userAccount", "提现账户");
        map.put("accountTypeCd", "账户类型");
        map.put("cashAmount", "提现金额");
        map.put("cashApplyTime", "申请日期");
        map.put("auditTypeCd", "审核状态");
        map.put("auditOpinion", "审核意见");
        map.put("auditUserName", "审核人");
        map.put("auditTime", "审核时间");
        return map;
    }

}
