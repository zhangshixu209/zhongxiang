package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.zxgoods.service.IZxOrderInfoService;
import com.chmei.nzbdata.zxgoods.service.IZxOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	 * 编辑订单信息
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
			int i = getBaseDao().update("OrderInfoMapper.updateOrderInfoInfo", params);
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
			List<Map<String, Object>> seckillList = getBaseDao().queryForList(
					"GoodsExamineMapper.queryMyReleaseGoodsList", params);
			//   1.2 免费兑换
			List<Map<String, Object>> freeGoodsList = getBaseDao().queryForList(
					"FreeGoodsMapper.queryMyReleaseGoodsList", params);
			//   1.3 幸运购物
			List<Map<String, Object>> luckyGoodsList = getBaseDao().queryForList(
					"LuckyGoodsMapper.queryMyReleaseGoodsList", params);
			listAll.addAll(seckillList);
			listAll.addAll(freeGoodsList);
			listAll.addAll(luckyGoodsList);
			// 排序
			listAll.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
				long beginMillisecond = ((Date) o1.get("crtTime")).getTime();
				long endMillisecond = ((Date) o2.get("crtTime")).getTime();
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
}
