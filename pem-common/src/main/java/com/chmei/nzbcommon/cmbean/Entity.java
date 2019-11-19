package com.chmei.nzbcommon.cmbean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.cmutil.BeanUtil;

/**
 * 实体类
 */
public class Entity implements Serializable {
	private static final long serialVersionUID = -9214996402647704623L;
	private static final Logger logger = LoggerFactory.getLogger(Entity.class);

	public String toString() {
		Map<String, Object> map = BeanUtil.convertBean2Map(this);
		return map.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> T deepClone() {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);

			return (T) oi.readObject();
		} catch (Exception e) {
			logger.error("deepClone", "", e);
		}
		return null;
	}
}