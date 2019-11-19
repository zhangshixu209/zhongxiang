package com.chmei.nzbservice.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 会员资产（钱包，红包，广告费）接口
 *
 * @author zhangshixu
 * @since 2019年11月08日 15点14分
 */
public interface IMemberAssetsService {

    /**
     * 查询钱包收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryWalletMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询红包收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryRedPacketMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询广告费收支明细
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryAdvertisingMoneyInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询会员资产通用接口
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 异常信息
     */
    void queryMemberAssetsInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
