package com.chmei.nzbmanage.common.controller;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;
import com.chmei.nzbcommon.cmutil.ControlConstants;
import com.chmei.nzbcommon.enums.ItemBelongEnum;
import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.configure.NzbManageProperties;

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());	
	
	@Autowired
	private IControlService nzbServiceControlServiceConsumer;
	@Autowired
	protected ICacheService cacheService;
	@Autowired
	protected NzbManageProperties nzbManageProperties;
	/** 通过入参和service名称和方法名称构造InputDTO调用Service获取OutputDTO **/
	public OutputDTO getOutputDTO(Map<String, Object> params,
			String service, String method) {
		
		return getOutputDTO(params, service, method, null);
	}
	
	public <T> OutputDTO getOutputDTO(String service, String method, T javaBean) {
		return getOutputDTO(service, method, javaBean, null);
	}
	
	/**
	 * 通过入参和service名称和方法名称构造InputDTO调用Service获取OutputDTO
	 * @param params
	 * @param service 服务名称
	 * @param method 服务方法
	 * @param isOperLog 是否开启传递操作日志必要参数: true:开启, false:关闭
	 * @return
	 */
	private OutputDTO getOutputDTO(Map<String, Object> params,
			String service, String method, ItemBelongEnum itemBelongEnum) {
		OutputDTO outputDTO = null;
		if (StringUtils.isNotBlank(service) && StringUtils.isNotBlank(method)) {
			InputDTO inputDTO = new InputDTO();
			inputDTO.setParams(params);
			inputDTO.setService(service);
			inputDTO.setMethod(method);
			// 增加通用comParams
			Map<String, String> comParams = inputDTO.getComParams();
			comParams.put("accountId", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.ID), null));
			comParams.put("operationUserMobile", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.MOBILE), null));
			comParams.put("operationUserName", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.USERNAME), null));
			
			// 增加日志logParams
			Map<String, String> logParams = inputDTO.getLogParams();
			logParams.put("uuid", UUID.randomUUID().toString());
			if(null != itemBelongEnum) {
				logParams.put("itemBelong", String.valueOf(itemBelongEnum.getItemBelong()));
				logParams.put("cltptIpAddr", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.IP_ADDR), null));
			}
			outputDTO = this.execute(inputDTO);
			logger.info(outputDTO.toString());
		} else {
			logger.error("服务名和方法名不能为空!");
		}
		return outputDTO;
	}
	
	private <T> OutputDTO getOutputDTO(String service, String method, T javaBean, ItemBelongEnum itemBelongEnum) {
		OutputDTO outputDTO = null;
		if (StringUtils.isNotBlank(service) && StringUtils.isNotBlank(method)) {
			InputDTO inputDTO = new InputDTO();
			inputDTO.setService(service);
			inputDTO.setMethod(method);
			inputDTO.setJavaBean(javaBean);
			// 增加通用comParams
			Map<String, String> comParams = inputDTO.getComParams();
			comParams.put("accountId", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.ID), null));
			comParams.put("operationUserMobile", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.MOBILE), null));
			comParams.put("operationUserName", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.USERNAME), null));
			
			// 增加日志logParams
			Map<String, String> logParams = inputDTO.getLogParams();
			logParams.put("uuid", UUID.randomUUID().toString());
			if(null != itemBelongEnum) {
				logParams.put("itemBelong", String.valueOf(itemBelongEnum.getItemBelong()));
				logParams.put("cltptIpAddr", Objects.toString(getSession().getAttribute(Constants.SESSION_USER.IP_ADDR), null));
			}
			outputDTO = this.execute(inputDTO);
			logger.info(outputDTO.toString());
		} else {
			logger.error("服务名和方法名不能为空!");
		}
		return outputDTO;
	}
	
	public OutputDTO getOutputDTOPlatform(Map<String, Object> params,
			String service, String method) {
		
		return getOutputDTO(params, service, method, ItemBelongEnum.platform_manage);
	}
	
	/* 调用后台执行service方法 */
	private OutputDTO execute(InputDTO inputDTO) {
		OutputDTO outputDTO = null;
		try {
			outputDTO = nzbServiceControlServiceConsumer.execute(inputDTO);

		} catch (Exception e) {
			logger.error("Invoke Service Error.", inputDTO.getService()
					+ "." + inputDTO.getMethod(), e);
			e.printStackTrace();
		}
		return outputDTO;
	}
	/* bean校验ajax返回 */
	protected OutputDTO returnValidatorAjaxResult() {
		OutputDTO outputDTO = new OutputDTO();
		outputDTO.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
		outputDTO.setCode("后台参数校验失败！");
		return outputDTO;
	}

	/* bean校验String返回 */
	protected String returnValidatorStrResult() {
		return "error/500";
	}

	/* bean校验ModelAndView返回 */
	protected ModelAndView returnValidatorMavResult() {
		return new ModelAndView("error/500");
	}



	/** Get the current Session **/
	public Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	/**
	 * 当前用户Id
	 */
	public Long getCurrUserId() {
		return (Long)getSession().getAttribute(Constants.SESSION_USER.ID);
	}
	
	/**
	 * 当前用户所有角色id
	 */
	public Long getCurrUserRoleIds() {
		return (Long)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
	}
	
	/**
	 * 当前用户名
	 */
	public String getCurrUserName() {
		return (String)getSession().getAttribute(Constants.SESSION_USER.USERNAME);
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	 /*	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeEcmaScript(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), false));
	}*/
}
