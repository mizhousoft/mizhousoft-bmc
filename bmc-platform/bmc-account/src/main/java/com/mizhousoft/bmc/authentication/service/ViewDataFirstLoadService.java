package com.mizhousoft.bmc.authentication.service;

import java.util.Map;

/**
 * 视图数据首次加载服务
 *
 * @version
 */
public interface ViewDataFirstLoadService
{
	/**
	 * 获取加载数据
	 * 
	 * @return
	 */
	Map<String, Object> obtainLoadedData();
}
