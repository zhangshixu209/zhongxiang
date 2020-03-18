package com.chmei.nzbdata.zxgoods.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享合作商dao接口
 * 
 * @author zhangshixu
 * @since 2020年3月17日 10点42分
 *
 */
public interface IZxOfficialPartnerService {

	/**
	 * 新增合作商
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void saveOfficialPartnerInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 编辑合作商
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void updateOfficialPartnerInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除合作商
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void delOfficialPartnerInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询合作商详情
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryOfficialPartnerDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询合作商列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void queryOfficialPartnerList(InputDTO input, OutputDTO output) throws NzbDataException;
}
