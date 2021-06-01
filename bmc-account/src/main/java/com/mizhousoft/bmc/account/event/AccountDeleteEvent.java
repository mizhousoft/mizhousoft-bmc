package com.mizhousoft.bmc.account.event;

import org.springframework.context.ApplicationEvent;

import com.mizhousoft.bmc.account.domain.Account;

/**
 * 帐号删除事件
 *
 * @version
 */
public class AccountDeleteEvent extends ApplicationEvent
{
	private static final long serialVersionUID = -2081070192378668590L;

	private final Account account;

	/**
	 * 构造函数
	 *
	 * @param account
	 */
	public AccountDeleteEvent(Account account)
	{
		super(account);

		this.account = account;
	}

	/**
	 * 获取account
	 * 
	 * @return
	 */
	public Account getAccount()
	{
		return account;
	}
}
