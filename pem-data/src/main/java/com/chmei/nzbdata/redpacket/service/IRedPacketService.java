package com.chmei.nzbdata.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享红包dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 14点58分
 *
 */
public interface IRedPacketService {
	/**
	 * 众享红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void saveRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 返回红包信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void updateImgRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 众享红包抢红包
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void robRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void viewRedPacketDone(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void viewRedPacketInfo(InputDTO input, OutputDTO output) throws NzbDataException;

}
