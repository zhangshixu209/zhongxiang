package com.chmei.nzbservice.notice.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.notice.service.INoticeService;
import com.chmei.nzbservice.noticetype.service.impl.NoticetypeServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 公告管理接口实现类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceImpl implements INoticeService {
    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(NoticetypeServiceImpl.class);

    /**
     * 分页查询公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 分页查询我的公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryMyNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryMyNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 存草稿保存
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Transactional
    @Override
    public void insertNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        //存草稿插入公告信息
        input.setService("noticeService");
        input.setMethod("insertNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 发布保存
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Transactional
    @Override
    public void publishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        //发布插入公告信息
        input.setService("noticeService");
        input.setMethod("publishNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 删除公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Transactional
    @Override
    public void deleteNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        //删除公告信息并删除对应权限信息
        input.setService("noticeService");
        input.setMethod("deleteNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 外部发布按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void publishBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("publishBtnNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 外部撤回按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void recallBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("recallBtnNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 点击公告主题查询页面详情
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryNoticeContentById(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryNoticeContentById");
        getNzbDataService().execute(input, output);
    }

    /**
     * 分页查询已阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryReadMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryReadMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 分页查询未阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryUnReadMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryUnReadMeaasgeInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 分页获取阅读人与未阅读人详细信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryAllMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryAllMeaasgeInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 编辑存草稿
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void updateNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("updateNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 编辑发布
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void editPublishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("editPublishNoticeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 公告状态下拉列表展示
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryNoticeStatusMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryNoticeStatusMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 公告紧急程度下拉
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryInstancyLevelMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryInstancyLevelMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 根据id查询单条公告信息(回显使用)
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryNoticeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("queryNoticeDetailMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 获取到该公告的权限树信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void getNoticePermissionTree(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticeService");
        input.setMethod("getNoticePermissionTree");
        getNzbDataService().execute(input, output);
    }
}
