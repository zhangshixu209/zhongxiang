package com.chmei.nzbdata.message.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.message.service.IZxMessageService;
import com.chmei.nzbdata.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众享信息dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月11日 09点30分
 */

@Service("zxMessageService")
public class ZxMessageServiceImpl extends BaseServiceImpl implements IZxMessageService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxMessageServiceImpl.class);

	/**
	 * 众享信息新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveZxMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.saveZxMessageInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try{
			params.put("zxMessageId", getSequence()); // 获取id
			int count = getBaseDao().insert("ZxMessageMapper.saveZxMessageInfo", params);
			String filePaths = (String) params.get("filePaths");
			if (StringUtil.isNotEmpty(filePaths)) {
				saveZxMessageImgInfo(params); // 保存附件
			}
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("新增失败");
			}
			output.setCode("0");
			output.setMsg("发布成功");
		} catch (Exception ex) {
			LOGGER.error("新增失败: " + ex);
		}
	}

	/**
	 * 众享信息列表查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZxMessageList(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.queryRotationChartList, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			int total = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessageCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("ZxMessageMapper.queryZxMessageList", params);
				if (null != list && list.size() > 0) {
					for (Map<String, Object> map : list) {
						Map<String, Object> result = new HashMap<>();
						// 查询附件
						result.put("zxMessageId", map.get("zxMessageId"));       // 众享信息ID
						List<Map<String, Object>> filePaths = getBaseDao().queryForList("ZxMessageMapper.queryFileList",
								result);
						// 统计当前众享信息总赞数
						int praiseTotalAll = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessagePraiseCount", result);
						result.put("memberAccount", map.get("memberAccount"));   // 当前登录用户ID
						int praiseTotal = queryZxMessagePraiseCount(result);     // 查询当前用户是否已点赞该众享信息
						if (praiseTotal > 0) {
							map.put("praiseStatus", "1"); // 已点赞
						} else {
							map.put("praiseStatus", "0"); // 未点赞
						}
						map.put("praiseTotalAll", praiseTotalAll); // 点赞数量
						map.put("filePaths", filePaths);     	   // 图片信息
					}
				}
				output.setItems(list);
			}
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
		}
	}

	/**
	 * 众享信息点赞统计
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveZxMessagePraiseCount(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.saveZxMessagePraiseCount, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			int count;
			Map<String, Object> map = new HashMap<>();
			int praiseTotal = queryZxMessagePraiseCount(params); // 查询当前用户是否已点赞该众享信息
			if (praiseTotal > 0) {
				// 删除众享信息点赞(已点赞删除)
				count = getBaseDao().delete("ZxMessageMapper.delZxMessagePraiseCount", params);
				map.put("praiseStatus", "0"); // 未点赞
			} else {
				// 新增众享信息点赞(未点赞新增)
				params.put("zxMessagePraiseId", getSequence()); // 生成ID
				count = getBaseDao().insert("ZxMessageMapper.saveZxMessagePraiseCount", params);
				map.put("praiseStatus", "1"); // 已点赞
			}
			map.put("zxMessageId", params.get("zxMessageId"));
			// 统计当前众享信息总赞数
			int praiseTotalAll = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessagePraiseCount", map);
			map.put("praiseTotalAll", praiseTotalAll);
			output.setItem(map);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("点赞失败");
			}
		} catch (Exception ex) {
			LOGGER.error("点赞失败: " + ex);
		}
	}

	/**
	 * 众享信息浏览统计
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveZxMessageBrowseCount(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.saveZxMessagePraiseCount, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 新增众享信息浏览次数
			int count = getBaseDao().insert("ZxMessageMapper.saveZxMessageBrowseCount", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("保存失败");
			}
			output.setCode("0");
			output.setMsg("保存成功");
		} catch (Exception ex) {
			LOGGER.error("系统错误: " + ex);
		}
	}

	/**
	 * 众享信息收藏
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveZxMessageCollection(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.saveZxMessageCollection, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 校验是否收藏众享信息
			int total = getBaseDao().getTotalCount("ZxPushMessageMapper.checkMessageCollection", params);
			if (total > 0) {
				output.setCode("-1");
				output.setMsg("众享信息已收藏");
				return;
			}
			// 新增众享信息收藏
			int count = getBaseDao().insert("ZxMessageMapper.saveZxMessageCollection", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("保存失败");
			}
			output.setCode("0");
			output.setMsg("收藏成功");
		} catch (Exception ex) {
			LOGGER.error("系统错误: " + ex);
		}
	}

	/**
	 * 众享信息删除我的收藏
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delZxMessageCollection(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.saveZxMessageCollection, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 众享信息删除我的收藏
			int count = getBaseDao().delete("ZxMessageMapper.delZxMessageCollection", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("删除失败");
			}
			output.setCode("0");
			output.setMsg("取消成功");
		} catch (Exception ex) {
			LOGGER.error("系统错误: " + ex);
		}
	}

	/**
	 * 众享信息删除我的发布
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delMyZxMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.delMyZxMessageInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			// 众享信息删除我的发布
			int count = getBaseDao().delete("ZxMessageMapper.delMyZxMessageInfo", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("删除失败");
			}
			output.setCode("0");
			output.setMsg("删除成功");
		} catch (Exception ex) {
			LOGGER.error("系统错误: " + ex);
		}
	}

	/**
	 * 众享信息我的收藏列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryMyZxMessageCollection(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.queryMyZxMessageCollection, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			int total = getBaseDao().getTotalCount("ZxMessageMapper.queryMyZxMessageCollectionCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("ZxMessageMapper.queryMyZxMessageCollection", params);
				output.setItems(list);
			}
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
		}
	}

	/**
	 * 众享信息详情查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZxMessageDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.queryZxMessageDetail, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject("ZxMessageMapper.queryZxMessageDetail", params);
			if (null != item) {
				Map<String, Object> result = new HashMap<>();
				result.put("zxMessageId", item.get("zxMessageId")); // 众享信息ID
				List<Map<String, Object>> filePaths = getBaseDao().queryForList("ZxMessageMapper.queryFileList",
						result);
				item.put("filePaths", filePaths); // 图片信息
			}
			output.setItem(item);
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
		}
	}

	/**
	 * 统计点赞次数
	 * @param params 入参
	 * @return
	 */
	private int queryZxMessagePraiseCount(Map<String, Object> params){
		Long zxMessageId = (Long) params.get("zxMessageId");
		if (null != zxMessageId){
			// 查询当前用户是否已点赞该众享信息
			int praiseTotal = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessagePraiseCount", params);
			if (praiseTotal > 0) {
				return 1; // 已点赞
			} else {
				return 0; // 未点赞
			}
		}
		return 0;
	}

	/**
	 * 保存众享信息附件
	 *
	 * @param params 入参
	 * @throws NzbDataException
	 */
	private void saveZxMessageImgInfo(Map<String, Object> params) throws NzbDataException {
		try{
			String filePaths = MapUtils.getString(params, "filePaths");
			if (StringUtils.isNotEmpty(filePaths)) {
				@SuppressWarnings("unchecked")
//				List<String> fileList = JsonUtil.convertJson2Object(filePaths, List.class);
				String[] fileArray = filePaths.split("\\$");
				for (String list : fileArray) {
					Map<String, Object> paraMap = new HashMap<>();
					paraMap.put("zxMessageUrlId", getSequence());
					paraMap.put("zxMessageId", params.get("zxMessageId")); // 众享信息表id
					paraMap.put("zxMessageImgsUrl", list); 				   // 图片路径
					getBaseDao().insert("ZxMessageMapper.saveZxMessageImgInfo", paraMap);
				}
			}
		} catch (Exception ex) {
			LOGGER.error("新增失败: " + ex);
		}
	}

}
