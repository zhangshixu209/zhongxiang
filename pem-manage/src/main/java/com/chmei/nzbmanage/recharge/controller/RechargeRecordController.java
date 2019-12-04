package com.chmei.nzbmanage.recharge.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.recharge.bean.RechargeRecordForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
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

    @Value("${aliPay.app.alipayCharset}")
    String alipayCharset;
    @Value("${aliPay.app.rsaAlipayPublicKey}")
    String rsaAlipayPublicKey;
    @Value("${aliPay.app.signType}")
    String signType;

    /**
     * 支付宝支付回调
     * @param request
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/aliPayCallback", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST })
    public OutputDTO aliPayCallback(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<>();
        Map<String, Object> params_ = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
            params_.put(name, valueStr);
        }
        boolean flag = AlipaySignature.rsaCheckV1(params, rsaAlipayPublicKey, alipayCharset,
                signType);
        OutputDTO outputDTO = new OutputDTO();
        if (flag) {
            outputDTO = getOutputDTO(params_, "zxPayService", "aliPayCallback");
        } else {
            outputDTO.setCode("-1");
            outputDTO.setMsg("系统异常");
        }
        return outputDTO;
    }

    /**
     * 微信充值
     *
     * @param rechargeRecordForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/wxPay")
    public OutputDTO wxPay(@ModelAttribute RechargeRecordForm rechargeRecordForm) {
        LOGGER.info("微信充值...RechargeRecordController.wxPay()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(rechargeRecordForm);
        outputDTO = getOutputDTO(params, "zxPayService", "wxPay");
        return outputDTO;
    }

}
