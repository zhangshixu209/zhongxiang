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

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.ControlConstants.RETURN_CODE;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.constant.RedisKey;
import com.chmei.nzbmanage.common.exception.NzbManageException;

public class CsrfFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(CsrfFilter.class);
	
	@Autowired
	private ICacheService cacheService;
	
	private Set<String> refers = null;
	
	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {
		/*加载白名单*/
		String parameter = paramFilterConfig.getInitParameter("refers");
		if(StringUtil.isNotEmpty(parameter)){
			refers = new HashSet<>();
			String[] arr = parameter.split(",");
			for (String str : arr) {
				refers.add(str.trim());
			}
		}
	}

	/**
	 * 过滤方法........
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain paramFilterChain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		 String path = servletRequest.getRequestURI();
		 //String ProjectName = servletRequest.getContextPath();
		 //拦截配置的是/api/* , 这里要将验证码,登录相关的不拦截.
		if(path.matches(".*/api/pb/admin/.*")){
    		// 请求包含pb则放行
			paramFilterChain.doFilter(request, response);
			return;
    	}
		
		Session session = SecurityUtils.getSubject().getSession();   //获取到session信息..........
		String accoundId = session.getAttribute(Constants.SESSION_USER.ID).toString();      //获取用户ID
		String refer = servletRequest.getHeader("Referer");
		
		int resultType = checkFrequency(servletRequest,accoundId);    //判断是否超过最大次数限制
		
		if(Constants.STATE_FREQUENCY_OVER_MAX==resultType) {
			LOG.info("用户=[{}]访问路径=[{}]已到最大次数",accoundId,path);
			errorFrequencyResponse(servletResponse,"请求频次过快，请稍后再试！",String.valueOf(Constants.STATE_FREQUENCY_OVER_MAX));
			return;
		}
		
		// 判断这个头是否为空，或这个头的首地址是否在referer白名单 或者是不在白名单中都进行过滤返回
		if (StringUtil.isEmpty(refer) || !containRefer(refer)) {     
			LOG.info("refer验证失败,refer=[{}]",refer);
			errorResponse(servletResponse,"非法访问");
			return;
		}
		String userIP = "";//ip
		try {
			userIP = getIpAddr(servletRequest);          //获取 当前用户的Ip地址
		} catch (Exception e) {
			LOG.error("获取用户登录ip地址失败:[{}]", e.getMessage());
		}
		session.setAttribute("userIP", userIP);             //将用户的IP放到session中
		paramFilterChain.doFilter(request, response);         //所有的都走完了, 就放行............
	}

	/**
	 * 逻辑：
	 * 1、频次开关frequencySwitch是否开启。不开，直接进下一个 filter
	 * 2、开关开。是否在请求过滤名单中。不在，直接下一filter
	 * 3、在过滤名单中，判断会员缓存计数器是否存在。存在，计数器+1，判是否超，不超继续下一filter.超过，返回前端
	 * 4、会员缓存计数器不存在，创建，并设置计数器为1和有效期，下一filter
	 * @param servletRequest：请求路径
	 * @param accoundId：用户ID
	 * @return  0： 校验通过，666：超过最大次数 。
	 * 1.key :system-config filed:frequencySwitch  value: 0 or1
	 * 2.key :redis_frequency_white_list  field: seat:task:queryList  value:  100:60:10;
	 * 3.redis_agent_request_frequency_"accoundId"_"[0]"(上面的那个100)
	 * 将3的value+1;并重新设置过期时间为[1],并返回最终的次数
	 * 比较[2] 与  更新后的次数.超了提示;没有超过就过去....
	 * 
	 */
	private int  checkFrequency(HttpServletRequest servletRequest, String accoundId) {
		int result=0;//校验通过
		try {
			//1、判断频次校验开关是否开着       从redis中获取的.
			String frequencySwitch = cacheService.hget(RedisKey.REDIS_SYSTEM_CONFIG, RedisKey.REDIS_FREQUENCY_SWITCH);
			//frequencySwitch，0关闭,1开启
			if("1".equals(frequencySwitch)) {         //开启次数限制
				String pathInfo = servletRequest.getServletPath();
				if(StringUtil.isNotEmpty(pathInfo)) {
					String redisTypeUrl = getFrequencyRedisUrl(pathInfo);//
					boolean isInWhiteList = cacheService.hexists(RedisKey.REDIS_FREQUENCY_WHITE_LIST, redisTypeUrl);
					if(isInWhiteList) {		//对该请求需要进行次数的限制
						String urlConfig = cacheService.hget(RedisKey.REDIS_FREQUENCY_WHITE_LIST, redisTypeUrl);
						//过滤名单中配置信息 格式：100:60:10  100代表给url的id，60是有效期，10是最大访问次数。访问100在60s内最多能访问10次
						if(StringUtil.isNotEmpty(urlConfig) && urlConfig.split(":").length==3) {
							String []urlConfigParams=urlConfig.split(":");
							String agentFrequencyKey=RedisKey.REDIS_AGENT_REQUEST_FREQUENCY+"_"+accoundId+"_"+urlConfigParams[0];
							//更新计数器，如果第一次访问，就会返回1.
							int limitNum = Integer.parseInt(urlConfigParams[2]);
							int requestNum = updateUrlFrequencyNum(agentFrequencyKey, urlConfigParams[1]); 
							if(requestNum > limitNum) {
								if ("-1".equals(String.valueOf(cacheService.ttl(agentFrequencyKey)))) { // 检查计数器是否设置有效期
									cacheService.expire(agentFrequencyKey, Integer.valueOf(urlConfigParams[1]));
									LOG.info("reset agentFrequencyKey expire time =[{}]",agentFrequencyKey);
								}
								result = Constants.STATE_FREQUENCY_OVER_MAX;//返回前端
								LOG.info("会员[{}]访问请求[{}],[{}]次,超过频次[{}]了!",accoundId,redisTypeUrl,requestNum,urlConfig);
							}
						}
					}
				}
			}//关闭的直接返回0，进入下一个filter
		} catch (NzbManageException e) {
			//如果走到这里，说明 result还没被设置为1或者2，能正常走到下一个 filter
			LOG.error("校验访问次数失败:[{}]",e.getMessage());
		}
		return result;
	}
	
	//更新计数器   每次操作的时候重新设置过期时间
	private int updateUrlFrequencyNum(String agentFrequencyKey, String seconds) throws NzbManageException {
		int requestNum = cacheService.incr(agentFrequencyKey).intValue(); //计数器+1
		if (requestNum == 1) {
			cacheService.expire(agentFrequencyKey, Integer.valueOf(seconds));
		}
		return requestNum;
	}
	
	/*
	 * 拼装redis形式的路径    OCP的路径.....
	 * /front/agent/homeOutBoundBusiness!getAgentOutboundTaskIndexOrderByRecentlyTime?uid=home00029
	 * 上面url对应redis形式：
	 * agent:homeOutBoundBusiness:getAgentOutboundTaskIndexOrderByRecentlyTime:home00029
	 * /cs/seat/myma/updateMyma cs项目的访问路径;
	 */
	private String getFrequencyRedisUrl(String pathInfo) {
		String redisTypeUrl;//sonar bug 不允许附初始值                         需不需要用?进行切割,获取参数前面的路径
		redisTypeUrl = pathInfo.replaceAll("/", ":");
		return redisTypeUrl;
	}

	@Override
	public void destroy() {
		//

	}

	/**
	 * 像页面返回错误信息
	 * @param response
	 * @param errMsg
	 * @throws IOException
	 */
	private static void errorResponse(HttpServletResponse response,String errMsg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		OutputDTO output = new OutputDTO();
		output.setCode(RETURN_CODE.SYSTEM_ERROR);
		output.setMsg(errMsg);
		out.print(JsonUtil.OutputDTO2Json(output));
		out.flush();
		out.close();
	}
	private static void errorFrequencyResponse(HttpServletResponse response,String errMsg,String errCode) throws IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		OutputDTO output = new OutputDTO();
		output.setCode(errCode);
		output.setMsg(errMsg);
		out.print(JsonUtil.OutputDTO2Json(output));
		out.flush();
		out.close();
	}
	
	//判断访问的url 是否需要校验token      
	@SuppressWarnings("unused")
	private static boolean containToken(String url)
	{
		for (String str : Constants.getUseCheckToken()) {
			if (url.indexOf(str) >= 0) {
				return true;
			}
			}
		
		return false;
	}
    //判断referer是否在白名单  
	private boolean containRefer(String refer) {
		if (StringUtil.isNotEmpty(refer)) {
			for (String str : refers) {
				if (refer.startsWith(str)) {
					return true;
				}
			}
		}
		return false;
	}
	/** 
	 * 获取访问用户的客户端IP（适用于公网与局域网）. 
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
