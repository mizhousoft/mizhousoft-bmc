package com.mizhousoft.bmc.auditlog;

import com.mizhousoft.bmc.BMCException;

/**
 * 日志审计异常类
 * 
 * @version
 */
public class AuditLogException extends BMCException
{
	private static final long serialVersionUID = 5336804117081064935L;

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 */
	public AuditLogException(String errorCode)
	{
		super(errorCode);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public AuditLogException(String errorCode, String message, Throwable throwable)
	{
		super(errorCode, message, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 * @param message
	 */
	public AuditLogException(String errorCode, String message)
	{
		super(errorCode, message);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 * @param codeParams
	 * @param message
	 * @param throwable
	 */
	public AuditLogException(String errorCode, String[] codeParams, String message, Throwable throwable)
	{
		super(errorCode, codeParams, message, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 * @param throwable
	 */
	public AuditLogException(String errorCode, Throwable throwable)
	{
		super(errorCode, throwable);
	}
}
