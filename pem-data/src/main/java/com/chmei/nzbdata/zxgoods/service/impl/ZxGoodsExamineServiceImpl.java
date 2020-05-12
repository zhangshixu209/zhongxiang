package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxgoods.service.IZxGoodsExamineService;
import com.chmei.nzbdata.zxgoods.service.IZxGoodsExamineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.util.*;

/**
 * 众享商品
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点41分
 */

@Service("zxGoodsExamineService")
public class ZxGoodsExamineServiceImpl extends BaseServiceImpl implements IZxGoodsExamineService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxGoodsExamineServiceImpl.class);

	/**
	 * 商品审核
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void authGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("GoodsExamineMapper.authGoodsExamineInfo", params);
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
				}
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
	 * 商品上架、下架（秒杀中的商品下架需要返还参与者金额）
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void goodsShelfInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) getBaseDao().queryForObject(
					"GoodsExamineMapper.queryGoodsExamineDetail", params);
			int goodsSurplusNum = (int) result.get("goodsSurplusNum");   // 商品剩余数量
			if (goodsSurplusNum > 0) {
				Map<String, Object> map_ = new HashMap<>();
				map_.put("goodsId", params.get("id"));
				map_.put("rotationNum", result.get("rotationNum")); // 商品轮次
//				int goodsReleaseNum = (int) result.get("goodsReleaseNum");   // 商品发布数量
				int partakeNumber = (int) result.get("partakeNumber");   // 商品总需参与人数 partakeNumber
				// 根据轮次和商品ID查询参与总人数
				int isSeckill = getBaseDao().getTotalCount("ZeroSeckillInfoMapper.queryIsZeroSeckillInfo", map_);
				int num = partakeNumber - isSeckill;
				if (num == 0 || isSeckill == 0) {
					params.put("goodsSurplusNum", 0);
					getBaseDao().update("GoodsExamineMapper.updateGoodsExamineNum", params);
				} else {
					params.put("goodsSurplusNum", 1);
					getBaseDao().update("GoodsExamineMapper.updateGoodsExamineNum", params);
				}
			}
			int i = getBaseDao().update("GoodsExamineMapper.authGoodsExamineInfo", params);
			if (i > 0) {
				// 审核状态
				String goodsStatus = (String) params.get("goodsStatus");
				if("1005".equals(goodsStatus)) {
					String message = "您所发布的“" + params.get("goodsDesc") + "”商品已被下架！如有疑问请联系我们！下架原因："+params.get("auditOpinion");
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
				output.setMsg("下架成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("下架失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 删除商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().delete("GoodsExamineMapper.delGoodsExamineInfo", params);
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
	 * 查询商品详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryGoodsExamineDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"GoodsExamineMapper.queryGoodsExamineDetail", params);
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
	 * 查询商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryGoodsExamineList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("GoodsExamineMapper.queryGoodsExamineCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("GoodsExamineMapper.queryGoodsExamineList", params);
				String buyMemberAccount = (String) params.get("buyMemberAccount"); // APP当前登录人账号
				for (Map<String, Object> map : list) {
					Map<String, Object> result = new HashMap<>();
					long joinNum = (long) map.get("joinNum");     // 参与人数
					int partakeNumber = (int) map.get("partakeNumber"); // 总人数
					long supNum = partakeNumber - joinNum; // 如果参与人数够了
					map.put("supNum", supNum);
					result.put("goodsId", map.get("id"));
					result.put("buyMemberAccount", params.get("buyMemberAccount"));
					if (StringUtil.isNotEmpty(buyMemberAccount)) {
						int isBuy = getBaseDao().getTotalCount("ZeroSeckillInfoMapper.queryZeroSeckillIsBuy", result);
						if (isBuy > 0) {
							map.put("isBuy", "1"); // 已参与该活动
						} else {
							map.put("isBuy", "0"); // 未参与该活动
						}
					}
				}
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 零元秒杀新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int neededAdFeeTotal = (int) params.get("neededAdFeeTotal"); // 广告费总数
			int goodsParcelPrice = (int) params.get("goodsParcelPrice"); // 商品包邮价格
			if ((neededAdFeeTotal * 0.1) > goodsParcelPrice) {
				output.setCode("-1");
				output.setMsg("总需广告费不能大于商品价值的10倍！");
				return;
			}
			params.put("id", getSequence());
			params.put("goodsStatus", "1001");
			params.put("rotationNum",getSequence()); // 轮次
			int i = getBaseDao().insert("GoodsExamineMapper.saveZeroSeckillInfo", params);
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
	 * 零元秒杀购买
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buyGoodsExamineInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"GoodsExamineMapper.queryGoodsExamineDetail", params);
			Map<String, Object> user_ = new HashMap<>();
			user_.put("memberAccount", params.get("memberAccount"));
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", user_);
			// 判断广告费余额是否充足:
			String advertisingMoney = (String) item.get("advertisingFee");
			if(Double.valueOf(advertisingMoney) < Double.valueOf(map.get("neededAdFee")+"")){
				output.setCode("-1");
				output.setMsg("广告费不足!");
				return;
			}
			// TODO 总人数=购买人数时，开始抽奖
			long joinNum = (long) map.get("joinNum");     // 参与人数
			int partakeNumber = (int) map.get("partakeNumber"); // 总人数
			long supNum = partakeNumber - joinNum; // 如果参与人数够了
			int goodsSurplusNum = (int) map.get("goodsSurplusNum"); // 商品剩余数量
			if (goodsSurplusNum > 0) { // 剩余数量
				if (supNum > 0) {
					params.put("rotationNum", map.get("rotationNum"));
					int maxLuckyOrderNumber = getBaseDao().getTotalCount(
							"ZeroSeckillInfoMapper.queryZeroSeckillMaxNum", params);
					Map<String, Object> goods = new HashMap<>();
					goods.put("id", getSequence());
					goods.put("luckyOrderNumber", (maxLuckyOrderNumber + 1));
					goods.put("goodsId", map.get("id"));
					goods.put("buyMemberAccount", params.get("memberAccount"));
					goods.put("luckyNumber", params.get("luckyNumber"));
					goods.put("rotationNum", map.get("rotationNum"));
					int i = getBaseDao().insert("ZeroSeckillInfoMapper.buyGoodsExamineInfo", goods);
					if (i > 0) {
						// 扣除当前人广告费金额
						Map<String, Object> user = new HashMap<>();
						user.put("memberAccount", params.get("memberAccount"));
						user.put("advertisingFee", Double.valueOf(advertisingMoney) - Double.valueOf(map.get("neededAdFee")+""));
						int k = getBaseDao().update("MemberMapper.updateMemberBalance", user);
						if(k > 0) {
							// 记录广告费扣款记录:
							Map<String, Object> adRecord = new HashMap<>();
							adRecord.put("advertisingInfoId", getSequence());
							adRecord.put("advertisingInfoAddOrMinus", "-");
							adRecord.put("advertisingInfoUserId", params.get("memberAccount"));
							adRecord.put("advertisingInfoMoney", Double.valueOf(map.get("neededAdFee")+""));
							adRecord.put("advertisingInfoFrom", "秒杀商品");
							getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);
						}
						// 创建订单信息
						creatOrderInfo(params);
						output.setCode("0");
						output.setMsg("参与成功！");
						return;
					}
				}
			} else {
				output.setCode("-1");
				output.setMsg("活动已结束！");
				return;
			}
			output.setCode("-1");
			output.setMsg("参与失败！");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 零元秒杀幸运榜
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZeroSeckillLuckyList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("GoodsExamineMapper.queryZeroSeckillLuckyCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("GoodsExamineMapper.queryZeroSeckillLuckyList", params);
				if (null != list && list.size() > 0) {
					output.setItems(list);
				}
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 零元秒杀幸运榜参与详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryZeroSeckillPartakeList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"GoodsExamineMapper.queryPartakeHeadInfo", params);
			int total = getBaseDao().getTotalCount("ZeroSeckillInfoMapper.queryZeroSeckillInfoCount", params);
			if (total > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList(
						"ZeroSeckillInfoMapper.queryZeroSeckillInfoList", params);
				if (null != list && list.size() > 0) {
					output.setItems(list);
				}
			}
			output.setTotal(total);
			output.setItem(map);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 新增商品关注次数
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveGoodsViewCount(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("GoodsExamineMapper.queryGoodsViewCount", params);
			if (i > 0) {
				output.setCode("-1");
//				output.setMsg("已关注");
				return;
			}
			// 新增众享信息浏览次数
			int count = getBaseDao().insert("GoodsExamineMapper.saveGoodsViewCount", params);
			if (count < 1) {
				output.setCode("-1");
				output.setMsg("关注失败");
				return;
			}
			output.setCode("0");
//			output.setMsg("关注成功");
		} catch (Exception ex) {
			LOGGER.error("系统错误: " + ex);
		}
	}

	/**
	 * 校验是否开通发布窗口
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void checkReleaseWindow(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			String goodsType = (String) params.get("goodsType");
			params.put("authStatus", "1003");
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"BusinessAuthMapper.queryBusinessAuthDetail", params);
			if (null == map) {
				output.setCode("-1");
				output.setMsg("请开通商品发布窗口！");
				return;
			}
			String goodsArr = "1001,1002"; // 待审核、审核通过
			params.put("goodsStatus", goodsArr);  // 商品当前状态
			if("1001".equals(goodsType)){
				//   1.1 秒杀商品
				List<Map<String, Object>> seckillList = getBaseDao().queryForList(
						"GoodsExamineMapper.queryMyReleaseGoodsList", params);
				String seckillWindow = (String) map.get("seckillWindow");
				if (null != seckillWindow && Integer.valueOf(seckillWindow) > 0) {
					if(null != seckillList && seckillList.size() > 0){
						if (seckillList.size() >= Integer.valueOf(seckillWindow)) {
							output.setCode("-1");
							output.setMsg("商品发布窗口不足！");
							return;
						}
					}
				} else {
					output.setCode("-1");
					output.setMsg("请开通商品发布窗口！");
					return;
				}
			} else if("1002".equals(goodsType)){
				//   1.2 免费兑换
				List<Map<String, Object>> freeGoodsList = getBaseDao().queryForList(
						"FreeGoodsMapper.queryMyReleaseGoodsList", params);
				String freeWindow = (String) map.get("freeWindow");
				if (null != freeWindow && Integer.valueOf(freeWindow) > 0) {
					if(null != freeGoodsList && freeGoodsList.size() > 0){
						if (freeGoodsList.size() >= Integer.valueOf(freeWindow)) {
							output.setCode("-1");
							output.setMsg("商品发布窗口不足！");
							return;
						}
					}
				} else {
					output.setCode("-1");
					output.setMsg("请开通商品发布窗口！");
					return;
				}
			} else if("1003".equals(goodsType)){
				//   1.3 幸运购物
				List<Map<String, Object>> luckyGoodsList = getBaseDao().queryForList(
						"LuckyGoodsMapper.queryMyReleaseGoodsList", params);
				String luckyWindow = (String) map.get("luckyWindow");
				if (null != luckyWindow && Integer.valueOf(luckyWindow) > 0) {
					if(null != luckyGoodsList && luckyGoodsList.size() > 0){
						if (luckyGoodsList.size() >= Integer.valueOf(luckyWindow)) {
							output.setCode("-1");
							output.setMsg("商品发布窗口不足！");
							return;
						}
					}
				} else {
					output.setCode("-1");
					output.setMsg("请开通商品发布窗口！");
					return;
				}
			}
			output.setCode("0");
//			output.setMsg("校验通过！");
		} catch (Exception ex) {
			LOGGER.error("系统错误: " + ex);
		}
	}

	/**
	 * 创建订单信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int creatOrderInfo(Map<String, Object> map){
		Map<String, Object> result = (Map<String, Object>) getBaseDao().queryForObject(
				"GoodsExamineMapper.queryGoodsExamineDetail", map);
		int goodsSurplusNum = (int) result.get("goodsSurplusNum"); // 商品剩余数量
		Map<String, Object> map_ = new HashMap<>();
		map_.put("goodsId", map.get("id"));
		map_.put("rotationNum", map.get("rotationNum")); // 商品轮次
		long joinNum = (long) result.get("joinNum");     // 参与人数
		int partakeNumber = (int) result.get("partakeNumber"); // 总人数
		long supNum = partakeNumber - joinNum; // 如果参与人数够了
		if(supNum == 0) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("id", map.get("id")); // 商品ID
			map1.put("goodsSurplusNum", goodsSurplusNum - 1); // 商品剩余数量-1
			map1.put("rotationNum", getSequence());           // 更新商品轮次
			getBaseDao().update("GoodsExamineMapper.updateGoodsExamineNum", map1);
			int goodsSurplusNumSur = goodsSurplusNum - 1;
			if (goodsSurplusNumSur == 0) { // 商品数量为0 更新商品状态为已结束
				Map<String, Object> maps = new HashMap<>();
				maps.put("id", map.get("id"));
				maps.put("goodsStatus", "1006"); // 商品结束
				getBaseDao().update("GoodsExamineMapper.authGoodsExamineInfo", maps); // 更新商品状态为已结束
			}
			LOGGER.info("=============订单开始创建==============");
			// 计算幸运序号
			Map<String, Object> list = (Map<String, Object>) getBaseDao().queryForObject(
					"ZeroSeckillInfoMapper.querySumGoodsLuckStar", map_);
			Double orderNum = (Double) list.get("orderNum");
			Long peopleTotal = (Long) list.get("peopleTotal");
			Map<String, Object> luckys = new HashMap<>();
			int luckyMan = orderNum.intValue() % peopleTotal.intValue();
			if (luckyMan == 0) {
				Map<String, Object> list_ = (Map<String, Object>) getBaseDao().queryForObject(
						"ZeroSeckillInfoMapper.queryZeroSeckillInfoOne", map_);
				luckyMan = (int) list_.get("luckyOrderNumber");
			}
			luckys.put("luckyMan", luckyMan); // 幸运者
			luckys.put("goodsId", result.get("id")); // 商品ID
			luckys.put("rotationNum", result.get("rotationNum")); // 商品轮次
			Map<String, Object> lucky = (Map<String, Object>) getBaseDao().queryForObject(
					"ZeroSeckillInfoMapper.queryZeroSeckillInfoDetail", luckys);
			lucky.put("goodsLuckStar", 1); // 幸运标识
			// 更新幸运者
			int i = getBaseDao().update("ZeroSeckillInfoMapper.updateGoodsLuckStar", lucky);
			if (i > 0) {
				luckys.put("memberAccount", lucky.get("buyMemberAccount"));
				// 查询默认收货地址
				Map<String, Object> address = (Map<String, Object>) getBaseDao().queryForObject(
						"ReceivingAddressMapper.queryAddressIsDefault", luckys);
				// 创建订单信息
				Map<String, Object> orderInfo = new HashMap<>();
				String orderArea = (String) address.get("consigArea");
				orderArea = orderArea.replace("$", "");
				orderInfo.put("id", getSequence());
				orderInfo.put("sendGoodsAccount", result.get("memberAccount"));
				orderInfo.put("consigGoodsAccount", address.get("memberAccount"));
				orderInfo.put("consigName", address.get("consigName"));
				orderInfo.put("consigNamePhone", address.get("consigNamePhone"));
//				orderInfo.put("consigArea", address.get("consigArea"));
				orderInfo.put("consigAddress", orderArea+address.get("consigAddress"));
				orderInfo.put("orderStatus", "1001");      // 待发货
				orderInfo.put("orderType", "1001");        // 零元秒杀
				orderInfo.put("goodsId", map.get("id"));   // 商品ID
				getBaseDao().insert("OrderInfoMapper.saveOrderInfoInfo", orderInfo);
				LOGGER.info("=============订单创建成功==============");
				return 1;
			}
		}
		return 0;
	}

}
