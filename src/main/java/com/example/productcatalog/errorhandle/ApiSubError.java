package com.example.productcatalog.errorhandle;

/**
 * Sub-typed error which will be used in {@link ApiError}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public class ApiSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
    private SubErrorType subErrorType;

    public ApiSubError(String object, String field, Object rejectedValue, String message, SubErrorType subErrorType) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
        this.subErrorType = subErrorType;
    }

    public ApiSubError(String object, String message, SubErrorType subErrorType) {
        this.object = object;
        this.message = message;
        this.subErrorType = subErrorType;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SubErrorType getSubErrorType() {
        return subErrorType;
    }

    public void setSubErrorType(SubErrorType subErrorType) {
        this.subErrorType = subErrorType;
    }
}
