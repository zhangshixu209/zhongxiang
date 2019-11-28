package com.chmei.nzbdata.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 众享好友关系dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月27日 09点39分
 *
 */
public interface IZxFriendService {

	/**
	 * 添加众享好友关系
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void addZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除众享好友关系
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void deleteZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 关注众享好友
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void notesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 取消关注众享好友
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void cancelNotesZxFriendInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询众享好友列表(带分组关系)
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryZxFriendList(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 众享好友分组新增
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void saveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 众享好友分组编辑
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void updateZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 众享好友分组删除
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void delZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 移动我的好友到指定分组
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void moveZxFriendGroupingInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询众享好友分组列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryZxFriendGroupingList(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 查询众享好友朋友圈列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryZxFriendCircleList(InputDTO input, OutputDTO output) throws NzbDataException;

}
