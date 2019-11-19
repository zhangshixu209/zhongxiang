package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 角色权限管理接口
 * date: 2018年8月8日 下午4:57:57  
 * @author lianziyu  
 * @version   
 * @since JDK 1.7
 */
public interface IRoleRightService {
	
	
	/**
	 * 修改角色权限
	 * @author lianziyu  
	 * @param input input 需传入roleId,以及新增的权限id的数组rightIds
	 * @param output
	 * @throws NzbServiceException
	 */
	public void updateRoleRight(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 查询角色拥有的权限
	 * @author lianziyu  
	 * @param input typeCd menu表示菜单，button表示按钮，link表示链接,sysTypeCd 1:管理端,2:接包方端,3:发包方端
	 * @param output
	 * @throws NzbServiceException
	 */
	public void queryRightListByRoleId(InputDTO input, OutputDTO output) throws NzbServiceException;
}
