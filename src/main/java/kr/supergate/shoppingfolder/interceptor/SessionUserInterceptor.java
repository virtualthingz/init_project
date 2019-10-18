package kr.supergate.shoppingfolder.interceptor;



import kr.supergate.shoppingfolder.common.Attribute;
import kr.supergate.shoppingfolder.common.XHttpHeader;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.exception.UnauthorizedException;

import kr.supergate.shoppingfolder.service.AccountService;
import kr.supergate.shoppingfolder.util.ActiveProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionUserInterceptor extends HandlerInterceptorAdapter {
	final Logger logger = LoggerFactory.getLogger(SessionUserInterceptor.class);
	
	@Autowired
	private AccountService accountService;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String session = null;

		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (XHttpHeader.X_SESSION.equals(cookie.getName())) {
					session = cookie.getValue();
				}
			}
		}


		if (StringUtils.isEmpty(session)) {
			session = request.getHeader(XHttpHeader.X_SESSION);
		}

		if (StringUtils.isEmpty(session)) {
			throw new UnauthorizedException("x-session is empty.");
		}


		User user = accountService.getUserBySession(session);
		if (user == null) {
			throw new UnauthorizedException("user is null.");
		}

		request.setAttribute(Attribute.USER, user);
		
		return super.preHandle(request, response, handler);
	}
}
