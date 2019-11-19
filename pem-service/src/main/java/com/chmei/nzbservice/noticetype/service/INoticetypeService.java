package com.chmei.nzbservice.noticetype.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 公告分类接口
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public interface INoticetypeService {
    /**
     * 插入公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void insertNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 更新公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void updateNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 删除公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void deleteNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询公共分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查看单条公共分类信息详情
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryNoticrtypeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 公告分类下拉列表展示
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryAllNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
}
