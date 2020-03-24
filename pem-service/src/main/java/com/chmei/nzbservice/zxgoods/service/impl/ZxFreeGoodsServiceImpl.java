package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxgoods.service.IZxFreeGoodsService;
import com.chmei.nzbservice.zxgoods.service.IZxFreeGoodsService;
import org.springframework.stereotype.Service;

/**
 * 免费兑换service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxFreeGoodsService")
public class ZxFreeGoodsServiceImpl extends BaseServiceImpl implements IZxFreeGoodsService {

	/**
	 * 新增免费兑换
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFreeGoodsService");
		input.setMethod("saveFreeGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑免费兑换
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFreeGoodsService");
		input.setMethod("updateFreeGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除免费兑换
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFreeGoodsService");
		input.setMethod("delFreeGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询免费兑换详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryFreeGoodsDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFreeGoodsService");
		input.setMethod("queryFreeGoodsDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询免费兑换列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryFreeGoodsList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFreeGoodsService");
		input.setMethod("queryFreeGoodsList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 购买免费兑换商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void buyFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFreeGoodsService");
		input.setMethod("buyFreeGoodsInfo");
		getNzbDataService().execute(input, output);
	}
}
