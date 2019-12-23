package com.chmei.nzbdata.recharge.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.orderutils.IdGeneratorUtils;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.recharge.service.IRechargeRecordService;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值记录dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月21日 15点58分
 */

@Service("rechargeRecordService")
public class RechargeRecordServiceImpl extends BaseServiceImpl implements IRechargeRecordService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeRecordServiceImpl.class);
	@Resource
	private IZxMyTeamService iZxMyTeamService;

	/**
	 * 初始化加载充值记录查询列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryRechargeRecordList(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("rechargeRecordService.queryRechargeRecordList, input::" + input.getParams().toString());
		try {
			Map<String, Object> params = input.getParams();
			int total = getBaseDao().getTotalCount("RechargeRecordMapper.queryRechargeRecordCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList(
						"RechargeRecordMapper.queryRechargeRecordList", params);
				if (null != list && list.size() > 0) {
					for (Map<String, Object> map : list) {
						Map<String, Object> result = new HashMap<>();
						result.put("memberAccount", map.get("memberAccount"));
						InputDTO inputDTO = new InputDTO();
						inputDTO.setParams(result);
						// 根据当前用户ID 查询团队人数
						int size1 = iZxMyTeamService.countMyTeam(inputDTO, output);
						map.put("teamNum", size1); // 直推人数
					}
				}
				output.setItems(list);
			}
			output.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
