package com.chmei.nzbdata.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享好友转账接口
 *
 * @author zhangshixu
 * @since 2019年11月12日 16点39分
 */
public interface ITransferAccountsService {

    /**
     * 众享好友转账
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void memberTransferAccounts(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 校验转账时是否显示广告费
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void checkIsAdvertisingFee(InputDTO input, OutputDTO output) throws NzbDataException;
}
