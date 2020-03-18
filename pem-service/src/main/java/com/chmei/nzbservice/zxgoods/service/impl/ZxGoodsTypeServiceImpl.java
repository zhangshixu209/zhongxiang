package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IAdShareOutBonusService;
import com.chmei.nzbservice.zxgoods.service.IZxGoodsTypeService;
import org.springframework.stereotype.Service;

/**
 * 广告分红service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxGoodsTypeService")
public class ZxGoodsTypeServiceImpl extends BaseServiceImpl implements IZxGoodsTypeService {

	/**
	 * 新增商品类别
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveGoodsTypeInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsTypeService");
		input.setMethod("saveGoodsTypeInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑商品类别
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateGoodsTypeInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsTypeService");
		input.setMethod("updateGoodsTypeInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除商品类别
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delGoodsTypeInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsTypeService");
		input.setMethod("delGoodsTypeInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询商品类别详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryGoodsTypeDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsTypeService");
		input.setMethod("queryGoodsTypeDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询商品类别列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryGoodsTypeList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxGoodsTypeService");
		input.setMethod("queryGoodsTypeList");
		getNzbDataService().execute(input, output);
	}
}
