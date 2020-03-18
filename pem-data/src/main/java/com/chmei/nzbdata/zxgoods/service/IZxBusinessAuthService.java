package com.chmei.nzbdata.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享商家认证dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点42分
 *
 */
public interface IZxBusinessAuthService {

	/**
	 * 商家认证审核
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void authBusinessInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 商家认证新增
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void saveBusinessInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 编辑商家认证
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void updateBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除商家认证
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void delBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询商家认证详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryBusinessAuthDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询商家认证列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryBusinessAuthList(InputDTO input, OutputDTO output) throws NzbDataException;
}
