package com.chmei.nzbdata.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 广告分红dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月13日 14点06分
 *
 */
public interface IAdShareOutBonusService {

	/**
	 * 激活广告分红
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void adShareOutBonusActivate(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 追加广告分红
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void appendShareOutBonus(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询广告分红详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryShareOutBonusDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询是否第一次绑定推荐人
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryIsFirstRecommend(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 根据账号查询推荐人信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryRecommendInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 申请分红
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void applyShareOutBonus(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 申请分红之前的查询条件接口,比如 直推有效人数,广告费余额额度,计算分红周期
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void findApplyForAdvertisingInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 根据当前登录用户账号查询分红任务完成了几次
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	long findTaskCountByMemberAccount(InputDTO input, OutputDTO output) throws NzbDataException;
}
