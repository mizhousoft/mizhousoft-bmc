package com.mizhousoft.bmc.dictionary.domain;

import java.time.LocalDateTime;

/**
 * JSON字典
 *
 * @version
 */
public class JSONDict
{
	// ID
	private int id;

	// 服务ID
	private String srvId;

	// key
	private String key;

	// 值
	private String value;

	// 更新时间
	private LocalDateTime utime;

	// 创建时间
	private LocalDateTime ctime;

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
	 * 获取srvId
	 * 
	 * @return
	 */
	public String getSrvId()
	{
		return srvId;
	}

	/**
	 * 设置srvId
	 * 
	 * @param srvId
	 */
	public void setSrvId(String srvId)
	{
		this.srvId = srvId;
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
	public LocalDateTime getUtime()
	{
		return utime;
	}

	/**
	 * 设置utime
	 * 
	 * @param utime
	 */
	public void setUtime(LocalDateTime utime)
	{
		this.utime = utime;
	}

	/**
	 * 获取ctime
	 * 
	 * @return
	 */
	public LocalDateTime getCtime()
	{
		return ctime;
	}

	/**
	 * 设置ctime
	 * 
	 * @param ctime
	 */
	public void setCtime(LocalDateTime ctime)
	{
		this.ctime = ctime;
	}
}
