package com.chmei.nzbservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志匹配器
 * <p>Title: OperLogMatcher</p>  
 * <p>Description: </p>  
 * @author gaoxuemin  
 * @date 2018年9月6日
 */
public final class OperLogMatcher {

	public final static Map<String, Map<String, String>> logMatcher = new LinkedHashMap<String, Map<String, String>>();
	
	public final static List<String> operationModelList = new ArrayList<>();
	
	static {
		/** 平台管理端  */
		// 登录
		logMatcher.put("operationLogService_loginLog_" + ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端登录", OperationActionEnum.select));
		logMatcher.put("operationLogService_logoutLog_" + ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端登出", OperationActionEnum.select));
		//字典相关
		logMatcher.put("dictService_addDictClassify_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端新增字典类型", OperationActionEnum.insert));
		logMatcher.put("dictService_updateDictClassifyInfo_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端编辑字典类型", OperationActionEnum.update));
		logMatcher.put("dictService_updateDictClassifyState_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改数据字典分类状态", OperationActionEnum.update));
		logMatcher.put("dictService_addDictData_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端添加字典数据", OperationActionEnum.insert));
		logMatcher.put("dictService_updateDictData_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改字典数据信息", OperationActionEnum.update));
		logMatcher.put("dictService_updateDictState_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改字典数据状态", OperationActionEnum.update));
		
		//权限相关
		logMatcher.put("tURightService_save_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端新增权限", OperationActionEnum.insert));
		logMatcher.put("tURightService_update_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改权限", OperationActionEnum.update));
		logMatcher.put("tURightService_delete_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端删除权限", OperationActionEnum.delete));
		logMatcher.put("tURoleService_save_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端新增角色", OperationActionEnum.insert));
		logMatcher.put("tURoleService_update_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改角色", OperationActionEnum.update));
		logMatcher.put("tURoleService_deleteBatch_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端删除角色", OperationActionEnum.delete));
		//系统管理员相关
		logMatcher.put("tUAdminService_save_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端新增系统管理员", OperationActionEnum.insert));
		logMatcher.put("tUAdminService_update_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改系统管理员", OperationActionEnum.update));
		//角色权限相关
		logMatcher.put("tURoleRightService_update_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改角色权限", OperationActionEnum.update));
		//角色用户相关
		logMatcher.put("tURoleUserService_updateRoleUserByRole_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改角色拥有的用户", OperationActionEnum.update));
		logMatcher.put("tURoleUserService_updateRoleUserByUser_"+ItemBelongEnum.platform_manage.getItemBelong(), setLogConfig("平台管理端修改用户拥有的角色", OperationActionEnum.update));
		// 添加至操作日志对象集合
		for(Map<String, String> operModel : OperLogMatcher.logMatcher.values()) {
			operationModelList.add(operModel.get("operationModel"));
		}
	}
	
	private static Map<String, String> setLogConfig(String operationModel, OperationActionEnum operationAction){
		Map<String, String> map = new HashMap<>();
		map.put("operationModel", operationModel);
		map.put("operationAction", operationAction.getOperationAction());
		return map;
	}
	
	/**
	 * 日志所属平台
	 * <p>Title: ItemBelongEnum</p>  
	 * <p>Description: </p>  
	 * @author gaoxuemin  
	 * @date 2018年9月5日
	 */
	public enum ItemBelongEnum {
		platform_manage(1), // 平台管理端
		seat(2), // 坐席端
		er(3), // 发包商
		sp(4); // 接包商
		
		private final int itemBelong;
		private ItemBelongEnum(int itemBelong) {
			this.itemBelong = itemBelong;
		}
		
		public int getItemBelong() {
			return itemBelong;
		}
	}
	
	/**
	 * 操作方式(目前只有通用的增删改查)
	 * <p>Title: operationActionEnum</p>  
	 * <p>Description: </p>  
	 * @author gaoxuemin  
	 * @date 2018年9月5日
	 */
	public enum OperationActionEnum {
		select("查询"), // 
		insert("新增"), // 
		update("修改"), // 
		delete("删除"); // 
		
		private final String operationAction;
		private OperationActionEnum(String operationAction) {
			this.operationAction = operationAction;
		}
		
		public String getOperationAction() {
			return operationAction;
		}
	}
	
}
