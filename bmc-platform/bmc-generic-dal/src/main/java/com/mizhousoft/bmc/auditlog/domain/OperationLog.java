package com.mizhousoft.bmc.auditlog.domain;

/**
 * 操作日志持久层对象
 * 
 * @version
 */
public class OperationLog extends BaseAuditLog
{
	// 操作
	private String operation;

	// 帐号
	private String accountName;

	// 操作终端
	private String terminal;

	/**
	 * 获取operation
	 * 
	 * @return
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * 设置operation
	 * 
	 * @param operation
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * 获取accountName
	 * 
	 * @return
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * 设置accountName
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * 获取terminal
	 * 
	 * @return
	 */
	public String getTerminal()
	{
		return terminal;
	}

	/**
	 * 设置terminal
	 * 
	 * @param terminal
	 */
	public void setTerminal(String terminal)
	{
		this.terminal = terminal;
	}
}
