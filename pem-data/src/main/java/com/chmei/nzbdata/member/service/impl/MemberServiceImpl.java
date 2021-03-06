package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.autoconfigure.idgene.InvitationCodeGnerateUtil;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.im.comm.TokenUtil;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.im.service.impl.EasemobIMUsers;
import com.chmei.nzbdata.member.service.IMemberService;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.client.model.NewPassword;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 会员管理接口实现类
 *
 * @author zhangshixu
 * @since 2019年10月24日 13点42分
 */
@Service("memberService")
public class MemberServiceImpl extends BaseServiceImpl implements IMemberService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(MemberServiceImpl.class);
    /** 调用环信接口 */
    private static final EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    @Resource
    private IZxMyTeamService iZxMyTeamService;

    /**
     * 新增会员信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void saveMemberInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            String check = checkUserIsExist(input); // 校验本地用户是否已存在
            // 判断是否已经注册环信账号
            Object imUser = easemobIMUsers.getIMUserByUserName((String) params.get("memberAccount"));
            if (null != imUser) {
                output.setCode("-1");
                output.setMsg("此用户已注册!");
                return;
            }
            if ("0".equals(check)) {
                params.put("id", getSequence()); // 获取id
                int count = getBaseDao().insert("MemberMapper.saveMemberInfo", params);
                if (count < 1) {
                    output.setCode("-1");
                    output.setMsg("注册失败");
                    return;
                }
                // 注册到环信用户
                RegisterUsers registerUsers = new RegisterUsers();
                User user = new User();
                String memberAccount = (String) params.get("memberAccount");
                String memberPwd = (String) params.get("newPassword");
                user.setUsername(memberAccount); // 用户账号
                user.setPassword(memberPwd);     // 用户密码
                registerUsers.add(user);
                Object result = easemobIMUsers.createNewIMUserSingle(registerUsers); // 调用环信创建IM用户接口
                LOGGER.info("imUser============:"+gson.toJson(result));
                Nickname nickname = new Nickname();
                if (null != result) {
                    nickname.setNickname((String) params.get("nickname")); // 用户昵称
                    Object nick = easemobIMUsers.modifyIMUserNickNameWithAdminToken(memberAccount, nickname);
                    LOGGER.info("nickname============:"+gson.toJson(nick));
                }
                Map<String, Object> friendGroup = new HashMap<>();
                friendGroup.put("zxFriendGroupingId", getSequence());
                friendGroup.put("zxFriendUserId", memberAccount);
                friendGroup.put("zxFriendGroupingName", "默认分组");
                friendGroup.put("zxFriendGroupingType", "Y"); // 默认分组标识
                // 为用户添加默认分组
                int i = getBaseDao().insert("ZxFriendGroupingMapper.saveZxFriendGroupingInfo", friendGroup);
                if (i > 0) {
                    String extensionCode = (String) params.get("extensionCode"); // 推广码
                    if (StringUtil.isNotEmpty(extensionCode)) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
                                "MemberMapper.queryMemberForExtensionCode", params);
                        if (null != map_) {
                            map_.put("id", getSequence());
                            map_.put("coverMemberAccount", params.get("memberAccount"));
                            getBaseDao().insert("MemberMapper.saveZxAppMyShareExtend", map_);
                        }
                    }
                }
                Map<String, Object> map = new HashMap<>();
                map.put("memberAccount", memberAccount);
                output.setCode("0");
                output.setMsg("注册成功");
                output.setItem(map);
            } else if ("-1".equals(check)){
                output.setCode("-1");
                output.setMsg("此用户已注册!");
            } else {
                output.setCode("-1");
                output.setMsg("此用户已冻结!");
            }
        } catch (Exception ex) {
            LOGGER.error("新增失败", ex);
        }
    }

    /**
     * 查询会员列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryMemberList(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("MemberMapper.queryMemberCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("MemberMapper.queryMemberList", params);
                if (null != list && list.size() > 0) {
                    for (Map<String, Object> map : list) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("memberAccount", map.get("memberAccount"));
                        InputDTO inputDTO = new InputDTO();
                        inputDTO.setParams(result);
                        // 根据当前用户ID 查询团队人数
                        int size1 = iZxMyTeamService.countMyTeam(inputDTO, output);
                        map.put("teamNum", size1); // 直推人数
                    }
                }
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception ex) {
            LOGGER.error("查询失败" + ex);
        }
    }

    /**
     * 查询会员被投诉记录
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryMemberComplaintRecord(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("MemberMapper.queryMemberComplaintRecordCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("MemberMapper.queryMemberComplaintRecord", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
        }
    }

    /**
     * 查询会员详细信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryMemberDetail(InputDTO input, OutputDTO output) throws NzbDataException {
        LOGGER.info("MemberServiceImpl.queryMemberDetail, input::" + input.getParams().toString());
        Map<String, Object> params = input.getParams();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> item = (Map<String, Object>) getBaseDao().
                    queryForObject("MemberMapper.queryMemberDetail", params);
            output.setItem(item);
        } catch (Exception ex) {
            LOGGER.error("查询失败: " + ex);
        }
    }

    /**
     * 会员充值（钱包余额、广告费）
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void saveMemberRechargeInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> item = (Map<String, Object>) getBaseDao().
                    queryForObject("MemberMapper.queryMemberDetail", params);
            String oldWalletBalance = (String) item.get("walletBalance");
            String oldAdvertisingFee = (String) item.get("advertisingFee");
            int count = getBaseDao().update("MemberMapper.saveMemberRechargeManaInfo", params);
            if (count > 0) {
                String walletBalance = (String) params.get("walletBalance");
                String advertisingFee = (String) params.get("advertisingFee");
                if (StringUtil.isNotEmpty(advertisingFee)) {
                    // 后台充值广告费
                    Map<String, Object> adRecord = new HashMap<>();
                    adRecord.put("advertisingInfoId", getSequence());
                    adRecord.put("advertisingInfoAddOrMinus", "+");
                    adRecord.put("advertisingInfoUserId", item.get("memberAccount"));
                    adRecord.put("advertisingInfoMoney", Double.valueOf(advertisingFee) - Double.valueOf(oldAdvertisingFee));
                    adRecord.put("advertisingInfoFrom", "后台充值");
                    getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
                    // 后台充值记录
                    Map<String,Object> insertMap = new HashMap<>();
                    insertMap.put("id", getSequence());
                    insertMap.put("memberAccount", item.get("memberAccount"));
//                insertMap.put("serialId","");
                    insertMap.put("validStsCd","1");
                    insertMap.put("rechargeAmount", Double.valueOf(advertisingFee) - Double.valueOf(oldAdvertisingFee));
                    insertMap.put("status", "1001");
                    getBaseDao().insert("RechargeRecordMapper.saveRechargeRecordInfo", insertMap);
                } else {
                    // 后台充值钱包
                    Map<String, Object> walletMoneyInfo = new HashMap<>();
                    walletMoneyInfo.put("walletInfoId", getSequence());
                    walletMoneyInfo.put("walletInfoAddOrMinus", "+");
                    walletMoneyInfo.put("walletInfoUserId", item.get("memberAccount"));
                    walletMoneyInfo.put("walletInfoMoney", Double.valueOf(walletBalance) - Double.valueOf(oldWalletBalance));
                    walletMoneyInfo.put("walletInfoFrom", "后台充值");
                    getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
                    // 后台充值记录
                    Map<String,Object> insertMap = new HashMap<>();
                    insertMap.put("id", getSequence());
                    insertMap.put("memberAccount", item.get("memberAccount"));
//                insertMap.put("serialId","");
                    insertMap.put("validStsCd","1");
                    insertMap.put("rechargeAmount", Double.valueOf(walletBalance) - Double.valueOf(oldWalletBalance));
                    insertMap.put("status", "1002");
                    getBaseDao().insert("RechargeRecordMapper.saveRechargeRecordInfo", insertMap);

                }
            } else {
                output.setCode("-1");
                output.setMsg("保存失败");
            }
        } catch (Exception ex) {
            LOGGER.error("保存失败", ex);
        }
    }

    /**
     * 修改会员登录/支付密码
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateLoginOrPayPwd(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            String memberAccount = (String) params.get("memberAccount"); // 用户账户
            int count = getBaseDao().update("MemberMapper.updateLoginOrPayPwd", params);
            if (count > 0) {
                String memberPwd = (String) params.get("memberPwd");
                if (StringUtil.isNotEmpty(memberPwd)){ // 修改登录密码, 需要同时修改环信密码
                    NewPassword newPassword = new NewPassword(); // 不加密密码
                    newPassword.setNewpassword((String) params.get("newPassword"));
                    // 调用环信修改IM用户密码接口
                    Object result = easemobIMUsers.modifyIMUserPasswordWithAdminToken(memberAccount, newPassword);
                    LOGGER.info("newPassword============:"+gson.toJson(result));
                    // 更新微信登录信息密码
                    Map<String, Object> map = new HashMap<>();
                    map.put("realPwd", params.get("newPassword"));
                    map.put("memberAccount", memberAccount);
                    getBaseDao().update("WxLoginInfoMapper.updateWxLoginPwd", map);
                }
                output.setCode("0");
                output.setMsg("设置成功");
            } else {
                output.setCode("-1");
                output.setMsg("设置失败");
            }
        } catch (Exception ex) {
            LOGGER.error("保存失败", ex);
        }
    }

    /**
     * 修改新绑定手机号
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateBindPhoneNum(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().update("MemberMapper.updateBindPhoneNum", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("绑定失败");
            }
            output.setCode("0");
            output.setMsg("绑定成功！");
        } catch (Exception ex) {
            LOGGER.error("保存失败", ex);
        }
    }

    /**
     * 查询会员账户管理列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryMemberAccountList(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("MemberAccountMapper.queryMemberAccountCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("MemberAccountMapper.queryMemberAccountList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception ex) {
            LOGGER.error("查询失败" + ex);
        }
    }

    /**
     * 新增会员账户管理
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void saveMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            params.put("id", getSequence()); // 获取id
            int count = getBaseDao().insert("MemberAccountMapper.saveMemberAccountInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("添加失败！");
                return;
            }
            output.setCode("0");
            output.setMsg("添加成功！");
        } catch (Exception ex) {
            LOGGER.error("新增失败", ex);
        }
    }

    /**
     * 删除会员账户管理
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void delMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().delete("MemberAccountMapper.delMemberAccountInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("删除账户失败");
                return;
            }
            output.setCode("0");
            output.setMsg("删除账户成功");
        } catch (Exception ex) {
            LOGGER.error("删除失败", ex);
        }
    }

    /**
     * 修改会员信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateMemberInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().update("MemberMapper.updateMemberInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("保存失败");
            }
            output.setCode("0");
            output.setMsg("修改成功");
        } catch (Exception ex) {
            LOGGER.error("保存失败", ex);
        }
    }

    /**
     * 查询我的众享信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void queryMyZxMessageList(InputDTO input, OutputDTO output) throws NzbDataException {
        LOGGER.info("ZxMessageServiceImpl.queryMyZxMessageList, input::" + input.getParams().toString());
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessageCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("ZxMessageMapper.queryZxMessageList", params);
                for (Map<String, Object> map : list) {
                    Map<String, Object> result = new HashMap<>();
                    // 查询附件
                    result.put("zxMessageId", map.get("zxMessageId"));       // 众享信息ID
                    List<Map<String, Object>> filePaths = getBaseDao().queryForList("ZxMessageMapper.queryFileList",
                            result);
                    // 统计我发布的众享信息总赞数
                    int praiseTotalAll = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessagePraiseCount", result);
                    map.put("praiseTotalAll", praiseTotalAll); // 点赞数量
                    map.put("filePaths", filePaths);     	   // 图片信息
                }
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
        }
    }

    /**
     * 查询我的广告红包列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void queryAllRedPacketList(InputDTO input, OutputDTO output) throws NzbDataException {
        LOGGER.info("ZxMessageServiceImpl.queryAllRedPacketList, input::" + input.getParams().toString());
        Map<String, Object> params = input.getParams();
        List<Map<String, Object>> listAll = new ArrayList<>(); // 重新封装红包list
        try {
            // 1.查询红包信息 --> 发布红包时间,红包个数,抢完所耗时总时间,红包广告标语,红包类型(众享红包,链接,图文,视频)
            //   1.1 众享红包
            List<Map<String, Object>> redPacketMap = getBaseDao().queryForList(
                    "RedPacketMapper.queryMyReleaseRedPacket", params);
            //   1.2 图文红包
            List<Map<String, Object>> imgRedPacketMap = getBaseDao().queryForList(
                    "ImgRedPacketMapper.queryMyReleaseRedPacket", params);
            //   1.3 视频红包
            List<Map<String, Object>> videoRedPacketMap = getBaseDao().queryForList(
                    "VideoRedPacketMapper.queryMyReleaseRedPacket", params);
            //   1.4 链接红包
            List<Map<String, Object>> linkRedPacketMap = getBaseDao().queryForList(
                    "LinkRedPacketMapper.queryMyReleaseRedPacket", params);
            listAll.addAll(redPacketMap);
            listAll.addAll(imgRedPacketMap);
            listAll.addAll(videoRedPacketMap);
            listAll.addAll(linkRedPacketMap);
            // 排序
            listAll.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
                long beginMillisecond = ((Date) o1.get("redPacketDateSort")).getTime();
                long endMillisecond = ((Date) o2.get("redPacketDateSort")).getTime();
                if(beginMillisecond > endMillisecond){
                    return -1;
                }
                return 1;
            });
            output.setItems(listAll);
            output.setTotal(listAll.size());
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
        }
    }

    /**
     * 会员警告、冻结和解冻
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void memberHandle(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            // 投诉状态
            String status = (String) params.get("status");
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
                    "MemberMapper.queryMemberDetail", params);
            Map<String, Object> maps = new HashMap<>();
            maps.put("memberAccount", map.get("memberAccount"));
            if("1004".equals(status)){ // 冻结账号
                Object result = easemobIMUsers.deactivateIMUser((String) params.get("memberAccount"));
                if (null != result) {
                    maps.put("status", "3"); // 冻结
                }
                LOGGER.info("deactivateIMUser============:"+gson.toJson(result));
            } else if ("1005".equals(status)) { // 解冻账号
                Object result = easemobIMUsers.activateIMUser((String) params.get("memberAccount"));
                maps.put("status", "1"); // 解冻
                LOGGER.info("deactivateIMUser============:"+gson.toJson(result));
            } else if ("1003".equals(status)) {
                map.put("id", getSequence());
                map.put("messageTitle", "警告信息");
                map.put("messageContent", params.get("auditOpinion"));
                map.put("messageStatus", "1");
                map.put("messageType", Constants.MESSAGE_TYPE_1007);
                map.put("memberAccount", map.get("memberAccount"));
                maps.put("status", "2"); // 冻结
                // 添加推送消息
                getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
            }
            int count = getBaseDao().update("MemberMapper.updateMemberInfo", maps);
            if (count > 0) {
                output.setCode("0");
                output.setMsg("处理成功");
            } else {
                output.setCode("-1");
                output.setMsg("保存失败");
            }
        } catch (Exception ex) {
            LOGGER.error("保存失败", ex);
        }
    }

    /**
     * 新增用户推广信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public void saveZxAppMyShare(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
                    "MemberMapper.queryMemberMyShare", params);
            if (null != map) { // 如果存在直接返回，否则新增
                output.setItem(map);
                output.setCode("0");
                output.setMsg("查询成功");
                return;
            }
            params.put("id", getSequence());
            String serialCode = InvitationCodeGnerateUtil.toSerialCode(getSequence());
            params.put("extensionCode", serialCode); // 推广码
            int count = getBaseDao().insert("MemberMapper.saveZxAppMyShare", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("保存失败");
            } else {
                Map<String, Object> maps = (Map<String, Object>) getBaseDao().queryForObject(
                        "MemberMapper.queryMemberMyShare", params);
                if (null != maps) { // 如果存在直接返回，否则新增
                    output.setItem(maps);
                    output.setCode("0");
                    output.setMsg("查询成功");
                }
            }
        } catch (Exception ex) {
            LOGGER.error("保存失败", ex);
        }
    }

    /**
     * 查询我的分享人实名认证信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void queryMyShareList(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("MemberMapper.queryMyShareCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao().queryForList("MemberMapper.queryMyShareList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception ex) {
            LOGGER.error("查询失败" + ex);
        }
    }

    /**
     * 查询微信登录信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void queryWxLoginInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> item = (Map<String, Object>) getBaseDao().
                    queryForObject("WxLoginInfoMapper.queryWxLoginInfo", params);
            output.setItem(item);
        } catch (Exception ex) {
            LOGGER.error("查询失败: " + ex);
        }
    }

    /**
     * 绑定微信登录信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void saveWxLoginInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total =  getBaseDao().getTotalCount("WxLoginInfoMapper.queryWxLoginTotal", params);
            if (total > 0) {
                output.setCode("-1");
                output.setMsg("微信已绑定其他账号！");
                return;
            }
            params.put("id", getSequence());
            int count = getBaseDao().insert("WxLoginInfoMapper.saveWxLoginInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("绑定失败");
                return;
            }
            output.setCode("0");
            output.setMsg("绑定成功");
        } catch (Exception ex) {
            LOGGER.error("绑定失败", ex);
        }
    }

    /**
     * 解绑微信登录信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void delWxLoginInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().delete("WxLoginInfoMapper.delWxLoginInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("解绑失败");
                return;
            }
            output.setCode("0");
            output.setMsg("解绑成功");
        } catch (Exception ex) {
            LOGGER.error("解绑失败", ex);
        }
    }

    /**
     * 校验用户是否存在
     *
     * @param input  入参
     * @throws NzbDataException 异常信息
     */
    private String checkUserIsExist(InputDTO input) throws NzbDataException {
        LOGGER.info("MemberServiceImpl.checkUserIsExist, input::" + input.getParams().toString());
        Map<String, Object> params = input.getParams();
        String check = "0";
        try {
            int item = getBaseDao().getTotalCount("MemberMapper.checkUserIsExist", params);
            if (item > 0) {
                LOGGER.info("会员已存在");
                check = "-1";
                return check;
            }
        } catch (Exception ex) {
            LOGGER.error("查询失败: " + ex);
        }
        return check;
    }
}
