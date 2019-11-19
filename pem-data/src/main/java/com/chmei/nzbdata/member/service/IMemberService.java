package com.chmei.nzbdata.member.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 会员管理接口
 *
 * @author zhangshixu
 * @since 2019年10月24日 13点40分
 */
public interface IMemberService {

    /**
     * 新增会员信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void saveMemberInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询会员列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryMemberList(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询会员被投诉记录
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryMemberComplaintRecord(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询会员详细信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryMemberDetail(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 会员充值（钱包余额、广告费）
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void saveMemberRechargeInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 修改会员登录/支付密码
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateLoginOrPayPwd(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 修改新绑定手机号
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateBindPhoneNum(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询会员账户管理列表
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void queryMemberAccountList(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 新增会员账户管理
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void saveMemberAccountIfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 删除会员账户管理
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void delMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 修改会员信息
     *
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    void updateMemberInfo(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询我的众享信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    void queryMyZxMessageList(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 查询我的广告红包列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常
     */
    void queryAllRedPacketList(InputDTO input, OutputDTO output) throws NzbDataException;
}
