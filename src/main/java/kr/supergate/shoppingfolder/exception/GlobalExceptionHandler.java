package kr.supergate.shoppingfolder.exception;


import kr.supergate.shoppingfolder.exception.common.CustomException;
import kr.supergate.shoppingfolder.exception.model.ApiError;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import kr.supergate.shoppingfolder.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	private static final String STANDARD_SPRING_EXCEPTION = "STANDARD_SPRING_EXCEPTION";

    @Autowired
    private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(STANDARD_SPRING_EXCEPTION, e);
		return super.handleExceptionInternal(e, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.toString(),
				ex.getBindingResult().getFieldErrors().stream()
						.map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
						.collect(Collectors.joining(" "))),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handle(RuntimeException e) {
		String msg = HttpStatus.INTERNAL_SERVER_ERROR.name() + " " + e.getMessage();
		logger.error(msg, e);
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(NoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Object> handle(NoContentException e) {
		logger.error(HttpStatus.NO_CONTENT.name(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handle(BadRequestException e) {
		logger.error(HttpStatus.BAD_REQUEST.name(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handle(UnauthorizedException e) {
		logger.error(HttpStatus.UNAUTHORIZED.name(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Object> handle(ConflictException e) {
		logger.error(HttpStatus.CONFLICT.name(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UpgradeRequiredException.class)
	@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
	public ResponseEntity<Object> handle(UpgradeRequiredException e) {
		logger.error(HttpStatus.UPGRADE_REQUIRED.name(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UPGRADE_REQUIRED);
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<Object> handle(ServiceUnavailableException e) {
		logger.error(HttpStatus.SERVICE_UNAVAILABLE.name(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}

    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Object> handle(NotImplementedException e) {
        logger.error(HttpStatus.NOT_IMPLEMENTED.name(), e);
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handle(CustomException e) {
        ErrorMessage errorMessage = e.getErrorMessage();
        String code = errorMessage.name();
        String message = messageSource.getMessage(errorMessage.getValue(), null, HeaderUtil.getLocal());
        return new ResponseEntity<>(new ApiError(code, message), e.getHttpStatus());
    }

}