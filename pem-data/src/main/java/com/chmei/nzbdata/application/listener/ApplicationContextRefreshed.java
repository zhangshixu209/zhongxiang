package com.chmei.nzbdata.application.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.chmei.nzbcommon.redis.serializer.JdkAndStringValueRedisSerializer;

/**
 * 容器刷新之后回调该事件
 * @author lixinjie
 * @since 2018-05-08
 */
@Component
public class ApplicationContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

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
	}

}
