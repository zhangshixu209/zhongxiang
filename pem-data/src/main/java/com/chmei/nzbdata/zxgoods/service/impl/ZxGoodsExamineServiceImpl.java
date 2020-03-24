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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			int i = getBaseDao().update("GoodsExamineMapper.authGoodsExamineInfo", params);
			if (i > 0) {
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
				if (StringUtil.isNotEmpty(buyMemberAccount)) {
					for (Map<String, Object> map : list) {
						Map<String, Object> result = new HashMap<>();
						result.put("goodsId", map.get("id"));
						result.put("buyMemberAccount", params.get("buyMemberAccount"));
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
			int i = getBaseDao().insert("GoodsExamineMapper.saveZeroSeckillInfo", params);
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
			int goodsSurplusNum = (int) map.get("goodsSurplusNum"); // 商品剩余数量
			if (goodsSurplusNum > 0) {
				int maxLuckyOrderNumber = getBaseDao().getTotalCount(
						"ZeroSeckillInfoMapper.queryZeroSeckillMaxNum", params);
				Map<String, Object> goods = new HashMap<>();
				goods.put("id", getSequence());
				goods.put("luckyOrderNumber", (maxLuckyOrderNumber + 1));
				goods.put("goodsId", map.get("id"));
				goods.put("buyMemberAccount", params.get("memberAccount"));
				goods.put("luckyNumber", params.get("luckyNumber"));
				int i = getBaseDao().insert("ZeroSeckillInfoMapper.buyGoodsExamineInfo", goods);
				if (i > 0) {
					Map<String, Object> map_ = new HashMap<>();
					map_.put("id", params.get("id")); // 商品ID
					map_.put("goodsSurplusNum", goodsSurplusNum - 1); // 商品剩余数量-1
					int j = getBaseDao().update("GoodsExamineMapper.updateGoodsExamineNum", map_);
					if (j > 0) {
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
					}
					// 创建订单信息
					creatOrderInfo(map_);
					output.setCode("0");
					output.setMsg("秒杀成功！");
					return;
				}
			} else {
				output.setCode("-1");
				output.setMsg("商品已结束！");
				return;
			}
			output.setCode("-1");
			output.setMsg("秒杀失败！");
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
		Map<String, Object> result = (Map<String, Object>) getBaseDao().queryForObject(
				"GoodsExamineMapper.queryGoodsExamineDetail", map);
		int goodsSurplusNum = (int) result.get("goodsSurplusNum"); // 商品剩余数量
		Map<String, Object> map_ = new HashMap<>();
		map_.put("goodsId", map.get("id"));
		if(goodsSurplusNum == 0) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("id", map.get("id"));
			maps.put("goodsStatus", "1006"); // 商品结束
			getBaseDao().update("GoodsExamineMapper.authGoodsExamineInfo", maps); // 更新商品状态为已结束
			LOGGER.info("=============订单开始创建==============");
			// 计算幸运序号
			Map<String, Object> list = (Map<String, Object>) getBaseDao().queryForObject(
					"ZeroSeckillInfoMapper.querySumGoodsLuckStar", map_);
			BigDecimal orderNum = (BigDecimal) list.get("orderNum");
			Long peopleTotal = (Long) list.get("peopleTotal");
			Map<String, Object> luckys = new HashMap<>();
			int luckyMan = orderNum.intValue() / peopleTotal.intValue();
			if (luckyMan <= 0) {
				Map<String, Object> list_ = (Map<String, Object>) getBaseDao().queryForList("ZeroSeckillInfoMapper.queryZeroSeckillInfoList", map_);
				luckyMan = (int) list_.get("luckyOrderNumber");
			}
			luckys.put("luckyMan", luckyMan); // 幸运者
			luckys.put("goodsId", result.get("id")); // 商品ID
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
				orderInfo.put("id", getSequence());
				orderInfo.put("sendGoodsAccount", result.get("memberAccount"));
				orderInfo.put("consigGoodsAccount", address.get("memberAccount"));
				orderInfo.put("consigName", address.get("consigName"));
				orderInfo.put("consigNamePhone", address.get("consigNamePhone"));
				orderInfo.put("consigArea", address.get("consigArea"));
				orderInfo.put("consigAddress", address.get("consigAddress"));
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
