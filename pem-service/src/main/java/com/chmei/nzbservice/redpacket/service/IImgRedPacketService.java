package com.chmei.nzbservice.redpacket.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 图文红包service接口
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点42分
 *
 */
public interface IImgRedPacketService {
	/**
	 * 图文红包发布新增
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveImgRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 返回红包信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateRedPacketInfoById(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 图文红包抢红包
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void robRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void viewRedPacketDone(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void viewRedPacketInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 根据红包ID查询红包详细信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryRedPacketDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 根据红包ID和用户ID查看此用户是否抢当前这个红包
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void checkUserIsRobRedPacket(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 当前用户是否领取该红包 参数为 memberAccount（当前用户账号）,redPackageIds（红包Id） 返回值
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void selectListStockByRedPacketId(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询所有红包根据用户权限（性别，年龄，地区）
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryAllRedPacketByAuth(InputDTO input, OutputDTO output) throws NzbServiceException;
}
