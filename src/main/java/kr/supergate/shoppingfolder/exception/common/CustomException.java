package kr.supergate.shoppingfolder.exception.common;

import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public abstract class CustomException extends RuntimeException {

    protected HttpStatus httpStatus;
    private ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
