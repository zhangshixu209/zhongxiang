package com.chmei.nzbmanage.index.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**
 * 管理员端首页查询待审核任务bean
 * Date:     2018年12月12日 上午10:31:56
 * @author   tongym
 * @version
 * @since    JDK 1.8
 */
public class QueryTaskBean extends BaseForm {
    private String taskState;

    private Long id;

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
