package com.mizhousoft.bmc.security.exception;

/**
 * 帐号锁定异常
 *
 * @version
 */
public class AccountLockedException extends AuthenticationException
{
	private static final long serialVersionUID = -6244437727688201849L;

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public AccountLockedException(String errorCode, String message, Throwable throwable)
	{
		super(errorCode, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 */
	public AccountLockedException(String errorCode, String message)
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
	public AccountLockedException(String errorCode, String[] codeParams, String message, Throwable throwable)
	{
		super(errorCode, codeParams, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param throwable
	 */
	public AccountLockedException(String errorCode, Throwable throwable)
	{
		super(errorCode, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 */
	public AccountLockedException(String errorCode)
	{
		super(errorCode);
	}
}
