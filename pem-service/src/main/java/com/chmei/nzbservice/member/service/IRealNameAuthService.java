package com.chmei.nzbservice.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 会员管理接口
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点05分
 */
public interface IRealNameAuthService {

    /**
     * 新增会员信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void saveRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询会员列表
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void updateRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询实名认证信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryRealNameInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
