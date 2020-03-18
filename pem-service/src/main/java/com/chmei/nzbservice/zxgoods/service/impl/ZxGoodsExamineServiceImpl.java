package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxgoods.service.IZxGoodsExamineService;
import org.springframework.stereotype.Service;

/**
 * 广告分红service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxGoodsExamineService")
public class ZxGoodsExamineServiceImpl extends BaseServiceImpl implements IZxGoodsExamineService {


	/**
	 * 商品审核
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void authGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsExamineService");
		input.setMethod("authGoodsExamineInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 商品上架、下架（秒杀中的商品下架需要返还参与者金额）
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void goodsShelfInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsExamineService");
		input.setMethod("goodsShelfInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsExamineService");
		input.setMethod("delGoodsExamineInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询商品详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryGoodsExamineDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsExamineService");
		input.setMethod("queryGoodsExamineDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryGoodsExamineList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsExamineService");
		input.setMethod("queryGoodsExamineList");
		getNzbDataService().execute(input, output);
	}
}
