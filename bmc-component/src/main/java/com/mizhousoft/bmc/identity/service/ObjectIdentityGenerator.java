package com.mizhousoft.bmc.identity.service;

/**
 * 对象ID标识生成器
 *
 * @version
 */
public interface ObjectIdentityGenerator
{
	/**
	 * 生成ID
	 * 
	 * @param name
	 * @return
	 */
	String genObjectId(String name);
}
