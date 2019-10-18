package kr.supergate.shoppingfolder.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class SessionUtils {

	 public static String generate() {
		 return UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
	 }

	public static String getKey(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (XHttpHeader.X_SESSION.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}

		return null;
	}
}
