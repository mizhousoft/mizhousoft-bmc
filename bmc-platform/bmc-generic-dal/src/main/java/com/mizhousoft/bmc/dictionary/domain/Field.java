package com.mizhousoft.bmc.dictionary.domain;

/**
 * 字段值
 *
 * @version
 */
public class Field
{
	// key
	private final String key;

	// 值
	private final String value;

	/**
	 * 构造函数
	 *
	 * @param key
	 * @param value
	 */
	public Field(String key, String value)
	{
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * 获取key
	 * 
	 * @return
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public String getValue()
	{
		return value;
	}
}
