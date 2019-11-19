package com.chmei.nzbservice.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.member.service.IMemberService;
import com.chmei.nzbservice.operatelog.service.IOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员管理接口实现类
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点07分
 */
@Service("memberService")
public class MemberServiceImpl extends BaseServiceImpl implements IMemberService {
    /**
     * 操作日志对象
     */
    @Autowired
    private IOperateLogService operateLogService;

    /**
     * 新增会员信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveMemberInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.MEMBER_MANAGE_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("memberService");
        input.setMethod("saveMemberInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询会员列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryMemberList(InputDTO input, OutputDTO output) throws NzbServiceException {
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.MEMBER_MANAGE_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("memberService");
        input.setMethod("queryMemberList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询会员被投诉记录
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryMemberComplaintRecord(InputDTO input, OutputDTO output) throws NzbServiceException {
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.MEMBER_MANAGE_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("memberService");
        input.setMethod("queryMemberComplaintRecord");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询会员详细信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryMemberDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.MEMBER_MANAGE_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("memberService");
        input.setMethod("queryMemberDetail");
        getNzbDataService().execute(input, output);
    }

    /**
     * 会员充值（钱包余额、广告费）
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveMemberRechargeInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        if ("0".equals(output.getCode())) {
            // 成功插入操作日志信息
            input.getParams().put("operatePage", Constants.OPERATE_PAGE_CD.MEMBER_MANAGE_CD); // 操作页面代码
            input.getParams().put("operateType", Constants.OPERATE_TYPE_CD.OPERATE_INSERT_CD); // 操作类型代码
            operateLogService.saveOperateLogInfo(input, output);
        }
        input.setService("memberService");
        input.setMethod("saveMemberRechargeInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 修改会员登录/支付密码
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateLoginOrPayPwd(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("updateLoginOrPayPwd");
        getNzbDataService().execute(input, output);
    }

    /**
     * 修改新绑定手机号
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateBindPhoneNum(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("updateBindPhoneNum");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询会员账户管理列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryMemberAccountList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("queryMemberAccountList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 新增会员账户管理
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("saveMemberAccountInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 删除会员账户管理
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void delMemberAccountInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("delMemberAccountInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 修改会员信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateMemberInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("updateMemberInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询我的众享信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryMyZxMessageList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("queryMyZxMessageList");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询我的广告红包列表
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryAllRedPacketList(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("memberService");
        input.setMethod("queryAllRedPacketList");
        getNzbDataService().execute(input, output);
    }

}
