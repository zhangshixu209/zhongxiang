package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.zxgoods.service.IZxFreeGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众享免费兑换商品
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点41分
 */

@Service("zxFreeGoodsService")
public class ZxFreeGoodsServiceImpl extends BaseServiceImpl implements IZxFreeGoodsService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxFreeGoodsServiceImpl.class);

	/**
	 * 新增免费兑换商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int neededAdFeeTotal = (int) params.get("neededAdFee"); // 广告费总数
			int goodsParcelPrice = (int) params.get("goodsParcelPrice"); // 商品包邮价格goods_parcel_price
			if ((neededAdFeeTotal * 0.1) > goodsParcelPrice) {
				output.setCode("-1");
				output.setMsg("所需广告费不能大于商品价值的10倍！");
				return;
			}
			params.put("id", getSequence()); // 主键ID
			params.put("goodsStatus", "1001");
			int i = getBaseDao().insert("FreeGoodsMapper.saveFreeGoodsInfo", params);
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
	 * 编辑免费兑换商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("FreeGoodsMapper.authFreeGoodsInfo", params);
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
	 * 删除免费兑换商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().delete("FreeGoodsMapper.delFreeGoodsInfo", params);
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
	 * 查询免费兑换商品详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryFreeGoodsDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"FreeGoodsMapper.queryFreeGoodsDetail", params);
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
	 * 查询免费兑换商品列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryFreeGoodsList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("FreeGoodsMapper.queryFreeGoodsCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("FreeGoodsMapper.queryFreeGoodsList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 购买免费兑换商品
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buyFreeGoodsInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"FreeGoodsMapper.queryFreeGoodsDetail", params);
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
			int goodsSurplusNum = (int) map.get("goodsSurplusNum"); // 商品剩余数量
			if (goodsSurplusNum > 0) {
				Map<String, Object> map_ = new HashMap<>();
				map_.put("id", params.get("id")); // 商品ID
				map_.put("goodsSurplusNum", goodsSurplusNum - 1); // 商品剩余数量-1
				map_.put("memberAccount", params.get("memberAccount")); // 购买用户账号
				map_.put("sendMemberAccount", map.get("memberAccount")); // 商品发布用户账号
				// 创建订单信息
				int i = creatOrderInfo(map_);
				if (i > 0) {
					int j = getBaseDao().update("FreeGoodsMapper.updateFreeGoodsNum", map_);
					if (j > 0) {
						int goodsSurplusNumSur = goodsSurplusNum - 1;
						if (goodsSurplusNumSur == 0) { // 商品数量为0 更新商品状态为已结束
							Map<String, Object> maps = new HashMap<>();
							maps.put("id", map.get("id"));
							maps.put("goodsStatus", "1006"); // 商品结束
							getBaseDao().update("FreeGoodsMapper.authFreeGoodsInfo", maps); // 更新商品状态为已结束
						}
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
							adRecord.put("advertisingInfoFrom", "兑换商品");
							getBaseDao().insert("AdvertisingMoneyInfoMapper.saveAdvertisingMoneyInfo", adRecord);

						}
					}
					output.setCode("0");
					output.setMsg("兑换成功！");
					return;
				}
			} else {
				output.setCode("-1");
				output.setMsg("商品已结束！");
				return;
			}
			output.setCode("-1");
			output.setMsg("兑换失败！");
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
		orderInfo.put("orderType", "1002");        // 免费兑换
		orderInfo.put("goodsId", map.get("id"));   // 商品ID
		int i = getBaseDao().insert("OrderInfoMapper.saveOrderInfoInfo", orderInfo);
		if (i > 0) {
			LOGGER.info("=============订单创建成功==============");
			return 1;
		}
		return 0;
	}
}
