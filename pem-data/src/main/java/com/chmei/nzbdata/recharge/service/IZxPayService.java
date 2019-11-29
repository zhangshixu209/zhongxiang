package com.chmei.nzbdata.recharge.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 微信/支付宝支付dao接口
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点23分
 *
 */
public interface IZxPayService {

	/**
	 * 支付宝支付
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void alipay(InputDTO input, OutputDTO output) throws NzbDataException;


}
