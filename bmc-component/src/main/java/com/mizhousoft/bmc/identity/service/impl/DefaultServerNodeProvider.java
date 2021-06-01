package com.mizhousoft.bmc.identity.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mizhousoft.bmc.identity.service.ServerNodeProvider;

/**
 * 默认的服务节点提供者
 *
 * @version
 */
@Component
public class DefaultServerNodeProvider implements ServerNodeProvider
{
	@Value("${server.node.id}")
	private String serverNodeId;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getServerNodeId()
	{
		return serverNodeId;
	}
}
