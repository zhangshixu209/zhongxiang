package com.chmei.nzbmanage.member.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 会员管理封装类
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点23分
 */
public class MemberForm extends BaseForm {

    /**
     * 主键
     */
    private Long id;

    /**
     * 会员账号
     */
    private String memberAccount;

    /**
     * 会员昵称
     */
    private String nickname;

    /**
     * 会员密码
     */
    private String memberPwd;

    /**
     * 绑定手机号（默认与账号一致）
     */
    private String bindPhone;

    /**
     * 推荐人账号
     */
    private String recomAccount;

    /**
     * 推荐人昵称
     */
    private String recomNickName;

    /**
     * 红包余额
     */
    private String redEnveBalance;

    /**
     * 钱包余额
     */
    private String walletBalance;

    /**
     * 广告费
     */
    private String advertisingFee;

    /**
     * 用户状态（1正常，2警告，3冻结）
     */
    private String status;

    /**
     * 创建人Id
     */
    private Long crtUserId;

    /**
     * 创建人姓名
     */
    private String crtUserName;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 修改人Id
     */
    private Long modfUserId;

    /**
     * 修改人姓名
     */
    private String modfUserName;

    /**
     * 修改时间
     */
    private Date modfUserTime;

    private String username;
    private String password;

    private String code;
    private String sessionId;

    private String newPassword;
    private String oldPassword;

    /**
     * 会员支付密码
     */
    private String memberPaymentPwd;

    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 账户类型
     */
    private String accountType;
    /**
     * 会员提现账户
     */
    private String paymentAccount;

    /**
     * 会员头像
     */
    private String headImgUrl;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号码
     */
    private String cardNum;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生年月日
     */
    private String birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 省ID
     */
    private Integer provinceId;

    /**
     * 市ID
     */
    private Integer cityId;

    /**
     * 县ID
     */
    private Integer countyId;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区/县名称
     */
    private String countyName;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public String getRecomAccount() {
        return recomAccount;
    }

    public void setRecomAccount(String recomAccount) {
        this.recomAccount = recomAccount;
    }

    public String getRecomNickName() {
        return recomNickName;
    }

    public void setRecomNickName(String recomNickName) {
        this.recomNickName = recomNickName;
    }

    public String getRedEnveBalance() {
        return redEnveBalance;
    }

    public void setRedEnveBalance(String redEnveBalance) {
        this.redEnveBalance = redEnveBalance;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getAdvertisingFee() {
        return advertisingFee;
    }

    public void setAdvertisingFee(String advertisingFee) {
        this.advertisingFee = advertisingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(Long crtUserId) {
        this.crtUserId = crtUserId;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Long getModfUserId() {
        return modfUserId;
    }

    public void setModfUserId(Long modfUserId) {
        this.modfUserId = modfUserId;
    }

    public String getModfUserName() {
        return modfUserName;
    }

    public void setModfUserName(String modfUserName) {
        this.modfUserName = modfUserName;
    }

    public Date getModfUserTime() {
        return modfUserTime;
    }

    public void setModfUserTime(Date modfUserTime) {
        this.modfUserTime = modfUserTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getMemberPaymentPwd() {
        return memberPaymentPwd;
    }

    public void setMemberPaymentPwd(String memberPaymentPwd) {
        this.memberPaymentPwd = memberPaymentPwd;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
