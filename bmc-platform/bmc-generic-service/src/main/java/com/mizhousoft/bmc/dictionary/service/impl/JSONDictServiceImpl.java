package com.mizhousoft.bmc.dictionary.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
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
	@Autowired
	private JSONDictMapper dictMapper;

	// 缓存 <key, JSONDict>，10分钟内有效
	private Cache<String, JSONDict> cache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putValue(String key, Object object) throws JSONException
	{
		String value = null;
		if (null != object)
		{
			value = JSONUtils.toJSONString(object);
		}

		synchronized (this)
		{
			JSONDict jsonDict = getJSONDict(key);
			if (null == jsonDict)
			{
				JSONDict newDict = new JSONDict();
				newDict.setKey(key);
				newDict.setValue(value);
				newDict.setUtime(new Date());
				newDict.setCtime(newDict.getUtime());
				dictMapper.save(newDict);

				cache.put(key, newDict);
			}
			else
			{
				// 防止更新数据库失败，改了内存数据
				JSONDict newDict = new JSONDict();
				newDict.setId(jsonDict.getId());
				newDict.setKey(key);
				newDict.setValue(value);
				newDict.setUtime(new Date());
				newDict.setCtime(jsonDict.getCtime());
				dictMapper.update(newDict);

				jsonDict.setValue(value);
				jsonDict.setUtime(new Date());

				cache.put(key, jsonDict);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getValue(String key, Class<T> clazz) throws JSONException
	{
		JSONDict jsonDict = getJSONDict(key);
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
		JSONDict jsonDict = cache.getIfPresent(key);
		if (null != jsonDict)
		{
			cache.invalidate(key);

			dictMapper.delete(jsonDict.getId());
		}
	}

	private synchronized JSONDict getJSONDict(String key)
	{
		JSONDict jsonDict = cache.getIfPresent(key);
		if (null == jsonDict)
		{
			jsonDict = dictMapper.findByKey(key);
			if (null != jsonDict)
			{
				cache.put(key, jsonDict);
			}
		}

		return jsonDict;
	}
}
