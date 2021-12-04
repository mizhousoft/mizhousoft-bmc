package com.mizhousoft.bmc.dictionary.mapper;

import java.util.List;

import com.mizhousoft.bmc.dictionary.domain.FieldDict;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 字段字典持久层
 *
 * @version
 */
public interface FieldDictMapper extends CrudMapper<FieldDict, Integer>
{
	/**
	 * 根据domain查询
	 * 
	 * @param domain
	 * @return
	 */
	List<FieldDict> findByDomain(String domain);
}
