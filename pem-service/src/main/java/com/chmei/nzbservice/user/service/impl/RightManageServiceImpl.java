package com.chmei.nzbservice.user.service.impl;

import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.user.service.IRightMangeService;

/**
 * 初始化添加菜单和角色
 * @author 翟超锋
 *
 */
@Service("rightManageService")
public class RightManageServiceImpl extends BaseServiceImpl implements IRightMangeService {

	/**
	 * 初始化添加菜单
	 */
	@Override
	public void queryInitAllRight(InputDTO input, OutputDTO output) {
		getNzbDataService().execute(input,output);
	}

	/**
	 * 初始化添加角色
	 */
	@Override
	public void queryInitAllRoleRight(InputDTO input, OutputDTO output) {
		getNzbDataService().execute(input,output);
	}
}
  
