package com.chmei.nzbmanage.right.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.cmutil.ControlConstants.RETURN_CODE;
import com.chmei.nzbcommon.util.MD5Util;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbcommon.util.excel.ExcelReadUtils;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
//import com.chmei.nzbmanage.reportmanage.bean.ReportConfigForm;
import com.chmei.nzbmanage.right.bean.*;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * 管理员控制管理类
 * Date:     2018年8月13日 上午11:13:27 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
@RestController
@Validated
@RequestMapping("/api/sys/admin")
public class AdminController  extends BaseController{
	
	/**
	 * log
	 */
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/**
	  * 查询管理端管理员
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/queryAdmins")
	 public OutputDTO queryAdmins(@ModelAttribute AdminQueryForm adminQueryForm) {
		 Map<String,Object> params = BeanUtil.convertBean2Map(adminQueryForm);
		 params.put("roleId", Constants.ROOT_ROLE);
	     OutputDTO outputDTO = getOutputDTO(params,"adminService","queryAdminList");
		 List<Map<String, Object>> adminList = outputDTO.getItems();
		 String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
		 if(roleIds.contains(Constants.ROOT_ROLE)) {
        	 //是超管
			 for (Map<String, Object> admin : adminList) {
				 admin.put("isRoot", "0");
			 }
	         outputDTO.setItems(adminList);
         }
	     return outputDTO;
	 }
	 
	 /**
	  * 管理端管理员添加
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/addAdmin")
	 public OutputDTO addAdmin(@ModelAttribute AdminAddForm adminAddForm) {
		 Map<String,Object> params = BeanUtil.convertBean2Map(adminAddForm);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 // 校验手机号是否注册
		 if(isPhoneReg(adminAddForm.getLoginId(),"")){
			 return new OutputDTO("-1", "该工号已存在");
		 }
		 // 添加帐号
		 params.put("loginPw", MD5Util.generate(params.get("loginPw").toString()));
	     OutputDTO outputDTO = getOutputDTOPlatform(params,"adminService","saveAdmin");
	     return outputDTO;
	 }
	 
	 /**
	  * 管理端管理员编辑
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/updateAdmin")
	 public OutputDTO updateAdmin(@ModelAttribute AdminUpdateForm adminUpdateForm) {
		 Map<String,Object> params = BeanUtil.convertBean2Map(adminUpdateForm);
		 Session session = getSession();
		 params.put("modfUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 // 校验该工号已存在是否注册
		 if(isPhoneReg(adminUpdateForm.getLoginId(),adminUpdateForm.getId())){
			return new OutputDTO("-1", "该工号已存在");
		 }
		 // 添加帐号
		 if(StringUtil.isNotEmpty(params.get("loginPw").toString())) {
			 params.put("loginPw", MD5Util.generate(params.get("loginPw").toString()));
		 }else {
			 params.put("loginPw",null);
		 }
	     OutputDTO outputDTO = getOutputDTOPlatform(params,"adminService","updateAdmin");
	     return outputDTO;
	 }
	 
	 
	 /**
	  * 根据id删除用户
	  * @author 翟超锋  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delAdminDetail")
	 public OutputDTO delAdminDetail(String id) {
	     logger.info("AdminController.delAdminDetail删除数据:" + id);
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"adminService","delAdminDetail");
		 return outputDTO;
	 }
	 
	 
	 /**
	  * 设置启用和禁用账号
	  * @author 翟超锋  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/enableORdisable")
	 public OutputDTO enableORdisable(String id,String status) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 if(status.equals("1")){
			 map.put("validStsCd", "0");//失效
		 }else if(status.equals("0")){
			 map.put("validStsCd", "1");//有效
		 }
		 OutputDTO outputDTO = getOutputDTO(map,"adminService","enableORdisable");
		 return outputDTO;
	 }
	 
	 
	 
	 /**
	  * 根据Id查看角色详情(编辑)
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/queryAdminDetailByEdit")
	 public OutputDTO queryAdminDetailByEdit(String id) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"adminService","queryAdminDetail");
		 return outputDTO;
	 }
	 
	 
	 
	 
	 /**
	  * 根据Id查看角色详情(查看)
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/queryAdminDetailByView")
	 public OutputDTO queryAdminDetailByView(String id) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"adminService","queryAdminDetail");
		 return outputDTO;
	 }
	 /**
	  * 查询角色,传入userId返回包含是否拥有该角色（hasState为0不包含，为1包含）。
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/queryRoleListWithHasAdmin")
	 public OutputDTO queryRoleListWithHasAdmin(@ModelAttribute RoleQueryForm roleQueryForm) {
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleQueryForm);
         OutputDTO outputDTO = getOutputDTO(params,"roleService","queryRoleListWithHasAdmin");
         return outputDTO;
	 }
	 
	 /**
	  * 查询管理员拥有的角色
	  * @author lianziyu  
	  * @param userId
	  * @return
	  */
	 @RequestMapping("/queryAdminRoles")
	 public OutputDTO queryAdminRoles(@ModelAttribute RoleQueryForm roleQueryForm) {
		 Map<String,Object> params = BeanUtil.convertBean2Map(roleQueryForm);
		 OutputDTO outputDTO = getOutputDTO(params,"roleUserService","queryRoleUserList");
	     return outputDTO;
	 }
	 
	 /**
	  * 修改管理员拥有的角色
	  * @author lianziyu  
	  * @param userId
	  * @return
	  */
	 @RequestMapping("/updateAdminRoles")
	 public OutputDTO updateAdminRoles(@ModelAttribute AdminRoleUpdateFrom adminRoleUpdateFrom) {
		 if(adminRoleUpdateFrom.getAddRoleIds().contains(Constants.ROOT_ROLE)||adminRoleUpdateFrom.getRemoveRoleIds().contains(Constants.ROOT_ROLE)) {
			 String roleIds  = (String)getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
	         if(!roleIds.contains(Constants.ROOT_ROLE)) {
	        	 return new OutputDTO("-1", "没有操作超级管理员权限");
	         }
		 }
		 Map<String,Object> params = BeanUtil.convertBean2Map(adminRoleUpdateFrom);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 //该数组是与原有用户相比多出的新增关联用户Id
		 params.put("addRoleIds", adminRoleUpdateFrom.getAddRoleIds().split(","));
		 //该数组是与原有用户相比去除的新增关联用户Id
		 params.put("removeRoleIds", adminRoleUpdateFrom.getRemoveRoleIds().split(","));
		 logger.info("修改角色用户,用户Id=[{}],系统Id=[{}],新增角色Ids=[{}],删除角色Ids=[{}],操作人Id=[{}]",
				 params.get("userId"),params.get("sysTypeCd"),adminRoleUpdateFrom.getAddRoleIds(),
				 adminRoleUpdateFrom.getRemoveRoleIds(),params.get("crtUserId"));
		 OutputDTO outputDTO = getOutputDTOPlatform(params,"roleUserService","updateRoleUserByUser");
	     return outputDTO;
	 }
	 
	 /**
	  * 查询管理员,传入roleId返回包含是否拥有某角色（hasState为0不包含，为1包含）。
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/queryAdminsByHasRoleState")
	 public OutputDTO queryAdminsByHasRoleState(@ModelAttribute AdminQueryForm adminQueryForm) {
		Map<String,Object> params = BeanUtil.convertBean2Map(adminQueryForm);
        OutputDTO outputDTO = getOutputDTO(params,"adminService","queryAdminListByHasRoleState");
        return outputDTO;
	 }
	 
	 
	 
	 /**
	 *  判断手机号是否已注册
	 */
	 private boolean isPhoneReg(String loginId,String id){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("loginId", loginId);
		params.put("id", id);
		logger.info("管理端校验手机号是否已存在，手机号=[{}]",loginId);
		OutputDTO output = getOutputDTO(params, "adminService", "queryAdminByLoginId");
		if(output.getItem() != null && !output.getItem().isEmpty()){
			return true;
		}
		logger.info("手机号已存在，手机号=[{}]", loginId);
		return false;
	 }
	 
	 /**
	  * 管理端管理员重置用户密码
	  * @param adminUpdateForm 入参对象
	  * @return outputDTO 公共出参对象
	  */
	 @RequestMapping("/resetAdminPwd")
	 public OutputDTO resetAdminPwd(@RequestParam String userId) {
		 Map<String,Object> params = new HashMap<String, Object>();
		 Session session = getSession();
		 params.put("modfUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 if(StringUtil.isEmpty(userId)) {
			 return new OutputDTO("-1", "用户id不能为空");
		 }
		 params.put("id", userId);
		 // 重置密码
		 params.put("loginPw", MD5Util.generate("123456")); //重置密码为 123
	     OutputDTO outputDTO = getOutputDTOPlatform(params, "adminService", "updateAdmin");
	     return outputDTO;
	 }
	 
	 
	 /**
	  * 管理端管理员添加
	  * @author lianziyu  
	  * @param roleId
	  * @return
	  */
	 @RequestMapping("/getDeepartmentUserTree")
	 public OutputDTO getDeepartmentUserTree() {
		 Map<String,Object> params = new HashMap<String, Object>();
	     OutputDTO outputDTO = getOutputDTOPlatform(params,"adminService","getDeepartmentUserTree");
	     return outputDTO;
	 }
	 
	 /**
	  * 报表配置人员权限的时候获取部门和人员树状，需要过滤没有分类权限人员 
	  * @param busiNum 业务区编号
	  * @param busiCatNum 业务分类编号
	  * @param reportCatNum 报表分类编号
	  * @return

	 @RequestMapping("/getReportUserTree")
	 public OutputDTO getReportUserTree(@ModelAttribute ReportConfigForm reportConfigForm) {
	     Map<String, Object> params = BeanUtil.convertBean2Map(reportConfigForm);
		 OutputDTO outputDTO = getOutputDTOPlatform(params,"adminService","getReportUserTree");
		 return outputDTO;
	 }*/
	 
	 /**
     * 批量导入用户数据
     * @param file 导入文件
     * @return
     */
    @RequestMapping("/importExcelUserData")
    public OutputDTO importExcelUserData(@RequestParam("file") MultipartFile file, String dtNum) {
        OutputDTO outputDTO = new OutputDTO();
        if (file == null || file.isEmpty()) {
            outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
            outputDTO.setMsg("导入失败 。" + "导入的excel文件表格内容为空");
            return outputDTO;
        }
        // 判断文件是否是xls文件
        if (!(file.getOriginalFilename().endsWith(".xls")) && !(file.getOriginalFilename().endsWith(".xlsx"))) {
            outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
            outputDTO.setMsg("导入失败 。" + "请选择正确的Excel格式文件");
            return outputDTO;
        }
        try {
            //存放表头信息
            String[] header = {"userName", "loginId", "cardNum", "sex", 
                    "userDepartmentName", "roleUser"};
            // 读取excel文件数据
            List<String> headers = Arrays.asList(header);
            Map<String, Object> params = new HashMap<>();
            List<Map<String, Object>> list = ExcelReadUtils.readExcel(file.getOriginalFilename(), 
                    file.getInputStream(), headers);
            if (list.size() > 0) {
                params.put("items", list);
                outputDTO = getOutputDTO(params, "adminService", "importExcelUserData");
                int failure = outputDTO.getTotal();
                outputDTO.setMsg("导入完成，成功：" + failure + "条;失败：" + (list.size() - failure) + "条！");
            } else {
                outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
                outputDTO.setMsg("excel数据不能为空！");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
            logger.error("AdminController.importExcelUserData：" + e);
        }
        return outputDTO;
    }
	
    /**
     * 获取所有用户及部门权限信息
     * @return

    @RequestMapping("/getDataTableUserTree")
    public OutputDTO getDataTableUserTree(@ModelAttribute ReportConfigForm reportConfigForm) {
        Map<String, Object> params = BeanUtil.convertBean2Map(reportConfigForm);
        OutputDTO outputDTO = getOutputDTOPlatform(params,"adminService","getDataTableUserTree");
        return outputDTO;
    }*/
}
  
