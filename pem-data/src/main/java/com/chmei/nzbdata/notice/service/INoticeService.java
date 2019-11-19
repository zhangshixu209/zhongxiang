package com.chmei.nzbdata.notice.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
/**
 * 公告管理接口
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public interface INoticeService {

    /**
     * 存草稿插入公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void insertNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 发布插入公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常信息
     */
    void publishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;


    /**
     * 编辑存草稿
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 编辑发布
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常信息
     */
    void editPublishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;


    /**
     * 删除公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void deleteNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 外部发布按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void publishBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 外部撤回按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void recallBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查看单条公告信息详情
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryNoticeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 加载公告状态列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryNoticeStatusMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 加载紧急程度列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryInstancyLevelMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 点击公告主题请求接口
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void  queryNoticeContentById(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询我的公告(推送给我的)
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryMyNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查看已阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常信息
     */
    void queryReadMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查看未阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryUnReadMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查看详情
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryAllMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 获取到该公告的用户权限信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void getNoticePermissionTree(InputDTO input, OutputDTO output) throws NzbDataException;

}
