package com.chmei.nzbservice.recharge.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import com.chmei.nzbservice.recharge.service.IRechargeRecordService;
import com.chmei.nzbservice.recharge.service.IZxPayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 轮播图service接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月29日 17点48分
 *
 */
@Service("zxPayService")
public class ZxPayServiceImpl extends BaseServiceImpl implements IZxPayService {
	
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(ZxPayServiceImpl.class);

	/**
	 * 支付宝支付
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void alipay(InputDTO input, OutputDTO output) throws NzbServiceException{
		input.setService("zxPayService");
		input.setMethod("alipay");
		getNzbDataService().execute(input, output);
	}
}
