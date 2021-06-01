package com.mizhousoft.bmc.security.session;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;

/**
 * 安全的SessionDAO
 * 
 * @version
 */
public class SecureSessionDAO extends MemorySessionDAO
{
	/**
	 * 创建Session Id
	 * 
	 * @param session
	 * @return
	 */
	protected Serializable doCreate(Session session)
	{
		Serializable sessionId = session.getId();
		storeSession(sessionId, session);
		return sessionId;
	}
}
