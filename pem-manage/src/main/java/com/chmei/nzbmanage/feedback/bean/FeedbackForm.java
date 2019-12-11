package com.chmei.nzbmanage.feedback.bean;

import java.util.Date;

import com.chmei.nzbmanage.common.controller.BaseFormReport;

/**
 * 反馈意见入参对象
 * 
 * @author zhangshixu
 * @since 2019年10月22日 14点53分
 */
public class FeedbackForm extends BaseFormReport {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    private Long id;

    /**
     * 反馈主题
     */
    private String titles;
    
    /**
     * 反馈内容
     */
    private String contents;

    /**
     * 工单名称
     */
    private String startPensonId;

    /**
     * 发起人姓名
     */
    private String startPensonName;

    /**
     * 发起时间
     */
    private Date startPensonTime;
    
    private String startTime;
    private String endTime;
    
    
    

    /**
     * 状态,0待回复，1已回复
     */
    private String status;

    /**
     * 回复内容
     */
    private String replyContent;
    
    /**
     * 回复人id
     */
    private String endPensonId;

    /**
     * 回复人姓名
     */
    private String endPensonName;

    /**
     *回复时间
     */
    private Date endPensonTime;
    
    /**
     *附件地址
     */
    private String filePaths;

	/**
	 * 反馈类型
	 */
	private String feedType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getStartPensonId() {
		return startPensonId;
	}

	public void setStartPensonId(String startPensonId) {
		this.startPensonId = startPensonId;
	}

	public String getStartPensonName() {
		return startPensonName;
	}

	public void setStartPensonName(String startPensonName) {
		this.startPensonName = startPensonName;
	}

	public Date getStartPensonTime() {
		return startPensonTime;
	}

	public void setStartPensonTime(Date startPensonTime) {
		this.startPensonTime = startPensonTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getEndPensonId() {
		return endPensonId;
	}

	public void setEndPensonId(String endPensonId) {
		this.endPensonId = endPensonId;
	}

	public String getEndPensonName() {
		return endPensonName;
	}

	public void setEndPensonName(String endPensonName) {
		this.endPensonName = endPensonName;
	}

	public Date getEndPensonTime() {
		return endPensonTime;
	}

	public void setEndPensonTime(Date endPensonTime) {
		this.endPensonTime = endPensonTime;
	}

	public String getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
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

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
}