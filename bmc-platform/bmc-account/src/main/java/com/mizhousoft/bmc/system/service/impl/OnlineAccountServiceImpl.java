package com.mizhousoft.bmc.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.model.OnlineAccount;
import com.mizhousoft.bmc.system.service.OnlineAccountService;
import com.mizhousoft.boot.authentication.AccountDetails;
import com.mizhousoft.boot.authentication.GrantedAuthority;
import com.mizhousoft.commons.crypto.generator.RandomGenerator;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.domain.Pageable;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.lang.DateUtils;

/**
 * 在线用户服务
 *
 * @version
 */
@Service
public class OnlineAccountServiceImpl implements OnlineAccountService
{
	private static final Logger LOG = LoggerFactory.getLogger(OnlineAccountServiceImpl.class);

	// 注销ID
	private static final String LOGOFF_RANDOM_ID = "LOGOFF_RANDOM_ID";

	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<OnlineAccount> queryOnlineAccounts(Session currentSession, Pageable pageRequest)
	{
		List<OnlineAccount> onlineAccounts = new ArrayList<OnlineAccount>(10);
		List<Session> sessions = getPageActiveSessions(pageRequest);

		for (Session session : sessions)
		{
			AccountDetails accountDetails = getAccountDetails(session);
			if (null == accountDetails)
			{
				continue;
			}

			StringBuilder roleBuffer = new StringBuilder(20);
			Collection<? extends GrantedAuthority> authorities = accountDetails.getAuthorities();
			for (GrantedAuthority authority : authorities)
			{
				if (roleBuffer.length() != 0)
				{
					roleBuffer.append(",");
				}

				roleBuffer.append(authority.getAuthority());
			}

			Date loginTime = session.getStartTimestamp();

			OnlineAccount onlineAccount = new OnlineAccount();
			onlineAccount.setIpAddress(session.getHost());
			onlineAccount.setLoginTime(DateUtils.formatYmdhms(loginTime));
			onlineAccount.setId(accountDetails.getAccountId());
			onlineAccount.setName(accountDetails.getAccountName());
			onlineAccount.setRole(roleBuffer.toString());

			if (currentSession.getId().equals(session.getId()))
			{
				onlineAccount.setCurrentAccount(true);
			}

			String randomId = RandomGenerator.genHexString(32, false);
			onlineAccount.setRandomId(randomId);
			session.setAttribute(LOGOFF_RANDOM_ID, randomId);

			onlineAccounts.add(onlineAccount);
		}

		Page<OnlineAccount> dataPage = PageBuilder.build(onlineAccounts, pageRequest, onlineAccounts.size());
		return dataPage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountDetails logoffAccount(long accountId, String randomId) throws BMCException
	{
		Session session = null;
		Collection<Session> activeSessions = sessionDAO.getActiveSessions();
		for (Session s : activeSessions)
		{
			String sessionRandomId = (String) s.getAttribute(LOGOFF_RANDOM_ID);
			if (randomId.equals(sessionRandomId))
			{
				session = s;
				break;
			}
		}

		AccountDetails accountDetails = null;

		if (null != session)
		{
			accountDetails = getAccountDetails(session);
			if (null != accountDetails)
			{
				if (accountDetails.getAccountId() == accountId)
				{
					sessionDAO.delete(session);
				}
				else
				{
					throw new BMCException("bmc.online.account.logoff.invalid", "You can not logoff the account, account id is " + accountId
					        + ", session account id is " + accountDetails.getAccountId() + '.');
				}
			}
		}
		else
		{
			LOG.warn("Account has been logoffed, account id is " + accountId + '.');
		}

		return accountDetails;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logoffAll()
	{
		Collection<Session> activeSessions = sessionDAO.getActiveSessions();
		for (Session session : activeSessions)
		{
			AccountDetails accountDetails = getAccountDetails(session);
			if (null == accountDetails)
			{
				LOG.error("Session is not AccountDetails, id is {}, host is {}.", session.getId(), session.getHost());
				continue;
			}

			sessionDAO.delete(session);

			LOG.warn("Logoff account successfully, id is {}, name is {}.", accountDetails.getAccountId(), accountDetails.getAccountName());
		}
	}

	/**
	 * 获取页面激活的Session
	 * 
	 * @param pageRequest
	 * @return
	 */
	private List<Session> getPageActiveSessions(Pageable pageRequest)
	{
		Collection<Session> activeSessions = sessionDAO.getActiveSessions();
		List<Session> activeSessionList = new ArrayList<Session>(activeSessions);

		int beginIndex = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
		int endIndex = pageRequest.getPageNumber() * pageRequest.getPageSize();

		List<Session> sessions = new ArrayList<Session>(pageRequest.getPageSize());

		if (activeSessionList.size() > beginIndex)
		{
			if (activeSessionList.size() >= endIndex)
			{
				sessions.addAll(activeSessionList.subList(beginIndex, endIndex));
			}
			else
			{
				sessions.addAll(activeSessionList.subList(beginIndex, activeSessionList.size()));
			}
		}
		else
		{
			if (activeSessionList.size() > pageRequest.getPageSize())
			{
				int index = activeSessionList.size() - pageRequest.getPageSize();
				sessions.addAll(activeSessionList.subList(index, activeSessionList.size()));
			}
			else
			{
				sessions.addAll(activeSessionList);
			}
		}

		return sessions;
	}

	/**
	 * 获取AccountDetails
	 * 
	 * @param session
	 * @return
	 */
	private AccountDetails getAccountDetails(Session session)
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
