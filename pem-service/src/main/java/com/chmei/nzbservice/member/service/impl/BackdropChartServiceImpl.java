package com.chmei.nzbservice.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.member.service.IBackdropChartService;
import com.chmei.nzbservice.member.service.ITransferAccountsService;
import org.springframework.stereotype.Service;

/**
 * 二维码背景图service
 *
 * @author zhangshixu
 * @since 2019年11月12日 17点51分
 */
@Service("backdropChartService")
public class BackdropChartServiceImpl extends BaseServiceImpl implements IBackdropChartService {


    /**
     * 二维码背景图列表查询
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryBackdropChartList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("backdropChartService");
        input.setMethod("queryBackdropChartList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 新增二维码背景图
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("backdropChartService");
        input.setMethod("saveBackdropChartInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询二维码背景图详情
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryBackdropChartDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("backdropChartService");
        input.setMethod("queryBackdropChartDetail");
        getNzbDataService().execute(input, output);
    }

    /**
     * 编辑二维码背景图
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("backdropChartService");
        input.setMethod("updateBackdropChartInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 删除二维码背景图
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void deleteBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("backdropChartService");
        input.setMethod("deleteBackdropChartInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 设置默认二维码背景图
     *
     * @param input  入參
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void setDefaultBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("backdropChartService");
        input.setMethod("setDefaultBackdropChartInfo");
        getNzbDataService().execute(input, output);
    }
}
