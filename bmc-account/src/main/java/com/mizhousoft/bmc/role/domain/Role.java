package com.mizhousoft.bmc.role.domain;

/**
 * 角色
 * 
 * @version
 */
public class Role
{
	// ID
	private int id;

	// 类型
	private int type;

	// 业务ID
	private String srvId;

	// 角色名
	private String name;

	// 中文角色名
	private String displayNameCN;

	// 英文角色名
	private String displayNameUS;

	// 中文角色描述
	private String descriptionCN;

	// 英文角色描述
	private String descriptionUS;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 获取type
	 * 
	 * @return
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * 设置type
	 * 
	 * @param type
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * 获取srvId
	 * 
	 * @return
	 */
	public String getSrvId()
	{
		return srvId;
	}

	/**
	 * 设置srvId
	 * 
	 * @param srvId
	 */
	public void setSrvId(String srvId)
	{
		this.srvId = srvId;
	}

	/**
	 * 获取name
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取displayNameCN
	 * 
	 * @return
	 */
	public String getDisplayNameCN()
	{
		return displayNameCN;
	}

	/**
	 * 设置displayNameCN
	 * 
	 * @param displayNameCN
	 */
	public void setDisplayNameCN(String displayNameCN)
	{
		this.displayNameCN = displayNameCN;
	}

	/**
	 * 获取displayNameUS
	 * 
	 * @return
	 */
	public String getDisplayNameUS()
	{
		return displayNameUS;
	}

	/**
	 * 设置displayNameUS
	 * 
	 * @param displayNameUS
	 */
	public void setDisplayNameUS(String displayNameUS)
	{
		this.displayNameUS = displayNameUS;
	}

	/**
	 * 获取descriptionCN
	 * 
	 * @return
	 */
	public String getDescriptionCN()
	{
		return descriptionCN;
	}

	/**
	 * 设置descriptionCN
	 * 
	 * @param descriptionCN
	 */
	public void setDescriptionCN(String descriptionCN)
	{
		this.descriptionCN = descriptionCN;
	}

	/**
	 * 获取descriptionUS
	 * 
	 * @return
	 */
	public String getDescriptionUS()
	{
		return descriptionUS;
	}

	/**
	 * 设置descriptionUS
	 * 
	 * @param descriptionUS
	 */
	public void setDescriptionUS(String descriptionUS)
	{
		this.descriptionUS = descriptionUS;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"type\":\"");
		builder.append(type);
		builder.append("\", \"srvId\":\"");
		builder.append(srvId);
		builder.append("\", \"name\":\"");
		builder.append(name);
		builder.append("\", \"displayNameCN\":\"");
		builder.append(displayNameCN);
		builder.append("\", \"displayNameUS\":\"");
		builder.append(displayNameUS);
		builder.append("\", \"descriptionCN\":\"");
		builder.append(descriptionCN);
		builder.append("\", \"descriptionUS\":\"");
		builder.append(descriptionUS);
		builder.append("\"}");
		return builder.toString();
	}
}
