package com.mizhousoft.bmc.dictionary.service.impl;

import java.time.LocalDateTime;
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

	// 缓存 <srvId-key, JSONDict>，5分钟内有效
	private Cache<String, JSONDict> cache = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putValue(String srvId, String key, Object object) throws JSONException
	{
		String value = null;
		if (null != object)
		{
			value = JSONUtils.toJSONString(object);
		}

		synchronized (this)
		{
			String cacheKey = buildCacheKey(srvId, key);

			JSONDict jsonDict = getJSONDict(srvId, key);
			if (null == jsonDict)
			{
				JSONDict newDict = new JSONDict();
				newDict.setSrvId(srvId);
				newDict.setKey(key);
				newDict.setValue(value);
				newDict.setUtime(LocalDateTime.now());
				newDict.setCtime(newDict.getUtime());
				dictMapper.save(newDict);

				cache.put(cacheKey, newDict);
			}
			else
			{
				// 防止更新数据库失败，改了内存数据
				JSONDict newDict = new JSONDict();
				newDict.setId(jsonDict.getId());
				newDict.setKey(key);
				newDict.setValue(value);
				newDict.setUtime(LocalDateTime.now());
				newDict.setCtime(jsonDict.getCtime());
				dictMapper.update(newDict);

				jsonDict.setValue(value);
				jsonDict.setUtime(LocalDateTime.now());

				cache.put(cacheKey, jsonDict);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getValue(String srvId, String key, Class<T> clazz) throws JSONException
	{
		JSONDict jsonDict = getJSONDict(srvId, key);
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
	public void delete(String srvId, String key)
	{
		String cacheKey = buildCacheKey(srvId, key);

		JSONDict jsonDict = cache.getIfPresent(cacheKey);
		if (null != jsonDict)
		{
			cache.invalidate(cacheKey);

			dictMapper.delete(jsonDict.getId());
		}
	}

	private synchronized JSONDict getJSONDict(String srvId, String key)
	{
		String cacheKey = buildCacheKey(srvId, key);

		JSONDict jsonDict = cache.getIfPresent(cacheKey);
		if (null == jsonDict)
		{
			jsonDict = dictMapper.findByKey(srvId, key);
			if (null != jsonDict)
			{
				cache.put(cacheKey, jsonDict);
			}
		}

		return jsonDict;
	}

	private String buildCacheKey(String srvId, String key)
	{
		return srvId + "-" + key;
	}
}
