package com.mizhousoft.bmc.authentication.service;

import java.util.Map;

/**
 * 应用首次加载服务
 *
 * @version
 */
public interface ApplicationFirstLoadService
{
	/**
	 * 获取加载数据
	 * 
	 * @return
	 */
	Map<String, Object> obtainLoadedData();
}
