package com.mizhousoft.bmc.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 退出Controller
 * 
 * @version
 */
@Controller
public class LogoutController
{
	/**
	 * 进入登录页面
	 * 
	 * @param req
	 * @param some
	 * @return
	 */
	@RequestMapping(value = "/logout.action", method = RequestMethod.GET)
	public String logout()
	{
		return "redirect:/index.html";
	}
}
