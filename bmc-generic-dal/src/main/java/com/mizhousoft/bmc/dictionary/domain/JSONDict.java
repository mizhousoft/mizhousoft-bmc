package com.mizhousoft.bmc.dictionary.domain;

import java.util.Date;

/**
 * JSON字典
 *
 * @version
 */
public class JSONDict
{
	// ID
	private int id;

	// key
	private String key;

	// 值
	private String value;

	// 更新时间
	private Date utime;

	// 创建时间
	private Date ctime;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
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
	 * 设置key
	 * 
	 * @param key
	 */
	public void setKey(String key)
	{
		this.key = key;
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

	/**
	 * 设置value
	 * 
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * 获取utime
	 * 
	 * @return
	 */
	public Date getUtime()
	{
		return utime;
	}

	/**
	 * 设置utime
	 * 
	 * @param utime
	 */
	public void setUtime(Date utime)
	{
		this.utime = utime;
	}

	/**
	 * 获取ctime
	 * 
	 * @return
	 */
	public Date getCtime()
	{
		return ctime;
	}

	/**
	 * 设置ctime
	 * 
	 * @param ctime
	 */
	public void setCtime(Date ctime)
	{
		this.ctime = ctime;
	}
}
