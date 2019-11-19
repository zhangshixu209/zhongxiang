package com.chmei.nzbmanage.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbcommon.enums.OutPutEnum;
import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.constant.RedisKey;
import com.chmei.nzbmanage.dubbo.consumer.NzbServiceControlServiceConsumer;

/**
 * 权限过滤器  
 * Date:     2018年9月4日 下午3:08:15 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RightFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(RightFilter.class);
	
	@Autowired
	private ICacheService cacheService;
	
    //链接白名单 
    private Set<String> whiteList = null;
    //后缀白名单
    private Set<String> whitePostFix = null;
    //没有权限跳转地址
    private String notRightUrl = null;
    //没有登录跳转地址
    private String notLoginUrl = null;
    
    
    /**
     * 权限拦截
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
    	HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String url = servletRequest.getServletPath();
		boolean isHtml = url.endsWith(Constants.FILTER_PARAM.HTML_EXT);
		String post_fix = url.substring(url.lastIndexOf(".")+1);
        if(whitePostFix.contains(post_fix.toLowerCase()) || isWhiteURL(url)) {
        	chain.doFilter(request, response);
        } else {
    		try {
    			Session session = SecurityUtils.getSubject().getSession();
    			Long sessionUserId = (Long) session.getAttribute(Constants.SESSION_USER.ID);
        		if(sessionUserId == null || sessionUserId == 0){
        			// 未登录，跳转到登录页面
        			denyAccess(servletResponse, OutPutEnum.NotLogin.getCode(),
	    					OutPutEnum.NotLogin.getDesc(), notLoginUrl, isHtml);
        		}else{
        			//校验角色Id是否存在
        			String roleIds = (String) session.getAttribute(Constants.SESSION_USER.ROLE_IDS);
        			if(StringUtil.isEmpty(roleIds)) {
        				//可以获取修改密码的图片
        				if(url.indexOf("index/main/imgCode") !=-1){
        					logger.info("[{}]用户，试图访问管理端[{}]，但没有角色信息 ,可以访问!", session.getAttribute(Constants.SESSION_USER.MOBILE),url);
        					//是超管
            				chain.doFilter(request, response);
            				return;
        				}
        				logger.info("[{}]用户，试图访问管理端[{}]，但没有角色信息 !", session.getAttribute(Constants.SESSION_USER.MOBILE),url);
        				denyAccess(servletResponse, OutPutEnum.NotRight.getCode(),OutPutEnum.NotRight.getDesc(), notRightUrl, isHtml);
        				return;
        			}
        			//校验所有权限是否存在
        			if(!cacheService.isExist(RedisKey.REDIS_SYS_RIGHTS_HASH)) {
        				//不存在，重新加载所有权限
        				initAllRight(servletRequest.getServletContext());
        			}
        			//校验是否有角色权限存在
        			if(!cacheService.isExist(RedisKey.REDIS_SYS_ROLE_RIGHT_HASH)) {
        				//不存在，重新加载所有角色权限
        				initAllRoleRight(servletRequest.getServletContext());
        			}
        			if(roleIds.contains(Constants.ROOT_ROLE)) {
        				//是超管
        				chain.doFilter(request, response);
        				return;
        			}
        			boolean isAccess = isAuthorized(url, roleIds);
        			if(!isAccess) {
        				logger.info("[{}]用户，试图访问管理端[{}]，但没有该权限!", session.getAttribute(Constants.SESSION_USER.MOBILE),url);
        				denyAccess(servletResponse, OutPutEnum.NotRight.getCode(),
		    					OutPutEnum.NotRight.getDesc(), notRightUrl, isHtml);
        				return;
        			}
        			chain.doFilter(request, response);
        		}
			} catch (Exception e) {
				logger.error("SessionFilter过滤器异常："+e.getMessage(), e);
			}
    	}
    }

	/**
	 * 校验是否被授权访问
	 * @param url
	 * @param privCode
	 * @param rights
	 * @return
	 */
	private boolean isAuthorized(String url, String roleIds) {
		boolean isAccess = Constants.FILTER_PARAM.DEFAUT_ACCESS || true;	// 默认允许访问
//		//判断缓存中是否有缓存的权限
//		if(cacheService.isExist(RedisKey.REDIS_SYS_RIGHTS_HASH)) {
//			String urlHash = MD5Util.md5(url);
//			if(cacheService.hget(RedisKey.REDIS_SYS_RIGHTS_HASH, urlHash) != null) {
//				// 按钮权限校验
//				Map<String, Object> roleRight = checkRolesHasRight(roleIds, urlHash);
//				if(null!=roleRight) {
//					isAccess = isCanAccess(roleRight, url);
//				}else {                                      
//					isAccess = false;
//				}
//			}
//		}
		return isAccess;
	}
	
	//用户的角色中是否有包含该权限，包含返回该权限，不包含，返回null
	@SuppressWarnings("unchecked")
	private Map<String, Object> checkRolesHasRight(String roleIds,String key) {
		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			String roleRightStr = cacheService.hget(RedisKey.REDIS_SYS_ROLE_RIGHT_HASH, roleId);
			Map<String, Object> roleRight = JsonUtil.convertJson2Object(roleRightStr, Map.class);
			if(null!=roleRight&&roleRight.containsKey(key)) {
				String rightStr = (String) roleRight.get(key);
				return JsonUtil.convertJson2Object(rightStr, Map.class);
			}
		}
		return null;
	}
	
	/**
	 * 校验是否被授权访问
	 * @param rightJson
	 * @param url
	 * @param privCode
	 * @return
	 */
	private boolean isCanAccess(Map<String, Object> right, String url) {
		String tsvldUrlParaFlag = (String) right.get("tsvldUrlParaFlag");
		String urlAddr = (String) right.get("urlAddr");
		if(Constants.FILTER_PARAM.CHK_URL.equals(tsvldUrlParaFlag)) { 
			// 校验URL和权限代码
			return url.equals(urlAddr);
		} else {
			return true;
		}
	}
	
	/**
	 * 拒绝访问
	 * @param response
	 * @param msg
	 * @param isHtml
	 * @throws IOException
	 */
	private void denyAccess(HttpServletResponse response, String code, String msg, String url, boolean isHtml) throws IOException {
		if(isHtml) {
			response.sendRedirect(url);
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				OutputDTO output = new OutputDTO(code, msg);
				//前端跳转的url
				output.setData(url);
				out.print(JsonUtil.convertObject2Json(output));
				out.flush();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}finally{
				if(out != null){
					out.close();
				}
			}
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
		initAllRight(fConfig.getServletContext());
		initAllRoleRight(fConfig.getServletContext());
		//String whiteListStr = fConfig.getInitParameter(Constants.FILTER_PARAM.RIGHT_WHITE_LIST);  
        //whiteList = strToSet(whiteListStr);
        //String whitePostFixStr = fConfig.getServletContext().getInitParameter(Constants.FILTER_PARAM.WHITE_POST_FIX);  
        //whitePostFix = strToSet(whitePostFixStr);
        //notRightUrl = PropertiesUtil.getString(Constants.FILTER_PARAM.NO_RIGHT_URL);
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
	
	public void initNotRightUrl(String notRightUrl) {
		this.notRightUrl = notRightUrl;
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
	
	/**
	 * 加载所有权限菜单和按钮
	 */
	private void initAllRight(ServletContext context) {
		ApplicationContext ac = WebApplicationContextUtils .getWebApplicationContext(context); 
		NzbServiceControlServiceConsumer ctrlService = ac.getBean(NzbServiceControlServiceConsumer.class);
		InputDTO input = new InputDTO();
		input.setService("rightManageService");
		input.setMethod("queryInitAllRight");
		logger.info("初始化加载系统权限");
		ctrlService.execute(input);
	} 
	
	/**
	 * 加载所有角色权限
	 */
	private void initAllRoleRight(ServletContext context) {
		ApplicationContext ac = WebApplicationContextUtils .getWebApplicationContext(context); 
		NzbServiceControlServiceConsumer ctrlService = ac.getBean(NzbServiceControlServiceConsumer.class);
		InputDTO input = new InputDTO();
		input.setService("rightManageService");
		input.setMethod("queryInitAllRoleRight");
		logger.info("初始化加载角色权限");
		ctrlService.execute(input);
	} 
	@Override
	public void destroy() {
		  
	}
}
  
