package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 版本号控制dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月13日 10点21分
 *
 */
public interface IZxAppVersionService {

	/**
	 * 新增版本号
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException  自定义异常
	 */
	void saveZxAppVersionInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询版本号列表
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException  自定义异常
	 */
	void queryZxAppVersionList(InputDTO input, OutputDTO output) throws NzbServiceException;
}
