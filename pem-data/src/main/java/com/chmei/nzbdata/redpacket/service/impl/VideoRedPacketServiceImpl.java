package com.chmei.nzbdata.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.redpacket.service.IVideoRedPacketService;
import com.chmei.nzbdata.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 视频红包dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月04日 14点56分
 */
@Service("videoRedPacketService")
public class VideoRedPacketServiceImpl extends BaseServiceImpl implements IVideoRedPacketService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(VideoRedPacketServiceImpl.class);
	private static final String WALLET_TYPE = "0"; // 钱包
	private static final String RED_PACKET_TYPE = "1"; // 红包
	private static final String ADVERSING_TYPE = "2"; // 广告钱

	/**
	 * 视频红包发布新增
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveVideoRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("VideoRedPacketServiceImpl.saveVideoRedPacketInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try{
			int redPacketVideoCount = (int) params.get("redPacketVideoCount");
			if(redPacketVideoCount < 50){
				output.setMsg("红包个数不能小于50个");
				return;
			}
			String redPacketVideoType = (String) params.get("redPacketVideoType"); // 发红包类型
			OutputDTO outputDTO = checkWalletMoney(input, output); // 校验钱包余额
			if("0".equals(outputDTO.getCode())){
				@SuppressWarnings("unchecked")
				Map<String, Object> item = (Map<String, Object>) getBaseDao().
						queryForObject("MemberMapper.queryMemberBalanceDetail", input.getParams());
				String walletBalance = (String) item.get("walletBalance"); // 钱包余额
				String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
				String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
				BigDecimal redPacketVideoMoneyCount = (BigDecimal) params.get("redPacketVideoMoneyCount");
				params.put("redPacketVideoId", getSequence()); // 获取id
				params.put("redPacketVideoUserId", params.get("memberAccount")); // 获取用户id
				int count = getBaseDao().insert("VideoRedPacketMapper.saveVideoRedPacketInfo", params);
				boolean flag = false;
				if (count > 0) {
					Map<String, Object> map = new HashMap<>();
					// 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
					switch (redPacketVideoType) {
						case WALLET_TYPE: // 钱包扣款
							double walletAmount = Double.valueOf(walletBalance) - redPacketVideoMoneyCount.doubleValue();
							map.put("walletBalance", walletAmount < 0 ? 0 : walletAmount);
							map.put("memberAccount", params.get("memberAccount")); // 会员账户
							int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
							if (i <= 0) {
								flag = true;
							} else {
								// 钱包扣除金额记录:
								Map<String, Object> walletMoneyInfo = new HashMap<>();
								walletMoneyInfo.put("walletInfoId", getSequence());
								walletMoneyInfo.put("walletInfoAddOrMinus", "-");
								walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
								walletMoneyInfo.put("walletInfoMoney", redPacketVideoMoneyCount);
								walletMoneyInfo.put("walletInfoFrom", "发布视频广告");
								getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
							}
							break;
						case RED_PACKET_TYPE: // 红包扣款
							double hbAmount = Double.valueOf(redEnveBalance) - redPacketVideoMoneyCount.doubleValue();
							map.put("redEnveBalance", hbAmount < 0 ? 0 : hbAmount);
							map.put("memberAccount", params.get("memberAccount")); // 会员账户
							int j = getBaseDao().update("MemberMapper.updateMemberBalance", map);
							if (j <= 0) {
								flag = true;
							} else {
								// 红包钱包记录
								Map<String, Object> redRecord = new HashMap<>();
								redRecord.put("redPacketInfoId", getSequence());
								redRecord.put("redPacketInfoAddOrMinus", "-");
								redRecord.put("redPacketInfoUserId", params.get("memberAccount"));
								redRecord.put("redPacketInfoMoney", redPacketVideoMoneyCount);
								redRecord.put("redPacketInfoFrom", "发布视频广告");
								getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
							}
							break;
						case ADVERSING_TYPE: // 广告钱包扣款
							double advertisingAmount = Double.valueOf(advertisingFee) - redPacketVideoMoneyCount.doubleValue();
							map.put("advertisingFee", advertisingAmount < 0 ? 0 : advertisingAmount);
							map.put("memberAccount", params.get("memberAccount")); // 会员账户
							int k = getBaseDao().update("MemberMapper.updateMemberBalance", map);
							if (k <= 0) {
								flag = true;
							} else {
								// 广告钱包记录
								Map<String, Object> adRecord = new HashMap<>();
								adRecord.put("advertisingInfoId", getSequence());
								adRecord.put("advertisingInfoAddOrMinus", "-");
								adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
								adRecord.put("advertisingInfoMoney", redPacketVideoMoneyCount);
								adRecord.put("advertisingInfoFrom", "发布视频广告");
								getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
							}
							break;
						default:
							break;
					}
					if (flag) {
						// 扣款失败,不能发布红包,直接数据库删除红包记录:
						String id = (String) params.get("redPacketVideoId");
						int i = getBaseDao().delete("VideoRedPacketMapper.delVideoRedPacketInfo", id);
						if(i <= 0){
							LOGGER.error("扣款失败,用户 {} " + params.get("memberAccount") + "删除发布红包 {} " + id + "失败!");
						}
						output.setCode("-1");
						output.setMsg("发布红包失败");
					}
					@SuppressWarnings("unchecked")
					Map<String, Object> stringObjectMap = (Map<String, Object>) getBaseDao().
							queryForObject("VideoRedPacketMapper.queryRedPacketLog", params);
					// 发布成功,直接进行红包的分配:
					List<Double> doubles = gradRedPacket(redPacketVideoCount, redPacketVideoMoneyCount.doubleValue());
					int i = this.insertListRedPacketImgInfo(doubles, (Long) params.get("redPacketVideoId"));
					if(i > 0 && stringObjectMap != null){
						sendBackRedPacketMoney(stringObjectMap); // 红包退回定时任务
						output.setCode("0");
						output.setMsg("信息发布成功!");
                        output.setItem(stringObjectMap);
					}
				} else {
					output.setCode("-1");
					output.setMsg("发布红包失败");
				}
			} else {
				output.setCode("-1");
				output.setMsg(outputDTO.getMsg()); // 返回错误消息
			}
		} catch (Exception ex) {
			LOGGER.error("发布红包失败: " + ex);
			output.setCode("-1");
			output.setMsg("发布红包失败");
		}
	}

	/**
	 * 红包退回定时任务
	 * @param params 入参
	 */
	@SuppressWarnings("unchecked")
	private void  sendBackRedPacketMoney(Map<String, Object> params){
		long daySpan = 24 * 3600 * 1000 * 3; // 24小时毫秒数 * 3, 72小时
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() { // 红包发布成功，执行定时任务
			@Override
			public void run() {
				LOGGER.info("==============红包过期退回任务开始================");
				Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
						"VideoRedPacketMapper.queryRedPacketMoneyStock", params);
				if (null != map_) {
					BigDecimal packetMoneyStock = (BigDecimal) map_.get("packetMoneyStock");
					if (packetMoneyStock.doubleValue() > 0) {
						Map<String, Object> item = (Map<String, Object>) getBaseDao().queryForObject(
								"MemberMapper.queryMemberBalanceDetail", params);
						if (null != item) {
							boolean flag = false;
							String walletBalance = (String) item.get("walletBalance"); // 钱包余额
							String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
							String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
							Map<String, Object> map = new HashMap<>();
							String redPacketType = (String) params.get("redPacketType");
							// 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
							switch (redPacketType) {
								case WALLET_TYPE: // 钱包退回
									double walletAmount = Double.valueOf(walletBalance) + packetMoneyStock.doubleValue();
									map.put("walletBalance", walletAmount < 0 ? 0 : walletAmount);
									map.put("memberAccount", params.get("memberAccount")); // 会员账户
									int i = getBaseDao().update("MemberMapper.updateMemberBalance", map);
									if (i <= 0) {
										flag = true;
									} else {
										// 删除过期的红包信息
										getBaseDao().delete("VideoRedPacketMapper.delRedPacketMoneyStock", params);
										// 钱包退回金额记录:
										Map<String, Object> walletMoneyInfo = new HashMap<>();
										walletMoneyInfo.put("walletInfoId", getSequence());
										walletMoneyInfo.put("walletInfoAddOrMinus", "+");
										walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
										walletMoneyInfo.put("walletInfoMoney", packetMoneyStock);
										walletMoneyInfo.put("walletInfoFrom", "视频广告红包过期退回");
										getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
									}
									break;
								case RED_PACKET_TYPE: // 红包退回
									double hbAmount = Double.valueOf(redEnveBalance) + packetMoneyStock.doubleValue();
									map.put("redEnveBalance", hbAmount < 0 ? 0 : hbAmount);
									map.put("memberAccount", params.get("memberAccount")); // 会员账户
									int j = getBaseDao().update("MemberMapper.updateMemberBalance", map);
									if (j <= 0) {
										flag = true;
									} else {
										// 删除过期的红包信息
										getBaseDao().delete("VideoRedPacketMapper.delRedPacketMoneyStock", params);
										// 红包退回钱包记录
										Map<String, Object> redRecord = new HashMap<>();
										redRecord.put("redPacketInfoId", getSequence());
										redRecord.put("redPacketInfoAddOrMinus", "+");
										redRecord.put("redPacketInfoUserId", params.get("memberAccount"));
										redRecord.put("redPacketInfoMoney", packetMoneyStock);
										redRecord.put("redPacketInfoFrom", "视频广告红包过期退回");
										getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
									}
									break;
								case ADVERSING_TYPE: // 广告钱包退回
									double advertisingAmount = Double.valueOf(advertisingFee) + packetMoneyStock.doubleValue();
									map.put("advertisingFee", advertisingAmount < 0 ? 0 : advertisingAmount);
									map.put("memberAccount", params.get("memberAccount")); // 会员账户
									int k = getBaseDao().update("MemberMapper.updateMemberBalance", map);
									if (k <= 0) {
										flag = true;
									} else {
										// 删除过期的红包信息
										getBaseDao().delete("VideoRedPacketMapper.delRedPacketMoneyStock", params);
										// 广告钱包退回记录
										Map<String, Object> adRecord = new HashMap<>();
										adRecord.put("advertisingInfoId", getSequence());
										adRecord.put("advertisingInfoAddOrMinus", "+");
										adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
										adRecord.put("advertisingInfoMoney", packetMoneyStock);
										adRecord.put("advertisingInfoFrom", "视频广告红包过期退回");
										getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
									}
									break;
								default:
									break;
							}
							if (flag) {
								LOGGER.error("红包退回失败!");
							}
							LOGGER.info("==============红包过期退回任务结束================");
						}
					}
				}
				timer.cancel();
			}
		},daySpan);
	}

	/**
	 * 返回红包信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public int updateRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		// 更新抢红包信息表
		int i = getBaseDao().update("VideoRedPacketMapper.updateRedPacketInfoById", params);
		if(i > 0){
			params.put("redPacketVideoId", params.get("redPacketId"));
			@SuppressWarnings("unchecked")
			Map<String, Object> packet = (Map<String, Object>) getBaseDao().
					queryForObject("VideoRedPacketMapper.queryRedPacketLog", params);
			// 更新完毕成功之后,更新红包的数据,红包的开始时间和结束时间
			if(Optional.ofNullable(packet).isPresent()){
				params.put("redPacketVideoId", packet.get("redPacketId"));
				Integer stock = (Integer) packet.get("redPacketStock");
				Integer count = (Integer) packet.get("redPacketCount");
				Date date = new Date();
				if(stock.intValue() == count.intValue()){
					params.put("redPacketVideoStartTime", date);
				}
				if(stock < count && stock == 1){
					params.put("redPacketVideoEndTime", date);
				}
				params.put("redPacketVideoStock", stock < 0 ? 0 : stock - 1 );
				int k = getBaseDao().update("VideoRedPacketMapper.updateByExampleSelective", params);
				if (k < 0) {
					output.setCode("-1");
					output.setMsg("抢红包失败");
				}
			}
		}
		return 0;
	}

	/**
	 * 视频红包抢红包
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void robRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 查询用户信息
			Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			// 通过用户账号和红包ID查询出红包信息:
			Long redPacketId = (Long) params.get("redPacketVideoId");
			String redPacketUserId = (String) params.get("memberAccount");
			String robUserId = (String) params.get("robUserId");
			//判断是否已经答错过
			Map<String,Object> ismap = new HashMap<>();
			ismap.put("redPacketId", redPacketId);
			ismap.put("userId", robUserId);
			ismap.put("tableName","zx_app_video_red_packet_scrape");
			int ishave = getBaseDao().getTotalCount("VideoRedPacketMapper.isScrapeRedPacket", ismap);
			if (ishave != 0) {
				output.setCode("-1"); // 8
				output.setMsg("答题错误,祝下次好运!");
				return;
			}
			// 根据用户ID和红包ID查询红包详细信息
			Map<String, Object> redPacket = (Map<String, Object>) getBaseDao().queryForObject(
					"VideoRedPacketMapper.queryRedPacketDetail", params);
			// 抢红包的人存在和红包信息存在才可以进行抢红包操作
			if (Optional.ofNullable(zxAppUser).isPresent() && Optional.ofNullable(redPacket).isPresent()) {
				// 先判断是否抢过红包:
				Map<String, Object> one = (Map<String, Object>) getBaseDao().queryForObject(
						"VideoRedPacketMapper.queryRedPacketInfoDetail", params);
				if (Optional.ofNullable(one).isPresent() && StringUtil.isNotEmpty((String) one.get("redPacketVideoUserId"))) {
					output.setCode("-1"); // 4
					output.setMsg("不能重复抢红包!");
					return;
				}
				if(0 == (int) redPacket.get("redPacketVideoStock")) { // 判断红包剩余数量
					output.setCode("-1"); // 5
					output.setMsg("很遗憾，红包已被领完!");
					return;
				}
				String answer = (String) params.get("redPacketVideoAnswer"); // 判断回答问题答案
				if (answer == null || answer.isEmpty() || !answer.equals(redPacket.get("redPacketVideoAnswer"))) {
					ismap.put("scrapeId", getSequence());
					getBaseDao().insert("RedPacketMapper.insertScrapeRedPacket", ismap);
					output.setCode("-1"); // 3
					output.setMsg("答题错误,祝下次好运!");
					return;
				}
				// 返回一个随意产生的红包给用户,并保存到数据库:
				Map<String, Object> maps = (Map<String, Object>) getBaseDao().queryForObject(
						"VideoRedPacketMapper.randomSelectRedPacketInfo", redPacket);
				// 之后将抢红包人的Id更新到抢红包的表中,并结算时间,存入到红包信息表中
				if (Optional.ofNullable(maps).isPresent()) {
					HashMap<String, Object> mapp = new HashMap<>();
					mapp.put("redPacketInfoId", maps.get("redPacketInfoId"));
					mapp.put("robUserId", params.get("robUserId"));
					mapp.put("redPacketId", params.get("redPacketVideoId"));
					InputDTO inputDTO = new InputDTO();
					inputDTO.setParams(mapp);
					// 更新抢红包信息表
					int time = updateRedPacketInfoById(inputDTO, output);
					if (time != -1) {
						// 更新用户表金额
						Map<String, Object> result = new HashMap<>();
						result.put("memberAccount", params.get("robUserId"));
						// 查询用户信息
						Map<String, Object> appUser =  (Map<String, Object>) getBaseDao().
								queryForObject("MemberMapper.queryMemberDetail", result);
						if (null != appUser) {
							Map<String, Object> updateUser = new HashMap<>();
							// 给抢红包的用户进行金额的更新,抢到的红包放入到红包金额中
							updateUser.put("memberAccount", params.get("robUserId"));
							// 使用那个发布的就进入那个钱包
							BigDecimal money = (BigDecimal) maps.get("redPacketMoney");
							String walletBalance = (String) appUser.get("walletBalance");
							String advertisingFee = (String) appUser.get("advertisingFee");

							if ("0".equals(redPacket.get("redPacketVideoType"))) {
								updateUser.put("walletBalance", BigDecimal.valueOf(Double.valueOf(walletBalance)).doubleValue() + money.doubleValue());
							} else {
								updateUser.put("advertisingFee", BigDecimal.valueOf(Double.valueOf(advertisingFee)).doubleValue() + money.doubleValue());
							}
							int i = getBaseDao().update("MemberMapper.updateMemberBalance", updateUser);
							if (i > 0) {
								Map<String, Object> redPac = new HashMap<>();
								redPac.put("memberAccount", redPacketUserId);
								Map<String, Object> zxAppUser1 = (Map<String, Object>) getBaseDao().queryForObject(
										"MemberMapper.queryMemberDetail", redPac);
								if (null != zxAppUser1) {
									if ("0".equals(redPacket.get("redPacketVideoType"))) {
										// 钱包记录
										Map<String, Object> walletMoneyInfo = new HashMap<>();
										walletMoneyInfo.put("walletInfoId", getSequence());
										walletMoneyInfo.put("walletInfoAddOrMinus", "+");
										walletMoneyInfo.put("walletInfoUserId", robUserId);
										walletMoneyInfo.put("walletInfoMoney", money);
										walletMoneyInfo.put("walletInfoFrom", "视频广告红包--来自" + zxAppUser1.get("nickname"));
										getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
									} else {
										// 广告钱包记录
										Map<String, Object> adRecord = new HashMap<>();
										adRecord.put("advertisingInfoId", getSequence());
										adRecord.put("advertisingInfoAddOrMinus", "+");
										adRecord.put("advertisingInfoUserId", robUserId);
										adRecord.put("advertisingInfoMoney", money);
										adRecord.put("advertisingInfoFrom", "视频广告红包--来自" + zxAppUser1.get("nickname"));
										getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
									}
								}
								maps = (Map<String, Object>) getBaseDao().queryForObject("VideoRedPacketMapper.selectRedPacketInfoByInfoId", maps);
								output.setCode("0"); // 3
								output.setMsg("返回单个红包金额信息!");
								output.setItem(maps);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void viewRedPacketDone(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			if(Optional.ofNullable(params).isPresent()) {
				// 根据抢红包人的ID查询红包金额信息表中红包ID
				List<Map<String,Object>> list = getBaseDao().queryForList(
						"VideoRedPacketMapper.selectRedPacketInfoByRobUserId", params);
				if(list != null && list.size() > 0){
					output.setCode("0");
					output.setMsg("数据存在!");
					output.setItems(list);
					return;
				}
				Long id = (Long) params.get("redPacketId");
				@SuppressWarnings("unchecked")
				Map<String, Object> maps = (Map<String, Object>) getBaseDao().
						queryForObject("VideoRedPacketMapper.queryRedPacketLog", id);
				if(Optional.ofNullable(maps).isPresent()){
					output.setCode("0");
					output.setMsg("暂无人抢该红包!");
					output.setItem(maps);
					return;
				}
				output.setCode("0");
				output.setMsg("该红包已过期!");
				return;
			}
			output.setCode("-1");
			output.setMsg("系统错误!");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void viewRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			if(Optional.ofNullable(params).isPresent()){
				// 根据红包id和用户id查询出红包信息和用户信息
				Map<String, Object> maps = (Map<String, Object>) getBaseDao().queryForObject(
						"VideoRedPacketMapper.selectUserRedPacketInfoAndRedPacketByUserId", params);
				if(Optional.ofNullable(maps).isPresent()){
					List<Map<String, Object>> maps_ = getBaseDao().queryForList(
							"VideoRedPacketMapper.selectRedPacketInfoByRedPacketId", params);
					maps.put("list",maps_);
					output.setItem(maps);
					output.setCode("0");
					output.setMsg("查询成功!");
					return;
				}
			}
			if(Optional.ofNullable(params).isPresent()) {
				Map<String, Object> maps = (Map<String, Object>) getBaseDao().
						queryForObject("VideoRedPacketMapper.queryRedPacketLog", params);
				if(Optional.ofNullable(maps).isPresent()){
					output.setItem(maps);
					output.setCode("0");
					output.setMsg("暂无人抢该红包!");
					return;
				}
				output.setCode("0");
				output.setMsg("该红包已过期!");
				return;
			}
			output.setCode("0");
			output.setMsg("暂无人抢该红包!");
			return;
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 根据红包ID查询红包详细信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryRedPacketDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().
					queryForObject("VideoRedPacketMapper.queryRedPacketLog", params);
			output.setItem(map);
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 根据红包ID和用户ID查看此用户是否抢当前这个红包
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void checkUserIsRobRedPacket(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int check =  getBaseDao().getTotalCount("VideoRedPacketMapper.checkUserIsRobRedPacket", params);
			Map<String, Object> checkMap = (Map<String, Object>) getBaseDao().queryForObject(
					"VideoRedPacketMapper.selectStockByRedPacketId", params);
			// 根据用户ID和红包ID查询红包详细信息
			Map<String, Object> map = new HashMap<>();
			map.put("redPacketVideoId", params.get("redPacketVideoId"));
			Map<String, Object> redPacket = (Map<String, Object>) getBaseDao().queryForObject(
					"VideoRedPacketMapper.queryRedPacketDetail", map);
			Date d = new Date();
			Date redPacketDate = (Date) redPacket.get("redPacketVideoDate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNowStr = sdf.format(d);
			long days = getDaySub(sdf.format(redPacketDate), dateNowStr);
			String msg;
			if(days >= 3) {
				if (params.get("memberAccount").equals(redPacket.get("redPacketVideoUserId"))) {
					if(0 == (int) redPacket.get("redPacketVideoStock")) { // 判断红包剩余数量
						output.setCode("-1"); // 5
						output.setMsg("此红包已被领完！");
						return;
					} else {
						msg = "此红包已退回！";
						output.setCode("-2"); // 4
						output.setMsg(msg);
						return;
					}
				} else {
					msg = "该红包已超过72小时，如果已领取，请在钱包记录中查询！";
					output.setCode("-3"); // 4
					output.setMsg(msg);
					return;
				}
			}
			if (Optional.ofNullable(checkMap).isPresent()) {
				// 通过用户账号和红包ID查询出红包信息:
				Long redPacketId = (Long) params.get("redPacketVideoId");
				String robUserId = (String) params.get("memberAccount");
				if(check == 0){
					//判断是否已经答错过
					Map<String,Object> ismap = new HashMap<>();
					ismap.put("redPacketId", redPacketId);
					ismap.put("userId", robUserId);
					ismap.put("tableName","zx_app_video_red_packet_scrape");
					int ishave = getBaseDao().getTotalCount("VideoRedPacketMapper.isScrapeRedPacket", ismap);
					if (ishave != 0) {
						output.setCode("-1"); // 8
						output.setMsg("答题错误,祝下次好运!");
						return;
					}
					if(0 == (int) redPacket.get("redPacketVideoStock")) { // 判断红包剩余数量
						output.setCode("-1"); // 5
						output.setMsg("此红包已被领完！");
						return;
					}
				}
				checkMap.put("isRobRedPacket",check > 0 ? "1" : "0");
				output.setMsg("成功");
				output.setItem(checkMap);
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 当前用户是否领取该红包 参数为 memberAccount（当前用户账号）,redPackageIds（红包Id） 返回值
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void selectListStockByRedPacketId(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String packetIds = (String) params.get("redPacketIds");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> btnList = JsonUtil.convertJson2Object(packetIds, List.class);
			params.put("redPacketIds", btnList);
			List<Map<String, Object>> list = getBaseDao().queryForList(
					"VideoRedPacketMapper.selectListStockByRedPacketId", params);
			if (null != list && list.size() > 0) {
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
	 * 查询所有红包根据用户权限（性别，年龄，地区）
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryAllRedPacketByAuth(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		List<Map<String, Object>> listAll = new ArrayList<>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("RealNameAuthMapper.queryRealNameInfo", params);
			// 查询用户信息
			Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			List<Map<String, Object>> list = getBaseDao().queryForList("VideoRedPacketMapper.queryRedPacketLog", params);
			if (Optional.ofNullable(item).isPresent()) {
				Integer age = (Integer) item.get("age");
				String sex = (String) item.get("sex");
				if (StringUtil.isEmpty(age + "") ||
						StringUtil.isEmpty(sex)) {
					output.setCode("-1");
					output.setMsg("请完善个人信息！");
					return;
				}
				if (null != list && list.size() > 0) {
					for (Map<String, Object> map : list) {
						Map<String, Object> result = new HashMap<>();
						Integer redPacketSex = (Integer) map.get("redPacketSex");           // 红包性别
						Integer redPacketAgeStart = (Integer) map.get("redPacketAgeStart"); // 红包开始年龄
						Integer redPacketAgeEnd = (Integer) map.get("redPacketAgeEnd");     // 红包结束年龄
						result.put("redProvName", map.get("provinceName"));                // 省名称
						result.put("redCityName", map.get("cityName"));                   // 市名称
						result.put("redCountyName", map.get("countyName"));               // 区县名称

						Map<String, Object> resultMap = new HashMap<>();
						resultMap.put("redPacketId", map.get("redPacketId"));
//						resultMap.put("memberAccount", params.get("memberAccount"));
						resultMap.put("robUserId", params.get("memberAccount"));
						int checkRed = checkOldRedPacket(resultMap); // 校验红包颜色
						if(checkRed == 1){
							map.put("redPacketColor", "1"); // 浅颜色
						} else {
							map.put("redPacketColor", "0"); // 红色
						}

						Date crtTime = (Date) zxAppUser.get("crtTime"); // 注册时间
						Date redPacketDate = (Date) map.get("redPacketDate"); // 红包发布时间
						if (crtTime.getTime() > redPacketDate.getTime()) { // 校验用户注册时间是否大于红包发布时间（小于红包发布时间可见红包）
							continue;
						}
						// 校验红包地区
						int checkArea = checkArea(item, result);
//						if(checkArea == 2){
//							output.setCode("-1");
//							output.setMsg("请完善个人信息！");
//							return;
//						}
						int sexFlag = 0; // 性别为3不限制年龄
						if (redPacketSex == 3 || sex.equals(redPacketSex + "")) {
							sexFlag = 1;
						}
						int minePacket = 0;
						if (params.get("memberAccount").equals(map.get("redPacketUserId"))){
							minePacket = 1;
						}
						// 校验红包年龄
						int checkAge = checkAge(age, redPacketAgeStart, (redPacketAgeEnd));
						if (minePacket == 1 ){
							listAll.add(map);
							continue;
						}
						if (sexFlag == 1 && checkAge == 1 && checkArea == 3) {
							listAll.add(map);
						}
					}
				} else {
					output.setCode("0");
					output.setMsg("暂无记录！");
				}
			}
			output.setItems(listAll);
			output.setTotal(listAll.size());
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 校验红包年龄限制
	 * @param age 用户年龄
	 * @param redPacketAgeStart 红包开始年龄
	 * @param redPacketAgeEnd 红包结束年龄
	 * @return
	 */
	private int checkAge(Integer age, Integer redPacketAgeStart, Integer redPacketAgeEnd){
		if (age >= redPacketAgeStart && age <= redPacketAgeEnd) {
			return 1;
		}
		return 0;
	}

	/**
	 * 校验省市县
	 * @param item 个人省市县
	 * @param result 红包省市县
	 * @return
	 */
	private int checkArea(Map<String, Object> item, Map<String, Object> result){
		if ("-1".equals(result.get("redProvName"))) {
			return 3; // 全国状态
		}
		if (StringUtil.isEmpty((String) item.get("provinceName"))) {
			return 2; // 请完善个人信息
		}
		// 省级红包，只匹配省级是否相等
		if (item.get("provinceName").equals(result.get("redProvName"))
				&& StringUtil.isEmpty((String) result.get("redCityName"))) {
			return 3; // 匹配成功
		} else if (!StringUtil.isEmpty((String) result.get("redCityName"))) {
			// 市级红包
			if (item.get("cityName").equals(result.get("redCityName"))) {
				return 3; // 匹配成功
			}
		} else if (!StringUtil.isEmpty((String) result.get("redCountyName"))) {
			// 区县级红包
			if (item.get("countyName").equals(result.get("redCountyName"))) {
				return 3; // 匹配成功
			}
		}
		return 0;
	}

	/**
	 * 校验钱包余额
	 * @param input 入参
	 * @param output 出参
	 */
	private OutputDTO checkWalletMoney(InputDTO input, OutputDTO output) {
		Integer redPacketVideoCount = (Integer) input.getParams().get("redPacketVideoCount"); // 红包个数
		BigDecimal redPacketVideoMoneyCount = (BigDecimal) input.getParams().get("redPacketVideoMoneyCount"); // 红包金额大小
		Double money = redPacketVideoMoneyCount.doubleValue() / redPacketVideoCount;
		if (money < 0.2) {
			output.setMsg("单个红包金额平均不能低于0.2元");
			output.setCode("-1");
			return output;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> item = (Map<String, Object>) getBaseDao().
				queryForObject("MemberMapper.queryMemberBalanceDetail", input.getParams());
		if (null != item) {
			String type = (String) input.getParams().get("redPacketVideoType");
			switch (type) {
				case WALLET_TYPE:
					String zxMyWalletAmount = (String) item.get("walletBalance");
					if (Double.valueOf(zxMyWalletAmount) < redPacketVideoMoneyCount.doubleValue()) {
						output.setMsg("钱包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				case RED_PACKET_TYPE:
					String zxMyHbAmount = (String) item.get("redEnveBalance");
					if (Double.valueOf(zxMyHbAmount) < redPacketVideoMoneyCount.doubleValue()) {
						output.setMsg("红包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				case ADVERSING_TYPE:
					String zxMyAdvertisingAmount = (String) item.get("advertisingFee");
					if (Double.valueOf(zxMyAdvertisingAmount) < redPacketVideoMoneyCount.doubleValue()) {
						output.setMsg("广告钱包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				default:
					output.setMsg("非法参数值(1,-1)");
					output.setCode("-1");
					return output;
			}
			output.setMsg("红包金额正确");
			output.setCode("0");
			return output;
		}
		output.setMsg("服务器错误(1,-1)");
		output.setCode("-1");
		return output;
	}

	/**
	 * 分配红包
	 * @param num 红包数量
	 * @param money 红包金额
	 * @return
	 */
	private List<Double> gradRedPacket(int num,double money){
		List<Double> array = new ArrayList<>();//红包随机分配的list
		Random r = new Random();
		double sum = 0;
		for (int i = 0; i < num; i++) {
			array.add(r.nextDouble()*money + 0.01 * num * money);//经过小小的计算，这样使最小的钱尽量接近0.01；num越大，此计算越没有用
			sum += array.get(i);
		}
		for (int i = 0; i < array.size(); i++) {
			array.set(i, array.get(i) / sum*money);
		}
		Collections.sort(array);
		for (int i = 0; i < array.size(); i++) {//将钱进行分配；
			if(array.get(i)<=0.01){//不足0.01的直接给0.01；
				array.set(i, 0.01);
			}else if(i==array.size()-1){
				BigDecimal he =new BigDecimal("0");
				for(int j=0;j<array.size()-1;j++){
					BigDecimal b =new BigDecimal(Double.toString(array.get(j)));
					he=he.add(b);
				}
				BigDecimal moneyb =new BigDecimal(Double.toString(money));
				array.set(i, moneyb.subtract(he).doubleValue());
			}else{
				array.set(i, (int)(array.get(i)*100)*1.0/100);
			}
		}
		Collections.shuffle(array);
		return array;
	}

	/**
	 * 计算手气最佳
	 * @param doubles
	 * @param redPacketVideoId
	 * @return
	 */
	private int insertListRedPacketImgInfo(List<Double> doubles, Long redPacketVideoId) {
		int i = 0;
		Double maxMoney = getMaxMoney(doubles);
		for (Double num : doubles) {
			Map<String, Object> params = new HashMap<>();
			params.put("redPacketVideoInfoId", getSequence()); // 视频信息红包ID
			params.put("redPacketVideoId", redPacketVideoId); // 视频红包ID
			params.put("num", num); // 数量
			// 如果获取红包最大值,修改表中LuckStar的值为 1
			if(maxMoney.doubleValue() != 0 && num.doubleValue() == maxMoney.doubleValue()){
				params.put("luckStar", 1); // 是否手气最佳
				i += getBaseDao().insert("VideoRedPacketMapper.insertListRedPacketInfo", params);
				continue;
			} else {
				params.put("luckStar", 0); // 是否手气最佳
				i += getBaseDao().insert("VideoRedPacketMapper.insertListRedPacketInfo", params);
			}
		}
		return i;
	}

	/**
	 * 计算最大值
	 * @param doubles
	 * @return
	 */
	private Double getMaxMoney(List<Double> doubles){
		if(Optional.ofNullable(doubles).isPresent()){
			Double max = doubles.get(0);
			for (Double aDouble : doubles) {
				if(max < aDouble){
					max = aDouble;
				}
			}
			return max;
		}
		return 0D;
	}

	/**
	 * 校验红包颜色
	 * @param item
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int checkOldRedPacket(Map<String, Object> item) {
		// 通过用户账号和红包ID查询出红包信息:
		Long redPacketId = (Long) item.get("redPacketId");
		String robUserId = (String) item.get("robUserId");
		//判断是否已经答错过
		Map<String, Object> ismap = new HashMap<>();
		ismap.put("redPacketId", redPacketId);
		ismap.put("userId", robUserId);
		ismap.put("tableName", "zx_app_video_red_packet_scrape");
		int ishave = getBaseDao().getTotalCount("VideoRedPacketMapper.isScrapeRedPacket", ismap);
		if (ishave != 0) { // 答题错误,祝下次好运!
			return 1;
		}
		item.put("redPacketVideoId", redPacketId);
		// 根据用户ID和红包ID查询红包详细信息
		Map<String, Object> redPacket = (Map<String, Object>) getBaseDao().queryForObject(
				"VideoRedPacketMapper.queryRedPacketDetail", item);
		// 抢红包的人存在和红包信息存在才可以进行抢红包操作
		if (Optional.ofNullable(redPacket).isPresent()) {
			// 先判断是否抢过红包:
			Map<String, Object> one = (Map<String, Object>) getBaseDao().queryForObject(
					"VideoRedPacketMapper.queryRedPacketInfoDetail", item);
			if (Optional.ofNullable(one).isPresent() && StringUtil.isNotEmpty((String) one.get("redPacketVideoUserId"))) { // 不能重复抢红包
				return 1;
			}
			if (0 == (int) redPacket.get("redPacketVideoStock")) { // 判断红包剩余数量 很遗憾，红包已被领完!
				return 1;
			}
			Date d = new Date();
			Date redPacketDate = (Date) redPacket.get("redPacketVideoDate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNowStr = sdf.format(d);
			long days = getDaySub(sdf.format(redPacketDate), dateNowStr);
			if(days >= 3) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 校验红包天数
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static long getDaySub(String beginDateStr,String endDateStr) {

		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("day:"+day);

		return day;
	}

}
