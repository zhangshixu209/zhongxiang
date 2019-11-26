package com.chmei.nzbservice.redpacket.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.redpacket.service.IImgRedPacketService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 图文红包service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点44分
 *
 */
@Service("imgRedPacketService")
public class ImgRedPacketServiceImpl extends BaseServiceImpl implements IImgRedPacketService {
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(ImgRedPacketServiceImpl.class);

	/**
	 * 图文红包发新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveImgRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		LOGGER.info("图文红包新增...imgRedPacketService.saveImgRedPacketInfo....");
		input.setService("imgRedPacketService");
		input.setMethod("saveImgRedPacketInfo");
		getNzbDataService().execute(input, output);
	}
	/**
	 * 返回红包信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("updateRedPacketInfoById");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 图文红包抢红包
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void robRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("robRedPacketInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void viewRedPacketDone(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("viewRedPacketDone");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void viewRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("viewRedPacketInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 根据红包ID查询红包详细信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryRedPacketDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("queryRedPacketDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 根据红包ID和用户ID查看此用户是否抢当前这个红包
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void checkUserIsRobRedPacket(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("checkUserIsRobRedPacket");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 当前用户是否领取该红包 参数为 memberAccount（当前用户账号）,redPackageIds（红包Id） 返回值
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void selectListStockByRedPacketId(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("selectListStockByRedPacketId");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询所有红包根据用户权限（性别，年龄，地区）
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryAllRedPacketByAuth(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("imgRedPacketService");
		input.setMethod("queryAllRedPacketByAuth");
		getNzbDataService().execute(input, output);
	}

}
