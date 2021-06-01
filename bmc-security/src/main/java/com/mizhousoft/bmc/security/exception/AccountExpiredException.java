package com.mizhousoft.bmc.security.exception;

/**
 * 帐号过期异常
 *
 * @version
 */
public class AccountExpiredException extends AuthenticationException
{
	private static final long serialVersionUID = -6133144996468767783L;

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public AccountExpiredException(String errorCode, String message, Throwable throwable)
	{
		super(errorCode, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 */
	public AccountExpiredException(String errorCode, String message)
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
	public AccountExpiredException(String errorCode, String[] codeParams, String message, Throwable throwable)
	{
		super(errorCode, codeParams, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param throwable
	 */
	public AccountExpiredException(String errorCode, Throwable throwable)
	{
		super(errorCode, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 */
	public AccountExpiredException(String errorCode)
	{
		super(errorCode);
	}
}
