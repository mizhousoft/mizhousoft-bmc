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

	private Map<String, List<Field>> dictMap = new ConcurrentHashMap<>(10);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName(String domain, String key)
	{
		Field field = getField(domain, key);
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
	public Field getField(String domain, String key)
	{
		List<Field> list = queryByDomain(domain);

		Field field = list.stream().filter(item -> item.getKey().equals(key)).findFirst().orElse(null);

		return field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized List<Field> queryByDomain(String domain)
	{
		List<Field> list = dictMap.get(domain);
		if (null == list)
		{
			List<FieldDict> dicts = dictMapper.findByDomain(domain);

			list = new ArrayList<>(dicts.size());
			for (FieldDict dict : dicts)
			{
				Field field = new Field(dict.getKey(), dict.getValue());

				list.add(field);
			}

			dictMap.put(domain, list);
		}

		return list;
	}

}
