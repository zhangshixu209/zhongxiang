package com.chmei.nzbdata.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享收货地址dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月18日 16点38分
 *
 */
public interface IZxReceivingAddressService {

	/**
	 * 新增收货地址
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void saveReceivingAddressInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 编辑收货地址
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void updateReceivingAddressInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除收货地址
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void delReceivingAddressInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询收货地址详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryReceivingAddressDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询收货地址列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryReceivingAddressList(InputDTO input, OutputDTO output) throws NzbDataException;
}
