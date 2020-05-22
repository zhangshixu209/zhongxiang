package com.chmei.nzbservice.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 二维码背景图dao接口
 * 
 * @author zhangshixu
 * @since 2020年5月20日 09点37分
 *
 */
public interface IBackdropChartService {
	/**
	 * 二维码背景图列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryBackdropChartList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 新增二维码背景图
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询二维码背景图详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryBackdropChartDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 编辑二维码背景图
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除二维码背景图
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void deleteBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 设置默认二维码背景图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void setDefaultBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
}
