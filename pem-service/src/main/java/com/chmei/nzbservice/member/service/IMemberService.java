package com.chmei.nzbservice.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 会员管理接口
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点05分
 */
public interface IMemberService {

    /**
     * 新增会员信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void saveMemberInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询会员列表
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryMemberList(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询会员被投诉记录
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryMemberComplaintRecord(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询会员详细信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryMemberDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 会员充值（钱包余额、广告费）
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void saveMemberRechargeInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 修改会员登录/支付密码
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void updateLoginOrPayPwd(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 修改新绑定手机号
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void updateBindPhoneNum(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询会员账户管理列表
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryMemberAccountList(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 新增会员账户管理
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void saveMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 删除会员账户管理
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void delMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 修改会员信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void updateMemberInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询我的众享信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryMyZxMessageList(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询我的广告红包列表
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryAllRedPacketList(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 会员警告、冻结和解冻
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void memberHandle(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 新增用户推广信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void saveZxAppMyShare(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询我的分享人实名认证信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryMyShareList(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 查询微信登录信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void queryWxLoginInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 绑定微信登录信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void saveWxLoginInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 解绑微信登录信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    void delWxLoginInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
