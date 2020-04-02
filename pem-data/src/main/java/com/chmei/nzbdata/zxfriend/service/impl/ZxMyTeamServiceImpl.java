package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 众享好友群管理dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点26分
 */

@Service("zxMyTeamService")
public class ZxMyTeamServiceImpl extends BaseServiceImpl implements IZxMyTeamService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxMyTeamServiceImpl.class);

	/**
	 * 根据当前登录人ID 查询自己手下的手下的直推有效人,如果过了30天,直推的还没有直推有效人,则回归分红周期到10天
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkedDay(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		// 查询当前人下有效人数
		int count = 0;
		try {
			params.put("teamParentId", params.get("memberAccount"));
			// 查询我的团队信息
			List<Map<String, Object>> effs = getBaseDao().queryForList(
					"ZxMyTeamMapper.queryMyTeamInfo", params);
			//说明此人下面还有推荐人 否则说明此人下面再无推荐人 直接返回上一层为0  在此只判断二级人员
			if(effs != null && effs.size() > 0){
				for (Map<String, Object> eff : effs) {
					Map<String, Object> maps = new HashMap<>();
					maps.put("memberAccount", eff.get("teamRecommendedUserId"));
					// 首先判断当前此人是否存在 eff.get("teamRecommendedUserId"))
					Map<String, Object> appUser = (Map<String, Object>) getBaseDao().queryForObject(
							"MemberMapper.queryMemberDetail", maps);
					if(null != appUser){
						//存在以后 判断是不是有效的推荐人
						//此人目前不是有效人员 继续往下查询 只要子系有一个是 那么他就是
						Date teamDate = (Date) eff.get("teamDate");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						ParsePosition pos = new ParsePosition(0);
						Date strtodate = formatter.parse(teamDate.toString(), pos);
						if((System.currentTimeMillis() - strtodate.getTime()) / (24 * 3600 * 1000) > 30){
							Map<String, Object> param = new HashMap<>();
							param.put("memberAccount", eff.get("teamRecommendedUserId"));
							input.setParams(param);
							int returncount = checkedDay(input, output);
							//说明查到了有效人员 count++
							if(returncount != 0){
								count ++;
							}
							//无效人员直接更新团队数据
							Map<String, Object> record = new HashMap<>();
							record.put("teamParentId", eff.get("teamParentId"));
							record.put("teamRecommendedUserId", eff.get("teamRecommendedUserId"));
							record.put("teamType", Constants.N);
							// 更新我的团队信息
							getBaseDao().update("ZxMyTeamMapper.updateMyTeamInfo", record);
						} else {
							// 证明是直推的人没有超过30天,不需要检查
							count ++;
						}
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
		return count;
	}

	/**
	 * 根据当前登录人ID 查询当前团队有多少人
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public int countMyTeam(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		//团队总数
		int count = 0;
		try {
			params.put("teamParentId", params.get("memberAccount"));
			// 查询我的团队信息
			List<Map<String, Object>> effs = getBaseDao().queryForList(
					"ZxMyTeamMapper.queryMyTeamInfo", params);
			if (null != effs && effs.size() > 0) {
				count += effs.size();
				for (Map<String, Object> eff : effs) {
					Map<String, Object> param = new HashMap<>();
					param.put("memberAccount", eff.get("teamRecommendedUserId"));
					InputDTO inputDTO = new InputDTO();
					inputDTO.setParams(param);
					int result = this.countMyTeam(inputDTO, output);
					count += result;
				}
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
		return count;
	}

	/**
	 * 根据登录用户ID, 查询自己手下的团队信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	@Override
	public void queryTheLower(InputDTO input, OutputDTO output) throws NzbDataException {
		try {
			Map<String, Object> queryTheLowerList = queryTheLowerList(input, output);
			if (null != queryTheLowerList) {
				output.setItem(queryTheLowerList); // 团队列表查询
			}
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 根据登录用户ID, 查询自己手下的团队信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> queryTheLowerList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		// 1.查询前登录人信息
		Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
				"ZxMyTeamMapper.queryTheLower", params);
		int count = countMyTeam(input, output);
		map.put("count", count); // 计算每个人的推荐人数
		if(map == null){
			map = new HashMap<>();
		}
		map.put("lists","[]");
		// 1.查询当前人下的所有人的ID:
		params.put("teamParentId", params.get("memberAccount"));
		// 查询我的团队信息
		List<Map<String, Object>> allPersonnelByParentId = getBaseDao().queryForList(
				"ZxMyTeamMapper.queryMyTeamInfo", params);
		if (Optional.ofNullable(allPersonnelByParentId).isPresent()) {
			List<Map<String,Object>> listMaps = new ArrayList<>();
			for (Map<String, Object> myTeam : allPersonnelByParentId) {
				Map<String, Object> param = new HashMap<>();
				param.put("memberAccount", myTeam.get("teamRecommendedUserId"));
				// 通过当前名下所有人ID, 查询数据:
				Map<String, Object> stringObjectMap = (Map<String, Object>) getBaseDao().queryForObject(
						"ZxMyTeamMapper.queryTheLower", param);
				if(!stringObjectMap.get("count").equals(0)){
					Map<String, Object> param3 = new HashMap<>();
					param3.put("memberAccount", stringObjectMap.get("memberAccount"));
					InputDTO inputDTO = new InputDTO();
					inputDTO.setParams(param3);
					Map<String, Object> objectMap = this.queryTheLowerList(inputDTO, output);
					listMaps.add(objectMap);
				}else{
					listMaps.add(stringObjectMap);
				}
			}
			map.put("lists",listMaps);
		}
		return map;
	}
}
