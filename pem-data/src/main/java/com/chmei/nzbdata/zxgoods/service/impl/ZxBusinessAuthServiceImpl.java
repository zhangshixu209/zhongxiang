package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.zxgoods.service.IZxBusinessAuthService;
import com.chmei.nzbdata.zxgoods.service.IZxBusinessAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 众享商家认证
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点41分
 */

@Service("zxBusinessAuthService")
public class ZxBusinessAuthServiceImpl extends BaseServiceImpl implements IZxBusinessAuthService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxBusinessAuthServiceImpl.class);

	/**
	 * 商家认证审核
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void authBusinessInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("BusinessAuthMapper.authBusinessInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("保存成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("保存失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 商家认证新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveBusinessInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence()); // 主键ID
			int i = getBaseDao().insert("BusinessAuthMapper.saveBusinessInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("保存成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("保存失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 编辑商家认证
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("BusinessAuthMapper.updateBusinessAuthInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("修改成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("修改失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 删除商家认证
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().delete("BusinessAuthMapper.delBusinessAuthInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("删除成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("删除失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询商家认证详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryBusinessAuthDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"BusinessAuthMapper.queryBusinessAuthDetail", params);
			if (null == map) {
				output.setCode("-1");
				output.setMsg("查询失败");
			}
			output.setItem(map);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询商家认证列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryBusinessAuthList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("BusinessAuthMapper.queryBusinessAuthCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("BusinessAuthMapper.queryBusinessAuthList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 开通发布窗口
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void openReleaseWindow(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("BusinessAuthMapper.openReleaseWindow", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("开通成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("开通失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}
}
