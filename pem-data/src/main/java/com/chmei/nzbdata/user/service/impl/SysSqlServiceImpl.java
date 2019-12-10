package com.chmei.nzbdata.user.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.ISysSqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysSqlService")
public class SysSqlServiceImpl extends BaseServiceImpl implements ISysSqlService {
	/**
	 * LOGGER
	 */
	private  static final Logger LOGGER = LoggerFactory.getLogger(SysSqlServiceImpl.class);

	/**
	 * 查询数据库备份/还原列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryMySqlList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int total = getBaseDao().getTotalCount("SysSqlMapper.queryMySqlCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("SysSqlMapper.queryMySqlList", params);
				output.setItems(list);
			}
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 备份数据库信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void backupsDBInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence());
			params.put("optType", "1");  // 备份
			int i = getBaseDao().insert("SysSqlMapper.saveSysMysqlInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("备份失败");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 还原数据库信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void reductionDBInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence());
			params.put("optType", "2");  // 还原
			int i = getBaseDao().insert("SysSqlMapper.saveSysMysqlInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("还原失败");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}
}
  
