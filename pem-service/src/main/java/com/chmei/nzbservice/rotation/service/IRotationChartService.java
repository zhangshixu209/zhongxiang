package com.chmei.nzbservice.rotation.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 轮播图service接口
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点28分
 *
 */
public interface IRotationChartService {
	/**
	 * 轮播图列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryRotationChartList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 新增轮播图
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveRotationChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 轮播图详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryRotationChartDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 编辑轮播图
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateRotationChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除轮播图
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void deleteRotationChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改轮播图在线状态
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateOnlineStatus(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 轮播图列表查询APP使用
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryRotationListForApp(InputDTO input, OutputDTO output) throws NzbServiceException;
}
