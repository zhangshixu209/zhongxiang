package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.zxgoods.service.IZxLuckyGoodsService;
import com.chmei.nzbdata.zxgoods.service.IZxLuckyGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			int i = getBaseDao().insert("LuckyGoodsMapper.saveLuckyGoodsInfo", params);
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
			int i = getBaseDao().update("LuckyGoodsMapper.updateLuckyGoodsInfo", params);
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
			String integralMoney = (String) item.get("integralMoney"); // 积分
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
					// TODO====商家金额如何增加确认收货后增加
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
		orderInfo.put("id", getSequence());
		orderInfo.put("sendGoodsAccount", map.get("sendMemberAccount"));
		orderInfo.put("consigGoodsAccount", address.get("memberAccount"));
		orderInfo.put("consigName", address.get("consigName"));
		orderInfo.put("consigNamePhone", address.get("consigNamePhone"));
		orderInfo.put("consigArea", address.get("consigArea"));
		orderInfo.put("consigAddress", address.get("consigAddress"));
		orderInfo.put("orderStatus", "1001");      // 待发货
		orderInfo.put("orderType", "1003");        // 幸运购物
		orderInfo.put("goodsId", map.get("id"));   // 商品ID
		int i = getBaseDao().insert("OrderInfoMapper.saveOrderInfoInfo", orderInfo);
		if (i > 0) {
			LOGGER.info("=============订单创建成功==============");
			return 1;
		}
		return 0;
	}
}
