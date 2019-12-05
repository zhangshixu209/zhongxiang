package com.chmei.nzbdata.complaint.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.complaint.service.IGroupComplaintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 群投诉dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年12月05日 13点52分
 */

@Service("groupComplaintService")
public class GroupComplaintServiceImpl extends BaseServiceImpl implements IGroupComplaintService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupComplaintServiceImpl.class);

	/**
	 * 列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryGroupComplaintList(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("groupComplaintService.queryGroupComplaintList, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		int total = getBaseDao().getTotalCount("GroupComplaintMapper.queryGroupComplaintCount", params);
		if (total > 0) {
			List<Map<String, Object>> list = getBaseDao().queryForList("GroupComplaintMapper.queryGroupComplaintList", params);
			output.setItems(list);
		}
		output.setTotal(total);
	}

	/**
	 * 群投诉详情查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryGroupComplaintDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("groupComplaintService.queryGroupComplaintDetail, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("GroupComplaintMapper.queryGroupComplaintDetail", params);
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 修改群投诉状态
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateGroupComplaintInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int count = getBaseDao().update("GroupComplaintMapper.updateGroupComplaintInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("保存失败");
			}
		} catch (Exception ex) {
			LOGGER.error("保存失败", ex);
		}
	}

	/**
	 * 新增群投诉
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveGroupComplaintInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence()); // 获取id
			int count = getBaseDao().update("GroupComplaintMapper.saveGroupComplaintInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("保存失败");
			}
		} catch (Exception ex) {
			LOGGER.error("保存失败", ex);
		}
	}

}
