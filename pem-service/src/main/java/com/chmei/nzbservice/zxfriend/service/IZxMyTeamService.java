package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 我的团队service接口
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点06分
 *
 */
public interface IZxMyTeamService {

	/**
	 * 根据登录用户ID, 查询自己手下的团队信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryTheLower(InputDTO input, OutputDTO output) throws NzbServiceException;

}
