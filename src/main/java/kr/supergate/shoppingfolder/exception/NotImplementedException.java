package kr.supergate.shoppingfolder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
@SuppressWarnings("serial")
public class NotImplementedException extends RuntimeException {

	public NotImplementedException(String message) {
		super(message);
	}
	
}
