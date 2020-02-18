package com.chmei.nzbservice.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.member.service.IRealNameAuthService;
import com.chmei.nzbservice.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 实名认证接口实现类
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点07分
 */
@Service("realNameAuthService")
public class RealNameAuthServiceImpl extends BaseServiceImpl implements IRealNameAuthService {

    /**
     * 新增实名认证信息
     * @param input 入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void saveRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("realNameAuthService");
        input.setMethod("saveRealNameAuthInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 编辑实名认证信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void updateRealNameAuthInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("realNameAuthService");
        input.setMethod("updateRealNameAuthInfo");
        getNzbDataService().execute(input, output);
    }

    /**
     * 查询实名认证信息
     *
     * @param input  入参
     * @param output 出参
     * @throws NzbServiceException 自定义异常
     */
    @Override
    public void queryRealNameInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
        input.setService("realNameAuthService");
        input.setMethod("queryRealNameInfo");
        getNzbDataService().execute(input, output);
        Map<String, Object> item = output.getItem();
        if (null != item) { // realName
            String credNum = (String) item.get("cardNum");
            String realName = (String) item.get("realName");
            if (!StringUtil.isEmpty(credNum)) {
                item.put("cardNum", StringUtil.desensitizationCredNum(credNum));
            }
            if (!StringUtil.isEmpty(realName)) {
                item.put("realName", StringUtil.desensitiveName(realName));
            }
        }
    }

}
