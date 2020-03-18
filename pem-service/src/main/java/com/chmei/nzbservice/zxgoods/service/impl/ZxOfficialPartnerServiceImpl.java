package com.chmei.nzbservice.zxgoods.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxgoods.service.IZxGoodsTypeService;
import com.chmei.nzbservice.zxgoods.service.IZxOfficialPartnerService;
import org.springframework.stereotype.Service;

/**
 * 广告分红service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxOfficialPartnerService")
public class ZxOfficialPartnerServiceImpl extends BaseServiceImpl implements IZxOfficialPartnerService {


	/**
	 * 新增合作商
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveOfficialPartnerInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOfficialPartnerService");
		input.setMethod("saveOfficialPartnerInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 编辑合作商
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateOfficialPartnerInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOfficialPartnerService");
		input.setMethod("updateOfficialPartnerInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除合作商
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delOfficialPartnerInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOfficialPartnerService");
		input.setMethod("delOfficialPartnerInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询合作商详情
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryOfficialPartnerDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOfficialPartnerService");
		input.setMethod("queryOfficialPartnerDetail");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询合作商列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryOfficialPartnerList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxOfficialPartnerService");
		input.setMethod("queryOfficialPartnerList");
		getNzbDataService().execute(input, output);
	}
}
