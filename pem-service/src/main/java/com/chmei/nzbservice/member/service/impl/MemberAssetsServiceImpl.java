package com.chmei.nzbservice.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.member.service.IMemberAssetsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 会员管理接口实现类
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点07分
 */
@Service("memberAssetsService")
public class MemberAssetsServiceImpl extends BaseServiceImpl implements IMemberAssetsService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(MemberAssetsServiceImpl.class);

    /**
     * 查询钱包收支明细
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryWalletMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("queryWalletMoneyInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询红包收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryRedPacketMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("queryRedPacketMoneyInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询广告费收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryAdvertisingMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("queryAdvertisingMoneyInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询积分收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryIntegralMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("queryIntegralMoneyInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询会员资产通用接口
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryMemberAssetsInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        Map<String, Object> params = input.getParams();
        try {
            String assetsType = (String) params.get("assetsType");
            if ("1001".equals(assetsType)) {
                queryWalletMoneyInfo(input, output);      // 钱包明细
            } else if ("1002".equals(assetsType)) {
                queryIntegralMoneyInfo(input, output);    // 积分明细
            } else if ("1003".equals(assetsType)){
                queryAdvertisingMoneyInfo(input, output); // 广告费明细
            } else if ("1004".equals(assetsType)){
                queryAdvertCoinInfo(input, output);       // 广告币明细
            } else if ("1005".equals(assetsType)){
//                queryAdvertCoinInfo(input, output); // 期权股明细
                // TODO 期权股明细查询
            }
        } catch (Exception e) {
            LOGGER.error("系统错误", e);
        }
    }

    /**
     * 查询资金状况列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryMemberMoneyTotalList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("queryMemberMoneyTotalList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询广告币收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void queryAdvertCoinInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("queryAdvertCoinInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 新增广告币兑换钱包余额信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    @Override
    public void saveAdvertCoinExchangeMoney(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberAssetsService");
        input.setMethod("saveAdvertCoinExchangeMoney");
        getNzbDataService().execute(input, output);
    }

}
