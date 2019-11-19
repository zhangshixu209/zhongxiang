package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 角色权限
 * 
 * @author xzq
 * @email xiezhenqiang@qq.com
 * @date 2018-08-06 16:50:31
 */
public interface ITURoleRightService {
	
	/**
	 * 修改
	 * @author lianziyu  
	 * @param input 需传入roleId,以及新增的权限id的数组rightIds
	 * @param output
	 * @throws NzbDataException
	 */
	public void update(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 获取角色的所拥有菜单
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void queryRightListByRoleId(InputDTO input, OutputDTO output) throws NzbDataException;
}
