package com.chmei.nzbmanage.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.chmei.nzbcommon.util.CheckUtil;
import com.chmei.nzbmanage.common.constant.Constants;


public class XSSRequestWrapper extends HttpServletRequestWrapper {

	public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    
	
	@Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        if(Constants.getPassParamsName().contains(parameter)){
        	return value;
        }
         value=CheckUtil.xssEncode(value);
        // value = BCConvertUtils.bj2qj(value);
        return value;		
    }
}
