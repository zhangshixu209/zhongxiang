package com.chmei.nzbmanage.notice.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告实体类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public class Notice implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 6366851896339086053L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 公告编码
     */
    private String code;
    /**
     * 公告状态
     */
    private Long status;
    /**
     * 公告名称
     */
    private String name;
    /**
     * 公告类型id
     */
    private Long noticeTypeId;
    /**
     * 公告类型名称
     */
    private String noticeTypeName;
    /**
     * 紧急程度id
     */
    private Long instancyLevelId;
    /**
     * 紧急程度名称
     */
    private String instancyLevelName;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 发送类型
     */
    private String sendType;
    /**
     * 发布人id
     */
    private Long publishId;
    /**
     * 发布人名称
     */
    private String publishName;
    /**
     * 发送总人数
     */
    private int sendTotal;
    /**
     * 发布部门
     */
    private String publishDepartment;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 创建人id
     */
    private Long crtId;
    /**
     * 创建人名称
     */
    private String crtName;
    /**
     * 创建时间
     */
    private Date crtTime;
    /**
     * 修改人id
     */
    private Long modfId;
    /**
     * 修改人名称
     */
    private String modfName;
    /**
     * 修改时间
     */
    private Date modfTime;
    /**
     * 当前用户id
     */
    private Long currentUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setNoticeTypeId(Long noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }

    public String getNoticeTypeName() {
        return noticeTypeName;
    }

    public void setNoticeTypeName(String noticeTypeName) {
        this.noticeTypeName = noticeTypeName;
    }

    public Long getInstancyLevelId() {
        return instancyLevelId;
    }

    public void setInstancyLevelId(Long instancyLevelId) {
        this.instancyLevelId = instancyLevelId;
    }

    public String getInstancyLevelName() {
        return instancyLevelName;
    }

    public void setInstancyLevelName(String instancyLevelName) {
        this.instancyLevelName = instancyLevelName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public int getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(int sendTotal) {
        this.sendTotal = sendTotal;
    }

    public String getPublishDepartment() {
        return publishDepartment;
    }

    public void setPublishDepartment(String publishDepartment) {
        this.publishDepartment = publishDepartment;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Long getModfId() {
        return modfId;
    }

    public void setModfId(Long modfId) {
        this.modfId = modfId;
    }

    public String getModfName() {
        return modfName;
    }

    public void setModfName(String modfName) {
        this.modfName = modfName;
    }

    public Date getModfTime() {
        return modfTime;
    }

    public void setModfTime(Date modfTime) {
        this.modfTime = modfTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }
}
