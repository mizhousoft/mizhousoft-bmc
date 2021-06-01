package com.mizhousoft.bmc.security.exception;

/**
 * 帐号禁用异常
 *
 * @version
 */
public class AccountDisabledException extends AuthenticationException
{
	private static final long serialVersionUID = -1868061184673181401L;

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public AccountDisabledException(String errorCode, String message, Throwable throwable)
	{
		super(errorCode, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 */
	public AccountDisabledException(String errorCode, String message)
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
	public AccountDisabledException(String errorCode, String[] codeParams, String message, Throwable throwable)
	{
		super(errorCode, codeParams, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param throwable
	 */
	public AccountDisabledException(String errorCode, Throwable throwable)
	{
		super(errorCode, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 */
	public AccountDisabledException(String errorCode)
	{
		super(errorCode);
	}
}
