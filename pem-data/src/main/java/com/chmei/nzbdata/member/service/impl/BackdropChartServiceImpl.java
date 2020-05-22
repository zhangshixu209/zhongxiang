package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IBackdropChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 二维码背景图dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月21日 15点58分
 */

@Service("backdropChartService")
public class BackdropChartServiceImpl extends BaseServiceImpl implements IBackdropChartService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackdropChartServiceImpl.class);

	/**
	 * 列表查询
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryBackdropChartList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int total = getBaseDao().getTotalCount("BackdropChartMapper.queryBackdropChartCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("BackdropChartMapper.queryBackdropChartList", params);
				output.setItems(list);
			}
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
		}
	}

	/**
	 * 新增二维码背景图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("BackdropChartServiceImpl.saveBackdropChartInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try{
			params.put("id", getSequence()); // 获取id
			int count = getBaseDao().insert("BackdropChartMapper.saveBackdropChartInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("新增失败");
			}
		} catch (Exception ex) {
			LOGGER.error("新增失败: " + ex);
		}
	}

	/**
	 * 二维码背景图详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void queryBackdropChartDetail (InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("BackdropChartServiceImpl.queryBackdropChartDetail, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject("BackdropChartMapper.queryBackdropChartDetail", params);
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 编辑二维码背景图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("BackdropChartServiceImpl.updateBackdropChartInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 更新二维码背景图表
			int count = getBaseDao().update("BackdropChartMapper.updateBackdropChartInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("更新失败");
			}
		} catch (Exception ex) {
			LOGGER.error("更新失败: " + ex);
		}
	}

	/**
	 * 删除二维码背景图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void deleteBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("BackdropChartServiceImpl.deleteBackdropChartInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 删除二维码背景图表
			int count = getBaseDao().delete("BackdropChartMapper.deleteBackdropChartInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("删除失败");
			}
		} catch (Exception ex) {
			LOGGER.error("删除失败: " + ex);
		}
	}

	/**
	 * 设置默认二维码背景图
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void setDefaultBackdropChartInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("BackdropChartServiceImpl.setDefaultBackdropChartInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 更新二维码背景图表
			int count = getBaseDao().update("BackdropChartMapper.setDefaultBackdropChartInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("设置失败");
			}
			output.setCode("0");
			output.setMsg("设置成功");
		} catch (Exception ex) {
			LOGGER.error("设置失败: " + ex);
		}
	}

}
