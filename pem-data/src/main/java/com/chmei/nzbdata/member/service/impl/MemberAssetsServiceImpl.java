package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IMemberAssetsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员资金（钱包，红包，广告费）接口实现类
 *
 * @author zhangshixu
 * @since 2019年11月08日 15点10分
 */
@Service("memberAssetsService")
public class MemberAssetsServiceImpl extends BaseServiceImpl implements IMemberAssetsService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(MemberAssetsServiceImpl.class);

    /**
     * 查询钱包收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryWalletMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("WalletMoneyInfoMapper.queryWalletMoneyInfoCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("WalletMoneyInfoMapper.queryWalletMoneyInfoList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

    /**
     * 查询红包收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryRedPacketMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("RedRecordMoneyInfoMapper.queryRedPacketMoneyInfoCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("RedRecordMoneyInfoMapper.queryRedPacketMoneyInfoList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

    /**
     * 查询广告费收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryAdvertisingMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("AdvertisingMoneyInfoMapper.queryAdvertisingMoneyInfoCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("AdvertisingMoneyInfoMapper.queryAdvertisingMoneyInfoList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

}
