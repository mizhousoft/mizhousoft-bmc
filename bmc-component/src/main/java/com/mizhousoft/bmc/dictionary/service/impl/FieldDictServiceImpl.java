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
	private static final Logger LOG = LoggerFactory.getLogger(FieldDictServiceImpl.class);

	@Autowired
	private FieldDictMapper dictMapper;

	// 缓存 <domain-key, FieldDict>
	private Map<String, FieldDict> dictMap = new ConcurrentHashMap<>(100);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putValue(String domain, String key, Object value)
	{
		String mapKey = buildMapKey(domain, key);
		FieldDict fieldDict = dictMap.get(mapKey);
		if (null == fieldDict)
		{
			throw new IllegalArgumentException("Field dict not found, domain is " + domain + ", key is " + key);
		}

		String v = (value == null ? null : value.toString());

		// 防止更新数据库失败，改了内存数据
		FieldDict newDict = new FieldDict();
		newDict.setId(fieldDict.getId());
		newDict.setValue(v);
		newDict.setUtime(new Date());
		dictMapper.update(newDict);

		fieldDict.setValue(v);
		fieldDict.setUtime(new Date());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getIntValue(String domain, String key)
	{
		String mapKey = buildMapKey(domain, key);
		FieldDict fieldDict = dictMap.get(mapKey);
		if (null == fieldDict)
		{
			throw new IllegalArgumentException("Field dict not found, domain is " + domain + ", key is " + key);
		}

		return Integer.valueOf(fieldDict.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getLongValue(String domain, String key)
	{
		String mapKey = buildMapKey(domain, key);
		FieldDict fieldDict = dictMap.get(mapKey);
		if (null == fieldDict)
		{
			throw new IllegalArgumentException("Field dict not found, domain is " + domain + ", key is " + key);
		}

		return Long.valueOf(fieldDict.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getBooleanValue(String domain, String key)
	{
		String mapKey = buildMapKey(domain, key);
		FieldDict fieldDict = dictMap.get(mapKey);
		if (null == fieldDict)
		{
			throw new IllegalArgumentException("Field dict not found, domain is " + domain + ", key is " + key);
		}

		return Boolean.valueOf(fieldDict.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(String domain, String key)
	{
		String mapKey = buildMapKey(domain, key);
		FieldDict fieldDict = dictMap.get(mapKey);
		if (null == fieldDict)
		{
			throw new IllegalArgumentException("Field dict not found, domain is " + domain + ", key is " + key);
		}

		return fieldDict.getValue();
	}

	@PostConstruct
	public void init()
	{
		List<FieldDict> list = dictMapper.findAll();

		Map<String, FieldDict> dictMap = new ConcurrentHashMap<>(100);
		for (FieldDict item : list)
		{
			String mapKey = buildMapKey(item.getDomain(), item.getKey());
			dictMap.put(mapKey, item);
		}

		this.dictMap = dictMap;

		LOG.info("Load field dict successfully, size is {}.", dictMap.size());
	}

	private String buildMapKey(String domain, String key)
	{
		return domain + "-" + key;
	}
}
