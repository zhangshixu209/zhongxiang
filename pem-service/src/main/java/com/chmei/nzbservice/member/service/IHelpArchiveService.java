package com.chmei.nzbservice.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 帮助文档dao接口
 * 
 * @author zhangshixu
 * @since 2020年5月20日 09点37分
 *
 */
public interface IHelpArchiveService {
	/**
	 * 帮助文档列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryHelpArchiveList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 新增帮助文档
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询帮助文档详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryHelpArchiveDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 编辑帮助文档
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除帮助文档
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void deleteHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
