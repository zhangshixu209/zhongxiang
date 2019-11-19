package com.chmei.nzbdata.util;

import java.util.Map;

public final class RedisKey {
	  
    /**管理端所有权限reids key*/
    public static final String REDIS_SYS_MANAGE_RIGHTS_HASH = "report:manage:rightHash"; 
    /**管理端角色权限reids key*/
	public static final String REDIS_SYS_MANAGE_ROLE_RIGHT_HASH = "report:manage:roleRightHash"; 
	
	

	// 新添加的账号冻结列表
	public static final String REDIS_ACCOUNT_FREEZE = "user:account:freeze";
    
	public interface SYS_CACHE_KEY{
		String AREA_KEY = "area:"; // 省市缓存Key
		String CHILDREN_AREA_KEY = "childrenArea:"; // 父节点的子节点列表
	}    
    
	
}
