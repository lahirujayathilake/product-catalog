package com.example.productcatalog.errorhandle;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * Global exception handler
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle MissingServletRequestParameterException.
     * When a required parameter is missing this method will be triggered.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        return createResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getParameterName() + " parameter is missing", ex));
    }

    /**
     * Handle MethodArgumentNotValidException.
     * Triggered when an object fails @Valid validation.
     *
     * @param ex      MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object wrapped with ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, SubErrorType.VALIDATION_ERROR.toString(), ex);
        apiError.addFieldErrors(ex.getBindingResult().getFieldErrors(), SubErrorType.VALIDATION_ERROR);
        apiError.addGlobalErrors(ex.getBindingResult().getGlobalErrors(), SubErrorType.VALIDATION_ERROR);

        return createResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object wrapped with ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return createResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON Request", ex));
    }

    /**
     * Handle HttpMessageNotWritableException
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object wrapped with ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return createResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error Writing JSON Output", ex));
    }

    /**
     * Handle NoHandlerFoundException
     *
     * @param ex      NoHandlerFoundException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object wrapped with ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        return createResponseEntity(apiError);
    }

    /**
     * Handles ConstraintViolationException.
     * Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object wrapped with ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, SubErrorType.CONSTRAINT_VIOLATION.toString());
        apiError.addConstraintViolations(ex.getConstraintViolations());

        return createResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object wrapped with ResponseEntity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Entity Not Found");
        apiError.setDebugMessage(ex.getLocalizedMessage());

        return createResponseEntity(apiError);
    }

    /**
     * Handle DataIntegrityViolationException.
     * Inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object wrapped with ResponseEntity
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return createResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database Error", ex.getCause()));
        }

        return createResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Database Error"));
    }

    /**
     * Handle Exception, handle generic Exceptions
     *
     * @param ex the Exception
     * @return the ApiError object wrapped with ResponseEntity
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                        ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return createResponseEntity(apiError);
    }

    /**
     * Handle AccessDeniedException, handle generic Exceptions
     *
     * @param ex the Exception
     * @return the ApiError object wrapped with ResponseEntity
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, "Access Denied For", ex);
        apiError.setDebugMessage(ex.getMessage());
        return createResponseEntity(apiError);
    }

    private ResponseEntity<Object> createResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
