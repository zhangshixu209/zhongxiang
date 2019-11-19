package com.chmei.nzbdata.notice.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
/**
 * 公告阅读信息接口
 *
 * @author wangteng
 * @since 2019年5月13日
 */
public interface INoticeReadService {
    /**
     * 插入公告阅读信息信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void insertNoticeReadMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 查询公告阅读信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryNoticeReadMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException;

}
