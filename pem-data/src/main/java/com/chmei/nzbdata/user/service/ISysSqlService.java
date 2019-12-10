package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 数据库备份/还原service接口
 * 
 * @author zhangshixu
 * @since 2019年12月10日 09点16分
 *
 */
public interface ISysSqlService {

	/**
	 * 查询数据库备份/还原列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryMySqlList(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 备份数据库信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void backupsDBInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 还原数据库信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void reductionDBInfo(InputDTO input, OutputDTO output) throws NzbDataException;

}
