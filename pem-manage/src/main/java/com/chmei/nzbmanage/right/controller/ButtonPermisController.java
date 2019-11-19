package com.chmei.nzbmanage.right.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.MD5Util;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.constant.Constants.SESSION_USER;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.right.bean.AdminAddForm;
import com.chmei.nzbmanage.right.bean.AdminQueryForm;
import com.chmei.nzbmanage.right.bean.AdminRoleUpdateFrom;
import com.chmei.nzbmanage.right.bean.AdminUpdateForm;
import com.chmei.nzbmanage.right.bean.RoleQueryForm;

/**
 * 按钮权限控制器
 * @Date: 2018年8月13日 上午11:13:27
 * @author 翟超锋
 * @version 1.0
 * @since JDK 1.7
 */
@RestController
@Validated
@RequestMapping("/api/admin")
public class ButtonPermisController extends BaseController {

	/**
	 * log
	 */
	private static final Logger logger = LoggerFactory.getLogger(ButtonPermisController.class);

	/**
	 * 按钮权限验证
	 * 
	 * @author 翟超锋
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/report_button_permis")
	public Map<String, Object> queryAdmins(HttpServletRequest request, HttpServletResponse response,String buttonPermis) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", 1);//预定返回1
		return map;
	}

}
