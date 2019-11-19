package com.chmei.nzbservice.noticetype.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.noticetype.service.INoticetypeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
/**
 * 公告分类接口实现类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@Service("noticetypeService")
public class NoticetypeServiceImpl extends BaseServiceImpl implements INoticetypeService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(NoticetypeServiceImpl.class);

    /**
     * 插入公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void insertNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticetypeService");
        input.setMethod("insertNoticetypeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 更新公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void updateNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticetypeService");
        input.setMethod("updateNoticetypeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 删除公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void deleteNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticetypeService");
        input.setMethod("deleteNoticetypeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询公共分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticetypeService");
        input.setMethod("queryNoticetypeMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查看单条公共分类信息详情
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryNoticrtypeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticetypeService");
        input.setMethod("queryNoticrtypeDetailMessageInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 公告分类下拉列表展示
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常信息
     */
    @Override
    public void queryAllNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("noticetypeService");
        input.setMethod("queryAllNoticetypeMessageInfo");
        getNzbDataService().execute(input, output);
    }
}
