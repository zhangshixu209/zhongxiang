package com.chmei.nzbmanage.application.listener;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbcommon.redis.serializer.JdkAndStringValueRedisSerializer;
import com.chmei.nzbcommon.util.RSAUtilSQL;

/**
 * 容器刷新之后回调该事件
 * @author lixinjie
 * @since 2018-05-08
 */
@Component
public class ApplicationContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

	  /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ApplicationContextRefreshed.class);
    
	@Autowired
	private ICacheService cacheService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//重新设置key/value的序列化器
		RedisTemplate<Object, Object> redisTemplate = event.getApplicationContext().getBean("redisTemplate", RedisTemplate.class);
		JdkAndStringValueRedisSerializer jdkAndStringValueRedisSerializer = new JdkAndStringValueRedisSerializer();
		redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
		redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
		redisTemplate.setValueSerializer(jdkAndStringValueRedisSerializer);
		redisTemplate.setHashValueSerializer(jdkAndStringValueRedisSerializer);
		
//		//ico初始化之后加载RSA非对称加解密钥匙
//		Map<String, String> sqlKey = RSAUtilSQL.getPrivateKey();
//		cacheService.setString(RSAUtilSQL.SQL_PUBLIC_KEY, sqlKey.get(RSAUtilSQL.SQL_PUBLIC_KEY));
//		cacheService.setString(RSAUtilSQL.SQL_PRIVATE_Key, sqlKey.get(RSAUtilSQL.SQL_PRIVATE_Key));
//		String publicKey = cacheService.getString(RSAUtilSQL.SQL_PUBLIC_KEY);
//		String privateKey = cacheService.getString(RSAUtilSQL.SQL_PRIVATE_Key);
//		LOGGER.info("SQL-RSA非对称密钥对初始化放入redis:SQL_PUBLIC_KEY:"+publicKey);
//		LOGGER.info("--------------------------------------------------");
//		LOGGER.info("SQL-RSA非对称密钥对初始化放入redis:SQL_PRIVATE_KEY:"+privateKey);
	}

}
