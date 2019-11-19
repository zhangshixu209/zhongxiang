package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;

/**
 * 权限控制接口  
 * Date:     2018年8月24日 下午5:20:01 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public interface IRightMangeService {
	/**
	 * 初始化加载所有权限到缓存
	 * @author lianziyu  
	 * @param input
	 * @param output
	 */
	void queryInitAllRight(InputDTO input, OutputDTO output);
	
	/**
	 * 初始化加载所有角色的权限到缓存
	 * @author lianziyu  
	 * @param input
	 * @param output
	 */
	void queryInitAllRoleRight(InputDTO input, OutputDTO output);
	
}
  
