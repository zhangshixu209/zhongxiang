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
					double advertisingMoney = (double) item.get("advertisingFee");
					if(advertisingMoney < Constants.TRANSFER_MONEY){
						output.setCode("-1");
						output.setMsg("广告费钱包余额不足!");
						return;
					}
					// 判断是否有分红任务 去最后一条
					List<Map<String, Object>> shareOutBonusInfo = getBaseDao().queryForList("ShareOutBonusMapper.findTaskByUserIdAndMarkS", params);
					//查看广告费 最近一次的情况
					List<Map<String, Object>> zxAppDeals_ = getBaseDao().queryForList(
							"AssetsTransferMapper.queryNewAssetsTransferList", params);
					// 无广告费分红任务:
					long count = iAdShareOutBonusService.findTaskCountByMemberAccount(input, output);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ParsePosition pos = new ParsePosition(0); // 日期转换
					if(count == 0){
						// 没有分红任务的也可以进行发布交易,要一个月发布一次广告费
						if(zxAppDeals_ != null && zxAppDeals_.size() > 0){
							String startDate = (String) zxAppDeals_.get(0).get("updateDealDate");
							Date starToDate = formatter.parse(startDate, pos);
							long time = starToDate.getTime();
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
									String delaDate = (String) zxAppDeals_.get(0).get("updateDealDate");
									Date starToDate = formatter.parse(delaDate, pos);
									long time = starToDate.getTime();
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
					user.put("advertisingFee", advertisingMoney - Constants.TRANSFER_MONEY);
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
						record_.put("dealMoney", Constants.TRANSFER_MONEY);
						record_.put("dealMoneyMark", "2");
						record_.put("dealUserId", memberAccount);
						int insert = getBaseDao().insert("AssetsTransferMapper.saveDealInfo", adRecord);
						if(insert > 0){
							output.setCode("0");
							output.setMsg("发布成功!");
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

}
