package com.chmei.nzbservice.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享好友转账接口
 *
 * @author zhangshixu
 * @since 2019年11月12日 17点51分
 */
public interface ITransferAccountsService {

    /**
     * 众享好友转账
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void memberTransferAccounts(InputDTO input, OutputDTO output) throws NzbServiceException;

}
