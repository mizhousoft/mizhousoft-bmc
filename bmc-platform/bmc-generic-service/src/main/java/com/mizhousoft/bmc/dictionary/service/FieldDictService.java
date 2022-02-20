package com.mizhousoft.bmc.dictionary.service;

import java.util.List;

import com.mizhousoft.bmc.dictionary.domain.FieldDict;

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
	 * @param domain
	 * @param key
	 * @param value
	 */
	void putValue(String domain, String key, Object value);

	/**
	 * 获取值
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	int getIntValue(String domain, String key);

	/**
	 * 获取值
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	long getLongValue(String domain, String key);

	/**
	 * 获取值
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	boolean getBooleanValue(String domain, String key);

	/**
	 * 获取值
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	String getValue(String domain, String key);

	/**
	 * 根据domain查询
	 * 
	 * @param domain
	 * @return
	 */
	List<FieldDict> queryByDomain(String domain);
}
