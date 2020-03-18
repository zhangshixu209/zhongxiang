package com.chmei.nzbservice.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享商品审核dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点42分
 *
 */
public interface IZxGoodsExamineService {

	/**
	 * 商品审核
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void authGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 商品上架、下架（秒杀中的商品下架需要返还参与者金额）
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void goodsShelfInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除商品
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void delGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询商品详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryGoodsExamineDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询商品列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryGoodsExamineList(InputDTO input, OutputDTO output) throws NzbServiceException;
}
