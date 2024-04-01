package com.mizhousoft.bmc.auditlog.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	// 最后修改时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime lastModified;

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
	 * 获取lastModified
	 * 
	 * @return
	 */
	public LocalDateTime getLastModified()
	{
		return lastModified;
	}

	/**
	 * 设置lastModified
	 * 
	 * @param lastModified
	 */
	public void setLastModified(LocalDateTime lastModified)
	{
		this.lastModified = lastModified;
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
