package com.chmei.nzbmanage.common.filter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.util.DateUtil;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.exception.NzbManageException;


public class AccessLogFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(AccessLogFilter.class);

	@Override
	public void destroy() {

	}

	/**
	 * 放行验证
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Session session = SecurityUtils.getSubject().getSession();//shiro session
		
		String refer = req.getHeader("Referer");
		String ua = req.getHeader("User-Agent");

		/*---------------Start--------用户相关设备,请求信息-------*/
		String userIP = "";//ip
		try {
			userIP = getIpAddr(req);     //获取用户的IP地址-------------;
		} catch (NzbManageException e) {
			LOG.error("获取登录ip地址失败:[{}]", e.getMessage());
		}
		String userID = session.getAttribute(Constants.SESSION_USER.ID)==null?"":session.getAttribute(Constants.SESSION_USER.ID).toString();//用户的id
		if(StringUtil.isEmpty(userID)){//获取已登录账号或者sessionid		
		  userID = session.getId().toString();			
		}
		String requestTimeStamp = DateUtil.date2String(new Date());//请求时间戳
		String responseTimeStamp = "";//响应时间戳 
		String userOrigin = getClientType(req);//客户端类型
		String userReference = refer == null ? "" : refer;
		String requestURL = req.getRequestURL().toString(); //请求的路径......
		String browserVersion = getBrowserVersion(ua);//浏览器版本
		String browserPlatform = getOS(ua);//获取操作系统
		String isMobileDevice = ua.contains("Mobile") ? "是" : "否";           //移动设备
		String mobileDeviceModel = getModel(ua);//获取设备型号
		/*----------------End--------------*/
		

			//将设备信息封装到buillder中 整合
		StringBuilder msg = new StringBuilder("");
		msg.append(userIP).append("|").append(userID).append("|")
				.append(requestTimeStamp).append("|").append(responseTimeStamp)
				.append("|").append(userOrigin).append("|")
				.append(userReference).append("|").append(requestURL).append("|")
				.append("|").append(browserVersion).append("|")
				.append(browserPlatform).append("|").append(isMobileDevice)
				.append("|").append(mobileDeviceModel);
		LOG.info("登录信息=[{}]",msg.toString());
		chain.doFilter(req, res);          //放行
	}

	/**
	 * 将信息记录到日志文件
	 * 
	 * @param logFile
	 *            日志文件
	 * @param mesInfo
	 *            信息
	 * @throws IOException
	 */
	public void logMsg(File logFile, String mesInfo) throws IOException {
		if (logFile == null) {
			throw new IllegalStateException("logFile can not be null!");
		}
		Writer txtWriter = new FileWriter(logFile, true);
		txtWriter.write(mesInfo);
		txtWriter.flush();
		txtWriter.close();
	}
	
	private static String getModel(String userAgent){//windows phone--
		if(userAgent.contains("Mac OS")){//Apple Like
			if(userAgent.contains("iPhone")){
				return "iPhone";
			}else if(userAgent.contains("iPad")){
				return "iPad";
			}else if(userAgent.contains("iPod")){
				return "iPod";
			}
		}
		String regex = ";\\s?([^;]+?)\\s?(Build)?/";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userAgent);
		String model = "";
		if(matcher.find()){
			model = matcher.group(1).trim().replace(") AppleWebKit", "");
		}
		return model;
	}
	
	private static String getOS(String userAgent){
		String model = "";
		String regex = "U; (.+?);";
		if(userAgent.contains("iPhone")){
			regex = "iPhone; CPU (.+?) like Mac OS";
		}else if(!userAgent.contains("Android")){
			if(userAgent.contains("Windows")){
				return "Windows";
			}else{
				return "Unix";
			}
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userAgent);
		if(matcher.find()){
			model = matcher.group(1).trim();
		}
		
		return model;
	}
	
	@SuppressWarnings("unused")
	private static String getWxVersion(String userAgent){
		String model = "";
		String regex = "MicroMessenger/(.+?)($| .+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userAgent);
		if(matcher.find()){
			model = matcher.group(1).trim();
		}
		
		return model;
	}
	
	private static String getBrowserVersion(String userAgent){
		String model = "";
		String regex = "(MicroMessenger/(.+?))($| .+)";
		if(!userAgent.contains("MicroMessenger")){
			regex = "((Chrome|\\w+Browser|Firefox|Safari)/(.+?))($| .+)";
		}
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(userAgent);
		if(matcher.find()){
			model = matcher.group(1).trim();
		}
		
		return model;
	}
	
	private static String getClientType(HttpServletRequest req){//客户端类型：微信/浏览器
		if(StringUtil.isNotEmpty(req.getHeader("wxuserAgent"))){//windows phone特殊处理
			return "微信";
		}else{
			return req.getHeader("User-Agent").contains("MicroMessenger") ? "微信" : "浏览器";
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

		//
	}
	
	/** 
	 * 获取访问用户的客户端IP（适用于公网与局域网）.              应该不需要改动的
	 */
	private static final String getIpAddr(final HttpServletRequest request)  
	        throws NzbManageException {  
	    if (request == null) {  
	        throw new NzbManageException("getIpAddr method HttpServletRequest Object is null");  
	    }  
	    String ipString = request.getHeader("x-forwarded-for");  
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getRemoteAddr();  
	    }  
	      
	    // 多个路由时，取第一个非unknown的ip  
	    final String[] arr = ipString.split(",");  
	    for (final String str : arr) {  
	        if (!"unknown".equalsIgnoreCase(str)) {  
	            ipString = str;  
	            break;  
	        }  
	    }  
	      
	    return ipString;  
	}
	


}
