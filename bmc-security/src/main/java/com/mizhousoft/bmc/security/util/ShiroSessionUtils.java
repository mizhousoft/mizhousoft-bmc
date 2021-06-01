package com.mizhousoft.bmc.security.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.bmc.security.AccountDetails;

/**
 * Shiro Session工具类
 *
 * @version
 */
public abstract class ShiroSessionUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(ShiroSessionUtils.class);

	/**
	 * 获取所有的AccountDetails
	 * 
	 * @param sessions
	 * @return
	 */
	public static List<AccountDetails> getAccountDetailsList(Collection<Session> sessions)
	{
		List<AccountDetails> accountDetailsList = new ArrayList<AccountDetails>(10);

		for (Session session : sessions)
		{
			AccountDetails accountDetails = getAccountDetails(session);
			if (null != accountDetails)
			{
				accountDetailsList.add(accountDetails);
			}
		}

		return accountDetailsList;
	}

	/**
	 * 根据session获取AccountDetails
	 * 
	 * @param session
	 * @return
	 */
	public static AccountDetails getAccountDetails(Session session)
	{
		Object simplePrincipalCollection = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if (simplePrincipalCollection instanceof SimplePrincipalCollection)
		{
			Object primaryPrincipal = ((SimplePrincipalCollection) simplePrincipalCollection).getPrimaryPrincipal();
			if (primaryPrincipal instanceof AccountDetails)
			{
				return ((AccountDetails) primaryPrincipal);
			}
			else
			{
				LOG.error("Primary Principal object is not AccountDetails.");
			}
		}
		else
		{
			LOG.error("Session PRINCIPALS_SESSION_KEY object is not SimplePrincipalCollection.");
		}

		return null;
	}
}
