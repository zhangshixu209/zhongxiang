package com.chmei.nzbdata.recharge.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 轮播图dao接口
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点23分
 *
 */
public interface IRechargeRecordService {

	/**
	 * 初始化加载充值记录查询列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryRechargeRecordList(InputDTO input, OutputDTO output) throws NzbDataException;
}
