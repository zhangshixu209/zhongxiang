package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxgoods.service.IZxReceivingAddressService;
import org.springframework.stereotype.Service;

/**
 * 收货地址service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxReceivingAddressService")
public class ZxReceivingAddressServiceImpl extends BaseServiceImpl implements IZxReceivingAddressService {

	/**
	 * 新增收货地址
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveReceivingAddressInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxReceivingAddressService");
		input.setMethod("saveReceivingAddressInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑收货地址
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateReceivingAddressInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxReceivingAddressService");
		input.setMethod("updateReceivingAddressInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除收货地址
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delReceivingAddressInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxReceivingAddressService");
		input.setMethod("delReceivingAddressInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询收货地址详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryReceivingAddressDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxReceivingAddressService");
		input.setMethod("queryReceivingAddressDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询收货地址列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryReceivingAddressList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxReceivingAddressService");
		input.setMethod("queryReceivingAddressList");
		getNzbDataService().execute(input, output);
	}
}
