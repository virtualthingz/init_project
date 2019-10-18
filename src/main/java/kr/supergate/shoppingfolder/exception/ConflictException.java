package kr.supergate.shoppingfolder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@SuppressWarnings("serial")
public class ConflictException extends RuntimeException {

	public ConflictException(String message) {
		super(message);
	}
	
}
