package com.chmei.nzbdata.recharge.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.recharge.service.IZxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 充值记录dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月21日 15点58分
 */

@Service("zxPayService")
public class ZxPayServiceImpl extends BaseServiceImpl implements IZxPayService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxPayServiceImpl.class);


	@Value("${aliPay.app.aliPayGateway}")
	String aliPayGateway;
	@Value("${aliPay.app.aliPayAppId}")
	String aliPayAppId;
	@Value("${aliPay.app.rsaPrivatKey}")
	String rsaPrivatKey;
	@Value("${aliPay.app.alipayFormat}")
	String alipayFormat;
	@Value("${aliPay.app.alipayCharset}")
	String alipayCharset;
	@Value("${aliPay.app.rsaAlipayPublicKey}")
	String rsaAlipayPublicKey;
	@Value("${aliPay.app.signType}")
	String signType;

	@Value("${aliPay.app.alipayNotifyUrl}")
	String alipayNotifyUrl;
	@Value("${aliPay.app.alipayBody}")
	String alipayBody;
	@Value("${aliPay.app.alipaySubject}")
	String alipaySubject;
	@Value("${aliPay.app.alipayProductCode}")
	String alipayProductCode;

	/**
	 * 支付宝支付
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void alipay(InputDTO input, OutputDTO output) throws NzbDataException{
		// 开始使用支付宝SDK中提供的API
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayGateway, aliPayAppId, rsaPrivatKey, alipayFormat, alipayCharset, rsaAlipayPublicKey, signType);
		// 注意：不同接口这里的请求对象是不同的，这个可以查看蚂蚁金服开放平台的API文档查看
		AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(alipayBody);
		model.setSubject(alipaySubject);
		// 唯一订单号 根据项目中实际需要获取相应的
		String orderNo = String.valueOf(UUID.randomUUID());
		model.setOutTradeNo(orderNo);
		// 支付超时时间（根据项目需要填写）
		model.setTimeoutExpress("30m");
		// 支付金额（项目中实际订单的需要支付的金额，金额的获取与操作请放在服务端完成，相对安全）
		BigDecimal rechargeAmount = (BigDecimal) input.getParams().get("rechargeAmount");
		model.setTotalAmount(rechargeAmount.stripTrailingZeros().toPlainString());
		model.setProductCode(alipayProductCode);
		alipayRequest.setBizModel(model);
		// 支付成功后支付宝异步通知的接收地址url
		alipayRequest.setNotifyUrl(alipayNotifyUrl);

		// 注意：每个请求的相应对象不同，与请求对象是对应。
		AlipayTradeAppPayResponse alipayResponse = null;
		try {
			alipayResponse = alipayClient.sdkExecute(alipayRequest);
			//插入数据库
			Map<String,Object> insertMap = new HashMap<>();
			insertMap.put("id", getSequence());
			insertMap.put("memberAccount", input.getParams().get("memberAccount"));
			insertMap.put("serialId",orderNo);
			insertMap.put("validStsCd",2);
			insertMap.put("rechargeAmount", rechargeAmount);
			insertMap.put("status", "1003");
			getBaseDao().insert("充值新增", insertMap);
		} catch (AlipayApiException e) {
			LOGGER.error("系统异常", e);
			output.setCode("-1");
			output.setMsg("系统异常");
			return;
		}
		// 返回支付相关信息(此处可以直接将getBody中的内容直接返回，无需再做一些其他操作)
		Map<String, Object> maps = new HashMap<>();
		maps.put("alipay", alipayResponse.getBody());
		output.setItem(maps);
	}
}
