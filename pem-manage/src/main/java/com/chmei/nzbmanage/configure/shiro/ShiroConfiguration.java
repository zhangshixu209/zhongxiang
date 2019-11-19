package com.chmei.nzbmanage.configure.shiro;

import java.util.Arrays;

import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chmei.nzbmanage.common.shiro.JedisSessionDAO;
import com.chmei.nzbmanage.common.shiro.JedisSessionRepository;

/**
 * <p>系统只使用shiro做session共享
 * <p>权限部分是通过过滤器自己实现的
 * @author lixinjie
 * @since 2018-11-29
 */
@Configuration
public class ShiroConfiguration {
	
	@Bean
	public JedisSessionRepository sessionRepository() {
		return new JedisSessionRepository();
	}
	
	@Bean
	public SessionDAO sessionDAO() {
		return new JedisSessionDAO(sessionRepository());
	}

	@Bean
	public WebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDAO());
		return sessionManager;
	}
	
	@Bean
	public WebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager());
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return filterFactoryBean;
    }
	
	//由于已经使用FilterRegistrationBean注册了其它过滤器
	//就导致shiro官方提供的与springboot集成方式有个bug
	//原因是ShiroWebFilterConfiguration类的45行加了@ConditionalOnMissingBean注解导致的
	//因此放弃了官方的集成方式，application.yml中把shiro禁用，自己来注册bean
	@Bean
	public FilterRegistrationBean filterShiroFilterRegistrationBean() throws Exception {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter((AbstractShiroFilter)shiroFilterFactoryBean().getObject());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setOrder(100);
        return filterRegistrationBean;
    }
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
	    chainDefinition.addPathDefinition("/**", "anon");
	    return chainDefinition;
	}
	
}
