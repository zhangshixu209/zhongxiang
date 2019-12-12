package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxfriend.service.IZxPushMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 众享推送消息dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年12月11日 10点42分
 */

@Service("zxPushMessageService")
public class ZxPushMessageServiceImpl extends BaseServiceImpl implements IZxPushMessageService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxPushMessageServiceImpl.class);

	/**
	 * 添加推送消息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void addPushMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 添加推送消息
			params.put("id", getSequence());
			int i = getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("新增推送消息失败");
				return;
			}
			output.setCode("0");
			output.setMsg("新增推送消息成功");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 查询推送消息列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryPushMessageList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			List<Map<String, Object>> list = getBaseDao().queryForList("ZxPushMessageMapper.queryPushMessageList", params);
			if (null != list && list.size() > 0) {
				output.setCode("0");
				output.setMsg("查询成功");
				output.setItems(list);
			} else {
				output.setCode("-1");
				output.setMsg("查询失败");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 修改推送消息阅读状态
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updatePushMessageStatus(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxPushMessageMapper.queryPushMessageDetail", params);
			if (null != map) {
				output.setCode("0");
				output.setMsg("查询成功");
				output.setItem(map);
			}
			String messageStatus = (String) params.get("messageStatus");
			if (StringUtil.isNotEmpty(messageStatus)) {
				// 添加推送消息
				int i = getBaseDao().update("ZxPushMessageMapper.updatePushMessageStatus", params);
				if (i < 0) {
					output.setCode("-1");
					output.setMsg("修改推送消息失败");
					return;
				}
				output.setCode("0");
				output.setMsg("修改推送消息成功");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

}
