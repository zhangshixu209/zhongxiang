package com.chmei.nzbdata.member.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.member.service.IHelpArchiveService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助文档dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年10月21日 15点58分
 */

@Service("helpArchiveService")
public class HelpArchiveServiceImpl extends BaseServiceImpl implements IHelpArchiveService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HelpArchiveServiceImpl.class);

	/**
	 * 列表查询
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryHelpArchiveList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int total = getBaseDao().getTotalCount("HelpArchiveMapper.queryHelpArchiveCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("HelpArchiveMapper.queryHelpArchiveList", params);
				output.setItems(list);
			}
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
		}
	}

	/**
	 * 新增帮助文档
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("HelpArchiveServiceImpl.saveHelpArchiveInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try{
			params.put("id", getSequence()); // 获取id
			int count = getBaseDao().insert("HelpArchiveMapper.saveHelpArchiveInfo", params);
			saveFile(params); // 保存附件
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("新增失败");
			}
		} catch (Exception ex) {
			LOGGER.error("新增失败: " + ex);
		}
	}

	/**
	 * 保存附件
	 *
	 * @param params 入参
	 */
	private void saveFile(Map<String, Object> params) {
		String filePaths = MapUtils.getString(params, "filePaths");
		if (StringUtils.isNotEmpty(filePaths)) {
			String[] arrFile = filePaths.split("[$]"); // 每个文件路径之间的分隔符
			for (int i = 0; i < arrFile.length; i++) {
				if (StringUtils.isEmpty(arrFile[i])) {
					continue;
				}
				String[] arr = arrFile[i].split("#");
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("id", getSequence());
				paraMap.put("objId", params.get("id")); // 工单表id
				paraMap.put("filePath", arr[0]);
				paraMap.put("fileName", arr[1]);
				paraMap.put("fileTypeCd", getFileType(arr[1]));
				paraMap.put("fileSeqno", String.valueOf(i + 1)); // 序号
				paraMap.put("crtUserId", params.get("crtUserId"));
				getBaseDao().insert("HelpArchiveMapper.saveFile", paraMap);
			}
		}
	}

	/**
	 * 获取文件类型
	 *
	 * @param fileName 文件名
	 * @return 文件类型
	 */
	private String getFileType(String fileName) {
		String subfix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String result;
		switch (subfix) {
			case "jpg":
				result = "1004";
				break;
			case "jpeg":
				result = "1004";
				break;
			case "png":
				result = "1004";
				break;
			default:
				result = "";
				break;
		}
		return result;
	}

	/**
	 * 帮助文档详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void queryHelpArchiveDetail (InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("HelpArchiveServiceImpl.queryHelpArchiveDetail, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject("HelpArchiveMapper.queryHelpArchiveDetail", params);
			// 查询附件
			params.put("objId", params.get("id"));
			List<Map<String, Object>> fileList = getBaseDao().queryForList("HelpArchiveMapper.queryFileList",
					params);
			item.put("fileList", fileList);
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 编辑帮助文档
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("HelpArchiveServiceImpl.updateHelpArchiveInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 更新帮助文档表
			int count = getBaseDao().update("HelpArchiveMapper.updateHelpArchiveInfo", params);
			// 删除附件表
			params.put("objId", MapUtils.getLong(params, "id"));
			getBaseDao().delete("HelpArchiveMapper.deleteFile", params);
			saveFile(params); // 再插入附件表
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("更新失败");
			}
		} catch (Exception ex) {
			LOGGER.error("更新失败: " + ex);
		}
	}

	/**
	 * 删除帮助文档
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void deleteHelpArchiveInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("HelpArchiveServiceImpl.deleteHelpArchiveInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 删除帮助文档表
			int count = getBaseDao().delete("HelpArchiveMapper.deleteHelpArchiveInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("删除失败");
			}
		} catch (Exception ex) {
			LOGGER.error("删除失败: " + ex);
		}
	}

}
