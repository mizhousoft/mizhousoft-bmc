package com.mizhousoft.bmc.dictionary.service;

import com.mizhousoft.commons.json.JSONException;

/**
 * JSON字典服务
 *
 * @version
 */
public interface JSONDictService
{
	/**
	 * 设置值
	 * 
	 * @param key
	 * @param object
	 * @throws JSONException
	 */
	void putValue(String key, Object object) throws JSONException;

	/**
	 * 获取值
	 * 
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	<T> T getValue(String key, Class<T> clazz) throws JSONException;

	/**
	 * 删除数据
	 * 
	 * @param key
	 */
	void delete(String key);
}
