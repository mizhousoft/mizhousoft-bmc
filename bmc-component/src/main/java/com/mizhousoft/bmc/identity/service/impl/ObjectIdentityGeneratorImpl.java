package com.mizhousoft.bmc.identity.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mizhousoft.bmc.identity.domain.ObjectIdentity;
import com.mizhousoft.bmc.identity.mapper.ObjectIdentityMapper;
import com.mizhousoft.bmc.identity.service.ObjectIdentityGenerator;
import com.mizhousoft.bmc.identity.service.ServerNodeProvider;

/**
 * 对象ID标识生成器
 *
 * @version
 */
@Component
public class ObjectIdentityGeneratorImpl implements ObjectIdentityGenerator
{
	// 对象ID标识持久层
	@Autowired
	private ObjectIdentityMapper objectIdentityMapper;

	@Autowired
	private ServerNodeProvider serverNodeProvider;

	/**
	 * 生成资源ID
	 * 
	 * @param name
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public synchronized String genObjectId(String name)
	{
		ObjectIdentity ri = objectIdentityMapper.findById(name);
		if (null == ri)
		{
			ri = new ObjectIdentity();
			ri.setName(name);
			ri.setValue(1);

			objectIdentityMapper.save(ri);
		}
		else
		{
			ri.setValue(ri.getValue() + 1);
			objectIdentityMapper.update(ri);
		}

		// ID从1000000开始取值
		String value = StringUtils.leftPad(String.valueOf(ri.getValue()), 7, '0');

		String serverNodeId = serverNodeProvider.getServerNodeId();

		StringBuilder idbuf = new StringBuilder(25);
		idbuf.append(serverNodeId).append('-').append(name).append('-').append(value);

		return idbuf.toString();
	}
}
