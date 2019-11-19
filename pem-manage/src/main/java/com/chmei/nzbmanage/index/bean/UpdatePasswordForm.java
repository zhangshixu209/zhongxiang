package com.chmei.nzbmanage.index.bean;

import javax.validation.constraints.NotNull;

import com.chmei.nzbmanage.common.controller.BaseForm;

public class UpdatePasswordForm extends BaseForm{

    /**
            * 序列号
     */
    private static final long serialVersionUID = 1L;
    
    /**密码*/
    @NotNull
    private String password;
    /**新密码*/
    @NotNull
    private String newPassword;
    /**确认密码*/
    @NotNull
    private String comfirmPassword;
    /**图片验证码*/
    @NotNull
    private String imgCode;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getComfirmPassword() {
        return comfirmPassword;
    }
    public void setComfirmPassword(String comfirmPassword) {
        this.comfirmPassword = comfirmPassword;
    }
    public String getImgCode() {
        return imgCode;
    }
    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }
    
}