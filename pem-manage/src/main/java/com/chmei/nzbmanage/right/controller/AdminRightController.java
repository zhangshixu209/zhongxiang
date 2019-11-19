
package com.chmei.nzbmanage.right.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbcommon.enums.PrivType;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.constant.RedisKey;
import com.chmei.nzbmanage.common.controller.BaseController;

/**  
 * 管理员权限控制类
 * Date:     2018年8月16日 下午2:38:48 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
@RestController
@RequestMapping("/api/adminRight")
public class AdminRightController extends BaseController {
	/**
	  * 查询管理员拥有的权限结构树
	  * @author lianziyu  
	  * @return
	  */
	@RequestMapping("/queryMenuList")
    public OutputDTO queryMenuList(){
    	OutputDTO outputDTO = new OutputDTO();
    	String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
    	//从reids缓存中获取该用户所拥有的所有权限的并集
    	List<Map<String, Object>> menuList = getTheMenuListFromRoleRightList(roleIds);
    	if(null==menuList) {
    		outputDTO.setCode("0");
    		outputDTO.setItems(null);
    		return outputDTO;
    	}
    	//对菜单进行排序
    	sortMenuList(menuList);
    	//获取根节点菜单
		Map<String, Object> rootRight = findOneLevelMenu(menuList);
		if(null!=rootRight) {
			//整理菜单
			menuList =  getChildMenuList(menuList, rootRight); 
		}
    	outputDTO.setCode("0");
		outputDTO.setItems(menuList);
		return outputDTO;
    }
	
	
	/**
	 * 按钮权限控制
	 */
	@RequestMapping("/btnAuthorize")
	@SuppressWarnings("unchecked")
	public OutputDTO btnAuthorize(String btns) {
		btns = btns.replaceAll("&quot;", "\"");
		OutputDTO output = new OutputDTO();
		String cdName = "btnCd";
		if(StringUtil.isNotEmpty(btns)) {
			output.setCode("0");
			List<Map<String, Object>> btnList = JsonUtil.convertJson2Object(btns, List.class);
			String roleIds = (String) getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
			if(btnList != null) {
				//btnCd：1：有权限，0：无权限
				if(roleIds.contains(Constants.ROOT_ROLE) || !cacheService.isExist(RedisKey.REDIS_SYS_RIGHTS_HASH)) {
					for(Map<String, Object> btn : btnList) {
						btn.put(cdName, "1");
					}
				} else {
					for(Map<String, Object> btn : btnList) {
						String btnCd = (String) btn.get(cdName);
						if(cacheService.hget(RedisKey.REDIS_SYS_RIGHTS_HASH, btnCd) != null) {
							btn.put(cdName, checkRolesHasRight(roleIds, btnCd)?"1":"0");
						} else {
							btn.put(cdName, "1");
						}
						btn.put("mo", btnCd);
					}
				}
				output.setItems(btnList);
			} 
		}
		return output;
	}
	
	//用户的角色中是否有包含该权限，包含返回该权限，不包含，返回null
	@SuppressWarnings("unchecked")
	private boolean checkRolesHasRight(String roleIds,String key) {
		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			String roleRightStr = cacheService.hget(RedisKey.REDIS_SYS_ROLE_RIGHT_HASH, roleId);
			if(StringUtil.isEmpty(roleRightStr)) {
				return false;
			}
			Map<String, Object> roleRight = JsonUtil.convertJson2Object(roleRightStr, Map.class);
			if(roleRight.containsKey(key)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 对菜单进行排序
	 * @author lianziyu  
	 * @param menuList
	 */
	private void sortMenuList(List<Map<String,Object>> menuList){
		Collections.sort(menuList, new Comparator<Map<String, Object>>(){  
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {  
            	Integer orderLevel1 =(Integer)o1.get("orderLevel");//name1是从你list里面拿出来的一个  
            	Integer orderLevel2= (Integer)o2.get("orderLevel"); //name1是从你list里面拿出来的第二个name      
	             return orderLevel1.compareTo(orderLevel2);    
            }
		});
	}
	
	/**
	 * 从角色权限里筛选出菜单
	 * @author lianziyu  
	 * @param rightList
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	private List<Map<String, Object>> getTheMenuListFromRoleRightList(String roleIds) {
		//校验所有权限是否存在
		cacheService.del(RedisKey.REDIS_SYS_RIGHTS_HASH);
		if(!cacheService.isExist(RedisKey.REDIS_SYS_RIGHTS_HASH)) {
			//不存在，重新加载所有权限
			initAllRight();
		}
		//校验是否有角色权限存在
		cacheService.del(RedisKey.REDIS_SYS_ROLE_RIGHT_HASH);
		if(!cacheService.isExist(RedisKey.REDIS_SYS_ROLE_RIGHT_HASH)) {
			//不存在，重新加载所有角色权限
			initAllRoleRight();
		}
		Map<String, String> allRolesRight = new HashMap();
		List<Map<String, Object>> menuList = new ArrayList<>();
		String[] roleIdArray = roleIds.split(",");
		if(roleIds.contains(Constants.ROOT_ROLE)) {//超管角色
			Map<String, String> allRightMap = cacheService.getMap(RedisKey.REDIS_SYS_RIGHTS_HASH);
			//包含超管角色，则拥有所有菜单
			allRolesRight.putAll(allRightMap);
		}else {
			for (String roleId : roleIdArray) {
				//从redis缓存中获取该角色拥有的权限
				if(StringUtil.isNotEmpty(roleId)) {
					String jsonRoleRightStr = cacheService.hget(RedisKey.REDIS_SYS_ROLE_RIGHT_HASH, roleId);
					Map<String, String> roleRightMap =  JsonUtil.convertJson2Object(jsonRoleRightStr, Map.class);
					allRolesRight.putAll(roleRightMap);
				}
			}
		}
		for (String key : allRolesRight.keySet()) { 
			Map<String, Object> roleRight = JsonUtil.convertJson2Object((String)allRolesRight.get(key), Map.class);
			if(PrivType.MENU.getCode().equals(roleRight.get("typeCd"))) {
				menuList.add(roleRight);
			}
		} 
		return menuList;
	}
	
	//整理菜单列表
	private List<Map<String, Object>> getChildMenuList(List<Map<String, Object>> allRightList, Map<String, Object> parent){
		Iterator<Map<String, Object>> it = allRightList.iterator();
		List<Map<String, Object>> childList = new ArrayList<>();
		String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
		while (it.hasNext()) {
			Map<String, Object> right = it.next();
			String parentId = roleIds.contains(Constants.ROOT_ROLE)?
					String.valueOf(parent.get("id")):String.valueOf(parent.get("rightId"));
			if(parentId.equals(right.get("parentId"))) {
				List<Map<String, Object>> c_childList = getChildMenuList(allRightList, right);
				if(c_childList.size()>0&&c_childList.get(0).size()>0) {
					right.put("child", c_childList);
				}
				childList.add(right);
		   }
		}
      return childList;  
	}
	
	/**
	 * 获取根节点
	 * @author lianziyu  
	 * @param menuList
	 * @return
	 */
	private Map<String, Object>findOneLevelMenu(List<Map<String, Object>> menuList) {
		for (Map<String, Object> map : menuList) {
			if("0".equals(map.get("parentId"))) {
				return map;
			}
		}
		return null;
	}
	
	private void initAllRight() {
		InputDTO input = new InputDTO();
		Map<String, Object> params = new HashMap<>();
		input.setParams(params);
		logger.info("初始化加载系统权限");
		getOutputDTO(params, "rightManageService", "queryInitAllRight");
	} 
	
	private void initAllRoleRight() {
		InputDTO input = new InputDTO();
		Map<String, Object> params = new HashMap<>();
//		params.put("sysTypeCd", AppType.Manage.getCode().toString());
		input.setParams(params);
		logger.info("初始化加载系统权限");
		getOutputDTO(params, "rightManageService", "queryInitAllRoleRight");
	} 
}
  
