package com.chmei.nzbservice.user.service.impl;

import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.user.service.UserService;
import com.chmei.nzbservice.util.Constants;
/**
 * ClassName: UserServiceImpl  
 * Function: 用户相关处理实现类
 * date: 2018年7月24日 15:16:58  
 * @author 徐卫强  
 * @since JDK 1.7
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService   {

	/**
	 * 根据手机号获取用户帐号信息
	 * @param input
	 * @param output
	 */
	@Override
	public void queryUserAcctInfoByMobile(InputDTO input, OutputDTO output){
		getNzbDataService().execute(input, output);
	}
	
	/**
	 * 添加用户账号
	 * @param input
	 * @param output
	 */
	@Override
	public void addUserAccount(InputDTO input, OutputDTO output){
		getNzbDataService().execute(input, output);
	}
	
	/**
	 * 重置用户密码
	 * @param input
	 * @param output
	 */
	@Override
	public void updateUserAccontPwd(InputDTO input, OutputDTO output){
		getNzbDataService().execute(input, output);
	}
	
	/**
	 *  根据手机号获取管理用户信息
	 * @param input
	 * @param output
	 */
	@Override
	public void queryAdminUserInfoByMobile(InputDTO input, OutputDTO output){
		getNzbDataService().execute(input, output);
	}
	
	/**
	 * 将账号添加到redis中 过期时间是5分钟
	 * @param
	 */
	public void addLimitedUserMobileTORedis(InputDTO input, OutputDTO output){
		//将改用户放到redis中,让其在10分钟内不允许登录;  redis的key  limitedUserMobile:用户名(mobile号)
		String mobile = input.getParams().get("mobile").toString();
		String redis_mobile = Constants.REDIS_SYS_FAILURE_MOBILE;
		if(!getCacheService().isExist(redis_mobile+mobile)){
			getCacheService().setex(redis_mobile+mobile, "1", 60*5);
			output.setData(1);
			return ;
		}
		Integer num = Integer.parseInt(getCacheService().getString(redis_mobile+mobile)) + 1;
		getCacheService().setex(redis_mobile+mobile, ""+num, 60*5);
		output.setData(num);
	}

	/**
	 * 判断redis中是否包含过期key
	 *
	 * @param input
	 * @param output
	 */
	@Override
	public void getUserMobileTORedis(InputDTO input, OutputDTO output) {
		String mobile = input.getParams().get("mobile").toString();
		String redis_mobile = Constants.REDIS_SYS_FAILURE_MOBILE;
		boolean exist = getCacheService().isExist(redis_mobile+mobile);
		if(!exist){
			output.setData(false);
			return;
		}
		Integer num = Integer.parseInt(getCacheService().getString(redis_mobile+mobile));
		if(num == 3){
			output.setData(true);
		}else{
			output.setData(false);
		}
	}

	/**
	 * 将输入次数3次的 添加到redis中,24小时内不让实名;
	 *
	 * @param input
	 * @param output
	 */
	@Override
	public void addUserMobileTORedis(InputDTO input, OutputDTO output) {
		//将改用户放到redis中,让其在10分钟内不允许登录;  redis的key  limitedUserMobile:用户名(mobile号)
		String mobile = input.getParams().get("mobile").toString();
		String redis_mobile = Constants.REDIS_SYS_FAILURE_MOBILE;
		if(!getCacheService().isExist(redis_mobile+mobile)){
			getCacheService().setex(redis_mobile+mobile, "1", 60*1440);
			output.setData(1);
			return ;
		}
		Integer num = Integer.parseInt(getCacheService().getString(redis_mobile+mobile)) + 1;
		getCacheService().setex(redis_mobile+mobile, ""+num, 60*1440);
		output.setData(num);
	}

	/**
	 * 将账号添加到redis中 过期时间是5分钟
	 * @param
	 */
	public void getLimitedUserMobileTORedis(InputDTO input, OutputDTO output){
		String mobile = input.getParams().get("mobile").toString();
		String redis_mobile = Constants.REDIS_SYS_FAILURE_MOBILE;
		boolean exist = getCacheService().isExist(redis_mobile+mobile);
		if(!exist){
			output.setData(false);  
			return;
		}
		Integer num = Integer.parseInt(getCacheService().getString(redis_mobile+mobile));
		if(num == 6){
			output.setData(true);
		}else{
			output.setData(false);
		}
	}
	
	
	/**
	 * 判断改mobile是否存在被限制登录只有当value=6的时候才限制登录
	 * @param mobile
	 */
	/*public void existsLinmitedMobile(InputDTO input, OutputDTO output){
		String mobile = input.getParams().get("mobile").toString();
		 boolean exist = getCacheService().isExist("limitedUserMobile:"+mobile);
		 if(!exist){
			 output.setData(false);  
			 return;
		 }
		String num = getCacheService().getString("limitedUserMobile:"+mobile);
		if(exist && num.equals("6")){
			double time = getCacheService().ttl("limitedUserMobile:"+mobile).doubleValue();
			Long longTime = Math.round(time/60);
			output.setData(true);
			output.setTotal(longTime.intValue());
			return ;
		}
		 output.setData(false);      
	}*/
	
	/**
	 * 更新登录时间
	 * @param
	 */
	public void updateLoginTime(InputDTO input, OutputDTO output){
		getNzbDataService().execute(input, output);
	}
	
	/**
	 * 获取冻结帐号，存入缓存
	 */
	public void queryInitAllFreezeAccount(InputDTO input, OutputDTO output){
		getNzbDataService().execute(input, output);
	}

	/**
	 * 判断redis中是否包含过期key
	 *
	 * @param input
	 * @param output
	 */
	@Override
	public void getUserCodeIpTORedis(InputDTO input, OutputDTO output) {
		String codeIp = input.getParams().get("codeIp").toString();
		String redis_codeIp = Constants.REDIS_SYS_FAILURE_CODEIP;
		boolean exist = getCacheService().isExist(redis_codeIp+codeIp);
		if(!exist){
			output.setData(false);
			return;
		}
		Integer num = Integer.parseInt(getCacheService().getString(redis_codeIp+codeIp));
		if(num == 5){
			output.setData(true);
		}else{
			output.setData(false);
		}
	}

	/**
	 * 将输入次数5次的 添加到redis中,24小时不让发短信
	 *
	 * @param input
	 * @param output
	 */
	@Override
	public void addUserCodeIpTORedis(InputDTO input, OutputDTO output) {
//将改用户放到redis中,让其在10分钟内不允许登录;  redis的key  limitedUserMobile:用户名(mobile号)
		String codeIp = input.getParams().get("codeIp").toString();
		String redis_codeIp = Constants.REDIS_SYS_FAILURE_CODEIP;
		if(!getCacheService().isExist(redis_codeIp+codeIp)){
			getCacheService().setex(redis_codeIp+codeIp, "1", 60*1440);
			output.setData(1);
			return ;
		}
		Integer num = Integer.parseInt(getCacheService().getString(redis_codeIp+codeIp)) + 1;
		getCacheService().setex(redis_codeIp+codeIp, ""+num, 60*1440);
		output.setData(num);
	}
}
