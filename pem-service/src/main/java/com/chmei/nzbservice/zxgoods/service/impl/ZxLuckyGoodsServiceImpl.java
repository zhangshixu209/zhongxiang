package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxgoods.service.IZxLuckyGoodsService;
import com.chmei.nzbservice.zxgoods.service.IZxLuckyGoodsService;
import org.springframework.stereotype.Service;

/**
 * 幸运购物service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxLuckyGoodsService")
public class ZxLuckyGoodsServiceImpl extends BaseServiceImpl implements IZxLuckyGoodsService {

	/**
	 * 新增幸运购物
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("saveLuckyGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑幸运购物
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("updateLuckyGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除幸运购物
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("delLuckyGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询幸运购物详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryLuckyGoodsDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("queryLuckyGoodsDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询幸运购物列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryLuckyGoodsList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("queryLuckyGoodsList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 购买幸运购物商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void buyLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("buyLuckyGoodsInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 免单活动
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void partakeFreeSheetAct(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("partakeFreeSheetAct");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 免单活动列表（幸运榜可用）
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryPartakeFreeSheetList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("queryPartakeFreeSheetList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 幸运购物幸运榜列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryLuckyList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("queryLuckyList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 幸运购物幸运榜详情列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryLuckyDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxLuckyGoodsService");
		input.setMethod("queryLuckyDetail");
		getNzbDataService().execute(input, output);
	}
}
