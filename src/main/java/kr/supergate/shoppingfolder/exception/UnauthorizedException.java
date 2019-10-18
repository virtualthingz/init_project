package kr.supergate.shoppingfolder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException() {
		super(HttpStatus.UNAUTHORIZED.getReasonPhrase());
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}
	
}
