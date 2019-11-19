package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;

/**
 * ClassName: UserService  
 * Function: 用户相关处理实现类接口
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
	 * 更新登录时间
	 * @param mobile
	 */
	void updateLoginTime(InputDTO input, OutputDTO output);
	
	/**
	 * 获取冻结帐号，存入缓存
	 */
	void queryInitAllFreezeAccount(InputDTO input, OutputDTO output);
}
