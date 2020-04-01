package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.util.StringUtil;
import com.chmei.nzbservice.zxgoods.service.IZxBusinessAuthService;
import com.chmei.nzbservice.zxgoods.service.IZxGoodsTypeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 广告分红service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxBusinessAuthService")
public class ZxBusinessAuthServiceImpl extends BaseServiceImpl implements IZxBusinessAuthService {


	/**
	 * 商家认证审核
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void authBusinessInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("authBusinessInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 商家认证新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveBusinessInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("saveBusinessInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑商家认证
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("updateBusinessAuthInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除商家认证
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("delBusinessAuthInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询商家认证详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryBusinessAuthDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("queryBusinessAuthDetail");
		getNzbDataService().execute(input, output);
		String credNum = (String) output.getItem().get("credNum");
		String phoneNumber = (String) output.getItem().get("phoneNumber");
		// 脱敏
		if (!StringUtil.isEmpty(credNum)) {
			Map<String, Object> map = new HashMap<>();
			map.put("credNum", StringUtil.desensitizationCredNum(credNum));
			map.put("phoneNumber", StringUtil.desensitivePhone(phoneNumber));
			output.setItem(map);
		}
	}

	/**
	 * 查询商家认证列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryBusinessAuthList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("queryBusinessAuthList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 商家认证开通发布窗口
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void openReleaseWindow(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("openReleaseWindow");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 取消发布窗口
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void cancelReleaseWindow(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxBusinessAuthService");
		input.setMethod("cancelReleaseWindow");
		getNzbDataService().execute(input, output);
	}
}
