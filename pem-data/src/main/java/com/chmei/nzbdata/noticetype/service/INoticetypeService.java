package com.chmei.nzbdata.noticetype.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

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
     * @throws NzbDataException 异常信息
     */
    void insertNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 更新公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 删除公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void deleteNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询公共分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查看单条公共分类信息详情
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryNoticrtypeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询所有的公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    void queryAllNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;


}
