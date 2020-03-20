package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxgoods.service.IZxGoodsExamineService;
import com.chmei.nzbdata.zxgoods.service.IZxGoodsExamineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
			int goodsSurplusNum = (int) map.get("goodsSurplusNum"); // 商品剩余数量
			if (goodsSurplusNum > 0) {
				Map<String, Object> zeroDetail = (Map<String, Object>) getBaseDao().queryForObject(
						"ZeroSeckillInfoMapper.queryZeroSeckillMaxNum", params);
				int maxLuckyOrderNumber = (int) zeroDetail.get("maxLuckyOrderNumber");
				params.put("id", getSequence());
				params.put("luckyOrderNumber", (maxLuckyOrderNumber + 1));
				int i = getBaseDao().insert("GoodsExamineMapper.buyGoodsExamineInfo", params);
				if (i > 0) {
					Map<String, Object> map_ = new HashMap<>();
					map_.put("id", params.get("goodsId")); // 商品ID
					map_.put("goodsSurplusNum", goodsSurplusNum - 1); // 商品剩余数量-1
					getBaseDao().update("GoodsExamineMapper.updateGoodsExamineNum", params);

					// TODO========

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
		int goodsSurplusNum = (int) map.get("goodsSurplusNum"); // 商品剩余数量
		return 0;
	}
}
