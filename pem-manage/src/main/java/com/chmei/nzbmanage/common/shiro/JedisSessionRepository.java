package com.chmei.nzbmanage.common.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.chmei.nzbcommon.redis.serializer.SpecialRedisSerializer;
import com.chmei.nzbmanage.configure.NzbManageProperties;

public class JedisSessionRepository {

	private static final Logger logger = LoggerFactory.getLogger(JedisSessionRepository.class);
	
	private static final String REDIS_SHIRO_SESSION = "SHIRO-ADMIN-SESSION:";
	
	@Autowired
	private NzbManageProperties nzbManageProperties;
	@Autowired
	private SpecialRedisSerializer specialRedisSerializer;
	@Autowired
	private RedisTemplate<Object, Object> specialRedisTemplate;
	
	public void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			throw new IllegalArgumentException("session is null");
		}
		try {
			String sessionKey = buildRedisSessionKey(session.getId());
			int timeout = nzbManageProperties.getSessionTimeout();
			specialRedisTemplate.opsForValue().set(sessionKey, session, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSession(Serializable id) {
		if (id == null) {
			throw new IllegalArgumentException("session id is null");
		}
		try {
			String sessionKey = buildRedisSessionKey(id);
			specialRedisTemplate.delete(sessionKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Session getSession(Serializable id) {
		if (id == null)
			throw new IllegalArgumentException("session id is null");
		Session session = null;
		try {
			String sessionKey = buildRedisSessionKey(id);
			byte[] bytes = (byte[])specialRedisTemplate.opsForValue().get(sessionKey);
			if (bytes != null && bytes.length > 0) {
				session = (Session)specialRedisSerializer.ofObject(bytes);
				int timeout = nzbManageProperties.getSessionTimeout();
				specialRedisTemplate.expire(sessionKey, timeout, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public Collection<Session> getAllSessions() {
		return null;
	}
	
	private String buildRedisSessionKey(Serializable sessionId) {
		return REDIS_SHIRO_SESSION + sessionId;
	}
}