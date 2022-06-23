package com.mizhousoft.bmc.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 角色权限持久层Mapper
 *
 * @version
 */
public interface RolePermissionMapper extends CrudMapper<RolePermission, Integer>
{
	/**
	 * 根据角色名删除
	 * 
	 * @param srvId
	 * @param roleName
	 */
	void deleteByRoleName(@Param("srvId")
	String srvId, @Param("roleName")
	String roleName);

	/**
	 * 根据角色名称查询权限
	 * 
	 * @param srvId
	 * @param roleName
	 * @return
	 */
	List<RolePermission> findByRoleName(@Param("srvId")
	String srvId, @Param("roleName")
	String roleName);
}
