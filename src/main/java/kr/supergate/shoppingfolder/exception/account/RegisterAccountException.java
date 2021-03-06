package kr.supergate.shoppingfolder.exception.account;

import kr.supergate.shoppingfolder.exception.common.CustomException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class RegisterAccountException extends CustomException {
    public RegisterAccountException(ErrorMessage errorMessage) {
        super(errorMessage);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
