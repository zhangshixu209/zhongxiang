package com.chmei.nzbservice.notice.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
/**
 * 公告管理接口
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public interface INoticeService {
    /**
     * 分页查询公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 分页查询我的公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryMyNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 存草稿保存
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void insertNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 发布保存
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void publishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 删除公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void deleteNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 外部发布按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void publishBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 外部撤回按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void recallBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 点击公告主题查询页面详情
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryNoticeContentById(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 分页查询已阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryReadMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 分页查询未阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryUnReadMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 分页获取阅读人与未阅读人详细信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryAllMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 编辑存草稿
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void updateNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 编辑发布
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void editPublishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 公告状态下拉列表展示
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryNoticeStatusMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 公告紧急程度下拉
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryInstancyLevelMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 根据id查询单条公告信息(回显使用)
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void  queryNoticeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 获取到该公告的权限树信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void getNoticePermissionTree(InputDTO input, OutputDTO output) throws NzbServiceException;
}
