package com.mizhousoft.bmc.system.request;

/**
 * 注销帐号请求
 *
 * @version
 */
public class LogoffAccountRequest
{
	// ID
	private long id;

	// Random id
	private String randomId;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * 获取randomId
	 * 
	 * @return
	 */
	public String getRandomId()
	{
		return randomId;
	}

	/**
	 * 设置randomId
	 * 
	 * @param randomId
	 */
	public void setRandomId(String randomId)
	{
		this.randomId = randomId;
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
		builder.append("\"}");
		return builder.toString();
	}
}
