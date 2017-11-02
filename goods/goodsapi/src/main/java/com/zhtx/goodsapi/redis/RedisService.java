package com.zhtx.goodsapi.redis;
 
import java.util.Set;
import java.util.concurrent.TimeUnit; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.redis.core.RedisTemplate; 
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service; 
@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private ValueOperations<String, Object> getOperation() {
		return redisTemplate.opsForValue();
	}

	/**
	 * redis存储，永久有效,当前RedisService为了和.net程序互通，value只支持string
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void set(String key, String value) {
		ValueOperations<String, Object> operation = getOperation();
		operation.set(suffxKey(key), value);
	}

	/**
	 * redis存储方法,当前RedisService为了和.net程序互通，value只支持string
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @param timeout
	 *            过期时间，单位默认为秒，如果需要更改，些调用重载方法{@link set(String key, Object value,
	 *            long timeout, TimeUnit timeUnit)}
	 */
	public void set(String key, String value, long timeout) {
		set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * redis存储,当前RedisService为了和.net程序互通，value只支持string
	 * 
	 * @param key
	 *            redis key
	 * @param value
	 *            redis value
	 * @param timeout
	 *            过期时间
	 * @param timeUnit
	 *            过期时间单位
	 */
	public void set(String key, String value, long timeout, TimeUnit timeUnit) {
		ValueOperations<String, Object> operation = getOperation();
		operation.set(suffxKey(key), value, timeout, timeUnit);
	}

	/**
	 * 获取Redis值， isSuffxKey=false时不加redis版本及前缀,当前RedisService为了和.net程序互通，value只支持string
	 * */
	public String get(String key,Boolean isSuffxKey) {
		ValueOperations<String, Object> operation = getOperation();
		String finalKey=isSuffxKey?suffxKey(key):key;
		Object object = operation.get(finalKey);
		if (object!=null) {
			return object.toString();
		}
		return null;
	}

	/**
	 * 获取Redis值， isSuffxKey=true+版本及前缀,当前RedisService为了和.net程序互通，value只支持string
	 * */
	public String get(String key) {
		return get(key,true);
	}
	
	public void remove(String keyPattern,Boolean isSuffxKey) {
		String finalKey=isSuffxKey?suffxKey(keyPattern):keyPattern;
		Set<String> removeKeys = redisTemplate.keys(finalKey);
		redisTemplate.delete(removeKeys);
	}
	public void remove(String keyPattern) {
		Set<String> removeKeys = redisTemplate.keys(suffxKey(keyPattern));
		redisTemplate.delete(removeKeys);
	}
	/**
	 * 获取所有相似key
	 * 
	 * @param keyPattern
	 *            模糊键
	 * @author haichao
	 * @date 2015年9月29日 13:10:05
	 * */
	public Set<String> keys(String keyPattern){
		return redisTemplate.keys("*"+keyPattern+"*");
	}

	/**
	 * 给key添加后缀或前缀
	 * 
	 * @author hailongzhao
	 * @date 20150902
	 * @param orginKey
	 * @return
	 */
	private String suffxKey(String orginKey) {
		//return "java_tools_" + PropertyUtils.getProperty("GlobalVersion") + "_"+ orginKey;
		return orginKey;
	}
}
