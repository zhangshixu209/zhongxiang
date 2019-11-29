package com.chmei.nzbmanage.recharge.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.recharge.bean.RechargeRecordForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 充值记录控制器
 * 
 * @author zhangshixu
 * @since 2019年10月29日 17点33分
 */
@RestController
@RequestMapping("/api/recharge")
public class RechargeRecordController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(RechargeRecordController.class);
    
    /**
     * 初始化加载充值记录查询列表
     *
     * @param rechargeRecordForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRechargeRecordList")
    public OutputDTO queryRechargeRecordList(@ModelAttribute RechargeRecordForm rechargeRecordForm) {
    	LOGGER.info("初始化加载充值记录查询列表...RechargeRecordController.queryRechargeRecordList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(rechargeRecordForm);
        outputDTO = getOutputDTO(params, "rechargeRecordService", "queryRechargeRecordList");
        return outputDTO;
    }

    /**
     * 初始化加载充值记录查询列表
     *
     * @param rechargeRecordForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/alipay")
    public OutputDTO alipay(@ModelAttribute RechargeRecordForm rechargeRecordForm) {
        LOGGER.info("初始化加载充值记录查询列表...RechargeRecordController.alipay()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(rechargeRecordForm);
        outputDTO = getOutputDTO(params, "zxPayService", "alipay");
        return outputDTO;
    }

}
