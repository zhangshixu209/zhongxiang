
package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**  
 * 权限控制
 * Date:     2018年8月23日 下午7:11:10 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public interface RightManageService {
	/**
	 * 初始化加载所有权限到缓存
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	void queryInitAllRight(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 初始化加载所有角色的权限到缓存
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	void queryInitAllRoleRight(InputDTO input, OutputDTO output) throws NzbDataException;
	
}
  
