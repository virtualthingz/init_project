package kr.supergate.shoppingfolder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
@SuppressWarnings("serial")
public class UpgradeRequiredException extends RuntimeException {

	public UpgradeRequiredException(String message) {
		super(message);
	}
	
}
