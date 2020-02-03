package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IZxFriendService;
import org.springframework.stereotype.Service;

/**
 * 众享好友service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点31分
 *
 */
@Service("zxFriendService")
public class ZxFriendServiceImpl extends BaseServiceImpl implements IZxFriendService {

	/**
	 * 添加众享好友关系
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void addZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("addZxFriendInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 删除众享好友关系
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void deleteZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("deleteZxFriendInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 关注众享好友
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void notesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("notesZxFriendInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 取消关注众享好友
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void cancelNotesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("cancelNotesZxFriendInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询众享好友列表(带分组关系)
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxFriendList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("queryZxFriendList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享好友分组新增
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void saveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("saveZxFriendGroupingInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享好友分组编辑
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("updateZxFriendGroupingInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 众享好友分组删除
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void delZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("delZxFriendGroupingInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 移动我的好友到指定分组
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void moveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("moveZxFriendGroupingInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询众享好友分组列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxFriendGroupingList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("queryZxFriendGroupingList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询众享好友朋友圈列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxFriendCircleList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("queryZxFriendCircleList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 修改好友备注
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void updateZxFriendRemarkInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("updateZxFriendRemarkInfo");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询众享好友手机通讯录列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxFriendPhoneList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("queryZxFriendPhoneList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 查询众享好友手机通讯录列表
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryZxFriendDetail(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxFriendService");
		input.setMethod("queryZxFriendDetail");
		getNzbDataService().execute(input, output);
	}

}
