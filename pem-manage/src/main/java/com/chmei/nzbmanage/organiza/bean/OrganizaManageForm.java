package com.chmei.nzbmanage.organiza.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织机构管理Form
 *
 * @author zhangsx
 * @since 2019年05月10日 15点51分
 */
public class OrganizaManageForm implements Serializable {
    
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 组织机构编号
     */
    private String organizaId;

    /**
     * 组织机构名称
     */
    private String organizaName;

    /**
     * 父机构编号
     */
    private String parentOrganizaId;

    /**
     * 父机构名称
     */
    private String parentOrganizaName;

    /**
     * 排序
     */
    private String sort;

    /**
     * 子级机构编号
     */
    private String sonOrganizaId;

    /**
     * 子级机构名称
     */
    private String sonOrganizaName;

    /**
     * 有效标识（0：无效，1有效）
     */
    private String status;

    /**
     * 创建用户id
     */
    private Long crtUserId;

    /**
     * 机构创建时间
     */
    private Date crtTime;

    /**
     * 修改用户Id
     */
    private Long modfUserId;

    /**
     * 修改时间
     */
    private Date modfTime;
    
    /**
     * 备注
     */
    private String remark;
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizaId() {
        return organizaId;
    }

    public void setOrganizaId(String organizaId) {
        this.organizaId = organizaId;
    }

    public String getOrganizaName() {
        return organizaName;
    }

    public void setOrganizaName(String organizaName) {
        this.organizaName = organizaName;
    }

    public String getParentOrganizaId() {
        return parentOrganizaId;
    }

    public void setParentOrganizaId(String parentOrganizaId) {
        this.parentOrganizaId = parentOrganizaId;
    }

    public String getParentOrganizaName() {
        return parentOrganizaName;
    }

    public void setParentOrganizaName(String parentOrganizaName) {
        this.parentOrganizaName = parentOrganizaName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSonOrganizaId() {
        return sonOrganizaId;
    }

    public void setSonOrganizaId(String sonOrganizaId) {
        this.sonOrganizaId = sonOrganizaId;
    }

    public String getSonOrganizaName() {
        return sonOrganizaName;
    }

    public void setSonOrganizaName(String sonOrganizaName) {
        this.sonOrganizaName = sonOrganizaName;
    }

    public Long getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(Long crtUserId) {
        this.crtUserId = crtUserId;
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

    public Date getModfTime() {
        return modfTime;
    }

    public void setModfTime(Date modfTime) {
        this.modfTime = modfTime;
    }
    
}