package com.chmei.nzbmanage.index.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.ControlConstants.RETURN_CODE;
import com.chmei.nzbcommon.util.MD5Util;
import com.chmei.nzbcommon.util.SecurityCode;
import com.chmei.nzbcommon.util.SecurityImage;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.index.bean.UpdatePasswordForm;

/**
 * 管理端首页管理
 */
@RestController
@RequestMapping("/api/index/main")
public class IndexController extends BaseController {

	/**
	 * log
	 */
    private static final Logger logger = Logger.getLogger(IndexController.class);

  
    
    /**
             * 验证码
     * @param response 
     * @user songyw
     * @date 2019年3月19日
     */
    @ResponseBody
    @RequestMapping(value="/imgCode",method=RequestMethod.GET)
    public void userCheckCode(HttpServletResponse response) {
        ServletOutputStream outStream = null;
        try {
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            String code = SecurityCode.getSecurityCode();
            getSession().setAttribute("userCheckCode", code);
            // 将图像输出到Servlet输出流中。
            outStream = response.getOutputStream();
            ByteArrayInputStream inputStream = SecurityImage
                    .getImageAsInputStream(code);
            int len = inputStream.available();
            if (len > 0) {
                byte[] bb = new byte[1024];
                while (inputStream.read(bb) > 0) {
                    outStream.write(bb);
                }
            }
        } catch (IOException e) {
            logger.error("userCheckCode:", e);
        }finally{
            if(outStream !=null){
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
             * 验证输入的原始密码
     * @param mobile
     * @param password
     * @return 
     * @user songyw
     * @date 2019年3月19日
     */
    public boolean userPasswordIsMatch(String mobile, String password){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("loginId", mobile);
//      params.put("password", password);
        OutputDTO output = getOutputDTO(params, "adminService", "queryAdminByMobile");
        if(output.getItem() == null || output.getItem().isEmpty()){
            logger.info("没有查询到该用户:"+mobile);
            return false;
        }
        String pwd = output.getItem().get("loginPw").toString();
        if(MD5Util.verify(password, pwd)){
            logger.info(mobile+"用户密码验证成功");
            return true;
        }
        logger.info(mobile+"用户密码验证失败");
        return false;
    }
    
    /**
              * 管理端修改密码
     * @param updatePasswordForm
     * @return 
     * @user songyw
     * @date 2019年3月19日
     */
    
    @RequestMapping("/updateAdminPassword")
    public OutputDTO updateSeatUserPassword(@ModelAttribute("UpdateForm") @Validated UpdatePasswordForm updatePasswordForm){
        OutputDTO outputDTO = new OutputDTO();
        Session session = getSession();
        try {
            //从session中获取当前登录用户手机号
            String mobile = (String) session.getAttribute(Constants.SESSION_USER.MOBILE);
            if (null == session.getAttribute("userCheckCode")) {
                outputDTO.setCode("-1");
                outputDTO.setMsg("图片验证码错误");
                return outputDTO;
            }
            String codeImg = session.getAttribute("userCheckCode").toString();
            if (StringUtil.isEmpty(codeImg)|| !codeImg.equalsIgnoreCase(updatePasswordForm.getImgCode())) {
                session.removeAttribute("userCheckCode");
                outputDTO.setCode("-1");
                outputDTO.setMsg("图片验证码错误");
                return outputDTO;
            }
            //验证帐号密码
            if(!userPasswordIsMatch(mobile,updatePasswordForm.getPassword())){
                outputDTO.setCode("-1");
                outputDTO.setMsg("原密码不正确，请重新输入！");
                return outputDTO;
            }
            //验证确认密码和密码是否匹配
            if(!updatePasswordForm.getComfirmPassword().equals(updatePasswordForm.getNewPassword())){
                outputDTO.setCode("-1");
                outputDTO.setMsg("确认密码和新密码不匹配，请重试！");
                return outputDTO;
            }
            //验证新密码不能是123456
            if("123456".equals(updatePasswordForm.getNewPassword())){
                outputDTO.setCode("-1");
                outputDTO.setMsg("新密码不能是初始化密码,请修改!");
                return outputDTO;
            }
            //修改密码
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("password", MD5Util.generate(updatePasswordForm.getNewPassword()));
            map.put("mobile", mobile);
            outputDTO = getOutputDTO(map, "adminService", "updateAdminPwd");
            logger.info(mobile+"的密码修改成功");
        } catch (Exception e) {
            outputDTO.setCode(RETURN_CODE.SYSTEM_ERROR);
            outputDTO.setMsg("密码修改失败");
            logger.error("IndexController.updateAdminPwd"+e);
        }
        return outputDTO;
    }
}