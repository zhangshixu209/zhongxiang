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

}
