package com.chmei.nzbservice.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享订单信息dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月18日 17点08分
 *
 */
public interface IZxOrderInfoService {

	/**
	 * 新增订单信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
     * @return
	 */
	void saveOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 编辑订单信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void updateOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除订单信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void delOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询订单信息详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryOrderInfoDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询订单信息列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryOrderInfoList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询我的发布商品列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryMyReleaseGoodsList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询我的发布商品列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryMyReleaseOrderDetail(InputDTO input, OutputDTO output) throws NzbServiceException;
}
