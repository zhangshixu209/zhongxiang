package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.im.comm.TokenUtil;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.im.service.impl.EasemobIMUsers;
import com.chmei.nzbdata.member.service.IMemberService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                params.put("id", getSequence()); // 获取id
                int count = getBaseDao().insert("MemberMapper.saveMemberInfo", params);
                if (count < 1) {
                    output.setCode("-1");
                    output.setMsg("新增失败");
                }
                Map<String, Object> friendGroup = new HashMap<>();
                friendGroup.put("zxFriendGroupingId", getSequence());
                friendGroup.put("zxFriendUserId", memberAccount);
                friendGroup.put("zxFriendGroupingName", "默认分组");
                friendGroup.put("zxFriendGroupingType", "Y"); // 默认分组标识
                // 为用户添加默认分组
                getBaseDao().insert("ZxFriendGroupingMapper.saveZxFriendGroupingInfo", friendGroup);
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
            int count = getBaseDao().update("MemberMapper.saveMemberRechargeInfo", params);
            if (count < 1) {
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
                }
                output.setCode("0");
                output.setMsg("修改成功");
            } else {
                output.setCode("-1");
                output.setMsg("修改密码失败");
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
                output.setMsg("保存失败");
            }
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
                output.setMsg("新增账户失败");
                return;
            }
            output.setCode("0");
            output.setMsg("新增账户成功");
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
            output.setItems(listAll);
            output.setTotal(listAll.size());
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
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
