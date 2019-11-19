package com.chmei.nzbmanage.right.controller;

import java.util.HashMap;
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
import com.chmei.nzbcommon.util.MD5Util;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbcommon.util.UuidUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.right.bean.RightAddForm;
import com.chmei.nzbmanage.right.bean.RightDeleteForm;
import com.chmei.nzbmanage.right.bean.RightUpdateForm;

/**  
 * 权限管理
 * Date:     2018年8月8日 下午5:38:07 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
@RestController
@Validated
@RequestMapping("/api/sys/right")
public class RightController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(RightController.class);
	 /**
	  * 查询权限结构树
	  * @author lianziyu  
	  * @return
	  */
	 @RequestMapping("/queryRightList")
     public OutputDTO queryRightList(){
        Map<String, Object> map = new HashMap<>();
        OutputDTO outputDTO = getOutputDTO(map,"rightService","queryRightTree");
        return outputDTO;
     }
	 
	 /**
	  * 根据Id查看权限详情（编辑）
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/queryRightDetail")
	 public OutputDTO queryRightDetail(String id) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"rightService","queryRightDetail");
		 return outputDTO;
	 }
	 
	 /**
	  * 根据Id查看权限详情(查看详情)
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/queryRightDetailByView")
	 public OutputDTO queryRightDetailByView(String id) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("id", id);
		 OutputDTO outputDTO = getOutputDTO(map,"rightService","queryRightDetail");
		 return outputDTO;
	 }
	 
	 /**
	  * 权限添加
	  * @author lianziyu  
	  * @param rightAddForm
	  * @return
	  */
	 @RequestMapping("/addRight")
	 public OutputDTO addRight(@ModelAttribute RightAddForm rightAddForm){
		 if(StringUtil.isEmpty(rightAddForm.getParentId())) {
			 rightAddForm.setParentId("0");
		 }
		 rightAddForm.setValidFlag("1");
		 String mo =  null;
		 if(StringUtil.isEmpty(rightAddForm.getUrlAddr())) {
			 mo = UuidUtil.get32UUID().toUpperCase();
		 }else {
			 if(isUrlAddrHas(rightAddForm.getUrlAddr(), "")) {
				 return new OutputDTO("-1", "该URL参数已存在");
			 }
			 mo = MD5Util.md5(rightAddForm.getUrlAddr());
		 }
		 rightAddForm.setMo(mo);
		 Map<String,Object> params = BeanUtil.convertBean2Map(rightAddForm);
		 Session session = getSession();
		 params.put("crtUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 return getOutputDTOPlatform(params, "rightService", "saveRight");
	 } 
	 
	 /**
	  * 修改权限
	  * @author lianziyu  
	  * @param rightAddForms
	  * @return
	  */
	 @RequestMapping("/editRight")
	 public OutputDTO editRight(@ModelAttribute RightUpdateForm rightUpdateForm){
		 String mo =  null;
		 if(StringUtil.isEmpty(rightUpdateForm.getUrlAddr())) {
			 mo = "";
		 }else {
			 if(isUrlAddrHas(rightUpdateForm.getUrlAddr(), String.valueOf(rightUpdateForm.getId()))) {
				 return new OutputDTO("-1", "该权限地址已存在");
			 }
			 mo = MD5Util.md5(rightUpdateForm.getUrlAddr());
		 }
		 rightUpdateForm.setMo(mo);
		 Map<String,Object> params = BeanUtil.convertBean2Map(rightUpdateForm);
		 Session session = getSession();
		 params.put("modfUserId", session.getAttribute(Constants.SESSION_USER.ID));
		 return getOutputDTOPlatform(params, "rightService", "updateRight");
	 }
	 
	 /**
	  * 删除权限
	  * @author lianziyu  
	  * @param id
	  * @return
	  */
	 @RequestMapping("/deleteRight")
	 public OutputDTO deleteRight(@ModelAttribute RightDeleteForm rightDeleteForm){
	     logger.info("RightController.deleteRight删除数据:" + rightDeleteForm.getId());
		 Map<String,Object> params = BeanUtil.convertBean2Map(rightDeleteForm);
		 params.put("modfUserId", getSession().getAttribute(Constants.SESSION_USER.ID));
		 return getOutputDTOPlatform(params, "rightService", "deleteRight");
	 } 
	 
	 /**
	 *  判断链接是否已存在
	 */
	 private boolean isUrlAddrHas(String urlAddr,String id){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("urlAddr", urlAddr);
		params.put("id", id);
		logger.info("校验链接是否已存在,链接地址=[{}]",urlAddr);
		OutputDTO output = getOutputDTO(params, "rightService", "queryRightByUrlAddr");
		if(output.getItem() != null && !output.getItem().isEmpty()){
			return true;
		}
		logger.info("链接已存在,链接地址=[{}]",urlAddr);
		return false;
	 }
}
  
