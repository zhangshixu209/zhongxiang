package com.chmei.nzbdata.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.redpacket.service.IRedPacketService;
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
					@SuppressWarnings("unchecked")
					Map<String, Object> stringObjectMap = (Map<String, Object>) getBaseDao().
							queryForObject("RedPacketMapper.queryRedPacketLog", params.get("redPacketId"));
					// 发布成功,直接进行红包的分配:
					List<Double> doubles = gradRedPacket(redPacketCount, redPacketMoneyCount.doubleValue());
					int i = this.insertListRedPacketImgInfo(doubles, (Long) params.get("redPacketId"));
					if(i > 0 && stringObjectMap != null){
						output.setCode("0");
						output.setMsg("发布红包成功!");
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
	public void updateImgRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbDataException {
		Map<String, Object> params = input.getParams();
		// 更新抢红包信息表
		int i = getBaseDao().update("RedPacketMapper.updateRedPacketInfoById", params);
		if(i > 0){
			@SuppressWarnings("unchecked")
			Map<String, Object> packet = (Map<String, Object>) getBaseDao().
					queryForObject("RedPacketMapper.queryRedPacketLog", params.get("redPacketId"));
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
				}
			}
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
					if (Double.valueOf(zxMyWalletAmount) < money) {
						output.setMsg("钱包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				case RED_PACKET_TYPE:
					String zxMyHbAmount = (String) item.get("redEnveBalance");
					if (Double.valueOf(zxMyHbAmount) < money) {
						output.setMsg("红包余额不足!");
						output.setCode("-1");
						return output;
					}
					break;
				case ADVERSING_TYPE:
					String zxMyAdvertisingAmount = (String) item.get("advertisingFee");
					if (Double.valueOf(zxMyAdvertisingAmount) < money) {
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
