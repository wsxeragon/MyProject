package cn.inphase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.inphase.tool.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {

	@Autowired
	private JedisPool jedisPool;

	/** 缓存生存时间 */
	private final static int EXPIRE = 60000;

	/**
	 * 从jedis连接池中获取获取jedis对象
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			if (jedis != null)
				jedis.close();
			throw e;
		}
		return jedis;
	}

	/**
	 * 回收jedis
	 */
	public void returnJedis(Jedis jedis) {
		if (jedis != null)
			jedisPool.returnResource(jedis);
	}

	public void set(String key, String value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key, value);
		jedis.expire(key, EXPIRE);
		returnJedis(jedis);
	}

	public void set(String key, String value, int expire) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key, value);
		if (expire <= 0) {
			expire = EXPIRE;
		}
		jedis.expire(key, expire);
		returnJedis(jedis);
	}

	public void set(String key, Object value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key.getBytes(), SerializeUtil.serialize(value));
		jedis.expire(key.getBytes(), EXPIRE);
		returnJedis(jedis);
	}

	public void set(String key, Object value, int expire) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key.getBytes(), SerializeUtil.serialize(value));
		if (expire <= 0) {
			expire = EXPIRE;
		}
		jedis.expire(key.getBytes(), EXPIRE);
		returnJedis(jedis);
	}

	public void set(String key, int value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public void set(String key, int value, int expire) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value), expire);
	}

	public void set(String key, long value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public void set(String key, long value, int expire) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value), expire);
	}

	public void set(String key, float value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public void set(String key, float value, int expire) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value), expire);
	}

	public void set(String key, double value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public void set(String key, double value, int expire) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value), expire);
	}

	public Float getFloat(String key) {
		if (isBlank(key))
			return null;
		return Float.valueOf(getStr(key));
	}

	public Double getDouble(String key) {
		if (isBlank(key))
			return null;
		return Double.valueOf(getStr(key));
	}

	public Long getLong(String key) {
		if (isBlank(key))
			return null;
		return Long.valueOf(getStr(key));
	}

	public Integer getInt(String key) {
		if (isBlank(key))
			return null;
		return Integer.valueOf(getStr(key));
	}

	public String getStr(String key) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		String value = jedis.get(key);
		returnJedis(jedis);
		return value;
	}

	public Object getObj(String key) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		byte[] bits = jedis.get(key.getBytes());
		Object obj = SerializeUtil.unserialize(bits);
		returnJedis(jedis);
		return obj;
	}

	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}

}
