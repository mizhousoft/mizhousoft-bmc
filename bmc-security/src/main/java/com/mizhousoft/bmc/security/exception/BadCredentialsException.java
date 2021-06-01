package com.mizhousoft.bmc.security.exception;

/**
 * 错误凭证异常
 *
 * @version
 */
public class BadCredentialsException extends AuthenticationException
{
	private static final long serialVersionUID = -7927953723836581237L;

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public BadCredentialsException(String errorCode, String message, Throwable throwable)
	{
		super(errorCode, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 */
	public BadCredentialsException(String errorCode, String message)
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
	public BadCredentialsException(String errorCode, String[] codeParams, String message, Throwable throwable)
	{
		super(errorCode, codeParams, message, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 * @param codeParams
	 * @param message
	 */
	public BadCredentialsException(String errorCode, String[] codeParams, String message)
	{
		super(errorCode, codeParams, message);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param throwable
	 */
	public BadCredentialsException(String errorCode, Throwable throwable)
	{
		super(errorCode, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 */
	public BadCredentialsException(String errorCode)
	{
		super(errorCode);
	}
}
