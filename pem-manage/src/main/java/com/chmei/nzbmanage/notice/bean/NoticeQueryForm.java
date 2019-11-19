package com.chmei.nzbmanage.notice.bean;

import java.io.Serializable;

/**
 * 公告页面查询封装类类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public class NoticeQueryForm implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -3770975339952603809L;
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
    private Integer status;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 公告名称
     */
    private String name;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 是否已读
     */
    private String isSee;
    /**
     * 是否是超级管理员用户
     */
    private String isRoot;

    /**
     * 起始行
     */
    private Integer start;

    /**
     * 每页显示条数
     */
    private Integer limit;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getIsSee() {
        return isSee;
    }

    public void setIsSee(String isSee) {
        this.isSee = isSee;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }
}
