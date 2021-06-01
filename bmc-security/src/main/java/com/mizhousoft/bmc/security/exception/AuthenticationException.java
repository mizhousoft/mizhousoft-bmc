package com.mizhousoft.bmc.security.exception;

import com.mizhousoft.commons.data.NestedException;

/**
 * 认证异常
 *
 * @version
 */
public abstract class AuthenticationException extends NestedException
{
	private static final long serialVersionUID = -4542318192107683780L;

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public AuthenticationException(String errorCode, String message, Throwable throwable)
	{
		super(errorCode, message, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param message
	 */
	public AuthenticationException(String errorCode, String message)
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
	public AuthenticationException(String errorCode, String[] codeParams, String message, Throwable throwable)
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
	public AuthenticationException(String errorCode, String[] codeParams, String message)
	{
		super(errorCode, codeParams, message);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 * @param throwable
	 */
	public AuthenticationException(String errorCode, Throwable throwable)
	{
		super(errorCode, throwable);
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode
	 */
	public AuthenticationException(String errorCode)
	{
		super(errorCode);
	}
}
