package com.chmei.nzbdata.notice.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
/**
 * 公告权限信息接口
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public interface INoticePermissionService {

    /**
     * 批量插入公告权限信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void insertNoticePermissionMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 批量更新公告权限信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateNoticePermissiontypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 批量删除公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void deleteNoticePermissiontypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;


}
