package com.chmei.nzbmanage.operatelog.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志Form
 *
 * @author zhangsx
 * @since 2019年05月13日 15点51分
 */
public class OperateLogForm implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 日志编号
     */
    private String logNum;

    /**
     * 操作页面
     */
    private String operatePage;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 执行操作时间
     */
    private Date execOperateTime;

    /**
     * 有效标识（0：无效，1有效）
     */
    private String status;

    /**
     * 操作人id
     */
    private Long crtUserId;

    /**
     * 操作人姓名
     */
    private String crtUserName;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 修改用户Id
     */
    private Long modfUserId;

    /**
     * 修改人姓名
     */
    private String modfUserName;

    /**
     * 修改时间
     */
    private Date modfTime;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
    
    /** 
     * 起始行 
     */
    private Integer start;
    
    /**
     * 每页显示条数
     */
    private Integer limit;

    private String userId;
    private String userMobile;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogNum() {
        return logNum;
    }

    public void setLogNum(String logNum) {
        this.logNum = logNum;
    }

    public String getOperatePage() {
        return operatePage;
    }

    public void setOperatePage(String operatePage) {
        this.operatePage = operatePage;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Date getExecOperateTime() {
        return execOperateTime;
    }

    public void setExecOperateTime(Date execOperateTime) {
        this.execOperateTime = execOperateTime;
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

    public Date getModfTime() {
        return modfTime;
    }

    public void setModfTime(Date modfTime) {
        this.modfTime = modfTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}