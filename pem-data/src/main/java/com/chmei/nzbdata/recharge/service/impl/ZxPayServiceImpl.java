package com.chmei.nzbdata.recharge.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.recharge.service.IZxPayService;
import com.chmei.nzbdata.util.UUID;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	@Value("${aliPay.app.alipayCertPath}")
	String alipayCertPath;
	@Value("${aliPay.app.alipayRootCertPath}")
	String alipayRootCertPath;
	@Value("${aliPay.app.appCertPath}")
	String appCertPath;

	/**
	 * 文件跟路径
	 */
	@Value("${alipay_cert_file_path}")
	private String AliPay_CERT_FILE_PATH;

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
//		AlipayClient alipayClient = new DefaultAlipayClient(aliPayGateway, aliPayAppId, rsaPrivatKey, alipayFormat, alipayCharset, rsaAlipayPublicKey, signType);
		// 注意：不同接口这里的请求对象是不同的，这个可以查看蚂蚁金服开放平台的API文档查看
		AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(alipayBody);
		model.setSubject(alipaySubject);
		// 唯一订单号 根据项目中实际需要获取相应的
		String orderNo = String.valueOf(UUID.next());
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

		//构造client
		CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
		certAlipayRequest.setServerUrl(aliPayGateway);
		certAlipayRequest.setAppId(aliPayAppId);
		certAlipayRequest.setPrivateKey(rsaPrivatKey);
		certAlipayRequest.setFormat(alipayFormat);
		certAlipayRequest.setCharset(alipayCharset);
		certAlipayRequest.setSignType(signType);
		certAlipayRequest.setCertPath(AliPay_CERT_FILE_PATH + appCertPath);
		certAlipayRequest.setAlipayPublicCertPath(AliPay_CERT_FILE_PATH + alipayCertPath);
		certAlipayRequest.setRootCertPath(AliPay_CERT_FILE_PATH + alipayRootCertPath);

		// 注意：每个请求的相应对象不同，与请求对象是对应。
		AlipayTradeAppPayResponse alipayResponse = null;
		Map<String,Object> insertMap = new HashMap<>();
		try {
			//构造client
			AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
			alipayResponse = alipayClient.sdkExecute(alipayRequest);
			//插入数据库
			insertMap.put("id", getSequence());
			insertMap.put("memberAccount", input.getParams().get("memberAccount"));
			insertMap.put("serialId",orderNo);
			insertMap.put("validStsCd",2);
			insertMap.put("rechargeAmount", rechargeAmount);
			insertMap.put("status", "1003");
			getBaseDao().insert("RechargeRecordMapper.saveRechargeRecordInfo", insertMap);
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

	private static final String TRADE_SUCCESS="TRADE_SUCCESS";
	private static final String TRADE_CLOSED="TRADE_CLOSED";
	/**
	 * 支付宝支付回调
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void aliPayCallback(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String realPay = (String) params.get("total_amount");
			String orderNo = (String) params.get("out_trade_no");
			String tradeStatus = (String) params.get("trade_status");
			if (TRADE_SUCCESS.equals(tradeStatus)){
				//TODO 取出订单
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("serialId", orderNo);
				Map<String,Object> result = (Map<String, Object>) getBaseDao().queryForObject(
						"RechargeRecordMapper.queryRechargeRecordDetail", updateMap);
				//TODO 先判断是否和数据库金钱一样
				String totalMount = (String) result.get("rechargeAmount");
				if(totalMount.equals(realPay)){
					//TODO 在进行修改数据库
					updateMap.put("validStsCd", "1"); // 有效状态
					getBaseDao().update("RechargeRecordMapper.updateSuccessOrderPay", updateMap);
					//TODO 修改完成进行加钱
					// 查询用户信息
					Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
							queryForObject("MemberMapper.queryMemberDetail", result);
					//TODO 存在就修改钱包
					if(zxAppUser != null){
						Map<String, Object> updateUser = new HashMap<>();
						updateUser.put("memberAccount", zxAppUser.get("memberAccount"));
						updateUser.put("walletBalance", Double.valueOf((String) zxAppUser.get("walletBalance")) + Double.parseDouble(realPay));
						int i = getBaseDao().update("MemberMapper.updateMemberBalance", updateUser);
						//TODO 加入记录
						if(i > 0){
							Map<String, Object> walletMoneyInfo = new HashMap<>();
							walletMoneyInfo.put("walletInfoId", getSequence());
							walletMoneyInfo.put("walletInfoAddOrMinus", "+");
							walletMoneyInfo.put("walletInfoUserId", zxAppUser.get("memberAccount"));
							walletMoneyInfo.put("walletInfoMoney", Double.parseDouble(realPay));
							walletMoneyInfo.put("walletInfoFrom", "支付宝-钱包充值");
							getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
						}
						output.setCode("0");
						output.setMsg("充值成功");
					}
				}
			} else if (TRADE_CLOSED.equals(tradeStatus)) {
				output.setCode("-1");
				output.setMsg("充值失败");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	@Autowired
	private WXPayConfig wxPayConfig;

	@Value("${wx.app.key}")
	String key;

	@Value("${wx.app.notifyUrl}")
	String notify_url;

	@Value("${wx.app.appId}")
	String appId;

	@Value("${wx.app.mchId}")
	String mch_id;

	@Value("${wx.app.body}")
	String body;

	/**
	 * 微信支付
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void wxPay(InputDTO input, OutputDTO output) throws NzbDataException {
		String orderNo = String.valueOf(UUID.next());
		Map<String, Object> params = input.getParams();
		@SuppressWarnings("unchecked")
		Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
				queryForObject("MemberMapper.queryMemberDetail", input.getParams());
		if(zxAppUser != null){
			Map<String, Object> map = wxPayInfo((String) params.get("memberAccount"),
					orderNo, (BigDecimal) params.get("rechargeAmount"), (String) params.get("ip"));
			output.setItem(map);
		}
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(20);
	private static final String SUCCESS="SUCCESS";
	/**
	 * 微信支付回调
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void wxPayCallback(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String resultCode = (String) params.get("resultCode");
			String orderNo = (String) params.get("outTradeNo");
			String totalFee =  (String) params.get("totalFee");
			if(check(params)){
				executorService.execute(() -> {
					//TODO 成功处理
					if (resultCode.equals(SUCCESS)) {
						//TODO 取出订单
						Map<String,Object> updateMap = new HashMap<>();
						updateMap.put("serialId", orderNo);
						Map<String,Object> result = (Map<String, Object>) getBaseDao().queryForObject(
								"RechargeRecordMapper.queryRechargeRecordDetail", updateMap);
						//TODO 先判断是否和数据库金钱一样
						String totalMount = (String) result.get("rechargeAmount");

						if(totalMount.equals(totalFee)){
							//TODO 在进行修改数据库
							updateMap.put("validStsCd", "1"); // 有效状态
							getBaseDao().update("RechargeRecordMapper.updateSuccessOrderPay", updateMap);
							//TODO 修改完成进行加钱
							// 查询用户信息
							Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
									queryForObject("MemberMapper.queryMemberDetail", result);
							//TODO 存在就修改钱包
							if(zxAppUser != null){
								Map<String, Object> updateUser = new HashMap<>();
								updateUser.put("memberAccount", zxAppUser.get("memberAccount"));
								updateUser.put("walletBalance", Double.valueOf((String) zxAppUser.get("walletBalance")) + Double.parseDouble(totalFee));
								int i = getBaseDao().update("MemberMapper.updateMemberBalance", updateUser);
								//TODO 加入记录
								if(i > 0){
									Map<String, Object> walletMoneyInfo = new HashMap<>();
									walletMoneyInfo.put("walletInfoId", getSequence());
									walletMoneyInfo.put("walletInfoAddOrMinus", "+");
									walletMoneyInfo.put("walletInfoUserId", zxAppUser.get("memberAccount"));
									walletMoneyInfo.put("walletInfoMoney", Double.parseDouble(totalFee));
									walletMoneyInfo.put("walletInfoFrom", "微信-钱包充值");
									getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
								}
								output.setCode("0");
								output.setMsg("充值成功");
							}
						}

					} else {
						//TODO 失败处理
						output.setCode("-1");
						output.setMsg("付款失败");
					}
				});
			}
			if (resultCode.equals(SUCCESS)) {
				output.setCode("0");
				output.setMsg("付款成功");
			} else {
				output.setCode("-1");
				output.setMsg("付款失败");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
			output.setCode("-1");
			output.setMsg("付款失败");
		}
	}

	/**
	 * 校验微信支付信息
	 * @param params
	 * @return
	 */
	private Boolean check(Map<String, Object> params) {
		String outTradeNo = (String) params.get("out_trade_no");
		// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		Map<String,Object> maps = new HashMap<>();
		maps.put("serialId",outTradeNo);
		@SuppressWarnings("unchecked")
		Map<String,Object> objectMap = (Map<String, Object>) getBaseDao().queryForObject(
				"RechargeRecordMapper.queryRechargeRecordDetail", maps);
		if (objectMap == null) {
			return false;
		}
		// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		Integer totalAmount = (Integer) params.get("total_fee");
		Integer beforeAmount = (Integer) objectMap.get("rechargeAmount");
		if (!totalAmount.equals(beforeAmount)) {
			return false;
		}
		return true;
	}

	/**
	 * 微信支付调用
	 * @param phone
	 * @param orderNO
	 * @param payAmount
	 * @param ip
	 * @return
	 */
	private Map<String, Object> wxPayInfo(String phone, String orderNO, BigDecimal payAmount, String ip) {
		try {
			Map<String, String> map = new LinkedHashMap<>();
			String nonce_str = WXPayUtil.generateNonceStr();
			Double v = payAmount.doubleValue() * 100;
			Integer monry = BigDecimal.valueOf(v).movePointRight(2).intValue();
			map.put("appid", appId);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("out_trade_no", orderNO);
			map.put("total_fee", monry.toString());
			map.put("spbill_create_ip", ip);
			map.put("notify_url", notify_url);
			map.put("trade_type", "APP");
			WXPay pay = new WXPay(wxPayConfig);
			map.put("sign",WXPayUtil.generateSignature(map,key));
			Map<String, String> returnMap = pay.unifiedOrder(map);
			Map<String, String> resultMap = new LinkedHashMap<>();
			Map<String, Object> returnMap_ = new LinkedHashMap<>();
			resultMap.put("appid", appId);
			resultMap.put("package", "Sign=WXPay");
			resultMap.put("partnerid", mch_id);
			resultMap.put("prepayid", returnMap.get("prepay_id"));
			resultMap.put("noncestr", System.currentTimeMillis() + "");
			resultMap.put("timestamp", System.currentTimeMillis() / 1000 + "");
			resultMap.put("sign", WXPayUtil.generateSignature(resultMap,key));
			resultMap.remove("package");
			resultMap.put("packageType", "Sign=WXPay");
			if("OK".equals(returnMap.get("return_msg"))){
				//插入数据库
				Map<String,Object> insertMap = new HashMap<>();
				//插入数据库
				insertMap.put("id", getSequence());
				insertMap.put("memberAccount", phone);
				insertMap.put("serialId", orderNO);
				insertMap.put("validStsCd",2);
				insertMap.put("rechargeAmount", v/100);
				insertMap.put("status", "1003");
				getBaseDao().insert("RechargeRecordMapper.saveRechargeRecordInfo", insertMap);
				returnMap_.put("data", resultMap);
				return returnMap_;
			}
		} catch (Exception e) {
			LOGGER.info("微信支付失败{}", e.getStackTrace());
			return null;
		}
		return null;
	}

//	public static void main(String[] args) {
//		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//		model.setBody("我是测试数据");
//		model.setSubject("App支付测试Java");
//		//请保证OutTradeNo值每次保证唯一
//		model.setOutTradeNo("2017099966661939235");
//		model.setTimeoutExpress("30m");
//		model.setTotalAmount("0.01");
//		model.setProductCode("QUICK_MSECURITY_PAY");
//		request.setBizModel(model);
//		request.setNotifyUrl("http://zhongxiang.natapp1.cc/aliPayCallback");
//		//构造client
//		CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
//		certAlipayRequest.setServerUrl(ZrkAliPayCertConfig.aliPayGateway);
//		certAlipayRequest.setAppId(ZrkAliPayCertConfig.aliPayAppId);
//		certAlipayRequest.setPrivateKey(ZrkAliPayCertConfig.rsaPrivatKey);
//		certAlipayRequest.setFormat(ZrkAliPayCertConfig.alipayFormat);
//		certAlipayRequest.setCharset(ZrkAliPayCertConfig.alipayCharset);
//		certAlipayRequest.setSignType(ZrkAliPayCertConfig.signType);
//		certAlipayRequest.setCertPath(ZrkAliPayCertConfig.appCertPath);
//		certAlipayRequest.setAlipayPublicCertPath(ZrkAliPayCertConfig.alipayCertPath);
//		certAlipayRequest.setRootCertPath(ZrkAliPayCertConfig.alipayRootCertPath);
//		try {
//			//构造client
//			AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
//			//这里和普通的接口调用不同，使用的是sdkExecute
//			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//			System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//	}


}
