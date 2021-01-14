package com.common.exception.handler;


import com.common.exception.BusinessException;
import com.common.exception.HeaderException;
import com.common.exception.NotFoundException;
import com.common.exception.ValidationException;
import com.common.exception.response.ErrorResponse;
import com.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Component
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public final class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private Logger LOGGER;

	@ExceptionHandler(value = NotFoundException.class)
	public final ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(getError(e));
	}

	@ExceptionHandler(value = HeaderException.class)
	public final ResponseEntity<ErrorResponse> headerException(HeaderException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getError(e));
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public final ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
		log(e);
		ErrorResponse error = getError("ILA" + e.hashCode(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public final ResponseEntity<ErrorResponse> ConstraintViolationException(ConstraintViolationException e) {
		log(e);
		ErrorResponse error = getError("CV" + e.hashCode(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(value = ValidationException.class)
	public final ResponseEntity<ErrorResponse> validationException(ValidationException e) {
		log(e);
		ErrorResponse error = getError("V" + e.hashCode(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public final ResponseEntity<ErrorResponse> runtimeException(RuntimeException e) {
		log(e);
		ErrorResponse error = getError("" + e.hashCode(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log(ex);
		final ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
		String errorMessage = objectError.getDefaultMessage();
		final ErrorResponse error = getError("MANV" + errorMessage.hashCode(), errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	private ErrorResponse getError(BusinessException e) {
		log(e);
		return getError(e.getCode(), e.getMessage());
	}

	private ErrorResponse getError(String code, String message) {
		LOGGER.log("Parsing exception a friendly error response");
		LOGGER.log(message);
		return new ErrorResponse(code, message);
	}

	private void log(Throwable e) {
		LOGGER.exception(e);
	}
}
