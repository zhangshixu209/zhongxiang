package com.chmei.nzbmanage.login.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chmei.nzbcommon.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.cmutil.ControlConstants.RETURN_CODE;
import com.chmei.nzbcommon.enums.Validity;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.exception.NzbManageException;
import com.chmei.nzbmanage.login.bean.LoginForm;

/**
 * ClassName: ErUserManageController 
 * Function: 平台管理用户管理
 * date: 2018年7月19日 下午5:44:26
 * @author xuwq
 * @since JDK 1.7
 */
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {

	/**
	 * log
	 */
	private static final Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * 判断开关是否关闭
	 */
	@RequestMapping("pb/admin/needs")
	public OutputDTO needs() {
		OutputDTO out = new OutputDTO("0", "获取成功");
		String loginNeedCheck = nzbManageProperties.getLoginNeedMsg();
		out.setData(loginNeedCheck);
		return out;
	}

	/**
	 * 用户登录
	 * 
	 * @author 徐卫强
	 * @param loginForm
	 * @return
	 */
	@RequestMapping("pb/admin/login")
	public OutputDTO login(@ModelAttribute @Validated LoginForm loginForm, HttpServletRequest request)
			throws NzbManageException {
		OutputDTO output = new OutputDTO();
		@SuppressWarnings("deprecation")
		String decodePaw = URLDecoder.decode(loginForm.getPassword());
		loginForm.setPassword(decodePaw);
		// 检验图片验证码
		String codeImg = (String) getSession().getAttribute("userCheckCode");
		if (StringUtil.isEmpty(codeImg) || !codeImg.equalsIgnoreCase(loginForm.getImgCode())) {
			getSession().removeAttribute("userCheckCode");
			output.setCode("-1");
			output.setMsg("图片验证码错误");
			return output;
		}
		// 清除session中的图片验证码
		getSession().removeAttribute("userCheckCode");
		// 是否检验短信验证码
		String needMsg = nzbManageProperties.getLoginNeedMsg();
		if ("1".equals(needMsg)) {
			// 获取短信验证码session中验证码以及发送时间
			if (null == getSession().getAttribute(loginForm.getMobile())) {
				output.setCode("-1");
				output.setMsg("请获取手机验证码");
				return output;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> msgSession = (Map<String, Object>) getSession().getAttribute(loginForm.getMobile());
			// 校验短信验证码,判断是否超时
			String msgCodeTime = (String) msgSession.get("msgCodeTime");
			boolean flg = checktimeout(msgCodeTime);
			if (!flg) {
				output.setCode("-1");
				output.setMsg("验证码超时，请重新获取");
				return output;
			}
			// 验证短信验证码是否正确
			String msgCode = (String) msgSession.get("msgCode");
			if (StringUtil.isEmpty(msgCode) || !msgCode.equalsIgnoreCase(loginForm.getMsgCode())) {
				output.setCode("-1");
				output.setMsg("短信验证码错误");
				return output;
			}
			// 清空session中的短信信息
			getSession().removeAttribute(loginForm.getMobile());
		}
		// 验证帐号密码
		output = userPasswordIsMatch(loginForm.getMobile(), loginForm.getPassword(), IpUtil.getIpAddr(request));
		return output;
	}

	/**
	 * 判断是否超时
	 * 
	 * @param datestr
	 * @return
	 */
	private boolean checktimeout(String datestr) {
		Date date = DateUtil.string2Date(datestr, DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		Date curDate = new Date();
		if (StringUtils.isNotEmpty(datestr)) {
			long dg = (curDate.getTime() - date.getTime()) / 1000;
			if (dg <= nzbManageProperties.getSmsExpireSeconds()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验用户帐号密码匹配
	 * 
	 * @author 徐卫强
	 * @param username
	 * @param password
	 * @return
	 */
	public OutputDTO userPasswordIsMatch(String mobile, String password, String ipAddr) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		/*
		 * //调用jhpt层先判断该手机号是否存在被限制的redis中 OutputDTO outDTO =
		 * getOutputDTO(params, "userService", "existsLinmitedMobile"); Boolean
		 * flag = (Boolean)outDTO.getData(); if(flag){ return new
		 * OutputDTO("-1", "密码错误次数过多,帐号已被锁定,请"+outDTO.getTotal()+"分钟后稍后再试"); }
		 */
		OutputDTO output = getOutputDTO(params, "userService", "queryAdminUserInfoByMobile");
		if (output.getItem() == null || output.getItem().isEmpty()) {
			return new OutputDTO("-1", "帐号不存在");
		}
		if (Validity.VALID.getCode() != Integer.parseInt(String.valueOf(output.getItem().get("validStsCd")))) {
			return new OutputDTO("-1", "帐号已禁用,请请联系管理员启用!");
		}
		String pwd = (String) output.getItem().get("loginPw");
		if (MD5Util.verify(password, pwd)) {
			//锁定密码判断是否释放
			OutputDTO dto = getOutputDTO(params, "userService", "getLimitedUserMobileTORedis"); 
			boolean data = (boolean) dto.getData();
			if(data){
				return new OutputDTO("-1", "账号已被锁定,请5分钟后再登录!");
			}
			// 匹配
			getSession().setAttribute(Constants.SESSION_USER.ID, output.getItem().get("id"));
			getSession().setAttribute(Constants.SESSION_USER.MOBILE, mobile);
			getSession().setAttribute(Constants.SESSION_USER.USERNAME, output.getItem().get("userName"));
			getSession().setAttribute(Constants.SESSION_USER.IP_ADDR, ipAddr);
			getSession().setAttribute(Constants.SESSION_USER.USER_DEPARTMENT_ID, output.getItem().get("userDepartmentId"));
			// 获取该管理员拥有的橘色
			queryRolesByUserId((Long) output.getItem().get("id"));
			Map<String, Object> map = new HashMap<>();
			map.put("userId", output.getItem().get("id"));
			map.put("userMobile", mobile);
			map.put("userName", output.getItem().get("userName"));
			map.put("ipAddr", ipAddr);
			// 登录成功,记录登录日志
			getOutputDTO(map, "operateLogService", "saveLoginLogInfo");
			return new OutputDTO("0", "success");
		} else {
			OutputDTO dto = getOutputDTO(params, "userService", "addLimitedUserMobileTORedis"); // 将账号添加到redis中并设置过期时间是5分钟
			Integer num = (Integer) dto.getData();
			if (num == 6) {
				return new OutputDTO("-1", "账号已被锁定,请5分钟后再登录..");
			}
			return new OutputDTO("-1", "帐号或密码错误,还剩"+(6 - num)+"次");
		}
	}

	/**
	 * 获取验证码图片
	 */
	@ResponseBody
	@RequestMapping(value = "pb/admin/imgCode", method = RequestMethod.GET)
	public void userCheckCode(HttpServletResponse response) {
		ServletOutputStream outStream = null;
		try {
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String code = SecurityCode.getSecurityCode();
			getSession().setAttribute("userCheckCode", code);
			// 将图像输出到Servlet输出流中。
			outStream = response.getOutputStream();
			ByteArrayInputStream inputStream = SecurityImage.getImageAsInputStream(code);
			int len = inputStream.available();
			if (len > 0) {
				byte[] bb = new byte[1024];
				while (inputStream.read(bb) > 0) {
					outStream.write(bb);
				}
			}
			outStream.close();

		} catch (IOException e) {
			logger.error("userCheckCode:", e);
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 发送短信验证码    暂时无用
	@RequestMapping("pb/admin/sendMsg")
	public OutputDTO sendRegMsg(String phone, String imgCode) {
		OutputDTO outputDTO = new OutputDTO();
		Session session = getSession();
		try {
			// 验证图片验证码
			if (null == getSession().getAttribute("userCheckCode")) {
				return new OutputDTO("3", "图片验证码错误");
			}
			String codeImg = getSession().getAttribute("userCheckCode").toString();
			if (StringUtil.isEmpty(codeImg) || !codeImg.equalsIgnoreCase(imgCode)) {
				return new OutputDTO("3", "图片验证码错误");
			}
			String REGEX_MOBILE = "^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$";
			if (StringUtil.isEmpty(phone) || !Pattern.matches(REGEX_MOBILE, phone)) {
				return new OutputDTO("3", "请填写正确的手机号");
			}
			// 判断手机号是否存在
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", phone);
			OutputDTO output = getOutputDTO(params, "userService", "queryAdminUserInfoByMobile");
			if (output.getItem() == null || output.getItem().isEmpty()) {
				return new OutputDTO("3", "帐号不存在");
			}
			if (!"1".equals(String.valueOf(output.getItem().get("validStsCd")))) {
				return new OutputDTO("3", "帐号已冻结");
			}
			// 发送短信验证码
			String msgCode = null;// DxSmsSendUtil.getSmsCode();
			boolean sendResult = true;// DxSmsSendUtil.send(phone, msgCode);
			if (sendResult) {
				logger.info(phone + "的验证码为：" + msgCode);
				Map<String, Object> msgSession = new HashMap<>();
				msgSession.put("msgCode", msgCode);
				msgSession.put("msgCodeTime",
						DateUtil.date2String(new Date(), DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
				session.setAttribute(phone, msgSession);
				outputDTO = new OutputDTO("1", "验证码发送成功");
				// outputDTO.setData(msgCode);
//				logger.info(phone + "的验证码发送成功," + msgCode);
				return outputDTO;
			} else {
				return new OutputDTO("-1", "验证码发送失败");
			}
		} catch (Exception e) {
			outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
			outputDTO.setMsg("验证码发送失败");
			logger.error("LoginController.sendRegMsg：" + e);
			return outputDTO;
		}

	}

	/**
	 * 获取管理员所拥有的角色的所有权限
	 * 
	 * @author lianziyu
	 * @param userId
	 * @return
	 */
	private void queryRolesByUserId(Long userId) {
		String roleIds = "";
		String roleNames = "";
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		OutputDTO output = getOutputDTO(param, "roleUserService", "queryRoleUserAll");
		if (null != output.getItems()) {
			List<Map<String, Object>> roleUserList = output.getItems();
			for (Map<String, Object> map : roleUserList) {
				if (null != map && StringUtil.isNotEmpty(String.valueOf(map.get("roleId")))) {
					roleIds = roleIds + "," + map.get("roleId");
					roleNames = roleNames + "," + map.get("roleName");
				}
			}
		}
		roleIds = roleIds.length() > 0 ? roleIds.substring(1) : roleIds;
		roleNames = roleNames.length() > 0 ? roleNames.substring(1) : roleNames;
		getSession().setAttribute(Constants.SESSION_USER.ROLE_IDS, roleIds);
		getSession().setAttribute(Constants.SESSION_USER.ROLE_NAMES, roleNames);

	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@RequestMapping("pb/admin/lgOut")
	public OutputDTO lgOut() {
		OutputDTO output = new OutputDTO();
		try {
			Session session = getSession();
			if (null != session) {
				// 记录登出日志
				getOutputDTOPlatform(null, "operationLogService", "logoutLog");
				Collection<Object> sessionKeys = session.getAttributeKeys();
				for (Object key : sessionKeys) {
					session.removeAttribute(key);
				}
			}
			output = new OutputDTO("0", "退出登录成功");
			logger.info("退出登录成功");
		} catch (Exception e) {
			output.setCode(RETURN_CODE.SYSTEM_ERROR);
			output.setMsg("退出登陆失败");
			logger.error("LoginController.lgOut" + e);
		}
		return output;
	}

	/**
	 * 获取用户登陆信息
	 * 
	 * @return
	 */
	@RequestMapping("pb/admin/getLoginInfo")
	public OutputDTO getLoginInfo() {
		OutputDTO output = new OutputDTO(RETURN_CODE.IS_OK, "查询成功");
		try {
			Session session = getSession();
			Map<String, Object> item = output.getItem();
			item.put("roleName", session.getAttribute(Constants.SESSION_USER.ROLE_NAMES));
			item.put("userName", session.getAttribute(Constants.SESSION_USER.USERNAME));
		} catch (Exception e) {
			output.setCode(RETURN_CODE.SYSTEM_ERROR);
			output.setMsg("获取用户登陆信息失败");
			logger.error("LoginController.lgOut" + e);
		}
		return output;
	}
	
	
	/**
	 * 通过手机号查询用户登录信息
	 * @return output 返回结果
	 */
    @RequestMapping("pb/admin/queryUserLoginInfo")
    public OutputDTO queryUserLoginInfo() {
        OutputDTO output = new OutputDTO();
        try {
            Session session = getSession();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("mobile", session.getAttribute(Constants.SESSION_USER.MOBILE));
            output = getOutputDTO(params, "userService", "queryUserAcctInfoByMobile");
            Map<String, Object> map = output.getItem();
            String pwd = (String) map.get("loginPw");
            String password = "123456";
            if (MD5Util.verify(password, pwd)) {
                output.setCode("0");
            } else {
                output.setCode("-9999");
            }
        } catch (Exception e) {
            output.setCode(RETURN_CODE.SYSTEM_ERROR);
            output.setMsg("获取用户登陆信息失败");
            logger.error("LoginController.queryUserLoginInfo" + e);
        }
        return output;
    }
}