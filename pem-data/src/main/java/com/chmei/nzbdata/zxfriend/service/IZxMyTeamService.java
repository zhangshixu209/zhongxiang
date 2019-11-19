package com.chmei.nzbdata.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

import java.util.Map;

/**
 * 我的团队dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月13日 10点21分
 *
 */
public interface IZxMyTeamService {

	/**
	 * 根据当前登录人ID 查询自己手下的手下的直推有效人,如果过了30天,直推的还没有直推有效人,则回归分红周期到10天
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	int checkedDay(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 根据当前登录人ID 查询当前团队有多少人
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	int countMyTeam(InputDTO input, OutputDTO output) throws NzbDataException;


	/**
	 * 根据登录用户ID, 查询自己手下的团队信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void queryTheLower(InputDTO input, OutputDTO output) throws NzbDataException;
}
