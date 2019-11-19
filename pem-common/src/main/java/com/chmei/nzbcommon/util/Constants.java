package com.chmei.nzbcommon.util;

/**
 * 常量类
 * @author zhangsx
 * @since 2019年05月13日 15点20分
 * 
 */
public final class Constants {
	
	public static final String MY_REPORT = "MY_REPORT";

	//超管角色Id
    public static final String ROOT_ROLE = "6666666666";
		
    /** 操作页面代码 */
    public interface OPERATE_PAGE_CD {
        String INDEX_CD = "1000" ;//首页
        String MEMBER_MANAGE_CD = "1001"; //会员管理
        String FEED_BACK_CD = "1011"; //反馈意见管理
        String ROTATION_MANAGE_CD = "1002" ;//轮播图管理
        String CASH_AUDIT_CD = "1003" ;//提现审核
        String COMPLAINT_MANAGE_CD = "1004"; //投诉管理
        String RECHARGE_RECORD_CD = "1005"; //充值记录
        String NOTICE_TYPE_CD = "1010" ;//公告分类
        String NOTICE_MANAGE_CD = "1011"; //公告管理
        String MY_NOTICE_CD = "1012"; //我的公告
        String ORGANIZATION_MANAGE_CD = "1013" ;//组织机构管理
        String USER_MANAGE_CD = "1014" ;//用户管理
        String ROLE_MANAGE_CD  ="1015" ;//角色管理
        String PERMISSION_MANAGE_CD = "1016" ;//权限管理
        String PARAMS_CONFIG_CD = "1017" ;//参数配置
        String DATATABLE_MANAGE_CD  ="1018" ; //数据表管理
    }
    
    /** 操作类型代码 */
    public interface OPERATE_TYPE_CD {
        String OPERATE_DOWNLOAD_CD = "1001"; //下载
        String OPERATE_INSERT_CD = "1002"; //新增
        String OPERATE_UPDATE_CD = "1003"; //编辑
        String OPERATE_DELETE_CD = "1004"; //删除
        String OPERATE_BROWSE_CD = "1005"; //浏览
        String OPERATE_LOGIN_CD = "1006"; //登录
        String OPERATE_SUBMIT_CD = "1007"; //提交
        String OPERATE_QUERY_CD = "1008" ;//查询
        String OPERATE_NOTICENAME_CD = "1009" ;//主题(名称)信息查看
        String OPERATE_READDETAIL_CD = "1010" ;//查看详情
        String OPERATE_INSERT_DRAF_CD  ="1011" ;//新增存草稿
        String OPERATE_INSERT_SUBMIT_CD = "1012" ;//新增提交
        String OPERATE_UPDATE_DRAF_CD  ="1013" ;//编辑存草稿
        String OPERATE_UPDATE_SUBMIT_CD = "1014" ;//编辑提交
        String OPERATE_DATAGROUP_SURE_CD = "1015" ;//数据组确认
        String OPERATE_CHECK_REJECT_CD ="1016" ;//验收驳回
        String OPERATE_CHECK_OK_CD ="1017" ;//验收通过
        String OPERATE_CHECK_QUERY_CD = "1018" ;//验收查询
        String OPERATE_OFFLINE_CD = "1019" ;//报表离线
        String OPERATE_RENAME_CD = "1020" ;//重命名
        String OPERATE_TOMYREPORT_CD = "1021" ;//移到我的报表
        String OPERATE_PUBLISH_CD ="1022" ;//发布上线
        String OPERATE_CREAT_REPORT_CD = "1023" ;//创建报表
        String OPERATE_REMOVE_REPORT_CD  ="1024" ;//移除报表
        String OPERATE_HIGH_COLLECT_CD = "1025" ;//高级汇总
        String OPERATE_MYREPORT_QUERY_CD ="1026" ;//详情查询
        String OPERATE_QUERYREADDETAIL_CD ="1027" ;//查看已阅人信息
        String OPERATE_QUERYUNREADDETAIL_CD ="1028" ;//查看未阅读人信息
        String OPERATE_REBACK_CD ="1029" ;//撤回
        String OPERATE_DISABLE_CD = "1030" ;//禁用
        String OPERATE_ABLE_CD  ="1031" ;//启用
        String OPERATE_RESET_PASSWORDS_CD = "1032" ;//重置密码
        String OPERATE_CONFIG_ROLE_CD  ="1033" ;//配置角色
        String OPERATE_CONFIG_PERMISSION_CD = "1034" ;//配置权限
        String OPERATE_RELEVANCE_USER_CD = "1035" ;//关联用户
        String OPERATE_CONFIG_REPORT_CD = "1036" ;//配置报表
        String OPERATE_SUB_TEST_CD = "1037" ;//提交测试
        String OPERATE_PUBLISH_ONLINE_CD = "1038" ;//发布上线
        String OPERATE_DOWN_TEMPLATE = "1039" ;//下载模板
        String OPERATE_DATA_OFFLINE = "1049" ;//数据表下线
        String OPERATE_DATA_SOURCE = "1050" ;//远程查询数据库
        String OPERATE_DATA_IMPORT = "1051" ;//数据表导入数据
        String RECHARGE_TYPE_CD = "1052"; // 充值
    }
    
    /** 响应状态 */
    public interface ANSWER_STATE {
        String SUCCESS = "00"; //成功
        String FAIL = "02"; //失败
    }
}
