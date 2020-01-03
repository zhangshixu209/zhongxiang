package com.chmei.nzbdata.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.zxfriend.service.IAdShareOutBonusService;
import com.chmei.nzbdata.zxfriend.service.IZxAssetsTransferService;
import com.chmei.nzbdata.zxfriend.service.IZxMyTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 资产转让dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月19日 11点40分
 */
@Service("zxAssetsTransferService")
public class ZxAssetsTransferServiceImpl extends BaseServiceImpl implements IZxAssetsTransferService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxAssetsTransferServiceImpl.class);

	@Resource
	IAdShareOutBonusService iAdShareOutBonusService;

	@Resource
	IZxMyTeamService iZxMyTeamService;

	/**
	 * 资产转让列表查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryAssetsTransferList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		Map<String,Object> map = new HashMap<>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			map.put("memberAccount", item.get("memberAccount"));     // 用户账户
			map.put("walletMoney", item.get("walletBalance"));       // 钱包余额
			map.put("advertisingMoney", item.get("advertisingFee")); // 广告费余额
			int total = getBaseDao().getTotalCount("AssetsTransferMapper.queryAssetsTransferCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList(
						"AssetsTransferMapper.queryAssetsTransferList", params);
				output.setItems(list);
			}
			output.setItem(map);
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 发布资产转让信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void releaseTransferInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String memberAccount = (String) params.get("memberAccount"); // 当前用户账号
			params.put("coverMemberAccount", memberAccount);
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			// 查询广告分红信息
			Map<String, Object> shareOutBonus = (Map<String, Object>) getBaseDao().queryForObject(
					"ShareOutBonusMapper.queryShareOutBonusDetail", params);
			if (null != shareOutBonus) {
				if (Optional.ofNullable(item).isPresent()) {
					// 交易金额固定为100元
					// 判断广告费余额是否充足:
					String advertisingMoney = (String) item.get("advertisingFee");
					if(Double.valueOf(advertisingMoney) < Constants.TRANSFER_MONEY){
						output.setCode("-1");
						output.setMsg("广告费钱包余额不足!");
						return;
					}
					// 判断是否有分红任务 去最后一条
					List<Map<String, Object>> shareOutBonusInfo = getBaseDao().queryForList("ShareOutBonusMapper.findTaskByUserIdAndMarkS", params);
					//查看广告费 最近一次的情况
					List<Map<String, Object>> zxAppDeals_ = getBaseDao().queryForList(
							"AssetsTransferMapper.queryDealRecordDate", params);
					// 无广告费分红任务:
					long count = iAdShareOutBonusService.findTaskCountByMemberAccount(input, output);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ParsePosition pos = new ParsePosition(0); // 日期转换
					if(count == 0){
						// 没有分红任务的也可以进行发布交易,要一个月发布一次广告费
						if(zxAppDeals_ != null && zxAppDeals_.size() > 0){
							Date startDate = (Date) zxAppDeals_.get(0).get("dealRecordDate");
							long time = startDate.getTime();
							if(-((time - System.currentTimeMillis()) / (24 * 3600 * 1000)) <= 30){
								output.setCode("-1");
								output.setMsg("您的账号每月只能发布一次!");
								return;
							}
						}
					} else {
						// 有广告分红任务
						// 2.根据用户ID,查询此用户下有多少有效人员
						int size = iZxMyTeamService.checkedDay(input, output);
						//前10个周期 按照周期走
						if (count < 10L) {
							if(shareOutBonusInfo != null && shareOutBonusInfo.size() > 0){
								Map<String, Object> share = shareOutBonusInfo.get(0); // 获取第一条
								String startDate = (String) share.get("adShareOutBonusInfoStart"); // 申请分红开始时间
								Date starToDate = formatter.parse(startDate, pos);
								int endDay = (int) share.get("adShareOutBonusInfoDayNum"); // 申请分红倒计时天数
								if(System.currentTimeMillis() < starToDate.getTime() + (endDay * 24 * 3600 * 1000)){
									output.setCode("-1");
									output.setMsg("请于下一分红周期再次发布!");
									return;
								}
							}
						} else {
							//没有有效直推 每个月发布一次
							if (size == 0) {
								if(zxAppDeals_ != null && zxAppDeals_.size() > 0){
									Date delaDate = (Date) zxAppDeals_.get(0).get("dealRecordDate");
									long time = delaDate.getTime();
									if(-((time - System.currentTimeMillis()) / (24 * 3600 * 1000)) <= 30){
										output.setCode("-1");
										output.setMsg("您的账号每月只能发布一次!");
										return;
									}
								}
							} else {
								//有有效直推 按照发布广告费周期走 查看是不是在上次周期时间之内
								if (shareOutBonusInfo != null && shareOutBonusInfo.size() >0){
										Map<String, Object> share = shareOutBonusInfo.get(0); // 获取第一条
										String startDate = (String) share.get("adShareOutBonusInfoStart"); // 申请分红开始时间
										Date starToDate = formatter.parse(startDate, pos);
										int endDay = (int) share.get("adShareOutBonusInfoDayNum"); // 申请分红倒计时天数
									if(System.currentTimeMillis() < starToDate.getTime() + (endDay * 24 * 3600 * 1000)){
										output.setCode("-1");
										output.setMsg("请于下一分红周期再次发布!");
										return;
									}
								}
							}
						}
					}
					// 判断当前人是否取消或者有发布的,如果有而且还在分红任务内,则不允许在此进行发布交易
					List<Map<String, Object>> zxAppDeals = getBaseDao().queryForList(
							"AssetsTransferMapper.queryNewAssetsTransferList", params);
					if(zxAppDeals != null && zxAppDeals.size() > 0){
						output.setCode("-1");
						output.setMsg("请于下一周期再次发布!");
						return;
					}
					// 扣除当前人广告费金额
					Map<String, Object> user = new HashMap<>();
					user.put("memberAccount", memberAccount);
					user.put("advertisingFee", Double.valueOf(advertisingMoney) - Constants.TRANSFER_MONEY);
					int i = getBaseDao().update("MemberMapper.updateMemberBalance", user);
					if(i > 0){
						// 记录广告费扣款记录:
						Map<String, Object> adRecord = new HashMap<>();
						adRecord.put("advertisingInfoId", getSequence());
						adRecord.put("advertisingInfoAddOrMinus", "-");
						adRecord.put("advertisingInfoUserId", memberAccount);
						adRecord.put("advertisingInfoMoney", Constants.TRANSFER_MONEY);
						adRecord.put("advertisingInfoFrom", "广告费转让");
						getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
						// 进行记录转让信息
						Map<String, Object> record_ = new HashMap<>();
						record_.put("dealId", getSequence()); // 资产转让ID
						record_.put("dealMoney", Constants.TRANSFER_MONEY);
						record_.put("dealMoneyMark", "2");
						record_.put("dealUserId", memberAccount);
						int insert = getBaseDao().insert("AssetsTransferMapper.saveDealInfo", record_);
						if(insert > 0){
							output.setCode("0");
							output.setMsg("发布成功!");
							return;
						} else {
							output.setCode("-1");
							output.setMsg("发布失败!");
							return;
						}
					}
				}
			} else {
				output.setCode("-1");
				output.setMsg("请先去激活分红模块!");
				return;
			}
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 购买资产转让信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buyTransferFee(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 使用我的钱包购买:
			// 扣除金额,并做金额扣款记录,之后将信息记录到取消表中两份,一份是当前人的购买记录,一份是购买另外人的记录
			Map<String, Object> user =  (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			String v = (String) user.get("walletBalance");
			if(Double.valueOf(v) < 100d){
				output.setCode("-1");
				output.setMsg("钱包余额不足!");
				return;
			}
			Map<String, Object> userByed = new HashMap<>();
			userByed.put("memberAccount", params.get("fromMemberAccount"));
			// 2 增加出售方我的钱包金额, 广告费在发布时已经扣除
			Map<String, Object> userBy = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", userByed);
			userByed.put("walletBalance", Double.valueOf(userBy.get("walletBalance")+"") + 100d);
			int j = getBaseDao().update("MemberMapper.updateMemberBalance", userByed);
			if (j > 0) {
				// 1.1 进行钱包金额记录 出售方
				Map<String, Object> walletMoneyInfo = new HashMap<>();
				walletMoneyInfo.put("walletInfoId", getSequence());
				walletMoneyInfo.put("walletInfoAddOrMinus", "+");
				walletMoneyInfo.put("walletInfoUserId", params.get("fromMemberAccount"));
				walletMoneyInfo.put("walletInfoMoney", 100d);
				walletMoneyInfo.put("walletInfoFrom", "购买广告费");
				getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
			}
			// 成交后，购买方减少我的钱包100，广告费钱包增加100，出售方刚好相反。
			// 1 减少购买方我的钱包金额,增加广告费钱包金额
			Map<String, Object> userBuy = new HashMap<>();
			userBuy.put("memberAccount", params.get("memberAccount"));
			Map<String, Object> userByt = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", userBuy);
			// 自己买自己的话金额不增不减
			userBuy.put("walletBalance", Double.valueOf(userByt.get("walletBalance")+"") - 100d);
			userBuy.put("advertisingFee", Double.valueOf(userByt.get("advertisingFee")+"") + 100d);
			int i = getBaseDao().update("MemberMapper.updateMemberBalance", userBuy);
			if (i > 0) {
				// 1.1 进行钱包金额记录 购买方
				Map<String, Object> walletMoneyInfo = new HashMap<>();
				walletMoneyInfo.put("walletInfoId", getSequence());
				walletMoneyInfo.put("walletInfoAddOrMinus", "-");
				walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
				walletMoneyInfo.put("walletInfoMoney", 100d);
				walletMoneyInfo.put("walletInfoFrom", "购买广告费");
				getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
				// 1.2 进行广告费金额记录 购买方
				Map<String, Object> adRecord = new HashMap<>();
				adRecord.put("advertisingInfoId", getSequence());
				adRecord.put("advertisingInfoAddOrMinus", "+");
				adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
				adRecord.put("advertisingInfoMoney", 100d);
				if(params.get("memberAccount").equals(params.get("fromMemberAccount"))){
					adRecord.put("advertisingInfoFrom", "出售广告费");
				} else {
					adRecord.put("advertisingInfoFrom", "购买广告费");
				}
				getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
			}
			// 先查询被购买的人的发布记录的时间
			params.put("dealMoneyMark", "2");
			List<Map<String, Object>> zxAppDeals = getBaseDao().queryForList(
					"AssetsTransferMapper.queryNewAssetsTransferList", params);
			// 记录购买信息到取消表中,之后删除那个发布表信息
			Map<String, Object> record_ = new HashMap<>();
			record_.put("zxAppDealRecordId", getSequence());
			record_.put("zxAppDealRecordDate", (zxAppDeals != null && zxAppDeals.size() > 0) ? zxAppDeals.get(0).get("dealDate") : new Date());
			record_.put("zxAppDealRecordUserId", params.get("memberAccount"));
			record_.put("zxAppDealRecordMoney", 100D);
			// 此字段是标注字段 0 是取消记录 1 自己购买记录 2 是被购买记录
			record_.put("zxAppDealType", "2");
			int k = getBaseDao().insert("AssetsTransferMapper.saveZxDealRecordInfo", record_);
			if(k > 0){
				// 删除发布记录
				Map<String, Object> dealExample = new HashMap<>();
				dealExample.put("memberAccount", params.get("fromMemberAccount"));
				int m = getBaseDao().delete("AssetsTransferMapper.deleteDealInfo", dealExample);
				if(m > 0){
					// 记录被购买人信息
					Map<String, Object> cancelRecorded = new HashMap<>();
					cancelRecorded.put("zxAppDealRecordId", getSequence());
					cancelRecorded.put("zxAppDealRecordDate", new Date());
					cancelRecorded.put("zxAppDealRecordUserId", params.get("fromMemberAccount"));
					cancelRecorded.put("zxAppDealRecordMoney", 100D);
					// 此字段是标注字段 0 是取消记录 1 自己购买记录 2 是被购买记录
					cancelRecorded.put("zxAppDealType", "1");
					getBaseDao().insert("AssetsTransferMapper.saveZxDealRecordInfo", cancelRecorded);
				}
			}
			output.setCode("0");
			output.setMsg("购买成功!");
			return;
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 取消资产转让信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cancelTransferInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 查询发布交易的时间
			List<Map<String, Object>> zxAppDeals = getBaseDao().queryForList(
					"AssetsTransferMapper.queryAssetsTransferList", params);
			Map<String, Object> record = new HashMap<>();
			record.put("dealId", params.get("dealId"));
			record.put("dealMoneyMark", "0"); // 取消发布
			record.put("memberAccount", params.get("memberAccount"));
			int i = getBaseDao().update("AssetsTransferMapper.updateDealInfo", record);
			if (i > 0) {
				// 将取消信息插入到取消表中记录:
				Map<String, Object> record_ = new HashMap<>();
				record_.put("zxAppDealRecordId", getSequence());
				record_.put("zxAppDealRecordDate", (zxAppDeals != null && zxAppDeals.size() > 0) ? zxAppDeals.get(0).get("dealDate") : new Date());
				record_.put("zxAppDealRecordUserId", params.get("memberAccount"));
				record_.put("zxAppDealRecordMoney", 100D);
				// 此字段是标注字段 0 是取消记录 1 自己购买记录 2 是被购买记录
				record_.put("zxAppDealType", "0");
				getBaseDao().insert("AssetsTransferMapper.saveZxDealRecordInfo", record_);
				// 取消成功之后将费用进行回退
				Map<String, Object> user = (Map<String, Object>) getBaseDao().
						queryForObject("MemberMapper.queryMemberDetail", params);
				String advertisingMoney = (String) user.get("advertisingFee");
				Map<String, Object> user_ = new HashMap<>();
				user_.put("memberAccount", params.get("memberAccount"));
				user_.put("advertisingFee", Double.valueOf(advertisingMoney) + 100d);
				int i1 = getBaseDao().update("MemberMapper.updateMemberBalance", user_);
				if(i1 > 0){
					// 记录广告费扣款记录:
					Map<String, Object> adRecord = new HashMap<>();
					adRecord.put("advertisingInfoId", getSequence());
					adRecord.put("advertisingInfoAddOrMinus", "+");
					adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
					adRecord.put("advertisingInfoMoney", 100d);
					adRecord.put("advertisingInfoFrom", "取消广告费转让");
					getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
				}
				output.setCode("0");
				output.setMsg("取消成功!");
				return;
			} else {
				output.setCode("-1");
				output.setMsg("取消失败!");
				return;
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 交易记录列表查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryDealRecordList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		Map<String,Object> map = new HashMap<>();
		try {
			int total = getBaseDao().getTotalCount("AssetsTransferMapper.queryDealRecordCount", params);
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			map.put("memberAccount", item.get("memberAccount"));     // 用户账户
			map.put("walletMoney", item.get("walletBalance"));       // 钱包余额
			map.put("advertisingMoney", item.get("advertisingFee")); // 广告费余额
			params.put("dealUserId", params.get("memberAccount")); // 查询转让表
			List<Map<String, Object>> recordList = getBaseDao().queryForList("AssetsTransferMapper.queryDealRecordList", params);
			List<Map<String, Object>> list = getBaseDao().queryForList("AssetsTransferMapper.queryAssetsTransferList", params);
			if (null != list && list.size() > 0) {
				Map<String, Object> zxAppDeal = list.get(0);
				Map<String, Object> record = new HashMap<>();
				record.put("dealRecordId", zxAppDeal.get("dealId"));
				record.put("dealRecordMoney", zxAppDeal.get("dealMoney"));
				record.put("dealRecordUserId", zxAppDeal.get("dealUserId"));
				record.put("dealRecordDate", zxAppDeal.get("dealDate"));
				record.put("dealRecordType", "3");
				recordList.add(record);
			}
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			ParsePosition pos = new ParsePosition(0); // 日期转换
			recordList.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
				long beginMillisecond = ((Date) o1.get("dealRecordDate")).getTime();
				long endMillisecond = ((Date) o2.get("dealRecordDate")).getTime();
				if(beginMillisecond > endMillisecond){
					return -1;
				}
				return 1;
			});
			output.setItems(recordList);
			output.setItem(map);
			output.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

}
