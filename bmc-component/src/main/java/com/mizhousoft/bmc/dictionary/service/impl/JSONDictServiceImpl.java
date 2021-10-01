package com.mizhousoft.bmc.dictionary.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.dictionary.domain.JSONDict;
import com.mizhousoft.bmc.dictionary.mapper.JSONDictMapper;
import com.mizhousoft.bmc.dictionary.service.JSONDictService;
import com.mizhousoft.commons.json.JSONException;
import com.mizhousoft.commons.json.JSONUtils;

/**
 * JSON字典服务
 *
 * @version
 */
@Service
public class JSONDictServiceImpl implements JSONDictService
{
	private static final Logger LOG = LoggerFactory.getLogger(JSONDictServiceImpl.class);

	@Autowired
	private JSONDictMapper dictMapper;

	// 缓存 <key, JSONDict>
	private Map<String, JSONDict> dictMap = new ConcurrentHashMap<>(100);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void putValue(String key, Object object) throws JSONException
	{
		String value = null;
		if (null != object)
		{
			value = JSONUtils.toJSONString(object);
		}

		JSONDict jsonDict = dictMap.get(key);
		if (null == jsonDict)
		{
			JSONDict newDict = new JSONDict();
			newDict.setKey(key);
			newDict.setValue(value);
			newDict.setUtime(new Date());
			newDict.setCtime(newDict.getUtime());
			dictMapper.save(newDict);

			dictMap.put(key, newDict);
		}
		else
		{
			// 防止更新数据库失败，改了内存数据
			JSONDict newDict = new JSONDict();
			newDict.setId(jsonDict.getId());
			newDict.setValue(value);
			newDict.setUtime(new Date());
			dictMapper.update(newDict);

			jsonDict.setValue(value);
			jsonDict.setUtime(new Date());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getValue(String key, Class<T> clazz) throws JSONException
	{
		JSONDict jsonDict = dictMap.get(key);
		if (null != jsonDict)
		{
			String value = jsonDict.getValue();
			if (null != value)
			{
				return JSONUtils.parse(value, clazz);
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(String key)
	{
		JSONDict jsonDict = dictMap.remove(key);
		if (null != jsonDict)
		{
			dictMapper.delete(jsonDict.getId());
		}
	}

	@PostConstruct
	public void init()
	{
		List<JSONDict> list = dictMapper.findAll();

		Map<String, JSONDict> dictMap = new ConcurrentHashMap<>(100);
		for (JSONDict item : list)
		{
			dictMap.put(item.getKey(), item);
		}

		this.dictMap = dictMap;

		LOG.info("Load json dict successfully, size is {}.", dictMap.size());
	}
}
