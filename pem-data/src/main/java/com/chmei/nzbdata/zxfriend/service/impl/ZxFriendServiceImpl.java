package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.zxfriend.service.IZxFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众享好友关系dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点26分
 */

@Service("zxFriendService")
public class ZxFriendServiceImpl extends BaseServiceImpl implements IZxFriendService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxFriendServiceImpl.class);

	/**
	 * 添加众享好友关系
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String zxFriendUserId = (String) params.get("zxFriendUserId"); // 当前登录人账号
			String zxFriendFriendId = (String) params.get("zxFriendFriendId"); // 需要添加好友账号
			params.put("memberAccount", zxFriendFriendId);
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (null == item) {
				output.setCode("-1");
				output.setMsg("用户不存在！");
				return;
			}
			// 判断是否添加过好友
			Map<String, Object> friend = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxFriendMapper.queryZxFriendDetail", params);
			if (null != friend && "Y".equals(friend.get("zxFriendFriendType"))) {
				output.setCode("-1");
				output.setMsg("已经是好友关系,不能重复添加！");
				return;
			}
			Map<String, Object> groupCountA = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxFriendGroupingMapper.queryZxFriendGroupingInfo", params);
			if (null != groupCountA) {
				params.put("zxFriendId", getSequence());   // 众享好友ID
				params.put("zxFriendFriendType", "Y");     // 是否好友状态
				params.put("zxFriendAddDate", "Y"); // 添加好友时间
				params.put("zxFriendGroupingId", groupCountA.get("zxFriendGroupingId"));
//				getBaseDao().update("ZxFriendMapper.updateZxFriendInfo", params);
				// 新增众享好友信息
				int i = getBaseDao().insert("ZxFriendMapper.saveZxFriendInfo", params);
				if (i < 0) {
					output.setCode("-1");
					output.setMsg("后台添加好友失败");
					return;
				}
			}
			Map<String, Object> maps = new HashMap<>();
			maps.put("zxFriendUserId", zxFriendFriendId); // 以好友账号为当前账号
			maps.put("zxFriendFriendId", zxFriendUserId); // 当前人账号为好友账号
			maps.put("zxFriendAddDate", "Y");      		  // 添加好友时间
			// 反向判断是否添加过好友
			Map<String, Object> friendTo = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxFriendMapper.queryZxFriendDetail", maps);
			if (null == friendTo) {
				maps.put("zxFriendId", getSequence());
				// 用好友账号查询好友的默认分组ID
				Map<String, Object> groupCount = (Map<String, Object>) getBaseDao().queryForObject(
						"ZxFriendGroupingMapper.queryZxFriendGroupingInfo", maps);
				if (null != groupCount) {
					maps.put("zxFriendGroupingId", groupCount.get("zxFriendGroupingId"));
					maps.put("zxFriendFriendType", "Y");   // 是否好友状态
					getBaseDao().insert("ZxFriendMapper.saveZxFriendInfo", maps);
				}
			} else {
				maps.put("zxFriendFriendType", "Y");   // 是否好友状态
				getBaseDao().update("ZxFriendMapper.updateZxFriendInfo", maps);
			}
			output.setCode("0");
			output.setMsg("添加好友成功");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 删除众享好友关系
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void deleteZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			List<Map<String, Object>> list = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendList", params);
			// 双向删除好友
			Map<String, Object> map = new HashMap<>();
			map.put("zxFriendUserId", params.get("zxFriendFriendId"));
			map.put("zxFriendFriendId", params.get("zxFriendUserId"));
			List<Map<String, Object>> listFriend = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendList", map);
			if (null != list && list.size() > 0 &&
					null != listFriend && listFriend.size() > 0) {
				int i = getBaseDao().delete("ZxFriendMapper.deleteZxFriendInfo", params);
				int j = getBaseDao().delete("ZxFriendMapper.deleteZxFriendInfo", map); // 双向删除好友
				if (i > 0 && j > 0) {
					output.setCode("0");
					output.setMsg("删除好友成功");
					return;
				}
			}
			params.put("memberAccount", params.get("zxFriendFriendId"));
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (null == item) {
				output.setCode("-1");
				output.setMsg("好友不存在");
				return;
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 关注众享好友
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void notesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String zxFriendFriendId = (String) params.get("zxFriendFriendId"); // 需要添加好友账号
			params.put("memberAccount", zxFriendFriendId);
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (null == item) {
				output.setCode("-1");
				output.setMsg("用户不存在！");
				return;
			}
			// 判断是否关注过好友
			Map<String, Object> friend = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxFriendMapper.queryZxFriendDetail", params);
			if (null == friend) {
				params.put("zxFriendId", getSequence()); // 众享好友ID
				params.put("zxFriendNotesType", "Y");   // 是否关注状态
				// 新增众享好友信息
				int i = getBaseDao().insert("ZxFriendMapper.saveZxFriendInfo", params);
				if (i < 0) {
					output.setCode("-1");
					output.setMsg("关注失败");
				}
				output.setCode("0");
				output.setMsg("关注成功");
			}
			params.put("zxFriendNotesType", "Y");   // 是否关注状态
			if (null != friend && "N".equals(friend.get("zxFriendNotesType"))) {
				int i = getBaseDao().update("ZxFriendMapper.updateZxFriendInfo", params);
				if (i < 0) {
					output.setCode("-1");
					output.setMsg("关注失败");
				}
				output.setCode("0");
				output.setMsg("关注成功");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 取消关注众享好友
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cancelNotesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String zxFriendFriendId = (String) params.get("zxFriendFriendId"); // 需要添加好友账号
			params.put("memberAccount", zxFriendFriendId);
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (null == item) {
				output.setCode("-1");
				output.setMsg("用户不存在！");
				return;
			}
			// 判断是否关注过好友
			Map<String, Object> friend = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxFriendMapper.queryZxFriendDetail", params);
			if (null == friend) {
				output.setCode("-1");
				output.setMsg("好友关系不存在");
				return;
			}
			params.put("zxFriendNotesType", "N");   // 是否关注状态
			if (null != friend && "Y".equals(friend.get("zxFriendNotesType"))) {
				int i = getBaseDao().update("ZxFriendMapper.updateZxFriendInfo", params);
				if (i < 0) {
					output.setCode("-1");
					output.setMsg("取消关注失败");
				}
				output.setCode("0");
				output.setMsg("取消关注成功");
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 查询众享好友列表(带分组关系)
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZxFriendList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			List<Map<String, Object>> list = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendListByGroup", params);
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
	 * 众享好友分组新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("zxFriendGroupingId", getSequence()); // 主键ID
			int i = getBaseDao().insert("ZxFriendGroupingMapper.saveZxFriendGroupingInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("新增分组失败");
				return;
			}
			output.setCode("0");
			output.setMsg("新增分组成功");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 众享好友分组编辑
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("ZxFriendGroupingMapper.updateZxFriendGroupingInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("修改分组失败");
				return;
			}
			output.setCode("0");
			output.setMsg("修改分组成功");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 众享好友分组删除
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("zxFriendGroupingType", "Y");
			// 用好友账号查询好友的默认分组ID
			@SuppressWarnings("unchecked")
			Map<String, Object> groupCount = (Map<String, Object>) getBaseDao().queryForObject(
					"ZxFriendGroupingMapper.queryZxFriendGroupingInfo", params);
			int i = getBaseDao().delete("ZxFriendGroupingMapper.delZxFriendGroupingInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("删除分组失败");
				return;
			}
			// 需要参数：当前人账号，分组ID查询出所有要移动到默认分组的用户
			List<Map<String, Object>> list = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendList", params);
			if (null != list && list.size() > 0) { // 该分组下存在好友时执行
				List<Long> ids = new ArrayList<>();
				for (Map<String, Object> id : list) {
					ids.add((Long) id.get("zxFriendId"));
				}
				// 批量更新好友分组ID
				Map<String, Object> maps = new HashMap<>();
				maps.put("ids", ids); // 需要更新分组的用户ID
				maps.put("zxFriendGroupingId", groupCount.get("zxFriendGroupingId"));
				getBaseDao().update("ZxFriendMapper.batchUpdateZxFriendGroup", maps);
			}
			output.setCode("0");
			output.setMsg("删除分组成功");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 移动我的好友到指定分组
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void moveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("ZxFriendMapper.updateZxFriendInfo", params);
			if (i < 0) {
				output.setCode("-1");
				output.setMsg("移动好友失败");
				return;
			}
			output.setCode("0");
			output.setMsg("移动好友成功");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 查询众享好友分组列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZxFriendGroupingList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			List<Map<String, Object>> list = getBaseDao().queryForList("ZxFriendGroupingMapper.queryZxFriendGroupingList", params);
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
	 * 查询众享好友朋友圈列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZxFriendCircleList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 1 根据用户账户查询拥有的众享好友
			List<Map<String, Object>> list = getBaseDao().queryForList("ZxFriendMapper.queryZxFriendList", params);
			List<String> userList = new ArrayList<>();
			if (null != list && list.size() > 0) {
				for (Map<String, Object> map : list) {
					userList.add((String) map.get("zxFriendFriendId"));
				}
			}
			userList.add((String) params.get("zxFriendUserId")); // 当前登录用户账户
			String users = String.join(",", userList);
			// 根据好友账户批量查询众享信息
			Map<String, Object> maps = new HashMap<>();
			maps.put("memberAccounts", users.subSequence(1, users.length())); // 好友账户list
			int total = getBaseDao().getTotalCount("ZxMessageMapper.queryZxFriendMessageCount", maps);
			if (total > 0) {
				maps.put("start", params.get("start"));
				maps.put("limit", params.get("limit"));
				List<Map<String, Object>> friendCircleList = getBaseDao().queryForList(
						"ZxMessageMapper.queryZxFriendMessageList", maps);
				if (null != friendCircleList && friendCircleList.size() > 0) {
					for (Map<String, Object> map : friendCircleList) {
						Map<String, Object> result = new HashMap<>();
						// 查询附件
						result.put("zxMessageId", map.get("zxMessageId"));       // 众享信息ID
						List<Map<String, Object>> filePaths = getBaseDao().queryForList("ZxMessageMapper.queryFileList",
								result);
						// 统计当前众享信息总赞数
						int praiseTotalAll = getBaseDao().getTotalCount("ZxMessageMapper.queryZxMessagePraiseCount", result);
						result.put("memberAccount", params.get("zxFriendUserId"));   // 当前登录用户ID
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
				output.setMsg("查询成功");
				output.setItems(friendCircleList);
			}
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
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

}
