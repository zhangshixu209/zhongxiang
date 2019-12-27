package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IRealNameAuthService;
import com.chmei.nzbdata.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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
                Map<String, Object> map = StringUtil.getCarInfo(cardNum);
                params.put("sex", map.get("sex")); // 截取身份证性别
                params.put("age", map.get("age")); // 截取身份证年龄
            }
            int total = getBaseDao().update("RealNameAuthMapper.updateRealNameAuthInfo", params);
            if (total < 1) {
                output.setCode("-1");
                output.setMsg("保存失败");
            }
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

}
