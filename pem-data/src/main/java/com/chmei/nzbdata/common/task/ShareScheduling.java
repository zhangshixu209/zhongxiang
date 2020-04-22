package com.chmei.nzbdata.common.task;

import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * log對象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareScheduling.class);

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
                        long endTime = starToDate.getTime() + (endDay * 24 * 3600 * 1000); // 结束时间
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
}
