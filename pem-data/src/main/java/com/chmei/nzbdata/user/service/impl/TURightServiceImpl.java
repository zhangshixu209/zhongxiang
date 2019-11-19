package com.chmei.nzbdata.user.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.ITURightService;
import com.chmei.nzbdata.util.RedisKey;

@Transactional
@Service("tURightService")
@SuppressWarnings({"rawtypes","unchecked"})
public class TURightServiceImpl extends BaseServiceImpl implements ITURightService {
	/**
	 * log
	 */
	private  static final Logger logger = LoggerFactory.getLogger(TURightServiceImpl.class);
	@Override
	public void queryObject(InputDTO input, OutputDTO output) throws NzbDataException{
		Map queryForObject = getBaseDao().queryForObject("TURightMapper.queryObject", input.getParams(),Map.class);
		output.setItem(queryForObject);
	}
	
	@Override
	public void queryRightByUrlAddr(InputDTO input, OutputDTO output) throws NzbDataException {
		Map queryForObject = getBaseDao().queryForObject("TURightMapper.queryRightByUrlAddr", input.getParams(),Map.class);
		output.setItem(queryForObject);
	}
	
	/**
	 * 获取所有权限
	 */
	private List<Map<String,Object>> queryAll(InputDTO input, OutputDTO output) throws NzbDataException{
		return getBaseDao().queryForList(
				"TURightMapper.queryAll", input.getParams());
	}
	@Override
	public void save(InputDTO input, OutputDTO output) throws NzbDataException{
		Map<String, Object> map = input.getParams();
		long id = getSequence();
		map.put("id", id);
		getCrtInfo(map,input);
		int insert = getBaseDao().insert("TURightMapper.save", map);
		if(insert>0) {
			logger.info("新增权限，权限Id=[{}]", id);
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
			//删除权限缓存
			deleteCache();
		}
		output.setTotal(insert);
		
	}


	@Override
	public void delete(InputDTO input, OutputDTO output) throws NzbDataException{
		//获取所有节点
		List<Map<String, Object>> allRightList = queryAll(input, output);
		//获取该节点下所有子节点
		String ids  = getRightChildsByParentId(allRightList, input.getParams().get("id").toString(),input.getParams().get("id").toString());
		input.getParams().put("ids", ids.split(","));
		int delete = getBaseDao().delete("TURightMapper.deleteBatch", input.getParams());
		logger.info("删除权限，权限Id=[{}],操作人Id=[{}]", input.getParams().get("id"),input.getParams().get("modfUserId"));
		if(delete>0) {
			getBaseDao().delete("TURoleRightMapper.deleteByRightIds", input.getParams());
			logger.info("删除角色权限，权限Id=[{}],操作人Id=[{}]", input.getParams().get("id"),input.getParams().get("modfUserId"));
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
		}
		//删除权限缓存
		deleteCache();
		output.setTotal(delete);
	}
	
	@Override
	public void update(InputDTO input, OutputDTO output) throws NzbDataException {
		//获取该节点
		Map<String, Object> map = input.getParams();
		getModfInfo(map,input);
		int i = getBaseDao().update("TURightMapper.update", map);
		if(i>0) {
			logger.info("修改权限，权限Id=[{}],操作人Id=[{}]", input.getParams().get("id"),map.get("modfUserId"));
			addOperLog(input, JSONObject.toJSONString(input.getParams()));
			//删除权限缓存
			deleteCache();
		}
		output.setTotal(i);
	}

	/**
	 * 权限菜单加载
	 */
	@Override
	public void queryRightTree(InputDTO input, OutputDTO output) throws NzbDataException {
		//获取所有权限
		List<Map<String, Object>> allRightList = getBaseDao().queryForList(
				"TURightMapper.queryRightAndHasRole", input.getParams());
		if(allRightList.size()>0) {//权限查询过程添加{菜单}
			List<Map<String,Object>> dealAddMenu = dealAddMenuOrButton(allRightList);
			output.setItems(dealAddMenu);
		}
	}
	
	/**
	 * 封装一个添加菜单和按钮
	 * @param allRightList
	 * @return
	 */
	private List<Map<String, Object>>  dealAddMenuOrButton(List<Map<String, Object>> allRightList){
		if(allRightList == null || allRightList.size() == 0){
			return allRightList;
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < allRightList.size(); i++) {
			Map<String, Object> map = allRightList.get(i);
			Object object = map.get("urlAddr");
			if(object != null && object.toString().length() > 0){
				map.put("name", map.get("name")+"[菜单]");
			}
			Object extend3 = map.get("extend3");
			if(extend3 != null && extend3.toString().length() > 0){
				map.put("name", map.get("name")+"[按钮]");
			}
			list.add(map);
		}
		return list;
	}
	
	//获取母节点下所有的子节点Id
	private String getRightChildsByParentId(List<Map<String, Object>> allRightList, String parentId,String ids) {  
		Iterator<Map<String, Object>> it = allRightList.iterator();
		while (it.hasNext()) {
			Map<String, Object> right = it.next();
			if(parentId.equals(right.get("parentId").toString())) {
				ids = ids +","+ right.get("id");
				ids = getRightChildsByParentId(allRightList, right.get("id").toString(),ids);
		   }
		}
        return ids;  
	}

	@Override
	public void queryAllRight(InputDTO input, OutputDTO output) throws NzbDataException {
		List<Map<String, Object>> list = getBaseDao().queryForList(
				"TURightMapper.queryList", input.getParams());
		output.setItems(list);
	} 
	
	/**
	 * 刷新缓存
	 * @param sysTypeCd
	 */
	private void deleteCache() {
		String roleRightKey = RedisKey.REDIS_SYS_MANAGE_ROLE_RIGHT_HASH;
		String rightKey = RedisKey.REDIS_SYS_MANAGE_RIGHTS_HASH;
		this.getCacheService().del(rightKey);
		this.getCacheService().del(roleRightKey);
		logger.info("删除权限缓存redis");
	}
	/**
     * 获取部门树   
     * @param input 入参
     * @param output 返回结果
     * @throws NzbDataException 自定义异常
     */
    @Override
    public void queryUserDepartment(InputDTO input, OutputDTO output) throws NzbDataException {
        List<Map<String, Object>> allRightList = getBaseDao().queryForList(
                "TURightMapper.queryUserDepartment", input.getParams());
        if (allRightList.size() > 0) { //权限查询过程添加{菜单}
            List<Map<String, Object>> dealAddMenu = dealAddMenuOrButton(allRightList);
            output.setItems(dealAddMenu);
        }
    }
	
}
