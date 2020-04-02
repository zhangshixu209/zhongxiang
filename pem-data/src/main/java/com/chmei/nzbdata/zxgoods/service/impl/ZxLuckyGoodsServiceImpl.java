package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxgoods.service.IZxLuckyGoodsService;
import com.chmei.nzbdata.zxgoods.service.IZxLuckyGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 众享幸运购物商品
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点41分
 */

@Service("zxLuckyGoodsService")
public class ZxLuckyGoodsServiceImpl extends BaseServiceImpl implements IZxLuckyGoodsService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxLuckyGoodsServiceImpl.class);

	/**
	 * 新增幸运购物商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence()); // 主键ID
			params.put("goodsStatus", "1001");
			int i = getBaseDao().insert("LuckyGoodsMapper.saveLuckyGoodsInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("发布成功，请留意审核通知！");
				return;
			}
			output.setCode("-1");
			output.setMsg("发布失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 编辑幸运购物商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("LuckyGoodsMapper.authLuckyGoodsInfo", params);
			if (i > 0) {
				// 审核状态
				String goodsStatus = (String) params.get("goodsStatus");
				if("1002".equals(goodsStatus)){
					String message = "您发布的“" + params.get("goodsDesc") + "”商品已成功上架！";
					Map<String, Object> map = new HashMap<>();
					map.put("id", getSequence());
					map.put("messageTitle", "通知消息");
					map.put("messageContent", message);
					map.put("messageStatus", "1");
					map.put("messageType", Constants.MESSAGE_TYPE_1009);
					map.put("memberAccount", params.get("memberAccount"));
					// 添加推送消息
					getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
				} else if("1003".equals(goodsStatus)) {
					String message = "您所发布的“" + params.get("goodsDesc") + "”商品未通过审核！如有疑问请联系我们！拒绝原因："+params.get("auditOpinion");
					Map<String, Object> map = new HashMap<>();
					map.put("id", getSequence());
					map.put("messageTitle", "通知消息");
					map.put("messageContent", message);
					map.put("messageStatus", "1");
					map.put("messageType", Constants.MESSAGE_TYPE_1009);
					map.put("memberAccount", params.get("memberAccount"));
					// 添加推送消息
					getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
				} else if("1005".equals(goodsStatus)) {
					String message = "您所发布的“" + params.get("goodsDesc") + "”商品已被下架！如有疑问请联系我们！下架原因原因："+params.get("auditOpinion");
					Map<String, Object> map = new HashMap<>();
					map.put("id", getSequence());
					map.put("messageTitle", "通知消息");
					map.put("messageContent", message);
					map.put("messageStatus", "1");
					map.put("messageType", Constants.MESSAGE_TYPE_1009);
					map.put("memberAccount", params.get("memberAccount"));
					// 添加推送消息
					getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
				}
				output.setCode("0");
				output.setMsg("修改成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("修改失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 删除幸运购物商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().delete("LuckyGoodsMapper.delLuckyGoodsInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("删除成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("删除失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询幸运购物商品详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryLuckyGoodsDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"LuckyGoodsMapper.queryLuckyGoodsDetail", params);
			if (null == map) {
				output.setCode("-1");
				output.setMsg("查询失败");
			}
			output.setItem(map);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询幸运购物商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryLuckyGoodsList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("LuckyGoodsMapper.queryLuckyGoodsCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("LuckyGoodsMapper.queryLuckyGoodsList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 购买幸运购物商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buyLuckyGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"LuckyGoodsMapper.queryLuckyGoodsDetail", params);
			Map<String, Object> user_ = new HashMap<>();
			user_.put("memberAccount", params.get("memberAccount"));
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", user_);
			// 判断钱包余额是否充足:
			String walletBalance = (String) item.get("walletBalance");
			if(Double.valueOf(walletBalance) < Double.valueOf(map.get("goodsParcelPrice")+"")){
				output.setCode("-1");
				output.setMsg("钱包余额不足!");
				return;
			}
			int goodsSurplusNum = (int) map.get("goodsSurplusNum"); // 商品剩余数量
			if (goodsSurplusNum > 0) {
				Map<String, Object> map_ = new HashMap<>();
				map_.put("id", params.get("id")); // 商品ID
				map_.put("goodsSurplusNum", goodsSurplusNum - 1); // 商品剩余数量-1
				map_.put("memberAccount", params.get("memberAccount")); // 购买用户账号
				map_.put("sendMemberAccount", map.get("memberAccount")); // 商品发布用户账号
				map_.put("goodsParcelPrice", map.get("goodsParcelPrice")); // 支付金额
				// 创建订单信息
				int i = creatOrderInfo(map_);
				if (i > 0) {
					int j = getBaseDao().update("LuckyGoodsMapper.updateLuckyGoodsNum", map_);
					if (j > 0) {
						// 扣除当前人钱包金额
						Map<String, Object> user = new HashMap<>();
						user.put("memberAccount", params.get("memberAccount"));
						user.put("walletBalance", Double.valueOf(walletBalance) - Double.valueOf(map.get("goodsParcelPrice")+""));
						int k = getBaseDao().update("MemberMapper.updateMemberBalance", user);
						if(k > 0) {
							// 钱包扣除金额记录:
							Map<String, Object> walletMoneyInfo = new HashMap<>();
							walletMoneyInfo.put("walletInfoId", getSequence());
							walletMoneyInfo.put("walletInfoAddOrMinus", "-");
							walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
							walletMoneyInfo.put("walletInfoMoney", Double.valueOf(map.get("goodsParcelPrice")+""));
							walletMoneyInfo.put("walletInfoFrom", "幸运购物");
							getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
						}
					}
					output.setCode("0");
					output.setMsg("购买成功！");
					return;
				}
			} else {
				Map<String, Object> maps = new HashMap<>();
				maps.put("id", map.get("id"));
				maps.put("goodsStatus", "1006"); // 商品结束
				getBaseDao().update("LuckyGoodsMapper.authLuckyGoodsInfo", maps); // 更新商品状态为已结束
				output.setCode("-1");
				output.setMsg("商品已结束！");
				return;
			}
			output.setCode("-1");
			output.setMsg("购买失败！");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 免单活动
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void partakeFreeSheetAct(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			// 第一步-判断积分是否充足
			BigDecimal shoppingAmount = (BigDecimal) params.get("shoppingAmount");
			Map<String, Object> user_ = new HashMap<>();
			user_.put("memberAccount", params.get("memberAccount"));
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", user_);
			// 判断积分余额是否充足:
			String integralMoney = (String) item.get("integralMoney");
			if (Double.valueOf(integralMoney) < shoppingAmount.doubleValue()) {
				output.setCode("-1");
				output.setMsg("积分余额不足!");
				return;
			}
			// 第二步-判断是否第一次新增座位
			Long seatNum = (Long) params.get("seatNum");
			if (null != seatNum){
				int seat = getBaseDao().getTotalCount("LuckyGoodsMapper.querySeatTotal", params);
				if(seat > 0){
					output.setCode("-1");
					output.setMsg("不能重复选择!");
					return;
				}
				params.put("id", getSequence());
				params.put("seatNum", seatNum); // 座位号
				int i = getBaseDao().insert("LuckyGoodsMapper.partakeFreeSheetAct", params);
				if (i > 0) {
					// 增加当前人积分金额
					Map<String, Object> user1 = new HashMap<>();
					user1.put("memberAccount", params.get("memberAccount"));
					user1.put("integralMoney", Double.valueOf(integralMoney) - shoppingAmount.doubleValue());
					getBaseDao().update("MemberMapper.updateMemberBalance", user1);
					// 增加积分金额记录:
					Map<String, Object> integralMoneyInfo = new HashMap<>();
					integralMoneyInfo.put("integralInfoId", getSequence());
					integralMoneyInfo.put("integralInfoAddOrMinus", "-");
					integralMoneyInfo.put("integralInfoUserId", params.get("memberAccount"));
					integralMoneyInfo.put("integralInfoMoney", shoppingAmount);
					integralMoneyInfo.put("integralInfoFrom", "幸运抽奖");
					getBaseDao().insert("IntegralMoneyInfoMapper.saveIntegralMoneyInfo", integralMoneyInfo);
					int m = countLuckyMan(params); // 活动参与成功调用
					if (m > 0) {
						output.setCode("0");
						output.setMsg("参与成功！");
						return;
					}
				}
				output.setCode("-1");
				output.setMsg("参与失败！");
			} else {
				params.put("id", getSequence());
				params.put("seatNum", getSequence());
				params.put("activityType", "1");
				int i = getBaseDao().insert("LuckyGoodsMapper.partakeFreeSheetAct", params);
				if (i > 0) {
					// 增加当前人积分金额
					Map<String, Object> user1 = new HashMap<>();
					user1.put("memberAccount", params.get("memberAccount"));
					user1.put("integralMoney", Double.valueOf(integralMoney) - shoppingAmount.doubleValue());
					getBaseDao().update("MemberMapper.updateMemberBalance", user1);
					// 增加积分金额记录:
					Map<String, Object> integralMoneyInfo = new HashMap<>();
					integralMoneyInfo.put("integralInfoId", getSequence());
					integralMoneyInfo.put("integralInfoAddOrMinus", "-");
					integralMoneyInfo.put("integralInfoUserId", params.get("memberAccount"));
					integralMoneyInfo.put("integralInfoMoney", shoppingAmount);
					integralMoneyInfo.put("integralInfoFrom", "幸运抽奖");
					getBaseDao().insert("IntegralMoneyInfoMapper.saveIntegralMoneyInfo", integralMoneyInfo);
					output.setCode("0");
					output.setMsg("参与成功");
					return;
				}
				output.setCode("-1");
				output.setMsg("参与失败！");
			}
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 免单活动列表（幸运榜可用）
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryPartakeFreeSheetList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("LuckyGoodsMapper.queryPartakeFreeSheetCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("LuckyGoodsMapper.queryPartakeFreeSheetList", params);
				Map<Long, List<Map<String, Object>>> resultMap = new HashMap<>();
				for (Map<String, Object> map : list) {
					if(resultMap.containsKey(map.get("seatNum"))){
						resultMap.get(map.get("seatNum")).add(map);
					} else {
						List<Map<String, Object>> tmpList = new ArrayList<>();
						tmpList.add(map);
						resultMap.put((long) map.get("seatNum"), tmpList);
					}
				}
				Map<String, Object> maps = new HashMap<>();
				maps.put("maps", resultMap);
				output.setItem(maps);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 幸运购物幸运榜列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryLuckyList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("LuckyGoodsMapper.queryLuckyCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("LuckyGoodsMapper.queryLuckyList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 幸运购物幸运榜详情列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryLuckyDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"LuckyGoodsMapper.queryPartakeHeadInfo", params);
			int i = getBaseDao().getTotalCount("LuckyGoodsMapper.queryPartakeFreeSheetCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("LuckyGoodsMapper.queryPartakeFreeSheetListDetail", params);
				output.setItems(list);
			}
			output.setTotal(i);
			output.setItem(map);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 创建订单信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int creatOrderInfo(Map<String, Object> map){
		Map<String, Object> map_ = new HashMap<>();
		map_.put("goodsId", map.get("id"));
		map_.put("memberAccount", map.get("memberAccount"));
		LOGGER.info("=============订单开始创建==============");
		// 查询默认收货地址
		Map<String, Object> address = (Map<String, Object>) getBaseDao().queryForObject(
				"ReceivingAddressMapper.queryAddressIsDefault", map_);
		// 创建订单信息
		Map<String, Object> orderInfo = new HashMap<>();
		String orderArea = (String) address.get("consigArea");
		orderArea = orderArea.replace("$", "");
		orderInfo.put("id", getSequence());
		orderInfo.put("sendGoodsAccount", map.get("sendMemberAccount"));
		orderInfo.put("consigGoodsAccount", address.get("memberAccount"));
		orderInfo.put("consigName", address.get("consigName"));
		orderInfo.put("consigNamePhone", address.get("consigNamePhone"));
//		orderInfo.put("consigArea", address.get("consigArea"));
		orderInfo.put("consigAddress", orderArea+address.get("consigAddress"));
		orderInfo.put("orderStatus", "1001");      // 待发货
		orderInfo.put("orderType", "1003");        // 幸运购物
		orderInfo.put("goodsId", map.get("id"));   // 商品ID
		int i = getBaseDao().insert("OrderInfoMapper.saveOrderInfoInfo", orderInfo);
		if (i > 0) {
			// 系统钱包金额增加记录:
			Map<String, Object> systemMoneyInfo = new HashMap<>();
			systemMoneyInfo.put("systemInfoId", getSequence());
			systemMoneyInfo.put("systemInfoAddOrMinus", "+");
			systemMoneyInfo.put("systemInfoUserId", address.get("memberAccount"));
			systemMoneyInfo.put("orderId", orderInfo.get("id")); // 订单ID
			systemMoneyInfo.put("systemInfoMoney", Double.valueOf(map.get("goodsParcelPrice")+""));
			systemMoneyInfo.put("systemInfoFrom", "幸运购物");
			getBaseDao().insert("SystemMoneyInfoMapper.saveSystemMoneyInfo", systemMoneyInfo);
			LOGGER.info("=============订单创建成功==============");
			return 1;
		}
		return 0;
	}

	/**
	 * 创建订单信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int countLuckyMan(Map<String, Object> map){
		LOGGER.info("=============开始计算中奖用户==============");
		// 计算幸运序号
		Map<String, Object> lucky = (Map<String, Object>) getBaseDao().queryForObject(
				"LuckyGoodsMapper.queryPartakeFreeSheetAct", map);
		Double orderNum = (Double) lucky.get("orderNum"); // 幸运数字
		Long peopleTotal = (Long) lucky.get("peopleTotal");       // 参与人数
		Map<String, Object> luckys = new HashMap<>();
		int luckyMan = orderNum.intValue() % peopleTotal.intValue(); // 幸运数字总数 % 总人数取余
		if (luckyMan == 0) { // 如果余数为0，按照时间降序查询第一人
			Map<String, Object> list_ = (Map<String, Object>) getBaseDao().queryForObject(
					"LuckyGoodsMapper.queryPartakeFreeSheetOne", map);
			luckyMan = Integer.valueOf(list_.get("luckyOrderNumber")+"");
		}
		luckys.put("luckyMan", luckyMan); // 幸运者
		luckys.put("seatNum", map.get("seatNum")); // 商品ID
		Map<String, Object> luckyMap = (Map<String, Object>) getBaseDao().queryForObject(
				"LuckyGoodsMapper.queryPartakeFreeSheetDetail", luckys);
		luckyMap.put("goodsLuckStar", 1);   // 幸运标识

		// 更新幸运者
		int i = getBaseDao().update("LuckyGoodsMapper.updateGoodsLuckStar", luckyMap);
		if (i > 0) {
			Map<String, Object> lu = new HashMap<>();
			lu.put("seatNum", luckyMap.get("seatNum"));
			lu.put("activityType", "2");
			getBaseDao().update("LuckyGoodsMapper.updateGoodsLuckStar", lu);
			// 系统钱包金额减少
			Map<String, Object> system = new HashMap<>();
			system.put("systemInfoMoney", map.get("shoppingAmount"));
			getBaseDao().update("SystemMoneyInfoMapper.updateSystemMoneyInfo", system);
			// 系统钱包金额增加记录:
			Map<String, Object> systemMoneyInfo = new HashMap<>();
			systemMoneyInfo.put("systemInfoId", getSequence());
			systemMoneyInfo.put("systemInfoAddOrMinus", "-");
			systemMoneyInfo.put("systemInfoUserId", luckyMap.get("memberAccount"));
			systemMoneyInfo.put("systemInfoMoney", Double.valueOf(map.get("shoppingAmount")+""));
			systemMoneyInfo.put("systemInfoFrom", "免单活动支出");
			getBaseDao().insert("SystemMoneyInfoMapper.saveSystemMoneyInfo", systemMoneyInfo);

			Map<String, Object> user_ = new HashMap<>();
			user_.put("memberAccount", luckyMap.get("memberAccount"));
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", user_);
			// 判断钱包余额是否充足:
			String walletBalance = (String) item.get("walletBalance");
			// 扣除当前人钱包金额
			Map<String, Object> user = new HashMap<>();
			user.put("memberAccount", luckyMap.get("memberAccount"));
			user.put("walletBalance", Double.valueOf(walletBalance) + Double.valueOf(map.get("shoppingAmount")+""));
			int k = getBaseDao().update("MemberMapper.updateMemberBalance", user);
			if(k > 0) {
				// 钱包扣除金额记录:
				Map<String, Object> walletMoneyInfo = new HashMap<>();
				walletMoneyInfo.put("walletInfoId", getSequence());
				walletMoneyInfo.put("walletInfoAddOrMinus", "+");
				walletMoneyInfo.put("walletInfoUserId", luckyMap.get("memberAccount"));
				walletMoneyInfo.put("walletInfoMoney", Double.valueOf(map.get("shoppingAmount")+""));
				walletMoneyInfo.put("walletInfoFrom", "幸运抽奖");
				getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
			}
			LOGGER.info("=============订单创建成功==============");
			return 1;
		}
		return 0;
	}
}
