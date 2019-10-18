package kr.supergate.shoppingfolder.exception.user;


import kr.supergate.shoppingfolder.exception.common.CustomException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class InvalidUpdateException extends CustomException {
    public InvalidUpdateException(ErrorMessage errorMessage) {
        super(errorMessage);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
