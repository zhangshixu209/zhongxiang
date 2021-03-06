package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.ITransferAccountsService;
import com.chmei.nzbdata.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众享好友转账接口实现类
 *
 * @author zhangshixu
 * @since 2019年11月12日 17点52分
 */
@Service("transferAccountsService")
public class TransferAccountsServiceImpl extends BaseServiceImpl implements ITransferAccountsService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(TransferAccountsServiceImpl.class);

    /**
     * 众享好友转账
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void memberTransferAccounts(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            String memberAccount = (String) params.get("memberAccount"); // 当前用户账号
            String friendMemberAccount = (String) params.get("friendMemberAccount"); // 好友账号
            double transferMoney = (double) params.get("transferMoney"); // 转账金额
            String accountType = (String) params.get("accountType"); // 类型 0 ：钱包 1:广告钱包
            Map<String, Object> result = new HashMap<>();
            result.put("memberAccount", friendMemberAccount);
            // 查询当前用户余额
            Map<String, Object> item = (Map<String, Object>) getBaseDao().
                    queryForObject("MemberMapper.queryMemberDetail", params);
            // 查询好友用户信息
            Map<String, Object> item2 = (Map<String, Object>) getBaseDao().
                    queryForObject("MemberMapper.queryMemberDetail", result);
            if (null != item && null != item2) {
                String walletBalance = (String) item.get("walletBalance");   // 钱包余额
                String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
                // 判断金额是否充足
                if("0".equals(accountType)){
                    if(Double.valueOf(walletBalance) - transferMoney < 0){
                        output.setCode("-1");
                        output.setMsg("钱包余额不足!");
                        return;
                    }
                } else {
                    if(Double.valueOf(advertisingFee) - transferMoney < 0){
                        output.setCode("-1");
                        output.setMsg("广告费余额不足!");
                        return;
                    }
                }
                Map<String, Object> userInfo1 = new HashMap<>();
                //第一步减去转账人余额
                userInfo1.put("id", item.get("id")); // 会员ID
                if("0".equals(accountType)){
                    userInfo1.put("walletBalance", Double.valueOf(walletBalance) - transferMoney);
                } else {
                    userInfo1.put("advertisingFee", Double.valueOf(advertisingFee) - transferMoney);
                }
                // 更新用户余额
                getBaseDao().update("MemberMapper.saveMemberRechargeInfo", userInfo1);
                // 1 根据用户账户查询拥有的众享好友
                Map<String, Object> friend = new HashMap<>();
                friend.put("zxFriendUserId", memberAccount);
                friend.put("zxFriendFriendId", friendMemberAccount);
                List<Map<String, Object>> friendList = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendList", friend);
                String friendRemark = ""; // 好友备注
                if (null != friendList && friendList.size() > 0) {
                    friendRemark = (String) friendList.get(0).get("zxFriendRemark");
                    if (StringUtil.isEmpty(friendRemark)) {
                        friendRemark = (String) item2.get("nickname");
                    }
                }
                // 1 根据用户账户查询拥有的众享好友
                Map<String, Object> mine = new HashMap<>();
                mine.put("zxFriendUserId", friendMemberAccount);
                mine.put("zxFriendFriendId", memberAccount);
                List<Map<String, Object>> mineList = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendList", mine);
                String mineRemark = ""; // 好友对我的备注
                if (null != mineList && mineList.size() > 0) {
                    mineRemark = (String) mineList.get(0).get("zxFriendRemark");
                    if (StringUtil.isEmpty(mineRemark)) {
                        mineRemark = (String) item.get("nickname");
                    }
                }
                //增加记录
                if("0".equals(accountType)){
                    addMessage(memberAccount,transferMoney,mineRemark,friendRemark,"0",1);
                } else {
                    addMessage(memberAccount,transferMoney,mineRemark,friendRemark,"1",1);
                }
                Map<String, Object> userInfo2 = new HashMap<>();
                //第二步增加收账人余额
                userInfo2.put("id", item2.get("id")); // 会员ID
                if("0".equals(accountType)){
                    userInfo2.put("walletBalance", Double.valueOf((String) item2.get("walletBalance")) + transferMoney);
                } else {
                    userInfo2.put("advertisingFee", (Double.valueOf((String) item2.get("advertisingFee")) + transferMoney));
                }
                // 更新用户余额
                getBaseDao().update("MemberMapper.saveMemberRechargeInfo", userInfo2);

                //增加记录
                if("0".equals(accountType)){
                    addMessage(friendMemberAccount,transferMoney,mineRemark,friendRemark,"0",0);
                } else {
                    addMessage(friendMemberAccount,transferMoney,mineRemark,friendRemark,"1",0);
                }
                output.setCode("0");
                output.setMsg("转账成功!");
            }

        } catch (Exception e) {
            LOGGER.error("系统错误", e);
        }
    }

    //type 扣款类型  add 0增加 1减少
    private void addMessage(String memberAccount,Double money,String userName, String toName,String type,Integer add){
        if("0".equals(type)){
            // 钱包扣除金额记录:
            Map<String, Object> walletMoneyInfo = new HashMap<>();
            walletMoneyInfo.put("walletInfoId", getSequence());
            walletMoneyInfo.put("walletInfoAddOrMinus", add == 0 ? "+" : "-");
            walletMoneyInfo.put("walletInfoUserId", memberAccount);
            walletMoneyInfo.put("walletInfoMoney", money);
            walletMoneyInfo.put("walletInfoFrom", add == 0 ? "收到"+userName+"转账" : "转账给"+toName);
            getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);

        } else {
            // 广告钱包记录
            Map<String, Object> adRecord = new HashMap<>();
            adRecord.put("advertisingInfoId", getSequence());
            adRecord.put("advertisingInfoAddOrMinus", add == 0 ? "+" : "-");
            adRecord.put("advertisingInfoUserId", memberAccount);
            adRecord.put("advertisingInfoMoney", money);
            adRecord.put("advertisingInfoFrom", add == 0 ? "收到"+userName+"转账" : "转账给"+toName);
            getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
        }
    }

    /**
     * 校验转账时是否显示广告费
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void checkIsAdvertisingFee(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            // 1、判断当前用户是否追加过分红
            params.put("advertisingInfoFrom", "追加分红");
            // 追加分红
            int share = getBaseDao().getTotalCount("AdvertisingMoneyInfoMapper.queryAdvertisingMoney", params);
            if (share > 0) {
                output.setCode("0");
                output.setMsg("显示广告费");
                return;
            }
            // 2、判断所推荐的会员是否满足100实名
            List<Map<String, Object>> list = getBaseDao().queryForList("MemberMapper.queryMyShareMemberList", params);
            if(list.size() >= 100){
                params.put("list", list);
                int total = getBaseDao().getTotalCount("RealNameAuthMapper.checkCardNumTotal", params);
                if (total >= list.size()) {
                    output.setCode("0");
                    output.setMsg("显示广告费");
                    return;
                }
            }
            output.setCode("-1");
            output.setMsg("不显示广告费");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
