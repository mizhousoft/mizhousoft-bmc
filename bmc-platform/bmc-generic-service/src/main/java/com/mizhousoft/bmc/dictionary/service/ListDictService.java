package com.mizhousoft.bmc.dictionary.service;

import java.util.List;

import com.mizhousoft.bmc.dictionary.domain.Field;

/**
 * 列表字典服务
 *
 * @version
 */
public interface ListDictService
{
	/**
	 * 获取名称
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	String getName(String domain, String key);

	/**
	 * 获取Field
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	Field getField(String domain, String key);

	/**
	 * 根据domain查询
	 * 
	 * @param domain
	 * @return
	 */
	List<Field> queryByDomain(String domain);
}
