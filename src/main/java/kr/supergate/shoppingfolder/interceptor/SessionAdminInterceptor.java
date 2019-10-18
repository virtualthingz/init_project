package kr.supergate.shoppingfolder.interceptor;


import kr.supergate.shoppingfolder.common.Attribute;
import kr.supergate.shoppingfolder.common.XHttpHeader;
import kr.supergate.shoppingfolder.domain.admin.Admin;
import kr.supergate.shoppingfolder.exception.UnauthorizedException;
import kr.supergate.shoppingfolder.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.plugin.Intercepts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionAdminInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private AccountService accountService;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String profile = System.getProperty("spring.profiles.active");

		if("localhost".equals(profile)) {
			return true;
		}

		String session = null;
		
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (XHttpHeader.ISSESSIONID.equals(cookie.getName())) {
					session = cookie.getValue();
				}
			}
		}
		
		if (StringUtils.isEmpty(session)) {
			session = request.getHeader(XHttpHeader.ISSESSIONID);
		}

		if (StringUtils.isEmpty(session)) {
			throw new UnauthorizedException("session is empty.");
		}
		
		
		Admin admin = accountService.getAdminBySession(session);
		if (admin == null) {
			throw new UnauthorizedException("admin is null.");
		}
		
		request.setAttribute(Attribute.ADMIN, admin);
		
		return super.preHandle(request, response, handler);
	}
}
