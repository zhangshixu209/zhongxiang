package com.chmei.nzbmanage.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.enums.OutPutEnum;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;


public class SessionFilter  implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);
	
    //链接白名单 
    private Set<String> whiteList = null;
    //后缀白名单
    private Set<String> whitePostFix = null;
    //没有登录跳转地址
    private String notLoginUrl = null;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
    	HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String url = servletRequest.getServletPath();
		String post_fix = url.substring(url.lastIndexOf(".")+1);
        if(whitePostFix.contains(post_fix) || isWhiteURL(url)) {
        	chain.doFilter(request, response);
        }else {
    		try {
    			Session session = SecurityUtils.getSubject().getSession();
    			Long sessionUserId = (Long) session.getAttribute(Constants.SESSION_USER.ID);
        		if(sessionUserId == null || sessionUserId == 0){
        			// 未登录，跳转到登录页面
        			denyAccess(servletRequest,servletResponse, OutPutEnum.NotLogin.getCode(),
	    					OutPutEnum.NotLogin.getDesc(), notLoginUrl);
        		}else{
        			chain.doFilter(request, response);
        		}
			} catch (Exception e) {
				logger.error("SessionFilter过滤器异常："+e.getMessage(), e);
			}
    	}
    }

	
	
	/**
	 * 拒绝访问
	 * @param response
	 * @param msg
	 * @param isHtml
	 * @throws IOException
	 */
	private void denyAccess(HttpServletRequest request,HttpServletResponse response,
			                String code, String msg, String url) throws IOException {
		 //判断是否为ajax请求,默认不是
		boolean isAjaxRequest = false; 
		if(StringUtil.isNotEmpty(request.getHeader("x-requested-with"))
				&& request.getHeader("x-requested-with").equals("XMLHttpRequest")){ 
			isAjaxRequest = true; 
		} 
		String contextPath = request.getContextPath();
		if(isAjaxRequest) {
			response.setContentType("text/html; charset=UTF-8");
			response.setStatus(499);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(contextPath+url);
				out.flush();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}finally{
				if(out != null){
					out.close();
				}
			}
		} else {
			response.sendRedirect(contextPath + url);
		}
	}
	
	//判断是不是白名单
	private boolean isWhiteURL(String url) {
        for (String whiteURL : whiteList) {  
            if (url.matches(whiteURL)) { 
                return true;  
            }  
        } 
        return false;  
    }
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		//String whiteListStr = fConfig.getInitParameter(Constants.FILTER_PARAM.LOGIN_WHITE_LIST);  
        //whiteList = strToSet(whiteListStr);
        //String whitePostFixStr = fConfig.getServletContext().getInitParameter(Constants.FILTER_PARAM.WHITE_POST_FIX);  
        //whitePostFix = strToSet(whitePostFixStr);
        //notLoginUrl = PropertiesUtil.getString(Constants.FILTER_PARAM.LOGIN_URL);
	}
	
	public void initWhiteList(String whiteList) {
		  this.whiteList = strToSet(whiteList);
	}
	
	public void initWhitePostFix(String whitePostFix) {
		this.whitePostFix = strToSet(whitePostFix);
	}
	
	public void initNotLoginUrl(String notLoginUrl) {
		this.notLoginUrl = notLoginUrl;
	}
	
	private Set<String> strToSet(String urlStr) {  
        String[] array = urlStr.split(",");  
        Set<String> urlSet = new HashSet<String>();  
        for (String url : array) {  
            url = url.trim();  
            if (url.length() == 0) {  
                continue;  
            }  
            urlSet.add(url);  
        }  
        return urlSet;  
    }  
	
	@Override
	public void destroy() {
		
	}
}
