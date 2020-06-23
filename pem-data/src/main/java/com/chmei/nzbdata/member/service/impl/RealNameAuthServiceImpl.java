package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IRealNameAuthService;
import com.chmei.nzbdata.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员管理接口实现类
 *
 * @author zhangshixu
 * @since 2019年10月24日 13点42分
 */
@Service("realNameAuthService")
public class RealNameAuthServiceImpl extends BaseServiceImpl implements IRealNameAuthService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(RealNameAuthServiceImpl.class);

    /**
     * 新增实名认证信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void saveRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            String cardNum = (String) params.get("cardNum");
            if (!StringUtil.isEmpty(cardNum)) {
                int check = getBaseDao().getTotalCount("RealNameAuthMapper.checkCardNum", params);
                if (check > 0) {
                    output.setCode("-1");
                    output.setMsg("此证件已被使用");
                    return;
                }
                Map<String, Object> map = StringUtil.getCarInfo(cardNum);
                params.put("sex", map.get("sex")); // 截取身份证性别
                params.put("age", map.get("age")); // 截取身份证年龄
            }
            params.put("id", getSequence()); // 获取id
            int count = getBaseDao().insert("RealNameAuthMapper.saveRealNameAuthInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
            checkMyShare(params); // 校验是否需要赠送广告费
            output.setCode("0");
            output.setMsg("保存成功");
        } catch (Exception ex) {
            LOGGER.error("新增失败", ex);
        }
    }

    /**
     * 修改实名认证信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            String cardNum = (String) params.get("cardNum");
            if (!StringUtil.isEmpty(cardNum)) {
                int check = getBaseDao().getTotalCount("RealNameAuthMapper.checkCardNum", params);
                if (check > 0) {
                    output.setCode("-1");
                    output.setMsg("此证件已被使用");
                    return;
                }
                Map<String, Object> map = StringUtil.getCarInfo(cardNum);
                params.put("sex", map.get("sex")); // 截取身份证性别
                params.put("age", map.get("age")); // 截取身份证年龄
            }
            int total = getBaseDao().update("RealNameAuthMapper.updateRealNameAuthInfo", params);
            if (total < 1) {
                output.setCode("-1");
                output.setMsg("保存失败");
            }
            checkMyShare(params); // 校验是否需要赠送广告费
            output.setCode("0");
            output.setMsg("保存成功");
            output.setTotal(total);
        } catch (Exception ex) {
            LOGGER.error("查询失败" + ex);
        }
    }

    /**
     * 查询实名认证信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryRealNameInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        LOGGER.info("MemberServiceImpl.queryRealNameInfo, input::" + input.getParams().toString());
        Map<String, Object> params = input.getParams();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> item = (Map<String, Object>) getBaseDao().
                    queryForObject("RealNameAuthMapper.queryRealNameInfo", params);
            output.setItem(item);
        } catch (Exception ex) {
            LOGGER.error("查询失败: " + ex);
        }
    }

    /**
     * 校验是否需要增加注册赠送和分享赠送
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    private int checkMyShare(Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        Map<String, Object> item = (Map<String, Object>) getBaseDao().
                queryForObject("RealNameAuthMapper.queryRealNameInfo", params);
        if (null != item) {
            String cardNum = (String) item.get("cardNum");
            if (StringUtil.isNotEmpty(cardNum)) {
                // 通过被推荐人账号查询推荐人信息
                params.put("coverMemberAccount", params.get("memberAccount"));
                Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
                        "MemberMapper.queryMyShareMember", params);
                if (null != map_) { // 存在推荐关系，继续下一步
                    params.put("advertisingInfoFrom", "注册赠送");
                    int i = getBaseDao().getTotalCount("AdvertisingMoneyInfoMapper.queryAdvertisingMoney", params);
                    if (i == 0) {
                        // 查询注册人余额
                        Map<String, Object> item_ = (Map<String, Object>) getBaseDao().
                                queryForObject("MemberMapper.queryMemberBalanceDetail", params);
                        Double adMoney = 10.00;
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("memberAccount", params.get("memberAccount"));
                        user1.put("advertisingFee", Double.valueOf(item_.get("advertisingFee")+"") + adMoney);
                        getBaseDao().update("MemberMapper.updateMemberBalance", user1);
                        // 注册赠送记录:
                        Map<String, Object> adRecord = new HashMap<>();
                        adRecord.put("advertisingInfoId", getSequence());
                        adRecord.put("advertisingInfoAddOrMinus", "+");
                        adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
                        adRecord.put("advertisingInfoMoney", adMoney);
                        adRecord.put("advertisingInfoFrom", "注册赠送");
                        getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                        Double adCoin = 5.00; // 广告币
                        // 查询分享人余额
                        Map<String, Object> item2 = (Map<String, Object>) getBaseDao().
                                queryForObject("MemberMapper.queryMemberBalanceDetail", map_);
                        Map<String, Object> user2 = new HashMap<>();
                        user2.put("memberAccount", map_.get("memberAccount"));
//                        user2.put("advertisingFee", Double.valueOf(item2.get("advertisingFee")+"") + adMoney);
                        user2.put("advertCoin", Double.valueOf(item2.get("advertCoin")+"") + adCoin);
                        getBaseDao().update("MemberMapper.updateMemberBalance", user2);
                        // ==========================2020年6月22日 15点10分=======================================
                        // 分享赠送记录:
                        // 广告币钱包记录
                        Map<String, Object> advertCoinMap = new HashMap<>();
                        advertCoinMap.put("advertCoinId", getSequence());
                        advertCoinMap.put("advertCoinAddOrMinus", "+");
                        advertCoinMap.put("advertCoinUserId", map_.get("memberAccount"));
                        advertCoinMap.put("advertCoinMoney", adCoin);
                        advertCoinMap.put("advertCoinFrom", "分享赠送");
                        getBaseDao().insert("AdvertCoinMapper.saveAdvertCoin", advertCoinMap);
                        // ==========================2020年6月22日 15点10分=======================================
                        return 1;
                    }
                } else {
                    // 不存在推荐关系，实名认证后也赠送10广告费
                    params.put("advertisingInfoFrom", "注册赠送");
                    int i = getBaseDao().getTotalCount("AdvertisingMoneyInfoMapper.queryAdvertisingMoney", params);
                    if (i == 0) {
                        // 查询注册人余额
                        Map<String, Object> item_ = (Map<String, Object>) getBaseDao().
                                queryForObject("MemberMapper.queryMemberBalanceDetail", params);
                        Double adMoney = 10.00;
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("memberAccount", params.get("memberAccount"));
                        user1.put("advertisingFee", Double.valueOf(item_.get("advertisingFee")+"") + adMoney);
                        getBaseDao().update("MemberMapper.updateMemberBalance", user1);
                        // 注册赠送记录:
                        Map<String, Object> adRecord = new HashMap<>();
                        adRecord.put("advertisingInfoId", getSequence());
                        adRecord.put("advertisingInfoAddOrMinus", "+");
                        adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
                        adRecord.put("advertisingInfoMoney", adMoney);
                        adRecord.put("advertisingInfoFrom", "注册赠送");
                        getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                    }
                }
            }
        }
        return 0;
    }

}
