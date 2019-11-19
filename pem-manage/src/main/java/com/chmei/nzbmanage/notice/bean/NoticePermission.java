package com.chmei.nzbmanage.notice.bean;

import java.io.Serializable;

/**
 * 公告权限实体类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public class NoticePermission implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 2686286709535237576L;
    /**
     * 公告id
     */
    private Long noticeId;
    /**
     * 接收人id
     */
    private Long crt_id;
    /**
     * 状态
     */
    private int status;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getCrt_id() {
        return crt_id;
    }

    public void setCrt_id(Long crt_id) {
        this.crt_id = crt_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status; }
}
