package com.chmei.nzbmanage.noticetype.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 公告分类实体类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public class Noticetype implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1417077484960993083L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 公告分类编码
     */
    private String code;
    /**
     * 公告分类名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人id
     */
    private Long insertId;
    /**
     * 创建人名称
     */
    private String insertName;
    /**
     * 更新者id
     */
    private Long updateId;
    /**
     *更新者名称
     */
    private String updateName;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 排序
     */
    private Integer sort;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getInsertId() {
        return insertId;
    }

    public void setInsertId(Long insertId) {
        this.insertId = insertId;
    }

    public String getInsertName() {
        return insertName;
    }

    public void setInsertName(String insertName) {
        this.insertName = insertName;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getsort() {
        return sort;
    }

    public void setsort(Integer sort) {
        this.sort = sort;
    }

}
