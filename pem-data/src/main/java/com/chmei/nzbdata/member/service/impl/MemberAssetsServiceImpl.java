package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IMemberAssetsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

                double balance = Double.valueOf(map.get("walletAmount")+"") + Double.valueOf(map.get("activateMoney")+"") +
                        Double.valueOf(map.get("applyMoney")+"") +Double.valueOf(map.get("groupMoney")+"") +
                        Double.valueOf(map.get("relReflectMoney")+"") + Double.valueOf(map.get("txsxMoney")+"") +
                        Double.valueOf(map.get("reflectMoney")+"") + allPackets + Double.valueOf(map.get("systemMoney")+"") +
                        Double.valueOf(map.get("seckillMoney")+"");
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

    /**
     * 查询广告币收支明细
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryAdvertCoinInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("AdvertCoinMapper.queryAdvertCoinCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("AdvertCoinMapper.queryAdvertCoinList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

    /**
     * 新增广告币兑换钱包余额信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void saveAdvertCoinExchangeMoney(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            // 查询当前用户钱包余额，广告币余额
            Map<String, Object> item2 = (Map<String, Object>) getBaseDao().
                    queryForObject("MemberMapper.queryMemberBalanceDetail", params);
            // 兑换钱包余额消耗广告币
            Double exchangeMoney = Double.valueOf((String) params.get("exchangeMoney"));
            Double exchangeAdvertCoin = Double.valueOf((String) params.get("exchangeAdvertCoin"));
            // 我的钱包余额和广告币
            Double advertCoin = Double.valueOf((String) item2.get("advertCoin"));
            Double walletBalance = Double.valueOf((String) item2.get("walletBalance"));
            if (exchangeAdvertCoin > advertCoin) {
                output.setCode("-1");
                output.setMsg("广告币不足！");
                return;
            }
            Map<String, Object> user2 = new HashMap<>();
            user2.put("memberAccount", params.get("memberAccount"));
            user2.put("advertCoin", advertCoin - exchangeAdvertCoin);  // 减去兑换的广告币
            user2.put("walletBalance", walletBalance + exchangeMoney); // 增加广告币兑换的钱包余额
            int i = getBaseDao().update("MemberMapper.updateMemberBalance", user2);
            if (i > 0) {
                // 减去兑换的广告币钱包记录
                Map<String, Object> advertCoinMap = new HashMap<>();
                advertCoinMap.put("advertCoinId", getSequence());
                advertCoinMap.put("advertCoinAddOrMinus", "-");
                advertCoinMap.put("advertCoinUserId", params.get("memberAccount"));
                advertCoinMap.put("advertCoinMoney", exchangeAdvertCoin);
                advertCoinMap.put("advertCoinFrom", "广告币兑换");
                getBaseDao().insert("AdvertCoinMapper.saveAdvertCoinInfo", advertCoinMap);
                // 增加广告币兑换的钱包余额记录
                Map<String, Object> walletMoneyInfo = new HashMap<>();
                walletMoneyInfo.put("walletInfoId", getSequence());
                walletMoneyInfo.put("walletInfoAddOrMinus", "+");
                walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
                walletMoneyInfo.put("walletInfoMoney", exchangeMoney);
                walletMoneyInfo.put("walletInfoFrom", "广告币兑换");
                getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
                Map<String, Object> map = new HashMap<>();
                map.put("advertCoin", user2.get("advertCoin")+"");
                output.setCode("0");
                output.setMsg("兑换成功");
                output.setItem(map);
            } else {
                output.setCode("-1");
                output.setMsg("兑换失败！");
            }
        } catch (Exception e) {
            LOGGER.error("兑换失败" + e);
        }
    }

}
