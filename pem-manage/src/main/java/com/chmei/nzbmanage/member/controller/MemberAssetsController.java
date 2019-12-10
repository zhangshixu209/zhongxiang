package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.excel.ExcelUtil;
import com.chmei.nzbcommon.util.excel.exception.ExcelException;
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
 * 会员资产明细控制器
 *
 * @author zhangshixu
 * @since 2019年11月08日 14点02分
 */
@RestController
@RequestMapping("/api/memberAssets")
public class MemberAssetsController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(MemberAssetsController.class);

    /**
     * 查询会员资产明细
     * @param memberAssetsForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberAssetsInfo")
    public OutputDTO queryRealNameInfo(@ModelAttribute MemberAssetsForm memberAssetsForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberAssetsForm);
            outputDTO = getOutputDTO(map, "memberAssetsService", "queryMemberAssetsInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询资金状况
     *
     * @param memberAssetsForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberMoneyTotalList")
    public OutputDTO queryMemberMoneyTotalList(@ModelAttribute MemberAssetsForm memberAssetsForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberAssetsForm);
            outputDTO = getOutputDTO(map, "memberAssetsService", "queryMemberMoneyTotalList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
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
    public OutputDTO exportExcel(@ModelAttribute MemberAssetsForm bean, HttpServletResponse response) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(bean);
            outputDTO = getOutputDTO(params, "memberAssetsService", "queryMemberMoneyTotalList");
            LinkedHashMap<String, String> map = getFielMap();
            List<Map<String, Object>> list = outputDTO.getItems();
            ExcelUtil.listToExcel(list, map, "资金状况导出", response);
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
        map.put("walletAmount", "钱包总余额");
        map.put("allPackets", "未抢完的红包");
        map.put("activateMoney", "开通广告分红手续费");
        map.put("applyMoney", "申请分红手续费");
        map.put("groupMoney", "群升级费用");
        map.put("reflectMoney", "提现手续费");
        map.put("relReflectMoney", "实际提现总金额");
        map.put("balance", "=");
        map.put("totalMoney", "会员充值总金额");
        map.put("type", "状态");
        return map;
    }

}
