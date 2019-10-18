package kr.supergate.shoppingfolder.exception.account;

import kr.supergate.shoppingfolder.exception.common.CustomException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException(ErrorMessage errorMessage) {
        super(errorMessage);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
