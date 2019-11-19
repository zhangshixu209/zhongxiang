package com.chmei.nzbmanage.common.constant;  
/** 
 * redis 常量类 
 * Date:     2018年8月17日 上午11:54:10 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RedisKey {
	
	public static final String REDIS_SYS_RIGHTS_HASH = "report:manage:rightHash"; 
	public static final String REDIS_SYS_ROLE_RIGHT_HASH = "report:manage:roleRightHash"; 
	
	//-------------------------------------
	public static final String REDIS_SYSTEM_CONFIG = "system-config";
	public static final String REDIS_FREQUENCY_SWITCH ="frequencySwitch";
	public static final String REDIS_FREQUENCY_WHITE_LIST ="redis_frequency_white_list";
	public static final String REDIS_AGENT_REQUEST_FREQUENCY ="redis_agent_request_frequency";
}
  
