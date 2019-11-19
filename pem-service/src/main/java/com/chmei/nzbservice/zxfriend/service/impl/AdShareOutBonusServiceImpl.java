package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IAdShareOutBonusService;
import org.springframework.stereotype.Service;

/**
 * 广告分红service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("adShareOutBonusService")
public class AdShareOutBonusServiceImpl extends BaseServiceImpl implements IAdShareOutBonusService {

	/**
	 * 激活广告分红
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void adShareOutBonusActivate(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("adShareOutBonusService");
		input.setMethod("adShareOutBonusActivate");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 追加广告分红
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void appendShareOutBonus(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("adShareOutBonusService");
		input.setMethod("appendShareOutBonus");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询是否第一次绑定推荐人
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryIsFirstRecommend(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("adShareOutBonusService");
		input.setMethod("queryIsFirstRecommend");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 根据账号查询推荐人信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryRecommendInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("adShareOutBonusService");
		input.setMethod("queryRecommendInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 申请分红
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void applyShareOutBonus(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("adShareOutBonusService");
		input.setMethod("applyShareOutBonus");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 申请分红之前的查询条件接口,比如 直推有效人数,广告费余额额度,计算分红周期
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void findApplyForAdvertisingInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("adShareOutBonusService");
		input.setMethod("findApplyForAdvertisingInfo");
		getNzbDataService().execute(input, output);
	}
}
