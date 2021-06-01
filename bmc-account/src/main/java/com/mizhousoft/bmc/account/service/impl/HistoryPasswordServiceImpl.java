package com.mizhousoft.bmc.account.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.account.domain.HistoryPassword;
import com.mizhousoft.bmc.account.mapper.HistoryPasswordMapper;
import com.mizhousoft.bmc.account.service.HistoryPasswordService;

/**
 * 历史密码服务
 *
 * @version
 */
@Service
public class HistoryPasswordServiceImpl implements HistoryPasswordService
{
	// 历史密码持久层Mapper
	@Autowired
	private HistoryPasswordMapper historyPasswordMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(long accountId, String oldPassword)
	{
		HistoryPassword historyPassword = new HistoryPassword();
		historyPassword.setAccountId(accountId);
		historyPassword.setHistoryPwd(oldPassword);
		historyPassword.setModifyTime(new Date());

		historyPasswordMapper.save(historyPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(long accountId)
	{
		historyPasswordMapper.delete(accountId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryPassword> queryHistoryPasswords(long accountId, int topNum)
	{
		return historyPasswordMapper.findHistoryPasswords(accountId, topNum);
	}
}
