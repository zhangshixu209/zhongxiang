package com.chmei.nzbmanage.noticetype.bean;

import java.io.Serializable;
/**
 * 公告分类页面封装类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public class NoticetypeQueryForm implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 4430997623725475258L;
    /**
     * 公告分类编码
     */
    private  String code;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 公告分类名称
     */
    private String name;
    /**
     * 起始行
     */
    private Integer start;

    /**
     * 每页显示条数
     */
    private Integer limit;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

}
