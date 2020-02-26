package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.zxfriend.service.IZxAppVersionService;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 版本号控制dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点26分
 */

@Service("zxAppVersionService")
public class ZxAppVersionServiceImpl extends BaseServiceImpl implements IZxAppVersionService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxAppVersionServiceImpl.class);


	/**
	 * 新增版本号
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveZxAppVersionInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 添加推送消息
			params.put("id", getSequence());
			int i = getBaseDao().insert("ZxAppVersionMapper.saveZxAppVersionInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("保存成功");
				return;
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 查询版本号列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZxAppVersionList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("ZxAppVersionMapper.queryZxAppVersionCount", params);
			if (i > 0) {
				// 查询版本号列表
				List<Map<String, Object>> list = getBaseDao().queryForList(
						"ZxAppVersionMapper.queryZxAppVersionList", params);
				for (Map<String, Object> map : list) {
					String appUrl = (String) map.get("appUrl");
					map.put("appUrl", appUrl.replace("/upload", "/home/lymanage/cm_uploadFiles"));
				}
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}
}
