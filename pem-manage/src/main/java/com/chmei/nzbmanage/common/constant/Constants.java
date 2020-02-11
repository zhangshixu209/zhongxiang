package com.chmei.nzbmanage.common.constant;

import java.util.Arrays;
import java.util.List;

public final class Constants {
	
	 /**管理端所有权限reids key*/
//    public static final String REDIS_SYS_MANAGE_BUTTON_HASH = "report:manage:button"; 
	
	public interface SESSION_USER {
		String ID = "userinfo_id";
		String MOBILE = "userinfo_mobile";
		String NICKNAME = "userinfo_nick_name";
		String MENU_LIST = "user_menu_list";
		String ROLE_IDS = "user_role_ids";
		String USERNAME = "user_name"; // 平台管理端用户名
		String IP_ADDR = "ip_addr"; // 登录用户当前IP
		String ROLE_NAMES = "user_role_names";//用户角色
		String BUTTON_PERMIS_NAMES = "button_permis";//按钮权限标识
		String USER_DEPARTMENT_ID = "user_department_id";//部门id
	}
	
	//权限拦截参数
	public interface FILTER_PARAM{
		//html页面后缀
		String HTML_EXT = "html";
		//默认没有配置权限是否允许访问
		boolean DEFAUT_ACCESS = false;
		//0表示校验链接，1表示不校验
		String CHK_URL = "0";
		//登陆页面地址
		String LOGIN_URL = "LOGIN_URL";
		//没有权限地址
		String NO_RIGHT_URL = "NO_RIGHT_URL";
		//web.xml配置的置登录白名单参数名
		String LOGIN_WHITE_LIST = "loginWhiteList";
		//web.xml配置的置权限白名单参数名
		String RIGHT_WHITE_LIST = "rightWhiteList";
		//web.xml配置的置XSS白名单参数名
		String XSS_WHITE_LIST = "xssWhiteList";
		//web.xml置后缀白名单参数名
		String WHITE_POST_FIX = "whitePostFix";
	}
	
	//超管角色Id
	public static final String ROOT_ROLE = "6666666666";
	
	//------------------------------filter中使用到的------------------
	//半角转全角url
	//有的文本输入框需要输入半角字符，不能直接拒绝，需要将半角替换为全角 英文符号转成中文
	private static final String[] USE_REPLACE_HALF_ANGLE={"",
	};
			
	public static String[] getUseReplaceHalfAngle() {
		return USE_REPLACE_HALF_ANGLE;
	}
	//csrfFilter中访问次数限制的值
	 public static final int STATE_FREQUENCY_OVER_MAX= -9998; 	// 会员端请求频次好过超过最大频次
	 
	// 不需要做csrf post校验的url              
	private static final  String[] NO_CHECK_POST ={""
	};
	
	public static String[] getNoCheckPost() {
		return NO_CHECK_POST;
	}
	
	//需要做token校验的url -----------------------------路径需要改------ 
	private static final  String[] USE_CHECK_TOKEN ={
		""
		};
	
	public static String[] getUseCheckToken() {
		return USE_CHECK_TOKEN;
	}
	
	//不进行全角转换的参数名称
	private static final String[] PASS_PARAMS_NAME = {
		"password","loginPw","loginPw","againpassword"
	};
	public static List<String> getPassParamsName(){
		return Arrays.asList(PASS_PARAMS_NAME);
	}
	
	/**
	  * 默认上传文件类
	 */
	public static final String DEFAULT_STORAGE_CLASS = "com.chmei.nzbmanage.common.upload.FileSystemStorage";

	public static final String USER_NAME = "root"; // 数据库账号
	public static final String USER_PWD = "2wsx@WSX"; // 数据库密码
	public static final String DB_HOST = "localhost"; // 数据库地址
	public static final String DB_NAME = "pem_manage_db"; // 指定数据库名称
}
