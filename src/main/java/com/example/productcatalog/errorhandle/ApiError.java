package com.example.productcatalog.errorhandle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Error class wrapper with meaningful messages to embedded
 * in a {@link org.springframework.http.ResponseEntity}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
public class ApiError {

    private HttpStatus httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String debugMessage;

    private List<ApiSubError> subErrors;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiError(HttpStatus httpStatus, String message, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();

    }

    private void addSubError(ApiSubError error) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }

        subErrors.add(error);
    }

    /**
     * Create {@link ApiSubError} using {@link FieldError FieldError's}
     * properties and add to the {@link #subErrors} list
     *
     * @param fieldErrors object which properties should be added to the {@link #subErrors}
     * @param type        error type
     */
    public void addFieldErrors(List<FieldError> fieldErrors, SubErrorType type) {
        fieldErrors.forEach(f -> addSubError(
                new ApiSubError(f.getObjectName(), f.getField(), f.getRejectedValue(), f.getDefaultMessage(), type)));

    }

    /**
     * Create {@link ApiSubError} using {@link ObjectError ObjectError's}
     * properties and add to the {@link #subErrors} list
     *
     * @param objectErrors object which properties should be added to the {@link #subErrors}
     * @param type         error type
     */
    public void addGlobalErrors(List<ObjectError> objectErrors, SubErrorType type) {
        objectErrors.forEach(g -> addSubError(new ApiSubError(g.getObjectName(), g.getDefaultMessage(), type)));
    }


    /**
     * Create {@link ApiSubError} using {@link ConstraintViolation ConstriantViolation's}
     * properties and add to the {@link #subErrors} list
     *
     * @param constraintViolations object which properties should be added to the {@link #subErrors}
     */
    public void addConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(cv -> addSubError(
                new ApiSubError(cv.getRootBeanClass().getSimpleName(),
                        ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                        cv.getInvalidValue(), cv.getMessage(), SubErrorType.CONSTRAINT_VIOLATION)));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
