package com.mizhousoft.bmc.dictionary.service;

/**
 * 字段字典服务
 *
 * @version
 */
public interface FieldDictService
{
	/**
	 * 设置值
	 * 
	 * @param srvId
	 * @param domain
	 * @param key
	 * @param value
	 */
	void putValue(String srvId, String domain, String key, Object value);

	/**
	 * 获取值
	 * 
	 * @param srvId
	 * @param domain
	 * @param key
	 * @return
	 */
	int getIntValue(String srvId, String domain, String key);

	/**
	 * 获取值
	 * 
	 * @param srvId
	 * @param domain
	 * @param key
	 * @return
	 */
	long getLongValue(String srvId, String domain, String key);

	/**
	 * 获取值
	 * 
	 * @param srvId
	 * @param domain
	 * @param key
	 * @return
	 */
	boolean getBooleanValue(String srvId, String domain, String key);

	/**
	 * 获取值
	 * 
	 * @param srvId
	 * @param domain
	 * @param key
	 * @return
	 */
	String getValue(String srvId, String domain, String key);
}
