package com.mizhousoft.bmc.dictionary.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mizhousoft.bmc.dictionary.domain.FieldDict;
import com.mizhousoft.bmc.dictionary.mapper.FieldDictMapper;
import com.mizhousoft.bmc.dictionary.service.FieldDictService;

/**
 * 字段字典服务
 *
 * @version
 */
@Service
public class FieldDictServiceImpl implements FieldDictService
{
	@Autowired
	private FieldDictMapper dictMapper;

	// 缓存 <srvId-domain-key, FieldDict>，5分钟内有效
	private Cache<String, FieldDict> cache = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void putValue(String srvId, String domain, String key, Object value)
	{
		FieldDict fieldDict = getFieldDict(srvId, domain, key);

		String v = (value == null ? null : value.toString());

		if (StringUtils.equals(v, fieldDict.getValue()))
		{
			return;
		}

		// 防止更新数据库失败，改了内存数据
		FieldDict newDict = new FieldDict();
		newDict.setId(fieldDict.getId());
		newDict.setValue(v);
		newDict.setUtime(new Date());
		dictMapper.update(newDict);

		fieldDict.setValue(v);
		fieldDict.setUtime(new Date());

		String cacheKey = buildCacheKey(srvId, domain, key);
		cache.put(cacheKey, fieldDict);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getIntValue(String srvId, String domain, String key)
	{
		FieldDict fieldDict = getFieldDict(srvId, domain, key);

		return Integer.valueOf(fieldDict.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getLongValue(String srvId, String domain, String key)
	{
		FieldDict fieldDict = getFieldDict(srvId, domain, key);

		return Long.valueOf(fieldDict.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getBooleanValue(String srvId, String domain, String key)
	{
		FieldDict fieldDict = getFieldDict(srvId, domain, key);

		return Boolean.valueOf(fieldDict.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(String srvId, String domain, String key)
	{
		FieldDict fieldDict = getFieldDict(srvId, domain, key);

		return fieldDict.getValue();
	}

	private synchronized FieldDict getFieldDict(String srvId, String domain, String key)
	{
		String cacheKey = buildCacheKey(srvId, domain, key);

		FieldDict fieldDict = cache.getIfPresent(cacheKey);
		if (null == fieldDict)
		{
			List<FieldDict> list = dictMapper.findByDomain(srvId, domain);
			for (FieldDict item : list)
			{
				String k = buildCacheKey(srvId, item.getDomain(), item.getKey());
				cache.put(k, item);
			}

			fieldDict = cache.getIfPresent(cacheKey);
		}

		if (null == fieldDict)
		{
			throw new IllegalArgumentException("Field dict not found, service id is " + srvId + " domain is " + domain + ", key is " + key);
		}

		return fieldDict;
	}

	private String buildCacheKey(String srvId, String domain, String key)
	{
		return srvId + "-" + domain + "-" + key;
	}
}
