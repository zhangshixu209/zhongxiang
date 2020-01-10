package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 众享好友service接口
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点30分
 *
 */
public interface IZxFriendService {

	/**
	 * 添加众享好友关系
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void addZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除众享好友关系
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void deleteZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 关注众享好友
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void notesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 取消关注众享好友
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void cancelNotesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询众享好友列表(带分组关系)
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryZxFriendList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享好友分组新增
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享好友分组编辑
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 众享好友分组删除
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void delZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 移动我的好友到指定分组
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void moveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询众享好友分组列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryZxFriendGroupingList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询众享好友朋友圈列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryZxFriendCircleList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改好友备注
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateZxFriendRemarkInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询众享好友手机通讯录列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryZxFriendPhoneList(InputDTO input, OutputDTO output) throws NzbServiceException;

}
