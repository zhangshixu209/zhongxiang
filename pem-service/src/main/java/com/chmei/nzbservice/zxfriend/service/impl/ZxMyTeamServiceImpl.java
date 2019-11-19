package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IZxMyTeamService;
import org.springframework.stereotype.Service;

/**
 * 我的团队service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点12分
 *
 */
@Service("zxMyTeamService")
public class ZxMyTeamServiceImpl extends BaseServiceImpl implements IZxMyTeamService {

	/**
	 * 根据登录用户ID, 查询自己手下的团队信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryTheLower(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxMyTeamService");
		input.setMethod("queryTheLower");
		getNzbDataService().execute(input, output);
	}
}
