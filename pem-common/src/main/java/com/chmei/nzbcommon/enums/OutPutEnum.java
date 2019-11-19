package com.chmei.nzbcommon.enums;  
/**  
 * Date:     2018年9月3日 下午4:26:32 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public enum OutPutEnum {
	Success("0", "成功"),
	ServerError("-1", "服务器程序错误"),
	NotRight("-2", "没有访问权限,请联系管理员添加角色"),
	NotLogin("-3", "没有登录"),
	XSSError("-4", "请求参数包含非法字符");
	
	private String code;
	private String desc;
	
	private OutPutEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
  
