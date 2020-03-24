package com.chmei.nzbdata.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享免费兑换商品dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月23日 10点42分
 *
 */
public interface IZxFreeGoodsService {

	/**
	 * 新增免费兑换商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void saveFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 编辑免费兑换商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void updateFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除免费兑换商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void delFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询免费兑换商品详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryFreeGoodsDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询免费兑换商品列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryFreeGoodsList(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 购买免费兑换商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void buyFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException;
}
