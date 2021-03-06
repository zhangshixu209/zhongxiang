package com.chmei.nzbdata.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享商品类别dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点42分
 *
 */
public interface IZxGoodsTypeService {

	/**
	 * 新增商品类别
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void saveGoodsTypeInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 编辑商品类别
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void updateGoodsTypeInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除商品类别
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void delGoodsTypeInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询商品类别详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryGoodsTypeDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询商品类别列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryGoodsTypeList(InputDTO input, OutputDTO output) throws NzbDataException;
}
