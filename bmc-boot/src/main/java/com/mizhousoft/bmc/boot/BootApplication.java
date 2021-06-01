package com.mizhousoft.bmc.boot;

import javax.servlet.ServletContextListener;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mizhousoft.commons.web.context.CommonContextLoaderListener;

@EnableAsync
@SpringBootApplication()
@EnableTransactionManagement
@ComponentScan("com.mizhousoft")
@MapperScan("**/mapper")
public class BootApplication extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(BootApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(BootApplication.class);
	}

	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> getServletContextListener()
	{
		ServletListenerRegistrationBean<ServletContextListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new CommonContextLoaderListener());
		registrationBean.setOrder(10);

		return registrationBean;
	}
}
