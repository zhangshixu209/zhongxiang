package com.chmei.nzbdata.zxgoods.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.KdniaoTrackQueryAPI;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxgoods.service.IZxOrderInfoService;
import com.chmei.nzbdata.zxgoods.service.IZxOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 众享订单信息
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点41分
 */

@Service("zxOrderInfoService")
public class ZxOrderInfoServiceImpl extends BaseServiceImpl implements IZxOrderInfoService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxOrderInfoServiceImpl.class);

	/**
	 * 新增订单信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence()); // 主键ID
			int i = getBaseDao().insert("OrderInfoMapper.saveOrderInfoInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("保存成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("保存失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 编辑订单信息(商家发货使用)
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("orderStatus", "1002"); // 发货状态
			int i = getBaseDao().update("OrderInfoMapper.updateOrderInfoInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("发货成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("发货失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 删除订单信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delOrderInfoInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().delete("OrderInfoMapper.delOrderInfoInfo", params);
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
	 * 查询订单信息详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryOrderInfoDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"OrderInfoMapper.queryOrderInfoDetail", params);
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
	 * 查询订单信息列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryOrderInfoList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("OrderInfoMapper.queryOrderInfoCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("OrderInfoMapper.queryOrderInfoList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询我的发布商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryMyReleaseGoodsList(InputDTO input, OutputDTO output) throws NzbDataException {
		LOGGER.info("ZxMessageServiceImpl.queryMyReleaseGoodsList, input::" + input.getParams().toString());
		Map<String, Object> params = input.getParams();
		List<Map<String, Object>> listAll = new ArrayList<>(); // 重新封装红包list
		try {
			//   1.1 秒杀商品
			List<Map<String, Object>> seckillList;
			if (StringUtil.isNotEmpty((String) params.get("buyMemberAccount"))) {
				seckillList = getBaseDao().queryForList(
						"GoodsExamineMapper.queryGoodsExamineList", params);
				for (Map<String, Object> map : seckillList) {
					Map<String, Object> result = new HashMap<>();
					long joinNum = (long) map.get("joinNum");     // 参与人数
					int partakeNumber = (int) map.get("partakeNumber"); // 总人数
					long supNum = partakeNumber - joinNum; // 如果参与人数够了
					map.put("supNum", supNum);
					result.put("goodsId", map.get("id"));
					result.put("buyMemberAccount", params.get("buyMemberAccount"));
					if (StringUtil.isNotEmpty((String) params.get("buyMemberAccount"))) {
						int isBuy = getBaseDao().getTotalCount("ZeroSeckillInfoMapper.queryZeroSeckillIsBuy", result);
						if (isBuy > 0) {
							map.put("isBuy", "1"); // 已参与该活动
						} else {
							map.put("isBuy", "0"); // 未参与该活动
						}
					}
				}
			} else {
				seckillList = getBaseDao().queryForList(
						"GoodsExamineMapper.queryMyReleaseGoodsList", params);
			}
			//   1.2 免费兑换
			List<Map<String, Object>> freeGoodsList = getBaseDao().queryForList(
					"FreeGoodsMapper.queryMyReleaseGoodsList", params);
			//   1.3 幸运购物
			List<Map<String, Object>> luckyGoodsList = getBaseDao().queryForList(
					"LuckyGoodsMapper.queryMyReleaseGoodsList", params);
			if(null != seckillList && seckillList.size() > 0){
				for (Map<String, Object> map : seckillList) {
					Integer goodsReleaseNum = (Integer) map.get("goodsReleaseNum"); // 发布数量
					Long receivedGoods = (Long) map.get("receivedGoods");		// 订单已收货数量
					if(null != receivedGoods){
						if (goodsReleaseNum.equals(receivedGoods.intValue())) { // 如果发布数量等于订单数量，已结束
							map.put("goodsStatus", "1006");
						} else if ("1001".equals(map.get("goodsStatus"))){
							map.put("goodsStatus", "1001");
						} else {
							map.put("goodsStatus", "1002");
						}
					}
				}
				listAll.addAll(seckillList);
			}
			if(null != freeGoodsList && freeGoodsList.size() > 0){
				for (Map<String, Object> map : freeGoodsList) {
					Integer goodsReleaseNum = (Integer) map.get("goodsReleaseNum"); // 发布数量
					Long receivedGoods = (Long) map.get("receivedGoods");		// 订单已收货数量
					if (goodsReleaseNum.equals(receivedGoods.intValue())) { // 如果发布数量等于订单数量，已结束
						map.put("goodsStatus", "1006");
					} else if ("1001".equals(map.get("goodsStatus"))){
						map.put("goodsStatus", "1001");
					} else {
						map.put("goodsStatus", "1002");
					}
				}
				listAll.addAll(freeGoodsList);
			}
			if(null != luckyGoodsList && luckyGoodsList.size() > 0){
				for (Map<String, Object> map : luckyGoodsList) {
					Integer goodsReleaseNum = (Integer) map.get("goodsReleaseNum"); // 发布数量
					Long receivedGoods = (Long) map.get("receivedGoods");		// 订单已收货数量
					if (goodsReleaseNum.equals(receivedGoods.intValue())) { // 如果发布数量等于订单数量，已结束
						map.put("goodsStatus", "1006");
					} else if ("1001".equals(map.get("goodsStatus"))){
						map.put("goodsStatus", "1001");
					} else {
						map.put("goodsStatus", "1002");
					}
				}
				listAll.addAll(luckyGoodsList);
			}
			// 排序
			listAll.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
				long beginMillisecond = ((Date) o1.get("releaseTime")).getTime();
				long endMillisecond = ((Date) o2.get("releaseTime")).getTime();
				if(beginMillisecond > endMillisecond){
					return -1;
				}
				return 1;
			});
			output.setItems(listAll);
			output.setTotal(listAll.size());
		} catch (Exception e) {
			LOGGER.error("查询失败: " + e);
		}
	}

	/**
	 * 查询我的发布商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryMyReleaseOrderDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"OrderInfoMapper.queryMyReleaseOrderDetail", params);
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
	 * 用户确认收货
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void userConfirmReceipt(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("orderStatus", "1003"); // 确认收货
			int i = getBaseDao().update("OrderInfoMapper.updateOrderInfoInfo", params);
			if (i > 0) {
				Map<String, Object> map_ = new HashMap<>();
				map_.put("id", params.get("id"));
				// 查询订单详情
				Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
						"OrderInfoMapper.queryOrderInfoDetail", map_);
				Map<String, Object> user_ = new HashMap<>();
				user_.put("memberAccount", map.get("sendGoodsAccount"));
				// 查询用户信息
				Map<String, Object> item = (Map<String, Object>) getBaseDao().
						queryForObject("MemberMapper.queryMemberDetail", user_);
				String advertisingMoney = (String) item.get("advertisingFee"); // 广告费
				String orderType = (String) map.get("orderType");
				if ("1001".equals(orderType)) {
					Map<String, Object> seckill = new HashMap<>();
					seckill.put("id", map.get("goodsId"));
					Map<String, Object> result1 = (Map<String, Object>) getBaseDao().queryForObject(
							"GoodsExamineMapper.queryGoodsExamineDetail", seckill);
					// 增加商家广告费金额
					Map<String, Object> user1 = new HashMap<>();
					user1.put("memberAccount", map.get("sendGoodsAccount"));
					user1.put("advertisingFee", Double.valueOf(advertisingMoney) + Double.valueOf(result1.get("neededAdFeeTotal")+""));
					int m = getBaseDao().update("MemberMapper.updateMemberBalance", user1);
					if(m > 0) {
						// 记录广告费增加记录:
						Map<String, Object> adRecord1 = new HashMap<>();
						adRecord1.put("advertisingInfoId", getSequence());
						adRecord1.put("advertisingInfoAddOrMinus", "+");
						adRecord1.put("advertisingInfoUserId", map.get("sendGoodsAccount"));
						adRecord1.put("advertisingInfoMoney", Double.valueOf(result1.get("neededAdFeeTotal")+""));
						adRecord1.put("advertisingInfoFrom", "发布秒杀商品");
						getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord1);
					}
				} else if ("1002".equals(orderType)){
					Map<String, Object> free = new HashMap<>();
					free.put("id", map.get("goodsId"));
					Map<String, Object> result2 = (Map<String, Object>) getBaseDao().queryForObject(
							"FreeGoodsMapper.queryFreeGoodsDetail", free);
					// 增加商家广告费金额
					Map<String, Object> user1 = new HashMap<>();
					user1.put("memberAccount", map.get("sendGoodsAccount"));
					user1.put("advertisingFee", Double.valueOf(advertisingMoney) + Double.valueOf(result2.get("neededAdFee")+""));
					int m = getBaseDao().update("MemberMapper.updateMemberBalance", user1);
					if (m > 0) {
						// 记录广告费增加记录:
						Map<String, Object> adRecord_ = new HashMap<>();
						adRecord_.put("advertisingInfoId", getSequence());
						adRecord_.put("advertisingInfoAddOrMinus", "+");
						adRecord_.put("advertisingInfoUserId", map.get("sendGoodsAccount"));
						adRecord_.put("advertisingInfoMoney", Double.valueOf(result2.get("neededAdFee")+""));
						adRecord_.put("advertisingInfoFrom", "发布兑换商品");
						getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord_);
					}
				} else if ("1003".equals(orderType)) {
					Map<String, Object> lucky = new HashMap<>();
					lucky.put("id", map.get("goodsId"));
					Map<String, Object> result3 = (Map<String, Object>) getBaseDao().queryForObject(
							"LuckyGoodsMapper.queryLuckyGoodsDetail", lucky);
					Map<String, Object> maps = new HashMap<>();
					maps.put("orderId", map.get("id")); // 订单ID
					maps.put("goodsParcelPrice", result3.get("goodsParcelPrice"));     // 支付金额
					maps.put("consigGoodsAccount", map.get("consigGoodsAccount")); // 收货人账号
					maps.put("sendGoodsAccount", map.get("sendGoodsAccount"));     // 发布人账号
					luckyGoodsInfo(maps); // 幸运购物逻辑处理
				}
				output.setCode("0");
				output.setMsg("确认成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("确认失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询在途商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryGoodsInTransitList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("OrderInfoMapper.queryGoodsInTransitCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("OrderInfoMapper.queryGoodsInTransitList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 查询订单物流信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryOrderLogisticsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		try {
			String expCode = (String) params.get("expressName"); //第一个参数是快递公司简称（YD -- 韵达速递）
			String expNo = (String) params.get("expressNumber");     //第二个参数是需要查询的快递单号
			String result = api.getOrderTracesByJson(expCode, expNo);
			JSONObject jsonObject = JSONObject.parseObject(result);
			List<Map<String, Object>> list = new ArrayList<>();
			if(jsonObject.containsKey("ShipperCode")){
				JSONArray Traces = jsonObject.getJSONArray("Traces");
				for(int i = 0; i < Traces.size(); i++) {
					Map<String, Object> map = new HashMap<>();
					JSONObject object = (JSONObject) Traces.get(i);
					String AcceptTime = object.getString("AcceptTime");
					String AcceptStation = object.getString("AcceptStation");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d2 = sdf2.parse(AcceptTime);
					map.put("logisticsTime", d2); // 快递时间
					map.put("logistics", AcceptStation);  // 快递信息
					list.add(map);
				}
			}
			// 排序
			list.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
				long beginMillisecond = ((Date) o1.get("logisticsTime")).getTime();
				long endMillisecond = ((Date) o2.get("logisticsTime")).getTime();
				if(beginMillisecond > endMillisecond){
					return -1;
				}
				return 1;
			});
			output.setItems(list);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 幸运购物逻辑处理
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int luckyGoodsInfo(Map<String, Object> map){
		Map<String, Object> user = new HashMap<>();
		user.put("memberAccount", map.get("consigGoodsAccount"));
		// 查询用户信息
		Map<String, Object> item_ = (Map<String, Object>) getBaseDao().
				queryForObject("MemberMapper.queryMemberDetail", user);
		String integralMoney = (String) item_.get("integralMoney"); // 积分
		// 增加当前人积分金额
		Map<String, Object> user1 = new HashMap<>();
		user1.put("memberAccount", map.get("consigGoodsAccount"));
		user1.put("integralMoney", Double.valueOf(integralMoney) + Double.valueOf(map.get("goodsParcelPrice")+""));
		int m = getBaseDao().update("MemberMapper.updateMemberBalance", user1);
		if(m > 0) {
			// 增加积分金额记录:
			Map<String, Object> integralMoneyInfo = new HashMap<>();
			integralMoneyInfo.put("integralInfoId", getSequence());
			integralMoneyInfo.put("integralInfoAddOrMinus", "+");
			integralMoneyInfo.put("integralInfoUserId", map.get("consigGoodsAccount"));
			integralMoneyInfo.put("integralInfoMoney", Double.valueOf(map.get("goodsParcelPrice")+""));
			integralMoneyInfo.put("integralInfoFrom", "幸运积分");
			getBaseDao().insert("IntegralMoneyInfoMapper.saveIntegralMoneyInfo", integralMoneyInfo);
		}
		////////////////////////////////////////商家相关////////////////////////////////////////////////////
		Map<String, Object> user_ = new HashMap<>();
		user_.put("memberAccount", map.get("sendGoodsAccount"));
		// 查询用户信息
		Map<String, Object> item = (Map<String, Object>) getBaseDao().
				queryForObject("MemberMapper.queryMemberDetail", user_);
		String walletBalance = (String) item.get("walletBalance"); // 钱包余额
		Map<String, Object> systemMap = new HashMap<>();
		systemMap.put("systemInfoUserId", "999999999");
		// 查询系统钱包
		Map<String, Object> system = (Map<String, Object>) getBaseDao().
				queryForObject("SystemMoneyInfoMapper.querySystemMoneyDetail", systemMap);
		BigDecimal goodsParcelPrice = (BigDecimal) system.get("systemInfoMoney");
		double goodsPrice = Double.valueOf(map.get("goodsParcelPrice")+"") / 2; // 系统扣除一半的钱给商家
		// 扣除当前人钱包金额
		Map<String, Object> userA = new HashMap<>();
		userA.put("memberAccount", item.get("memberAccount"));
		userA.put("walletBalance", Double.valueOf(walletBalance) + goodsPrice);
		int k = getBaseDao().update("MemberMapper.updateMemberBalance", userA);
		if(k > 0) {
			// 商家钱包增加金额记录:
			Map<String, Object> walletMoneyInfo = new HashMap<>();
			walletMoneyInfo.put("walletInfoId", getSequence());
			walletMoneyInfo.put("walletInfoAddOrMinus", "+");
			walletMoneyInfo.put("walletInfoUserId", item.get("memberAccount"));
			walletMoneyInfo.put("walletInfoMoney", goodsPrice);
			walletMoneyInfo.put("walletInfoFrom", "发布幸运购物");
			getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
			// 系统钱包金额减少
			system.put("systemInfoMoney", goodsParcelPrice.doubleValue() - goodsPrice);
			getBaseDao().update("SystemMoneyInfoMapper.updateSystemMoneyInfo", system);
			// 系统钱包金额减少记录:
			Map<String, Object> systemMoneyInfo = new HashMap<>();
			systemMoneyInfo.put("systemInfoId", getSequence());
			systemMoneyInfo.put("systemInfoAddOrMinus", "-");
			systemMoneyInfo.put("systemInfoUserId", item.get("memberAccount"));
			systemMoneyInfo.put("orderId", map.get("orderId")); // 订单ID
			systemMoneyInfo.put("systemInfoMoney", goodsPrice);
			systemMoneyInfo.put("systemInfoFrom", "幸运购物支付给商家");
			getBaseDao().insert("SystemMoneyInfoMapper.saveSystemMoneyInfo", systemMoneyInfo);
			return 1;
		}
		return 0;
	}
}
