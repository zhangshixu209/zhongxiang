package com.chmei.nzbservice.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.member.service.IHelpArchiveService;
import org.springframework.stereotype.Service;

/**
 * 帮助文档service
 *
 * @author zhangshixu
 * @since 2019年11月12日 17点51分
 */
@Service("helpArchiveService")
public class HelpArchiveServiceImpl extends BaseServiceImpl implements IHelpArchiveService {


    /**
     * 帮助文档列表查询
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryHelpArchiveList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("helpArchiveService");
        input.setMethod("queryHelpArchiveList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 新增帮助文档
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("helpArchiveService");
        input.setMethod("saveHelpArchiveInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询帮助文档详情
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryHelpArchiveDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("helpArchiveService");
        input.setMethod("queryHelpArchiveDetail");
        getNzbDataService().execute(input, output);
    }

    /**
     * 编辑帮助文档
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("helpArchiveService");
        input.setMethod("updateHelpArchiveInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 删除帮助文档
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void deleteHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("helpArchiveService");
        input.setMethod("deleteHelpArchiveInfo");
        getNzbDataService().execute(input, output);
    }
}
