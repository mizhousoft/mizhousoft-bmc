package com.mizhousoft.bmc.boot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.security.filter.FirstLoginCheckFilter;
import com.mizhousoft.bmc.security.filter.PasswordExpiredCheckFilter;
import com.mizhousoft.bmc.security.filter.SecurityContextPersistenceFilter;
import com.mizhousoft.bmc.security.filter.SecurityFrameworkFilter;
import com.mizhousoft.bmc.security.filter.authc.AccountPasswordAuthenticationFilter;
import com.mizhousoft.bmc.security.filter.authc.CustLogoutFilter;
import com.mizhousoft.bmc.security.filter.authc.SessionAuthenticationFilter;
import com.mizhousoft.bmc.security.filter.authz.AccessAuthorizationFilter;
import com.mizhousoft.bmc.security.mgt.DefaultWebSecurityManager;
import com.mizhousoft.bmc.security.realm.AuthenticationRealm;
import com.mizhousoft.bmc.security.service.AccessControlService;
import com.mizhousoft.bmc.security.service.AccountAuthcService;
import com.mizhousoft.bmc.security.session.SecureSessionDAO;
import com.mizhousoft.bmc.security.session.SecureSessionFactory;
import com.mizhousoft.bmc.security.session.SecureWebSessionManager;

/**
 * ShiroConfiguration
 *
 * @version
 */
@Configuration
public class ShiroConfiguration
{
	@Bean
	public SecureSessionDAO getSecureSessionDAO()
	{
		SecureSessionDAO sessionDAO = new SecureSessionDAO();
		return sessionDAO;
	}

	@Bean
	public SecureSessionFactory getSecureSessionFactory()
	{
		SecureSessionFactory sessionFactory = new SecureSessionFactory();
		return sessionFactory;
	}

	@Bean(destroyMethod = "destroy")
	public SecureWebSessionManager getSecureWebSessionManager(SecureSessionDAO sessionDAO, SecureSessionFactory sessionFactory)
	{
		SecureWebSessionManager sessionManager = new SecureWebSessionManager();
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setSessionFactory(sessionFactory);
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);

		ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
		sessionValidationScheduler.setInterval(1800000);
		sessionValidationScheduler.setSessionManager(sessionManager);
		sessionManager.setSessionValidationScheduler(sessionValidationScheduler);

		return sessionManager;
	}

	@Bean
	public AuthenticationRealm getAuthenticationRealm(AccountAuthcService accountAuthcService)
	{
		AuthenticationRealm authenticationRealm = new AuthenticationRealm();
		authenticationRealm.setAccountAuthcService(accountAuthcService);
		return authenticationRealm;
	}

	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(SecureWebSessionManager sessionManager,
	        AuthenticationRealm authenticationRealm)
	{
		DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
		webSecurityManager.setRememberMeManager(null);
		webSecurityManager.setSessionManager(sessionManager);
		webSecurityManager.setRealm(authenticationRealm);

		return webSecurityManager;
	}

	@Bean
	public DefaultFilterChainManager getDefaultFilterChainManager(PermissionService permissionService,
	        AccessControlService accessControlService)
	{
		DefaultFilterChainManager filterChain = new DefaultFilterChainManager();

		SessionAuthenticationFilter sessionAuthenticationFilter = new SessionAuthenticationFilter();
		sessionAuthenticationFilter.setLoginUrl("/login.action");

		LogoutFilter logoutFilter = new CustLogoutFilter();
		logoutFilter.setRedirectUrl("/login.action");

		AccountPasswordAuthenticationFilter accountAuthenticationFilter = new AccountPasswordAuthenticationFilter();
		accountAuthenticationFilter.setLoginUrl("/login.action");
		accountAuthenticationFilter.setSuccessUrl("/");

		SecurityContextPersistenceFilter securityContextFilter = new SecurityContextPersistenceFilter();
		securityContextFilter.setLoginUrl("/login.action");

		AnonymousFilter anonFilter = new AnonymousFilter();

		AccessAuthorizationFilter accessAuthorizationFilter = new AccessAuthorizationFilter();
		accessAuthorizationFilter.setAccessControlService(accessControlService);
		accessAuthorizationFilter.setLoginUrl("/login.action");
		accessAuthorizationFilter.setUnauthorizedUrl("/unauthorized.action");

		FirstLoginCheckFilter firstLoginFilter = new FirstLoginCheckFilter();
		firstLoginFilter.setLoginUrl("/login.action");

		PasswordExpiredCheckFilter passwordExpiredCheckFilter = new PasswordExpiredCheckFilter();
		passwordExpiredCheckFilter.setLoginUrl("/login.action");

		Map<String, Filter> filters = new HashMap<String, Filter>(5);
		filters.put("anon", anonFilter);
		filters.put("logout", logoutFilter);
		filters.put("authc", accountAuthenticationFilter);
		filters.put("authz", accessAuthorizationFilter);
		filters.put("securityContextFilter", securityContextFilter);
		filters.put("sessionAuthc", sessionAuthenticationFilter);
		filters.put("firstLoginCheckFilter", firstLoginFilter);
		filters.put("passwordExpiredCheckFilter", passwordExpiredCheckFilter);
		filterChain.setFilters(filters);

		buildFilterChainManager(filterChain, permissionService);

		return filterChain;
	}

	@Bean
	public PathMatchingFilterChainResolver getPathMatchingFilterChainResolver(DefaultFilterChainManager filterChainManager)
	{
		PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
		filterChainResolver.setFilterChainManager(filterChainManager);

		return filterChainResolver;
	}

	public DefaultFilterChainManager buildFilterChainManager(DefaultFilterChainManager filterChain, PermissionService permissionService)
	{
		List<String> authzPaths = permissionService.queryAuthzRequestPaths();
		for (String authzPath : authzPaths)
		{
			filterChain.addToChain(authzPath, "authc");
			filterChain.addToChain(authzPath, "authz");
			filterChain.addToChain(authzPath, "securityContextFilter");
			filterChain.addToChain(authzPath, "sessionAuthc");
			filterChain.addToChain(authzPath, "firstLoginCheckFilter");
			filterChain.addToChain(authzPath, "passwordExpiredCheckFilter");
		}

		List<String> authcPaths = permissionService.queryAuthcRequestPaths();
		for (String authcPath : authcPaths)
		{
			filterChain.addToChain(authcPath, "authc");
			filterChain.addToChain(authcPath, "securityContextFilter");
			filterChain.addToChain(authcPath, "sessionAuthc");
			filterChain.addToChain(authcPath, "firstLoginCheckFilter");
			filterChain.addToChain(authcPath, "passwordExpiredCheckFilter");
		}

		List<String> onlyAuthcPaths = getOnlyAuthcPaths();
		for (String onlyAuthcPath : onlyAuthcPaths)
		{
			filterChain.addToChain(onlyAuthcPath, "authc");
			filterChain.addToChain(onlyAuthcPath, "securityContextFilter");
			filterChain.addToChain(onlyAuthcPath, "sessionAuthc");
		}

		filterChain.addToChain("/login.action", "authc");

		filterChain.addToChain("/logout.action", "authc");
		filterChain.addToChain("/logout.action", "logout");

		filterChain.addToChain("/**", "authc");
		filterChain.addToChain("/**", "authz");
		filterChain.addToChain("/**", "securityContextFilter");
		filterChain.addToChain("/**", "sessionAuthc");
		filterChain.addToChain("/**", "firstLoginCheckFilter");
		filterChain.addToChain("/**", "passwordExpiredCheckFilter");

		return filterChain;
	}

	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean()
	{
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		SecurityFrameworkFilter filter = new SecurityFrameworkFilter();
		registrationBean.setFilter(filter);

		List<String> urlPatterns = new ArrayList<String>(1);
		urlPatterns.add("*.action");
		registrationBean.setUrlPatterns(urlPatterns);

		return registrationBean;
	}

	private List<String> getOnlyAuthcPaths()
	{
		List<String> onlyAuthcPaths = new ArrayList<>(10);

		onlyAuthcPaths.add("/setting/password/modifyFirstLoginPassword.action");
		onlyAuthcPaths.add("/setting/password/modifyExpiredPassword.action");
		onlyAuthcPaths.add("/setting/password/fetchPasswordExpiringDays.action");
		onlyAuthcPaths.add("/setting/password/modifyExpiringPassword.action");
		onlyAuthcPaths.add("/unauthorized.action");

		return onlyAuthcPaths;
	}
}
