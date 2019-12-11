package com.chmei.nzbdata.util;

/**
 * 常量类
 */
public final class Constants {

    //超管角色Id
  	public static final String ROOT_ROLE = "6323525648384";
  	
  	 //会员角色Id
  	public static final String MEMBER_ROLE = "15030984638464";
  	
  	//我的报表 数量key
  	public static final String MY_REPORT = "MY_REPORT";
  	
  	// 用户默认密码
    public static final String DEFAULT_USER_PWD = "123456";
    
    // 数据库字段类型number
    public static final String COL_TYPE_NUMBER = "number";
    
    // 数据库字段类型decimal
    public static final String COL_TYPE_DECIMAL = "decimal";
    
    // 数据库字段类型date
    public static final String COL_TYPE_DATE = "date";
    //模块id  数据上传模块
    public static final String DATATABLE_MODEL_ID = "1";
    //数据上传模块默认公开权限9999
    public static final String DATATABLE_PERM = "9999";
    
    // 字段长度
    public static final int COL_LENGTH = 4;

  	public interface PredictType {
  		String PREDICT_MONITOR = "1"; // 预测参数
		String PREDICT_MEMBER = "2"; // 会员
		String PREDICT_CONDITIONS = "3"; // 业务
		String PREDICT_SITUATION = "4"; // 整体
	}
  	
  	/**
  	 * 
  	 * 工单状态常量
  	 */
  	public interface OrderStatus {
        String COMMITED = "1001"; // 已提交状态
    }

	/**
	 * 群升级标准
	 */
	public static final Integer MAXUSERS_ONE = 500;
	public static final Integer MAXUSERS_TWO = 1000;
	public static final Integer MAXUSERS_THREE = 1500;
	public static final Integer MAXUSERS_FOUR = 2000;

	/**
	 * 广告分红涨幅标准
	 */
	public static final Double limit1 = 1000D;
	public static final Double limit2 = 2000D;
	public static final Double limit3 = 5000D;
	public static final Double limit5 = 10000D;

	public static final double MONEY = 100D; // 默认额度1000

	/**
	 * 广告分红任务表,任务标志 D: 已经完成分红任务 S:开始分红任务还没有结束 N:没有分红任务
	 */
	public static final String D = "D";
	public static final String S = "S";
	public static final String N = "N";

	public static final double TRANSFER_MONEY = 100; // 转让固定金额100

	/** 推送消息类型 */
	public static final String MESSAGE_TYPE_1001 = "1001"; // 广告分红
	public static final String MESSAGE_TYPE_1002 = "1002"; // 众享公告
	public static final String MESSAGE_TYPE_1003 = "1003"; // 众享好友
	public static final String MESSAGE_TYPE_1004 = "1004"; // 投诉处理
	public static final String MESSAGE_TYPE_1005 = "1005"; // 提现审核
	public static final String MESSAGE_TYPE_1006 = "1006"; // 反馈回复

}
