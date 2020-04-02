package com.chmei.nzbdata.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.util.Constants;
import com.chmei.nzbdata.util.StringUtil;
import com.chmei.nzbdata.zxgoods.service.IZxBusinessAuthService;
import com.chmei.nzbdata.zxgoods.service.IZxBusinessAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众享商家认证
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点41分
 */

@Service("zxBusinessAuthService")
public class ZxBusinessAuthServiceImpl extends BaseServiceImpl implements IZxBusinessAuthService {
	/**
	 * log對象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxBusinessAuthServiceImpl.class);

	/**
	 * 商家认证审核
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void authBusinessInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("BusinessAuthMapper.authBusinessInfo", params);
			if (i > 0) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map_ = (Map<String, Object>) getBaseDao().queryForObject(
						"BusinessAuthMapper.queryBusinessAuthDetail", params);
				// 审核状态
				String authStatus = (String) params.get("authStatus");
				String businessType = (String) map_.get("businessType");
				if("1".equals(businessType)){
					businessType = "个人";
				} else {
					businessType = "企业";
				}
				if("1002".equals(authStatus)){
					String message = "您所提交的" + businessType + "商家认证资料已通过审核！";
					Map<String, Object> map = new HashMap<>();
					map.put("id", getSequence());
					map.put("messageTitle", "商家审核通知");
					map.put("messageContent", message);
					map.put("messageStatus", "1");
					map.put("messageType", Constants.MESSAGE_TYPE_1010);
					map.put("memberAccount", map_.get("memberAccount"));
					// 添加推送消息
					getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
				} else if("1003".equals(authStatus)) {
					String message = "您所提交的" + businessType + "商家认证资料未通过审核！温馨提示："+params.get("auditOpinion");
					Map<String, Object> map = new HashMap<>();
					map.put("id", getSequence());
					map.put("messageTitle", "商家审核通知");
					map.put("messageContent", message);
					map.put("messageStatus", "1");
					map.put("messageType", Constants.MESSAGE_TYPE_1010);
					map.put("memberAccount", map_.get("memberAccount"));
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
	 * 商家认证新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void saveBusinessInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			params.put("id", getSequence());  // 主键ID
			params.put("authStatus", "1001"); // 待审核状态
			int i = getBaseDao().insert("BusinessAuthMapper.saveBusinessInfo", params);
			if (i > 0) {
				output.setCode("0");
				output.setMsg("提交成功，请留意审核通知！");
				return;
			}
			output.setCode("-1");
			output.setMsg("提交失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 编辑商家认证
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void updateBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().update("BusinessAuthMapper.updateBusinessAuthInfo", params);
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
	 * 删除商家认证
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void delBusinessAuthInfo(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().delete("BusinessAuthMapper.delBusinessAuthInfo", params);
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
	 * 查询商家认证详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryBusinessAuthDetail(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject(
					"BusinessAuthMapper.queryBusinessAuthDetail", params);
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
	 * 查询商家认证列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@Override
	public void queryBusinessAuthList(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int i = getBaseDao().getTotalCount("BusinessAuthMapper.queryBusinessAuthCount", params);
			if (i > 0) {
				List<Map<String, Object>> list = getBaseDao().queryForList("BusinessAuthMapper.queryBusinessAuthList", params);
				output.setItems(list);
			}
			output.setTotal(i);
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 开通发布窗口
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void openReleaseWindow(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			Integer openMoneyTotal = (Integer) params.get("openMoneyTotal");
			Map<String, Object> user_ = new HashMap<>();
			user_.put("memberAccount", params.get("memberAccount"));
			Map<String, Object> item = (Map<String, Object>) getBaseDao().
					queryForObject("MemberMapper.queryMemberDetail", user_);
			// 判断钱包余额是否充足:
			String walletBalance = (String) item.get("walletBalance");
			if(Double.valueOf(walletBalance) < Double.valueOf(openMoneyTotal)){
				output.setCode("-1");
				output.setMsg("钱包余额不足!");
				return;
			}
			int i = getBaseDao().update("BusinessAuthMapper.openReleaseWindow", params);
			if (i > 0) {
				Map<String, Object> system = (Map<String, Object>) getBaseDao().queryForObject(
						"SystemMoneyInfoMapper.querySystemMoneyForAuth", params);
				if (null != system) {
					BigDecimal systemInfoMoney = (BigDecimal) system.get("systemInfoMoney");
					// 系统钱包金额增加
					system.put("systemInfoMoney", systemInfoMoney.doubleValue() + Double.valueOf(openMoneyTotal));
					getBaseDao().update("SystemMoneyInfoMapper.updateSystemMoneyInfo", system);
				} else {
					// 系统钱包金额增加记录:
					Map<String, Object> systemMoneyInfo = new HashMap<>();
					systemMoneyInfo.put("systemInfoId", getSequence());
					systemMoneyInfo.put("systemInfoAddOrMinus", "+");
					systemMoneyInfo.put("systemInfoUserId", params.get("memberAccount"));
					systemMoneyInfo.put("systemInfoMoney", Double.valueOf(openMoneyTotal));
					systemMoneyInfo.put("systemInfoFrom", "开通秒杀活动保证金");
					getBaseDao().insert("SystemMoneyInfoMapper.saveSystemMoneyInfo", systemMoneyInfo);
				}
				// 扣除当前人钱包金额
				Map<String, Object> user = new HashMap<>();
				user.put("memberAccount", params.get("memberAccount"));
				user.put("walletBalance", Double.valueOf(walletBalance) - Double.valueOf(openMoneyTotal));
				int k = getBaseDao().update("MemberMapper.updateMemberBalance", user);
				if(k > 0) {
					// 钱包扣除金额记录:
					Map<String, Object> walletMoneyInfo = new HashMap<>();
					walletMoneyInfo.put("walletInfoId", getSequence());
					walletMoneyInfo.put("walletInfoAddOrMinus", "-");
					walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
					walletMoneyInfo.put("walletInfoMoney", Double.valueOf(openMoneyTotal));
					walletMoneyInfo.put("walletInfoFrom", "开通秒杀活动保证金");
					getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
				}
				output.setCode("0");
				output.setMsg("开通成功");
				return;
			}
			output.setCode("-1");
			output.setMsg("开通失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 取消发布窗口
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbDataException 自定义异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cancelReleaseWindow(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		try {
			int k = checkReleaseWindow(params); // 校验是否符合退回发布窗口
			if(k == 1){
				output.setCode("-1");
				output.setMsg("存在未完成订单！");
				return;
			} else if (k == 2) {
				output.setCode("-1");
				output.setMsg("存在未结束商品！");
				return;
			}
			Map<String, Object> winMap = new HashMap<>();
			winMap.put("id", params.get("id"));
			winMap.put("goodsType", params.get("goodsType"));
			winMap.put("memberAccount", params.get("memberAccount"));
			String goodsType = (String) params.get("goodsType");
			if("1001".equals(goodsType)){
				winMap.put("seckillWindow", params.get("cancelReleaseWindow"));
			} else if("1002".equals(goodsType)){
				winMap.put("freeWindow", params.get("cancelReleaseWindow"));
			} else if("1003".equals(goodsType)){
				winMap.put("luckyWindow", params.get("cancelReleaseWindow"));
			}
			// 第三步-更新窗口状态
			int i = getBaseDao().update("BusinessAuthMapper.cancelReleaseWindow", winMap);
			if (i > 0) {
				// 第四步-查询开通窗口保证金信息，根据窗口数量计算需要退回商户多少钱, 并更新系统钱包
				int m = updateWalletBalance(winMap); // 更新商户钱包、系统钱包
				if (m > 0) {
					output.setCode("0");
					output.setMsg("取消成功！");
					return;
				}
			}
			output.setCode("-1");
			output.setMsg("取消失败");
		} catch (Exception e) {
			LOGGER.error("系统错误", e);
		}
	}

	/**
	 * 校验是否符合退回发布窗口
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int checkReleaseWindow(Map<String, Object> params){
		String goodsType = (String) params.get("goodsType");
		Map<String, Object> orderMap = new HashMap<>();
		orderMap.put("sendGoodsAccount", params.get("memberAccount"));
		orderMap.put("orderStatus", "1003");
		orderMap.put("orderType", goodsType); // 开通类型
		// 第一步-查询是否有未完成订单
		List<Map<String, Object>> orderInfo = getBaseDao().queryForList(
				"OrderInfoMapper.queryOrderInfoDetail", orderMap);
		if (null != orderInfo && orderInfo.size() > 0) {
			return 1;
		}
		String goodsArr = "1001,1002"; // 待审核、审核通过
		params.put("goodsStatus", goodsArr);  // 商品当前状态
		// 第二步-查询是否有正在上架的商品、待审核、审核通过的
		if("1001".equals(goodsType)){
			//   1.1 秒杀商品
			List<Map<String, Object>> seckillList = getBaseDao().queryForList(
					"GoodsExamineMapper.queryMyReleaseGoodsList", params);
			if(null != seckillList && seckillList.size() > 0){
				return 2;
			}
		} else if("1002".equals(goodsType)){
			//   1.2 免费兑换
			List<Map<String, Object>> freeGoodsList = getBaseDao().queryForList(
					"FreeGoodsMapper.queryMyReleaseGoodsList", params);
			if(null != freeGoodsList && freeGoodsList.size() > 0){
				return 2;
			}
		} else if("1003".equals(goodsType)){
			//   1.3 幸运购物
			List<Map<String, Object>> luckyGoodsList = getBaseDao().queryForList(
					"LuckyGoodsMapper.queryMyReleaseGoodsList", params);
			if(null != luckyGoodsList && luckyGoodsList.size() > 0){
				return 2;
			}
		}
		return 0;
	}

	/**
	 * 计算要返回多少钱
	 * @param window
	 * @return
	 */
	private static String moneyTotal(String window){
		String money = "";
		if (StringUtil.isNotEmpty(window)) {
			switch (window){
				case "1" :
					money = "200";
					break;
				case "2" :
					money = "400";
					break;
				default:
					money = "600";
					break;
			}
		}
		return money;
	}

	/**
	 * 更新商户钱包、系统钱包
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int updateWalletBalance(Map<String, Object> params){
		Map<String, Object> system = (Map<String, Object>) getBaseDao().queryForObject(
				"SystemMoneyInfoMapper.querySystemMoneyForAuth", params);
		String windows = ""; // 窗口信息 cancelReleaseWindow
		// 第四步-查询开通窗口保证金信息，根据窗口数量计算需要退回商户多少钱, 并更新系统钱包
		String goodsType = (String) params.get("goodsType");
		if("1001".equals(goodsType)){
			windows = (String) params.get("seckillWindow");
		} else if("1002".equals(goodsType)){
			windows = (String) params.get("freeWindow");
		} else if("1003".equals(goodsType)){
			windows = (String) params.get("luckyWindow");
		}
		String money = moneyTotal(windows); // 校验回退金额
		BigDecimal systemInfoMoney = (BigDecimal) system.get("systemInfoMoney");
		// 系统钱包金额减少
		system.put("systemInfoMoney", systemInfoMoney.doubleValue() - Double.valueOf(money));
		getBaseDao().update("SystemMoneyInfoMapper.updateSystemMoneyInfo", system);
		Map<String, Object> users = new HashMap<>();
		users.put("memberAccount", params.get("memberAccount"));
		// 查询用户信息
		Map<String, Object> item = (Map<String, Object>) getBaseDao().
				queryForObject("MemberMapper.queryMemberDetail", users);
		String walletBalance = (String) item.get("walletBalance");
		// 增加当前人钱包金额
		Map<String, Object> user = new HashMap<>();
		user.put("memberAccount", params.get("memberAccount"));
		user.put("walletBalance", Double.valueOf(walletBalance) + Double.valueOf(money));
		int k = getBaseDao().update("MemberMapper.updateMemberBalance", user);
		if(k > 0) {
			// 钱包增加金额记录:
			Map<String, Object> walletMoneyInfo = new HashMap<>();
			walletMoneyInfo.put("walletInfoId", getSequence());
			walletMoneyInfo.put("walletInfoAddOrMinus", "+");
			walletMoneyInfo.put("walletInfoUserId", params.get("memberAccount"));
			walletMoneyInfo.put("walletInfoMoney", Double.valueOf(money));
			walletMoneyInfo.put("walletInfoFrom", "退回秒杀活动保证金");
			getBaseDao().insert("WalletMoneyInfoMapper.saveWalletMoneyInfo", walletMoneyInfo);
			return 1;
		}
		return 0;
	}
}
