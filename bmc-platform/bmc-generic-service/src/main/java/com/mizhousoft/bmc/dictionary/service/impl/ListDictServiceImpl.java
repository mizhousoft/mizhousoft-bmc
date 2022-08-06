package com.mizhousoft.bmc.dictionary.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.dictionary.domain.Field;
import com.mizhousoft.bmc.dictionary.domain.FieldDict;
import com.mizhousoft.bmc.dictionary.mapper.FieldDictMapper;
import com.mizhousoft.bmc.dictionary.service.ListDictService;

/**
 * 列表字典服务
 *
 * @version
 */
@Service
public class ListDictServiceImpl implements ListDictService
{
	@Autowired
	private FieldDictMapper dictMapper;

	// Map<srvId-domain, List<Field>>
	private Map<String, List<Field>> dictMap = new ConcurrentHashMap<>(10);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName(String srvId, String domain, String key)
	{
		Field field = getField(srvId, domain, key);
		if (null != field)
		{
			return field.getValue();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field getField(String srvId, String domain, String key)
	{
		List<Field> list = queryByDomain(srvId, domain);

		Field field = list.stream().filter(item -> item.getKey().equals(key)).findFirst().orElse(null);

		return field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized List<Field> queryByDomain(String srvId, String domain)
	{
		String cacheKey = srvId + "-" + domain;
		List<Field> list = dictMap.get(cacheKey);
		if (null == list)
		{
			List<FieldDict> dicts = dictMapper.findByDomain(srvId, domain);

			list = new ArrayList<>(dicts.size());
			for (FieldDict dict : dicts)
			{
				Field field = new Field(dict.getKey(), dict.getValue());

				list.add(field);
			}

			dictMap.put(cacheKey, list);
		}

		return new ArrayList<>(list);
	}

}
