package com.chmei.nzbmanage.configure.filter;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chmei.nzbmanage.common.filter.RightFilter;
import com.chmei.nzbmanage.common.filter.SessionFilter;
import com.chmei.nzbmanage.common.filter.XSSFilter;

/**
 * @author lixinjie
 * @since 2018-11-29
 */
@Configuration
public class FilterConfiguration {

	@Bean
	public FilterRegistrationBean sessionFilter() {
		FilterRegistrationBean regBean = new FilterRegistrationBean();
		SessionFilter sessionFilter = new SessionFilter();
		sessionFilter.initWhiteList("/login/login.html,/noRight.html,/login/share.html,/api/pb/admin/.*," +
				"/api/member/.*,/api/recharge/.*,/api/rotation/.*,/api/friend/.*,/api/shareOutBonus/.*,/api/appVersion/.*");
		sessionFilter.initWhitePostFix("js,css,png,jpg,gif,mp3,mp4,icon,ico");
		sessionFilter.initNotLoginUrl("/login/login.html");
		regBean.setFilter(sessionFilter);
		regBean.setUrlPatterns(Arrays.asList("/*"));
		regBean.setOrder(200);
		return regBean;
	}
	
	@Bean
	public RightFilter rightFilterBean() {
		return new RightFilter();
	}
	
	@Bean
	public FilterRegistrationBean rightFilter() {
		FilterRegistrationBean regBean = new FilterRegistrationBean();
		RightFilter rightFilter = rightFilterBean();
		rightFilter.initWhiteList("/index.html,/noRight.html,/login/login.html,/login/share.html," +
				"/api/pb/admin/.*,/assets/.*,/api/adminRight/.*,/api/member/.*,/api/recharge/.*,/api/rotation/.*,/api/friend/.*,/api/shareOutBonus/.*,/api/appVersion/.*");
		rightFilter.initWhitePostFix("js,css,png,jpg,gif,mp3,mp4,icon,ico");
		rightFilter.initNotLoginUrl("/login/login.html");
		rightFilter.initNotRightUrl("/noRight.html");
		regBean.setFilter(rightFilter);
		regBean.setUrlPatterns(Arrays.asList("/*"));
		regBean.setOrder(300);
		return regBean;
	}
	
	@Bean
	public FilterRegistrationBean xSSFilter() {
		FilterRegistrationBean regBean = new FilterRegistrationBean();
		XSSFilter xSSFilter = new XSSFilter();
		xSSFilter.initWhiteList("/api/adminRight/btnAuthorize,/api/sys/right/addRight," +
				"/api/sys/right/editRight,/api/pb/admin/login,/api/member/.*,/api/recharge/.*,/api/rotation/.*,/api/friend/.*,/api/shareOutBonus/.*,/api/appVersion/.*");
		regBean.setFilter(xSSFilter);
		regBean.setUrlPatterns(Arrays.asList("/api/*"));
		regBean.setOrder(400);
		return regBean;
	}
}
