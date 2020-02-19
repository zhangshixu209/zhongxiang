package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;

/**
 * ClassName: UserService  
 * Function: 用户相关处理实现接口
 * date: 2018年7月24日 15:16:58  
 * @author 徐卫强  
 * @since JDK 1.7
 */
public interface UserService {
	
	/**
	 * 根据手机号获取用户帐号信息
	 * @param input
	 * @param output
	 */
	void queryUserAcctInfoByMobile(InputDTO input, OutputDTO output);
	
	/**
	 * 添加用户账号
	 * @param input
	 * @param output
	 */
	void addUserAccount(InputDTO input, OutputDTO output);
	
	/**
	 * 重置用户密码
	 * @param input
	 * @param output
	 */
	void updateUserAccontPwd(InputDTO input, OutputDTO output);
	
	/**
	 *  根据手机号获取管理用户信息
	 * @param input
	 * @param output
	 */
	void queryAdminUserInfoByMobile(InputDTO input, OutputDTO output);

	/**
	 * 将输入次数3次的 添加到redis中,5分钟内不让登录;
	 * @param mobile
	 */
	public void addLimitedUserMobileTORedis(InputDTO input, OutputDTO output);
	
	
	/**
	 * 判断redis中是否包含过期key
	 * @param mobile
	 */
	public void getUserMobileTORedis(InputDTO input, OutputDTO output);


	/**
	 * 将输入次数3次的 添加到redis中,5分钟内不让登录;
	 * @param mobile
	 */
	public void addUserMobileTORedis(InputDTO input, OutputDTO output);


	/**
	 * 判断redis中是否包含过期key
	 * @param mobile
	 */
	public void getLimitedUserMobileTORedis(InputDTO input, OutputDTO output);

	
	/**
	 * 判断改mobile是否存在被限制5分钟登录
	 * @param mobile
	 */
//	public void existsLinmitedMobile(InputDTO input, OutputDTO output);
	
	/**
	 * 更新登录时间
	 * @param mobile
	 */
	public void updateLoginTime(InputDTO input, OutputDTO output);
	
	/**
	 * 获取冻结帐号，存入缓存
	 */
	void queryInitAllFreezeAccount(InputDTO input, OutputDTO output);

	/**
	 * 判断redis中是否包含过期key
	 * @param cdeIp
	 */
	public void getUserCodeIpTORedis(InputDTO input, OutputDTO output);


	/**
	 * 将输入次数5次的 添加到redis中,24小时不让发短信
	 * @param cdeIp
	 */
	public void addUserCodeIpTORedis(InputDTO input, OutputDTO output);
}
