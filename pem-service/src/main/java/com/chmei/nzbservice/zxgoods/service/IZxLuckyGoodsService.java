package com.chmei.nzbservice.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享幸运购物商品dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月23日 10点42分
 *
 */
public interface IZxLuckyGoodsService {

	/**
	 * 新增幸运购物商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
     * @return
	 */
	void saveLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 编辑幸运购物商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void updateLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除幸运购物商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void delLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询幸运购物商品详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryLuckyGoodsDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询幸运购物商品列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryLuckyGoodsList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 购买幸运购物商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void buyLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
}
