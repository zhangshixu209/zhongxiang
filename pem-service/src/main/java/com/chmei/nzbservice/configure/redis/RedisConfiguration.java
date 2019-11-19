package com.chmei.nzbservice.configure.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.chmei.nzbcommon.redis.ICacheService;
import com.chmei.nzbcommon.redis.RedisTemplateCacheServiceImpl;
import com.chmei.nzbcommon.redis.serializer.SpecialRedisSerializer;
import com.chmei.nzbcommon.redis.serializer.SpecialRedisSerializerForLegacy;

/**
 * @author lixinjie
 * @since 2018-11-28
 */
@Configuration
public class RedisConfiguration {

	@Bean
	public SpecialRedisSerializer specialRedisSerializer() {
		return new SpecialRedisSerializerForLegacy();
	}
	
	/**
	 * 注册一个特殊的RedisTemplate，用户缓存接口实现类的底层
	 */
	@Bean
	public RedisTemplate<Object, Object> specialRedisTemplate(RedisConnectionFactory redisConnectionFactory,
			SpecialRedisSerializer specialRedisSerializer) {
		RedisTemplate<Object, Object> specialRedisTemplate = new RedisTemplate<>();
		specialRedisTemplate.setConnectionFactory(redisConnectionFactory);
		specialRedisTemplate.setKeySerializer(specialRedisSerializer);
		specialRedisTemplate.setValueSerializer(specialRedisSerializer);
		specialRedisTemplate.setHashKeySerializer(specialRedisSerializer);
		specialRedisTemplate.setHashValueSerializer(specialRedisSerializer);
		return specialRedisTemplate;
	}
	
	@Bean
	public ICacheService cacheService() {
		return new RedisTemplateCacheServiceImpl();
	}
}
