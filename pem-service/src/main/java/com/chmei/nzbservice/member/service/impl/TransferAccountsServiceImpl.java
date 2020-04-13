package com.chmei.nzbservice.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.member.service.IRealNameAuthService;
import com.chmei.nzbservice.member.service.ITransferAccountsService;
import org.springframework.stereotype.Service;

/**
 * 众享好友转账
 *
 * @author zhangshixu
 * @since 2019年11月12日 17点51分
 */
@Service("transferAccountsService")
public class TransferAccountsServiceImpl extends BaseServiceImpl implements ITransferAccountsService {

    /**
     * 众享好友转账
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void memberTransferAccounts(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("transferAccountsService");
        input.setMethod("memberTransferAccounts");
        getNzbDataService().execute(input, output);
    }

    /**
     * 校验转账时是否显示广告费
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void checkIsAdvertisingFee(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("transferAccountsService");
        input.setMethod("checkIsAdvertisingFee");
        getNzbDataService().execute(input, output);
    }
}
