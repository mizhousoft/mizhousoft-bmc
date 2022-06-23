package com.mizhousoft.bmc.account.domain;

import java.util.Date;

/**
 * 帐号
 *
 * @version
 */
public class Account
{
	// ID
	private long id;

	// 服务ID
	private String srvId;

	// 帐号名称
	private String name;

	// 帐号类型
	private int type;

	// 状态
	private int status;

	// 手机号
	private String phoneNumber;

	// 第一次登录
	private boolean firstLogin;

	// 锁定时间
	private Date lockTime;

	// 最后访问时间
	private Date lastAccessTime;

	// 最后访问IP地址
	private String lastAccessIpAddr;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(long id)
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
	 * 获取type
	 * 
	 * @return
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * 设置type
	 * 
	 * @param type
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * 获取status
	 * 
	 * @return
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * 获取phoneNumber
	 * 
	 * @return
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * 设置phoneNumber
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 获取firstLogin
	 * 
	 * @return
	 */
	public boolean isFirstLogin()
	{
		return firstLogin;
	}

	/**
	 * 设置firstLogin
	 * 
	 * @param firstLogin
	 */
	public void setFirstLogin(boolean firstLogin)
	{
		this.firstLogin = firstLogin;
	}

	/**
	 * 获取lockTime
	 * 
	 * @return
	 */
	public Date getLockTime()
	{
		return lockTime;
	}

	/**
	 * 设置lockTime
	 * 
	 * @param lockTime
	 */
	public void setLockTime(Date lockTime)
	{
		this.lockTime = lockTime;
	}

	/**
	 * 获取lastAccessTime
	 * 
	 * @return
	 */
	public Date getLastAccessTime()
	{
		return lastAccessTime;
	}

	/**
	 * 设置lastAccessTime
	 * 
	 * @param lastAccessTime
	 */
	public void setLastAccessTime(Date lastAccessTime)
	{
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * 获取lastAccessIpAddr
	 * 
	 * @return
	 */
	public String getLastAccessIpAddr()
	{
		return lastAccessIpAddr;
	}

	/**
	 * 设置lastAccessIpAddr
	 * 
	 * @param lastAccessIpAddr
	 */
	public void setLastAccessIpAddr(String lastAccessIpAddr)
	{
		this.lastAccessIpAddr = lastAccessIpAddr;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"name\":\"");
		builder.append(name);
		builder.append("\", \"type\":\"");
		builder.append(type);
		builder.append("\", \"status\":\"");
		builder.append(status);
		builder.append("\", \"phoneNumber\":\"");
		builder.append(phoneNumber);
		builder.append("\", \"firstLogin\":\"");
		builder.append(firstLogin);
		builder.append("\", \"lockTime\":\"");
		builder.append(lockTime);
		builder.append("\", \"lastAccessTime\":\"");
		builder.append(lastAccessTime);
		builder.append("\", \"lastAccessIpAddr\":\"");
		builder.append(lastAccessIpAddr);
		builder.append("\"}");
		return builder.toString();
	}
}
