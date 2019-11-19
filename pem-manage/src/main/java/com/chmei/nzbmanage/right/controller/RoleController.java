package com.chmei.nzbmanage.right.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.right.bean.RoleAddForm;
import com.chmei.nzbmanage.right.bean.RoleEditFrom;
import com.chmei.nzbmanage.right.bean.RoleQueryForm;
import com.chmei.nzbmanage.right.bean.RoleRightEditForm;
import com.chmei.nzbmanage.right.bean.RoleRightQueryForm;
import com.chmei.nzbmanage.right.bean.RoleUserEditForm;
import com.chmei.nzbmanage.right.bean.RoleUserForm;
import com.chmei.nzbmanage.right.bean.RoleUserQueryForm;

/**  
 * 角色管理
 * Date:     2018年8月9日 下午4:04:15 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
@RestController
@Validated
@RequestMapping("/api/sys/role")
public class RoleController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 * 分页查询所有角色，带有用户是否具有该角色
	 * <p>李新杰
	 */
	 @RequestMapping("/queryRolesForUser")
	 public OutputDTO queryRolesForUser(RoleQueryForm roleQF) {
		 Map<String, Object> params = BeanUtil.convertBean2Map(roleQF);
		 OutputDTO outputDTO = getOutputDTO(params,"roleService","queryRolesForUser");
		 return outputDTO;
	 }
	 
	/**
	 * 添加一条记录，为用户分配角色
	 * <p>李新杰
	 */
	 @RequestMapping("/addRoleUserForUser")
	 public OutputDTO addRoleUserForUser(RoleUserForm roleUserF) {
		 Map<String, Object> params = BeanUtil.convertBean2Map(roleUserF);
		 params.put("crtUserId", getCurrUserId());
		 params.put("crtTime", new Date());
		 OutputDTO outputDTO = getOutputDTO(params,"roleUserService","addRoleUserForUser");
		 return outputDTO;
	 }
	 
	/**
	 * 删除一条记录，为用户取消角色
	 * <p>李新杰
	 */
	 @RequestMapping("/removeRoleUserForUser")
	 public OutputDTO removeRoleUserForUser(RoleUserForm roleUserF) {
	     logger.info("WorkOrderManageController.removeRoleUserForUser删除数据:" + roleUserF.getId());
		 Map<String, Object> params = BeanUtil.convertBean2Map(roleUserF);
		 OutputDTO outputDTO = getOutputDTO(params,"roleUserService","removeRoleUserForUser");
		 return outputDTO;
	 }
	 
	/**
	  * 分页查询角色
	  * @author lianziyu  
	  * @return
	  */
	 @RequestMapping("/queryRoleList")
	 public OutputDTO queryRoleList(@ModelAttribute RoleQueryForm roleQueryForm){
		Map<String,Object> params = BeanUtil.convertBean2Map(roleQueryForm);
        OutputDTO outputDTO = getOutputDTO(params,"roleService","queryRoleList");
        List<Map<String, Object>> roleList = outputDTO.getItems();
        String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
        if(roleIds.contains(Constants.ROOT_ROLE)) {
        	//是超管
        	for (Map<String, Object> role : roleList) {
        		role.put("isinner", "1");
			}
        	outputDTO.setItems(roleList);
        }
        return outputDTO;
	 }
	 
	 /**
	  * 根据Id查看角色详情(编辑)
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/queryRoleDetailByEdit")
	 public OutputDTO queryRoleDetailByEdit(String id) {
		 if(!checkHasOperRootPower(id)) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"roleService","queryRoleDetail");
		 return outputDTO;
	 }
	 
	 /**
	  * 根据Id查看角色详情（查看）
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/queryRoleDetailByView")
	 public OutputDTO queryRoleDetailByView(String id) {
		 if(!checkHasOperRootPower(id)) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"roleService","queryRoleDetail");
		 return outputDTO;
	 }
	 /**
	  * 角色添加
	  * @author lianziyu  
	  * @param roleAddForm
	  * @return
	  */
	 @RequestMapping("/addRole")
	 public OutputDTO addRole(@ModelAttribute RoleAddForm roleAddForm){
		 roleAddForm.setIsinner("1");
		 roleAddForm.setValidStsCd("1");
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleAddForm);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 return getOutputDTOPlatform(params, "roleService", "saveRole");
	 } 
	 /**
	  * 修改角色
	  * @author lianziyu  
	  * @param roleAddForm
	  * @return
	  */
	 @RequestMapping("/editRole")
	 public OutputDTO editRole(@ModelAttribute RoleEditFrom roleEditFrom){
		 if(!checkHasOperRootPower(roleEditFrom.getId())) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleEditFrom);
		 Session session = getSession();
		 params.put("modfUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 return getOutputDTOPlatform(params, "roleService", "updateRole");
	 }
	 
	 /**
	  * 删除角色
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/deleteRole")
	 public OutputDTO deleteRole(String ids){
		 if(ids.contains(Constants.ROOT_ROLE)) {
			 String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
	         if(!roleIds.contains(Constants.ROOT_ROLE)) {
	        	 return new OutputDTO("-1", "没有操作超级管理员权限");
	         }
		 }
		 Map<String,Object> map = new HashMap<>();
		 map.put("ids", ids.split(","));
		 map.put("modfUserId", getSession().getAttribute(Constants.SESSION_USER.ID));
		 return getOutputDTOPlatform(map, "roleService", "deleteRoleBatch");
	 } 
	 
	 /**
	  * 查询角色权限树，包含所有权限，该角色拥有的权限hasState为1
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/queryRoleRights")
	 public OutputDTO queryRoleRights(String roleId) {
		 if(!checkHasOperRootPower(roleId)) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String,Object> map = new HashMap<>();
		 map.put("roleId", roleId);
		 return null;
	 }
	 
	 /**
	  * 修改角色权限
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/updateRoleRights")
	 public OutputDTO updateRoleRights(@ModelAttribute RoleRightEditForm roleUserUpdateForm) {
		 if(!checkHasOperRootPower(roleUserUpdateForm.getRoleId())) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleUserUpdateForm);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 logger.info("修改角色权限,角色Id=[{}],权限Ids=[{}],操作人Id=[{}]",params.get("roleId"),
				 roleUserUpdateForm.getRightIds(),params.get("crtUserId"));
		 //rightIds为树形权限所有选中的权限的id的数组
		 params.put("rightIds", roleUserUpdateForm.getRightIds().split(","));
		 //角色对应报表分类
//		 params.put("reportTypeId", roleUserUpdateForm.getReportTypeId().split(","));
		 return getOutputDTOPlatform(params, "roleRightService", "updateRoleRight");
	 }
	 
	 /**
	  * 查询管理端角色用户
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/queryRoleAdmins")
	 public OutputDTO queryRoleAdmins(@ModelAttribute RoleUserQueryForm roleUserQueryForm) {
		//获取角色用户列表
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleUserQueryForm);
		 OutputDTO outPut = getOutputDTO(params,"roleUserService","queryRoleUserList");
		 return outPut;
	 }
	 
	 /**
	  * 查询发包商角色用户
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/queryRoleErUsers")
	 public OutputDTO queryRoleErUsers(@ModelAttribute RoleUserQueryForm roleUserQueryForm) {
		//获取角色用户列表
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleUserQueryForm);
		 OutputDTO outPut = getOutputDTO(params,"roleUserService","queryRoleErUserList");
		 return outPut;
	 }
	 
	 /**
	  * 修改角色用户（管理端）
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/updateRoleAdmins")
	 public OutputDTO updateRoleAdmins(@ModelAttribute RoleUserEditForm roleUserEditForm) {
		 if(!checkHasOperRootPower(roleUserEditForm.getRoleId())) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleUserEditForm);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 //该数组是与原有用户相比多出的新增关联用户Id
		 params.put("addUserIds", roleUserEditForm.getAddUserIds().split(","));
		 //该数组是与原有用户相比去除的新增关联用户Id
		 params.put("removeUserIds", roleUserEditForm.getRemoveUserIds().split(","));
		 logger.info("修改角色用户,角色Id=[{}],系统Id=[{}],新增管理员Ids=[{}],删除管理员Ids=[{}],操作人Id=[{}]",
				 params.get("roleId"),params.get("sysTypeCd"),roleUserEditForm.getAddUserIds(),
				 roleUserEditForm.getRemoveUserIds(),params.get("crtUserId"));
		 return getOutputDTOPlatform(params, "roleUserService", "updateRoleUserByRole");
	 }
	 
	 /**
	  * 修改角色用户(发包方)
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/updateRoleErAdmins")
	 public OutputDTO updateRoleErAdmins(@ModelAttribute RoleUserEditForm roleUserEditForm) {
		 if(!checkHasOperRootPower(roleUserEditForm.getRoleId())) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleUserEditForm);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 logger.info("修改角色用户,角色Id=[{}],系统Id=[{}],新增管理员Ids=[{}],删除管理员Ids=[{}],操作人Id=[{}]",
				 params.get("roleId"),params.get("sysTypeCd"),roleUserEditForm.getAddUserIds(),
				 roleUserEditForm.getRemoveUserIds(),params.get("crtUserId"));
		 //该数组是与原有用户相比多出的新增关联用户Id
		 params.put("addUserIds", roleUserEditForm.getAddUserIds().split(","));
		 //该数组是与原有用户相比去除的新增关联用户Id
		 params.put("removeUserIds", roleUserEditForm.getRemoveUserIds().split(","));
		 return getOutputDTOPlatform(params, "roleUserService", "updateRoleUserByRole");
	 }
	 
	 /**
	  * 查询权限结构树
	  * 不传入roleId获取的为所有权限
	  * 传入roleId则可获取所有权限，该角色拥有的权限hasState为1
	  * @author lianziyu  
	  * @return
	  */
	 @RequestMapping("/queryRightListByRole")
     public OutputDTO queryRightListByRole(@ModelAttribute RoleRightQueryForm roleRightQueryForm ){
		 if(!checkHasOperRootPower(roleRightQueryForm.getRoleId())) {
			 return new OutputDTO("-1","没有操作超级管理员权限");
		 }
         Map<String,Object> params = BeanUtil.convertBean2Map(roleRightQueryForm);
         OutputDTO outputDTO = getOutputDTO(params,"rightService","queryRightTree");
         return outputDTO;
     }
	 /**
	  * 判断是否具有操作超管权限
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 private boolean checkHasOperRootPower(String roleId) {
		 boolean result = true;
		 if(Constants.ROOT_ROLE.equals(roleId)) {
			 String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
	         if(!roleIds.contains(Constants.ROOT_ROLE)) {
	        	 logger.info("不是超级管理员，无法操作超管角色。操作人=[{}]",getSession().getAttribute(Constants.SESSION_USER.ID));
	        	 result = false;
	         }
		 }
		 return result;
	 }
	 
	 /**
      * 查询部门结构树
      * 不传入roleId获取的为所有权限
      * 传入roleId则可获取所有权限，该角色拥有的权限hasState为1
      * @author lianziyu  
      * @return
      */
     @RequestMapping("/queryUserDepartment")
     public OutputDTO queryUserDepartment(@ModelAttribute RoleRightQueryForm roleRightQueryForm ){
         if(!checkHasOperRootPower(roleRightQueryForm.getRoleId())) {
             return new OutputDTO("-1","没有操作超级管理员权限");
         }
         Map<String,Object> params = BeanUtil.convertBean2Map(roleRightQueryForm);
         OutputDTO outputDTO = getOutputDTO(params,"rightService","queryUserDepartment");
         return outputDTO;
     }
	 
	 
	 
	 
	 
}