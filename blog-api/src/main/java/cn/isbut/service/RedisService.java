package cn.isbut.service;

import cn.isbut.model.vo.BlogInfo;
import cn.isbut.model.vo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 */
public interface RedisService {
	/**
	 * 得到当前页缓存
	 * @param hash redis的key
	 * @param pageNum 页数  hashmap的key
	 * @return 当前页缓存
	 */
	PageResult<BlogInfo> getBlogInfoPageResultByHash(String hash, Integer pageNum);

	/**
	 * 保存到hashmap
	 * @param hash redis的key
	 * @param key hashmap的key
	 * @param value hashmap的value
	 */
	void saveKVToHash(String hash, Object key, Object value);

	/**
	 * 保存map到redis
	 * @param hash redis的key
	 * @param map map集合
	 */
	void saveMapToHash(String hash, Map map);

	/**
	 * 得到map
	 * @param hash redis的key
	 * @return map
	 */
	Map getMapByHash(String hash);

	/**
	 * 得到值
	 * @param hash redis的key
	 * @param key hashmap的key
	 * @return 值
	 */
	Object getValueByHashKey(String hash, Object key);

	/**
	 * 自增
	 * @param hash redis的key
	 * @param key hashmap的key
	 * @param increment 自增
	 */
	void incrementByHashKey(String hash, Object key, int increment);

	/**
	 * 删除
	 * @param hash redis的key
	 * @param key hashmap的key
	 */
	void deleteByHashKey(String hash, Object key);

	/**
	 * String结构中获取值
	 * @param key  redis的key
	 * @param <T> 泛型
	 * @return 结果
	 */
	<T> List<T> getListByValue(String key);

	/**
	 * 保存list到redis中的string
	 * @param key redis的key
	 * @param list list集合
	 * @param <T> 泛型
	 */
	<T> void saveListToValue(String key, List<T> list);

	/**
	 * 通过key得到map
	 * @param key redis中的key
	 * @param <T> 泛型
	 * @return map
	 */
	<T> Map<String, T> getMapByValue(String key);

	/**
	 * 保存map到String
	 * @param key redis中key
	 * @param map map集合
	 * @param <T> 泛型
	 */
	<T> void saveMapToValue(String key, Map<String, T> map);

	/**
	 * 得到特定对象类型
	 * @param key redis中key
	 * @param t 目标对象类型
	 * @param <T> 泛型
	 * @return 对象
	 */
	<T> T getObjectByValue(String key, Class t);

	/**
	 * 通过自增
	 * @param key redis中key
	 * @param increment 子增值
	 */
	void incrementByKey(String key, int increment);

	/**
	 * 保存对象到redis
	 * @param key redis中的key
	 * @param object 对象
	 */
	void saveObjectToValue(String key, Object object);

	/**
	 * 保存值到set中
	 * @param key redis中的key
	 * @param value 值
	 */
	void saveValueToSet(String key, Object value);

	/**
	 * 统计set中数量
	 * @param key redis中的key
	 * @return 数量
	 */
	int countBySet(String key);

	/**
	 * 删除set中的值
	 * @param key redis中的key
	 * @param value 值
	 */
	void deleteValueBySet(String key, Object value);

	/**
	 * 判断set中是否存在值
	 * @param key redis中的key
	 * @param value 值
	 * @return 布尔值
	 */
	boolean hasValueInSet(String key, Object value);

	/**
	 * 删除定义的cache
	 * @param key redis中的key
	 */
	void deleteCacheByKey(String key);

	/**
	 * 判断是否存在key
	 * @param key redis中的key
	 * @return 布尔值
	 */
	boolean hasKey(String key);

	/**
	 * 设置key的过期时间
	 * @param key redis中的key
	 * @param time 过期时间
	 */
	void expire(String key, long time);
}
