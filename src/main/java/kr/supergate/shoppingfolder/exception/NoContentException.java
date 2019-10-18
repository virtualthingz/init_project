package kr.supergate.shoppingfolder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
@SuppressWarnings("serial")
public class NoContentException extends RuntimeException {

    public NoContentException() {
        super(HttpStatus.NO_CONTENT.getReasonPhrase());
    }

    public NoContentException(String message) {
        super(message);
    }

}
