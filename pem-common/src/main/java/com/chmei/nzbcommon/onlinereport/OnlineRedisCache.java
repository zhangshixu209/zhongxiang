package com.chmei.nzbcommon.onlinereport;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbcommon.util.StringUtil;

/**
 * ClassName: OnlineRedisCache 
 * Function: 在线编辑报表保存检验数据是否为自己锁定 
 * date: 2019年9月4日 下午5:44:26
 * @author 翟超锋
 * @since JDK 1.7
 */
public class OnlineRedisCache {
	
	/**在线修改报表人工字段锁定使用*/
	public static final String REDIS_CACHE_USERID = "report:online:userid:"; 
	/**在线修改报表人工字段锁定时长*/
	public static final int REDIS_CACHE_TIMES = 60*8; //10分钟

	/**log*/
	private static final Logger LOGGER = LoggerFactory.getLogger(OnlineRedisCache.class);
	
	
	static Lock lock = new ReentrantLock();//添加同步锁
	
	/**redis缓存*/
	@Autowired
    private ICacheService cacheService;
	
	
	/**
	 * 连接远程数据库查询是否已经保存
	 * @param conn   数据库连接信息
	 * @param id     需要保存的数据id
	 * @return  0可以保存，1数据库已经保存，2参数id不存在
	 */
	public int connDBCheck(Map<String, Object> conn, String id, String fileName, String tableName){
		if(StringUtils.isEmpty(id)){
			return 2;
		}
		//连接数据库在做改造
		String sql = "select "+ fileName +" from " + tableName + "where id = " + id;
		//连接远程数据库查询
		Map<String, Object> connRemotoDB = ConnRemoteDataSearchUtils.connRemotoDB(conn, sql, null, null, null,
				                                                                  null,null,fileName, 1, 10, 0,null);
		if(connRemotoDB != null && connRemotoDB.size() == 0){ // 表示存在可以修改
			return 0;
		}else{
			return 1;
		}
	}
	
	/**
	 * 操作报表的时候把需要的数据set到redis中
	 * 批注：只要存储到redis之后就需要等待失效或删除
	 * 业务场景：
	 *   1、当第一人先查询了放入之后不允许覆盖之后，只能自动失效或者自己删除，或者自己覆盖
	 *   2、当两人同时搜索的数据有交叉的情况，需要锁定没有交叉的数据并且谁先到的锁定多余数据
	 *   3、按条保存，只要没有存储就可以做保存
	 * @param key 用户id|姓名
	 * @param listData 存储数据id
	 */
	public void setRedisCache(String key,List<Map<String, Object>> listData) {
		LOGGER.info("用户{},需要存储数据:{}",key,listData); 
		if(listData == null || listData.size() == 0){
			return;
		}
		String dataIds = "";
		try{
			lock.lock();
			for (Map<String, Object> map : listData) {
                String modfTime = (String) map.get("modf_time"); // 修改时间
                String dataId = (String) map.get("id"); // 远程数据表ID
                if (StringUtil.isNotEmpty(modfTime)) { // 如果不为空，不存入redis
                    continue;
                } else {
                    if(!cacheService.isExist(REDIS_CACHE_USERID+dataId)){//不存在,做保存至缓存
                        cacheService.setexObject(REDIS_CACHE_USERID+dataId, key, REDIS_CACHE_TIMES);
                        dataIds += dataId+",";
                    }
                }
            }
		}catch(Exception e){
			e.printStackTrace();
			lock.unlock();
		}finally{
			lock.unlock();
		}
		LOGGER.info("用户id{},成功存储缓存锁定数据:{}",key,dataIds);
	}
	
	/**
	 * 使用用户id获取缓存数据并返回
	 * @param key
	 * @return 0:表示可以修改   1：不允许修改
	 */
	public String getRedisCache(String key, String dataId, Map<String,Object> conn, 
	        String fileName, String tableName) {
		String keys = cacheService.getString(REDIS_CACHE_USERID+dataId);
		String keyId = keys.substring(0, keys.lastIndexOf(",")); // 用户ID
		String keyName = keys.substring(keys.lastIndexOf(",") + 1); // 用户姓名
		if(keyId == null){
			//不存在分为两种情况 1、别人锁定了，但是自动释放了，当前人可以修改 
			//这种情况概率很低)2、别人修改好了，远程数据库也是修改好的结果，那就不允许修改了。
			int connDBCheck = connDBCheck(conn, keyId, fileName, tableName);
			if(connDBCheck == 0){
				return "0";
			}else{
				return "远程数据库已经修改完成，不允许更新修改"; // 远程数据库已经修改完成，不允许更新修改
			}
		}else{
			if(keyId.equals(key)){//属于自己锁定
				return "0";
			}else{//不属于自己锁定的返回锁定人信息
				return "用户："+keyName+"，正在修改此条数据！";
			}
		}
	}

	/**
	 * 当正常处理业务处理结束之后需要删除缓存中数据使用用户id
	 * @param key 用户id
	 * 默认删除成功
	 */
	public void delRedisCache(String key, String dataId) {
		cacheService.del(REDIS_CACHE_USERID+dataId);
		LOGGER.info("用户id{},成功删除缓存锁定数据{}",key,dataId);
	}

}
