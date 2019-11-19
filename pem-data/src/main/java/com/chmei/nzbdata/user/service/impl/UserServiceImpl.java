package com.chmei.nzbdata.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chmei.nzbcommon.account.AccountState;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.UserService;
//import com.chmei.nzbdata.util.CacheUtil;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.RedisKey;
/**
 * ClassName: UserServiceImpl  
 * Function: 用户相关处理实现类
 * date: 2018年7月24日 15:16:58  
 * @author 徐卫强  
 * @since JDK 1.7
 */
@Transactional
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService   {

	/**
	 * 根据手机号获取用户帐号信息
	 * @param input
	 * @param output
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void queryUserAcctInfoByMobile(InputDTO input, OutputDTO output){
		Object obj = getBaseDao().queryForObject("user.queryUserAcctInfoByMobile", input.getParams());
		if(obj != null ){
			Map<String,Object> item = (Map<String,Object>) obj;
			output.setItem(item);
			Map<String,Object> accountMap = new HashMap<>();
			String isSeat = "0";
			String isErUser = "0";
			String isSpManager = "0";
			String seatState = "0";
			String erUserState = "0";
			String spManagerState = "0";
			String seatGrade = "0";
			String orgName = "";
			String seatCode = "";
			//grade name
			String gradeName = "";
			String orgCode = "";
			/*if(item.get("orgId") != null){
				// 帐号所属单位不为空，则获取相关的角色信息
				Map<String,Object> queryResult = (Map<String,Object>)getBaseDao().queryForObject("user.queryUserDetailInfo", input.getParams());
				if(queryResult.get("seatId") != null && queryResult.get("seatState") != null && "0,3,5,6".indexOf(queryResult.get("seatState").toString()) > -1){
					isSeat = "1";
					seatState = queryResult.get("seatState").toString();
					seatGrade = queryResult.get("gradeNo") != null ? queryResult.get("gradeNo").toString() : "0";
					seatCode = queryResult.get("seatCode") != null ? queryResult.get("seatCode").toString() : "";
				}
				if(queryResult.get("spUserId") != null){
					isSpManager = "1";
					spManagerState = queryResult.get("spUserState") != null ? queryResult.get("spUserState").toString() : "0";
				}
				if(queryResult.get("erUserId") != null){
					isErUser = "1";
					erUserState = queryResult.get("erUserState") != null ? queryResult.get("erUserState").toString() : "0";
				}
				orgName = queryResult.get("orgName") != null ? queryResult.get("orgName").toString() : "";
				orgCode = queryResult.get("orgCode") != null ? queryResult.get("orgCode").toString() : "";
				gradeName = queryResult.get("gradeName") != null ? queryResult.get("gradeName").toString() : "";
			}*/
			accountMap.put("isSeat", isSeat);
			accountMap.put("isErUser", isErUser);
			accountMap.put("isSpManager", isSpManager);
			accountMap.put("seatCode", seatCode);
			accountMap.put("seatState", seatState);
			accountMap.put("erUserState", erUserState);
			accountMap.put("spManagerState", spManagerState);
			accountMap.put("seatGrade", seatGrade);
			accountMap.put("orgName", orgName);
			accountMap.put("orgCode", orgCode);
			accountMap.put("gradeName", gradeName);
			output.setData(accountMap);
		}
	}
	
	/**
	 * 添加用户账号
	 * @param input
	 * @param output
	 */
	@Override
	public void addUserAccount(InputDTO input, OutputDTO output){
		Object obj = getBaseDao().queryForObject("user.queryUserAcctInfoByMobile", input.getParams());
		if (obj!=null) {
			output.setCode("1");
			output.setMsg("该手机号已经注册");
			return;
		}
		Map<String,Object> params = input.getParams();
		Date date = new Date();
		Long id = getSequence();
		params.put("id", id);
		params.put("crtUserId", id);
		params.put("crtTime", date);
		params.put("registerTime", date);
		params.put("accState", AccountState.Normal.getCode().toString());
		getBaseDao().insert("user.insertUserAccount", params);
		//给用户分配角色
		Map<String,Object> roleMap = new HashMap<>();
		roleMap.put("id", getSequence());
		roleMap.put("crtUserId", id);
		roleMap.put("crtTime", date);
		roleMap.put("roleId", Constants.MEMBER_ROLE);;
		roleMap.put("userId", id);
		getBaseDao().insert("TURoleUserMapper.insertRoleUserForUser", roleMap);
		output.setCode("0");
		output.setItem(params);
	}
	
	/**
	 * 重置用户密码
	 * @param input
	 * @param output
	 */
	@Override
	public void updateUserAccontPwd(InputDTO input, OutputDTO output){
		getBaseDao().update("user.updateUserAccountPwd", input.getParams());
	}
	
	/**
	 *  根据手机号获取管理用户信息
	 * @param input
	 * @param output
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void queryAdminUserInfoByMobile(InputDTO input, OutputDTO output){
		Object obj = getBaseDao().queryForObject("user.queryAdminUserInfoByMobile", input.getParams());
		if(obj != null ){
			output.setItem((Map<String,Object>) obj);
		}
	}
	
	/**
	 * 更新登录时间
	 * @param mobile
	 */
	@Override
	public void updateLoginTime(InputDTO input, OutputDTO output){
		input.getParams().put("loginTime", new Date());
		getBaseDao().update("user.updateLoginTime", input.getParams());
	}
	
	/**
	 * 获取冻结帐号，存入缓存
	 */
	@Override
	public void queryInitAllFreezeAccount(InputDTO input, OutputDTO output){
		List<Map<String,Object>> list = getBaseDao().queryForList("user.queryInitAllFreezeAccount", null);
		if(list != null && !list.isEmpty()){
			for(Map<String,Object> temp : list){
				getCacheService().hset(RedisKey.REDIS_ACCOUNT_FREEZE, temp.get("id").toString(), "1");
			}
		}else{
			getCacheService().hset(RedisKey.REDIS_ACCOUNT_FREEZE, "initFreezeAccount", "1"); // 仅用于缓存创建占位
		}
	}
}
