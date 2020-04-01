package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxgoods.service.IZxOrderInfoService;
import org.springframework.stereotype.Service;

/**
 * 订单信息service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxOrderInfoService")
public class ZxOrderInfoServiceImpl extends BaseServiceImpl implements IZxOrderInfoService {

	/**
	 * 新增订单信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("saveOrderInfoInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑订单信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("updateOrderInfoInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除订单信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("delOrderInfoInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询订单信息详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryOrderInfoDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("queryOrderInfoDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询订单信息列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryOrderInfoList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("queryOrderInfoList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询我的发布商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryMyReleaseGoodsList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("queryMyReleaseGoodsList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询我的发布商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryMyReleaseOrderDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("queryMyReleaseOrderDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 用户确认收货
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void userConfirmReceipt(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("userConfirmReceipt");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询在途商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryGoodsInTransitList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("queryGoodsInTransitList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询订单物流信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryOrderLogisticsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOrderInfoService");
		input.setMethod("queryOrderLogisticsInfo");
		getNzbDataService().execute(input, output);
	}
}
