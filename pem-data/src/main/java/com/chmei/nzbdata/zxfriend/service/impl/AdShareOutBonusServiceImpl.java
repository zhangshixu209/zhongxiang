package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxfriend.service.IAdShareOutBonusService;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 追加分红dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点26分
 */

@Service("adShareOutBonusService")
public class AdShareOutBonusServiceImpl extends BaseServiceImpl implements IAdShareOutBonusService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AdShareOutBonusServiceImpl.class);

	@Resource
	private IZxMyTeamService iZxMyTeamService;

	/**
	 * 激活广告分红
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void adShareOutBonusActivate(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String memberAccount = (String) params.get("memberAccount"); // 当前用户账号
			params.put("coverMemberAccount", memberAccount);
			// 查询广告分红信息
			Map<String, Object> shareOutBonus = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryShareOutBonusDetail", params);
			if (null != shareOutBonus && "Y".equals(shareOutBonus.get("adShareOutBonusType"))) {
				Map<String, Object> teamExample = new HashMap<>();
				teamExample.put("teamParentId", memberAccount); // 团队隶属上级ID, 推荐人账户
				// 根据当前用户ID 查询自己直推人数
				List<Map<String, Object>> allPersonnel = getBaseDao().queryForList("ZxMyTeamMapper.queryMyTeamInfo", teamExample);
				// 根据当前用户ID 查询团队人数
				int size1 = iZxMyTeamService.countMyTeam(input, output);

				// 每位会员初始当前额度都为1000,推荐两位有效会员增加至2000;有效直推满5人,团队满25人,分红额度增加至5000;在此基础上,团队满50人,当前额度增加至10000.
				//直推人数
				int size = allPersonnel.size();

				Map<String, Object> outBonus = new HashMap<>();
				outBonus.put("adShareOutBonusUserId", memberAccount); // 广告分红表用户账户
				// 额度:
				BigDecimal adShareOutBonusLimit = (BigDecimal) shareOutBonus.get("adShareOutBonusLimit");
				// 剩余额度:
				BigDecimal adShareOutBonusResidueLimit = (BigDecimal) shareOutBonus.get("adShareOutBonusResidueLimit");

				// 定义初始涨幅参数:
				Double limit = 0D;
				if (size > 0) {
					if (size >= 2 && size < 5) {
						// 额度增长1000:
						limit = 2000D;
					} else if (size >= 5 && allPersonnel != null && (size1 >= 25 && size1 <50)) {
						// 额度增长3000
						limit = 5000D;
					} else if (size >= 5 && allPersonnel != null && size1 >= 50) {
						// 额度增长5000
						limit = 10000D;
					}
					if (Constants.limit1.equals(adShareOutBonusLimit) && Constants.limit1.equals(limit) ||
							Constants.limit2.equals(adShareOutBonusLimit) && Constants.limit3.equals(limit) ||
							Constants.limit5.equals(adShareOutBonusLimit) && Constants.limit5.equals(limit)) {
						outBonus.put("adShareOutBonusLimit", adShareOutBonusLimit.doubleValue() + limit);
						outBonus.put("adShareOutBonusResidueLimit", adShareOutBonusResidueLimit.doubleValue() + limit);
						int i = getBaseDao().update("ShareOutBonusMapper.updateShareOutBonusInfo", outBonus);
						if (i <= 0) {
							output.setCode("-1");
							output.setMsg("查询失败, 请重试!");
						}
					}
				}
				// 返回app参数整合:
				Map<String,Object> map = new HashMap<>();
				// 广告额度ID
				map.put("bonusId",shareOutBonus.get("adShareOutBonusId"));
				// 用户ID
				map.put("userId", shareOutBonus.get("adShareOutBonusUserId"));
				if (Constants.limit1.equals(adShareOutBonusLimit) && Constants.limit1.equals(limit) ||
						Constants.limit2.equals(adShareOutBonusLimit) && Constants.limit3.equals(limit) ||
						Constants.limit5.equals(adShareOutBonusLimit) && Constants.limit5.equals(limit)) {
					// 广告分红总额度:
					map.put("adShareOutBonusLimit", adShareOutBonusLimit.doubleValue() + limit);
					// 剩余分红额度金额
					map.put("adShareOutBonusResidueLimit", adShareOutBonusResidueLimit.doubleValue() + limit);
				} else {
					// 广告分红总额度:
					map.put("adShareOutBonusLimit", adShareOutBonusLimit);
					// 剩余分红额度金额
					map.put("adShareOutBonusResidueLimit", adShareOutBonusResidueLimit);
				}
				// 分红任务金额
				map.put("money", 0);
				// 分红时间周期
				map.put("circleDate", 0);
				// 是否有分红任务周期的标志
				map.put("mark", Constants.N);
				// 查询额度和剩余额度, 看是否有广告分红任务
				Map<String, Object> shareOutBonusInfo = (Map<String, Object>) getBaseDao().queryForObject(
						"ShareOutBonusMapper.findTaskByUserIdAndMarkS", params);
				// 有分红任务并且已经开始
				if (shareOutBonusInfo != null && Constants.S.equals(shareOutBonusInfo.get("adShareOutBonusInfoDone"))) {
					// 获取广告分红任务开始时间,结束时间和周期天数
					map.put("money",shareOutBonusInfo.get("adShareOutBonusInfoMoney"));
					String startDate = (String) shareOutBonusInfo.get("adShareOutBonusInfoStart");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (StringUtil.isNotEmpty(startDate)) {
						ParsePosition pos = new ParsePosition(0);
						Date starToDate = formatter.parse(startDate, pos);
						int endDay = (int) shareOutBonusInfo.get("adShareOutBonusInfoDayNum");
						map.put("circleDate",starToDate.getTime() + (endDay * 24 * 3600 * 1000));
					} else {
						map.put("circleDate", "0");
					}
					map.put("mark", Constants.S);
				}
				output.setCode("0");
				output.setMsg("查询成功");
				output.setItem(map);
			} else { // 未激活
				int i = activate(input, output); // 调用广告分红激活
				if (i == -1) {
					output.setCode("-1");
					output.setMsg("余额不足，请及时充值！");
					return;
				}
				if (i > 0) {
					// 分红任务金额
					shareOutBonus.put("money", 0);
					// 分红时间周期
					shareOutBonus.put("circleDate",0);
					// 是否有分红任务周期的标志
					shareOutBonus.put("mark", Constants.N);
					output.setCode("0");
					output.setMsg("激活成功！");
					output.setItem(shareOutBonus);
				}
			}

		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 激活广告分红
	 * @param input 入参
	 * @param output 出参
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int activate(InputDTO input, OutputDTO output) {
		Map<String, Object> params = input.getParams();
		try {
			// 先扣除账户余额100元作为激活分红的手续费:
			// 查询当前用户信息
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			Map<String, Object> map = new HashMap<>();
			if (Optional.ofNullable(item).isPresent()) {
				// 判断账户余额是否 > 100 元
				Double zxMyWalletAmount = Double.valueOf(item.get("walletBalance")+"");
				if (zxMyWalletAmount != null) {
					if (zxMyWalletAmount < Constants.MONEY) {
						output.setCode("-1");
						output.setMsg("余额不足，请及时充值！");
						return -1;
					}
					map.put("id", item.get("id")); // 用户ID
					map.put("memberAccount", item.get("memberAccount")); // 用户账户
					// 扣除账户中的余额 100 元
					map.put("walletBalance", zxMyWalletAmount - 100);
					// 设置用户信息中激活广告分红标志 1 : 已经激活, 0 : 未激活
					map.put("activateStatus", 1);
					int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
					if (i > 0) {
						// 记录激活分红进行扣除的钱包钱数:
						Map<String, Object> walletMoneyInfo = new HashMap<>();
						walletMoneyInfo.put("walletInfoId", getSequence());
						walletMoneyInfo.put("walletInfoAddOrMinus", "-");
						walletMoneyInfo.put("walletInfoUserId", item.get("memberAccount"));
						walletMoneyInfo.put("walletInfoMoney", 100.0);
						walletMoneyInfo.put("walletInfoFrom", "激活分红");
						getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
						// 记录结束
						// 广告分红激活
						Map<String, Object> record = new HashMap<>();
						record.put("adShareOutBonusId", getSequence());
						record.put("adShareOutBonusUserId", item.get("memberAccount"));
						// 激活默认额度为 1000 元
						record.put("adShareOutBonusLimit", 1000D);
						// 分红额度也为 1000 元
						record.put("adShareOutBonusResidueLimit", 1000D);
						// 设置激活标志 Y 是 N 否
						record.put("adShareOutBonusType", "Y");
						// 新增广告分红激活信息
						int ii = getBaseDao().insert("ShareOutBonusMapper.saveShareOutBonusInfo", record);
						return ii;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
		return 0;
	}

	/**
	 * 追加广告分红
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void appendShareOutBonus(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 被推荐人ID
			String coverMemberAccount = (String) params.get("coverMemberAccount");
			// 追加额度
			Integer money_ = (Integer) params.get("money");
			Double money = money_ / 1.0;
			// 推荐人的手机号(存储手机号)
			String memberAccount = (String) params.get("memberAccount");
			// 推荐人姓名
			String userName = (String) params.get("userName");

			// 根据当前用户ID查询剩余额度是否小于追加的钱数,如果小于不允许追加,判断广告红包余额是否充足,不足,不允许追加
			// 查询当前用户信息
			Map<String, Object> param = new HashMap<>();
			param.put("memberAccount", coverMemberAccount); // 被推荐人账户
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", param);
			String advertisingFee = (String) item.get("advertisingFee");
			if (null != item) {
				if ((Double.valueOf(advertisingFee) < money)) {
					output.setCode("-1");
					output.setMsg("余额不足，不允许追加！");
					return;
				}
			}
			// 查询广告分红信息
			Map<String, Object> shareOutBonus = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryShareOutBonusDetail", params);
			if (null != shareOutBonus) {
				// 广告分红剩余额度
				BigDecimal adShareOutBonusResidueLimit = (BigDecimal) shareOutBonus.get("adShareOutBonusResidueLimit");
				if (adShareOutBonusResidueLimit.doubleValue() == 0) {
					output.setCode("-1");
					output.setMsg("暂无可用额度！");
					return;
				}
				if ((adShareOutBonusResidueLimit.doubleValue() < money)) {
					output.setCode("-1");
					output.setMsg("剩余额度小于追加额度,不允许追加！");
					return;
				}
			}
			// ----- 设置广告分红的追加金额-----
			Map<String, Object> record = new HashMap<>();
			// 查询此人是否填写推荐人:
			Map<String, Object> teamExample = new HashMap<>();
			teamExample.put("teamParentId", memberAccount); // 团队隶属上级ID, 推荐人账户
			teamExample.put("teamRecommendedUserId", coverMemberAccount); // 被推荐人账户
			// 查询我的团队信息
			List<Map<String, Object>> zxAppMyTeams = getBaseDao().queryForList(
					"ZxMyTeamMapper.queryMyTeamInfo", teamExample);
			if (null != zxAppMyTeams && zxAppMyTeams.size() > 0) {
				Map<String, Object> sob = new HashMap<>();
				sob.put("coverMemberAccount", coverMemberAccount);
				// 证明有填写推荐人,只需要更新追加金额,不需要记录推荐人
				Map<String, Object> coverMember = (Map<String, Object>) getBaseDao().queryForObject(
						"ShareOutBonusMapper.queryShareOutBonusDetail", sob);
				if(coverMember != null){
					// 增加可用额度
					BigDecimal adShareOutBonusMoney = (BigDecimal) coverMember.get("adShareOutBonusMoney");
					record.put("adShareOutBonusMoney", (money + adShareOutBonusMoney.doubleValue()));
				}
				// 扣除剩余额度(如果剩余额度小于追加金额, 也是不允许追加的)
				if (shareOutBonus != null) {
					BigDecimal adShareOutBonusResidueLimit = (BigDecimal) shareOutBonus.get("adShareOutBonusResidueLimit");
					Double residueLimit =adShareOutBonusResidueLimit.doubleValue();
					record.put("adShareOutBonusResidueLimit", (residueLimit - money));
				}
				record.put("adShareOutBonusUserId", coverMemberAccount);
				// 设置更新追加条件
				int i = getBaseDao().update("ShareOutBonusMapper.updateShareOutBonusInfo", record);
				if (i > 0) {
					// 扣除广告红包金额钱数:
					Map<String, Object> user_ = new HashMap<>();
					double $$money_ = Double.valueOf(item.get("advertisingFee")+"") - money;
					user_.put("advertisingFee", $$money_);
					user_.put("memberAccount", coverMemberAccount); // 会员账户
					int ii = getBaseDao().update("MemberMapper.updateMemberBalance", user_);
					if (ii > 0) {
						// 广告钱包记录
						Map<String, Object> adRecord = new HashMap<>();
						adRecord.put("advertisingInfoId", getSequence());
						adRecord.put("advertisingInfoAddOrMinus", "-");
						adRecord.put("advertisingInfoUserId", coverMemberAccount);
						adRecord.put("advertisingInfoMoney", money);
						adRecord.put("advertisingInfoFrom", "追加分红");
						getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
					}
					output.setCode("0");
					output.setMsg("追加分红成功");
					return;
				}
			} else { // 没有填写过推荐人,第一次填写,需要跟新团队表数据
				// 设置追加金额:
				Map<String, Object> sob = new HashMap<>();
				sob.put("coverMemberAccount", coverMemberAccount);
				Map<String, Object> coverMember = (Map<String, Object>) getBaseDao().queryForObject(
						"ShareOutBonusMapper.queryShareOutBonusDetail", sob);
				if(coverMember != null){
					// 增加可用额度
					BigDecimal adShareOutBonusMoney = (BigDecimal) coverMember.get("adShareOutBonusMoney");
					record.put("adShareOutBonusMoney", (money + adShareOutBonusMoney.doubleValue()));
				}
				// 扣除剩余额度(如果剩余额度小于追加金额, 也是不允许追加的)
				if (shareOutBonus != null) {
					BigDecimal adShareOutBonusResidueLimit = (BigDecimal) shareOutBonus.get("adShareOutBonusResidueLimit");
					Double residueLimit = adShareOutBonusResidueLimit.doubleValue();
					record.put("adShareOutBonusResidueLimit", (residueLimit - money));
				}
				record.put("adShareOutBonusUserId", coverMemberAccount);
				// 设置更新追加条件
				int i = getBaseDao().update("ShareOutBonusMapper.updateShareOutBonusInfo", record);
				if (i > 0) {
					// 扣除广告红包金额钱数:
					Map<String, Object> user_ = new HashMap<>();
					double $$money_ = Double.valueOf(item.get("advertisingFee")+"") - money;
					user_.put("advertisingFee", $$money_);
					user_.put("memberAccount", coverMemberAccount); // 会员账户
					int ii = getBaseDao().update("MemberMapper.updateMemberBalance", user_);
					if (ii > 0) {
						// 广告钱包记录
						Map<String, Object> adRecord = new HashMap<>();
						adRecord.put("advertisingInfoId", getSequence());
						adRecord.put("advertisingInfoAddOrMinus", "-");
						adRecord.put("advertisingInfoUserId", coverMemberAccount);
						adRecord.put("advertisingInfoMoney", money);
						adRecord.put("advertisingInfoFrom", "追加分红");
						getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
					}
					// ----- 设置团队信息 -----
					Map<String, Object> myTeam = new HashMap<>();
					// 是没有填写过得,需要记录:
					// 必须填写，设计成推荐人为88888888的时候，默认是平台直推，正常情况下直推必须是已经激活的会员
					if ("88888888".equals(memberAccount)) {
						myTeam.put("teamParentId", "0");
					} else {
						// 设置推荐人ID(被推荐人隶属那个团队)
						myTeam.put("teamParentId", memberAccount);
					}
					myTeam.put("teamId", getSequence()); // 主键ID
					myTeam.put("teamUserName", userName);
					myTeam.put("teamUserPhone", memberAccount);
					// 设置被推荐人ID
					myTeam.put("teamRecommendedUserId", coverMemberAccount);
					// 第一个有效期内肯定是有效直推
					myTeam.put("teamType", "Y");
					// 向团队表中插入数据:
					int i1 = getBaseDao().insert("ZxMyTeamMapper.saveMyTeamInfo", myTeam);
					if (i1 > 0) {
						// 设置之后启动定时器,去监控此人下的人是否有直推有效人员,如果有则没问题,如果没有则将推荐人修改为无效人员
						output.setCode("0");
						output.setMsg("追加分红成功");
						return;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 查询广告分红详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryShareOutBonusDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("AdShareOutBonusServiceImpl.queryShareOutBonusDetail, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryShareOutBonusDetail", params);
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 查询是否第一次绑定推荐人
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryIsFirstRecommend(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("AdShareOutBonusServiceImpl.queryIsFirstRecommend, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryIsFirstRecommend", params);
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 根据账号查询推荐人信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryRecommendInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("AdShareOutBonusServiceImpl.queryRecommendInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (null != item) {
				String memberAccount = (String) item.get("memberAccount");
				if (memberAccount.equals(params.get("coverMemberAccount"))) {
					output.setCode("-1");
					output.setMsg("推荐人不能为当前用户！");
					return;
				}
				// 判断是否为正确的手机号
				String match = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
				if (!memberAccount.matches(match)) {
					output.setCode("-1");
					output.setMsg("推荐人不存在！");
					return;
				}
			} else {
				output.setCode("-1");
				output.setMsg("推荐人不存在！");
				return;
			}
			output.setCode("0");
			output.setItem(item);
		} catch (Exception ex) {
			LOGGER.error("查询失败: " + ex);
		}
	}

	/**
	 * 申请分红
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void applyShareOutBonus(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> outBonusInfo = new HashMap<>();
			String userId = (String) params.get("memberAccount");
			// 申请分红金额:
			Integer money_  = (Integer) params.get("money");
			if (Integer.MAX_VALUE < money_  || Integer.MIN_VALUE > money_) {
				output.setCode("-1");
				output.setMsg("请输入正确的金额！");
				return;
			}
			Double money = money_ / 1.0;
			// 申请分红时间周期
			Integer date = (Integer) params.get("date");
			// 判断是否有正在执行的分红任务
			Map<String, Object> taskByUserIdAndMarkS = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.findTaskByUserIdAndMarkS", params);
			if (taskByUserIdAndMarkS != null) {
				output.setCode("-1");
				output.setMsg("分红任务正在进行,请结束之后再申请下一个！");
				return;
			}
			// 查询用户信息
			Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", params);
			if (Optional.ofNullable(item).isPresent()) {
				Double zxMyWalletAmount = Double.valueOf(item.get("walletBalance") + "");
				// 扣除的手续费
				Double poundage = money * 0.01;
				if(zxMyWalletAmount < poundage){
					output.setCode("-1");
					output.setMsg("余额不足，请及时充值！");
					return;
				}
			}
			params.put("coverMemberAccount", userId);
			// 1.查询追加可分红的金额:
			@SuppressWarnings("unchecked")
			Map<String, Object> bonusMoney = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryShareOutBonusDetail", params);
			if (bonusMoney != null) {
				// 判断申请分红金额是否大于可用金额
				BigDecimal bMoney = (BigDecimal) bonusMoney.get("adShareOutBonusMoney");
				if (bMoney.doubleValue() < money) {
					output.setCode("-1");
					output.setMsg("申请分红金额大于可用分红金额！");
					return;
				}
			}
			// 2.扣除分红表中剩余额度的钱数
			outBonusInfo.put("adShareOutBonusInfoId", getSequence()); // 主键ID
			outBonusInfo.put("adShareOutBonusInfoUserId", userId);    // 申请分红用户ID
			outBonusInfo.put("adShareOutBonusInfoDayNum", date);      // 申请分红倒计时天数
			outBonusInfo.put("adShareOutBonusInfoMoney", money);      // 申请分红钱数
			// 设置分红开始标志
			outBonusInfo.put("adShareOutBonusInfoDone", "S");
			// 新增申请分红记录
			int i = insert(outBonusInfo);
			if(i > 0){
				output.setCode("0");
				output.setMsg("分红成功！");
				// 添加推送消息
				Map<String, Object> map = new HashMap<>();
				map.put("id", getSequence());
				map.put("messageTitle", "分红成功");
				map.put("messageStatus", "1");
				map.put("messageContent", "分红成功，请到钱包和广告费明细查询！");
				map.put("messageType", com.chmei.nzbdata.util.Constants.MESSAGE_TYPE_1001);
				map.put("memberAccount", userId);
				// 添加推送消息
				getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
				return;
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 申请分红之前的查询条件接口,比如 直推有效人数,广告费余额额度,计算分红周期
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void findApplyForAdvertisingInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 创建一个Map集合,返回app端数据:
			Map<String,Object> map = new HashMap<>();
			String userId = (String) params.get("memberAccount");
			params.put("coverMemberAccount", userId);
			// 1.查询追加可分红的金额:
			@SuppressWarnings("unchecked")
			Map<String, Object> bonus = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryShareOutBonusDetail", params);
			BigDecimal adShareOutBonusMoney = (BigDecimal) bonus.get("adShareOutBonusMoney");
			if (Optional.ofNullable(bonus).isPresent()) {
				// 分红可用余额:
				map.put("usedMoney", adShareOutBonusMoney);
			}
			// 2.根据用户ID,查询此用户下有多少有效人员
			int size = iZxMyTeamService.checkedDay(input, output);
			// 2.1 查询完成了几个分红周期:
			long count = findTaskCountByMemberAccount(input, output);
			// 3.1 新会员分红满10个周期后，如仍未有一位有效直推，再次申请分红的周期延长为30天。
			if(count > 10L && size == 0){
				map.put("days",30);
				map.put("persons",0);
				output.setCode("0");
				output.setMsg("查询成功!");
				output.setItem(map);
				return;
			} else {
				// 3. 计算分红天数: 新会员分红周期为10天，推荐一位有效会员，周期为8天，两位6天，3位4天，4位2天，5位以上1天
				// 3.2 30天后，如果你2个直推没再推荐新人，你2000的额度不滑落，但是分红周期由6天重新滑落到10天。如果是分够了10轮，分红周期滑落到30天。
				int day = 10;
				switch (size){
					case 1 :
						day = 8;
						break;
					case 2 :
						day = 6;
						break;
					case 3 :
						day = 4;
						break;
					case 4 :
						day = 2;
						break;
					default:
						day = 1;
						break;
				}
				map.put("days",day);
				map.put("persons",size);
				output.setCode("0");
				output.setMsg("查询成功!");
				output.setItem(map);
				return;
			}
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 根据当前登录用户账号查询分红任务完成了几次
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public long findTaskCountByMemberAccount(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		long taskCount = 0;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("memberAccount", params.get("memberAccount")); // 申请分红用户账号
			map.put("adShareOutBonusInfoDone", Constants.D); // 申请分红是否完成(D 已经分红结束完成 S 已经开始,不结束不允许开始下一个,N 没有分红任务)'
			// 查询我的团队信息
			taskCount = (long) getBaseDao().queryForObject("ShareOutBonusMapper.findTaskCountByMemberAccount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskCount;
	}

	/**
	 * 广告分红定时任务
	 * @param outBonusInfo
	 * @return
	 */
	@SuppressWarnings("all")
	public int insert(Map<String, Object> outBonusInfo){
		Date date = new Date();
		outBonusInfo.put("adShareOutBonusInfoStart", date);
		outBonusInfo.put("adShareOutBonusInfoDate", date);
		int i = getBaseDao().insert("ShareOutBonusMapper.saveAdShareOutBonusInfo", outBonusInfo);
		if(i > 0){
			// 扣除可用额度:
			Map<String, Object> bonusMoney = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.findAdShareOutBonusMoney", outBonusInfo);
			Map<String, Object> one = new HashMap<>();
			double mo = Double.valueOf(bonusMoney.get("adShareOutBonusMoney")+"") -
					Double.valueOf(outBonusInfo.get("adShareOutBonusInfoMoney")+"");
			one.put("adShareOutBonusMoney", mo);
			Map<String, Object> two = new HashMap<>();
			two.put("adShareOutBonusUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
			one.putAll(two);
			getBaseDao().update("ShareOutBonusMapper.updateShareOutBonusInfo", one);
			// 3.在确认分红成功之后,设置当前登录人为有效人员
			Map<String, Object> param = new HashMap<>();
			param.put("memberAccount", outBonusInfo.get("adShareOutBonusInfoUserId")); // 被推荐人账户
			Map<String, Object> user1 = (Map<String, Object>) getBaseDao().queryForObject(
					"MemberMapper.queryMemberDetail", param);
			Map<String, Object> user = new HashMap<>();
			// 扣除当前人手续费:
			if(user1 != null){
				user.put("memberAccount", outBonusInfo.get("adShareOutBonusInfoUserId"));
				user.put("walletBalance", Double.valueOf(user1.get("walletBalance")+"") -
						Double.valueOf(outBonusInfo.get("adShareOutBonusInfoMoney")+"") * 0.01);
				// 设置当前人为有效人员
				Date nowDate = new Date();
				long nowTime = nowDate.getTime();
				// 注册时间
				String startDate = (String) user1.get("crtTime");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ParsePosition pos = new ParsePosition(0);
				Date registerDate = formatter.parse(startDate, pos);
				long oldTime = registerDate.getTime();
				// 证明是在30天以内进行的激活,为有效人员
				if (((nowTime - oldTime) / (24 * 3600 * 1000) <= 30)){
					user.put("zxMyTeamId", 1);
				}
				int i1 = getBaseDao().update("MemberMapper.updateMemberBalance", user);
				if(i1 > 0){
					// 钱包扣除金额记录:
					Map<String, Object> walletMoneyInfo = new HashMap<>();
					walletMoneyInfo.put("walletInfoId", getSequence());
					walletMoneyInfo.put("walletInfoAddOrMinus", "-");
					walletMoneyInfo.put("walletInfoUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
					// 记录扣除当人金额的 1% 手续费
					walletMoneyInfo.put("walletInfoMoney", Double.valueOf(outBonusInfo.get("adShareOutBonusInfoMoney")+"") * 0.01);
					walletMoneyInfo.put("walletInfoFrom", "追加分红");
					getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
					if(1 == (int) user1.get("zxMyTeamId")){
						Map<String, Object> record = new HashMap<>();
						record.put("teamType", "Y");
						record.put("teamRecommendedUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
						int i_ = getBaseDao().update("ZxMyTeamMapper.updateMyTeamInfo", record);
					}
					// 当前时间的毫秒数 + 天数毫秒数  = 结束时间
					System.out.println(outBonusInfo.get("adShareOutBonusInfoDayNum"));
					long daySpan = 24 * 3600 * 1000 * Integer.valueOf(outBonusInfo.get("adShareOutBonusInfoDayNum")+"");
					final Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							LOGGER.info("用户{ " + outBonusInfo.get("adShareOutBonusInfoUserId") +" }分红任务开始");
							Map<String, Object> param = new HashMap<>();
							param.put("memberAccount", outBonusInfo.get("adShareOutBonusInfoUserId")); // 被推荐人账户
							Map<String, Object> userRun = (Map<String, Object>) getBaseDao().queryForObject(
									"MemberMapper.queryMemberDetail", param);
							// 更新任务结束标志
							Map<String, Object> record = new HashMap<>();
							record.put("adShareOutBonusInfoDone", "D");
							record.put("adShareOutBonusInfoUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
							int i = getBaseDao().update("ShareOutBonusMapper.updateAdShareOutBonusInfo", record);
							if(i > 0){
								// 每个分红周期结束后，申请分红金额的10%进入广告费钱包，5%进入红包。
								Map<String, Object> user = new HashMap<>();
								user.put("memberAccount", outBonusInfo.get("adShareOutBonusInfoUserId"));
								user.put("redEnveBalance", Double.valueOf(userRun.get("redEnveBalance")+"") + Double.valueOf(outBonusInfo.get("adShareOutBonusInfoMoney")+"") * 0.05);
								user.put("advertisingFee", Double.valueOf(userRun.get("advertisingFee")+"") + Double.valueOf(outBonusInfo.get("adShareOutBonusInfoMoney")+"") * 0.1);
								int ii = getBaseDao().update("MemberMapper.updateMemberBalance", user);
								if(ii > 0){
									// 并记录红包和广告费金额:
									Map<String, Object> redRecord = new HashMap<>();
									redRecord.put("redPacketInfoId", getSequence());
									// 分红赠送获取的钱数
									redRecord.put("redPacketInfoAddOrMinus", "+");
									redRecord.put("redPacketInfoUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
									redRecord.put("redPacketInfoMoney", Double.valueOf((String) outBonusInfo.get("adShareOutBonusInfoMoney")) * 0.05);
									redRecord.put("redPacketInfoFrom", "分红赠送");
									getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
									// 获取分红的钱数
									Map<String, Object> adRecord = new HashMap<>();
									adRecord.put("advertisingInfoId", getSequence());
									adRecord.put("advertisingInfoAddOrMinus", "+");
									adRecord.put("advertisingInfoUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
									adRecord.put("advertisingInfoMoney", Double.valueOf((String) outBonusInfo.get("adShareOutBonusInfoMoney")) * 0.1);
									adRecord.put("advertisingInfoFrom", "获得分红");
									getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
									// 将可用分红额度恢复
									Map<String, Object> bonusMoney = (Map<String, Object>) getBaseDao().queryForObject(
											"ShareOutBonusMapper.findAdShareOutBonusMoney", outBonusInfo);
									Map<String, Object> record_ = new HashMap<>();
									String adShareOutBonusMoney = (String) bonusMoney.get("adShareOutBonusMoney");
									String  outAdShareOutBonusInfoMoney = (String) outBonusInfo.get("adShareOutBonusInfoMoney");
									record_.put("adShareOutBonusMoney", Double.valueOf(adShareOutBonusMoney) + Double.valueOf(outAdShareOutBonusInfoMoney));
									record_.put("adShareOutBonusUserId", outBonusInfo.get("adShareOutBonusInfoUserId"));
									int j = getBaseDao().update("ShareOutBonusMapper.updateShareOutBonusInfo", record_);
									// 将交易表中的取消数据删除
									Map<String, Object> examplee = new HashMap<>();
									examplee.put("dealUserId", userRun.get("memberAccount"));
									examplee.put("dealMoneyMark", "0");
									getBaseDao().delete("AssetsTransferMapper.deleteDealInfo", examplee);
									LOGGER.info("用户{ " + outBonusInfo.get("adShareOutBonusInfoUserId") +" }分红任务结束");
								}
							}
							timer.cancel();
						}
					},daySpan);
					return i1;
				}
			}
		}
		return -1;
	}

}
