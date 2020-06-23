package com.chmei.nzbdata.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 会员资产（钱包，红包，广告费）接口
 *
 * @author zhangshixu
 * @since 2019年11月08日 14点41分
 */
public interface IMemberAssetsService {

    /**
     * 查询钱包收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryWalletMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询红包收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryRedPacketMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询广告费收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryAdvertisingMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询积分收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryIntegralMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询资金状况列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryMemberMoneyTotalList(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询广告币收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryAdvertCoinInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 新增广告币兑换钱包余额信息
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void saveAdvertCoinExchangeMoney(InputDTO input, OutputDTO output) throws NzbDataException;
}
