package com.chmei.nzbdata.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 会员管理接口
 *
 * @author zhangshixu
 * @since 2019年10月24日 13点40分
 */
public interface IRealNameAuthService {

    /**
     * 新增实名认证信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void saveRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 修改实名认证信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询实名认证信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryRealNameInfo(InputDTO input, OutputDTO output) throws NzbDataException;

}
