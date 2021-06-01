package com.mizhousoft.bmc.role.mapper;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 权限持久层Mapper
 *
 * @version
 */
public interface PermissionMapper extends CrudMapper<Permission, Integer>
{
	/**
	 * 根据请求路径查询权限
	 * 
	 * @param path
	 * @return
	 */
	Permission findByRequestPath(@Param("path") String path);
}
