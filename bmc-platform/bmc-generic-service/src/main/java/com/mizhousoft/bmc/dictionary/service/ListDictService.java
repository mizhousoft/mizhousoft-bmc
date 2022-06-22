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
	 * @param srvId
	 * @param domain
	 * @param key
	 * @return
	 */
	String getName(String srvId, String domain, String key);

	/**
	 * 获取Field
	 * 
	 * @param srvId
	 * @param domain
	 * @param key
	 * @return
	 */
	Field getField(String srvId, String domain, String key);

	/**
	 * 根据domain查询
	 * 
	 * @param srvId
	 * @param domain
	 * @return
	 */
	List<Field> queryByDomain(String srvId, String domain);
}
