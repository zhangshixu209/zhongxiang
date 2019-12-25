package com.chmei.nzbmanage.recharge.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.util.IpUtils;
import com.chmei.nzbmanage.recharge.bean.RechargeRecordForm;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
    @Value("${aliPay.app.alipayCertPath}")
    String alipayCertPath;
    /**
     * 文件跟路径
     */
    @Value("${alipay_cert_file_path}")
    String AliPay_CERT_FILE_PATH;
    /**
     * 支付宝支付回调
     * @param request
     * @return
     */
    @RequestMapping(value = "/aliPayCallback", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST })
    public String aliPayCallback(HttpServletRequest request) {
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
        String success = "";
        OutputDTO outputDTO = new OutputDTO();
        try {
            boolean flag = AlipaySignature.rsaCertCheckV1(params, AliPay_CERT_FILE_PATH + alipayCertPath,
                    alipayCharset, signType);
            if (flag) {
                outputDTO = getOutputDTO(params_, "zxPayService", "aliPayCallback");
                if ("0".equals(outputDTO.getCode())) {
                    success = "success";
                    return success;  // 必须success，否则支付宝会一直回调
                }
            } else {
                outputDTO.setCode("-1");
                outputDTO.setMsg("系统异常");
                success = "fail";
                return success;
            }
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
            outputDTO.setCode("-1");
            outputDTO.setMsg("系统异常");
        }
        return success;
    }

    /**
     * 微信充值
     *
     * @param rechargeRecordForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/wxPay")
    public OutputDTO wxPay(@ModelAttribute RechargeRecordForm rechargeRecordForm, HttpServletRequest request) {
        LOGGER.info("微信充值...RechargeRecordController.wxPay()...");
        String ip = IpUtils.getIpAddr(request);
        rechargeRecordForm.setIp(ip);
        Map<String, Object> params = BeanUtil.convertBean2Map(rechargeRecordForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxPayService", "wxPay");
        return outputDTO;
    }

    /**
     * 微信支付回调
     * @param request
     * @return
     */
    @RequestMapping(value = "/wxPayCallback", method = RequestMethod.POST)
    @ResponseBody
    public String wxPayCallback(HttpServletRequest request) {
        LOGGER.info("微信支付回调");
        OutputDTO outputDTO = new OutputDTO();
        try {
            // 读取参数
            // 解析xml成map
            Map<String, String> map = WXPayUtil.xmlToMap(getParam(request));
            //校验（验证订单号，付款金额等是否正确）
            String orderNo = map.get("out_trade_no");
            String resultCode = map.get("result_code");
            String totalFee = map.get("total_fee");
            Map<String, Object> params_ = new HashMap<>();
            params_.put("orderNo", orderNo);
            params_.put("resultCode", resultCode);
            params_.put("totalFee", totalFee);
            outputDTO = getOutputDTO(params_, "zxPayService", "wxPayCallback");
            if ("0".equals(outputDTO.getCode())) {
                outputDTO.setCode("0");
                outputDTO.setMsg("SUCCESS");
                return setXml("SUCCESS", "OK");
            } else {
                outputDTO.setCode("-1");
                outputDTO.setMsg("fail");
                return setXml("fail", "error");
            }
        } catch (Exception e) {
            LOGGER.info("微信支付回调发生异常{}", e);
            outputDTO.setCode("-1");
            outputDTO.setMsg("fail");
            return setXml("fail", "error");
        }
    }

    /**
     * 微信支付回调获取参数
     * @param request
     * @return
     * @throws IOException
     */
    private String getParam(HttpServletRequest request) throws IOException {
        // 读取参数
        InputStream inputStream;
        StringBuilder sb = new StringBuilder();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }

    /**
     * 通过xml发给微信消息
     *
     * @param return_code
     * @param return_msg
     * @return
     */
    private static String setXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap<>();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        try {
            return WXPayUtil.mapToXml(parameters);
        } catch (Exception e) {
            LOGGER.error("返回微信消息时map转xml失败");
            return "<xml><return_code><![CDATA[" + return_code + "]]>" + "</return_code><return_msg><![CDATA[" + return_msg
                    + "]]></return_msg></xml>";
        }
    }

}
