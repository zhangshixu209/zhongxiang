package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 广告分红service接口
 * 
 * @author zhangshixu
 * @since 2019年11月14日 09点58分
 *
 */
public interface IAdShareOutBonusService {

	/**
	 * 激活广告分红
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void adShareOutBonusActivate(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 追加广告分红
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void appendShareOutBonus(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询是否第一次绑定推荐人
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryIsFirstRecommend(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 根据账号查询推荐人信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryRecommendInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 申请分红
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void applyShareOutBonus(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 申请分红之前的查询条件接口,比如 直推有效人数,广告费余额额度,计算分红周期
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void findApplyForAdvertisingInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
