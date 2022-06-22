package com.mizhousoft.bmc.dictionary.mapper;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.dictionary.domain.JSONDict;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * JSON字典持久层
 *
 * @version
 */
public interface JSONDictMapper extends CrudMapper<JSONDict, Integer>
{
	/**
	 * 根据key查询
	 * 
	 * @param srvId
	 * @param key
	 * @return
	 */
	JSONDict findByKey(@Param("srvId")
	String srvId, @Param("key")
	String key);
}
