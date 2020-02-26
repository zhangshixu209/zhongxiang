package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IZxAppVersionService;
import com.chmei.nzbservice.zxfriend.service.IZxMyTeamService;
import org.springframework.stereotype.Service;

/**
 * 版本号service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点12分
 *
 */
@Service("zxAppVersionService")
public class ZxAppVersionServiceImpl extends BaseServiceImpl implements IZxAppVersionService {

	/**
	 * 新增版本号
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveZxAppVersionInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxAppVersionService");
		input.setMethod("saveZxAppVersionInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询版本号列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxAppVersionList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxAppVersionService");
		input.setMethod("queryZxAppVersionList");
		getNzbDataService().execute(input, output);
	}
}
