package com.mizhousoft.bmc.identity.domain;

/**
 * 对象ID标识
 *
 * @version
 */
public class ObjectIdentity
{
	// 标识名称
	private String name;

	// 值
	private long value;

	/**
	 * 获取name
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public long getValue()
	{
		return value;
	}

	/**
	 * 设置value
	 * 
	 * @param value
	 */
	public void setValue(long value)
	{
		this.value = value;
	}
}
