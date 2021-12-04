package com.mizhousoft.bmc.auditlog.domain;

/**
 * 日志文件
 *
 * @version
 */
public class LogFile implements Comparable<LogFile>
{
	// 名称
	private String name;

	// 大小
	private String size;

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
	 * 获取size
	 * 
	 * @return
	 */
	public String getSize()
	{
		return size;
	}

	/**
	 * 设置size
	 * 
	 * @param size
	 */
	public void setSize(String size)
	{
		this.size = size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(LogFile o)
	{
		return this.name.compareTo(o.getName());
	}
}
