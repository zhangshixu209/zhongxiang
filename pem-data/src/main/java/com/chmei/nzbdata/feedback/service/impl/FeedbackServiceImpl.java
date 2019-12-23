package com.chmei.nzbdata.feedback.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chmei.nzbdata.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.feedback.service.IFeedbackService;

/**
 * 反馈意见dao接口实现
 * 
 * @author 翟超锋
 * @since 2019年05月10日 15点30分
 */

@Service("feedbackService")
public class FeedbackServiceImpl extends BaseServiceImpl implements IFeedbackService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	/**
	 * 列表查询
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbDataException
	 *             自定义异常
	 */
	@Override
	public void queryList(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("feedbackService.queryList, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		int total = getBaseDao().getTotalCount("feedbackMapper.queryListCount", params);
		if (total > 0) {
			List<Map<String, Object>> list = getBaseDao().queryForList("feedbackMapper.queryList", params);
			output.setItems(list);
		}
		output.setTotal(total);
	}


	/**
	 * 回复单条根据id查询单条记录
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbDataException
	 *             自定义异常
	 */
	@Override
	public void selectByPrimaryKey(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("回复单条根据id查询单条记录...feedbackService.selectByPrimaryKey, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		List<Map<String, Object>> list = getBaseDao().queryForList("feedbackMapper.selectByPrimaryKey", params);
		if (CollectionUtils.isNotEmpty(list)) {
			// 查询附件
//			params.put("objId", list.get(0).get("id"));
//			List<Map<String, Object>> filelist = getBaseDao().queryForList("WorkOrderManageMapper.queryFileList", params);
//			list.get(0).put("fileList", filelist);
			output.setItem(list.get(0));
		}
	}


	/**
	 * 根据id删除反馈意见管理记录
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbDataException
	 *             自定义异常
	 */
	@Override
	public void deleteByPrimaryKey(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("根据id删除反馈意见管理记录...feedbackService.deleteByPrimaryKey, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		int count = getBaseDao().delete("feedbackMapper.deleteByPrimaryKey", params);
		output.setTotal(count);

	}

	/**
	 * 新增反馈意见记录
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbDataException
	 *             自定义异常
	 */
	@Override
	public void insertSelective(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("新增反馈意见记录...feedbackService.insertSelective, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		getCrtInfo(params, input);
		params.put("id", getSequence()); // 获取id
		saveFile(params); // 保存附件
		int i = getBaseDao().insert("feedbackMapper.insertSelective", params);
		if (i > 0) {
			output.setCode("0");
			output.setMsg("反馈成功");
			return;
		}
		output.setCode("-1");
		output.setMsg("反馈失败");
	}

	/**
	 * 回复反馈信息
	 * 
	 * @param input
	 *            入參
	 * @param output
	 *            返回对象
	 * @throws NzbDataException
	 *             自定义异常
	 */
	@Override
	public void doUpdateFeedback(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("WorkOrderManageServiceImpl.doUpdateFeedback, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		getModfInfo(params, input);
		// 更新工单表
		int count = getBaseDao().update("feedbackMapper.doUpdateFeedback", params);
		if (count > 0) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"feedbackMapper.selectByPrimaryKey", params);
			map.put("id", getSequence());
			map.put("messageTitle", map.get("titles"));
			map.put("messageContent", map.get("replyContent"));
			map.put("messageStatus", "1");
			map.put("messageType", Constants.MESSAGE_TYPE_1006);
			map.put("memberAccount", map.get("startPensonId"));
			// 添加推送消息
			getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
		}
		output.setTotal(count);
	}

	/**
	 * 保存附件
	 * 
	 * @param params
	 *            入参
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
				getBaseDao().insert("WorkOrderManageMapper.saveFile", paraMap);
			}
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件类型
	 */
	private String getFileType(String fileName) {
		String subfix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String result;
		switch (subfix) {
		case "xls":
			result = "1001";
			break;
		case "xlsx":
			result = "1001";
			break;
		case "doc":
			result = "1002";
			break;
		case "docx":
			result = "1002";
			break;
		case "txt":
			result = "1003";
			break;
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
			result = "1001";
			break;
		}
		return result;
	}

	
}
