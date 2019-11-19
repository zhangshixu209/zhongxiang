package com.chmei.nzbcommon.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class WebUtils {

	    /**
	     * 判断请求是否是ajax请求
	     * @param request
	     * @return
	     */
	    public static boolean isAjax(HttpServletRequest request){
	        Enumeration<String> headerNames = request.getHeaderNames();
	        String accept = request.getHeader("accept");
	        if(accept != null && accept.indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)){
	            return true;
	        }
	        return false;
	    }
	    
	    
	    /**  
	     * 获得ClassPath路径
	     * @author Lvcrosstime  
	     * @return  
	     */
	    public static String getClassPath(){
			
			URL url = Thread.currentThread().getContextClassLoader().getResource("");		
			String classpath = 	url.getPath();
			return classpath;	
			
		}
	public static Map<String,Object> getParams(HttpServletRequest request){
	    Map<String, String[]> temp = request.getParameterMap();
	    Map<String,Object> params = new HashMap<>();
	    temp.forEach((key, value)->{
	        String newValue = null;
	        if(value != null && value.length > 0) {
	            newValue = StringUtils.join(value);
	        }
	        params.put(key, newValue);
	    });
	    return params;
	}
}
