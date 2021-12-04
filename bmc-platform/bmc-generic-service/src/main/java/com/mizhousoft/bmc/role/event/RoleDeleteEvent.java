package com.mizhousoft.bmc.role.event;

import org.springframework.context.ApplicationEvent;

import com.mizhousoft.bmc.role.domain.Role;

/**
 * 角色删除事件
 *
 * @version
 */
public class RoleDeleteEvent extends ApplicationEvent
{
	private static final long serialVersionUID = -3776087181850776295L;

	// 角色
	private final Role role;

	/**
	 * 构造函数
	 *
	 * @param role
	 */
	public RoleDeleteEvent(Role role)
	{
		super(role);

		this.role = role;
	}

	/**
	 * 获取role
	 * 
	 * @return
	 */
	public Role getRole()
	{
		return role;
	}
}
