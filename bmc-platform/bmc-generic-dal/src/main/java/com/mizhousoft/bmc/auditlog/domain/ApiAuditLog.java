package com.mizhousoft.bmc.auditlog.domain;

/**
 * API日志持久层对象
 * 
 * @version
 */
public class ApiAuditLog extends BaseAuditLog
{
	// 操作
	private String operation;

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
