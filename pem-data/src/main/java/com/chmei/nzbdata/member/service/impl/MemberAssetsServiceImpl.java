package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IMemberAssetsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 查询积分收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryIntegralMoneyInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("IntegralMoneyInfoMapper.queryIntegralMoneyCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("IntegralMoneyInfoMapper.queryIntegralMoneyList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

    /**
     * 查询资金状况列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryMemberMoneyTotalList(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        List<Map<String, Object>> listAll = new ArrayList<>();
        try {
            List<Map<String, Object>> list = getBaseDao().queryForList("MemberAccountMapper.queryMemberMoneyTotalList", params);
            if(null != list && list.size() > 0){
                Map<String, Object> map = list.get(0);
                String redPacket = (String) map.get("redPacket");
                String redImgPacket = (String) map.get("redImgPacket");
                String redLinkPacket = (String) map.get("redLinkPacket");
                String redVideoPacket = (String) map.get("redVideoPacket");
                Double allPackets = (Double.valueOf(redPacket) + Double.valueOf(redImgPacket) +
                        Double.valueOf(redLinkPacket) +Double.valueOf(redVideoPacket));
                String walletAmount = (String) map.get("walletAmount");
                // map.put("walletAmount", Double.valueOf(walletAmount) + allPackets); // 未抢完红包
                map.put("allPackets", allPackets);

                Double balance = Double.valueOf(map.get("walletAmount")+"") + Double.valueOf(map.get("activateMoney")+"") +
                        Double.valueOf(map.get("applyMoney")+"") +Double.valueOf(map.get("groupMoney")+"") +
                        Double.valueOf(map.get("relReflectMoney")+"") + Double.valueOf(map.get("txsxMoney")+"") +
                        Double.valueOf(map.get("reflectMoney")+"") + allPackets;
                balance = balance - Double.valueOf(map.get("totalMoney")+"");
                if (balance == 0) {
                    map.put("type", "平衡");
                } else {
                    map.put("type", "异常");
                }
                map.put("balance", balance);
                listAll.add(map);
            }
            output.setItems(listAll);
            output.setTotal(listAll.size());
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

}
