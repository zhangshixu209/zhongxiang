package com.chmei.nzbdata.common.task;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxgoods.service.impl.ZxOrderInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请分红倒计时定时任务
 *
 * @author zhangshixu
 * @since 2020年4月21日 21点32分
 */
@EnableScheduling
@Component
public class ShareScheduling extends BaseServiceImpl {

    @Autowired
    private ZxOrderInfoServiceImpl zxOrderInfoServiceImpl;

    /**
     * log對象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareScheduling.class);
    private static final String WALLET_TYPE = "0"; // 钱包
    private static final String RED_PACKET_TYPE = "1"; // 红包
    private static final String ADVERSING_TYPE = "2"; // 广告钱

    @SuppressWarnings("unchecked")
    @Scheduled(cron = "0/10 * * * * ?")
    public void shareOutBonusTask(){
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("adShareOutBonusInfoDoneS", "S"); // 开始分红状态
            // 查询已申请的分红
            List<Map<String, Object>> list = getBaseDao().queryForList("ShareOutBonusMapper.findTaskByUserIdAndMarkS", params);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    Date startDate = (Date) map.get("adShareOutBonusInfoStart"); // 分红开始时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(startDate.toString())) {
                        ParsePosition pos = new ParsePosition(0);
                        Date starToDate = formatter.parse(startDate.toString(), pos);
                        int endDay = (int) map.get("adShareOutBonusInfoDayNum"); // 分红天数
                        long day = (long) endDay;
                        long endTime = starToDate.getTime() + (day * 24 * 3600 * 1000); // 结束时间
                        long nowTime = new Date().getTime(); // 当前时间
                        if (nowTime >= endTime) {
                            LOGGER.info("用户{ " + map.get("adShareOutBonusInfoUserId") +" }分红任务开始");
                            Map<String, Object> param = new HashMap<>();
                            param.put("memberAccount", map.get("adShareOutBonusInfoUserId")); // 被推荐人账户
                            Map<String, Object> userRun = (Map<String, Object>) getBaseDao().queryForObject(
                                    "MemberMapper.queryMemberDetail", param);
                            // 更新任务结束标志
                            Map<String, Object> record = new HashMap<>();
                            record.put("adShareOutBonusInfoDone", "D"); // 分红任务结束标识
                            record.put("adShareOutBonusInfoUserId", map.get("adShareOutBonusInfoUserId"));
                            int i = getBaseDao().update("ShareOutBonusMapper.updateAdShareOutBonusInfo", record);
                            if(i > 0){
                                // 每个分红周期结束后，申请分红金额的10%进入广告费钱包，5%进入红包。
                                Map<String, Object> user = new HashMap<>();
                                user.put("memberAccount", map.get("adShareOutBonusInfoUserId"));
                                user.put("advertisingFee", Double.valueOf(userRun.get("advertisingFee")+"") + Double.valueOf(map.get("adShareOutBonusInfoMoney")+"") * 0.1);
                                int ii = getBaseDao().update("MemberMapper.updateMemberBalance", user);
                                if(ii > 0){
                                    BigDecimal adShareOutBonusInfoMoney = (BigDecimal) map.get("adShareOutBonusInfoMoney");
                                    // 获取分红的钱数
                                    Map<String, Object> adRecord = new HashMap<>();
                                    adRecord.put("advertisingInfoId", getSequence());
                                    adRecord.put("advertisingInfoAddOrMinus", "+");
                                    adRecord.put("advertisingInfoUserId", map.get("adShareOutBonusInfoUserId"));
                                    adRecord.put("advertisingInfoMoney", adShareOutBonusInfoMoney.doubleValue() * 0.1);
                                    adRecord.put("advertisingInfoFrom", "广告分红");
                                    getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                                    // 将可用分红额度恢复
                                    Map<String, Object> bonusMoney = (Map<String, Object>) getBaseDao().queryForObject(
                                            "ShareOutBonusMapper.findAdShareOutBonusMoney", map);
                                    Map<String, Object> record_ = new HashMap<>();
                                    BigDecimal adShareOutBonusMoney = (BigDecimal) bonusMoney.get("adShareOutBonusMoney");
                                    record_.put("adShareOutBonusMoney", adShareOutBonusMoney.doubleValue() + adShareOutBonusInfoMoney.doubleValue());
                                    record_.put("adShareOutBonusUserId", map.get("adShareOutBonusInfoUserId"));
                                    getBaseDao().update("ShareOutBonusMapper.updateShareOutBonusInfo", record_);
                                    // 将交易表中的取消数据删除
                                    Map<String, Object> examplee = new HashMap<>();
                                    examplee.put("memberAccount", userRun.get("memberAccount"));
                                    examplee.put("dealMoneyMark", "0");
                                    getBaseDao().delete("AssetsTransferMapper.deleteDealInfo", examplee);
                                    LOGGER.info("用户{ " + map.get("adShareOutBonusInfoUserId") +" }分红任务结束");
                                }
                            }
                        }
                    }
                }
            } else {
                LOGGER.info("==============暂无分红任务处理！===============");
            }
        } catch (Exception e) {
            LOGGER.error("系统异常！", e);
        }
    }

    /**
     * 众享红包退回定时任务
     */
    @SuppressWarnings("unchecked")
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendBackRedPacketMoney(){
        Map<String, Object> params = new HashMap<>();
        try {
            // 查询红包剩余数量大于0的红包信息
            List<Map<String, Object>> list = getBaseDao().queryForList("RedPacketMapper.queryRedPacketStockInfo", params);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> mapp : list) {
                    Date startDate = (Date) mapp.get("redPacketDate"); // 红包创建时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(startDate.toString())) {
                        ParsePosition pos = new ParsePosition(0);
                        Date starToDate = formatter.parse(startDate.toString(), pos);
//                        long endTime = starToDate.getTime() + (24 * 3600 * 1000 * 3); // 结束时间
                        long endTime = starToDate.getTime() + (24 * 3600 * 1000); // 结束时间
                        long nowTime = new Date().getTime(); // 当前时间
                        if (nowTime >= endTime) {
                            Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
                                    "RedPacketMapper.queryRedPacketMoneyStock", mapp);
                            if (null != map_) {
                                LOGGER.info("==============红包过期退回任务开始================");
                                BigDecimal packetMoneyStock = (BigDecimal) map_.get("packetMoneyStock");
                                if (packetMoneyStock.doubleValue() > 0) {
                                    Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
                                            "MemberMapper.queryMemberBalanceDetail", mapp);
                                    if (null != item) {
                                        boolean flag = false;
                                        String walletBalance = (String) item.get("walletBalance"); // 钱包余额
                                        String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
                                        String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
                                        Map<String, Object> map = new HashMap<>();
                                        String redPacketType = (String) mapp.get("redPacketType");
                                        // 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
                                        switch (redPacketType) {
                                            case WALLET_TYPE: // 钱包退回
                                                double walletAmount = Double.valueOf(walletBalance) + packetMoneyStock.doubleValue();
                                                map.put("walletBalance", walletAmount < 0 ? 0 : walletAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (i <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("RedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 钱包退回金额记录:
                                                    Map<String, Object> walletMoneyInfo = new HashMap<>();
                                                    walletMoneyInfo.put("walletInfoId", getSequence());
                                                    walletMoneyInfo.put("walletInfoAddOrMinus", "+");
                                                    walletMoneyInfo.put("walletInfoUserId", mapp.get("memberAccount"));
                                                    walletMoneyInfo.put("walletInfoMoney", packetMoneyStock);
                                                    walletMoneyInfo.put("walletInfoFrom", "众享广告红包过期退回");
                                                    getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
                                                }
                                                break;
                                            case RED_PACKET_TYPE: // 红包退回
                                                double hbAmount = Double.valueOf(redEnveBalance) + packetMoneyStock.doubleValue();
                                                map.put("redEnveBalance", hbAmount < 0 ? 0 : hbAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int j = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (j <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("RedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 红包退回钱包记录
                                                    Map<String, Object> redRecord = new HashMap<>();
                                                    redRecord.put("redPacketInfoId", getSequence());
                                                    redRecord.put("redPacketInfoAddOrMinus", "+");
                                                    redRecord.put("redPacketInfoUserId", mapp.get("memberAccount"));
                                                    redRecord.put("redPacketInfoMoney", packetMoneyStock);
                                                    redRecord.put("redPacketInfoFrom", "众享广告红包过期退回");
                                                    getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
                                                }
                                                break;
                                            case ADVERSING_TYPE: // 广告钱包退回
                                                double advertisingAmount = Double.valueOf(advertisingFee) + packetMoneyStock.doubleValue();
                                                map.put("advertisingFee", advertisingAmount < 0 ? 0 : advertisingAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int k = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (k <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("RedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 广告钱包退回记录
                                                    Map<String, Object> adRecord = new HashMap<>();
                                                    adRecord.put("advertisingInfoId", getSequence());
                                                    adRecord.put("advertisingInfoAddOrMinus", "+");
                                                    adRecord.put("advertisingInfoUserId", mapp.get("memberAccount"));
                                                    adRecord.put("advertisingInfoMoney", packetMoneyStock);
                                                    adRecord.put("advertisingInfoFrom", "众享广告红包过期退回");
                                                    getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                        // TODO 调用计算提成及股权接口
                                        if (flag) {
                                            LOGGER.error("红包退回失败!");
                                        }
                                        LOGGER.info("==============红包过期退回任务结束================");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("系统异常！", e);
        }
    }

    /**
     * 视频红包退回定时任务
     */
    @SuppressWarnings("unchecked")
    @Scheduled(cron = "0 5 0 * * ?")
    public void sendBackVideoRedPacketMoney() {
        Map<String, Object> params = new HashMap<>();
        try {
            // 查询红包剩余数量大于0的红包信息
            List<Map<String, Object>> list = getBaseDao().queryForList("VideoRedPacketMapper.queryRedPacketStockInfo", params);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> mapp : list) {
                    Date startDate = (Date) mapp.get("redPacketDate"); // 红包创建时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(startDate.toString())) {
                        ParsePosition pos = new ParsePosition(0);
                        Date starToDate = formatter.parse(startDate.toString(), pos);
//                        long endTime = starToDate.getTime() + (24 * 3600 * 1000 * 3); // 结束时间
                        long endTime = starToDate.getTime() + (24 * 3600 * 1000); // 结束时间
                        long nowTime = new Date().getTime(); // 当前时间
                        if (nowTime >= endTime) {
                            Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
                                    "VideoRedPacketMapper.queryRedPacketMoneyStock", mapp);
                            if (null != map_) {
                                LOGGER.info("==============红包过期退回任务开始================");
                                BigDecimal packetMoneyStock = (BigDecimal) map_.get("packetMoneyStock");
                                if (packetMoneyStock.doubleValue() > 0) {
                                    Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
                                            "MemberMapper.queryMemberBalanceDetail", mapp);
                                    if (null != item) {
                                        boolean flag = false;
                                        String walletBalance = (String) item.get("walletBalance"); // 钱包余额
                                        String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
                                        String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
                                        Map<String, Object> map = new HashMap<>();
                                        String redPacketType = (String) mapp.get("redPacketType");
                                        // 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
                                        switch (redPacketType) {
                                            case WALLET_TYPE: // 钱包退回
                                                double walletAmount = Double.valueOf(walletBalance) + packetMoneyStock.doubleValue();
                                                map.put("walletBalance", walletAmount < 0 ? 0 : walletAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (i <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("VideoRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 钱包退回金额记录:
                                                    Map<String, Object> walletMoneyInfo = new HashMap<>();
                                                    walletMoneyInfo.put("walletInfoId", getSequence());
                                                    walletMoneyInfo.put("walletInfoAddOrMinus", "+");
                                                    walletMoneyInfo.put("walletInfoUserId", mapp.get("memberAccount"));
                                                    walletMoneyInfo.put("walletInfoMoney", packetMoneyStock);
                                                    walletMoneyInfo.put("walletInfoFrom", "视频广告红包过期退回");
                                                    getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
                                                }
                                                break;
                                            case RED_PACKET_TYPE: // 红包退回
                                                double hbAmount = Double.valueOf(redEnveBalance) + packetMoneyStock.doubleValue();
                                                map.put("redEnveBalance", hbAmount < 0 ? 0 : hbAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int j = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (j <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("VideoRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 红包退回钱包记录
                                                    Map<String, Object> redRecord = new HashMap<>();
                                                    redRecord.put("redPacketInfoId", getSequence());
                                                    redRecord.put("redPacketInfoAddOrMinus", "+");
                                                    redRecord.put("redPacketInfoUserId", mapp.get("memberAccount"));
                                                    redRecord.put("redPacketInfoMoney", packetMoneyStock);
                                                    redRecord.put("redPacketInfoFrom", "视频广告红包过期退回");
                                                    getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
                                                }
                                                break;
                                            case ADVERSING_TYPE: // 广告钱包退回
                                                double advertisingAmount = Double.valueOf(advertisingFee) + packetMoneyStock.doubleValue();
                                                map.put("advertisingFee", advertisingAmount < 0 ? 0 : advertisingAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int k = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (k <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("VideoRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 广告钱包退回记录
                                                    Map<String, Object> adRecord = new HashMap<>();
                                                    adRecord.put("advertisingInfoId", getSequence());
                                                    adRecord.put("advertisingInfoAddOrMinus", "+");
                                                    adRecord.put("advertisingInfoUserId", mapp.get("memberAccount"));
                                                    adRecord.put("advertisingInfoMoney", packetMoneyStock);
                                                    adRecord.put("advertisingInfoFrom", "视频广告红包过期退回");
                                                    getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                        // TODO 调用计算提成及股权接口
                                        if (flag) {
                                            LOGGER.error("红包退回失败!");
                                        }
                                        LOGGER.info("==============红包过期退回任务结束================");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("系统异常！", e);
        }
    }

    /**
     * 链接红包退回定时任务
     */
    @SuppressWarnings("unchecked")
    @Scheduled(cron = "0 10 0 * * ?")
    public void sendBackLinkRedPacketMoney() {
        Map<String, Object> params = new HashMap<>();
        try {
            // 查询红包剩余数量大于0的红包信息
            List<Map<String, Object>> list = getBaseDao().queryForList("LinkRedPacketMapper.queryRedPacketStockInfo", params);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> mapp : list) {
                    Date startDate = (Date) mapp.get("redPacketDate"); // 红包创建时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(startDate.toString())) {
                        ParsePosition pos = new ParsePosition(0);
                        Date starToDate = formatter.parse(startDate.toString(), pos);
//                        long endTime = starToDate.getTime() + (24 * 3600 * 1000 * 3); // 结束时间 3天
                        long endTime = starToDate.getTime() + (24 * 3600 * 1000); // 结束时间 1天
                        long nowTime = new Date().getTime(); // 当前时间
                        if (nowTime >= endTime) {
                            Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
                                    "LinkRedPacketMapper.queryRedPacketMoneyStock", mapp);
                            if (null != map_) {
                                LOGGER.info("==============红包过期退回任务开始================");
                                BigDecimal packetMoneyStock = (BigDecimal) map_.get("packetMoneyStock");
                                if (packetMoneyStock.doubleValue() > 0) {
                                    Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
                                            "MemberMapper.queryMemberBalanceDetail", mapp);
                                    if (null != item) {
                                        boolean flag = false;
                                        String walletBalance = (String) item.get("walletBalance"); // 钱包余额
                                        String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
                                        String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
                                        Map<String, Object> map = new HashMap<>();
                                        String redPacketType = (String) mapp.get("redPacketType");
                                        // 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
                                        switch (redPacketType) {
                                            case WALLET_TYPE: // 钱包退回
                                                double walletAmount = Double.valueOf(walletBalance) + packetMoneyStock.doubleValue();
                                                map.put("walletBalance", walletAmount < 0 ? 0 : walletAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (i <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("LinkRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 钱包退回金额记录:
                                                    Map<String, Object> walletMoneyInfo = new HashMap<>();
                                                    walletMoneyInfo.put("walletInfoId", getSequence());
                                                    walletMoneyInfo.put("walletInfoAddOrMinus", "+");
                                                    walletMoneyInfo.put("walletInfoUserId", mapp.get("memberAccount"));
                                                    walletMoneyInfo.put("walletInfoMoney", packetMoneyStock);
                                                    walletMoneyInfo.put("walletInfoFrom", "链接广告红包过期退回");
                                                    getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
                                                }
                                                break;
                                            case RED_PACKET_TYPE: // 红包退回
                                                double hbAmount = Double.valueOf(redEnveBalance) + packetMoneyStock.doubleValue();
                                                map.put("redEnveBalance", hbAmount < 0 ? 0 : hbAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int j = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (j <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("LinkRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 红包退回钱包记录
                                                    Map<String, Object> redRecord = new HashMap<>();
                                                    redRecord.put("redPacketInfoId", getSequence());
                                                    redRecord.put("redPacketInfoAddOrMinus", "+");
                                                    redRecord.put("redPacketInfoUserId", mapp.get("memberAccount"));
                                                    redRecord.put("redPacketInfoMoney", packetMoneyStock);
                                                    redRecord.put("redPacketInfoFrom", "链接广告红包过期退回");
                                                    getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
                                                }
                                                break;
                                            case ADVERSING_TYPE: // 广告钱包退回
                                                double advertisingAmount = Double.valueOf(advertisingFee) + packetMoneyStock.doubleValue();
                                                map.put("advertisingFee", advertisingAmount < 0 ? 0 : advertisingAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int k = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (k <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("LinkRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 广告钱包退回记录
                                                    Map<String, Object> adRecord = new HashMap<>();
                                                    adRecord.put("advertisingInfoId", getSequence());
                                                    adRecord.put("advertisingInfoAddOrMinus", "+");
                                                    adRecord.put("advertisingInfoUserId", mapp.get("memberAccount"));
                                                    adRecord.put("advertisingInfoMoney", packetMoneyStock);
                                                    adRecord.put("advertisingInfoFrom", "链接广告红包过期退回");
                                                    getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                        // TODO 调用计算提成及股权接口
                                        if (flag) {
                                            LOGGER.error("红包退回失败!");
                                        }
                                        LOGGER.info("==============红包过期退回任务结束================");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("系统异常！", e);
        }
    }

    /**
     * 图文红包退回定时任务
     */
    @SuppressWarnings("unchecked")
    @Scheduled(cron = "0 15 0 * * ?")
    public void sendBackImgRedPacketMoney() {
        Map<String, Object> params = new HashMap<>();
        try {
            // 查询红包剩余数量大于0的红包信息
            List<Map<String, Object>> list = getBaseDao().queryForList("ImgRedPacketMapper.queryRedPacketStockInfo", params);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> mapp : list) {
                    Date startDate = (Date) mapp.get("redPacketDate"); // 红包创建时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(startDate.toString())) {
                        ParsePosition pos = new ParsePosition(0);
                        Date starToDate = formatter.parse(startDate.toString(), pos);
//                        long endTime = starToDate.getTime() + (24 * 3600 * 1000 * 3); // 结束时间 3天
                        long endTime = starToDate.getTime() + (24 * 3600 * 1000); // 结束时间 1天
                        long nowTime = new Date().getTime(); // 当前时间
                        if (nowTime >= endTime) {
                            Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
                                    "ImgRedPacketMapper.queryRedPacketMoneyStock", mapp);
                            if (null != map_) {
                                LOGGER.info("==============红包过期退回任务开始================");
                                BigDecimal packetMoneyStock = (BigDecimal) map_.get("packetMoneyStock");
                                if (packetMoneyStock.doubleValue() > 0) {
                                    Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
                                            "MemberMapper.queryMemberBalanceDetail", mapp);
                                    if (null != item) {
                                        boolean flag = false;
                                        String walletBalance = (String) item.get("walletBalance"); // 钱包余额
                                        String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
                                        String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
                                        Map<String, Object> map = new HashMap<>();
                                        String redPacketType = (String) mapp.get("redPacketType");
                                        // 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
                                        switch (redPacketType) {
                                            case WALLET_TYPE: // 钱包退回
                                                double walletAmount = Double.valueOf(walletBalance) + packetMoneyStock.doubleValue();
                                                map.put("walletBalance", walletAmount < 0 ? 0 : walletAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (i <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("ImgRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 钱包退回金额记录:
                                                    Map<String, Object> walletMoneyInfo = new HashMap<>();
                                                    walletMoneyInfo.put("walletInfoId", getSequence());
                                                    walletMoneyInfo.put("walletInfoAddOrMinus", "+");
                                                    walletMoneyInfo.put("walletInfoUserId", mapp.get("memberAccount"));
                                                    walletMoneyInfo.put("walletInfoMoney", packetMoneyStock);
                                                    walletMoneyInfo.put("walletInfoFrom", "图文广告红包过期退回");
                                                    getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
                                                }
                                                break;
                                            case RED_PACKET_TYPE: // 红包退回
                                                double hbAmount = Double.valueOf(redEnveBalance) + packetMoneyStock.doubleValue();
                                                map.put("redEnveBalance", hbAmount < 0 ? 0 : hbAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int j = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (j <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("ImgRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 红包退回钱包记录
                                                    Map<String, Object> redRecord = new HashMap<>();
                                                    redRecord.put("redPacketInfoId", getSequence());
                                                    redRecord.put("redPacketInfoAddOrMinus", "+");
                                                    redRecord.put("redPacketInfoUserId", mapp.get("memberAccount"));
                                                    redRecord.put("redPacketInfoMoney", packetMoneyStock);
                                                    redRecord.put("redPacketInfoFrom", "图文广告红包过期退回");
                                                    getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
                                                }
                                                break;
                                            case ADVERSING_TYPE: // 广告钱包退回
                                                double advertisingAmount = Double.valueOf(advertisingFee) + packetMoneyStock.doubleValue();
                                                map.put("advertisingFee", advertisingAmount < 0 ? 0 : advertisingAmount);
                                                map.put("memberAccount", mapp.get("memberAccount")); // 会员账户
                                                int k = getBaseDao().update("MemberMapper.updateMemberBalance", map);
                                                if (k <= 0) {
                                                    flag = true;
                                                } else {
                                                    // 删除过期的红包信息
                                                    getBaseDao().delete("ImgRedPacketMapper.delRedPacketMoneyStock", mapp);
                                                    // 广告钱包退回记录
                                                    Map<String, Object> adRecord = new HashMap<>();
                                                    adRecord.put("advertisingInfoId", getSequence());
                                                    adRecord.put("advertisingInfoAddOrMinus", "+");
                                                    adRecord.put("advertisingInfoUserId", mapp.get("memberAccount"));
                                                    adRecord.put("advertisingInfoMoney", packetMoneyStock);
                                                    adRecord.put("advertisingInfoFrom", "图文广告红包过期退回");
                                                    getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                        // TODO 调用计算提成及股权接口
                                        if (flag) {
                                            LOGGER.error("红包退回失败!");
                                        }
                                        LOGGER.info("==============红包过期退回任务结束================");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("系统异常！", e);
        }
    }

    /**
     * 确认发货定时任务
     */
    @Scheduled(cron = "0 20 0 * * ?")
    public void userConfirmReceiptTask(){
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("orderStatus", "1002"); // 查询已发货的订单列表
            List<Map<String, Object>> list = getBaseDao().queryForList("OrderInfoMapper.queryOrderInfoList", params);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    Date startDate = (Date) map.get("crtTime"); // 订单创建时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(startDate.toString())) {
                        ParsePosition pos = new ParsePosition(0);
                        Date starToDate = formatter.parse(startDate.toString(), pos);
                        long orderTime = starToDate.getTime(); // 订单创建时间
                        long nowTime = new Date().getTime(); // 当前时间
                        long day = nowTime - orderTime;
                        long endTime = day / 86400000;
                        if (endTime > 30) {
                            LOGGER.info("==============="+endTime);
                            InputDTO inputDTO = new InputDTO();
                            OutputDTO outputDTO = new OutputDTO();
                            LOGGER.info("============执行确认收货定时任务开始============");
                            inputDTO.getParams().put("id", map.get("id"));
                            // 调用确认收货接口
                            zxOrderInfoServiceImpl.userConfirmReceipt(inputDTO, outputDTO);
                            LOGGER.info("============执行确认收货定时任务结束============");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("确认收货定时任务系统异常！", e);
        }
    }

}
