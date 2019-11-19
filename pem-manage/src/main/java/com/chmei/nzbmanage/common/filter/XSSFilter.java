package com.chmei.nzbmanage.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbcommon.enums.OutPutEnum;
import com.chmei.nzbcommon.util.CheckUtil;

public class XSSFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);
	
	// 上次刷新时间，由于该刷新会直接操作数据库，为防止有人恶意批量访问该URL导致DB压力，故增加访问间隔限制
	protected long lastAccTime = -1;                
	//链接白名单 
    private Set<String> whiteList = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String pathInfo = req.getPathInfo() == null ? "" : req.getPathInfo();
		String url = req.getServletPath() + pathInfo;
		//判断该请求是否包含参数以及是否是白名单，是则放过该请求
		if(checkHasParam(req) || isWhiteURL(url) || checkSqlFlag(url)) {
			logger.info("该URL不作xss校验：url=[{}]",url);
			chain.doFilter(req, response);
			return;
		}
		//转换半角
		req=  new XSSRequestWrapper(req);
    	

		// 获取请求所有参数，校验防止SQL注入，防止XSS漏洞
		Enumeration<String> params = req.getParameterNames();        //获取所有的参数的名称
		while (params.hasMoreElements()) {
			String paramN =  params.nextElement();
			String paramVale = req.getParameter(paramN);
			// 校验是否存在SQL注入信息
			if (CheckUtil.checkSQLInject(paramVale, url)) {        //判断是否有sql注入; 怎么判断的: 对参数判断是否包含sql中的关键字;
				logger.info("请求参数:"+paramN + "|" + paramVale+"请求路径为："+url);
				errorResponse(servletResponse);           //是了就返回错误,并返回前端
				return;
			}

		}
		chain.doFilter(req, response);
	}

	private static void errorResponse(HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		OutputDTO output = new OutputDTO();
		output.setCode(OutPutEnum.XSSError.getCode());
		output.setMsg(OutPutEnum.XSSError.getDesc());
		out.print(JsonUtil.OutputDTO2Json(output));
		out.flush();
		out.close();
	}

	/*private static boolean checkHalf( String action){
		
		for (String str : Constants.getUseReplaceHalfAngle()) {
			if (action.indexOf(str) >= 0) {
				return true;
			}
		}
		
		return false;
	}*/
	
	/**
	 * 校验该请求是否包含参数
	 * @author lianziyu  
	 * @param request
	 * @return
	 */
	private boolean checkHasParam(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Map map = request.getParameterMap();
        return map == null || map.isEmpty();
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
	
	/**
	 * 校验链接地址是否包含去除校验SQL标识
	 * @param url url地址
	 * @return 返回标识
	 */
    private boolean checkSqlFlag(String url) {
        boolean flag =  url.endsWith("/xssFilterSqlFlag");
        if (flag) {
            return true;
        }
        return false;
    }
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		//初始化加载XSS白名单
		//String whiteListStr = config.getInitParameter(Constants.FILTER_PARAM.XSS_WHITE_LIST);  
		//whiteList = strToSet(whiteListStr);
	}
	
	public void initWhiteList(String whiteList) {
		this.whiteList = strToSet(whiteList);
	}
	
	/**
	 * 白名单去空格，转set
	 * @author lianziyu  
	 * @param urlStr
	 * @return
	 */
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
