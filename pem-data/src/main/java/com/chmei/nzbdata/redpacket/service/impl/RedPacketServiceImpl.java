package com.chmei.nzbdata.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.redpacket.service.IRedPacketService;
import com.chmei.nzbdata.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 众享红包dao接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月05日 14点59分
 */
@Service("redPacketService")
public class RedPacketServiceImpl extends BaseServiceImpl implements IRedPacketService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RedPacketServiceImpl.class);
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
	public void saveRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("RedPacketServiceImpl.saveRedPacketInfo, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		try{
			int redPacketCount = (int) params.get("redPacketCount");
			if(redPacketCount < 50){
				output.setMsg("红包个数不能小于50个");
				return;
			}
			String redPacketType = (String) params.get("redPacketType"); // 发红包类型
			OutputDTO outputDTO = checkWalletMoney(input, output); // 校验钱包余额
			if("0".equals(outputDTO.getCode())){
				@SuppressWarnings("unchecked")
				Map<String, Object> item = (Map<String, Object>) getBaseDao().
						queryForObject("MemberMapper.queryMemberBalanceDetail", input.getParams());
				String walletBalance = (String) item.get("walletBalance"); // 钱包余额
				String redEnveBalance = (String) item.get("redEnveBalance"); // 红包余额
				String advertisingFee = (String) item.get("advertisingFee"); // 广告费余额
				BigDecimal redPacketMoneyCount = (BigDecimal) params.get("redPacketMoneyCount");
				params.put("redPacketId", getSequence()); // 获取id
				params.put("redPacketUserId", params.get("memberAccount")); // 获取用户id
				int count = getBaseDao().insert("RedPacketMapper.saveRedPacketInfo", params);
				boolean flag = false;
				if (count > 0) {
					Map<String, Object> map = new HashMap<>();
					// 插入红包成功,扣除相应的款项,钱包,红包,广告钱包
					switch (redPacketType) {
						case WALLET_TYPE: // 钱包扣款
							double walletAmount = Double.valueOf(walletBalance) - redPacketMoneyCount.doubleValue();
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
								walletMoneyInfo.put("walletInfoMoney", redPacketMoneyCount);
								walletMoneyInfo.put("walletInfoFrom", "发布众享红包");
								getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
							}
							break;
						case RED_PACKET_TYPE: // 红包扣款
							double hbAmount = Double.valueOf(redEnveBalance) - redPacketMoneyCount.doubleValue();
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
								redRecord.put("redPacketInfoMoney", redPacketMoneyCount);
								redRecord.put("redPacketInfoFrom", "发布众享红包");
								getBaseDao().insert("RedRecordMoneyInfoMapper.saveRedRecordMoneyInfo", redRecord);
							}
							break;
						case ADVERSING_TYPE: // 广告钱包扣款
							double advertisingAmount = Double.valueOf(advertisingFee) - redPacketMoneyCount.doubleValue();
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
								adRecord.put("advertisingInfoMoney", redPacketMoneyCount);
								adRecord.put("advertisingInfoFrom", "发布众享红包");
								getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
							}
							break;
						default:
							break;
					}
					if (flag) {
						// 扣款失败,不能发布红包,直接数据库删除红包记录:
						String id = (String) params.get("redPacketId");
						int i = getBaseDao().delete("RedPacketMapper.delRedPacketInfo", id);
						if(i <= 0){
							LOGGER.error("扣款失败,用户 {} " + params.get("memberAccount") + "删除发布红包 {} " + id + "失败!");
						}
						output.setCode("-1");
						output.setMsg("发布红包失败");
					}
					Map<String, Object> mmap = new HashMap<>();
					mmap.put("redPacketId", params.get("redPacketId"));
					@SuppressWarnings("unchecked")
					Map<String, Object> stringObjectMap = (Map<String, Object>) getBaseDao().
							queryForObject("RedPacketMapper.queryRedPacketLog", mmap);
					// 发布成功,直接进行红包的分配:
					List<Double> doubles = gradRedPacket(redPacketCount, redPacketMoneyCount.doubleValue());
					int i = this.insertListRedPacketImgInfo(doubles, (Long) params.get("redPacketId"));
					if(i > 0 && stringObjectMap != null){
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
		int i = getBaseDao().update("RedPacketMapper.updateRedPacketInfoById", params);
		if(i > 0){
			@SuppressWarnings("unchecked")
			Map<String, Object> packet = (Map<String, Object>) getBaseDao().
					queryForObject("RedPacketMapper.queryRedPacketLog", params);
			// 更新完毕成功之后,更新红包的数据,红包的开始时间和结束时间
			if(Optional.ofNullable(packet).isPresent()){
				params.put("redPacketId", packet.get("redPacketId"));
				Integer stock = (Integer) packet.get("redPacketStock");
				Integer count = (Integer) packet.get("redPacketCount");
				Date date = new Date();
				if(stock.intValue() == count.intValue()){
					params.put("redPacketStartTime", date);
				}
				if(stock < count && stock == 1){
					params.put("redPacketEndTime", date);
				}
				params.put("redPacketStock", stock < 0 ? 0 : stock - 1 );
				int k = getBaseDao().update("RedPacketMapper.updateByExampleSelective", params);
				if (k < 0) {
					output.setCode("-1");
					output.setMsg("抢红包失败");
					return -1;
				}
			}
		}
		return 0;
	}

	/**
	 * 众享红包抢红包
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
			Long redPacketId = (Long) params.get("redPacketId");
			String redPacketUserId = (String) params.get("memberAccount");
			String robUserId = (String) params.get("robUserId");
			//判断是否已经答错过
			Map<String,Object> ismap = new HashMap<>();
			ismap.put("redPacketId", redPacketId);
			ismap.put("userId", robUserId);
			ismap.put("tableName","zx_app_red_packet_scrape");
			int ishave = getBaseDao().getTotalCount("RedPacketMapper.isScrapeRedPacket", ismap);
			if (ishave != 0) {
				output.setCode("-1"); // 8
				output.setMsg("答题错误,祝下次好运!");
				return;
			}
			// 根据用户ID和红包ID查询红包详细信息
			Map<String, Object> redPacket = (Map<String, Object>) getBaseDao().queryForObject(
					"RedPacketMapper.queryRedPacketDetail", params);
			// 抢红包的人存在和红包信息存在才可以进行抢红包操作
			if (Optional.ofNullable(zxAppUser).isPresent() && Optional.ofNullable(redPacket).isPresent()) {
				// 先判断是否抢过红包:
				Map<String, Object> one = (Map<String, Object>) getBaseDao().queryForObject(
						"RedPacketMapper.queryRedPacketInfoDetail", params);
				if (Optional.ofNullable(one).isPresent() && StringUtil.isNotEmpty((String) one.get("redPacketUserId"))) {
					output.setCode("-1"); // 4
					output.setMsg("不能重复抢红包!");
					return;
				}
				if(0 == (int) redPacket.get("redPacketStock")) { // 判断红包剩余数量
					output.setCode("-1"); // 5
					output.setMsg("很遗憾，红包已被领完!");
					return;
				}
				String answer = (String) params.get("redPacketAnswer"); // 判断回答问题答案
				if (answer == null || answer.isEmpty() || !answer.equals(redPacket.get("redPacketAnswer"))) {
					ismap.put("scrapeId", getSequence());
					getBaseDao().insert("RedPacketMapper.insertScrapeRedPacket", ismap);
					output.setCode("-1"); // 3
					output.setMsg("回答错误，下次要用心哟!");
					return;
				}
				// 返回一个随意产生的红包给用户,并保存到数据库:
				Map<String, Object> maps = (Map<String, Object>) getBaseDao().queryForObject(
						"RedPacketMapper.randomSelectRedPacketInfo", redPacket);
				// 之后将抢红包人的Id更新到抢红包的表中,并结算时间,存入到红包信息表中
				if (Optional.ofNullable(maps).isPresent()) {
					HashMap<String, Object> mapp = new HashMap<>();
					mapp.put("redPacketInfoId", maps.get("redPacketInfoId"));
					mapp.put("robUserId", params.get("robUserId"));
					mapp.put("redPacketId", params.get("redPacketId"));
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
							if ("0".equals(redPacket.get("redPacketType"))) {
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
									if ("0".equals(redPacket.get("redPacketType"))) {
										// 钱包记录
										Map<String, Object> walletMoneyInfo = new HashMap<>();
										walletMoneyInfo.put("walletInfoId", getSequence());
										walletMoneyInfo.put("walletInfoAddOrMinus", "+");
										walletMoneyInfo.put("walletInfoUserId", robUserId);
										walletMoneyInfo.put("walletInfoMoney", money);
										walletMoneyInfo.put("walletInfoFrom", "众享广告红包--来自" + zxAppUser1.get("nickname"));
										getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
									} else {
										// 广告钱包记录
										Map<String, Object> adRecord = new HashMap<>();
										adRecord.put("advertisingInfoId", getSequence());
										adRecord.put("advertisingInfoAddOrMinus", "+");
										adRecord.put("advertisingInfoUserId", robUserId);
										adRecord.put("advertisingInfoMoney", money);
										adRecord.put("advertisingInfoFrom", "众享广告红包--来自" + zxAppUser1.get("nickname"));
										getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
									}
								}
								maps = (Map<String, Object>) getBaseDao().queryForObject("RedPacketMapper.selectRedPacketInfoByInfoId", maps);
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
						"RedPacketMapper.selectRedPacketInfoByRobUserId", params);
				if(list != null && list.size() > 0){
					output.setCode("0");
					output.setMsg("数据存在!");
					output.setItems(list);
					return;
				}
				Long id = (Long) params.get("redPacketId");
				@SuppressWarnings("unchecked")
				Map<String, Object> maps = (Map<String, Object>) getBaseDao().
						queryForObject("RedPacketMapper.queryRedPacketLog", id);
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
			if (Optional.ofNullable(params).isPresent()) {
				// 红包信息和当前人抢红包金额,和发布红包人的信息
				Map<String, Object> maps1 = (Map<String, Object>) getBaseDao().queryForObject(
						"RedPacketMapper.selectUserRedPacketInfoAndRedPacketByUserId", params);
				if (Optional.ofNullable(maps1).isPresent()) {
					Map<String, Object> stringObjectMap = (Map<String, Object>) getBaseDao().queryForObject(
							"RedPacketMapper.queryRedPacketInfoByRedPacketIdAndUserId", params);
					if (!Optional.ofNullable(stringObjectMap).isPresent()) {
						stringObjectMap = new HashMap<>();
						stringObjectMap.put("redPacketMoney",0);
						stringObjectMap.put("redPacketDate","");
					}
					maps1.putAll(stringObjectMap);
					// 非当前人抢红包信息
					List<Map<String, Object>> maps = getBaseDao().queryForList(
							"RedPacketMapper.selectRedPacketInfoByRedPacketId", params);
					maps1.put("list",maps);
					output.setItem(maps1);
					output.setCode("0");
					output.setMsg("查询成功!");
					return;
				}
			}
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
					queryForObject("RedPacketMapper.queryRedPacketLog", params);
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
	@Override
	public void checkUserIsRobRedPacket(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int check =  getBaseDao().getTotalCount("RedPacketMapper.checkUserIsRobRedPacket", params);
			@SuppressWarnings("unchecked")
			Map<String, Object> checkMap = (Map<String, Object>) getBaseDao().queryForObject(
					"RedPacketMapper.selectStockByRedPacketId", params);
			if (Optional.ofNullable(checkMap).isPresent()) {
				// 通过用户账号和红包ID查询出红包信息:
				Long redPacketId = (Long) params.get("redPacketId");
				String robUserId = (String) params.get("memberAccount");
				if(check == 0){
					//判断是否已经答错过
					Map<String,Object> ismap = new HashMap<>();
					ismap.put("redPacketId", redPacketId);
					ismap.put("userId", robUserId);
					ismap.put("tableName","zx_app_red_packet_scrape");
					int ishave = getBaseDao().getTotalCount("RedPacketMapper.isScrapeRedPacket", ismap);
					if (ishave != 0) {
						output.setCode("-1"); // 8
						output.setMsg("答题错误,祝下次好运!");
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
					"RedPacketMapper.selectListStockByRedPacketId", params);
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
	@SuppressWarnings("unchecked")
	@Override
	public void queryAllRedPacketByAuth(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		List<Map<String, Object>> listAll = new ArrayList<>();
		try {
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("RealNameAuthMapper.queryRealNameInfo", params);
			// 查询用户信息
			Map<String, Object> zxAppUser =  (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", params);
			List<Map<String, Object>> list = getBaseDao().queryForList("RedPacketMapper.queryRedPacketLog", params);
			if (Optional.ofNullable(item).isPresent()) {
				Integer age = (Integer) item.get("age");
				String sex = (String) item.get("sex");
				if (null == age || StringUtil.isEmpty(sex)) {
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

						Date crtTime = (Date) zxAppUser.get("crtTime"); // 注册时间
						Date redPacketDate = (Date) map.get("redPacketDate"); // 红包发布时间
						if (crtTime.getTime() > redPacketDate.getTime()) { // 校验用户注册时间是否大于红包发布时间（小于红包发布时间可见红包）
							continue;
						}
						// 校验红包地区
						int checkArea = checkArea(item, result);

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
	 * 根据父编码查询省市信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryAreaByParent(InputDTO input, OutputDTO output) throws NzbDataException{
		try {
			List<Map<String,Object>> queryForObject = getBaseDao().queryForList(
					"RedPacketMapper.queryAreaList", input.getParams());
			if(queryForObject != null && !queryForObject.isEmpty()) {
				output.setItems(queryForObject);
			}
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
	}

	/**
	 * 校验钱包余额
	 * @param input 入参
	 * @param output 出参
	 */
	private OutputDTO checkWalletMoney(InputDTO input, OutputDTO output) {
		Integer redPacketCount = (Integer) input.getParams().get("redPacketCount"); // 红包个数
		BigDecimal redPacketMoneyCount = (BigDecimal) input.getParams().get("redPacketMoneyCount"); // 红包金额大小
		Double money = redPacketMoneyCount.doubleValue() / redPacketCount;
		if (money < 0.2) {
			output.setMsg("单个红包金额平均不能低于0.2元");
			output.setCode("-1");
			return output;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> item = (Map<String, Object>) getBaseDao().
				queryForObject("MemberMapper.queryMemberBalanceDetail", input.getParams());
		if (null != item) {
			String type = (String) input.getParams().get("redPacketType");
			switch (type) {
				case WALLET_TYPE:
					String zxMyWalletAmount = (String) item.get("walletBalance");
					if (Double.valueOf(zxMyWalletAmount) < redPacketMoneyCount.doubleValue()) {
						output.setMsg("钱包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				case RED_PACKET_TYPE:
					String zxMyHbAmount = (String) item.get("redEnveBalance");
					if (Double.valueOf(zxMyHbAmount) < redPacketMoneyCount.doubleValue()) {
						output.setMsg("红包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				case ADVERSING_TYPE:
					String zxMyAdvertisingAmount = (String) item.get("advertisingFee");
					if (Double.valueOf(zxMyAdvertisingAmount) < redPacketMoneyCount.doubleValue()) {
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
	 * @param redPacketId
	 * @return
	 */
	private int insertListRedPacketImgInfo(List<Double> doubles, Long redPacketId) {
		int i = 0;
		Double maxMoney = getMaxMoney(doubles);
		for (Double num : doubles) {
			Map<String, Object> params = new HashMap<>();
			params.put("redPacketInfoId", getSequence()); // 视频信息红包ID
			params.put("redPacketId", redPacketId); // 视频红包ID
			params.put("num", num); // 数量
			// 如果获取红包最大值,修改表中LuckStar的值为 1
			if(maxMoney.doubleValue() != 0 && num.doubleValue() == maxMoney.doubleValue()){
				params.put("luckStar", 1); // 是否手气最佳
				i += getBaseDao().insert("RedPacketMapper.insertListRedPacketInfo", params);
				continue;
			} else {
				params.put("luckStar", 0); // 是否手气最佳
				i += getBaseDao().insert("RedPacketMapper.insertListRedPacketInfo", params);
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

}
