package com.perisatto.fiapprj.menuguru_order.handler.exceptions;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.perisatto.fiapprj.menuguru_order.handler.errors.HttpErrorHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	static final Logger logger = LogManager.getLogger(RestExceptionHandler.class);
	
	@Autowired
	private Properties requestProperties;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Bad request";
		String detail = ex.getMessage();
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Bad request";
		String detail = "Malformed request or unknown fields";
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Unsupported media type";
		String detail = ex.getMessage();
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Method not allowed";
		String detail = ex.getMessage();
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.METHOD_NOT_ALLOWED);
	}	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Internal server error";
		String detail = ex.getMessage();
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> handleRequestSchemaValidation(ValidationException ex) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Unprocessable entity";
		String detail = ex.getMessage();
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFound(NotFoundException ex) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Resource not found";
		String detail = ex.getMessage();
		String instance = resourcePath;
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleExceptions(Exception ex) {
		String resourcePath = requestProperties.getProperty("resourcePath");
		String title = "Internal server error";
		String detail = "A error ocurred during the operation. Please refer to application log for details";		
		String instance = resourcePath;
		logger.error(ex.getMessage());
		return buildResponseEntity(new HttpErrorHandler(title, detail, instance), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	private ResponseEntity<Object> buildResponseEntity(HttpErrorHandler apiError, HttpStatus httpStatus) {
		logger.debug("Finished with exception: " + apiError.getDetail());
		return new ResponseEntity<>(apiError, httpStatus);
	}

}
