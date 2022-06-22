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
	 * @param srvId
	 * @param key
	 * @param object
	 * @throws JSONException
	 */
	void putValue(String srvId, String key, Object object) throws JSONException;

	/**
	 * 获取值
	 * 
	 * @param <T>
	 * @param srvId
	 * @param key
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	<T> T getValue(String srvId, String key, Class<T> clazz) throws JSONException;

	/**
	 * 删除数据
	 * 
	 * @param srvId
	 * @param key
	 */
	void delete(String srvId, String key);
}
